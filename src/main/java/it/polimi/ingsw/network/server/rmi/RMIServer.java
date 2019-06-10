package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
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
    public void board(int boardType, int skulls) throws RemoteException {
        serverController.chooseBoardType(boardType, skulls);
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
    public void showSquare(int x, int y){
        serverController.showSquare(clientName, x, y);
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
    public void drop(String weaponName) throws RemoteException {
        serverController.drop(clientName, weaponName);
    }

    @Override
    public void dropPowerup(int powerup) throws RemoteException {
        serverController.dropPowerup(clientName, powerup);
    }

    @Override
    public void dropWeapon(int weapon) throws RemoteException {
        serverController.dropWeapon(clientName, weapon);
    }

    @Override
    public void discardPowerup(int powerup) throws RemoteException {
        serverController.discardPowerup(clientName, powerup);
    }

    @Override
    public void shoot(String weaponName, int effectNumber, boolean basicFirst, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction... directions) throws RemoteException {
        serverController.shoot(weaponName, effectNumber, basicFirst, clientName, firstVictim, secondVictim, thirdVictim, x, y, directions);
    }

    @Override
    public void moveAndReload(Direction firstDirection, Direction secondDirection, String... weapons) throws RemoteException {
        serverController.moveAndReload(clientName, firstDirection, secondDirection, weapons);
    }

    @Override
    public void powerup(String powerup, TokenColor victim, Color ammo, int x, int y, Direction... directions) throws RemoteException {
        serverController.powerup(clientName, powerup, victim, ammo, x, y, directions);
    }

    @Override
    public void powerupAmmos(PowerupData...powerups){
        serverController.powerupAmmos(clientName, powerups);
    }

    @Override
    public void powerupAmmos(int... powerups) throws RemoteException {
        serverController.powerupAmmos(clientName, powerups);
    }

    @Override
    public void reload(String weaponName){
        serverController.reload(clientName, weaponName);
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
