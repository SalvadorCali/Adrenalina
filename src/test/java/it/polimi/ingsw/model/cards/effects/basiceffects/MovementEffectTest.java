package it.polimi.ingsw.model.cards.effects.basiceffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.MovementEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.cli.DamageBoardCLI;
import it.polimi.ingsw.view.cli.MapCLI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests referring to the MovementEffect class.
 */
class MovementEffectTest {

    private GameController gameController = new GameController();
    private ClientData clientData = gameController.getActionInterface().getClientData();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.PURPLE);

    /**
     * Tests the controls of the tractor beam mod 1 basic effect.
     */
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

    /**
     * Tests the application of  the tractor beam mod 1 basic effect.
     */
    @Test
    void tractorBeamMod1UseTest(){

        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        playerSetup();
        mapCLI.printMap();
        Effect tractorBeamMod1 = new MovementEffect("Tractor Beam1", 1, 0,0,2,0);
        clientData.setFirstMove(Direction.UP);
        tractorBeamMod1.canUseEffect(gameController.getActionInterface());
        tractorBeamMod1.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        assertEquals(TokenColor.GREEN,victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());

    }

    /**
     * Tests the controls of the tractor beam mod 2 basic effect.
     */
    @Test
    void tractorBeamMod2Test(){
        playerSetup();
        currentPlayer.addAmmo(new Ammo(Color.RED), new Ammo(Color.YELLOW));
        Effect tractorBeamMod2 = new MovementEffect("Tractor Beam2", 1, 0,1,0,1);

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

    /**
     * Tests the application of the tractor beam mod 2 basic effect.
     */
    @Test
    void tractorBeamMod2UseTest(){

        playerSetup();
        currentPlayer.addAmmo(new Ammo(Color.RED), new Ammo(Color.YELLOW));
        Effect tractorBeamMod2 = new MovementEffect("Tractor Beam2", 3, 0,1,0,1);
        gameController.getActionInterface().move(0,2, victim);
        clientData.setFirstMove(Direction.LEFT);
        clientData.setSecondMove(Direction.LEFT);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        Printer.println(tractorBeamMod2.canUseEffect(gameController.getActionInterface()));
        tractorBeamMod2.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        assertEquals(TokenColor.GREEN,victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN,victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        assertEquals(TokenColor.GREEN,victim.getPlayerBoard().getDamageBoard()[2].getFirstColor());

    }

    /**
     * Tests the controls of the power glove mod 1 basic effect.
     */
    @Test
    void powerGloveMod1Test(){

        playerSetup();
        Effect powerGloveMod1 = new MovementEffect("Power Glove1", 1, 2,0,0,0);


        gameController.getActionInterface().move(0,1, victim);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertTrue(powerGloveMod1.canUseEffect(gameController.getActionInterface()));
        powerGloveMod1.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        DamageBoardCLI dbc = new DamageBoardCLI(victim);
        dbc.printDamageBoard();
        gameController.getActionInterface().move(1,0, victim);
        gameController.getGame().getBoard().move(0,0,currentPlayer);
        assertTrue(powerGloveMod1.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(1,1, victim);
        assertFalse(powerGloveMod1.canUseEffect(gameController.getActionInterface()));
    }

    /**
     * Tests the controls of the rocket launcher basic effect.
     */
    @Test
    void rocketLauncherTest(){

        playerSetup();
        Effect rocketLauncher = new MovementEffect("Rocket Launcher", 2,0,0,0,0 );

        gameController.getActionInterface().move(0,1, victim);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.RIGHT);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertTrue(rocketLauncher.canUseEffect(gameController.getActionInterface()));
        rocketLauncher.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());

        gameController.getActionInterface().move(1,0, victim);
        gameController.getActionInterface().getClientData().setFirstMove(null);
        assertTrue(rocketLauncher.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().move(0,0, victim);
        assertFalse(rocketLauncher.canUseEffect(gameController.getActionInterface()));

    }

    /**
     * Tests the controls of the grenade launcher basic effect.
     */
    @Test
    void grenadeLauncherTest(){
        playerSetup();
        Effect grenadeLauncher = new MovementEffect("Grenade Launcher", 2,0,0,0,0 );
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        clientData.setFirstMove(Direction.DOWN);
        clientData.setSecondMove(null);
        gameController.getGame().getBoard().move(1,0, victim);
        mapCLI.printMap();
        assertTrue(grenadeLauncher.canUseEffect(gameController.getActionInterface()));
        grenadeLauncher.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());

        clientData.setSecondMove(Direction.DOWN);
        assertFalse(grenadeLauncher.canUseEffect(gameController.getActionInterface()));

        clientData.setFirstMove(Direction.LEFT);
        clientData.setSecondMove(null);
        assertFalse(grenadeLauncher.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,0, victim);
        mapCLI.printMap();
        clientData.setFirstMove(Direction.UP);
        assertFalse(grenadeLauncher.canUseEffect(gameController.getActionInterface()));
    }

    /**
     * Tests the controls of the shotgun mod 1 basic effect test.
     */
    @Test
    void shotgunMod1Test(){

        playerSetup();
        Effect shotgun1 = new MovementEffect("Shotgun1", 3,0,0,0,0 );
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getActionInterface().move(0,0, victim);
        mapCLI.printMap();
        assertTrue(shotgun1.canUseEffect(gameController.getActionInterface()));
        shotgun1.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[2].getFirstColor());
        mapCLI.printMap();
        clientData.setFirstMove(Direction.DOWN);
        assertTrue(shotgun1.canUseEffect(gameController.getActionInterface()));
        shotgun1.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[3].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[4].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[5].getFirstColor());
        mapCLI.printMap();
        gameController.getGame().getBoard().move(0,0,victim);
        clientData.setSecondMove(Direction.DOWN);
        assertFalse(shotgun1.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(0,1, victim);
        assertFalse(shotgun1.canUseEffect(gameController.getActionInterface()));
    }

    /**
     * Tests the controls of the shotgun mod 2 basic effect test.
     */
    @Test
    void shotgunMod2Test(){

        playerSetup();
        Effect shotgun2 = new MovementEffect("Shotgun2", 2,0,0,0,0 );

        gameController.getActionInterface().move(0,1, victim);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertTrue(shotgun2.canUseEffect(gameController.getActionInterface()));
        shotgun2.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        mapCLI.printMap();
        gameController.getActionInterface().move(1,0, victim);
        assertTrue(shotgun2.canUseEffect(gameController.getActionInterface()));
        gameController.getActionInterface().move(0,0, victim);
        assertFalse(shotgun2.canUseEffect(gameController.getActionInterface()));
    }

    /**
     * Setups the game components for the tests.
     */
    void playerSetup(){

        //currentPlayerSetup
        gameController.getGame().getBoard().generatePlayer(0,0,currentPlayer);
        gameController.getGame().getPlayers().add(currentPlayer);
        clientData.setCurrentPlayer(currentPlayer);
        gameController.getGame().setCurrentPlayer(currentPlayer);
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
