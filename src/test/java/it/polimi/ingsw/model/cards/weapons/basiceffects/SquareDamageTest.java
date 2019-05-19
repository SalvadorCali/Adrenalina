package it.polimi.ingsw.model.cards.weapons.basiceffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.SquareDamageEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;



public class SquareDamageTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);

    @Test
    void hellionCanUseEffectTest(){

        playerSetup();
        Effect hellion = new SquareDamageEffect("Hellion", 1, 1,0, 2,0);
        gameController.getGame().getBoard().move(1,1, currentPlayer);
        assertTrue(hellion.canUseEffect(gameController.getActionInterface()));
    }

    void playerSetup(){

        ClientData clientData = gameController.getActionInterface().getClientData();

        //currentPlayerSetup
        gameController.getGame().getBoard().generatePlayer(0,0,currentPlayer);
        gameController.getGame().getPlayers().add(currentPlayer);
        clientData.setCurrentPlayer(currentPlayer);
        currentPlayer.increaseAmmoNumber(Color.BLUE);
        currentPlayer.increaseAmmoNumber(Color.BLUE);

        //victimSetup
        gameController.getGame().getBoard().generatePlayer(1,0,victim);
        gameController.getGame().getPlayers().add(victim);
        clientData.setVictim(victim);

        //positionSetup
        clientData.setSquare(2, 1);

    }
}
