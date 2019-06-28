package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

/**
 * This class represents a timer for the first spawn.
 */
public class SpawnLocationTimer extends Thread {
    /**
     * The ServerController that handles the game.
     */
    private ServerController serverController;
    /**
     * The GameController of this game.
     */
    private GameController gameController;

    /**
     * Class constructor.
     * @param serverController the ServerController that handles the game.
     * @param gameController the GameController of this game.
     */
    public SpawnLocationTimer(ServerController serverController, GameController gameController){
        this.serverController = serverController;
        this.gameController = gameController;
    }

    /**
     * Sleeps for a time, then if the game is in the spawn location phase, calls the {@link ServerController#randomChoice()} method.
     */
    @Override
    public void run(){
        try {
            sleep(Config.SPAWN_LOCATION_TIME);
            if(gameController.isSpawnLocationPhase()){
                serverController.randomChoice();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
