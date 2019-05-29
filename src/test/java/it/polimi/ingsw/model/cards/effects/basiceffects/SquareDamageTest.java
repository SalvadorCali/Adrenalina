package it.polimi.ingsw.model.cards.effects.basiceffects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.SquareDamageEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.DamageBoardCLI;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;
import static junit.framework.TestCase.*;



public class SquareDamageTest {

    private GameController gameController = new GameController();
    private Player currentPlayer = new Player(TokenColor.GREEN);
    private Player victim = new Player(TokenColor.BLUE);
    private Player secondVictim = new Player(TokenColor.YELLOW);

    @Test
    void hellionCanUseEffectTest(){

        playerSetup();
        Effect hellion = new SquareDamageEffect("Hellion", 1, 2,0, 0,0);
        gameController.getGame().getBoard().move(0,1, currentPlayer);
        gameController.getGame().getBoard().move(1,0, secondVictim);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertTrue(hellion.canUseEffect(gameController.getActionInterface()));
        hellion.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN,victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN,victim.getPlayerBoard().getRevengeMarks().get(0).getFirstColor());

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
    @Test
    void furnace1UseTest(){

        playerSetup();
        Effect furnace1 = new SquareDamageEffect("Furnace1",1,0,0,0,0);
        gameController.getGame().getBoard().move(0,1,victim);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        gameController.getActionInterface().getClientData().setSquare(0,1);
        Printer.println(furnace1.canUseEffect(gameController.getActionInterface()));
        furnace1.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN,secondVictim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
    }
    @Test
    void furnace2UseTest(){
        playerSetup();
        Effect furnace2 = new SquareDamageEffect("Furnace2",1,1,0,0,0);
        gameController.getGame().getBoard().move(0,1,victim);
        gameController.getGame().getBoard().move(0,1,secondVictim);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        gameController.getActionInterface().getClientData().setSquare(0,1);
        assertTrue(furnace2.canUseEffect(gameController.getActionInterface()));
        furnace2.useEffect(gameController.getActionInterface());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, victim.getPlayerBoard().getRevengeMarks().get(0).getFirstColor());
        assertEquals(TokenColor.GREEN, secondVictim.getPlayerBoard().getDamageBoard()[0].getFirstColor());
        assertEquals(TokenColor.GREEN, secondVictim.getPlayerBoard().getRevengeMarks().get(0).getFirstColor());

    }
    @Test
    void shockwave2Test(){
        playerSetup();
        Effect shockwave = new SquareDamageEffect("Shockwave", 1,0,0,0,1);
        currentPlayer.addAmmo(new Ammo(Color.YELLOW));
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().move(0,1,secondVictim);
        mapCLI.printMap();
        assertTrue(shockwave.canUseEffect(gameController.getActionInterface()));
        shockwave.useEffect(gameController.getActionInterface());
        DamageBoardCLI dbc = new DamageBoardCLI(victim);
        DamageBoardCLI dbc2 = new DamageBoardCLI(secondVictim);
        dbc.printDamageBoard();
        dbc2.printDamageBoard();
        currentPlayer.addAmmo(new Ammo(Color.YELLOW));
        gameController.getGame().getBoard().move(0,2,victim);
        mapCLI.printMap();
        assertTrue(shockwave.canUseEffect(gameController.getActionInterface()));
        shockwave.useEffect(gameController.getActionInterface());
        dbc.printDamageBoard();
        dbc2.printDamageBoard();
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

        //victimSetup
        gameController.getGame().getBoard().generatePlayer(0,2,secondVictim);
        gameController.getGame().getPlayers().add(secondVictim);
        clientData.setSecondVictim(secondVictim);

        //positionSetup
        clientData.setSquare(2, 1);

    }
}
