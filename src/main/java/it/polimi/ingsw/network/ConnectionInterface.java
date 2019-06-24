package it.polimi.ingsw.network;

import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A Remote interface used by the Client to have its personal Server.
 */
public interface ConnectionInterface extends Remote {
    /**
     * Gives to the Client its Server.
     * @param client the Client that called this method.
     * @return a new Server for the Client.
     * @throws RemoteException caused by the remote method.
     */
    RMIServerInterface enrol(RMIClientInterface client) throws RemoteException;
}
