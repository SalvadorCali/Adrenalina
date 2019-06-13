package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Game;
import org.junit.jupiter.api.Test;


public class KillshotTrackCLITest {
    @Test
    public void printTest(){
        GameController gameController = new GameController();
        Game game = gameController.getGame();
        game.createKillshotTrack(2);
        KillshotTrackCLI killshotTrackCLI = new KillshotTrackCLI(game.getKillshotTrack());
        killshotTrackCLI.printKillshotTrack();

        game.getKillshotTrack().get(0).setFirstColor(TokenColor.BLUE);
        //game.getKillshotTrack().get(3).setFirstColor(TokenColor.YELLOW);
        //game.getKillshotTrack().get(6).setFirstColor(TokenColor.PURPLE);
        //game.getKillshotTrack().get(6).setSecondColor(TokenColor.GREEN);
        killshotTrackCLI.setKillshotTrack(game.getKillshotTrack());
        killshotTrackCLI.printKillshotTrack();
    }
}
