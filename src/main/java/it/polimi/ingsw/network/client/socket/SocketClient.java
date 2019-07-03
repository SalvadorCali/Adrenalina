package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.controller.datas.GameData;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.NetworkString;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.cli.CommandLine;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.Socket;

/**
 * This class represents the Client for the socket connection.
 */
public class SocketClient implements ClientInterface, Runnable, Serializable {
    /**
     * An object that contains player's datas.
     */
    private PlayerController playerController;
    /**
     * A Thread object that represents this thread.
     */
    private Thread thisThread;
    /**
     * The input stream.
     */
    private ObjectInputStream objectInputStream;
    /**
     * The output stream.
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * A timer to handle the connection.
     */
    private ConnectionTimer connectionTimer;
    /**
     * The relative ui.
     */
    private ViewInterface view;
    /**
     * The socket to talk to the server.
     */
    private Socket clientSocket;
    /**
     * The username.
     */
    private String username;

    /**
     * Class constructor. Creates the connection and open the ui. If the requested connection is not found, the while cycle will be repeted.
     */
    public SocketClient(){
        playerController = new PlayerController(this);
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        boolean cycle = true;
        while(cycle){
            try {
                Printer.print(NetworkString.IP_MESSAGE);
                String host = userInputStream.readLine();
                clientSocket = new Socket(host, Config.SOCKET_PORT);
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                view = new CommandLine(this);
                view.start();
                view.setPlayerController(playerController);
                cycle = false;
            } catch (IOException e) {
                cycle = true;
            }
        }
    }

    /**
     * Class constructor used by the gui.
     * @param host the requested host.
     * @throws IOException caused by the streams.
     */
    public SocketClient(String host) throws IOException {
        playerController = new PlayerController(this);
        clientSocket = new Socket(host, Config.SOCKET_PORT);

        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        thisThread = new Thread(this);
        thisThread.start();
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
     * Creates a thread and runs it.
     */
    public void start() {
        thisThread = new Thread(this);
        thisThread.start();
    }

    /**
     * Reads message from the server and calls the {@link #readRequest(Message)} method to handle them.
     */
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (this.thisThread == currentThread){
            try {
                Message message = (Message) objectInputStream.readObject();
                readRequest(message);
            } catch (IOException | ClassNotFoundException e) {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    clientSocket.close();
                    thisThread = null;
                    System.exit(1);
                } catch (IOException e1) {
                    Printer.err(e1);
                }
            }
        }
    }

    /**
     * Receives a message and if it's a NOTIFY message, calls the {@link #notifyMessage()} method.
     * @param message the message received by the server.
     */
    private void readRequest(Message message){
        if (message == Message.NOTIFY) {
            try {
                notifyMessage();
            } catch (IOException | ClassNotFoundException e) {
                Printer.err(e);
            }
        }
    }

    /**
     * Takes the username and the color of the user and then sends them to the server.
     * @param username the username of the player.
     * @param color the color chosen by the player.
     */
    @Override
    public void login(String username, TokenColor color){
        try {
            this.username = username;
            connectionTimer = new ConnectionTimer(this);
            objectOutputStream.writeObject(Message.LOGIN);
            objectOutputStream.flush();
            objectOutputStream.writeUTF(username);
            objectOutputStream.flush();
            objectOutputStream.writeObject(color);
            objectOutputStream.flush();
            //objectOutputStream.writeObject(connectionTimer);
            //objectOutputStream.flush();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Closes the streams and disconnect the Client.
     */
    @Override
    public void disconnect(){
        try {
            objectOutputStream.writeObject(Message.DISCONNECT);
            objectOutputStream.flush();
            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the board and the number of skulls chosen by the first player for the game and sends them to the server.
     * @param boardType the chosen board.
     * @param skulls the number of skulls for the game.
     * @throws IOException caused by the streams.
     */
    @Override
    public void board(int boardType, int skulls) throws IOException {
        objectOutputStream.writeObject(Message.BOARD);
        objectOutputStream.flush();
        objectOutputStream.writeInt(boardType);
        objectOutputStream.flush();
        objectOutputStream.writeInt(skulls);
        objectOutputStream.flush();
    }

    /**
     * Takes the powerup chosen by the player during the first choice before the game.
     * @param choice the chosen powerup.
     * @throws IOException caused by the streams.
     */
    @Override
    public void choose(int choice) throws IOException {
        objectOutputStream.writeObject(Message.SPAWN);
        objectOutputStream.flush();
        objectOutputStream.writeInt(choice);
        objectOutputStream.flush();
    }

    /**
     * Takes the message from the user and sends it to the Server.
     * @throws IOException caused by the streams.
     */
    @Override
    public void showSquare() throws IOException{
        objectOutputStream.writeObject(Message.SQUARE);
        objectOutputStream.flush();
    }

    /**
     * Takes the coordinates of the square that the user wants to see and sends them to te Server.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @throws IOException caused by the streams.
     */
    @Override
    public void showSquare(int x, int y) throws IOException{
        objectOutputStream.writeObject(Message.SQUARE_XY);
        objectOutputStream.flush();
        objectOutputStream.writeInt(x);
        objectOutputStream.flush();
        objectOutputStream.writeInt(y);
        objectOutputStream.flush();
    }

    /**
     * Takes the directions where the user wants to move and sends them to the Server.
     * @param directions the directions where the user wants to move.
     * @throws IOException caused by the streams.
     */
    @Override
    public void move(Direction... directions) throws IOException {
        objectOutputStream.writeObject(Message.MOVE);
        objectOutputStream.flush();
        int directionsSize = directions.length;
        objectOutputStream.writeInt(directionsSize);
        objectOutputStream.flush();
        for (Direction direction : directions) {
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    /**
     * Takes the choice for the object that will be grabbed and directions where the player will be moved before the grab action.
     * @param choice the object that will be grabbed.
     * @param directions the directions where user will be moved.
     * @throws IOException caused by the streams.
     */
    @Override
    public void grab(int choice, Direction...directions) throws IOException {
        int directionsSize = directions.length;
        objectOutputStream.writeObject(Message.GRAB);
        objectOutputStream.flush();
        objectOutputStream.writeInt(choice);
        objectOutputStream.flush();
        objectOutputStream.writeInt(directionsSize);
        objectOutputStream.flush();
        for (Direction direction : directions) {
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    /**
     * Takes the name of the powerup that the user will drop and sends it to the the Server.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the streams.
     */
    @Override
    public void dropPowerup(int powerup) throws IOException {
        objectOutputStream.writeObject(Message.DROP_POWERUP);
        objectOutputStream.flush();
        objectOutputStream.writeInt(powerup);
        objectOutputStream.flush();
    }

    /**
     * Takes the name of the weapon that the user will drop and sends it to the Server.
     * @param weapon the chosen weapon.
     * @throws IOException caused by the streams.
     */
    @Override
    public void dropWeapon(int weapon) throws IOException {
        objectOutputStream.writeObject(Message.DROP_WEAPON);
        objectOutputStream.flush();
        objectOutputStream.writeInt(weapon);
        objectOutputStream.flush();
    }

    /**
     * Takes the name of the powerup that the user will discard to use it as ammos and sends it to the Server.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the streams.
     */
    @Override
    public void discardPowerup(int powerup) throws IOException {
        objectOutputStream.writeObject(Message.DISCARD_POWERUP);
        objectOutputStream.flush();
        objectOutputStream.writeInt(powerup);
        objectOutputStream.flush();
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
     * @throws IOException caused by the streams.
     */
    @Override
    public void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction... directions) throws IOException {
        objectOutputStream.writeObject(Message.SHOOT);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(weaponName);
        objectOutputStream.flush();
        objectOutputStream.writeInt(effectNumber);
        objectOutputStream.flush();
        objectOutputStream.writeBoolean(basicFirst);
        objectOutputStream.flush();
        objectOutputStream.writeObject(firstVictim);
        objectOutputStream.flush();
        objectOutputStream.writeObject(secondVictim);
        objectOutputStream.flush();
        objectOutputStream.writeObject(thirdVictim);
        objectOutputStream.flush();
        objectOutputStream.writeInt(x);
        objectOutputStream.flush();
        objectOutputStream.writeInt(y);
        objectOutputStream.flush();
        objectOutputStream.writeInt(directions.length);
        objectOutputStream.flush();
        for(Direction direction : directions){
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    /**
     * Takes a direction and an array of weapon's names that represents the movement and the weapons that will be reload.
     * @param firstDirection the direction where the player wants to move.
     * @param weapons the weapons that the user wants to reload.
     * @throws IOException caused by the streams.
     */
    @Override
    public void moveAndReload(Direction firstDirection, String... weapons) throws IOException {
        objectOutputStream.writeObject(Message.MOVE_RELOAD_1);
        objectOutputStream.flush();
        objectOutputStream.writeObject(firstDirection);
        Printer.println(firstDirection);
        objectOutputStream.flush();
        objectOutputStream.writeInt(weapons.length);
        Printer.println(weapons.length);
        objectOutputStream.flush();
        for(String weapon : weapons){
            Printer.println(weapon);
            objectOutputStream.writeUTF(weapon);
            objectOutputStream.flush();
        }
    }

    /**
     * Takes two directions and an array of weapon's names that represents the movement and the weapons that will be reload.
     * @param firstDirection the first direction where the player wants to move.
     * @param secondDirection the second direction where the player wants to move.
     * @param weapons the weapons that the user wants to reload.
     * @throws IOException caused by the streams.
     */
    @Override
    public void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws IOException {
        objectOutputStream.writeObject(Message.MOVE_RELOAD_2);
        objectOutputStream.flush();
        objectOutputStream.writeObject(firstDirection);
        objectOutputStream.flush();
        objectOutputStream.writeObject(secondDirection);
        objectOutputStream.flush();
        objectOutputStream.writeInt(weapons.length);
        objectOutputStream.flush();
        for(String weapon : weapons){
            objectOutputStream.writeUTF(weapon);
            objectOutputStream.flush();
        }
    }

    /**
     * Takes the parameters to use powerups during the game.
     * @param powerup the chosen powerup.
     * @param victim the chosen victim.
     * @param ammo the color of the ammo.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the directions chosen by the user.
     * @throws IOException caused by the streams.
     */
    @Override
    public void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction... directions) throws IOException {
        objectOutputStream.writeObject(Message.POWERUP);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(powerup);
        objectOutputStream.flush();
        objectOutputStream.writeObject(victim);
        objectOutputStream.flush();
        objectOutputStream.writeObject(ammo);
        objectOutputStream.flush();
        objectOutputStream.writeInt(x);
        objectOutputStream.flush();
        objectOutputStream.writeInt(y);
        objectOutputStream.flush();
        objectOutputStream.writeInt(directions.length);
        objectOutputStream.flush();
        for(Direction direction : directions){
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    /**
     * Takes the name of the weapon that will be reloaded and sends it to the Server.
     * @param weaponName the chosen weapon.
     * @throws IOException caused by the streams.
     */
    @Override
    public void reload(String weaponName) throws IOException {
        objectOutputStream.writeObject(Message.RELOAD);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(weaponName);
        objectOutputStream.flush();
    }

    /**
     * Takes the chosen powerup to discard to respawn.
     * @param powerup the chosen powerup.
     * @throws IOException caused by the streams.
     */
    @Override
    public void respawn(int powerup) throws IOException {
        objectOutputStream.writeObject(Message.RESPAWN);
        objectOutputStream.flush();
        objectOutputStream.writeInt(powerup);
        objectOutputStream.flush();
    }

    /**
     * Sends the END_TURN message to the Server.
     * @throws IOException caused by the streams.
     */
    @Override
    public void endTurn() throws IOException {
        objectOutputStream.writeObject(Message.END_TURN);
        objectOutputStream.flush();
    }

    /**
     * Reads the message and sends to the view the parameters to notify the action.
     * @throws IOException caused by the streams.
     * @throws ClassNotFoundException thrown if the class is not found.
     */
    private void notifyMessage() throws IOException, ClassNotFoundException {
        Message message = (Message) objectInputStream.readObject();
        Outcome outcome;
        GameData gameData;
        switch(message){
            case END_TURN:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerControllerSetterWithCurrentPlayer(gameData);
                if(outcome.equals(Outcome.RIGHT)){
                    view.notify(message);
                }
                break;
            case NOT_TURN:
                view.notify(message);
                break;
            case GAME:
                outcome = (Outcome) objectInputStream.readObject();
                if(outcome.equals(Outcome.ALL)){
                    gameData = (GameData) objectInputStream.readObject();
                    playerControllerSetter(gameData);
                }
                view.notify(message, outcome);
                break;
            case LOGIN:
                outcome = (Outcome) objectInputStream.readObject();
                if(outcome.equals(Outcome.RIGHT)){
                    connectionTimer.start();
                }
                gameData = (GameData) objectInputStream.readObject();
                view.notify(message, outcome, gameData.getUsername());
                break;
            case COLOR:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                view.notify(message, outcome, gameData.getColor());
                break;
            case PLAYER:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerController.setPlayer(gameData.getPlayer());
                break;
            case DISCONNECT:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                view.notify(message, outcome, gameData.getUsername());
                break;
            case SPAWN:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                view.notify(message, outcome, gameData.getPowerups());
                break;
            case NEW_TURN:
            case MOVE:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerControllerSetterWithCurrentPlayer(gameData);
                view.notify(message, outcome);
                break;
            case POWERUP:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerControllerSetterWithCurrentPlayer(gameData);
                playerController.setPowerup(gameData.getPowerup());
                if(gameData.getPowerup().equals(NetworkString.TARGETING_SCOPE) || gameData.getPowerup().equals(NetworkString.TAGBACK_GRENADE)){
                    playerController.setVictims(gameData.getVictims());
                }
                view.notify(message, outcome);
                break;
            case SQUARE:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                view.notify(message, outcome, gameData.getSquareData());
                break;
            case GRAB:
            case SHOOT:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerControllerSetterWithCurrentPlayerAndVictims(gameData);
                if(gameData.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;
            case BOARD:
                outcome = (Outcome) objectInputStream.readObject();
                view.notify(message, outcome);
                break;
            case SCORE:
            case END_GAME:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                view.notify(message, outcome, gameData.getScoreList());
                break;
            case RELOAD:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerControllerSetterWithCurrentPlayer(gameData);
                playerController.setWeapon(gameData.getWeapon());
                view.notify(message, outcome);
                break;
            case RECONNECTION:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerControllerSetter(gameData);
                view.notify(message, outcome, gameData.getUsername());
                break;
            case DROP_POWERUP:
            case DROP_WEAPON:
            case DISCARD_POWERUP:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerController.setPlayer(gameData.getPlayer(username));
                playerController.setOtherPlayers(gameData.getPlayers(username));
                view.notify(message, outcome);
                break;
            case RESPAWN:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerController.setPlayer(gameData.getPlayer(username));
                view.notify(message, outcome);
                break;
            case FINAL_FRENZY:
                outcome = (Outcome) objectInputStream.readObject();
                gameData = (GameData) objectInputStream.readObject();
                playerControllerSetterWithCurrentPlayer(gameData);
                playerController.setWeapon(gameData.getWeapon());
                playerController.setFinalFrenzy(true);
                playerController.setPlayerBoardFinalFrenzy(gameData.getPlayer().getPlayerBoard().isFinalFrenzy());
                view.notify(message);
                break;
            default:
                break;
        }
    }

    /**
     * Tests the connection.
     */
    @Override
    public void testConnection(){
        //tests the connection
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
