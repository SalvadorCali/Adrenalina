package it.polimi.ingsw.network.client.rmi;

import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.server.rmi.RMIServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.CommandLine;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {
    private RMIServerInterface server;
    private ViewInterface view;
    private String username;

    public RMIClient() throws IOException, NotBoundException {
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
    public void login(String username){
        this.username = username;
        try {
            server.login(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
