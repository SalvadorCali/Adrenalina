package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Subject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private GameController gameController;
    private Map<String, ServerInterface> servers;
    private Map<String, Player> players;

    public ServerController(){
        gameController = new GameController();
        servers = new HashMap<>();
        players = new HashMap<>();
    }

    public void addClient(String username, ServerInterface server){
        if(!gameController.isInGame()){
            ExecutorService adder = Executors.newFixedThreadPool(1);
            Runnable adderTask = () -> {
                if(servers.containsKey(username)){
                    try {
                        server.notifyLogin(Subject.WRONG, username);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }else{
                    servers.put(username, server);
                    servers.forEach((u, s) -> {
                        try {
                            if(u.equals(username)){
                                s.notifyLogin(Subject.RIGHT, username);
                            }else{
                                s.notifyLogin(Subject.ALL, username);
                            }
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    });
                    if(servers.size() == 3){
                        ScheduledExecutorService createGame = Executors.newSingleThreadScheduledExecutor();
                        Runnable createGameTask = () -> {
                            gameController.setColorSelection(true);
                            servers.forEach((u, s) -> {
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

    public void chooseColor(String username, TokenColor color){
        ServerInterface server = servers.get(username);
        if(!gameController.getGame().containsColor(color)){
            gameController.getGame().addPlayerColors(color);
            Player player = new Player(color);
            gameController.getGame().addPlayer((player));
            players.put(username, player);
            try {
                server.notify(Message.COLOR, Subject.RIGHT, player);
            } catch (IOException e) {
                Printer.err(e);
            }
            if(gameController.getGame().getPlayerColors().size() == gameController.getGame().getPlayers().size()){
               gameController.setInGame(true);
            }
        }else{
            try {
                server.notify(Message.COLOR, Subject.WRONG, color);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void move(String username, Direction...directions){
        if(gameController.canMove(players.get(username), directions)){
            gameController.move(players.get(username), directions);
            try {
                servers.get(username).notify(Message.MOVE, Subject.RIGHT, username);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try {
                servers.get(username).notify(Message.MOVE, Subject.WRONG, username);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void grab(String username, int choice, Direction...directions){
        gameController.grab(players.get(username), choice, directions);
    }
}
