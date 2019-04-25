package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Subject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private GameController gameController;
    private Map<String, ServerInterface> servers;
    private Map<TokenColor, String> colors;
    private Map<String, Player> players;

    public ServerController(){
        gameController = new GameController();
        servers = new HashMap<>();
        colors = new HashMap<>();
        players = new HashMap<>();
    }

    public void login(String username, TokenColor color, ServerInterface server){
        if(!gameController.isInGame()){
            boolean newUsername = !servers.containsKey(username);
            boolean newColor = !colors.containsKey(color);
            if(newUsername && newColor){
                servers.put(username, server);
                colors.put(color, username);
                servers.forEach((u, s) -> {
                    try {
                        if(u.equals(username)){
                            s.notify(Message.LOGIN, Subject.RIGHT, username);
                        }else{
                            s.notify(Message.LOGIN, Subject.ALL, username);
                        }
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                });
                if(servers.size() == 3){
                    ScheduledExecutorService createGame = Executors.newSingleThreadScheduledExecutor();
                    Runnable createGameTask = this::startGame;
                    createGame.schedule(createGameTask, 10000, TimeUnit.MILLISECONDS);
                }
            }else if(newUsername || newColor){
                if(newUsername){
                    try {
                        server.notify(Message.USERNAME, Subject.WRONG, username);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }else{
                    try {
                        server.notify(Message.COLOR, Subject.WRONG, color);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else{
                try {
                    server.notify(Message.LOGIN, Subject.WRONG, username);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            Printer.println("Game iniziato!");
        }
    }

    private void startGame(){
        colors.forEach((c, u) -> {
            try {
                Player player = new Player(c);
                servers.get(u).notify(Message.PLAYER, Subject.RIGHT, player);
                players.put(colors.get(c), player);
                gameController.getGame().addPlayerColors(c);
                gameController.getGame().addPlayer((player));
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        gameController.startGame();
        TurnTimer timer = new TurnTimer(gameController, servers, players);
        timer.start();
        Printer.println("Game iniziato!");
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

    public void endTurn(String username){
        gameController.endTurn(players.get(username));
        try {
            servers.get(username).notify(Message.END_TURN, Subject.RIGHT);
        } catch (IOException e) {
            Printer.err(e);
        }
    }
}
