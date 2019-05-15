package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.util.List;

public class TurnTimer extends Thread {
    private ServerController serverController;
    private GameController gameController;
    private List<Player> players;
    private Player player;

    public TurnTimer(ServerController serverController, List<Player> players, Player player){
        this.serverController = serverController;
        this.players = players;
        this.player = player;
    }

    @Override
    public void run() {
        try {
            sleep(Config.TURN_TIME);
            if(player.isMyTurn()){
                serverController.endTurn(player.getUsername());
            }
        } catch (InterruptedException e) {
            Printer.err(e);
            Thread.currentThread().interrupt();
        }
        /*
        while(gameController.isGamePhase()){
            for(int i = 0; i<players.size(); i++){
                players.get(i).setMyTurn(true);
                try {
                    sleep(Config.TURN_TIME);
                    if(players.get(i).isMyTurn()){
                        serverController.endTurn(players.get(i).getUsername());
                    }

                    if(i == players.size() - 1){
                        i = -1;
                    }
                } catch (InterruptedException e) {
                    Printer.err(e);
                    Thread.currentThread().interrupt(); //to handle
                }
            }
        }
        */
    }
}