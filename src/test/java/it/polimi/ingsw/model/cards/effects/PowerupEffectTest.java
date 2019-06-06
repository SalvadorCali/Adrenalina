package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.powerups.PowerupEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PowerupEffectTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);

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
