package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.*;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class AmmoPointTest {
    @Test
    public void grabTest(){
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
    }

    @Test
    public void fillTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        AmmoPoint square = new AmmoPoint(TokenColor.BLUE, Cardinal.DOOR, Cardinal.ROOM, Cardinal.WALL, Cardinal.DOOR);
        assertNull(square.getAmmoCard());

        square.fill(actionInterface);
        assertNotNull(square.getAmmoCard());
    }
}
