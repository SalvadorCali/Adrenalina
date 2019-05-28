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
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.DamageBoardCLI;
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
        Effect grenadeLauncher = new MovementEffect("Grenade Launcher", 1, 0,0,0,0);
        Effect grenadeLauncherAdd = new AdditionalSquareDamage("Grenade Launcher",1,1,0,0, grenadeLauncher);
        mapCLI.printMap();

        currentPlayer.addAmmo(new Ammo(Color.RED));
        gameController.getActionInterface().getClientData().setBasicFirst(true);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.DOWN);
        gameController.getActionInterface().getClientData().setSquare(0,1);
        assertTrue(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));
        grenadeLauncherAdd.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, secondVictim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, thirdVictim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        mapCLI.printMap();

        currentPlayer.addAmmo(new Ammo(Color.RED));
        gameController.getActionInterface().getClientData().setFirstMove(Direction.LEFT);
        assertFalse(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,0,victim);
        mapCLI.printMap();
        gameController.getActionInterface().getClientData().setFirstMove(null);
        assertTrue(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));
        grenadeLauncherAdd.useEffect(gameController.getActionInterface());
        mapCLI.printMap();
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        assertEquals(TokenColor.GREEN, secondVictim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        assertEquals(TokenColor.GREEN, thirdVictim.getPlayerBoard().getDamageBoard()[1].getFirstColor());

        gameController.getActionInterface().getClientData().setSquare(1,1);
        assertTrue(grenadeLauncher.canUseEffect(gameController.getActionInterface()));
        assertFalse(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

        gameController.getActionInterface().getClientData().setBasicFirst(false);
        assertFalse(grenadeLauncherAdd.canUseEffect(gameController.getActionInterface()));

    }

    @Test
    void rocketLauncherDoubleAddTest(){
        playerSetup();
        Effect rocketLauncher = new MovementEffect("Rocket Launcher",2,0,0,0,0);
        Effect rocketLauncherAdd = new AdditionalMove("Rocket Launcher",0,0,0,1,0,rocketLauncher);
        Effect rocketLauncherDouble = new AdditionalSquareDamage("Rocket Launcher",1,0,0,1,rocketLauncherAdd);
        gameController.getActionInterface().getClientData().setBasicFirst(true);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().move(1,0,secondVictim);
        currentPlayer.addAmmo(new Ammo(Color.YELLOW));
        gameController.getActionInterface().getClientData().setFirstMove(Direction.DOWN);
        gameController.getActionInterface().getClientData().setThirdMove(Direction.RIGHT);
        gameController.getActionInterface().getClientData().setFourthMove(Direction.DOWN);
        mapCLI.printMap();
        assertTrue(rocketLauncherDouble.canUseEffect(gameController.getActionInterface()));
        DamageBoardCLI d = new DamageBoardCLI(victim);
        d.printDamageBoard();
        rocketLauncherDouble.useEffect(gameController.getActionInterface());
        d.printDamageBoard();
        mapCLI.printMap();
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[2].getFirstColor());
        assertEquals(TokenColor.GREEN, secondVictim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
    }

    void playerSetup(){

        ClientData clientData = gameController.getActionInterface().getClientData();

        //currentPlayerSetup
        gameController.getGame().getBoard().generatePlayer(0,0,currentPlayer);
        gameController.getGame().getPlayers().add(currentPlayer);
        clientData.setCurrentPlayer(currentPlayer);
        gameController.getGame().setCurrentPlayer(currentPlayer);
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
