package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

public class SpawnLocationTimer extends Thread {
    private ServerController serverController;
    private GameController gameController;
    public SpawnLocationTimer(ServerController serverController, GameController gameController){
        this.serverController = serverController;
        this.gameController = gameController;
    }

    @Override
    public void run(){
        try {
            sleep(Config.SPAWN_LOCATION_TIME);
            if(gameController.isSpawnLocationPhase()){
                serverController.randomChoice();
            }
        } catch (InterruptedException e) {
            Printer.err(e);
        }
    }
}
