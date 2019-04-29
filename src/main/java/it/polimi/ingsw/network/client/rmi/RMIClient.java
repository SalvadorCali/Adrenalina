package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.CommandLine;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {
    private RMIServerInterface server;
    private PlayerController playerController;
    private ViewInterface view;
    private String username;

    public RMIClient() throws IOException, NotBoundException {
        playerController = new PlayerController(this);
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        Printer.print("[CLIENT]Please, set an ip address:");
        String host = userInputStream.readLine();
        ConnectionInterface connectionInterface = (ConnectionInterface) java.rmi.Naming.lookup("server");
        server = connectionInterface.enrol(this);
    }
    @Override
    public void start() {
        view = new CommandLine(this);
        view.start();
    }

    @Override
    public void login(String username, TokenColor color){
        this.username = username;
        try {
            server.login(username, color);
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
    public void move(Direction... directions) throws RemoteException {
        server.move(directions);
    }

    @Override
    public void grab(int choice, Direction...directions) throws RemoteException {
        server.grab(choice);
    }

    @Override
    public void endTurn() throws RemoteException{
        server.endTurn();
    }

    @Override
    public void notify(Message message) throws RemoteException{
        switch (message){
            case NEW_TURN:
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
