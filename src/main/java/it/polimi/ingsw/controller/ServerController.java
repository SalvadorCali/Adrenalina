package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private GameController gameController;
    private Map<String, ServerInterface> clients;

    public ServerController(){
        gameController = new GameController();
        clients = new HashMap<>();
    }

    public void addClient(String username, ServerInterface server){
        if(!gameController.isInGame()){
            ExecutorService adder = Executors.newFixedThreadPool(1);
            Runnable adderTask = () -> {
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
                            if(u.equals(username)){
                                s.notifyLogin(Response.RIGHT, username);
                            }else{
                                s.notifyLogin(Response.ALL, username);
                            }
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    });
                    if(clients.size() == 3){
                        ScheduledExecutorService createGame = Executors.newSingleThreadScheduledExecutor();
                        Runnable createGameTask = () -> {
                            gameController.setInGame(true);
                            gameController.setColorSelection(true);
                            clients.forEach((u, s) -> {
                                try {
                                    s.sendMessage(Advise.COLOR);
                                } catch (IOException e) {
                                    Printer.err(e);
                                }
                            });
                            Printer.println("Game iniziato!");
                        };
                        createGame.schedule(createGameTask, 10000, TimeUnit.MILLISECONDS);
                        adder.shutdownNow();
                        try {
                            adder.awaitTermination(0, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            Printer.err(e);
                        }
                    }
                }
            };
            adder.execute(adderTask);
        }else{
            Printer.println("Game già iniziato!");
        }
    }
/*
    public void createPlayer(TokenColor color){
        if(gameController.getGame().getPlayerColors().isEmpty()){
            gameController.getGame().
        }
    }
    */
}
