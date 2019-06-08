package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.server.ServerInterface;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIServerInterface extends ServerInterface, Remote {
    void login(String username, TokenColor color, ConnectionTimer connectionTimer) throws RemoteException;
    void board(int boardType, int skulls) throws RemoteException;
    void choose(int choice) throws RemoteException;
    void showSquare() throws RemoteException;
    void showSquare(int x, int y) throws RemoteException;
    void move(Direction...directions) throws RemoteException;
    void drop(String weaponName) throws RemoteException;
    void grab(int choice, Direction...directions) throws RemoteException;
    void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction...directions) throws RemoteException;
    void shoot(String weaponName, int effectNumber, TokenColor...colors) throws RemoteException;
    void shoot(String weaponName, int effectNumber, TokenColor color, int x, int y) throws RemoteException;
    void shoot(String weaponName, TokenColor color, int effectNumber, Direction...directions) throws RemoteException;
    void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws RemoteException;
    void powerup(String powerup, int x, int y) throws RemoteException;
    void powerup(String powerup, Direction direction, int value) throws RemoteException;
    void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction...directions) throws RemoteException;
    void powerupAmmos(PowerupData...powerups) throws RemoteException;
    void reload(String weaponName) throws RemoteException;
    void endTurn() throws RemoteException;
}
