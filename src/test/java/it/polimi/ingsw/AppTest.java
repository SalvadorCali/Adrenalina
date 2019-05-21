package it.polimi.ingsw;


import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.AmmoBoxReserveCLI;
import it.polimi.ingsw.view.GUI.LoginGUI;
import it.polimi.ingsw.view.MapCLI;
import javafx.application.Application;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
        Player player = new Player(TokenColor.GREY);
        Player player2 = new Player(TokenColor.YELLOW);
        GameController gameController = new GameController();
        gameController.getGame().getBoard().setPlayer(player, Color.BLUE);
        gameController.getGame().getBoard().setPlayer(player2, Color.BLUE);
        MapCLI map = new MapCLI(gameController.getGame().getBoard());
        map.printMap();
    }

}
