package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.timer.TurnTimer;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private GameController gameController;
    private Map<String, ServerInterface> servers;
    private Map<TokenColor, String> colors;
    private Map<String, Player> users;
    private Map<String, Player> disconnectedUsers;
    private List<Player> players;

    public ServerController(){
        gameController = new GameController();
        servers = new HashMap<>();
        colors = new HashMap<>();
        users = new HashMap<>();
        disconnectedUsers = new HashMap<>();
        players = new ArrayList<>();
    }


    public void login(String username, TokenColor color, ServerInterface server){
        if(!gameController.isInGame()){
            boolean newUsername = !servers.containsKey(username) && !disconnectedUsers.containsKey(username); //contains
            boolean newColor = !colors.containsKey(color) && !color.equals(TokenColor.NONE); //contains
            if(newUsername && newColor){
                addPlayer(username, color, server);
                if(servers.size() == Config.MIN_PLAYERS){
                    ScheduledExecutorService createGame = Executors.newSingleThreadScheduledExecutor();
                    Runnable createGameTask = this::startGame;
                    createGame.schedule(createGameTask, Config.START_TIME, TimeUnit.MILLISECONDS);
                }
            }else if(newUsername || newColor){
                if(!newUsername){
                    try {
                        server.notify(Message.LOGIN, Outcome.WRONG, username);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }else{
                    try {
                        server.notify(Message.COLOR, Outcome.WRONG, color);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else{
                try {
                    server.notify(Message.LOGIN, Outcome.WRONG, username);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            if(disconnectedUsers.containsKey(username)){
                if(disconnectedUsers.get(username).getColor().equals(color)){
                    reconnect(username, server);
                }
            }else{
                try {
                    server.notify(Message.GAME, Outcome.WRONG);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
    }

    private void addPlayer(String username, TokenColor color, ServerInterface server){
        servers.put(username, server);
        colors.put(color, username);
        //added
        Player player = new Player(color);
        player.setUsername(username);
        players.add(player);
        users.put(colors.get(color), player);

        Printer.println(username + " connected!");
        servers.forEach((u, s) -> {
            try {
                if(u.equals(username)){
                    s.notify(Message.LOGIN, Outcome.RIGHT, username);
                }else{
                    s.notify(Message.LOGIN, Outcome.ALL, username);
                }
            } catch (IOException e) {
                Printer.err(e);
            }
        });
    }

    private void startGame(){
        Printer.println("prova");
        colors.forEach((c, u) -> {
            try {
                servers.get(u).notify(Message.PLAYER, Outcome.RIGHT, users.get(u));
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        Printer.println("prova2");
        gameController.startGame(players);
        servers.forEach((username, server) -> {
            try {
                server.notify(Message.GAME, Outcome.ALL, gameController.getGame().getBoard());
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        Printer.println("prova3");
        players.get(0).setMyTurn(true);
        try {
            servers.get(players.get(0).getUsername()).notify(Message.NEW_TURN);
        } catch (IOException e) {
            Printer.err(e);
        }
        Printer.println("prova4");
        TurnTimer timer = new TurnTimer(this, gameController, players);
        timer.start();
        Printer.println("Game iniziato!");
    }

    public void disconnect(String username){
        if(users.containsKey(username)){
            disconnectedUsers.put(username, users.get(username));
            users.get(username).setDisconnected(true);
            users.remove(username);
            Printer.println(username + " disconnected!");
            servers.remove(username);
            servers.forEach((u,s) -> {
                try {
                    s.notify(Message.DISCONNECT, Outcome.ALL, username);
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
            /*
            Printer.println("Lista utenti:");
            Printer.print("Server:");
            servers.forEach((u,s) -> Printer.println(u));
            Printer.print("Colors:");
            colors.forEach((t,st) -> Printer.println(st));
            Printer.print("Users:");
            users.forEach((us,p) -> Printer.println(us));
            Printer.print("DiscUsers:");
            disconnectedUsers.forEach((use,pl) -> Printer.println(use));
            */
        }
    }

    private void reconnect(String username, ServerInterface server){
        users.put(username, disconnectedUsers.get(username));
        users.get(username).setDisconnected(false);
        disconnectedUsers.remove(username);
        servers.put(username, server);
        Printer.println(username + " connected!");
        servers.forEach((u, s) -> {
            try {
                if(u.equals(username)){
                    s.notify(Message.LOGIN, Outcome.RIGHT, username);
                }else{
                    s.notify(Message.LOGIN, Outcome.ALL, username);
                }
            } catch (IOException e) {
                Printer.err(e);
            }
        });
    }

    public void move(String username, Direction...directions){
        if(gameController.canMove(users.get(username), directions)){
            gameController.move(users.get(username), directions);
            try {
                servers.get(username).notify(Message.MOVE, Outcome.RIGHT, username);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try {
                servers.get(username).notify(Message.MOVE, Outcome.WRONG, username);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void grab(String username, int choice, Direction...directions){
        gameController.grab(users.get(username), choice, directions);
    }

    public void shoot(String username, TokenColor color){
        gameController.shoot(users.get(username), users.get(colors.get(color)));
    }

    public void endTurn(String username){
        for(int i = 0; i< players.size(); i++){
            if(players.get(i).getUsername().equals(username)){
                try {
                    if(servers.containsKey(username)){
                        servers.get(username).notify(Message.END_TURN);
                    }
                    if(i== players.size()-1){
                        if(servers.containsKey(players.get(0).getUsername())){
                            servers.get(players.get(0).getUsername()).notify(Message.NEW_TURN);
                        }
                    }else{
                        if(servers.containsKey(players.get(i+1).getUsername())){
                            servers.get(players.get(i+1).getUsername()).notify(Message.NEW_TURN);
                        }
                    }
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
        gameController.endTurn(users.get(username));
    }
}
