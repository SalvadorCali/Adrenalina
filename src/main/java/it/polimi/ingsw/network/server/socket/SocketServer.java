package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketServer implements Runnable, ServerInterface {
    private Thread thisThread;
    private Socket socket;
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
                Printer.err(e);
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
            case COLOR:
                break;
            default:
                break;
        }
    }

    public void login(){
        try{
            clientName = objectInputStream.readUTF();
        } catch (IOException e) {
            Printer.err(e);
        }
        serverController.addClient(clientName, this);
    }

    public void chooseColor(){
        TokenColor color = TokenColor.NONE;
        try {
            color = (TokenColor) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
        if(!color.equals(TokenColor.NONE)) {
            serverController.chooseColor(clientName, color);
        }
    }

    @Override
    public void notifyLogin(Subject subject, String username) throws IOException {
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(username);
        objectOutputStream.flush();
        objectOutputStream.writeObject(subject);
        objectOutputStream.flush();
    }

    @Override
    public void notify(Message message, Subject subject, Object object) throws RemoteException {

    }

    @Override
    public void sendMessage(Advise advise) throws IOException {
        objectOutputStream.writeObject(Message.MESSAGE);
        objectOutputStream.flush();
        objectOutputStream.writeObject(advise);
        objectOutputStream.flush();
    }
}
