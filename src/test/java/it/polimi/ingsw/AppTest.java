package it.polimi.ingsw;


import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void test2(){
        Map<String, Integer> players = new HashMap<>();
        players.put("a", 1);
        players.put("b", 2);
        players.put("c", 3);
        Map<String, Integer> playersWithoutUser = new HashMap<>(players);
        playersWithoutUser.remove("b", 2);
        playersWithoutUser.forEach((s,i)->Printer.println(i));
        players.forEach((s,i)->Printer.println(i));
    }
}
