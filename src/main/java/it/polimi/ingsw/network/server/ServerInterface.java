package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameData;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * This interface contains method that will be implemented by {@link it.polimi.ingsw.network.server.rmi.RMIServer} and {@link it.polimi.ingsw.network.server.socket.SocketServer}.
 */
public interface ServerInterface {
    /**
     * Method for the disconnection.
     * @throws RemoteException caused by the remote method.
     */
    void disconnect() throws RemoteException;

    /**
     * Sets the serverController.
     * @param serverController the ServerController that will be set.
     * @throws RemoteException caused by the remote method.
     */
    void setServerController(ServerController serverController) throws RemoteException;

    /**
     * Notify method.
     * @param message the message sent by the ServerController.
     * @throws IOException caused by the remote method or by the streams.
     */
    void notify(Message message) throws IOException;

    /**
     * Notify method, with outcome.
     * @param message the message sent by the ServerController.
     * @param outcome the outcome of the action.
     * @throws IOException caused by the remote method or by the streams.
     */
    void notify(Message message, Outcome outcome) throws IOException;

    /**
     * Notify method, with outcome and datas.
     * @param message the message sent by the ServerController.
     * @param outcome the outcome of the action.
     * @param gameData datas of the game.
     * @throws IOException caused by the remote method or by the streams.
     */
    void notify(Message message, Outcome outcome, GameData gameData) throws IOException;
}
