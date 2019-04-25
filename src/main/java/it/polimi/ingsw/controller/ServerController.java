package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.server.ServerInterface;
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
    private List<Player> playersList;
    private Map<TokenColor, String> colors;
    private Map<String, Player> players;

    public ServerController(){
        gameController = new GameController();
        servers = new HashMap<>();
        colors = new HashMap<>();
        players = new HashMap<>();
        playersList = new ArrayList<>();
    }

    public void login(String username, TokenColor color, ServerInterface server){
        if(!gameController.isInGame()){
            boolean newUsername = !servers.containsKey(username);
            boolean newColor = !colors.containsKey(color);
            if(newUsername && newColor){
                addPlayer(username, color, server);
                if(servers.size() == 3){
                    ScheduledExecutorService createGame = Executors.newSingleThreadScheduledExecutor();
                    Runnable createGameTask = this::startGame;
                    createGame.schedule(createGameTask, 10000, TimeUnit.MILLISECONDS);
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
            //handle this
            try {
                server.notify(Message.LOGIN);
            } catch (IOException e) {
                Printer.err(e);
            }
            Printer.println("Game iniziato!");
        }
    }

    private void addPlayer(String username, TokenColor color, ServerInterface server){
        servers.put(username, server);
        colors.put(color, username);
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
        colors.forEach((c, u) -> {
            try {
                Player player = new Player(c);
                player.setUsername(u);
                playersList.add(player);
                servers.get(u).notify(Message.PLAYER, Outcome.RIGHT, player);
                players.put(colors.get(c), player);
                gameController.getGame().addPlayerColors(c);
                gameController.getGame().addPlayer((player));
            } catch (IOException e) {
                Printer.err(e);
            }
        });
        gameController.startGame();
        playersList.get(0).setMyTurn(true);
        try {
            servers.get(playersList.get(0).getUsername()).notify(Message.NEW_TURN);
        } catch (IOException e) {
            Printer.err(e);
        }
        TurnTimer timer = new TurnTimer(this, gameController, servers, players);
        timer.start();
        Printer.println("Game iniziato!");
    }

    public void move(String username, Direction...directions){
        if(gameController.canMove(players.get(username), directions)){
            gameController.move(players.get(username), directions);
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
        gameController.grab(players.get(username), choice, directions);
    }

    public void endTurn(String username){
        for(int i=0; i<playersList.size(); i++){
            if(playersList.get(i).getUsername().equals(username)){
                try {
                    servers.get(username).notify(Message.END_TURN);
                    if(i==playersList.size()-1){
                        servers.get(playersList.get(0).getUsername()).notify(Message.NEW_TURN);
                    }else{
                        servers.get(playersList.get(i+1).getUsername()).notify(Message.NEW_TURN);
                    }
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }


        gameController.endTurn(players.get(username));
        try {
            servers.get(username).notify(Message.END_TURN, Outcome.RIGHT);
        } catch (IOException e) {
            Printer.err(e);
        }
    }
}
