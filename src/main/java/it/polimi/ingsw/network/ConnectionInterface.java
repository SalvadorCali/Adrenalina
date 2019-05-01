package it.polimi.ingsw.network;

import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectionInterface extends Remote {
    RMIServerInterface enrol(RMIClientInterface client) throws RemoteException;
    void print() throws RemoteException;
}
