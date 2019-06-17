package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.controller.GameData;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends ClientInterface, Remote {
    void notify(Message message) throws RemoteException;
    void notify(Message message, Outcome outcome) throws RemoteException;
    void notify(Message message, Outcome outcome, GameData gameData) throws RemoteException;
}
