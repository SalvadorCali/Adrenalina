package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private RMIClientInterface client;
    private ServerController serverController;
    private String clientName;

    public RMIServer(RMIClientInterface client, ServerController serverController) throws RemoteException {
        super();
        this.serverController = serverController;
        this.client = client;
    }

    @Override
    public void login(String username, TokenColor color){
        serverController.login(username, color, this);
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
    public void endTurn() throws RemoteException{
        serverController.endTurn(clientName);
    }

    @Override
    public void notifyLogin(Outcome outcome, String username) throws IOException {
        client.notifyLogin(outcome, username);
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

    @Override
    public void sendMessage(Advise advise){
        try {
            client.printMessage(advise);
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }
}
