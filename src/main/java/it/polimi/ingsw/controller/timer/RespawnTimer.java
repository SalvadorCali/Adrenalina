package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

/**
 * This class represents a timer for the respawn.
 */
public class RespawnTimer extends Thread{
    /**
     * The ServerController that handles the game.
     */
    private ServerController serverController;
    /**
     * The GameController of this game.
     */
    private GameController gameController;
    /**
     * The player that will be respawn.
     */
    private Player player;

    /**
     * Class constructor.
     * @param serverController the ServerController that handles the game.
     * @param gameController the GameController of this game.
     * @param player the player that will be respawn.
     */
    public RespawnTimer(ServerController serverController, GameController gameController, Player player){
        this.serverController = serverController;
        this.gameController = gameController;
        this.player = player;
    }

    /**
     * Sleeps for a time and then, if the player didn't respawn, calls the {@link ServerController#respawn(String, int)} method.
     */
    @Override
    public void run(){
        try {
            sleep(Config.RESPAWN_TIME);
            if(gameController.isRespawnPhase() && !player.isRespawned()){
                serverController.respawn(player.getUsername(), 1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
