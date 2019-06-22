package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.view.cli.MapCLI;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests referring to the GameBoard class.
 */
class GameBoardTest {

    private List<GameBoard> gameBoards = Parser.createGameBoards();
    private Player player = new Player(TokenColor.GREEN);
    private Position position = new Position(1,1);
    private Player victim = new Player(TokenColor.GREY);

    /**
     * Tests the correct move of a player on the board.
     */
    @Test
    void correctSingleMoveTest(){
        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN));
    }

    /**
     * Tests the correct double move of a player on the board.
     */
    @Test
    void correctDoubleMoveTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT));
    }

    /**
     * Tests the correct triple move of a player on the board.
     */
    @Test
    void correctTripleMoveTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT,Direction.RIGHT ));
    }

    /**
     * Tests the correct quadruple move of a player on the board.
     */
    @Test
    void correctQuadrupleMoveTest(){
        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT,Direction.RIGHT, Direction.UP ));
    }

    /**
     * Tests the wrong move of a player on the board.
     */
    @Test
    void firstMoveErrorTest(){
        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.RIGHT));
    }

    /**
     * Tests the wrong double move of a player on the board.
     */
    @Test
    void doubleMoveErrorTest(){
        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.DOWN));
    }

    /**
     * Tests the wrong triple move of a player on the board.
     */
    @Test
    void tripleMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT, Direction.DOWN));
    }

    /**
     * Tests the wrong quadruple move of a player on the board.
     */
    @Test
    void quadrupleMoveErrorTest(){
        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT, Direction.UP,Direction.LEFT));
    }

    /**
     * Tests the visibility of a victim in the same room of the shooter.
     */
    @Test
    void isVisibleSameRoomTest(){
        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        victim.setPosition(position);
        assertTrue(gameBoard.isVisible(player, victim));
    }

    /**
     * Tests the visibility of a victim throughout the board.
     */
    @Test
    void isVisibleTest(){

        GameBoard gameBoard = gameBoards.get(0);
        Player shooter = new Player(TokenColor.PURPLE);
        Player victim = new Player(TokenColor.BLUE);
        gameBoard.generatePlayer(0,0, shooter);
        gameBoard.generatePlayer(1,0, victim);

        assertTrue(gameBoard.isVisible(shooter, victim));

        gameBoard.move(0,1, shooter);
        System.out.print(shooter.getPosition().getX());
        System.out.println(shooter.getPosition().getY());
        System.out.print(victim.getPosition().getX());
        System.out.println(victim.getPosition().getY());
        System.out.println(gameBoard.isVisible(shooter, victim));
        assertTrue(gameBoard.isVisible(shooter, victim));

        gameBoard.move(0,2, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

        gameBoard.move(0,3, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

        gameBoard.move(1, 0, shooter);
        assertTrue(gameBoard.isVisible(shooter, victim));

        gameBoard.move(1,1, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

        gameBoard.move(1,2, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

        gameBoard.move(1,3, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

        gameBoard.move(2, 0, shooter);
        assertTrue(gameBoard.isVisible(shooter, victim));

        gameBoard.move(2,1, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

        gameBoard.move(2,2, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

        gameBoard.move(2,3, shooter);
        assertFalse(gameBoard.isVisible(shooter, victim));

    }

    /**
     * Tests the visibility of a victim who isn't in the same room of the shooter.
     */
    @Test
    void isVisibleThroughDoorTest(){
        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        victim.setPosition(new Position(2, 2));
        assertTrue(gameBoard.isVisible(player, victim));

        player.setPosition(new Position(1,0));
        victim.setPosition(new Position(2, 2));
        assertTrue(gameBoard.isVisible(player, victim));
    }

    /**
     * Tests the non-visibility of a victim.
     */
    @Test
    void isVisibleErrorTest(){
        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        Position victimPosition = new Position(0, 0);
        victim.setPosition(victimPosition);
        assertFalse(gameBoard.isVisible(player, victim));

        player.setPosition(new Position(0,0));
        victim.setPosition(new Position(2, 3));
        assertFalse(gameBoard.isVisible(player, victim));
    }

    /**
     * Tests that a player is correctly removed from the board.
     */
    @Test
    void removePlayerTest(){
        Parser.createGameBoards();
        GameBoard gameBoard = gameBoards.get(0);
        MapCLI mapCLI = new MapCLI(gameBoard);
        Player player = new Player(TokenColor.BLUE);
        gameBoard.generatePlayer(0,0,player);
        mapCLI.printMap();
        gameBoard.getArena()[player.getPosition().getX()][player.getPosition().getY()].getPlayers().remove(player);
        gameBoard.generatePlayer(0,0, player);
        gameBoard.move(Direction.RIGHT, player);
        mapCLI.printMap();
    }
}
