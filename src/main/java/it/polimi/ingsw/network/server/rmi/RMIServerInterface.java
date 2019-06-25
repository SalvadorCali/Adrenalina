package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.server.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This class contains the remote method for the rmi connection.
 */
public interface RMIServerInterface extends ServerInterface, Remote {
    /**
     * Method for the login.
     * @param username the chosen username.
     * @param color the color of the user.
     * @param connectionTimer the timer for the connection.
     * @throws RemoteException caused by the remote method.
     */
    void login(String username, TokenColor color, ConnectionTimer connectionTimer) throws RemoteException;

    /**
     * Method for the choice of the board and the number of skulls of the game.
     * @param boardType the chosen board.
     * @param skulls the number of skulls for the game.
     * @throws RemoteException caused by the remote method.
     */
    void board(int boardType, int skulls) throws RemoteException;

    /**
     * Method for the first choice of powerups.
     * @param choice the chosen powerup.
     * @throws RemoteException caused by the remote method.
     */
    void choose(int choice) throws RemoteException;

    /**
     * Method to show the square where the user is in.
     * @throws RemoteException caused by the remote method.
     */
    void showSquare() throws RemoteException;

    /**
     * Method to show the square requested by the user.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @throws RemoteException caused by the remote method.
     */
    void showSquare(int x, int y) throws RemoteException;

    /**
     * Method for the move action.
     * @param directions the directions chosen by the user.
     * @throws RemoteException caused by the remote method.
     */
    void move(Direction...directions) throws RemoteException;

    /**
     * Method to drop a powerup.
     * @param powerup the chosen powerup to drop.
     * @throws RemoteException caused by the remote method.
     */
    void dropPowerup(int powerup) throws RemoteException;

    /**
     * Method to drop a weapon.
     * @param weapon the chosen weapon to drop.
     * @throws RemoteException caused by the remote method.
     */
    void dropWeapon(int weapon) throws RemoteException;

    /**
     * Method to discard a poerup and use it as ammo.
     * @param powerup the chosen powerup.
     * @throws RemoteException caused by the remote method.
     */
    void discardPowerup(int powerup) throws RemoteException;

    /**
     * Method for the grab action.
     * @param choice the object to grab.
     * @param directions the directions where the user wants to move.
     * @throws RemoteException caused by the remote method.
     */
    void grab(int choice, Direction...directions) throws RemoteException;

    /**
     * Method for the shoot action.
     * @param weaponName the chosen weapon.
     * @param effectNumber the number of the effect.
     * @param basicFirst true if the user wants to use the basic effect first.
     * @param firstVictim the first victim.
     * @param secondVictim the second victim.
     * @param thirdVictim the third victim.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the chosen directions.
     * @throws RemoteException caused by the remote method.
     */
    void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction...directions) throws RemoteException;

    /**
     * Method for the move and reload action, used before shoot.
     * @param firstDirection the chosen direction.
     * @param weapons the weapons to reload.
     * @throws RemoteException caused by the remote method.
     */
    void moveAndReload(Direction firstDirection, String... weapons) throws RemoteException;

    /**
     * Method for the move and reload action, used before shoot.
     * @param firstDirection the first direction.
     * @param secondDirection the second direction.
     * @param weapons the weapons to reload.
     * @throws RemoteException caused by the remote method.
     */
    void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws RemoteException;

    /**
     * Method to use powerups during the game.
     * @param powerup the chosen powerup.
     * @param victim the chosen victim.
     * @param ammo the color of the ammo.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the chosen directions.
     * @throws RemoteException caused by the remote method.
     */
    void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction...directions) throws RemoteException;

    /**
     * Method for the reload action.
     * @param weaponName the weapon to reload.
     * @throws RemoteException caused by the remote method.
     */
    void reload(String weaponName) throws RemoteException;

    /**
     * Method for the respawn.
     * @param powerup the chosen powerup.
     * @throws RemoteException caused by the remote method.
     */
    void respawn(int powerup) throws RemoteException;

    /**
     * Method to end the turn.
     * @throws RemoteException caused by the remote method.
     */
    void endTurn() throws RemoteException;
}
