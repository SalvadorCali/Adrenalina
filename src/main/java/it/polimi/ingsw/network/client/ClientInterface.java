package it.polimi.ingsw.network.client;

import java.rmi.RemoteException;

public interface ClientInterface {
    void start() throws RemoteException;
    void login(String username) throws RemoteException;
}
