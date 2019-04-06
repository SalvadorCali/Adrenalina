package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.AmmoPoint;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class SpawnPointTest {
    /*
    @Test
    public void grabTest(){
        Game game = new Game(Parser.createGameBoards().get(2), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        Player player = new Player(TokenColor.YELLOW);
        game.setCurrentPlayer(player);
        AmmoPoint square = new AmmoPoint(TokenColor.BLUE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        AmmoCard ammoCard = new AmmoCard(new Ammo(Color.RED), new Ammo(Color.BLUE));
        square.setAmmoCard(ammoCard);
        square.grab(actionInterface, 0);

        assertEquals(Color.RED, player.getAmmoBox().get(0).getColor());
        assertEquals(Color.BLUE, player.getAmmoBox().get(1).getColor());
        assertEquals(1, player.getPowerups().size());
    }
    */
}
