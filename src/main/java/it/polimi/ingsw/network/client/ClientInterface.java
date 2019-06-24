package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.view.ViewInterface;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Contains methods that will be implemented by {@link it.polimi.ingsw.network.client.rmi.RMIClient} and {@link it.polimi.ingsw.network.client.socket.SocketClient}.
 */
public interface ClientInterface {
    /**
     * Getter for the playerController.
     * @return the playerController.
     * @throws RemoteException caused by the remote method.
     */
    PlayerController getPlayerController() throws RemoteException;

    /**
     * Starts the Client.
     * @throws RemoteException caused by the remote method.
     */
    void start() throws RemoteException;

    /**
     * Method for the login action.
     * @param username the username chosen by the user.
     * @param color the color chosen by the user.
     * @throws RemoteException caused by the remote method.
     */
    void login(String username, TokenColor color) throws RemoteException;

    /**
     * Sets the view.
     * @param view the view interface.
     * @throws RemoteException caused by the remote method.
     */
    void setView(ViewInterface view) throws RemoteException;

    /**
     * Method for the disconnection.
     * @throws RemoteException caused by the remote method.
     */
    void disconnect() throws RemoteException;

    /**
     * Method for the choice of the number and the number of skulls for the game.
     * @param boardType the chosen board.
     * @param skulls the number of skulls.
     * @throws IOException caused by the remote method or by the streams.
     */
    void board(int boardType, int skulls) throws IOException;

    /**
     * Method for the first choice of the powerup and the spawn.
     * @param choice the chosen powerup.
     * @throws IOException caused by the remote method or by the streams.
     */
    void choose(int choice) throws IOException;

    /**
     * Method to see the square of the board where the player is.
     * @throws IOException caused by the remote method or by the streams.
     */
    void showSquare() throws IOException;

    /**
     * Method to see a square of the board.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @throws IOException caused by the remote method or by the streams.
     */
    void showSquare(int x, int y) throws IOException;

    /**
     * Method for the move action.
     * @param directions the directions chosen by the user.
     * @throws IOException caused by the remote method or by the streams.
     */
    void move(Direction...directions) throws IOException;

    /**
     * Method for the grab action.
     * @param choice the object to grab.
     * @param directions the directions where the user wants to move.
     * @throws IOException caused by the remote method or by the streams.
     */
    void grab(int choice, Direction...directions) throws IOException;

    /**
     * Method to drop a powerup.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the remote method or by the streams.
     */
    void dropPowerup(int powerup) throws IOException;

    /**
     * Method to drop a weapon.
     * @param weapon the chosen weapon.
     * @throws IOException caused by the remote method or by the streams.
     */
    void dropWeapon(int weapon) throws IOException;

    /**
     * Method to discard a powerup and to use it as ammo.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the remote method or by the streams.
     */
    void discardPowerup(int powerup) throws IOException;

    /**
     * Method for the move and reload action, before the shoot action.
     * @param firstDirection the chosen direction.
     * @param weapons the chosen weapons.
     * @throws IOException caused by the remote method or by the streams.
     */
    void moveAndReload(Direction firstDirection, String...weapons) throws IOException;

    /**
     * Method for the move and reload action, before the shoot action.
     * @param firstDirection the first direction.
     * @param secondDirection the second direction.
     * @param weapons the chosen weapons.
     * @throws IOException caused by the remote method or by the streams.
     */
    void moveAndReload(Direction firstDirection, Direction secondDirection, String...weapons) throws IOException;

    /**
     * Method for the shoot action.
     * @param weaponName the chosen weapon.
     * @param effectNumber the chosen effect.
     * @param basicFirst true if the user wants to use the basic effect first.
     * @param firstVictim the first victim.
     * @param secondVictim the second victim.
     * @param thirdVictim the third victim.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the directions chosen by the user.
     * @throws IOException caused by the remote method or by the streams.
     */
    void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction...directions) throws IOException;

    /**
     * Method to use a powerup.
     * @param powerup the chosen powerup.
     * @param victim the victim.
     * @param ammo the color of the ammo.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the directions chosen by the user.
     * @throws IOException caused by the remote method or by the streams.
     */
    void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction...directions) throws IOException;

    /**
     * Method for the reload action.
     * @param weaponName the chosen weapon.
     * @throws IOException caused by the remote method or by the streams.
     */
    void reload(String weaponName) throws IOException;

    /**
     * Method for the respawn, after a death.
     * @param powerup the chosen powerup used for the respawn.
     * @throws IOException caused by the remote method or by the streams.
     */
    void respawn(int powerup) throws IOException;

    /**
     * Method to end a turn.
     * @throws IOException caused by the remote method or by the streams.
     */
    void endTurn() throws IOException;

    /**
     * Test the connection.
     * @throws RemoteException
     */
    void testConnection() throws RemoteException;
}
