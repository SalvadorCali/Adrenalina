package it.polimi.ingsw.model.cards.effects.basiceffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DirectionalDamage;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;

public class DirectionalDamageTest {

    private GameController gameController = new GameController();
    ClientData clientData = gameController.getActionInterface().getClientData();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.PURPLE);

    @Test
    void flameThrowerMod1Test(){

        playerSetup();
        Effect flameThrower1 = new DirectionalDamage("Flamethrower1",1, 0,2,0);

        clientData.setFirstMove(Direction.DOWN);

        gameController.getGame().getBoard().move(0,1, currentPlayer);
        gameController.getGame().getBoard().move(1,1, victim);
        gameController.getGame().getBoard().move(2,1, secondVictim);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertTrue(flameThrower1.canUseEffect(gameController.getActionInterface()));
        clientData.setSecondVictim(null);
        assertTrue(flameThrower1.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,2, victim);
        assertFalse(flameThrower1.canUseEffect(gameController.getActionInterface()));

        clientData.setSecondVictim(secondVictim);
        gameController.getGame().getBoard().move(1,1,victim);
        gameController.getGame().getBoard().move(2,2,secondVictim);
        assertFalse(flameThrower1.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void flameThrowerMod2Test(){

        playerSetup();
        Effect flameThrower2 = new DirectionalDamage("Flamethrower2",1, 0,2,0);
        gameController.getGame().getBoard().move(0,1, currentPlayer);
        clientData.setFirstMove(Direction.DOWN);
        assertTrue(flameThrower2.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(1,1, currentPlayer);
        assertTrue(flameThrower2.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(2,1, currentPlayer);
        assertFalse(flameThrower2.canUseEffect(gameController.getActionInterface()));

        gameController.getGame().getBoard().move(0,1, currentPlayer);
        clientData.setFirstMove(Direction.UP);
        assertFalse(flameThrower2.canUseEffect(gameController.getActionInterface()));

    }

    @Test
    void railgunMod1Test(){
        playerSetup();
        Effect railgun1 = new DirectionalDamage("Railgun1",1, 0,2,0);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().move(2,2, currentPlayer);
        gameController.getGame().getBoard().move(1,2,victim);
        gameController.getGame().getBoard().move(0,2,secondVictim);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.UP);
        assertTrue(railgun1.canUseEffect(gameController.getActionInterface()));
        gameController.getGame().getBoard().move(2,1, victim);
        assertFalse(railgun1.canUseEffect(gameController.getActionInterface()));
        gameController.getGame().getBoard().move(2,2, victim);
        gameController.getActionInterface().getClientData().setFirstMove(null);
        assertTrue(railgun1.canUseEffect(gameController.getActionInterface()));
        gameController.getGame().getBoard().move(0,3,victim);
        gameController.getGame().getBoard().move(0,2,currentPlayer);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.RIGHT);
        assertFalse(railgun1.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void railgunMod2Test(){
        playerSetup();
        Effect railgun2 = new DirectionalDamage("Railgun2",1, 0,2,0);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().move(2,2, currentPlayer);
        gameController.getGame().getBoard().move(1,2,victim);
        gameController.getGame().getBoard().move(0,2,secondVictim);
        gameController.getActionInterface().getClientData().setFirstMove(Direction.UP);
        assertTrue(railgun2.canUseEffect(gameController.getActionInterface()));
        gameController.getGame().getBoard().move(0,1,secondVictim);
        assertFalse(railgun2.canUseEffect(gameController.getActionInterface()));
        gameController.getGame().getBoard().move(2,2,victim);
        gameController.getGame().getBoard().move(1,2,secondVictim);
        //assertTrue(railgun2.canUseEffect(gameController.getActionInterface()));

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
