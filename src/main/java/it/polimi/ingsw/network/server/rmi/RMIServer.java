package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;
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
    public void login(String username){
        serverController.addClient(username, this);
    }

    @Override
    public void move(Direction... directions) throws RemoteException {
        serverController.move(clientName, directions);
    }

    @Override
    public void notifyLogin(Subject subject, String username) throws IOException {
        client.notifyLogin(subject, username);
    }

    @Override
    public void notify(Message message, Subject subject, Object object) throws RemoteException {
        client.notify(message, subject, object);
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
