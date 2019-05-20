package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.*;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class GameBoardTest {

    private List<GameBoard> gameBoards = Parser.createGameBoards();
    private Player player = new Player(TokenColor.GREEN);
    private Position position = new Position(1,1);
    private Player victim = new Player(TokenColor.GREY);

    @Test
    void correctSingleMoveTest(){
        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN));
    }

    @Test
    void correctDoubleMoveTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT));
    }

    @Test
    void correctTripleMoveTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT,Direction.RIGHT ));
    }

    @Test
    void correctQuadrupleMoveTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertTrue(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT,Direction.RIGHT, Direction.UP ));
    }

    @Test
    void firstMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.RIGHT));
    }

    @Test
    void doubleMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.DOWN));
    }

    @Test
    void tripleMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT, Direction.DOWN));
    }

    @Test
    void quadrupleMoveErrorTest(){

        GameBoard gameBoard = gameBoards.get(3);
        player.setPosition(position);
        assertFalse(gameBoard.canMove(player, Direction.DOWN, Direction.RIGHT, Direction.UP,Direction.LEFT));
    }

    @Test
    void isVisibleSameRoomTest(){
        GameBoard gameBoard = gameBoards.get(0);
        player.setPosition(position);
        victim.setPosition(position);
        assertTrue(gameBoard.isVisible(player, victim));
    }

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
