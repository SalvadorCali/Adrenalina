package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends ClientInterface, Remote {
    void notifyLogin(Outcome outcome, String username) throws RemoteException;
    void printMessage(Advise advise) throws RemoteException;
    void notify(Message message) throws RemoteException;
    void notify(Message message, Outcome outcome) throws RemoteException;
    void notify(Message message, Outcome outcome, Object object) throws RemoteException;
}
