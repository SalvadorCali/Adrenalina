package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

public class BoardTypeTimer extends Thread {
    private ServerController serverController;
    private GameController gameController;
    public BoardTypeTimer(ServerController serverController, GameController gameController){
        this.serverController = serverController;
        this.gameController = gameController;
    }

    @Override
    public void run(){
        try {
            sleep(Config.BOARD_TYPE_TIME);
            if(gameController.isBoardTypePhase()){
                serverController.chooseBoardType(1, 1);
            }
        } catch (InterruptedException e) {
            Printer.err(e);
        }
    }
}
