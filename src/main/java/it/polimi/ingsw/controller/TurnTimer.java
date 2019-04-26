package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class TurnTimer extends Thread {
    private ServerController serverController;
    private GameController gameController;
    private List<Player> players;

    public TurnTimer(ServerController serverController, GameController gameController, List<Player> players){
        this.serverController = serverController;
        this.gameController = gameController;
        this.players = players;
    }

    @Override
    public void run() {
        while(gameController.isInGame()){
            for(int i = 0; i<players.size(); i++){
                players.get(i).setMyTurn(true);
                try {
                    sleep(Config.TURN_TIME);
                    players.get(i).setMyTurn(false);
                    serverController.endTurn(players.get(i).getUsername());
                    if(i == players.size() - 1){
                        i = -1;
                    }
                } catch (InterruptedException e) {
                    Printer.err(e);
                    Thread.currentThread().interrupt(); //to handle
                }
            }
        }
    }
}