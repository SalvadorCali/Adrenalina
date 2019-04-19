package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientInterface {
    void start() throws RemoteException;
    void login(String username) throws RemoteException;
    void chooseColor(TokenColor color) throws RemoteException;
    void move(Direction...directions) throws IOException;
}
