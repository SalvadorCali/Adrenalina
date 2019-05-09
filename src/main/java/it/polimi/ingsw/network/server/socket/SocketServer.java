package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServer implements Runnable, ServerInterface {
    private Thread thisThread;
    private Socket socket;
    private ConnectionTimer connectionTimer;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ServerController serverController;
    private String clientName;

    public SocketServer(Socket socket, ServerController serverController) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.serverController = serverController;
        thisThread = new Thread(this);
        thisThread.start();
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (thisThread == currentThread) {
            try {
                Message message = (Message) objectInputStream.readObject();
                readRequest(message);
                //int val = objectInputStream.readInt();
                //handle the message
            } catch (IOException e) {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    thisThread = null;
                    serverController.disconnect(clientName);
                    //handle disconnection
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void readRequest(Message message){
        switch (message){
            case LOGIN:
                login();
                break;
            case DISCONNECT:
                disconnect();
                break;
            case USERNAME:
                break;
            case MOVE:
                move();
                break;
            case GRAB:
                grab();
                break;
            case SHOOT:
                shoot();
                break;
            case END_TURN:
                endTurn();
                break;
            default:
                break;
        }
    }

    public void login(){
        TokenColor color = TokenColor.NONE;
        try{
            clientName = objectInputStream.readUTF();
            color = (TokenColor) objectInputStream.readObject();
            connectionTimer = (ConnectionTimer) objectInputStream.readObject();
            connectionTimer.setServer(this);
            connectionTimer.start();
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
        serverController.login(clientName, color, this);
    }

    public void disconnect(){
        serverController.disconnect(clientName);
        try {
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void move(){
        Direction first, second, third;
        try {
            int directionsSize = objectInputStream.readInt();
            if(directionsSize == 1){
                first = (Direction) objectInputStream.readObject();
                serverController.move(clientName, first);
            }else if(directionsSize == 2){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                serverController.move(clientName, first, second);
            }else if(directionsSize == 3){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                third = (Direction) objectInputStream.readObject();
                serverController.move(clientName, first, second, third);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    public void grab(){
        int choice = 0;
        Direction first, second;
        try {
            choice = objectInputStream.readInt();
            int directionsSize = objectInputStream.readInt();
            if(directionsSize == 0){
                serverController.grab(clientName, choice);
            }else if(directionsSize == 1){
                first = (Direction) objectInputStream.readObject();
                serverController.grab(clientName, choice, first);
            }else{
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                serverController.grab(clientName, choice, first, second);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    public void shoot(){
        TokenColor color;
        try {
            color = (TokenColor) objectInputStream.readObject();
            serverController.shoot(clientName, color);
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    public void endTurn(){
        serverController.endTurn(clientName);
    }

    @Override
    public void notify(Message message) throws IOException {
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    @Override
    public void notify(Message message, Outcome outcome) throws IOException {
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.writeObject(outcome);
        objectOutputStream.flush();
    }

    @Override
    public void notify(Message message, Outcome outcome, Object object) throws IOException {
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.writeObject(outcome);
        objectOutputStream.flush();
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }
}
