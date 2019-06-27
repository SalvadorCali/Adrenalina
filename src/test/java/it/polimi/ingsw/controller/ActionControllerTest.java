package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link ActionController}.
 */
class ActionControllerTest {
    /**
     * A list of players used for testing.
     */
    private List<Player> playerList = new ArrayList<>();
    /**
     * A game used for testing.
     */
    private Game game;

    /**
     * Tests the getter of the ClientData.
     */
    @Test
    void clientDataTest(){
        ActionController actionController = (ActionController) create();
        ClientData clientData = clientDataSetup();
        actionController.setClientData(clientData);

        assertEquals(Direction.UP, actionController.getClientData().getFirstMove());
        assertEquals(Direction.DOWN, actionController.getClientData().getSecondMove());
        assertEquals(Direction.LEFT, actionController.getClientData().getThirdMove());
        assertEquals(Direction.RIGHT, actionController.getClientData().getFourthMove());
        assertEquals(3, actionController.getClientData().getSquare().getX());
        assertEquals(3, actionController.getClientData().getSquare().getY());
        assertEquals(TokenColor.BLUE, actionController.getClientData().getVictim().getColor());
        assertEquals(TokenColor.GREEN, actionController.getClientData().getSecondVictim().getColor());
        assertEquals(TokenColor.YELLOW, actionController.getClientData().getThirdVictim().getColor());
        assertTrue(actionController.getClientData().basicFirst());
    }

    /**
     * Tests that the number and the color of marks given by the method is correct.
     */
    @Test
    void playerMarkTest(){
        ActionInterface actionController = create();
        assertEquals(0, playerList.get(1).getPlayerBoard().getRevengeMarks().size());
        actionController.playerMark(playerList.get(1), 2);
        assertEquals(2, playerList.get(1).getPlayerBoard().getRevengeMarks().size());
        assertEquals(TokenColor.BLUE, playerList.get(1).getPlayerBoard().getRevengeMarks().get(0).getFirstColor());

        actionController.playerMark(playerList.get(0), playerList.get(2));
        assertEquals(1, playerList.get(2).getPlayerBoard().getRevengeMarks().size());
        assertEquals(TokenColor.BLUE, playerList.get(2).getPlayerBoard().getRevengeMarks().get(0).getFirstColor());
    }

    /**
     * Tests that the player is visible.
     */
    @Test
    void generatePlayerAndisVisibleTest(){
        ActionInterface actionController = create();
        game.getBoard().generatePlayer(1, 1, playerList.get(0));
        actionController.generatePlayer(playerList.get(0), playerList.get(1));
        actionController.generatePlayer(playerList.get(1), playerList.get(2));
        assertEquals(1, playerList.get(0).getPosition().getX());
        assertEquals(1, playerList.get(1).getPosition().getX());
        assertEquals(1, playerList.get(2).getPosition().getX());
        assertTrue(actionController.isVisible(playerList.get(1).getColor()));

        actionController.move(0, 0, playerList.get(1));
        assertFalse(actionController.isVisible(playerList.get(1).getColor()));
        actionController.move(0, 2, playerList.get(1));
        boolean visible = actionController.isVisible(playerList.get(1).getColor());
        assertTrue(visible);
    }

    /**
     * Tests that in a square there is a player or not.
     */
    @Test
    void squareControlTest(){
        ActionInterface actionController = create();
        game.getBoard().generatePlayer(1, 1, playerList.get(0));
        assertTrue(actionController.squareControl(0, 0, null));
        assertTrue(actionController.squareControl(1, 1, playerList.get(0)));
        assertFalse(actionController.squareControl(2, 1, playerList.get(0)));
    }

    /**
     * Creates a new game and sets the parameters to test the ActionController.
     * @return a new ActionController.
     */
    private ActionInterface create(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(playerList.get(0));
        game = gameController.getGame();
        return new ActionController(game);
    }

    /**
     * Creates three new players and adds them to the list.
     */
    private void playerSetup(){
        Player player1 = new Player(TokenColor.BLUE);
        player1.setUsername("A");
        Player player2 = new Player(TokenColor.GREEN);
        player2.setUsername("B");
        Player player3 = new Player(TokenColor.YELLOW);
        player3.setUsername("C");
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
    }

    /**
     * Sets the ClientData's attributes that will be tested.
     * @return a new ClientData object.
     */
    private ClientData clientDataSetup(){
        ClientData clientData = new ClientData();
        clientData.setFirstMove(Direction.UP);
        clientData.setSecondMove(Direction.DOWN);
        clientData.setThirdMove(Direction.LEFT);
        clientData.setFourthMove(Direction.RIGHT);
        clientData.setSquare(3, 3);
        clientData.setVictim(playerList.get(0));
        clientData.setSecondVictim(playerList.get(1));
        clientData.setThirdVictim(playerList.get(2));
        clientData.setBasicFirst(true);
        return clientData;
    }
}
