package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.AdrenalineZone;
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
    public void move(Direction... directions) throws RemoteException {
        server.move(directions);
    }

    @Override
    public void grab(int choice, Direction...directions) throws RemoteException {
        server.grab(choice);
    }

    @Override
    public void shoot(TokenColor color) throws RemoteException {
        server.shoot(color);
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
    public void powerupAmmos(PowerupData...powerups) throws RemoteException{
        server.powerupAmmos(powerups);
    }

    @Override
    public void reload(int...weapons) throws RemoteException{
        server.reload(weapons);
    }

    @Override
    public void endTurn() throws RemoteException{
        server.endTurn();
    }

    @Override
    public void notify(Message message) throws RemoteException{
        switch (message){
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
                    GameBoard gameBoard = (GameBoard) object;
                    playerController.setGameBoard(gameBoard);
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
                if(outcome.equals(Outcome.RIGHT)){
                    GameBoard gameBoard = (GameBoard) object;
                    playerController.setGameBoard(gameBoard);
                }
                view.notify(message);
            case MOVE:
                GameBoard gameBoard = (GameBoard) object;
                playerController.setGameBoard(gameBoard);
                view.notify(message, outcome);
                break;
            case POWERUP:
                GameBoard gb = (GameBoard) object;
                playerController.setGameBoard(gb);
                view.notify(message, outcome);
                break;
            case GRAB:
                Player player = (Player) object;
                playerController.setPlayer(player);
                view.notify(message, outcome);
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
