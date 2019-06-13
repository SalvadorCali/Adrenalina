package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

public class RespawnTimer extends Thread{
    private ServerController serverController;
    private GameController gameController;
    private Player player;
    public RespawnTimer(ServerController serverController, GameController gameController, Player player){
        this.serverController = serverController;
        this.gameController = gameController;
        this.player = player;
    }

    @Override
    public void run(){
        try {
            sleep(Config.RESPAWN_TIME);
            if(gameController.isRespawnPhase() && !player.isRespawned()){
                serverController.respawn(player.getUsername(), 1);
            }
        } catch (InterruptedException e) {
            Printer.err(e);
        }
    }
}
