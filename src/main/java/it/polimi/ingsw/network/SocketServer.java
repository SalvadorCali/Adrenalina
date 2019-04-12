package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServer implements Runnable {
    private Thread myThread;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public SocketServer(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        myThread = new Thread(this);
        myThread.start();
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (myThread == thisThread) {

        }
    }
}
