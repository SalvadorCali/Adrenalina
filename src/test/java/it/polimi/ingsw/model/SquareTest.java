package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gamecomponents.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class SquareTest {

    Square square = new SpawnPoint(TokenColor.BLUE, Cardinal.ROOM, Cardinal.DOOR, Cardinal.WALL, Cardinal.NONE);
    Square square2 = new AmmoPoint(TokenColor.YELLOW, Cardinal.DOOR, Cardinal.ROOM, Cardinal.NONE, Cardinal.WALL);
    Square square3 = new SpawnPoint(TokenColor.GREEN, Cardinal.WALL, Cardinal.NONE, Cardinal.DOOR, Cardinal.ROOM);
    Square square4 = new AmmoPoint(TokenColor.GREY, Cardinal.NONE, Cardinal.WALL, Cardinal.ROOM, Cardinal.DOOR);
    Position position = new Position(1,1);
    Player player = new Player(TokenColor.GREY);
    Player player2 = new Player(TokenColor.YELLOW);

    @Test
    void canMoveTest(){

        assertTrue(square.canMove(Direction.UP));
        assertTrue(square.canMove(Direction.DOWN));
        assertFalse(square.canMove(Direction.RIGHT));
        assertFalse(square.canMove(Direction.LEFT));

        assertTrue(square2.canMove(Direction.UP));
        assertTrue(square2.canMove(Direction.DOWN));
        assertFalse(square2.canMove(Direction.RIGHT));
        assertFalse(square2.canMove(Direction.LEFT));

        assertFalse(square3.canMove(Direction.UP));
        assertFalse(square3.canMove(Direction.DOWN));
        assertTrue(square3.canMove(Direction.RIGHT));
        assertTrue(square3.canMove(Direction.LEFT));

        assertFalse(square4.canMove(Direction.UP));
        assertFalse(square4.canMove(Direction.DOWN));
        assertTrue(square4.canMove(Direction.RIGHT));
        assertTrue(square4.canMove(Direction.LEFT));

    }

    @Test
    void moveTest(){

        player.setPosition(position);
        square.canMove(Direction.UP);
        square.move(player);
        assertTrue(square.getPlayers().contains(player));
    }

    @Test
    void moveAwayTest(){

        square.getPlayers().add(player);
        square.getPlayers().add(player2);
        square.moveAway(player);
        assertFalse(square.getPlayers().contains(player));
        assertTrue(square.getPlayers().contains(player2));

    }
}

