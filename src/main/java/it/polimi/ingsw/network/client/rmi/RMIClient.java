package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.controller.datas.GameData;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.controller.timer.ServerConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.NetworkString;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.rmi.RMIServer;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.cli.CommandLine;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class implements the remote method of {@link RMIClientInterface} and represents the Client for the rmi connection.
 */
public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {
    /**
     * The relative Server.
     */
    private RMIServerInterface server;
    /**
     * A timer to handle the connection.
     */
    private ConnectionTimer connectionTimer;
    /**
     * A timer to handle the server connection.
     */
    private ServerConnectionTimer serverConnectionTimer;
    /**
     * An object that contains player's datas.
     */
    private PlayerController playerController;
    /**
     * The relative ui.
     */
    private ViewInterface view;
    /**
     * The username.
     */
    private String username;

    /**
     * Class constructor. Creates the connection. If the requested connection is not found, the while cycle will be repeted.
     * @throws RemoteException thrown by the remote method.
     */
    public RMIClient() throws RemoteException {
        super(Config.RMI_FREE_PORT);
        boolean cycle = true;
        while(cycle){
            try {
                playerController = new PlayerController(this);
                BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
                Printer.print(NetworkString.IP_MESSAGE);
                String host = userInputStream.readLine();
                Registry registry = LocateRegistry.getRegistry(host, Config.RMI_FREE_PORT);
                ConnectionInterface connection = (ConnectionInterface) registry.lookup(NetworkString.REGISTRY_NAME);
                server = connection.enrol(this);
                cycle = false;
            } catch (NotBoundException | IOException e) {
                cycle = true;
            }
        }
    }

    /**
     * Class constructor used by the gui.
     * @param host the requested host.
     * @throws RemoteException caused by the remote method.
     * @throws NotBoundException caused by the registry.
     */
    public RMIClient(String host) throws RemoteException, NotBoundException {
        super(Config.RMI_FREE_PORT);
        playerController = new PlayerController(this);
        Registry registry = LocateRegistry.getRegistry(host, Config.RMI_FREE_PORT);
        ConnectionInterface connection = (ConnectionInterface) registry.lookup(NetworkString.REGISTRY_NAME);
        server = connection.enrol(this);
    }

    /**
     * Sets the view for the user.
     * @param view the object that will bet set as view.
     */
    @Override
    public void setView(ViewInterface view){
        this.view = view;
    }

    /**
     * Getter the playerController.
     * @return the user's playerController.
     */
    @Override
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * Creates the ui and runs it.
     */
    @Override
    public void start() {
        view = new CommandLine(this);
        view.start();
        view.setPlayerController(playerController);
    }

    /**
     * Takes the username and the color of the user and then sends them to the server.
     * @param username the username of the player.
     * @param color the color chosen by the player.
     */
    @Override
    public void login(String username, TokenColor color){
        connectionTimer = new ConnectionTimer(this);
        this.username = username;
        try {
            server.login(username, color, connectionTimer);
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    /**
     * Disconnects the Client.
     */
    @Override
    public void disconnect(){
        try {
            server.disconnect();
        } catch (RemoteException e) {
            Printer.err(e);
        }
        System.exit(1);
    }

    /**
     * Takes the board and the number of skulls chosen by the first player for the game and sends them to the server.
     * @param boardType the chosen board.
     * @param skulls the number of skulls for the game.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void board(int boardType, int skulls) throws IOException {
        server.board(boardType, skulls);
    }

    /**
     * Takes the powerup chosen by the player during the first choice before the game.
     * @param choice the chosen powerup.
     */
    @Override
    public void choose(int choice){
        try {
            server.choose(choice);
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the message from the user and sends it to the Server.
     */
    @Override
    public void showSquare(){
        try {
            server.showSquare();
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the coordinates of the square that the user wants to see and sends them to te Server.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     */
    @Override
    public void showSquare(int x, int y){
        try {
            server.showSquare(x, y);
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the directions where the user wants to move and sends them to the Server.
     * @param directions the directions where the user wants to move.
     */
    @Override
    public void move(Direction... directions) throws RemoteException {
        server.move(directions);
    }

    /**
     * Takes the name of the powerup that the user will drop and sends it to the the Server.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void dropPowerup(int powerup) throws IOException {
        server.dropPowerup(powerup);
    }

    /**
     * Takes the name of the weapon that the user will drop and sends it to the Server.
     * @param weapon the chosen weapon.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void dropWeapon(int weapon) throws IOException {
        server.dropWeapon(weapon);
    }

    /**
     * Takes the name of the powerup that the user will discard to use it as ammos and sends it to the Server.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void discardPowerup(int powerup) throws IOException {
        server.discardPowerup(powerup);
    }

    /**
     * Takes the choice for the object that will be grabbed and directions where the player will be moved before the grab action.
     * @param choice the object that will be grabbed.
     * @param directions the directions where user will be moved.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void grab(int choice, Direction...directions) throws RemoteException {
        server.grab(choice, directions);
    }

    /**
     * Takes all the paramethers for the shoot action and sends hem to the Server.
     * @param weaponName the chosen weapon.
     * @param effectNumber the number of the effect.
     * @param basicFirst true if the user wants to use first the basic effect.
     * @param firstVictim the first victim.
     * @param secondVictim the second victim.
     * @param thirdVictim the third victim.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the directions chosen by the user.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction... directions) throws IOException {
        server.shoot(weaponName, effectNumber, basicFirst, firstVictim, secondVictim, thirdVictim, x, y, directions);
    }

    /**
     * Takes a direction and an array of weapon's names that represents the movement and the weapons that will be reload.
     * @param firstDirection the direction where the player wants to move.
     * @param weapons the weapons that the user wants to reload.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void moveAndReload(Direction firstDirection, String... weapons) throws IOException {
        server.moveAndReload(firstDirection, weapons);
    }

    /**
     * Takes two directions and an array of weapon's names that represents the movement and the weapons that will be reload.
     * @param firstDirection the first direction where the player wants to move.
     * @param secondDirection the second direction where the player wants to move.
     * @param weapons the weapons that the user wants to reload.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws IOException {
        server.moveAndReload(firstDirection, secondDirection, weapons);
    }

    /**
     * Takes the parameters to use powerups during the game.
     * @param powerup the chosen powerup.
     * @param victim the chosen victim.
     * @param ammo the color of the ammo.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the directions chosen by the user.
     * @throws IOException caused by the remote method.
     */
    @Override
    public void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction... directions) throws IOException {
        server.powerup(powerup, victim, ammo, x, y, directions);
    }

    /**
     * Takes the name of the weapon that will be reloaded and sends it to the Server.
     * @param weaponName the chosen weapon.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void reload(String weaponName) throws RemoteException{
        server.reload(weaponName);
    }

    /**
     * Takes the chosen powerup to discard to respawn.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the remote method..
     */
    @Override
    public void respawn(int powerup) throws IOException {
        server.respawn(powerup);
    }

    /**
     * Calls the {@link RMIServerInterface#endTurn()} method.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void endTurn() throws RemoteException{
        server.endTurn();
    }

    /**
     * Notify a message to the view.
     * @param message the message sent by the Server.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void notify(Message message) throws RemoteException{
        view.notify(message);
    }

    /**
     * Notify a message and an outcome to the view.
     * @param message the message sent by the Server.
     * @param outcome the outcome of the action.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void notify(Message message, Outcome outcome) throws RemoteException{
        view.notify(message, outcome);
    }

    /**
     * Notify a message, and outcome an object to the Server.
     * @param message the message sent by the Server.
     * @param outcome the outcome of the action.
     * @param gameData datas sent by the Server.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void notify(Message message, Outcome outcome, GameData gameData) throws RemoteException {
        switch (message){
            case PLAYER:
                if(outcome.equals(Outcome.RIGHT)) {
                    playerController.setPlayer(gameData.getPlayer());
                }
                break;
            case GAME:
                if(outcome.equals(Outcome.ALL)){
                    playerControllerSetter(gameData);
                }
                view.notify(message, outcome);
                break;
            case LOGIN:
                if(outcome.equals(Outcome.RIGHT)) {
                    connectionTimer.start();
                    serverConnectionTimer = new ServerConnectionTimer(this, server);
                    serverConnectionTimer.start();
                }
                view.notify(message, outcome, gameData.getUsername());
                break;
            case COLOR:
                view.notify(message, outcome, gameData.getColor());
                break;
            case NEW_TURN:
            case MOVE:
                playerControllerSetterWithCurrentPlayer(gameData);
                view.notify(message, outcome);
                break;
            case POWERUP:
                playerControllerSetterWithCurrentPlayer(gameData);
                playerController.setPowerup(gameData.getPowerup());
                if(gameData.getPowerup().equals(NetworkString.TARGETING_SCOPE) || gameData.getPowerup().equals(NetworkString.TAGBACK_GRENADE)){
                    playerController.setVictims(gameData.getVictims());
                }
                view.notify(message, outcome);
                break;
            case GRAB:
            case SHOOT:
                playerControllerSetterWithCurrentPlayerAndVictims(gameData);
                if(gameData.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;
            case RELOAD:
                playerControllerSetterWithCurrentPlayer(gameData);
                playerController.setWeapon(gameData.getWeapon());
                view.notify(message, outcome);
                break;
            case RECONNECTION:
                playerControllerSetterWithCurrentPlayer(gameData);
                view.notify(message, outcome, gameData.getUsername());
                break;
            case FINAL_FRENZY:
                playerControllerSetterWithCurrentPlayer(gameData);
                playerController.setWeapon(gameData.getWeapon());
                playerController.setFinalFrenzy(true);
                playerController.setPlayerBoardFinalFrenzy(gameData.getPlayer().getPlayerBoard().isFinalFrenzy());
                view.notify(message);
                break;
            case RESPAWN:
                playerController.setPlayer(gameData.getPlayer(username));
                view.notify(message, outcome);
                break;
            case DROP_POWERUP:
            case DROP_WEAPON:
            case DISCARD_POWERUP:
                playerController.setPlayer(gameData.getPlayer(username));
                playerController.setOtherPlayers(gameData.getPlayers(username));
                view.notify(message, outcome);
                break;
            case SPAWN:
                view.notify(message, outcome, gameData.getPowerups());
                break;
            case SQUARE:
                view.notify(message, outcome, gameData.getSquareData());
                break;
            case DISCONNECT:
                view.notify(message, outcome, gameData.getUsername());
                break;
            case SCORE:
            case END_GAME:
                view.notify(message, outcome, gameData.getScoreList());
                break;
            case END_TURN:
                playerControllerSetterWithCurrentPlayer(gameData);
                if(outcome.equals(Outcome.RIGHT)){
                    view.notify(message);
                }
                break;
            default:
                view.notify(message, outcome);
                break;
        }
    }

    /**
     * Tests the connection.
     */
    @Override
    public void testConnection(){
        //tests the rmi connection
    }

    /**
     * Sets parameters for the player.
     * @param gameData datas from the Server.
     */
    private void playerControllerSetter(GameData gameData){
        playerController.setGameBoard(gameData.getGameBoard());
        playerController.setKillshotTrack(gameData.getKillshotTrack());
        playerController.setPlayer(gameData.getPlayer(username));
        playerController.setOtherPlayers(gameData.getPlayers(username));
    }

    /**
     * Sets parameters for the player and also the current player.
     * @param gameData datas from the Server.
     */
    private void playerControllerSetterWithCurrentPlayer(GameData gameData){
        playerControllerSetter(gameData);
        playerController.setCurrentPlayer(gameData.getCurrentPlayer());
    }

    /**
     * Sets parameters for the player and also his victims.
     * @param gameData datas from the Server.
     */
    private void playerControllerSetterWithCurrentPlayerAndVictims(GameData gameData){
        playerControllerSetterWithCurrentPlayer(gameData);
        playerController.setVictims(gameData.getVictims());
    }
}
