package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends ClientInterface, Remote {
    void notifyLogin(Subject subject, String username) throws RemoteException;
    void printMessage(Advise advise) throws RemoteException;
    void notify(Message message, Subject subject) throws RemoteException;
    void notify(Message message, Subject subject, Object object) throws RemoteException;
}
