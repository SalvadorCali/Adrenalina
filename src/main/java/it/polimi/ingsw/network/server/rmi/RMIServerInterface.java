package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.server.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIServerInterface extends ServerInterface, Remote {
    void login(String username, TokenColor color, ConnectionTimer connectionTimer) throws RemoteException;
    void choose(int choice) throws RemoteException;
    void showSquare() throws RemoteException;
    void move(Direction...directions) throws RemoteException;
    void grab(int choice, Direction...directions) throws RemoteException;
    void shoot(String weaponName, int effectNumber, TokenColor...colors) throws RemoteException;
    void shoot(String weaponName, int effectNumber, TokenColor color, int x, int y) throws RemoteException;
    void shoot(String weaponName, int effectNumber, TokenColor color, Direction...directions) throws RemoteException;
    void powerup(String powerup, int x, int y) throws RemoteException;
    void powerup(String powerup, Direction direction, int value) throws RemoteException;
    void powerupAmmos(PowerupData...powerups) throws RemoteException;
    void reload(int...weapons) throws RemoteException;
    void endTurn() throws RemoteException;
}
