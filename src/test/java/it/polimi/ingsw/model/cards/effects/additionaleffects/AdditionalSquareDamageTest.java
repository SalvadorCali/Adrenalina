package it.polimi.ingsw.model.cards.effects.additionaleffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.MovementEffect;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalMove;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalSquareDamage;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;

public class AdditionalSquareDamageTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.PURPLE);
    private Player thirdVictim = new Player(TokenColor.YELLOW);
    private MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());

    @Test
    void grenadeLauncherAddTest(){

        playerSetup();
        Effect grenadeLauncher = new MovementEffect("Grenade Launcher", 0, 0,0,2,0);
        Effect grenadeLauncherAdd = new AdditionalSquareDamage("Grenade Launcher",0,0,2,0, grenadeLauncher);
        mapCLI.printMap();

        gameController.getActionInterface().getClientData().setBasicFirst(true);
        gameController.getActionInterface().getClientData().setSquare(0,1);
        assertTrue(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().getClientData().setFirstMove(Direction.LEFT);
        assertFalse(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().getClientData().setFirstMove(Direction.DOWN);
        assertTrue(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().getClientData().setSquare(1,1);
        assertTrue(grenadeLauncher.canUseEffect(gameController.getActionInterface()));
        assertFalse(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().getClientData().setBasicFirst(false);
        assertFalse(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

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

        //secondVictimSetup
        gameController.getGame().getBoard().generatePlayer(0,1,secondVictim);
        gameController.getGame().getPlayers().add(secondVictim);
        clientData.setSecondVictim(secondVictim);

        //thirdVictimSetup
        gameController.getGame().getBoard().generatePlayer(0,1,thirdVictim);
        gameController.getGame().getPlayers().add(thirdVictim);
        clientData.setThirdVictim(thirdVictim);
    }
}
