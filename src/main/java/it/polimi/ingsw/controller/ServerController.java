package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.timer.BoardTypeTimer;
import it.polimi.ingsw.controller.timer.SpawnLocationTimer;
import it.polimi.ingsw.controller.timer.TurnTimer;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.KillshotTrackCLI;
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
    private GameData gameData;
    private boolean finalFrenzy;

    public ServerController(){
        gameController = new GameController();
        servers = new HashMap<>();
        colors = new HashMap<>();
        users = new HashMap<>();
        powerupsSpawn = new HashMap<>();
        disconnectedUsers = new HashMap<>();
        players = new ArrayList<>();
        spawnedPlayers = 0;
        gameData = new GameData();
        finalFrenzy = false;
    }


    public void login(String username, TokenColor color, ServerInterface server){
        if(!gameController.isGamePhase()){
            boolean newUsername = !servers.containsKey(username) && !disconnectedUsers.containsKey(username); //contains
            boolean newColor = !colors.containsKey(color) && !color.equals(TokenColor.NONE); //contains
            if(newUsername && newColor){
                addPlayer(username, color, server);
                if(servers.size() == Config.MIN_PLAYERS){
                    ScheduledExecutorService createGame = Executors.newSingleThreadScheduledExecutor();
                    //Runnable createGameTask = this::spawnLocation;
                    Runnable createGameTask = this::boardTypePhase;
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
        if(username.equals("cali2")){
            player.getPlayerBoard().addDamage(TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY,
                    TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY);
        }
        if(username.equals("cali3")){
            player.getPlayerBoard().addDamage(TokenColor.GREEN, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY,
                    TokenColor.GREY, TokenColor.GREEN, TokenColor.GREEN, TokenColor.GREEN);
        }
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

    private void boardTypePhase(){
        gameController.setBoardTypePhase(true);
        try {
            servers.get(players.get(0).getUsername()).notify(Message.BOARD, Outcome.RIGHT);
            for(int i=1; i<players.size(); i++){
                servers.get(players.get(i).getUsername()).notify(Message.BOARD, Outcome.ALL);
            }
        } catch (IOException e) {
            Printer.err(e);
        }
        BoardTypeTimer boardTypeTimer = new BoardTypeTimer(this, gameController);
        boardTypeTimer.start();
    }

    public void chooseBoardType(int boardType, int skulls){
        gameController.setBoardTypePhase(false);
        gameController.setBoard(boardType, skulls);
        spawnLocation();
    }

    private void startGame(){
        gameController.setSpawnLocationPhase(false);
        gameController.setGamePhase(true);
        players.get(0).setMyTurn(true);
        gameController.getGame().setCurrentPlayer(players.get(0));
        gameData.setGame(gameController.getGame());
        gameData.setPlayers(users);
        try {
            servers.get(players.get(0).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
        } catch (IOException e) {
            Printer.err(e);
        }
        TurnTimer timer = new TurnTimer(this, players, players.get(0));
        timer.start();
        Printer.println("Game iniziato!");
    }

    public void spawnLocation(){
        gameController.setBoardTypePhase(false);
        gameController.setSpawnLocationPhase(true);
        colors.forEach((c, u) -> {
            try {
                servers.get(u).notify(Message.PLAYER, Outcome.RIGHT, users.get(u));
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        gameController.startGame(players);
        gameData.setGame(gameController.getGame());
        gameData.setPlayers(users);
        servers.forEach((username, server) -> {
            try {
                server.notify(Message.GAME, Outcome.ALL, gameData);
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
        //users.get(username).addPowerup((PowerupCard) powerupsSpawn.get(username).get(0));
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

    public void showSquare(String username, int x, int y){
        try {
            servers.get(username).notify(Message.SQUARE, Outcome.RIGHT, gameController.showSquare(users.get(username), x, y));
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void disconnect(String username){
        if(users.get(username).isMyTurn()){
            gameController.endTurn(users.get(username));
        }
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
        gameData.setGame(gameController.getGame());
        gameData.setPlayers(users);
        servers.forEach((u, s) -> {
            try {
                if(u.equals(username)){
                    s.notify(Message.RECONNECTION, Outcome.RIGHT, gameData);
                }else{
                    s.notify(Message.RECONNECTION, Outcome.ALL, gameData);
                }
            } catch (IOException e) {
                Printer.err(e);
            }
        });
    }

    //board a tutti
    public void move(String username, Direction...directions){
        if(gameController.canMove(users.get(username), directions) && users.get(username).canUseAction()){
            gameController.move(users.get(username), directions);
            gameData.setGame(gameController.getGame());
            gameData.setPlayers(users);
            try {
                servers.get(username).notify(Message.MOVE, Outcome.RIGHT, gameData);

            } catch (IOException e) {
                Printer.err(e);
            }
            servers.forEach((u,s)-> {
                try {
                    if(!u.equals(username)){
                        s.notify(Message.MOVE, Outcome.ALL, gameData);
                    }
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
        }else{
            gameData.setGame(gameController.getGame());
            gameData.setPlayers(users);
            try {
                servers.get(username).notify(Message.MOVE, Outcome.WRONG, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }
    //board a tutti e playerBoard del grabber
    public void grab(String username, int choice, Direction...directions){
        if(gameController.grab(users.get(username), choice, directions)){
            if(directions.length > 0){
                gameData.setMovement(true);
            }
            gameData.setCurrentPlayer(username);
            gameData.setGame(gameController.getGame());
            gameData.setPlayers(users);
            try{
                servers.get(username).notify(Message.GRAB, Outcome.RIGHT, gameData);
            }catch (IOException e){
                Printer.err(e);
            }
            servers.forEach((u,s)-> {
                try {
                    if(!u.equals(username)){
                        s.notify(Message.GRAB, Outcome.ALL, gameData);
                    }
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
            gameData.setMovement(false);
        }else{
            gameData.setGame(gameController.getGame());
            gameData.setPlayers(users);
            try{
                servers.get(username).notify(Message.GRAB, Outcome.WRONG, gameData);
            }catch (IOException e){
                Printer.err(e);
            }
        }
    }


    public void drop(String username, String weapon){
        gameController.drop(users.get(username), weapon);
    }

    public Player[] getVictims(TokenColor...colors){
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
            try {
                servers.get(username).notify(Message.SHOOT, Outcome.WRONG, users.get(username));
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void shoot(String username, String weaponName, int effectNumber, TokenColor color, int x, int y){
        gameController.shoot(users.get(username), weaponName, effectNumber-1, users.get(colors.get(color)), x, y);
    }

    public void shoot(String username, String weaponName,  TokenColor color, int effectNumber, Direction...directions){
        gameController.shoot(users.get(username), weaponName, users.get(colors.get(color)), effectNumber-1, directions);
    }

    //metodo completo
    //playerBoard delle vittime
    public void shoot(String weaponName, int effectNumber, boolean basicFirst, String username, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction...directions){
        List<Player> victims = new ArrayList<>();
        Player victim1 = null;
        Player victim2 = null;
        Player victim3 = null;
        if(!firstVictim.equals(TokenColor.NONE)){
            victim1 = users.get(colors.get(firstVictim));
            victims.add(victim1);
        }
        if(!secondVictim.equals(TokenColor.NONE)){
            victim2 = users.get(colors.get(secondVictim));
            victims.add(victim2);
        }
        if(!thirdVictim.equals(TokenColor.NONE)){
            victim3 = users.get(colors.get(thirdVictim));
            victims.add(victim3);
        }
        if(gameController.shoot(weaponName, effectNumber - 1, basicFirst, users.get(username), victim1, victim2, victim3, x, y, directions)){
            try {
                gameData.setVictims(victims);
                if(directions.length > 0){
                    gameData.setMovement(true);
                }
                if(victims.isEmpty()){
                    users.forEach((u,p)->{
                        if(p.getPosition().getX() == users.get(username).getPosition().getX() &&
                                p.getPosition().getY() == users.get(username).getPosition().getY() &&
                                    !u.equals(username)){
                            victims.add(p);
                        }
                    });
                    gameData.setVictims(victims);
                }
                servers.get(username).notify(Message.SHOOT, Outcome.RIGHT, gameData);
                servers.forEach((u,s)-> {
                    try {
                        if(!u.equals(username)){
                            s.notify(Message.SHOOT, Outcome.ALL, gameData);
                        }
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                });
                gameData.setMovement(false);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try {
                servers.get(username).notify(Message.SHOOT, Outcome.WRONG, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void powerup(String username, String powerup, int x, int y){
        if(gameController.canMove(x,y)){
            gameController.move(users.get(username), x, y);
            gameData.setGame(gameController.getGame());
            gameData.setPlayers(users);
            try {
                servers.get(username).notify(Message.POWERUP, Outcome.RIGHT, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            gameData.setGame(gameController.getGame());
            gameData.setPlayers(users);
            try {
                servers.get(username).notify(Message.POWERUP, Outcome.WRONG, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void powerup(String username, String powerup, Direction direction, int value){
        if(value == 1){
            if(gameController.canMove(users.get(username), direction)){
                gameController.move(users.get(username), direction);
                gameData.setGame(gameController.getGame());
                gameData.setPlayers(users);
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.RIGHT, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }else{
                gameData.setGame(gameController.getGame());
                gameData.setPlayers(users);
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.WRONG, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else if(value == 2){
            if(gameController.canMove(users.get(username), direction, direction)){
                gameController.move(users.get(username), direction, direction);
                gameData.setGame(gameController.getGame());
                gameData.setPlayers(users);
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.RIGHT, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }else{
                gameData.setGame(gameController.getGame());
                gameData.setPlayers(users);
                try {
                    servers.get(username).notify(Message.POWERUP, Outcome.WRONG, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
    }

    //in base al powerup
    public void powerup(String username, String powerup, TokenColor victim, Color ammo, int x, int y, Direction...directions){
        if(gameController.usePowerup(powerup, users.get(username), users.get(colors.get(victim)), ammo, x, y, directions)){
            List<Player> victims = new ArrayList<>();
            switch(powerup){
                case "targetingscope":
                case "tagbackgrenade":
                    Player victim1 = users.get(colors.get(victim));
                    victims.add(victim1);
                    gameData.setVictims(victims);
                    break;
                default:
                    break;
            }
            gameData.setPowerup(powerup);
            gameData.setGame(gameController.getGame());
            servers.forEach((u,s)-> {
                try {
                    s.notify(Message.POWERUP, Outcome.RIGHT, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
        }else{
            gameData.setPowerup(powerup);
            gameData.setGame(gameController.getGame());
            try {
                servers.get(username).notify(Message.POWERUP, Outcome.WRONG, gameData);
            } catch (IOException e) {
                Printer.err(e);
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

    //nome arma e playerboard al solo reloader
    public void reload(String username, String weaponName){
        if(gameController.reload(users.get(username), weaponName)){
            try {
                servers.get(username).notify(Message.RELOAD, Outcome.RIGHT, weaponName);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try {
                servers.get(username).notify(Message.RELOAD, Outcome.WRONG, weaponName);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }
    //tutti i dati
    public void endTurn(String username){
        for(Player player : players){
            if(player.isDead()){
                deathAndRespawn();
                break;
            }
        }
        if(gameController.isFinalFrenzy() && !finalFrenzy){
            gameController.finalFrenzy();
            finalFrenzy = true;
            gameData.setGame(gameController.getGame());
            gameData.setPlayers(users);
            servers.forEach((u,s)-> {
                try {
                    s.notify(Message.FINAL_FRENZY, Outcome.ALL, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
        }
        for(int i = 0; i< players.size(); i++){
            if(players.get(i).getUsername().equals(username)){
                try {
                    if(servers.containsKey(username) && !players.get(i).isDisconnected()){
                        servers.get(username).notify(Message.END_TURN);
                    }
                    int index = nextPlayerIndex(i);
                    if(servers.containsKey(players.get(index).getUsername())){
                        gameData.setGame(gameController.getGame());
                        gameData.setPlayers(users);
                        servers.get(players.get(index).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
                        TurnTimer timer = new TurnTimer(this, players, players.get(index));
                        timer.start();
                    }
                    /*
                    if(i== players.size()-1){

                        if(servers.containsKey(players.get(0).getUsername())){
                            gameData.setGame(gameController.getGame());
                            gameData.setPlayers(users);
                            servers.get(players.get(0).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
                            TurnTimer timer = new TurnTimer(this, players, players.get(0));
                            timer.start();
                        }
                    }else{

                        if(servers.containsKey(players.get(i+1).getUsername())){
                            gameData.setGame(gameController.getGame());
                            gameData.setPlayers(users);
                            servers.get(players.get(i+1).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
                            TurnTimer timer = new TurnTimer(this, players, players.get(i+1));
                            timer.start();
                        }
                    }
                    */
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
        gameController.endTurn(users.get(username));
    }

    private int nextPlayerIndex(int index){
        boolean cycle = true;
        while(cycle){
            if(index == players.size() - 1){
                if(!players.get(0).isDisconnected()){
                    cycle = false;
                }
                index = 0;
            }else{
                if(!players.get(index + 1).isDisconnected()){
                    cycle = false;
                }
                index++;
            }
        }
        return index;
    }
    //punteggio
    private void deathAndRespawn(){
        gameController.deathAndRespawn(players);
        servers.forEach((u,s)-> {
            try {
                s.notify(Message.SCORE, Outcome.ALL, gameController.getScoreList());
            } catch (IOException e) {
                Printer.err(e);
            }
        });
    }
}
