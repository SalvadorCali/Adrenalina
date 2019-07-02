package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.datas.GameData;
import it.polimi.ingsw.controller.datas.SquareData;
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

/**
 * This class is the main controller of the game. It receives actions by Servers and sends them the result of the action.
 */
public class ServerController {
    /**
     * The number of spawned players.
     */
    private int spawnedPlayers;
    /**
     * The current game controller.
     */
    private GameController gameController;
    /**
     * A map of Servers.
     */
    private Map<String, ServerInterface> servers;
    /**
     * A map of players' colors.
     */
    private Map<TokenColor, String> colors;
    /**
     * A map of the players that are currently playing.
     */
    private Map<String, Player> users;
    /**
     * A map of disconnected players.
     */
    private Map<String, Player> disconnectedUsers;
    /**
     * A map of powerups, using for the spawn action.
     */
    private Map<String, List<Card>> powerupsSpawn;
    /**
     * The list of players that are currently playing.
     */
    private List<Player> players;
    /**
     * The data of the game that will be sent to the Servers.
     */
    private GameData gameData;
    /**
     * A boolean value, true if the game is in his final frenzy phase.
     */
    private boolean finalFrenzy;
    /**
     * A timer for the turn.
     */
    private TurnTimer timer;
    /**
     * A number that represents the number of deaths.
     */
    private int deathNumber;
    /**
     * The number of respawns after a death.
     */
    private int respawnNumber;
    /**
     * The number of turns of final frenzy.
     */
    private int finalFrenzyTurns;
    private static final String CONNECTED = " connected!";
    private static final String DISCONNECTED = " disconnected!";
    private static final String GAME_START = "Game start!";
    private static final String GAME_END = "Game end!";
    private static final String TARGETING_SCOPE = "targetingscope";
    private static final String TAGBACK_GRENADE = "tagbackgrenade";
    private static final String DEBUG_1 = "cali2";
    private static final String DEBUG_2 = "cali3";

    /**
     * Class constructor. Creates the GameController.
     */
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

    /**
     * Getter for the disconnected users.
     * @return the disconnected users.
     */
    public Map<String, Player> getDisconnectedUsers() {
        return disconnectedUsers;
    }

    /**
     * Getter for the players.
     * @return the players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns true if the game is in its game phase.
     * @return a boolean value.
     */
    public boolean isGamePhase(){
        return gameController.isGamePhase();
    }

    /**
     * The login method. It controls if there is another player with the same username in this or in another game, then adds the player to the game.
     * If the number of players connected is equals to the minimum number for a game, it starts a new game.
     * @param username the username chosen by the player.
     * @param color the color chosen by the player.
     * @param server the relative server.
     */
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

    /**
     * Add a new player to the list of players and notify this to other playes.
     * @param username the username of the new player.
     * @param color the color of the new player.
     * @param server the player's Server.
     */
    private void addPlayer(String username, TokenColor color, ServerInterface server){
        servers.put(username, server);
        colors.put(color, username);
        //added
        Player player = new Player(color);
        debugMode(player, username);
        player.setUsername(username);
        players.add(player);
        users.put(colors.get(color), player);

        Printer.println(username + CONNECTED);
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

    /**
     * Notify to the players the choice of the board and creates a timer for this phase of the game.
     */
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

    /**
     * Sets the board and the number of skulls chosen by the first player.
     * @param boardType the type of the board.
     * @param skulls the number of skulls of the killshot track.
     */
    public void chooseBoardType(int boardType, int skulls){
        gameController.setBoardTypePhase(false);
        gameController.setBoard(boardType, skulls);
        spawnLocation();
    }

    /**
     * Starts the game and sets the first player. Then creates a timer for the turn of the players.
     */
    private void startGame(){
        gameController.setSpawnLocationPhase(false);
        gameController.setGamePhase(true);
        int index = 0;
        for(int i=0; i<players.size(); i++){
            if(players.get(i).isDisconnected()){
                index++;
            }else{
                break;
            }
        }
        players.get(index).setMyTurn(true);
        gameController.getGame().setCurrentPlayer(players.get(index));
        setGameData();
        try {
            servers.get(players.get(index).getUsername()).notify(Message.NEW_TURN, Outcome.RIGHT, gameData);
            int index2 = index;
            servers.forEach((u,s)->{
                if(!u.equals(players.get(index2).getUsername())){
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
        timer = new TurnTimer(this, players, players.get(index));
        timer.start();
        Printer.println(GAME_START);
    }

    /**
     * Start the phase where players have to choose a powerup to spawn. It draws 2 powerups for each player and sends them. Then create a timer for this phase of the game.
     */
    public void spawnLocation(){
        gameController.setBoardTypePhase(false);
        gameController.setSpawnLocationPhase(true);
        servers.forEach((username, server) -> {
            try {
                gameData.setPlayer(users.get(username));
                server.notify(Message.PLAYER, Outcome.RIGHT, gameData);
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
        disconnectedUsers.forEach((username, player)->{
            List<Card> powerups = new ArrayList<>();
            powerups.add(gameController.drawPowerup());
            powerups.add(gameController.drawPowerup());
            powerupsSpawn.put(username, powerups);
        });
        SpawnLocationTimer spawnLocationTimer = new SpawnLocationTimer(this, gameController);
        spawnLocationTimer.start();
    }

    /**
     * Generates a player in a spawn point after his death, on the basis of his choice.
     * @param username the username of the dead player.
     * @param choice the powerup chosen for the respawn.
     */
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

    /**
     * Adds the chosen powerup to the player and generate him in the correct spawn point. Then, if the number of spawned players is equals to the number of players, starts the game.
     * @param username the username of the player.
     * @param choice the choosen powerup.
     */
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
        if(spawnedPlayers == servers.size() + disconnectedUsers.size()){
            startGame();
        }
    }

    /**
     * Makes a random choice for a player that didn't choose his powerup before the timeout.
     */
    public void randomChoice(){
        users.forEach((u,p) -> {
            if(!p.isSpawned()){
                p.setSpawned(true);
                gameController.addPowerup(p, powerupsSpawn.get(u).get(1));
                gameController.setPlayer(p, powerupsSpawn.get(u).get(0).getColor());
                spawnedPlayers++;
                if(spawnedPlayers == servers.size() + disconnectedUsers.size()){
                    startGame();
                }
            }
        });
        disconnectedUsers.forEach((u,p)->{
            if(!p.isSpawned()){
                p.setSpawned(true);
                gameController.addPowerup(p, powerupsSpawn.get(u).get(1));
                gameController.setPlayer(p, powerupsSpawn.get(u).get(0).getColor());
                spawnedPlayers++;
                if(spawnedPlayers == servers.size() + disconnectedUsers.size()){
                    startGame();
                }
            }
        });
    }

    /**
     * Sends to the user the requested square's data.
     * @param username the username of the player who wants to see a square.
     */
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

    /**
     * Sends to the user the requested square's data.
     * @param username the username of the player who wants to see a square.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     */
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

    /**
     * On the basis of the phase of the game, disconnects the player. Put out him from the users and servers, and add him to the disconnectedUsers.
     * @param username the username of the disconnected player.
     */
    public void disconnect(String username){
        if(disconnectedUsers.containsKey(username)){
            return;
        }
        /*
        if(gameController.isBoardTypePhase() && players.get(0).getUsername().equals(username)){
            chooseBoardType(1, 5);
        }*/
        if(gameController.isSpawnLocationPhase()){
            choose(username, 1);
        }
        if(users.get(username).isMyTurn()){
            users.get(username).setDisconnected(true);
            endTurnDisconnected(username);
        }
        if(users.containsKey(username)){
            users.get(username).setDisconnected(true);
            disconnectedUsers.put(username, users.get(username));
            users.remove(username);
            /*
            if(users.size()<Config.MIN_PLAYERS){
                endGame();
                return;
            }
             */
            Printer.println(username + DISCONNECTED);
            servers.remove(username);
            gameData.setUsername(username);
            servers.forEach((u,s) -> {
                try {
                    s.notify(Message.DISCONNECT, Outcome.ALL, gameData);
                } catch (IOException e) {
                    Printer.err(e);
                }
            });
            if(gameController.isBoardTypePhase() && players.get(0).getUsername().equals(username)){
                chooseBoardType(1, 5);
            }
        }
    }

    /**
     * Reconnects a player that was disconnected. Adds him to the user and servers.
     * @param username the username of the player who wants to reconnects himself to the game.
     * @param server the player's Server.
     */
    private void reconnect(String username, ServerInterface server){
        users.put(username, disconnectedUsers.get(username));
        users.get(username).setDisconnected(false);
        disconnectedUsers.remove(username);
        servers.put(username, server);
        Printer.println(username + CONNECTED);
        setGameData();
        gameData.setUsername(username);
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

    /**
     * If the player can move, moves him and sends to the players the result of the action.
     * @param username the username of the player who wants to move himself.
     * @param directions the directions where the player wants to move.
     */
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

    /**
     * If the player can grab, grabs in the correct square and sends to the other players the result of the action.
     * @param username the username of the player who wants to grab.
     * @param choice what the player wants to grab.
     * @param directions the directions where the player wants to move before the grab action.
     */
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

    /**
     * Drops a powerup.
     * @param username the username of the player who wants to drop a powerup.
     * @param powerup the powerup that the player wants to drop.
     */
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

    /**
     * Drops a weapon.
     * @param username the username of the player who wants to drop a weapon.
     * @param weapon the weapon that the player wants to drop.
     */
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

    /**
     * Discards a powerup and add it as ammo.
     * @param username the username of the player who wants to discard a powerup.
     * @param powerup the powerup that the player wants to discard.
     */
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

    /**
     * Calls the relative {@link GameController#shoot(String, int, boolean, Player, Player, Player, Player, int, int, Direction...)} method and sends to the other players the result of the action.
     * @param weaponName the name of the weapon.
     * @param effectNumber the number of the effect.
     * @param basicFirst true if the player wants to use the basic effect first.
     * @param username the username of the shooter.
     * @param firstVictim the first victim.
     * @param secondVictim the second victim.
     * @param thirdVictim the third victim.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the directions.
     */
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

    /**
     * If the player can move and reload, calls the relative {@link GameController} method.
     * @param username the username of the player.
     * @param firstDirection the first direction where the player wants to move.
     * @param weapons the weapons to reload.
     */
    public void moveAndReload(String username, Direction firstDirection, String...weapons){
        if(gameController.canMoveAndReload(users.get(username), firstDirection, weapons)){
            gameController.moveAndReload(users.get(username), firstDirection, weapons);
        }
    }

    /**
     * If the player can move and reload, calls the relative {@link GameController} method.
     * @param username the username of the player.
     * @param firstDirection the first direction where the player wants to move.
     * @param secondDirection the second direction where the player wants to move.
     * @param weapons the weapons to reload.
     */
    public void moveAndReload(String username, Direction firstDirection, Direction secondDirection, String...weapons){
        if(gameController.canMoveAndReload(users.get(username), firstDirection, secondDirection, weapons)){
            gameController.moveAndReload(users.get(username), firstDirection, secondDirection, weapons);
        }
    }

    /**
     * Takes the parameters to use a powerup and calls the relative {@link GameController} method and sends to the other players the result of the action.
     * @param username the username of the player.
     * @param powerup the powerup to use.
     * @param victim the victim of the powerup.
     * @param ammo the color of the ammo to discard.
     * @param x the square's abscissa.
     * @param y the square's ordinate.
     * @param directions the directions.
     */
    public void powerup(String username, String powerup, TokenColor victim, Color ammo, int x, int y, Direction...directions){
        if(gameController.usePowerup(powerup, users.get(username), users.get(colors.get(victim)), ammo, x, y, directions)){
            List<Player> victims = new ArrayList<>();
            switch(powerup){
                case TARGETING_SCOPE:
                case TAGBACK_GRENADE:
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

    /**
     * Resets the ammos given by powerup.
     * @param username the username of the player.
     */
    public void resetPowerupAmmos(String username){
        users.get(username).resetPowerupAmmos();
    }

    /**
     * Takes the name of the weapon to reload and calls the relative {@link GameController} method.
     * @param username the username of the player.
     * @param weaponName the name of the weapon to reload.
     */
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

    /**
     * Ends the turn of the player. If one of the players is dead it calls the {@link #deathAndRespawn()} method. If the final frenzy is end, stop the game.
     * @param username the username of the player.
     */
    public void endTurn(String username){
        if(users.get(username).isMyTurn()){
            for(Player player : players){
                if(player.isDead()){
                    deathAndRespawn();
                    return;
                }
            }
            if(finalFrenzy){
                finalFrenzyTurns++;
                if(finalFrenzyTurns == servers.size() + disconnectedUsers.size()){
                    endGame();
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
            gameController.endTurn(users.get(username));
            for(int i = 0; i< players.size(); i++){
                if(players.get(i).getUsername().equals(username)){
                    try {
                        if(servers.containsKey(username) && !players.get(i).isDisconnected()){
                            setGameData();
                            servers.get(username).notify(Message.END_TURN, Outcome.RIGHT, gameData);
                        }
                        int index = nextPlayerIndex(i);
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

        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    /**
     * Ends the turn for disconnected players.
     * @param username the username of the player.
     */
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
            gameController.endTurn(users.get(username));
            for(int i = 0; i< players.size(); i++){
                if(players.get(i).getUsername().equals(username)){
                    try {
                        if(servers.containsKey(username) && !players.get(i).isDisconnected()){
                            setGameData();
                            servers.get(username).notify(Message.END_TURN, Outcome.RIGHT, gameData);
                        }
                        int index = nextPlayerIndex(i);
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

        }else{
            try {
                servers.get(username).notify(Message.NOT_TURN);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    /**
     * Finds the index of the next player.
     * @param index the current index.
     * @return the index of the next player.
     */
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

    /**
     * Set the score after a death and then notifies to the death players the choice for the respawn.
     */
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

    /**
     * Ends the game.
     */
    private void endGame(){
        Printer.println(GAME_END);
        gameController.endGame();
        Map<TokenColor, Integer> score = gameController.getScoreList();
        setGameData();
        gameData.setScoreList(score);
        servers.forEach((u,s)->{
            try {
                s.notify(Message.SCORE, Outcome.ALL, gameData);
            } catch (IOException e) {
                Printer.err(e);
            }
        });
    }

    /**
     * Sets the data to send to the players before the beginning of the game.
     */
    private void setGameDataBeforeGame(){
        gameData.setGame(gameController.getGame());
        gameData.setPlayers(users);
    }

    /**
     * Sets the data to send to the players.
     */
    private void setGameData(){
        gameData.setGame(gameController.getGame());
        gameData.setPlayers(users);
        gameData.setCurrentPlayer(gameController.getGame().getCurrentPlayer().getUsername());
    }

    /**
     * Creates players with some features for the debug.
     * @param player the player.
     * @param username the username of the player.
     */
    private void debugMode(Player player, String username){
        if(username.equals(DEBUG_1)){
            player.getPlayerBoard().addDamage(TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY,
                    TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY);
        }
        if(username.equals(DEBUG_2)){
            player.getPlayerBoard().addDamage(TokenColor.GREEN, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY, TokenColor.GREY,
                    TokenColor.GREY, TokenColor.GREEN, TokenColor.GREEN, TokenColor.GREEN);
        }
    }
}
