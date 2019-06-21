package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests referring to the class SpawnPoint.
 */
class SpawnPointTest {
    /**
     * Tests that a player can grab a weapon in the spawn point only if its choice stays between 1 and 3.
     */
    @Test
    void canGrabTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        Player player = new Player(TokenColor.YELLOW);
        player.addAmmo(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        game.setCurrentPlayer(player);
        Deck weaponCards = Parser.createWeapons();
        List<WeaponCard> weapons = new ArrayList<>();
        weapons.add((WeaponCard)weaponCards.get(0));
        weapons.add((WeaponCard)weaponCards.get(1));
        weapons.add((WeaponCard)weaponCards.get(2));
        WeaponCard weaponCard = weapons.get(1);
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        square.setWeapons(weapons);
        assertTrue(square.canGrab(actionInterface, 1));
        assertTrue(square.canGrab(actionInterface, 2));
        assertTrue(square.canGrab(actionInterface,3));
        assertFalse(square.canGrab(actionInterface, 4));
        assertFalse(square.canGrab(actionInterface, 0));

    }
    /**
     * Tests that if a player decides to grab, the chosen weapon card is removed from the spawn point and added to the player's weapons.
     */
    @Test
    void grabTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        Player player = new Player(TokenColor.YELLOW);
        game.setCurrentPlayer(player);
        Deck weaponCards = Parser.createWeapons();
        List<WeaponCard> weapons = new ArrayList<>();
        weapons.add((WeaponCard)weaponCards.get(0));
        weapons.add((WeaponCard)weaponCards.get(1));
        weapons.add((WeaponCard)weaponCards.get(2));
        WeaponCard weaponCard = weapons.get(1);
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        square.setWeapons(weapons);
        square.grab(actionInterface, 2);
        assertEquals(weaponCard, player.getWeapons().get(0));
    }
    /**
     * Tests the refill of the spawn point with new random weapons.
     */
    @Test
    void fillTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.DOOR, Cardinal.ROOM, Cardinal.WALL, Cardinal.DOOR);
        assertEquals(0, square.getWeapons().size());

        square.fill(actionInterface);
        assertEquals(3, square.getWeapons().size());
    }
    /**
     * Tests that if a player decides to drop a weapon, the weapon is added to the list of weapons present in the spawn point.
     */
    @Test
    void dropWeaponTest(){
        Deck weaponCards = Parser.createWeapons();
        List<WeaponCard> weapons = new ArrayList<>();
        weapons.add((WeaponCard)weaponCards.get(0));
        weapons.add((WeaponCard)weaponCards.get(1));
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        square.setWeapons(weapons);
        assertEquals(weaponCards.get(0), square.getWeapons().get(0));
        assertEquals(weaponCards.get(1), square.getWeapons().get(1));
        assertEquals(2, square.getWeapons().size());
        square.drop(weaponCards.get(2));
        assertEquals(weaponCards.get(2), square.getWeapons().get(2));
        assertEquals(3, square.getWeapons().size());
    }
    /**
     * Tests that the getter of an ammo card in a spawn point always returns null.
     */
    @Test
    void getAmmoCardTest(){
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        assertNull(square.getAmmoCard());
    }
    /**
     * Tests that the spawn point is an active point of the board.
     */
    @Test
    void isActiveTest(){
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        assertTrue(square.isActive());
    }
}
