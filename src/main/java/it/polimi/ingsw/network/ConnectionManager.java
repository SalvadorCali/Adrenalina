package it.polimi.ingsw.network;

import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager implements ConnectionInterface, Runnable {
    private ServerSocket serverSocket;
    private Thread thisThread;
    private final ExecutorService pool;

    public ConnectionManager() throws IOException {
        serverSocket = new ServerSocket(4321);
        pool = Executors.newFixedThreadPool(30);
    }

    public void start(){
        thisThread = new Thread(this);
        thisThread.start();
    }

    @Override
    public void run(){
        Thread currentThread = Thread.currentThread();
        while (thisThread == currentThread) {
            try {
                pool.execute(new SocketServer(serverSocket.accept()));
            } catch (Exception e) {
                Printer.err(e);
            }
        }
    }
}
