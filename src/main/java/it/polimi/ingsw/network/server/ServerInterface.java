package it.polimi.ingsw.network.server;

import it.polimi.ingsw.view.Response;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ServerInterface {
    void notifyLogin(Response response, String username) throws IOException;
}
