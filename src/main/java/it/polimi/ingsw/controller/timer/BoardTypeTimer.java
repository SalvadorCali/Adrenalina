package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

/**
 * This class represents a timer for the choice of the board.
 */
public class BoardTypeTimer extends Thread {
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
    public BoardTypeTimer(ServerController serverController, GameController gameController){
        this.serverController = serverController;
        this.gameController = gameController;
    }

    /**
     * Sleeps for a time and the user didn't make his choice calls the {@link ServerController#chooseBoardType(int, int)} method.
     */
    @Override
    public void run(){
        try {
            sleep(Config.BOARD_TYPE_TIME);
            if(gameController.isBoardTypePhase()){
                serverController.chooseBoardType(1, 1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
