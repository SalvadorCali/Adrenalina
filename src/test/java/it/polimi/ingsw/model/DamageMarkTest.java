package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.view.MapCLI;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

public class DamageMarkTest {

    private GameController gameController = new GameController();
    private MapCLI mapCLI = new MapCLI(gameController);


    @Test
    public void lockRifleBasicTest() {

        ClientData clientData = gameController.getActionInterface().getClientData();
        Player victim = new Player(TokenColor.BLUE);
        Player currentPlayer = new Player(TokenColor.GREEN);
        Position currentPlayerPosition = new Position(1,3);
        Position victimPosition = new Position(2,2);
        currentPlayer.setPosition(currentPlayerPosition);
        victim.setPosition(victimPosition);
        currentPlayer.increaseAmmoNumber(Color.BLUE);
        currentPlayer.increaseAmmoNumber(Color.BLUE);
        mapCLI.printMap();
        gameController.getGame().getPlayers().add(victim);
        gameController.getGame().getPlayers().add(currentPlayer);
        gameController.getGame().getBoard().getArena()[1][3].addPlayer(currentPlayer);
        gameController.getGame().getBoard().getArena()[2][2].addPlayer(victim);
        clientData.setVictim(victim);
        clientData.setCurrentPlayer(currentPlayer);
        Effect lockRifle = new DamageMarkEffect("Lock Rifle", 2, 1, 0, 2, 0);
        System.out.println(lockRifle.canUseEffect(gameController.getActionInterface()));
    }
}
