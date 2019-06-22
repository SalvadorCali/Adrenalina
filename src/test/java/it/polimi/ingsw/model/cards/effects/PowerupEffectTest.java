package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.powerups.PowerupEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.cli.DamageBoardCLI;
import it.polimi.ingsw.view.cli.MapCLI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests referring to the PowerupEffect class.
 */
class PowerupEffectTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);

    /**
     * Tests the controls of the newton powerup.
     */
    @Test
    void newtonCanUseTest(){
        playerSetup();
        Effect newton = new PowerupEffect("Newton");
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().move(0,0,victim);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.DOWN);
        gameController.getActionInterface().getClientData().setSecondMove(Direction.DOWN);
        assertTrue(newton.canUseEffect(gameController.getActionInterface()));
        newton.useEffect(gameController.getActionInterface());
        gameController.getGame().getBoard().move(1,0,victim);
        mapCLI.printMap();
        assertFalse(newton.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().getClientData().setSecondMove(null);
        assertTrue(newton.canUseEffect(gameController.getActionInterface()));
        newton.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        gameController.getGame().getBoard().move(0,0,victim);
        gameController.getActionInterface().getClientData().setSecondMove(Direction.UP);
        assertFalse(newton.canUseEffect(gameController.getActionInterface()));
    }

    /**
     * Tests the controls of the teleporter powerup.
     */
    @Test
    void teleporterCanUseTest(){
        playerSetup();
        Effect teleporter = new PowerupEffect("Teleporter");
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        gameController.getActionInterface().getClientData().setSquare(1,2);
        assertTrue(teleporter.canUseEffect(gameController.getActionInterface()));
        teleporter.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        gameController.getActionInterface().getClientData().setSquare(0,3);
        assertFalse(teleporter.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().getClientData().setSquare(0,4);
        assertFalse(teleporter.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().getClientData().setSquare(3,0);
        assertFalse(teleporter.canUseEffect(gameController.getActionInterface()));
    }

    /**
     * Tests the controls of the targeting scope powerup.
     */
    @Test
    void targetingScopeCanUseTest(){
        playerSetup();
        Effect targetingScope = new PowerupEffect("Targeting Scope");
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertFalse(targetingScope.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().playerDamage(victim,1);
        gameController.getActionInterface().getClientData().setAmmoColor(Color.BLUE);
        assertTrue(targetingScope.canUseEffect(gameController.getActionInterface()));
        DamageBoardCLI dbc = new DamageBoardCLI(victim);
        dbc.printDamageBoard();
        targetingScope.useEffect(gameController.getActionInterface());
        dbc.printDamageBoard();
    }

    /**
     * Setups the components for the tests.
     */
    void playerSetup(){

        ClientData clientData = gameController.getActionInterface().getClientData();

        //currentPlayerSetup
        gameController.getGame().getBoard().generatePlayer(0,0,currentPlayer);
        gameController.getGame().getPlayers().add(currentPlayer);
        clientData.setCurrentPlayer(currentPlayer);
        gameController.getGame().setCurrentPlayer(currentPlayer);
        currentPlayer.addAmmo(new Ammo(Color.BLUE), new Ammo(Color.BLUE));

        //victimSetup
        gameController.getGame().getBoard().generatePlayer(1,0,victim);
        gameController.getGame().getPlayers().add(victim);
        clientData.setPowerupVictim(victim);


    }
}
