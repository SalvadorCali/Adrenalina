package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends ClientInterface, Remote {
    void notifyLogin(Response response, String username) throws RemoteException;
    void printMessage(Advise advise) throws RemoteException;
}
