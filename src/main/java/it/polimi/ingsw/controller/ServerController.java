package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.view.Message;
import it.polimi.ingsw.view.Response;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ServerController {
    private Map<String, ServerInterface> clients;

    public ServerController(){
        clients = new HashMap<>();
    }

    public void addClient(String username, ServerInterface server){
        if(clients.containsKey(username)){
            try {
                server.notifyLogin(Response.WRONG);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        clients.put(username, server);
    }
}
