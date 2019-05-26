package it.polimi.ingsw.model.cards.effects.additionaleffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalTarget;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdditionalTargetTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.PURPLE);
    private Player thirdVictim = new Player(TokenColor.YELLOW);
    private MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());

    @Test
    void lockRifleAdditionalTest(){

        playerSetup();
        Effect lockRifle = new DamageMarkEffect("Lock Rifle", 2, 1, 0, 2, 0);
        Effect lockRifleAddictional = new AdditionalTarget("Lock Rifle", 0, 1,0,0,0, lockRifle);

        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        assertTrue(lockRifleAddictional.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,0, currentPlayer);
        mapCLI.printMap();
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));
        assertFalse(lockRifleAddictional.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,2, currentPlayer);
        mapCLI.printMap();
        assertFalse(lockRifle.canUseEffect(gameController.getActionInterface()));
        assertFalse(lockRifleAddictional.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void thorFirstAdditionalTest(){

        playerSetup();
        Effect thor = new DamageMarkEffect("T.H.O.R.", 2, 0, 0, 2, 0);
        Effect additionalThor = new AdditionalTarget("T.H.O.R. Single", 1, 0,0,0,0, thor);

        gameController.getGame().getBoard().move(1,2, currentPlayer);
        gameController.getGame().getBoard().move(1,1, victim);
        gameController.getGame().getBoard().move(2,0,secondVictim);

        assertTrue(additionalThor.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,0, secondVictim);
        assertTrue(thor.canUseEffect(gameController.getActionInterface()));
        assertFalse(additionalThor.canUseEffect(gameController.getActionInterface()));



    }

    @Test
    void machineGunDoubleTest(){
        playerSetup();
        Effect machineGun = new DamageMarkEffect("Machine Gun", 2, 1, 0, 2, 0);
        Effect machineGunDouble = new AdditionalTarget("Machine Gun Double", 0, 0, 0,0,0, machineGun);

        mapCLI.printMap();
        assertTrue(machineGunDouble.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,1,thirdVictim);
        assertFalse(machineGunDouble.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void thorAdditionalTest(){
        playerSetup();
        Effect thor = new DamageMarkEffect("T.H.O.R.", 2, 1, 0, 2, 0);
        Effect thorSingle = new AdditionalTarget("T.H.O.R. Single", 0, 0, 0,0,0, thor);
        Effect thorDouble = new AdditionalTarget("T.H.O.R. Double",0,0,0,0,0, thorSingle);
        gameController.getGame().getBoard().move(0,1,victim);
        gameController.getGame().getBoard().move(0,2, secondVictim);
        gameController.getGame().getBoard().move(1,2,thirdVictim);

        assertTrue(thorSingle.canUseEffect(gameController.getActionInterface()));
        assertTrue(thorDouble.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,0, thirdVictim);
        mapCLI.printMap();
        assertTrue(thorSingle.canUseEffect(gameController.getActionInterface()));
        assertFalse(thorDouble.canUseEffect(gameController.getActionInterface()));

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
