package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private RMIClientInterface client;
    private ServerController serverController;
    private ConnectionTimer connectionTimer;
    private String clientName;

    public RMIServer(RMIClientInterface client, ServerController serverController) throws RemoteException {
        super(Config.RMI_FREE_PORT);
        this.serverController = serverController;
        this.client = client;
    }

    @Override
    public void login(String username, TokenColor color, ConnectionTimer connectionTimer){
        this.connectionTimer = connectionTimer;
        connectionTimer.setServer(this);
        connectionTimer.start();
        clientName = username;
        serverController.login(username, color, this);
    }

    @Override
    public void disconnect(){
        serverController.disconnect(clientName);
    }

    @Override
    public void choose(int choice){
        serverController.choose(clientName, choice);
    }

    @Override
    public void showSquare(){
        serverController.showSquare(clientName);
    }

    @Override
    public void move(Direction... directions) throws RemoteException {
        serverController.move(clientName, directions);
    }

    @Override
    public void grab(int choice, Direction...directions) throws RemoteException{
        serverController.grab(clientName, choice, directions);
    }

    @Override
    public void shoot(String weaponName, TokenColor...colors){
        serverController.shoot(clientName, weaponName, colors);
    }

    @Override
    public void powerup(String powerup, int x, int y){
        serverController.powerup(clientName, powerup, x, y);
    }

    @Override
    public void powerup(String powerup, Direction direction, int value){
        serverController.powerup(clientName, powerup, direction, value);
    }

    @Override
    public void powerupAmmos(PowerupData...powerups){
        serverController.powerupAmmos(clientName, powerups);
    }

    @Override
    public void reload(int...weapons){
        serverController.reload(clientName, weapons);
    }

    @Override
    public void endTurn() throws RemoteException{
        serverController.endTurn(clientName);
    }

    @Override
    public void notify(Message message) throws RemoteException {
        client.notify(message);
    }

    @Override
    public void notify(Message message, Outcome outcome) throws RemoteException {
        client.notify(message, outcome);
    }

    @Override
    public void notify(Message message, Outcome outcome, Object object) throws RemoteException {
        client.notify(message, outcome, object);
    }
}
