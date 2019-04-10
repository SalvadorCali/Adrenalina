package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.PlayerBoard;
import it.polimi.ingsw.util.Printer;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GameTest {
    @Test
    public void scoringTest(){
        Game game = createGame();
        game.scoring();
        Map<TokenColor, Integer> scoreList = game.getScoreList();
        assertEquals(21, scoreList.get(TokenColor.BLUE).intValue());
        assertEquals(16, scoreList.get(TokenColor.YELLOW).intValue());
        assertEquals(15, scoreList.get(TokenColor.GREY).intValue());
        assertEquals(0, scoreList.get(TokenColor.PURPLE).intValue());
    }

    public Game createGame(){
        GameController gameController = new GameController();
        Game game = gameController.getGame();
        Player player = new Player(TokenColor.BLUE);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.YELLOW);
        game.addPlayer(player);

        player = new Player(TokenColor.YELLOW);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.GREY,
                TokenColor.GREY, TokenColor.BLUE, TokenColor.GREY,
                TokenColor.GREY, TokenColor.BLUE, TokenColor.GREY,
                TokenColor.GREY, TokenColor.BLUE, TokenColor.GREY);
        game.addPlayer(player);

        player = new Player(TokenColor.GREY);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW);
        game.addPlayer(player);

        player = new Player(TokenColor.PURPLE);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.GREY, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.GREY, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.GREY, TokenColor.YELLOW);
        game.addPlayer(player);

        game.createScoreList();
        return game;
    }
}
