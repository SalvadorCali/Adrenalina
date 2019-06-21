package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Printer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests referring to the Square class.
 */
class SquareTest {

    private Square square = new SpawnPoint(TokenColor.BLUE, Cardinal.ROOM, Cardinal.DOOR, Cardinal.WALL, Cardinal.NONE);
    private Square square2 = new AmmoPoint(TokenColor.YELLOW, Cardinal.DOOR, Cardinal.ROOM, Cardinal.NONE, Cardinal.WALL);
    private Square square3 = new SpawnPoint(TokenColor.GREEN, Cardinal.WALL, Cardinal.NONE, Cardinal.DOOR, Cardinal.ROOM);
    private Square square4 = new AmmoPoint(TokenColor.GREY, Cardinal.NONE, Cardinal.WALL, Cardinal.ROOM, Cardinal.DOOR);
    private Position position = new Position(1,1);
    private Player player = new Player(TokenColor.GREY);
    private Player player2 = new Player(TokenColor.YELLOW);

    /**
     * Tests the setters of the Square class.
     */
    @Test
    void squareSettersTest(){
        square.setSpawn(true);
        square.setColor(TokenColor.YELLOW);
        assertEquals(TokenColor.YELLOW, square.getColor());
        assertTrue(square.isSpawn());
    }

    /**
     * Tests the containsPlayers control.
     */
    @Test
    void containsPlayersTest(){
        Player player = new Player(TokenColor.BLUE);
        assertFalse(square.containsPlayer(player));
        square.addPlayer(player);
        assertTrue(square.containsPlayer(player));
    }

    /**
     * Tests if a player can move in all the different directions inside a square.
     */
    @Test
    void canMoveTest(){

        assertFalse(square.canMove(null));
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

    /**
     * Tests that if a player moves from the square, he's no longer contained in the square.
     */
    @Test
    void moveTest(){

        player.setPosition(position);
        square.canMove(Direction.UP);
        square.move(player);
        assertTrue(square.getPlayers().contains(player));
    }

    /**
     * Tests that when a player is moved away from the square, he's no longer contained in the player's list of the square.
     */
    @Test
    void moveAwayTest(){

        square.getPlayers().add(player);
        square.getPlayers().add(player2);
        square.moveAway(player);
        assertFalse(square.getPlayers().contains(player));
        assertTrue(square.getPlayers().contains(player2));

    }

    /**
     * Tests that the noOutOfBounds method always returns false only if the player is trying to move to an Inactive point.
     */
    @Test
    void noOutOfBoundsTest(){
        assertTrue(square.noOutofBounds(null));
        assertTrue(square.noOutofBounds(Direction.UP));
        assertTrue(square.noOutofBounds(Direction.DOWN));
        assertFalse(square.noOutofBounds(Direction.RIGHT));
        assertTrue(square.noOutofBounds(Direction.LEFT));
    }
}

