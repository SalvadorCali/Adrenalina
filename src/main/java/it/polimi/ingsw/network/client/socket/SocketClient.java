package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.controller.GameData;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.SquareData;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.CommandLine;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.MapCLI;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class SocketClient implements ClientInterface, Runnable, Serializable {
    private PlayerController playerController;
    private Thread thisThread;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ConnectionTimer connectionTimer;
    private ViewInterface view;
    private Socket clientSocket;
    private String username;

    public SocketClient() throws IOException {
        playerController = new PlayerController(this);
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        Printer.print("[CLIENT]Please, set an ip address:");
        String host = userInputStream.readLine();
        clientSocket = new Socket(host, Config.SOCKET_PORT);

        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        view = new CommandLine(this);
        view.start();
        view.setPlayerController(playerController);
    }

    public SocketClient(String host) throws IOException {
        playerController = new PlayerController(this);
        clientSocket = new Socket(host, Config.SOCKET_PORT);

        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        thisThread = new Thread(this);
        thisThread.start();
    }

    @Override
    public void setView(ViewInterface view){
        this.view = view;
    }

    @Override
    public PlayerController getPlayerController() {
        return playerController;
    }

    public void start() {
        thisThread = new Thread(this);
        thisThread.start();
    }

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
                    Printer.println("Server disconnesso");
                    Printer.println("Eskereee");
                } catch (IOException e1) {
                    Printer.err(e1);
                }
            }
        }
    }

    public void readRequest(Message message){
        switch(message){
            case NOTIFY:
                try {
                    notifyMessage();
                } catch (IOException | ClassNotFoundException e) {
                    Printer.err(e);
                }
                break;
            default:
                break;
        }
    }

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

    @Override
    public void board(int boardType, int skulls) throws IOException {
        objectOutputStream.writeObject(Message.BOARD);
        objectOutputStream.flush();
        objectOutputStream.writeInt(boardType);
        objectOutputStream.flush();
        objectOutputStream.writeInt(skulls);
        objectOutputStream.flush();
    }

    @Override
    public void choose(int choice) throws IOException {
        objectOutputStream.writeObject(Message.SPAWN);
        objectOutputStream.flush();
        objectOutputStream.writeInt(choice);
        objectOutputStream.flush();
    }

    @Override
    public void showSquare() throws IOException{
        objectOutputStream.writeObject(Message.SQUARE);
        objectOutputStream.flush();
    }

    @Override
    public void showSquare(int x, int y) throws IOException{
        objectOutputStream.writeObject(Message.SQUARE_2);
        objectOutputStream.flush();
        objectOutputStream.writeInt(x);
        objectOutputStream.flush();
        objectOutputStream.writeInt(y);
        objectOutputStream.flush();
    }

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

    @Override
    public void grab(int choice, Direction...directions) throws IOException {
        int directionsSize = directions.length;
        objectOutputStream.writeObject(Message.GRAB);
        objectOutputStream.flush();
        objectOutputStream.writeInt(choice);
        objectOutputStream.flush();
        objectOutputStream.writeInt(directionsSize);
        objectOutputStream.flush();
        Printer.println(directionsSize);
        for (Direction direction : directions) {
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    @Override
    public void drop(String weaponName) throws IOException {
        objectOutputStream.writeObject(Message.DROP);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(weaponName);
        objectOutputStream.flush();
    }

    @Override
    public void dropPowerup(int powerup) throws IOException {
        objectOutputStream.writeObject(Message.DROP_POWERUP);
        objectOutputStream.flush();
        objectOutputStream.writeInt(powerup);
        objectOutputStream.flush();
    }

    @Override
    public void dropWeapon(int weapon) throws IOException {
        objectOutputStream.writeObject(Message.DROP_WEAPON);
        objectOutputStream.flush();
        objectOutputStream.writeInt(weapon);
        objectOutputStream.flush();
    }

    @Override
    public void discardPowerup(int powerup) throws IOException {
        objectOutputStream.writeObject(Message.DISCARD_POWERUP);
        objectOutputStream.flush();
        objectOutputStream.writeInt(powerup);
        objectOutputStream.flush();
    }

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

    @Override
    public void moveAndReload(Direction firstDirection, String... weapons) throws IOException {
        objectOutputStream.writeObject(Message.MOVE_RELOAD_1);
        objectOutputStream.flush();
        objectOutputStream.writeObject(firstDirection);
        objectOutputStream.flush();
        objectOutputStream.writeInt(weapons.length);
        objectOutputStream.flush();
        for(String weapon : weapons){
            objectOutputStream.writeObject(weapon);
            objectOutputStream.flush();
        }
    }

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
            objectOutputStream.writeObject(weapon);
            objectOutputStream.flush();
        }
    }

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
        switch(directions.length){
            case 1:
                objectOutputStream.writeObject(directions[0]);
                objectOutputStream.flush();
                break;
            case 2:
                objectOutputStream.writeObject(directions[0]);
                objectOutputStream.flush();
                objectOutputStream.writeObject(directions[1]);
                objectOutputStream.flush();
                break;
            default:
                break;
        }
    }

    @Override
    public void powerupAmmos(PowerupData...powerups) throws IOException{
        objectOutputStream.writeObject(Message.POWERUP_AMMOS);
        objectOutputStream.flush();
        objectOutputStream.writeInt(powerups.length);
        objectOutputStream.flush();
        for(PowerupData powerup : powerups){
            objectOutputStream.writeObject(powerup);
            objectOutputStream.flush();
        }
    }

    @Override
    public void powerupAmmos(int... powerups) throws IOException {
        objectOutputStream.writeObject(Message.POWERUP_AMMOS);
        objectOutputStream.flush();
        objectOutputStream.writeInt(powerups.length);
        objectOutputStream.flush();
        for(int powerup : powerups){
            objectOutputStream.writeInt(powerup);
            objectOutputStream.flush();
        }
    }

    @Override
    public void reload(String weaponName) throws IOException {
        objectOutputStream.writeObject(Message.RELOAD);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(weaponName);
        objectOutputStream.flush();
    }

    @Override
    public void endTurn() throws IOException {
        objectOutputStream.writeObject(Message.END_TURN);
        objectOutputStream.flush();
    }

    public void notifyMessage() throws IOException, ClassNotFoundException {
        Message message = (Message) objectInputStream.readObject();
        Outcome outcome;
        Object object;
        switch(message){
            case NEW_TURN:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();
                GameData gameData = (GameData) object;
                playerController.setGameBoard(gameData.getGameBoard());
                playerController.setKillshotTrack(gameData.getKillshotTrack());
                playerController.setPlayer(gameData.getPlayer(username));
                playerController.setOtherPlayers(gameData.getPlayers(username));
                view.notify(message, outcome);
                break;
            case END_TURN:
            case NOT_TURN:
                view.notify(message);
                break;
            case GAME:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();
                GameData gameData1 = (GameData) object;
                playerController.setGameBoard(gameData1.getGameBoard());
                playerController.setKillshotTrack(gameData1.getKillshotTrack());
                playerController.setPlayer(gameData1.getPlayer(username));
                playerController.setOtherPlayers(gameData1.getPlayers(username));
                view.notify(message, outcome);
                break;
            case LOGIN:
                outcome = (Outcome) objectInputStream.readObject();
                if(outcome.equals(Outcome.RIGHT)){
                    connectionTimer.start();
                }
                object = (String) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case COLOR:
                outcome = (Outcome) objectInputStream.readObject();
                object = (TokenColor) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case PLAYER:
                outcome = (Outcome) objectInputStream.readObject();
                object = objectInputStream.readObject();
                playerController.setPlayer((Player) object);
                break;
            case DISCONNECT:
                outcome = (Outcome) objectInputStream.readObject();
                object = (String) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case SPAWN:
                outcome = (Outcome) objectInputStream.readObject();
                object = (List<Card>) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case MOVE:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();
                GameData gameData2 = (GameData) object;
                playerController.setGameBoard(gameData2.getGameBoard());
                playerController.setKillshotTrack(gameData2.getKillshotTrack());
                playerController.setPlayer(gameData2.getPlayer(username));
                playerController.setOtherPlayers(gameData2.getPlayers(username));
                view.notify(message, outcome);
                break;
            case POWERUP:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();
                GameData gameData3 = (GameData) object;
                playerController.setGameBoard(gameData3.getGameBoard());
                playerController.setKillshotTrack(gameData3.getKillshotTrack());
                playerController.setPlayer(gameData3.getPlayer(username));
                playerController.setOtherPlayers(gameData3.getPlayers(username));
                playerController.setPowerup(gameData3.getPowerup());
                if(gameData3.getPowerup().equals("targetingscope") || gameData3.getPowerup().equals("tagbackgrenade")){
                    playerController.setVictims(gameData3.getPlayers(username));
                }
                view.notify(message, outcome);
                break;
            case GRAB:
                outcome = (Outcome) objectInputStream.readObject();
                GameData gameData4 = (GameData) objectInputStream.readObject();
                playerController.setGameBoard(gameData4.getGameBoard());
                playerController.setKillshotTrack(gameData4.getKillshotTrack());
                playerController.setPlayer(gameData4.getPlayer(username));
                playerController.setVictims(gameData4.getPlayers(username));
                playerController.setOtherPlayers(gameData4.getPlayers(username));
                playerController.setCurrentPlayer(gameData4.getCurrentPlayer());
                if(gameData4.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;
            case SQUARE:
                outcome = (Outcome) objectInputStream.readObject();
                object = (SquareData) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case SHOOT:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();

                GameData gameData5 = (GameData) object;
                playerController.setGameBoard(gameData5.getGameBoard());
                playerController.setKillshotTrack(gameData5.getKillshotTrack());
                playerController.setPlayer(gameData5.getPlayer(username));
                playerController.setVictims(gameData5.getVictims());
                playerController.setOtherPlayers(gameData5.getPlayers(username));
                if(gameData5.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;

            case BOARD:
                outcome = (Outcome) objectInputStream.readObject();
                view.notify(message, outcome);
                break;
            case SCORE:
                outcome = (Outcome) objectInputStream.readObject();
                object = (Map<TokenColor, Integer>) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case RELOAD:
                outcome = (Outcome) objectInputStream.readObject();
                object = (String) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case RECONNECTION:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();
                GameData gameData6 = (GameData) object;
                playerController.setGameBoard(gameData6.getGameBoard());
                playerController.setKillshotTrack(gameData6.getKillshotTrack());
                playerController.setPlayer(gameData6.getPlayer(username));
                playerController.setOtherPlayers(gameData6.getPlayers(username));
                view.notify(message, outcome);
                break;
            case DROP_POWERUP:
            case DROP_WEAPON:
            case DISCARD_POWERUP:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();
                GameData gameData8 = (GameData) object;
                playerController.setPlayer(gameData8.getPlayer(username));
                playerController.setOtherPlayers(gameData8.getPlayers(username));
                view.notify(message, outcome);
                break;
            case FINAL_FRENZY:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameData) objectInputStream.readObject();
                GameData gameData7 = (GameData) object;
                playerController.setFinalFrenzy(true);
                view.notify(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void testConnection(){}

    @Override
    public AdrenalineZone getAdrenalineZone(){
        return playerController.getAdrenalineZone();
    }
}
