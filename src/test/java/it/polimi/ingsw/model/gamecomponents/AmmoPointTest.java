package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests referring to the AmmoPoint class.
 */
class AmmoPointTest {
    /**
     * Tests that the weapons getter in an Ammo point always returns null.
     */
    @Test
    void getWeaponsTest(){
        AmmoPoint square = new AmmoPoint(TokenColor.RED, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        assertNull(square.getWeapons());
    }
    /**
     * Tests that the ammo card getter returns the correct value.
     */
    @Test
    void getAmmoCardTest(){
        AmmoPoint square = new AmmoPoint(TokenColor.RED, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        AmmoCard ammoCard = new AmmoCard(new Ammo(Color.RED), new Ammo(Color.BLUE));
        square.setAmmoCard(ammoCard);
        assertEquals(ammoCard, square.getAmmoCard());
    }
    /**
     * Tests that a player can grab from an ammo point only if his choice is "0" and the ammo point isn't empty.
     */
    @Test
    void canGrabTest(){
        AmmoPoint square = new AmmoPoint(TokenColor.RED, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        AmmoCard ammoCard = new AmmoCard(new Ammo(Color.RED), new Ammo(Color.BLUE));
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        square.setAmmoCard(ammoCard);
        assertFalse(square.canGrab(actionInterface,-1));
        assertFalse(square.canGrab(actionInterface,1));
        assertTrue(square.canGrab(actionInterface,0));
        square.setEmpty(true);
        assertFalse(square.canGrab(actionInterface,0));
    }
    /**
     * Tests that if a player grabs from an ammo point, the correct ammos and powerups are added to his ammobox.
     */
    @Test
    void grabTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        Player player = new Player(TokenColor.BLUE);
        game.setCurrentPlayer(player);
        AmmoPoint square = new AmmoPoint(TokenColor.RED, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        AmmoCard ammoCard = new AmmoCard(new Ammo(Color.RED), new Ammo(Color.BLUE));
        square.setAmmoCard(ammoCard);
        square.grab(actionInterface, 0);
        assertEquals(Color.RED, player.getAmmoBox().get(0).getColor());
        assertEquals(Color.BLUE, player.getAmmoBox().get(1).getColor());
        assertEquals(1, player.getPowerups().size());
        AmmoCard ammoCard1 = new AmmoCard(new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.YELLOW));
        square.setAmmoCard(ammoCard1);
        square.grab(actionInterface, 0);
        assertEquals(Color.BLUE, player.getAmmoBox().get(2).getColor());
        assertEquals(Color.RED, player.getAmmoBox().get(3).getColor());
        assertEquals(Color.YELLOW, player.getAmmoBox().get(4).getColor());
    }
    /**
     * Tests that an ammo point is correctly refilled.
     */
    @Test
    void fillTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        AmmoPoint square = new AmmoPoint(TokenColor.BLUE, Cardinal.DOOR, Cardinal.ROOM, Cardinal.WALL, Cardinal.DOOR);
        assertNull(square.getAmmoCard());
        square.fill(actionInterface);
        assertNotNull(square.getAmmoCard());
    }
}
