package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.MovementEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;

public class MovementEffectTest {

    private GameController gameController = new GameController();
    ClientData clientData = gameController.getActionInterface().getClientData();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.PURPLE);


    @Test
    void tractorBeamMod1Test(){

        playerSetup();
        Effect tractorBeamMod1 = new MovementEffect("Tractor Beam1", 1, 0,0,2,0);

        clientData.setFirstMove(Direction.UP);
        assertTrue(tractorBeamMod1.canUseEffect(gameController.getActionInterface()));
        clientData.setSecondMove(Direction.RIGHT);
        assertTrue(tractorBeamMod1.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(0,1, victim);
        clientData.setFirstMove(null);
        clientData.setSecondMove(null);
        assertTrue(tractorBeamMod1.canUseEffect(gameController.getActionInterface()));
    }












    void playerSetup(){

        //currentPlayerSetup
        gameController.getGame().getBoard().generatePlayer(0,0,currentPlayer);
        gameController.getGame().getPlayers().add(currentPlayer);
        clientData.setCurrentPlayer(currentPlayer);
        currentPlayer.increaseAmmoNumber(Color.BLUE);
        currentPlayer.increaseAmmoNumber(Color.BLUE);

        //victimSetup
        gameController.getGame().getBoard().generatePlayer(1,1,victim);
        gameController.getGame().getPlayers().add(victim);
        clientData.setVictim(victim);

        //secondVictimSetup
        gameController.getGame().getBoard().generatePlayer(0,1,secondVictim);
        gameController.getGame().getPlayers().add(secondVictim);
        clientData.setSecondVictim(secondVictim);

    }

}
