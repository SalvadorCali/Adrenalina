package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.timer.BoardTypeTimer;
import it.polimi.ingsw.controller.timer.RespawnTimer;
import it.polimi.ingsw.controller.timer.SpawnLocationTimer;
import it.polimi.ingsw.controller.timer.TurnTimer;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;
import java.rmi.RemoteException;
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
    private TurnTimer timer;
    private int deathNumber;
    private int respawnNumber;

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

    public Map<String, Player> getDisconnectedUsers() {
        return disconnectedUsers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isGamePhase(){
        return gameController.isGamePhase();
    }

    public void login(String username, TokenColor color, ServerInterface server){
        if(ServerControllerManager.containsDisconnectedUsername(username)){
            try {
                server.setServerController(ServerControllerManager.getServerController(username));
            } catch (RemoteException e) {
                Printer.err(e);
            }
            ServerControllerManager.getServerController(username).reconnect(username, server);
            return;
        }
        if(!gameController.isGamePhase()){
            boolean newUsername = !servers.containsKey(username) && !disconnectedUsers.containsKey(username) && !ServerControllerManager.containsUsername(username); //contains
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
                    gameData.setUsername(username);
                    try {
                        server.notify(Message.LOGIN, Outcome.WRONG, gameData);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }else{
                    gameData.setColor(color);
                    try {
                        server.notify(Message.COLOR, Outcome.WRONG, gameData);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else{
                gameData.setUsername(username);
                try {
                    server.notify(Message.LOGIN, Outcome.WRONG, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            if(disconnectedUsers.containsKey(username)){
                if(disconnectedUsers.get(username).getColor().equals(color)){
                    reconnect(username, server);
                }
            //}else if(ServerControllerManager.containsDisconnectedUsername(username)){
              //  ServerControllerManager.getServerController(username).reconnect(username, server);
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
        debugMode(player, username);
        player.setUsername(username);
        players.add(player);
        users.put(colors.get(color), player);

        Printer.println(username + " connected!");
        gameData.setUsername(username);
        servers.forEach((u, s) -> {
            try {
                if(u.equals(username)){
                    s.notify(Message.LOGIN, Outcome.RIGHT, gameData);
                }else{
                    s.notify(Message.LOGIN, Outcome.ALL, gameData);
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
        setGameData();
        try {
            servers.get(players.get(0).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
            servers.forEach((u,s)->{
                if(!u.equals(players.get(0).getUsername())){
                    try {
                        s.notify(Message.NEW_TURN, Outcome.ALL, gameData);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            });
        } catch (IOException e) {
            Printer.err(e);
        }
        timer = new TurnTimer(this, players, players.get(0));
        timer.start();
        Printer.println("Game iniziato!");
    }

    public void spawnLocation(){
        gameController.setBoardTypePhase(false);
        gameController.setSpawnLocationPhase(true);
        colors.forEach((c, u) -> {
            try {
                gameData.setPlayer(users.get(u));
                servers.get(u).notify(Message.PLAYER, Outcome.RIGHT, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        gameController.startGame(players);
        setGameDataBeforeGame();
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
            gameData.setPowerups(powerups);
            try {
                server.notify(Message.SPAWN, Outcome.RIGHT, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
            powerupsSpawn.put(username, powerups);
        });
        SpawnLocationTimer spawnLocationTimer = new SpawnLocationTimer(this, gameController);
        spawnLocationTimer.start();
    }

    public void respawn(String username, int choice){
        if(gameController.canRespawn(users.get(username), choice)){
            gameController.respawn(users.get(username), choice);
            respawnNumber++;
            if(respawnNumber == deathNumber){
                gameController.setRespawnPhase(false);
                gameController.setGamePhase(true);
                respawnNumber = 0;
                endTurn(gameController.getGame().getCurrentPlayer().getUsername());
            }
        }
    }

    public void choose(String username, int choice){
        users.get(username).setSpawned(true);
        if(choice == 1){
            gameController.addPowerup(users.get(username), powerupsSpawn.get(username).get(1));
        }else if(choice == 2){
            gameController.addPowerup(users.get(username), powerupsSpawn.get(username).get(0));
        }
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
                gameController.addPowerup(p, powerupsSpawn.get(u).get(1));
                gameController.setPlayer(p, powerupsSpawn.get(u).get(0).getColor());
                spawnedPlayers++;
                if(spawnedPlayers == servers.size()){
                    startGame();
                }
            }
        });
    }

    public void showSquare(String username){
        if(gameController.canShowSquare(users.get(username))){
            gameData.setSquareData(gameController.showSquare(users.get(username)));
            try {
                servers.get(username).notify(Message.SQUARE, Outcome.RIGHT, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            gameData.setSquareData(new SquareData());
            try {
                servers.get(username).notify(Message.SQUARE, Outcome.WRONG, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void showSquare(String username, int x, int y){
        if(gameController.canShowSquare(users.get(username), x, y)){
            gameData.setSquareData(gameController.showSquare(users.get(username), x, y));
            try {
                servers.get(username).notify(Message.SQUARE, Outcome.RIGHT, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }else{
            gameData.setSquareData(new SquareData());
            try {
                servers.get(username).notify(Message.SQUARE, Outcome.WRONG, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void disconnect(String username){
        Printer.println("disconnessione" + username);
        if(users.get(username).isMyTurn()){
            Printer.println("disconnessione2" + username);
            users.get(username).setDisconnected(true);
            endTurnDisconnected(username);
        }
        if(users.containsKey(username)){
            Printer.println("disconnessione3" + username);
            users.get(username).setDisconnected(true);
            disconnectedUsers.put(username, users.get(username));
            //users.get(username).setDisconnected(true);
            users.remove(username);
            /*
            if(users.size()<Config.MIN_PLAYERS){
                endGame();
                return;
            }
             */
            Printer.println(username + " disconnected!");
            servers.remove(username);
            gameData.setUsername(username);
            servers.forEach((u,s) -> {
                try {
                    s.notify(Message.DISCONNECT, Outcome.ALL, gameData);
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
        setGameData();
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
        if(users.get(username).isMyTurn()){
            if(gameController.canMove(users.get(username), directions) && users.get(username).canUseAction()){
                if(gameController.move(users.get(username), directions)){
                    setGameData();
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
                    setGameData();
                    try {
                        servers.get(username).notify(Message.MOVE, Outcome.WRONG, gameData);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else{
                setGameData();
                try {
                    servers.get(username).notify(Message.MOVE, Outcome.WRONG, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }
    //board a tutti e playerBoard del grabber
    public void grab(String username, int choice, Direction...directions){
        if(users.get(username).isMyTurn()){
            if(gameController.grab(users.get(username), choice, directions)){
                if(directions.length > 0){
                    gameData.setMovement(true);
                }
                setGameData();
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
                setGameData();
                try{
                    servers.get(username).notify(Message.GRAB, Outcome.WRONG, gameData);
                }catch (IOException e){
                    Printer.err(e);
                }
            }
        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }


    public void drop(String username, String weapon){
        gameController.drop(users.get(username), weapon);
    }

    public void dropPowerup(String username, int powerup){
        if(gameController.canDropPowerup(users.get(username), powerup)){
            try{
                gameController.dropPowerup(users.get(username), powerup);
                setGameData();
                servers.get(username).notify(Message.DROP_POWERUP, Outcome.RIGHT, gameData);
            }catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try{
                setGameData();
                servers.get(username).notify(Message.DROP_POWERUP, Outcome.WRONG, gameData);
            }catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void dropWeapon(String username, int weapon){
        if(gameController.canDropWeapon(users.get(username), weapon)){
            try{
                gameController.dropWeapon(users.get(username), weapon);
                setGameData();
                servers.get(username).notify(Message.DROP_WEAPON, Outcome.RIGHT, gameData);
            }catch (IOException e) {
                Printer.err(e);
            }
        }else{
            try{
                setGameData();
                servers.get(username).notify(Message.DROP_WEAPON, Outcome.WRONG, gameData);
            }catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void discardPowerup(String username, int powerup){
         if(gameController.canDiscardPowerup(users.get(username), powerup)){
            try{
                gameController.discardPowerup(users.get(username), powerup);
                setGameData();
                servers.get(username).notify(Message.DISCARD_POWERUP, Outcome.RIGHT, gameData);
            }catch (IOException e) {
                Printer.err(e);
            }
        }else{
             try{
                 setGameData();
                 servers.get(username).notify(Message.DISCARD_POWERUP, Outcome.WRONG, gameData);
             }catch (IOException e) {
                 Printer.err(e);
             }
        }
    }

    //metodo completo
    //playerBoard delle vittime
    public void shoot(String weaponName, int effectNumber, boolean basicFirst, String username, TokenColor firstVictim, TokenColor secondVictim, TokenColor thirdVictim, int x, int y, Direction...directions){
        if(users.get(username).isMyTurn()){
            List<Player> victims = new ArrayList<>();
            Player victim1 = null;
            Player victim2 = null;
            Player victim3 = null;
            if(!firstVictim.equals(TokenColor.NONE)){
                victim1 = users.get(colors.get(firstVictim));
            }
            if(!secondVictim.equals(TokenColor.NONE)){
                victim2 = users.get(colors.get(secondVictim));
            }
            if(!thirdVictim.equals(TokenColor.NONE)){
                victim3 = users.get(colors.get(thirdVictim));
            }
            if(gameController.shoot(weaponName, effectNumber - 1, basicFirst, users.get(username), victim1, victim2, victim3, x, y, directions)){
                try {
                    users.forEach((u,p)->{
                        if(p.isDamaged()){
                            victims.add(p);
                        }
                    });
                    setGameData();
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
        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void moveAndReload(String username, Direction firstDirection, String...weapons){
        if(gameController.canMoveAndReload(users.get(username), firstDirection, weapons)){
            gameController.moveAndReload(users.get(username), firstDirection, weapons);
        }
    }

    public void moveAndReload(String username, Direction firstDirection, Direction secondDirection, String...weapons){
        if(gameController.canMoveAndReload(users.get(username), firstDirection, secondDirection, weapons)){
            gameController.moveAndReload(users.get(username), firstDirection, secondDirection, weapons);
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
            setGameData();
            gameData.setPowerup(powerup);
            servers.forEach((u,s)-> {
                try {
                    s.notify(Message.POWERUP, Outcome.RIGHT, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
        }else{
            setGameData();
            gameData.setPowerup(powerup);
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

    public void powerupAmmos(String username, int...powerups){
        gameController.powerupAmmos(users.get(username), powerups);
    }

    public void resetPowerupAmmos(String username){
        users.get(username).resetPowerupAmmos();
    }

    //nome arma e playerboard al solo reloader
    public void reload(String username, String weaponName){
        if(users.get(username).isMyTurn()){
            if(gameController.reload(users.get(username), weaponName)){
                try {
                    setGameData();
                    gameData.setWeapon(weaponName);
                    users.get(username).setActionNumber(2);
                    servers.get(username).notify(Message.RELOAD, Outcome.RIGHT, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }else{
                try {
                    setGameData();
                    gameData.setWeapon(weaponName);
                    servers.get(username).notify(Message.RELOAD, Outcome.WRONG, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }
    //tutti i dati
    public void endTurn(String username){
        if(users.get(username).isMyTurn()){
            for(Player player : players){
                if(player.isDead()){
                    deathAndRespawn();
                    return;
                }
            }
            if(gameController.isFinalFrenzy() && !finalFrenzy){
                gameController.finalFrenzy();
                finalFrenzy = true;
                setGameData();
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
                        Printer.println("indice " + index);
                        if(servers.containsKey(players.get(index).getUsername())){
                            setGameData();
                            servers.get(players.get(index).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
                            timer.interrupt();
                            timer = new TurnTimer(this, players, players.get(index));
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
        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    public void endTurnDisconnected(String username){
        if(users.get(username).isMyTurn()){
            for(Player player : players){
                if(player.isDead()){
                    deathAndRespawn();
                    return;
                }
            }
            if(gameController.isFinalFrenzy() && !finalFrenzy){
                gameController.finalFrenzy();
                finalFrenzy = true;
                setGameData();
                servers.forEach((u,s)-> {
                    try {
                        if(!u.equals(username)){
                            s.notify(Message.FINAL_FRENZY, Outcome.ALL, gameData);
                        }
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
                        Printer.println("indice " + index);
                        if(servers.containsKey(players.get(index).getUsername())){
                            setGameData();
                            servers.get(players.get(index).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
                            timer.interrupt();
                            timer = new TurnTimer(this, players, players.get(index));
                            timer.start();
                        }
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }
            gameController.endTurn(users.get(username));
        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
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
        timer.interrupt();
        gameController.deathAndRespawn(players);
        gameData.setScoreList(gameController.getScoreList());
        servers.forEach((u,s)-> {
            try {
                s.notify(Message.SCORE, Outcome.ALL, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        gameData.setPlayers(users);
        deathNumber = 0;
        for(Player player : players){
            if(player.isDead()){
                if(player.isDisconnected()){
                    player.setDead(false);
                    player.setRespawned(false);
                    respawn(player.getUsername(), 1);
                    deathNumber++;
                }else{
                    player.setDead(false);
                    player.setRespawned(false);
                    deathNumber++;
                    try {
                        servers.get(player.getUsername()).notify(Message.RESPAWN, Outcome.RIGHT, gameData);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                    RespawnTimer respawnTimer = new RespawnTimer(this, gameController, player);
                    respawnTimer.start();
                }
            }
        }
    }

    private void endGame(){
        Printer.println("Game end!");
    }

    private void setGameDataBeforeGame(){
        gameData.setGame(gameController.getGame());
        gameData.setPlayers(users);
    }

    private void setGameData(){
        gameData.setGame(gameController.getGame());
        gameData.setPlayers(users);
        gameData.setCurrentPlayer(gameController.getGame().getCurrentPlayer().getUsername());
    }

    private void debugMode(Player player, String username){
        if(username.equals("cali2")){
            player.getPlayerBoard().addDamage(TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY,
                    TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY);
        }
        if(username.equals("cali3")){
            player.getPlayerBoard().addDamage(TokenColor.GREEN, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY,
                    TokenColor.GREY, TokenColor.GREEN, TokenColor.GREEN, TokenColor.GREEN);
        }
    }
}
