package it.polimi.ingsw.model.cards.effects.basiceffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.SquareDamageEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;



public class SquareDamageTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);

    @Test
    void hellionCanUseEffectTest(){

        playerSetup();
        Effect hellion = new SquareDamageEffect("Hellion", 1, 1,0, 2,0);
        gameController.getGame().getBoard().move(1,1, currentPlayer);
        //assertTrue(hellion.canUseEffect(gameController.getActionInterface()));
    }

    @Test
    void electroscytheUseTest1(){
        playerSetup();
        gameController.getGame().getBoard().move(0,0, victim);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        Effect electroscythe = new SquareDamageEffect("Electroscythe", 1, 0,0, 0,0);
        electroscythe.canUseEffect(gameController.getActionInterface());
        electroscythe.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN ,victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        Effect electroscythe2 = new SquareDamageEffect("Electroscythe", 2, 0,1, 1,0);
        currentPlayer.addAmmo(new Ammo(Color.BLUE), new Ammo(Color.RED));
        electroscythe2.canUseEffect(gameController.getActionInterface());
        electroscythe2.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN ,victim.getPlayerBoard().getDamageBoard()[1].getFirstColor());
        assertEquals(TokenColor.GREEN ,victim.getPlayerBoard().getDamageBoard()[2].getFirstColor());
    }

    void playerSetup(){

        ClientData clientData = gameController.getActionInterface().getClientData();

        //currentPlayerSetup
        gameController.getGame().getBoard().generatePlayer(0,0,currentPlayer);
        gameController.getGame().getPlayers().add(currentPlayer);
        clientData.setCurrentPlayer(currentPlayer);
        gameController.getGame().setCurrentPlayer(currentPlayer);


        //victimSetup
        gameController.getGame().getBoard().generatePlayer(1,0,victim);
        gameController.getGame().getPlayers().add(victim);
        clientData.setVictim(victim);

        //positionSetup
        clientData.setSquare(2, 1);

    }
}
