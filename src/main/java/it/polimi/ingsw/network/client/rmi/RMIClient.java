package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.controller.GameData;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.CommandLine;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.MapCLI;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {
    private RMIServerInterface server;
    private ConnectionTimer connectionTimer;
    private PlayerController playerController;
    private ViewInterface view;
    private String username;

    public RMIClient() throws IOException, NotBoundException {
        super(Config.RMI_FREE_PORT);
        playerController = new PlayerController(this);
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        Printer.print("[CLIENT]Please, set an ip address:");
        String host = userInputStream.readLine();
        //System.setProperty("java.rmi.client.hostname", InetAddress.getLocalHost().getHostAddress());
        //old
        /*
        ConnectionInterface connectionInterface = (ConnectionInterface) java.rmi.Naming.lookup("server");
        server = connectionInterface.enrol(this);
        */
        //new
        Registry registry = LocateRegistry.getRegistry(host, Config.RMI_FREE_PORT);
        ConnectionInterface connection = (ConnectionInterface) registry.lookup("server");
        server = connection.enrol(this);
        //server = connection.enrol((RMIClientInterface) UnicastRemoteObject.exportObject(this, Config.RMI_FREE_PORT));
    }

    public RMIClient(String host) throws RemoteException, NotBoundException {
        super(Config.RMI_FREE_PORT);
        playerController = new PlayerController(this);
        Registry registry = LocateRegistry.getRegistry(host, Config.RMI_FREE_PORT);
        ConnectionInterface connection = (ConnectionInterface) registry.lookup("server");
        server = connection.enrol(this);
    }

    @Override
    public void setView(ViewInterface view){
        this.view = view;
    }

    @Override
    public PlayerController getPlayerController() {
        return playerController;
    }

    @Override
    public void start() {
        view = new CommandLine(this);
        view.start();
        view.setPlayerController(playerController);
    }

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

    @Override
    public void disconnect(){
        try {
            server.disconnect();
        } catch (RemoteException e) {
            Printer.err(e);
        }
        System.exit(1);
    }

    @Override
    public void board(int boardType, int skulls) throws IOException {
        server.board(boardType, skulls);
    }

    @Override
    public void choose(int choice){
        try {
            server.choose(choice);
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    @Override
    public void showSquare(){
        try {
            server.showSquare();
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    @Override
    public void showSquare(int x, int y){
        try {
            server.showSquare(x, y);
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    @Override
    public void move(Direction... directions) throws RemoteException {
        server.move(directions);
    }

    @Override
    public void drop(String weaponName) throws RemoteException {
        server.drop(weaponName);
    }

    @Override
    public void grab(int choice, Direction...directions) throws RemoteException {
        server.grab(choice, directions);
    }

    @Override
    public void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction... directions) throws IOException {
        server.shoot(weaponName, effectNumber, basicFirst, firstVictim, secondVictim, thirdVictim, x, y, directions);
    }

    @Override
    public void shoot(String weaponName, int effectNumber, TokenColor...colors) throws RemoteException {
        server.shoot(weaponName, effectNumber, colors);
    }

    @Override
    public void shoot(String weaponName, int effectNumber, TokenColor color, int x, int y) throws IOException {
        server.shoot(weaponName, effectNumber, color, x, y);
    }

    @Override
    public void shoot(String weaponName, TokenColor color, int effectNumber, Direction... directions) throws IOException {
        server.shoot(weaponName, color, effectNumber, directions);
    }

    @Override
    public void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws IOException {
        server.moveAndReload(firstDirection, secondDirection, weapons);
    }

    @Override
    public void powerup(String powerup, int x, int y) throws RemoteException{
        server.powerup(powerup, x, y);
    }

    @Override
    public void powerup(String powerup, Direction direction, int value) throws RemoteException{
        server.powerup(powerup, direction, value);
    }

    @Override
    public void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction... directions) throws IOException {
        server.powerup(powerup, victim, ammo, x, y, directions);
    }

    @Override
    public void powerupAmmos(PowerupData...powerups) throws RemoteException{
        server.powerupAmmos(powerups);
    }

    @Override
    public void reload(String weaponName) throws RemoteException{
        server.reload(weaponName);
    }

    @Override
    public void endTurn() throws RemoteException{
        server.endTurn();
    }

    @Override
    public void notify(Message message) throws RemoteException{
        switch (message){
            case BOARD:
                view.notify(message);
                break;
            case END_TURN:
                view.notify(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void notify(Message message, Outcome outcome) throws RemoteException{
        view.notify(message, outcome);
    }

    @Override
    public void notify(Message message, Outcome outcome, Object object) throws RemoteException {
        switch (message){
            case PLAYER:
                if(outcome.equals(Outcome.RIGHT)) {
                    Player player = (Player) object;
                    playerController.setPlayer(player);
                }
                break;
            case GAME:
                if(outcome.equals(Outcome.ALL)){
                    GameData gameData = (GameData) object;
                    //GameBoard gameBoard = (GameBoard) object;
                    playerController.setGameBoard(gameData.getGameBoard());
                    playerController.setKillshotTrack(gameData.getKillshotTrack());
                    playerController.setPlayer(gameData.getPlayer(username));
                }
                view.notify(message, outcome);
                break;
            case LOGIN:
                if(outcome.equals(Outcome.RIGHT)) {
                    connectionTimer.start();
                }
                view.notify(message, outcome, object);
                break;
            case NEW_TURN:
                //if(outcome.equals(Outcome.RIGHT)){
                    GameData gameData7 = (GameData) object;
                    //GameBoard gameBoard = (GameBoard) object;
                    playerController.setGameBoard(gameData7.getGameBoard());
                    playerController.setKillshotTrack(gameData7.getKillshotTrack());
                    playerController.setPlayer(gameData7.getPlayer(username));
                //}
                view.notify(message, outcome);
            case MOVE:
                if(outcome.equals(Outcome.RIGHT)){
                    playerController.incrementMoves();
                }
                GameData gameData = (GameData) object;
                //GameBoard gameBoard = (GameBoard) object;
                playerController.setGameBoard(gameData.getGameBoard());
                playerController.setKillshotTrack(gameData.getKillshotTrack());
                playerController.setPlayer(gameData.getPlayer(username));
                view.notify(message, outcome);
                break;
            case POWERUP:
                GameData gameData2 = (GameData) object;
                playerController.setGameBoard(gameData2.getGameBoard());
                playerController.setKillshotTrack(gameData2.getKillshotTrack());
                playerController.setPlayer(gameData2.getPlayer(username));
                playerController.setPowerup(gameData2.getPowerup());
                if(gameData2.getPowerup().equals("targetingscope") || gameData2.getPowerup().equals("tagbackgrenade")){
                    playerController.setVictims(gameData2.getPlayers(username));
                }
                view.notify(message, outcome);
                break;
            case GRAB:
                if(outcome.equals(Outcome.RIGHT)){
                    playerController.incrementMoves();
                }
                GameData gameData4 = (GameData) object;
                playerController.setGameBoard(gameData4.getGameBoard());
                playerController.setKillshotTrack(gameData4.getKillshotTrack());
                playerController.setPlayer(gameData4.getPlayer(username));
                playerController.setVictims(gameData4.getPlayers(username));
                playerController.setCurrentPlayer(gameData4.getCurrentPlayer());
                if(gameData4.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;
            case SHOOT:
                if(outcome.equals(Outcome.RIGHT)){
                    playerController.incrementMoves();
                }
                GameData gameData3 = (GameData) object;
                //GameBoard gameBoard = (GameBoard) object;
                playerController.setGameBoard(gameData3.getGameBoard());
                playerController.setKillshotTrack(gameData3.getKillshotTrack());
                playerController.setPlayer(gameData3.getPlayer(username));
                playerController.setVictims(gameData3.getVictims());
                if(gameData3.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;
            case RELOAD:
                view.notify(message, outcome, object);
                break;
            case RECONNECTION:
                GameData gameData5 = (GameData) object;
                playerController.setGameBoard(gameData5.getGameBoard());
                playerController.setKillshotTrack(gameData5.getKillshotTrack());
                playerController.setPlayer(gameData5.getPlayer(username));
                view.notify(message, outcome);
                break;
            case FINAL_FRENZY:
                GameData gameData6 = (GameData) object;
                playerController.setFinalFrenzy(true);
                view.notify(message);
                break;
            default:
                view.notify(message, outcome, object);
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
