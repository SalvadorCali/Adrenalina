package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Tests referring to the Game class.
 */
class GameTest {

    /**
     * Tests the setters/getters of the Game class.
     */
    @Test
    void settersTest(){
        Game game = createGame();
        game.setRespawnPhase(true);
        assertTrue(game.isRespawnPhase());
        game.setGamePhase(true);
        assertTrue(game.isGamePhase());
        game.setSpawnLocationPhase(true);
        assertTrue(game.isSpawnLocationPhase());
        game.setBoardTypePhase(true);
        assertTrue(game.isBoardTypePhase());
        game.setColorSelection(true);
        assertTrue(game.isColorSelection());
        game.setFinalFrenzy(true);
        assertTrue(game.isFinalFrenzy());
        assertEquals(TokenColor.BLUE, game.getPlayers().get(0).getColor());
        assertEquals(TokenColor.BLUE, game.getPlayerColors().get(0));
    }

    /**
     * Tests that the ammo box and the ammo reserve of each player in the game is correctly filled.
     */
    @Test
    void giveAmmosTest(){
        Game game = createGame();
        game.giveAmmos();
        assertEquals(6, game.getPlayers().get(0).getAmmoReserve().size());
        assertEquals(3, game.getPlayers().get(0).getAmmoBox().size());
    }

    /**
     * Tests the correct ending of the turn.
     */
    @Test
    void endTurnTest(){
        Game game = createGame();
        ActionInterface actionInterface = new ActionController(game);
        game.endTurn(game.getCurrentPlayer(), actionInterface);
        assertEquals(TokenColor.YELLOW, game.getCurrentPlayer().getColor());
        for (int i = 0; i < game.getPlayers().size() ; i++) {
            assertFalse(game.getPlayers().get(i).isDamaged());
        }
    }

    /**
     * Tests that when the final frenzy starts, every player is not dead anymore.
     */
    @Test
    void finalFrenzyTest(){
        Game game = createGame();
        game.finalFrenzy();
        for (int i = 0; i < game.getPlayers().size() ; i++) {
            assertFalse(game.getPlayers().get(i).isDead());
        }
    }

    /**
     * Tests that the score of the game is correctly evaluated.
     */
    @Test
    void scoringTest(){
        Game game = createGame();
        game.scoring();
        Map<TokenColor, Integer> scoreList = game.getScoreList();
        assertEquals(20, scoreList.get(TokenColor.BLUE).intValue());
        assertEquals(16, scoreList.get(TokenColor.YELLOW).intValue());
        assertEquals(14, scoreList.get(TokenColor.GREY).intValue());
        assertEquals(0, scoreList.get(TokenColor.PURPLE).intValue());
    }

    @Test
    void scoringFinalFrenzyTest(){
        Game game = createGame();
        game.getPlayers().get(1).getPlayerBoard().setFinalFrenzy(true);
        game.setFinalFrenzy(true);
        game.scoring();
        Map<TokenColor, Integer> scoreList = game.getScoreList();
        assertEquals(14, scoreList.get(TokenColor.BLUE).intValue());
        assertEquals(16, scoreList.get(TokenColor.YELLOW).intValue());
        assertEquals(8, scoreList.get(TokenColor.GREY).intValue());
        assertEquals(0, scoreList.get(TokenColor.PURPLE).intValue());
    }

    @Test
    void scoringEndGame(){
        Game game = createGame();
        game.getPlayers().get(1).getPlayerBoard().setFinalFrenzy(true);
        game.setFinalFrenzy(true);
        game.setEndPhase(true);
        game.scoring();
        Map<TokenColor, Integer> scoreList = game.getScoreList();
        assertEquals(13, scoreList.get(TokenColor.BLUE).intValue());
        assertEquals(24, scoreList.get(TokenColor.YELLOW).intValue());
        assertEquals(8, scoreList.get(TokenColor.GREY).intValue());
        assertEquals(0, scoreList.get(TokenColor.PURPLE).intValue());
    }

    /**
     * Tests that the killshot track is correctly initialized.
     */
    @Test
    void createKillshotTrackTest(){
        Game game = createGame();
        game.createKillshotTrack(5);
        assertEquals(5, game.getKillshotTrack().size());
        for(int i=0; i<5; i++){
            assertEquals(TokenColor.SKULL, game.getKillshotTrack().get(i).getFirstColor());
        }
    }

    /**
     * Tests that every square is correctly filled with new ammo cards/weapon cards at the end of the turn.
     */
    @Test
    void fillSquaresTest(){
        Game game = createGame();
        ActionInterface actionInterface = new ActionController(game);
        game.fillSquares(actionInterface);
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                if(!game.getBoard().getArena()[i][j].getColor().equals(TokenColor.NONE)){
                }
            }
        }
        game.getBoard().getArena();
    }

    /**
     * Tests that the revenge marks are correctly setted.
     */
    @Test
    void setRevengeMarksTest(){
        Game game = createGame();
        game.setRevengeMarks();

        assertEquals(TokenColor.GREY, game.getPlayers().get(1).getPlayerBoard().getRevengeMarks().get(0).getFirstColor());
        assertEquals(TokenColor.PURPLE, game.getPlayers().get(1).getPlayerBoard().getRevengeMarks().get(1).getFirstColor());
        assertEquals(TokenColor.YELLOW, game.getPlayers().get(2).getPlayerBoard().getRevengeMarks().get(0).getFirstColor());
    }

    /**
     * Tests that the powerup deck is correctly updated after the drawing of a powerup.
     */
    @Test
    void drawPowerupTest(){
        Game game = createGame();
        int oldsize = game.getPowerup().size();
        game.drawPowerup();
        assertEquals(oldsize -1 ,game.getPowerup().size());
    }

    /**
     * Tests that at the end of the game, the standing is printed and is nobody's turn.
     */
    @Test
    void endGameTest(){
        Game game = createGame();
        game.endGame();
        assertFalse(game.isGamePhase());
    }

    /**
     * Setup a game to use in these tests.
     * @return a prepared Game.
     */
    Game createGame(){
        GameController gameController = new GameController();
        Game game = gameController.getGame();
        Player player = new Player(TokenColor.BLUE);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.YELLOW);
        game.addPlayer(player);
        game.setCurrentPlayer(player);

        player = new Player(TokenColor.YELLOW);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.GREY,
                TokenColor.GREY, TokenColor.BLUE, TokenColor.GREY,
                TokenColor.GREY, TokenColor.BLUE, TokenColor.GREY,
                TokenColor.GREY, TokenColor.BLUE, TokenColor.GREY);
        game.addPlayer(player);

        player = new Player(TokenColor.GREY);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW);
        game.addPlayer(player);

        player = new Player(TokenColor.PURPLE);
        player.setPlayerBoard(new PlayerBoard());
        player.getPlayerBoard().addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.GREY, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.GREY, TokenColor.YELLOW,
                TokenColor.YELLOW, TokenColor.GREY, TokenColor.YELLOW);
        game.addPlayer(player);

        game.createScoreList();
        return game;
    }
}
