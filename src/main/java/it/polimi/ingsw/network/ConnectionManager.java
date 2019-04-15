package it.polimi.ingsw.network;

import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager extends UnicastRemoteObject implements ConnectionInterface, Runnable {
    private ServerSocket serverSocket;
    private Thread thisThread;
    private final ExecutorService pool;

    public ConnectionManager() throws IOException {
        //socket
        serverSocket = new ServerSocket(Config.SOCKET_PORT);
        pool = Executors.newFixedThreadPool(Config.EXECUTOR_SIZE);

        //rmi
        LocateRegistry.createRegistry(Config.RMI_PORT);
        java.rmi.Naming.rebind("server", this);
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

    @Override
    public RMIServerInterface enrol(RMIClientInterface client) throws RemoteException {
        return new RMIServer(client);
    }
}
