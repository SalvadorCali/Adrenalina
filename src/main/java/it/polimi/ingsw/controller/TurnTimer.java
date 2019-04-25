package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class TurnTimer extends Thread {
    private ServerController serverController;
    private GameController gameController;
    private Map<String, ServerInterface> servers;
    private List<Player> playersList;

    public TurnTimer(ServerController serverController, GameController gameController, Map<String, ServerInterface>servers, Map<String, Player> players){
        this.serverController = serverController;
        this.gameController = gameController;
        this.servers = servers;
        playersList = new ArrayList<>();
        for(Map.Entry<String, Player> entry : players.entrySet()){
            playersList.add(entry.getValue());
        }
    }

    @Override
    public void run() {
        while(gameController.isInGame()){
            for(int i = 0; i<playersList.size(); i++){
                playersList.get(i).setMyTurn(true);
                try {
                    sleep(30000);
                    playersList.get(i).setMyTurn(false);
                    serverController.endTurn(playersList.get(i).getUsername());
                    if(i == playersList.size() - 1){
                        i = -1;
                    }
                } catch (InterruptedException e) {
                    Printer.err(e);
                }
            }
        }
    }
}