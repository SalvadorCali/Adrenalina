package it.polimi.ingsw.network;

import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager implements ConnectionInterface, Runnable {
    private static final int SOCKET_PORT = 4321;
    private static final int EXECUTOR_SIZE = 30;
    private ServerSocket serverSocket;
    private Thread thisThread;
    private final ExecutorService pool;

    public ConnectionManager() throws IOException {
        serverSocket = new ServerSocket(SOCKET_PORT);
        pool = Executors.newFixedThreadPool(EXECUTOR_SIZE);
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
