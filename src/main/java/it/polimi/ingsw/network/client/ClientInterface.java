package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ClientInterface {
    void start() throws RemoteException;
    void login(String username, TokenColor color) throws RemoteException;
    void disconnect() throws RemoteException;
    void choose(int choice) throws IOException;
    void showSquare() throws IOException;
    void move(Direction...directions) throws IOException;
    void grab(int choice, Direction...directions) throws IOException;
    void shoot(TokenColor color) throws IOException;
    void powerup(String powerup, int x, int y) throws IOException;
    void powerup(String powerup, Direction direction, int value) throws IOException;
    void powerupAmmos(PowerupData...powerups) throws IOException;
    void reload(int...weapons) throws IOException;
    void endTurn() throws IOException;
    AdrenalineZone getAdrenalineZone() throws RemoteException;
    void testConnection() throws RemoteException;
}
