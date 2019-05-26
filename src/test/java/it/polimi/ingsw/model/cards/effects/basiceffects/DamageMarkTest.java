package it.polimi.ingsw.model.cards.effects.basiceffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.DamageBoardCLI;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;

public class DamageMarkTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.PURPLE);
    private Player thirdVictim = new Player(TokenColor.YELLOW);


    @Test
    void lockRifleCanUseEffectTest() {

        playerSetup();

        Effect lockRifle = new DamageMarkEffect("Lock Rifle", 2, 1, 0, 1, 0);
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,1,currentPlayer);
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,2,currentPlayer);
        assertFalse(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,0,currentPlayer);
        lockRifle.canUseEffect(gameController.getActionInterface());

        gameController.getGame().getBoard().move(2,0,currentPlayer);
        assertTrue(lockRifle.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,1,currentPlayer);
        assertFalse(lockRifle.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void lockRifleUseTest(){

        playerSetup();
        Effect lockRifle = new DamageMarkEffect("Lock Rifle", 2, 1, 0, 0, 0);
        lockRifle.canUseEffect(gameController.getActionInterface());
        lockRifle.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getRevengeMarks().get(0).getFirstColor());
    }

    @Test
    void cyberbladeCanUseEffectTest(){

        playerSetup();
        Effect cyberblade = new DamageMarkEffect("Cyberblade",2,0,0,2,0);

        gameController.getGame().getBoard().move(1,0,currentPlayer);
        cyberblade.canUseEffect(gameController.getActionInterface());

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                gameController.getGame().getBoard().move(i, j, currentPlayer);
                if(i == victim.getPosition().getX() && j == victim.getPosition().getY())
                    assertTrue(cyberblade.canUseEffect(gameController.getActionInterface()));
                else
                    assertFalse(cyberblade.canUseEffect(gameController.getActionInterface()));
            }
        }

    }

    @Test
    void whisperCanUseEffectTest(){

        playerSetup();
        Effect whisper = new DamageMarkEffect("Whisper",3,1,0,2,0);

        gameController.getGame().getBoard().move(1,2, victim);

        gameController.getGame().getBoard().move(0, 0, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0, 1, currentPlayer);
        assertTrue(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0, 2, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,3, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,0, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,1, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,2, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,3, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,0, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,1, currentPlayer);
        assertTrue(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,2, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,3, currentPlayer);
        assertFalse(whisper.canUseEffect(gameController.getActionInterface()));

    }

    @Test
    void machineGunCanUseEffectTest(){

        playerSetup();
        Effect machineGun = new DamageMarkEffect("Machine Gun",3,1,0,2,0);

        assertTrue(machineGun.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,0, secondVictim);
        assertFalse(machineGun.canUseEffect(gameController.getActionInterface()));


    }

    @Test
    void heatseekerCanUseEffectTest(){

        playerSetup();
        Effect heatseeker = new DamageMarkEffect("Heatseeker", 2, 1, 0, 2, 0);

        assertFalse(heatseeker.canUseEffect(gameController.getActionInterface()));
        gameController.getGame().getBoard().move(0, 1, currentPlayer);

        assertFalse(heatseeker.canUseEffect(gameController.getActionInterface()));
        heatseeker.canUseEffect(gameController.getActionInterface());

        gameController.getGame().getBoard().move(0, 2, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,3, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface())); ////

        gameController.getGame().getBoard().move(1,0, currentPlayer);
        assertFalse(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,1, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,2, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,3, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,0, currentPlayer);
        assertFalse(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,1, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,2, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,3, currentPlayer);
        assertTrue(heatseeker.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void zx2SecondMode(){

        playerSetup();
        Effect zx22 = new DamageMarkEffect("ZX-22",0,0,0,2,0);
        assertTrue(zx22.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,1, victim);
        assertFalse(zx22.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,1, victim);
        gameController.getGame().getBoard().move(1,1, secondVictim);
        assertFalse(zx22.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,1, victim);
        gameController.getGame().getBoard().move(0,0, secondVictim);
        gameController.getGame().getBoard().move(2,2, thirdVictim);
        assertFalse(zx22.canUseEffect(gameController.getActionInterface()));

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
        clientData.setVictim(victim);

        //secondVictimSetup
        gameController.getGame().getBoard().generatePlayer(0,1,secondVictim);
        gameController.getGame().getPlayers().add(secondVictim);
        clientData.setSecondVictim(secondVictim);

        //thirdVictimSetup
        gameController.getGame().getBoard().generatePlayer(0,0, thirdVictim);
        gameController.getGame().getPlayers().add(thirdVictim);
        clientData.setThirdVictim(thirdVictim);

    }
}
