package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.util.List;

/**
 * This class represents a timer for each player's turn.
 */
public class TurnTimer extends Thread {
    /**
     * The ServerController that handles the game.
     */
    private ServerController serverController;
    /**
     * The players of the game.
     */
    private List<Player> players;
    /**
     * The current player.
     */
    private Player player;

    /**
     * Class constructor.
     * @param serverController the ServerController that handles the game.
     * @param players the players of the game.
     * @param player the current player.
     */
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
            Thread.currentThread().interrupt();
        }
    }
}