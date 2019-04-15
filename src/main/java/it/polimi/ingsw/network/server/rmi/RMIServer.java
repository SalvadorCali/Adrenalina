package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.util.Printer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private RMIClientInterface client;
    public RMIServer(RMIClientInterface client) throws RemoteException {
        super();
        this.client = client;
    }

    @Override
    public void hello() throws RemoteException {
        Printer.println("Hello");
    }
}
