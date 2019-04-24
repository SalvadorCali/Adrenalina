package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.CommandLine;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements ClientInterface, Runnable {
    private PlayerController playerController;
    private Thread thisThread;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ViewInterface view;
    private String username;

    public SocketClient() throws IOException {
        playerController = new PlayerController(this);
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        Printer.print("[CLIENT]Please, set an ip address:");
        String host = userInputStream.readLine();
        Socket clientSocket = new Socket(host, Config.SOCKET_PORT);

        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        view = new CommandLine(this);
        view.start();
    }

    public void start() {
        thisThread = new Thread(this);
        thisThread.start();
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (this.thisThread == currentThread){
            try {
                Message message = (Message) objectInputStream.readObject();
                readRequest(message);
            } catch (IOException | ClassNotFoundException e) {
                Printer.err(e);
            }
        }
    }

    public void readRequest(Message message){
        switch(message){
            case NOTIFY:
                    notifyMessage();
                break;
            case COLOR:
                try {
                    notifyColor();
                } catch (IOException | ClassNotFoundException e) {
                    Printer.err(e);
                }
                break;
            case MESSAGE:
                try {
                    printMessage();
                } catch (IOException | ClassNotFoundException e) {
                    Printer.err(e);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void login(String username, TokenColor color){
        try {
            objectOutputStream.writeObject(Message.LOGIN);
            objectOutputStream.flush();
            objectOutputStream.writeUTF(username);
            objectOutputStream.flush();
            objectOutputStream.writeObject(color);
            objectOutputStream.flush();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    @Override
    public void chooseColor(TokenColor color) throws RemoteException {
        try {
            objectOutputStream.writeObject(Message.COLOR);
            objectOutputStream.flush();
            objectOutputStream.writeObject(color);
            objectOutputStream.flush();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    @Override
    public void move(Direction... directions) throws IOException {
        objectOutputStream.writeObject(Message.MOVE);
        objectOutputStream.flush();
        int directionsSize = directions.length;
        objectOutputStream.writeInt(directionsSize);
        objectOutputStream.flush();
        for (Direction direction : directions) {
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    @Override
    public void grab(int choice, Direction...directions) throws IOException {
        int directionsSize = directions.length;
        objectOutputStream.writeObject(Message.GRAB);
        objectOutputStream.flush();
        objectOutputStream.writeInt(choice);
        objectOutputStream.flush();
        objectOutputStream.writeInt(directionsSize);
        objectOutputStream.flush();
        for (Direction direction : directions) {
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    public void notifyColor() throws IOException, ClassNotFoundException {
        Subject subject = (Subject) objectInputStream.readObject();
        Object object = objectInputStream.readObject();
        view.notify(Message.COLOR, subject, object);
    }

    public void notifyLogin() throws IOException, ClassNotFoundException {
        String username = objectInputStream.readUTF();
        Subject subject = (Subject) objectInputStream.readObject();
        view.notifyLogin(subject, username);
    }

    public void notifyMessage(){

    }

    public void printMessage() throws IOException, ClassNotFoundException {
        Advise advise = (Advise) objectInputStream.readObject();
        view.printMessage(advise);
    }

    @Override
    public AdrenalineZone getAdrenalineZone(){
        return playerController.getAdrenalineZone();
    }
}
