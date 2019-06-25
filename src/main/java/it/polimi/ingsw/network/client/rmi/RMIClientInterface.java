package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.controller.datas.GameData;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This class contains remote method that will be implemented by the {@link RMIClient}.
 */
public interface RMIClientInterface extends ClientInterface, Remote {
    /**
     * Notify method for the view.
     * @param message the message sent from the Server.
     * @throws RemoteException caused by the remote method.
     */
    void notify(Message message) throws RemoteException;

    /**
     * Notify method for the view, with outcome.
     * @param message the message sent from the Server.
     * @param outcome the outcome of the action.
     * @throws RemoteException caused by the remote method.
     */
    void notify(Message message, Outcome outcome) throws RemoteException;

    /**
     * Notify method for the view, with outcome and datas.
     * @param message the message sent from the Server.
     * @param outcome the outcome of the action.
     * @param gameData datas of the game.
     * @throws RemoteException caused by the remote method.
     */
    void notify(Message message, Outcome outcome, GameData gameData) throws RemoteException;
}
