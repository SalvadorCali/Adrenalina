package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.datas.GameData;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Config;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implements the {@link RMIServerInterface} and represents the Server for the rmi connection.
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    /**
     * The relative Client.
     */
    private RMIClientInterface client;
    /**
     * The ServerController that handles the game.
     */
    private ServerController serverController;
    /**
     * A timer to handles the connection of the Client.
     */
    private ConnectionTimer connectionTimer;
    /**
     * The username of the relative Client.
     */
    private String clientName;

    /**
     * Class constructor.
     * @param client the relative Client.
     * @param serverController the serverController of the game.
     * @throws RemoteException caused by the remote method.
     */
    public RMIServer(RMIClientInterface client, ServerController serverController) throws RemoteException {
        super(Config.RMI_FREE_PORT);
        this.serverController = serverController;
        this.client = client;
    }

    /**
     * Setter for the ServerController.
     * @param serverController the ServerController that will be set.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void setServerController(ServerController serverController) throws RemoteException{
        this.serverController = serverController;
    }

    /**
     * Takes username and color from the Client and sends them to the ServerController.
     * @param username the chosen username.
     * @param color the color chosen by the user.
     * @param connectionTimer the timer for the connection.
     */
    @Override
    public void login(String username, TokenColor color, ConnectionTimer connectionTimer){
        this.connectionTimer = connectionTimer;
        connectionTimer.setServer(this);
        connectionTimer.start();
        clientName = username;
        serverController.login(username, color, this);
    }

    /**
     * Calls the relative method of the ServerController.
     */
    @Override
    public void disconnect(){
        serverController.disconnect(clientName);
    }

    /**
     * Takes a board and a number of skulls and sends them to the ServerController.
     * @param boardType the chosen board.
     * @param skulls the number of skulls for the game.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void board(int boardType, int skulls) throws RemoteException {
        serverController.chooseBoardType(boardType, skulls);
    }

    /**
     * Takes the chosen powerup for the spawn and sends it to the ServerController.
     * @param choice the chosen powerup.
     */
    @Override
    public void choose(int choice){
        serverController.choose(clientName, choice);
    }

    /**
     * Calls the relative ServerController method that shows the square where the player is in.
     */
    @Override
    public void showSquare(){
        serverController.showSquare(clientName);
    }

    /**
     * Takes the coordinates of the square that the user wants to see and sends them to the ServerController.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     */
    @Override
    public void showSquare(int x, int y){
        serverController.showSquare(clientName, x, y);
    }

    /**
     * Takes the directions where the user wants to move and sends them to the ServerController.
     * @param directions the directions where the user wants to move.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void move(Direction... directions) throws RemoteException {
        serverController.move(clientName, directions);
    }

    /**
     * Takes the object to grab and the directions where the user wants to move.
     * @param choice the chosen object to grab.
     * @param directions the directions where the user wants to move.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void grab(int choice, Direction...directions) throws RemoteException{
        serverController.grab(clientName, choice, directions);
    }

    /**
     * Takes the powerup that the user wants to drop.
     * @param powerup the chosen powerup.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void dropPowerup(int powerup) throws RemoteException {
        serverController.dropPowerup(clientName, powerup);
    }

    /**
     * Takes the weapon that the user wants to drop.
     * @param weapon the chosen weapon.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void dropWeapon(int weapon) throws RemoteException {
        serverController.dropWeapon(clientName, weapon);
    }

    /**
     * Takes the powerup that the user wants to discard to use it as ammo.
     * @param powerup the chosen powerup.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void discardPowerup(int powerup) throws RemoteException {
        serverController.discardPowerup(clientName, powerup);
    }

    /**
     * Takes the parameters for the shoot action.
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
    @Override
    public void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction... directions) throws RemoteException {
        serverController.shoot(weaponName, effectNumber, basicFirst, clientName, firstVictim, secondVictim, thirdVictim, x, y, directions);
    }

    /**
     * Takes a direction and an array of weapon's names for the move and reload action.
     * @param firstDirection the chosen direction.
     * @param weapons the chosen weapons.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void moveAndReload(Direction firstDirection, String... weapons) throws RemoteException {
        serverController.moveAndReload(clientName, firstDirection, weapons);
    }

    /**
     * Takes two directions and an array of weapon's names for the move and reload action.
     * @param firstDirection the first direction.
     * @param secondDirection the second direction.
     * @param weapons the chosen weapons.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws RemoteException {
        serverController.moveAndReload(clientName, firstDirection, secondDirection, weapons);
    }

    /**
     * Takes parameters to use powerups during the game.
     * @param powerup the chosen powerup.
     * @param victim the victim.
     * @param ammo the color of the ammo.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the chosen directions.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction... directions) throws RemoteException {
        serverController.powerup(clientName, powerup, victim, ammo, x, y, directions);
    }

    /**
     * Takes the name of the weapon to reload.
     * @param weaponName the chosen weapon.
     */
    @Override
    public void reload(String weaponName){
        serverController.reload(clientName, weaponName);
    }

    /**
     * Takes the powerup for the respawn.
     * @param powerup the chosen powerup.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void respawn(int powerup) throws RemoteException {
        serverController.respawn(clientName, powerup);
    }

    /**
     * Calls the relative ServerController method to end the turn.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void endTurn() throws RemoteException{
        serverController.endTurn(clientName);
    }

    /**
     * Calls the Client's notify method.
     * @param message the message sent by the ServerController.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void notify(Message message) throws RemoteException {
        client.notify(message);
    }

    /**
     * Calls the Client's notify method.
     * @param message the message sent by the ServerController.
     * @param outcome the outcome of the action.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void notify(Message message, Outcome outcome) throws RemoteException {
        client.notify(message, outcome);
    }

    /**
     * Calls the client's notify method.
     * @param message the message sent by the ServerController.
     * @param outcome the outcome of the action.
     * @param gameData datas of the game.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void notify(Message message, Outcome outcome, GameData gameData) throws RemoteException {
        client.notify(message, outcome, gameData);
    }
}
