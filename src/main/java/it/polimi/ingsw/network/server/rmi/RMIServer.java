package it.polimi.ingsw.network.server.rmi;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.network.client.rmi.RMIClientInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.Response;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private RMIClientInterface client;
    private ServerController serverController;

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
    public void notifyLogin(Response response) throws RemoteException {

    }
}
