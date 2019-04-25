package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.server.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIServerInterface extends ServerInterface, Remote {
    void login(String username, TokenColor color) throws RemoteException;
    void move(Direction...directions) throws RemoteException;
    void grab(int choice, Direction...directions) throws RemoteException;
    void endTurn() throws RemoteException;
}
