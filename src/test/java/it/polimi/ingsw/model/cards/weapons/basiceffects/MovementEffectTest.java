package it.polimi.ingsw.model.cards.weapons.basiceffects;

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
    private ClientData clientData = gameController.getActionInterface().getClientData();
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

    @Test
    void tractorBeamMod2Test(){
        playerSetup();
        Effect tractorBeamMod2 = new MovementEffect("Tractor Beam2", 1, 0,0,2,0);

        clientData.setFirstMove(Direction.UP);
        assertFalse(tractorBeamMod2.canUseEffect(gameController.getActionInterface()));
        clientData.setSecondMove(Direction.RIGHT);
        assertFalse(tractorBeamMod2.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(0,1, victim);
        clientData.setFirstMove(null);
        clientData.setSecondMove(null);
        assertFalse(tractorBeamMod2.canUseEffect(gameController.getActionInterface()));
        clientData.setFirstMove(Direction.LEFT);
        assertTrue(tractorBeamMod2.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(0,2, victim);
        clientData.setSecondMove(Direction.LEFT);
        assertTrue(tractorBeamMod2.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void powerGloveMod1Test(){

        playerSetup();
        Effect powerGloveMod1 = new MovementEffect("Power Glove1", 1, 2,0,2,0);

        gameController.getActionInterface().move(0,1, victim);
        assertTrue(powerGloveMod1.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(1,0, victim);
        assertTrue(powerGloveMod1.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(1,1, victim);
        assertFalse(powerGloveMod1.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void rocketLauncherTest(){

        playerSetup();
        Effect rocketLauncher = new MovementEffect("Rocket Launcher", 2,0,0,2,0 );

        gameController.getActionInterface().move(0,1, victim);
        assertTrue(rocketLauncher.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().move(1,0, victim);
        assertTrue(rocketLauncher.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().move(0,0, victim);
        assertFalse(rocketLauncher.canUseEffect(gameController.getActionInterface()));

    }

    @Test
    void shotgunMod1Test(){

        playerSetup();
        Effect shotgun1 = new MovementEffect("Shotgun1", 3,0,0,2,0 );

        gameController.getActionInterface().move(0,0, victim);
        assertTrue(shotgun1.canUseEffect(gameController.getActionInterface()));
        clientData.setFirstMove(Direction.DOWN);
        assertTrue(shotgun1.canUseEffect(gameController.getActionInterface()));
        clientData.setSecondMove(Direction.DOWN);
        assertFalse(shotgun1.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(0,1, victim);
        assertFalse(shotgun1.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void shotgunMod2Test(){

        playerSetup();
        Effect shotgun2 = new MovementEffect("Shotgun2", 2,0,0,2,0 );

        gameController.getActionInterface().move(0,1, victim);
        assertTrue(shotgun2.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(1,0, victim);
        assertTrue(shotgun2.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(0,0, victim);
        assertFalse(shotgun2.canUseEffect(gameController.getActionInterface()));
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
