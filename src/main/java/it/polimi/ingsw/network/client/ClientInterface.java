package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientInterface {
    void start() throws RemoteException;
    void login(String username, TokenColor color) throws RemoteException;
    void chooseColor(TokenColor color) throws RemoteException;
    void move(Direction...directions) throws IOException;
    void grab(int choice, Direction...directions) throws IOException;
    void endTurn() throws IOException;
    AdrenalineZone getAdrenalineZone() throws RemoteException;
}
