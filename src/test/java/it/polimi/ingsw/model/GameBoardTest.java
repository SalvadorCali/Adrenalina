package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class GameBoardTest {

    private List<GameBoard> gameBoards = Parser.createGameBoards();
    private Player player = new Player(TokenColor.GREEN);
    private Position position = new Position(1,1);

    @Test
    void correctSingleMoveTest(){
        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN));
    }

    @Test
    void correctDoubleMoveTest(){

        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT));
    }

    @Test
    void correctTripleMoveTest(){

        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT,Direction.RIGHT ));
    }

    @Test
    void correctQuadrupleMoveTest(){

        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT,Direction.RIGHT, Direction.UP ));
    }

    @Test
    void firstMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.RIGHT));
    }

    @Test
    void doubleMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.DOWN));
    }

    @Test
    void tripleMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT, Direction.DOWN));
    }

    @Test
    void quadrupleMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT, Direction.UP,Direction.LEFT));
    }
}
