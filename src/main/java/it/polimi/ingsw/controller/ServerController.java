package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.timer.SpawnLocationTimer;
import it.polimi.ingsw.controller.timer.TurnTimer;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.MapCLI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private int spawnedPlayers;
    private GameController gameController;
    private Map<String, ServerInterface> servers;
    private Map<TokenColor, String> colors;
    private Map<String, Player> users;
    private Map<String, Player> disconnectedUsers;
    private Map<String, List<Card>> powerupsSpawn;
    private List<Player> players;

    public ServerController(){
        gameController = new GameController();
        servers = new HashMap<>();
        colors = new HashMap<>();
        users = new HashMap<>();
        powerupsSpawn = new HashMap<>();
        disconnectedUsers = new HashMap<>();
        players = new ArrayList<>();
        spawnedPlayers = 0;
    }


    public void login(String username, TokenColor color, ServerInterface server){
        if(!gameController.isGamePhase()){
            boolean newUsername = !servers.containsKey(username) && !disconnectedUsers.containsKey(username); //contains
            boolean newColor = !colors.containsKey(color) && !color.equals(TokenColor.NONE); //contains
            if(newUsername && newColor){
                addPlayer(username, color, server);
                if(servers.size() == Config.MIN_PLAYERS){
                    ScheduledExecutorService createGame = Executors.newSingleThreadScheduledExecutor();
                    Runnable createGameTask = this::spawnLocation;
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
        gameController.setSpawnLocationPhase(false);
        gameController.setGamePhase(true);
        players.get(0).setMyTurn(true);
        gameController.getGame().setCurrentPlayer(players.get(0));
        try {
            servers.get(players.get(0).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameController.getGame().getBoard());
        } catch (IOException e) {
            Printer.err(e);
        }
        TurnTimer timer = new TurnTimer(this, players, players.get(0));
        timer.start();
        Printer.println("Game iniziato!");
    }

    public void spawnLocation(){
        gameController.setSpawnLocationPhase(true);
        colors.forEach((c, u) -> {
            try {
                servers.get(u).notify(Message.PLAYER, Outcome.RIGHT, users.get(u));
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        gameController.startGame(players);
        servers.forEach((username, server) -> {
            try {
                server.notify(Message.GAME, Outcome.ALL, gameController.getGame().getBoard());
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        servers.forEach((username, server) -> {
            List<Card> powerups = new ArrayList<>();
            powerups.add(gameController.drawPowerup());
            powerups.add(gameController.drawPowerup());
            try {
                server.notify(Message.SPAWN, Outcome.RIGHT, powerups);
            } catch (IOException e) {
                Printer.err(e);
            }
            powerupsSpawn.put(username, powerups);
        });
        SpawnLocationTimer spawnLocationTimer = new SpawnLocationTimer(this, gameController);
        spawnLocationTimer.start();
    }

    public void choose(String username, int choice){
        users.get(username).setSpawned(true);
        gameController.setPlayer(users.get(username), powerupsSpawn.get(username).get(choice - 1).getColor());
        spawnedPlayers++;
        if(spawnedPlayers == servers.size()){
            startGame();
        }
    }

    public void randomChoice(){
        users.forEach((u,p) -> {
            if(!p.isSpawned()){
                p.setSpawned(true);
                gameController.setPlayer(p, powerupsSpawn.get(u).get(0).getColor());
                spawnedPlayers++;
                if(spawnedPlayers == servers.size()){
                    startGame();
                }
            }
        });
    }

    public void showSquare(String username){
        try {
            servers.get(username).notify(Message.SQUARE, Outcome.RIGHT, gameController.showSquare(users.get(username)));
        } catch (IOException e) {
            Printer.err(e);
        }
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
        if(gameController.canMove(users.get(username), directions) && users.get(username).canUseAction()){
            gameController.move(users.get(username), directions);
            try {
                servers.get(username).notify(Message.MOVE, Outcome.RIGHT, gameController.getGame().getBoard());
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try {
                servers.get(username).notify(Message.MOVE, Outcome.WRONG, gameController.getGame().getBoard());
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }
    //da gestire
    public void grab(String username, int choice, Direction...directions){
        if(gameController.grab(users.get(username), choice, directions)){
            try{
                servers.get(username).notify(Message.GRAB, Outcome.RIGHT, users.get(username));
            }catch (IOException e){
                Printer.err(e);
            }
        }else{
            try{
                servers.get(username).notify(Message.GRAB, Outcome.WRONG, users.get(username));
            }catch (IOException e){
                Printer.err(e);
            }
        }
    }

    public Player[] getVictims(TokenColor...colors){
        Printer.println("ciaooo");
        Player[] victims = new Player[colors.length];
        for(int i=0; i<colors.length; i++){
            victims[i] = users.get(this.colors.get(colors[i]));
        }
        Printer.println(victims[0].getColor());
        return victims;

    }

    public void shoot(String username, String weaponName, int effectNumber, TokenColor...colors){
        if(gameController.shoot(users.get(username), weaponName, effectNumber-1, getVictims(colors))){
            try {
                servers.get(username).notify(Message.SHOOT, Outcome.RIGHT, users.get(username));
            } catch (IOException e) {
                Printer.err(e);
            }
            for(TokenColor color : colors){
                try {
                    servers.get(this.colors.get(color)).notify(Message.SHOOT, Outcome.ALL, users.get(this.colors.get(color)));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            Printer.println("lockriiiii");
            try {
                Printer.println("lockriiiiiooo");
                servers.get(username).notify(Message.SHOOT, Outcome.WRONG, users.get(username));
                Printer.println("lockriiiiill");
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void shoot(String username, String weaponName, int effectNumber, TokenColor color, int x, int y){
        gameController.shoot(users.get(username), weaponName, effectNumber-1, users.get(colors.get(color)), x, y);
    }

    public void shoot(String username, String weaponName,  TokenColor color, int effectNumber,Direction...directions){
        gameController.shoot(users.get(username), weaponName, users.get(colors.get(color)), effectNumber-1, directions);
    }

    public void powerup(String username, String powerup, int x, int y){
        if(gameController.canMove(x,y)){
            gameController.move(users.get(username), x, y);
            try {
                servers.get(username).notify(Message.POWERUP, Outcome.RIGHT, gameController.getGame().getBoard());
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try {
                servers.get(username).notify(Message.POWERUP, Outcome.WRONG, gameController.getGame().getBoard());
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void powerup(String username, String powerup, Direction direction, int value){
        if(value == 1){
            if(gameController.canMove(users.get(username), direction)){
                gameController.move(users.get(username), direction);
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.RIGHT, gameController.getGame().getBoard());
                } catch (IOException e) {
                    Printer.err(e);
                }
            }else{
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.WRONG, gameController.getGame().getBoard());
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else if(value == 2){
            if(gameController.canMove(users.get(username), direction, direction)){
                gameController.move(users.get(username), direction, direction);
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.RIGHT, gameController.getGame().getBoard());
                } catch (IOException e) {
                    Printer.err(e);
                }
            }else{
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.WRONG, gameController.getGame().getBoard());
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
    }

    public void powerupAmmos(String username, PowerupData ...powerups){
        for(PowerupData powerup : powerups){
            users.get(username).increasePowerupAmmoNumber(powerup.getColor());
        }
    }

    public void resetPowerupAmmos(String username){
        users.get(username).resetPowerupAmmos();
    }

    public void reload(String username, int...weapons){
        //
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
                            servers.get(players.get(0).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameController.getGame().getBoard());
                            TurnTimer timer = new TurnTimer(this, players, players.get(0));
                            timer.start();
                        }
                    }else{
                        if(servers.containsKey(players.get(i+1).getUsername())){
                            servers.get(players.get(i+1).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameController.getGame().getBoard());
                            TurnTimer timer = new TurnTimer(this, players, players.get(i+1));
                            timer.start();
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
