package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;
import it.polimi.ingsw.network.server.socket.SocketServer;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager implements ConnectionInterface, Runnable {
    private ServerSocket serverSocket;
    private Thread thisThread;
    private final ExecutorService pool;
    private ServerController serverController;
    private ServerControllerManager serverControllerManager = new ServerControllerManager();

    public ConnectionManager() throws IOException {
        //super(Config.RMI_FREE_PORT);
        //this.serverController = serverController;

        //socket
        serverSocket = new ServerSocket(Config.SOCKET_PORT);
        pool = Executors.newFixedThreadPool(Config.EXECUTOR_SIZE);

        //rmi extends UnicastRemoteObject
        /*
        LocateRegistry.createRegistry(Config.RMI_PORT);
        java.rmi.Naming.rebind("server", this);
        */

        //rmi new
        Registry registry = LocateRegistry.createRegistry(Config.RMI_PORT);
        //ConnectionInterface server = (ConnectionInterface) UnicastRemoteObject.exportObject(this, Config.RMI_PORT);
        //try {
            registry.rebind("server", (ConnectionInterface) UnicastRemoteObject.exportObject(this, Config.RMI_FREE_PORT));
        //} catch (AlreadyBoundException e) {
          //  Printer.err(e);
        //}
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
                pool.execute(new SocketServer(serverSocket.accept(), ServerControllerManager.getServerController()));
            } catch (Exception e) {
                Printer.err(e);
            }
        }
    }

    @Override
    public RMIServerInterface enrol(RMIClientInterface client) throws RemoteException {
        //return (RMIServerInterface) UnicastRemoteObject.exportObject(new RMIServer(client, serverController), Config.RMI_FREE_PORT);
        return new RMIServer(client, ServerControllerManager.getServerController());
    }
}
