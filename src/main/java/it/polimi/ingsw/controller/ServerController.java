package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.Response;

import java.io.IOException;
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
                server.notifyLogin(Response.WRONG, username);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            clients.put(username, server);
            clients.forEach((u, s) -> {
                try {
                    s.notifyLogin(Response.RIGHT, username);
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
        }

    }
}
