package it.polimi.ingsw.network.server;

import it.polimi.ingsw.view.Response;

import java.rmi.RemoteException;

public interface ServerInterface {
    void notifyLogin(Response response) throws RemoteException;
}
