package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.ServerControllerManager;
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
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class establishes the connection between Client and Server (rmi or socket). It is also a remote object.
 */
public class ConnectionManager implements ConnectionInterface, Runnable {
    /**
     * A ServerSocket for the socket connection.
     */
    private ServerSocket serverSocket;
    /**
     * A Thread object that represents this thread.
     */
    private Thread thisThread;
    /**
     * A pool to handles the socket connections.
     */
    private final ExecutorService pool;
    /**
     * A ServerController object.
     */
    private ServerController serverController;
    /**
     * The ServerControllerManager that gives ServerController objects.
     */
    private ServerControllerManager serverControllerManager = new ServerControllerManager();

    /**
     * Class constructor. Set the parameters for socket and rmi connections.
     * @throws IOException caused by the sockets.
     */
    public ConnectionManager() throws IOException {
        //socket
        serverSocket = new ServerSocket(Config.SOCKET_PORT);
        pool = Executors.newFixedThreadPool(Config.EXECUTOR_SIZE);

        //rmi
        Registry registry = LocateRegistry.createRegistry(Config.RMI_PORT);
        registry.rebind(NetworkString.REGISTRY_NAME, (ConnectionInterface) UnicastRemoteObject.exportObject(this, Config.RMI_FREE_PORT));
    }

    /**
     * Creates a new Thread and starts it.
     */
    public void start(){
        thisThread = new Thread(this);
        thisThread.start();
    }

    /**
     * In a while cycle accepts socket connections.
     */
    @Override
    public void run(){
        Thread currentThread = Thread.currentThread();
        while (thisThread == currentThread) {
            try {
                pool.execute(new SocketServer(serverSocket.accept(), ServerControllerManager.getServerController()));
            } catch (Exception e) {
                Printer.err(e);
            }
        }
    }

    /**
     * A remote method that gives to the Client its relative Server.
     * @param client a Client that called this method.
     * @return a new Server for the Client.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public RMIServerInterface enrol(RMIClientInterface client) throws RemoteException {
        return new RMIServer(client, ServerControllerManager.getServerController());
    }
}
