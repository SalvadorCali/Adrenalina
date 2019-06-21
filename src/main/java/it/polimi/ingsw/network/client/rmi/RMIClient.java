package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.controller.GameData;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.enums.Message;
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

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {
    private RMIServerInterface server;
    private ConnectionTimer connectionTimer;
    private PlayerController playerController;
    private ViewInterface view;
    private String username;

    public RMIClient() throws RemoteException {
        super(Config.RMI_FREE_PORT);
        boolean cycle = true;
        while(cycle){
            try {
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
                cycle = false;
            } catch (NotBoundException | IOException e) {
                cycle = true;
            }

        }

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
    public void dropPowerup(int powerup) throws IOException {
        server.dropPowerup(powerup);
    }

    @Override
    public void dropWeapon(int weapon) throws IOException {
        server.dropWeapon(weapon);
    }

    @Override
    public void discardPowerup(int powerup) throws IOException {
        server.discardPowerup(powerup);
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
    public void moveAndReload(Direction firstDirection, String... weapons) throws IOException {
        server.moveAndReload(firstDirection, weapons);
    }

    @Override
    public void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws IOException {
        server.moveAndReload(firstDirection, secondDirection, weapons);
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
    public void powerupAmmos(int... powerups) throws IOException {
        server.powerupAmmos(powerups);
    }

    @Override
    public void reload(String weaponName) throws RemoteException{
        server.reload(weaponName);
    }

    @Override
    public void respawn(int powerup) throws IOException {
        server.respawn(powerup);
    }

    @Override
    public void endTurn() throws RemoteException{
        server.endTurn();
    }

    @Override
    public void notify(Message message) throws RemoteException{
        view.notify(message);
        /*
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

         */
    }

    @Override
    public void notify(Message message, Outcome outcome) throws RemoteException{
        view.notify(message, outcome);
    }

    @Override
    public void notify(Message message, Outcome outcome, GameData gameData) throws RemoteException {
        GameData gameDatas = gameData;
        switch (message){
            case PLAYER:
                if(outcome.equals(Outcome.RIGHT)) {
                    playerController.setPlayer(gameDatas.getPlayer());
                }
                break;
            case GAME:
                if(outcome.equals(Outcome.ALL)){
                    playerController.setGameBoard(gameDatas.getGameBoard());
                    playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                    playerController.setPlayer(gameDatas.getPlayer(username));
                    playerController.setOtherPlayers(gameDatas.getPlayers(username));
                }
                view.notify(message, outcome);
                break;
            case LOGIN:
                if(outcome.equals(Outcome.RIGHT)) {
                    connectionTimer.start();
                }
                view.notify(message, outcome, gameDatas.getUsername());
                break;
            case COLOR:
                view.notify(message, outcome, gameDatas.getColor());
                break;
            case NEW_TURN:
                playerController.setGameBoard(gameDatas.getGameBoard());
                playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                playerController.setCurrentPlayer(gameDatas.getCurrentPlayer());
                view.notify(message, outcome);
                break;
            case MOVE:
                playerController.setGameBoard(gameDatas.getGameBoard());
                playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                playerController.setCurrentPlayer(gameDatas.getCurrentPlayer());
                view.notify(message, outcome);
                break;
            case POWERUP:
                playerController.setGameBoard(gameDatas.getGameBoard());
                playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                playerController.setCurrentPlayer(gameDatas.getCurrentPlayer());
                playerController.setPowerup(gameDatas.getPowerup());
                if(gameDatas.getPowerup().equals("targetingscope") || gameDatas.getPowerup().equals("tagbackgrenade")){
                    playerController.setVictims(gameDatas.getPlayers(username));
                }
                view.notify(message, outcome);
                break;
            case GRAB:
                playerController.setGameBoard(gameDatas.getGameBoard());
                playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                playerController.setVictims(gameDatas.getPlayers(username));
                playerController.setCurrentPlayer(gameDatas.getCurrentPlayer());
                if(gameDatas.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;
            case SHOOT:
                playerController.setGameBoard(gameDatas.getGameBoard());
                playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setCurrentPlayer(gameDatas.getCurrentPlayer());
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                playerController.setVictims(gameDatas.getVictims());
                if(gameDatas.isMovement()){
                    playerController.setMovement(true);
                }
                view.notify(message, outcome);
                break;
            case RELOAD:
                playerController.setGameBoard(gameDatas.getGameBoard());
                playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                playerController.setWeapon(gameDatas.getWeapon());
                playerController.setCurrentPlayer(gameDatas.getCurrentPlayer());
                view.notify(message, outcome);
                break;
            case RECONNECTION:
                playerController.setGameBoard(gameDatas.getGameBoard());
                playerController.setKillshotTrack(gameDatas.getKillshotTrack());
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                playerController.setCurrentPlayer(gameDatas.getCurrentPlayer());
                view.notify(message, outcome);
                break;
            case FINAL_FRENZY:
                playerController.setGameBoard(gameData.getGameBoard());
                playerController.setKillshotTrack(gameData.getKillshotTrack());
                playerController.setPlayer(gameData.getPlayer(username));
                playerController.setOtherPlayers(gameData.getPlayers(username));
                playerController.setCurrentPlayer(gameData.getCurrentPlayer());
                playerController.setWeapon(gameData.getWeapon());
                playerController.setFinalFrenzy(true);
                playerController.setPlayerBoardFinalFrenzy(gameData.getPlayer().getPlayerBoard().isFinalFrenzy());
                view.notify(message);
                break;
            case RESPAWN:
                playerController.setPlayer(gameDatas.getPlayer(username));
                view.notify(message, outcome);
                break;
            case DROP_POWERUP:
            case DROP_WEAPON:
            case DISCARD_POWERUP:
                playerController.setPlayer(gameDatas.getPlayer(username));
                playerController.setOtherPlayers(gameDatas.getPlayers(username));
                view.notify(message, outcome);
                break;
            case SPAWN:
                view.notify(message, outcome, gameDatas.getPowerups());
                break;
            case SQUARE:
                view.notify(message, outcome, gameDatas.getSquareData());
                break;
            case DISCONNECT:
                view.notify(message, outcome, gameDatas.getUsername());
                break;
            case SCORE:
                view.notify(message, outcome, gameDatas.getScoreList());
                break;
            default:
                view.notify(message, outcome);
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
