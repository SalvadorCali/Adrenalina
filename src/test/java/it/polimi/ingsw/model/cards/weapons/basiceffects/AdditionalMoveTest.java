package it.polimi.ingsw.model.cards.weapons.basiceffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalMove;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalTarget;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;

public class AdditionalMoveTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.PURPLE);
    private Player thirdVictim = new Player(TokenColor.YELLOW);
    private MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());

    @Test
    void plasmaGunAddictionalMoveTest(){

        playerSetup();
        Effect plasmaGun = new DamageMarkEffect("Plasma Gun", 0,0,0,2,0);
        Effect plasmaGunAdd = new AdditionalMove("Plasma Gun", 0,0,0,2,0, plasmaGun);
        gameController.getGame().getBoard().move(1,1, victim);
        gameController.getActionInterface().getClientData().setBasicFirst(false);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.RIGHT);
        gameController.getActionInterface().getClientData().setSecondMove(null);
        assertTrue(plasmaGunAdd.canUseEffect(gameController.getActionInterface()));
        mapCLI.printMap();

        gameController.getActionInterface().getClientData().setSecondMove(Direction.RIGHT);
        assertTrue(plasmaGunAdd.canUseEffect(gameController.getActionInterface()));
        mapCLI.printMap();

        gameController.getGame().getBoard().move(0,1, victim);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.DOWN);
        gameController.getActionInterface().getClientData().setSecondMove(Direction.DOWN);
        assertFalse(plasmaGunAdd.canUseEffect(gameController.getActionInterface()));
        mapCLI.printMap();


        gameController.getActionInterface().getClientData().setBasicFirst(true);
        assertTrue(plasmaGunAdd.canUseEffect(gameController.getActionInterface()));
        mapCLI.printMap();




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
