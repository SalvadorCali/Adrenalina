package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    @Override
    public void notifyLogin(Response response, String username) throws IOException {
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(username);
        objectOutputStream.flush();
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
    }

    @Override
    public void sendMessage(Advise advise) throws IOException {
        objectOutputStream.writeObject(Message.MESSAGE);
        objectOutputStream.flush();
        objectOutputStream.writeObject(advise);
        objectOutputStream.flush();
    }
}
