package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.cli.MapCLI;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private List<Player> playerList = new ArrayList<>();

    /**
     * Tests that players, ammo and gameboard are correctly setted at the beginning of a game.
     */
    @Test
    void startGameTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        for(int i = 0 ; i < gameController.getGame().getPlayers().size(); i++){
            assertEquals(3,gameController.getGame().getPlayers().get(i).getAmmoBox().size());
            assertEquals(6,gameController.getGame().getPlayers().get(i).getAmmoReserve().size());
            assertEquals(playerList.get(i).getUsername(), gameController.getGame().getPlayers().get(i).getUsername());
        }
        assertNotNull(gameController.getGame().getBoard());
        assertNotNull(gameController.getGame().getScoreList());
    }

    /**
     * Tests that the board and the killshot tracks are correctly setted when the first player makes his choice.
     */
    @Test
    void setBoardTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.setBoard(1, 5);
        assertEquals(BoardType.BASIC, gameController.getGame().getBoard().getType());
        assertEquals(5, gameController.getGame().getKillshotTrack().size());
    }

    /**
     * Tests that the player can move only 2 times in a normal turn, and 1 time in a final frenzy turn.
     */
    @Test
    void moveTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().setFinalFrenzy(false);
        gameController.getGame().getCurrentPlayer().resetActionNumber();
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().generatePlayer(0,0, gameController.getGame().getCurrentPlayer());
        mapCLI.printMap();
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT,Direction.RIGHT, Direction.DOWN));
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.RIGHT));
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT));
        gameController.move(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.RIGHT, Direction.DOWN);
        mapCLI.printMap();
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.DOWN));
        gameController.move(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.DOWN);
        mapCLI.printMap();
        assertFalse(gameController.move(gameController.getGame().getCurrentPlayer(), Direction.LEFT));
        gameController.getGame().setFinalFrenzy(true);
        assertFalse(gameController.move(gameController.getGame().getCurrentPlayer(),Direction.UP));
    }



    private void playerSetup(){
        Player player1 = new Player(TokenColor.BLUE);
        player1.setUsername("A");
        Player player2 = new Player(TokenColor.GREEN);
        player2.setUsername("B");
        Player player3 = new Player(TokenColor.YELLOW);
        player3.setUsername("C");
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
    }
}
