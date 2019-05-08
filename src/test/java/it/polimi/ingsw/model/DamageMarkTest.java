package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class DamageMarkTest {

    private GameController gameController = new GameController();


    @Test
    public void lockRifleBasicTest() {

        ClientData clientData = gameController.getActionInterface().getClientData();

        //currentPlayerSetup
        Player currentPlayer = new Player(TokenColor.GREEN);
        gameController.getGame().getBoard().generatePlayer(0,0,currentPlayer);
        gameController.getGame().getPlayers().add(currentPlayer);
        clientData.setCurrentPlayer(currentPlayer);
        currentPlayer.increaseAmmoNumber(Color.BLUE);
        currentPlayer.increaseAmmoNumber(Color.BLUE);

        //victimSetup
        Player victim = new Player(TokenColor.BLUE);
        gameController.getGame().getBoard().generatePlayer(1,0,victim);
        gameController.getGame().getPlayers().add(victim);
        clientData.setVictim(victim);


        Effect lockRifle = new DamageMarkEffect("Lock Rifle", 2, 1, 0, 2, 0);
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,1,currentPlayer);
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,2,currentPlayer);
        assertFalse(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,0,currentPlayer);
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,0,currentPlayer);
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,1,currentPlayer);
        assertFalse(lockRifle.canUseEffect(gameController.getActionInterface()));
    }
}
