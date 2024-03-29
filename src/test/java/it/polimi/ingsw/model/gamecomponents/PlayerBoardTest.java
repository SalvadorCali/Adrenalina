package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests referring to the PlayerBoard class.
 */
class PlayerBoardTest {

    /**
     * Tests the correct update/reset of the damages present on the player board.
     */
    @Test
    void addAndResetDamageTest(){
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addDamage(TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE);
        assertEquals(AdrenalineZone.FIRST, playerBoard.getAdrenalineZone());
        assertNotEquals(AdrenalineZone.DEFAULT, playerBoard.getAdrenalineZone());

        assertEquals(3, playerBoard.getDamageIndex());
        assertNotEquals(2, playerBoard.getDamageIndex());

        Token[] damageBoard = playerBoard.getDamageBoard();
        assertEquals(TokenColor.YELLOW, damageBoard[0].getFirstColor());
        assertEquals(TokenColor.BLUE, damageBoard[1].getFirstColor());
        assertEquals(TokenColor.PURPLE, damageBoard[2].getFirstColor());
        assertEquals(TokenColor.NONE, damageBoard[3].getFirstColor());
        assertNotEquals(TokenColor.GREEN, damageBoard[4].getFirstColor());

        playerBoard.resetDamage();
        assertEquals(0, playerBoard.getDamageIndex());
        assertNotEquals(3, playerBoard.getDamageIndex());

        for(int i=0; i<12; i++){
            assertEquals(TokenColor.NONE, damageBoard[1].getFirstColor());
        }
    }

    /**
     * Tests the correct update/reset of the marks present on the player board.
     */
    @Test
    void addAndResetDamageMarks(){
        PlayerBoard playerBoard = new PlayerBoard();

        playerBoard.addRevengeMarks(TokenColor.BLUE);
        playerBoard.addRevengeMarks(TokenColor.GREY);
        assertEquals(TokenColor.BLUE, playerBoard.getRevengeMarks().get(0).getFirstColor());
        assertNotEquals(TokenColor.GREY, playerBoard.getRevengeMarks().get(0).getFirstColor());
        assertEquals(TokenColor.GREY, playerBoard.getRevengeMarks().get(1).getFirstColor());
        assertNotEquals(TokenColor.GREEN, playerBoard.getRevengeMarks().get(0).getFirstColor());
        assertEquals(2, playerBoard.getRevengeMarks().size());

        playerBoard.resetRevengeMarks();
        assertEquals(0, playerBoard.getRevengeMarks().size());
        assertNotEquals(2, playerBoard.getRevengeMarks().size());
    }

    /**
     * Tests the correct update of the first blood and overkill tokens on the player board.
     */
    @Test
    void getFirstBloodKillshotAndOverkillTest(){
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addDamage(TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE);

        assertEquals(TokenColor.YELLOW, playerBoard.getFirstBlood());
        assertEquals(TokenColor.BLUE, playerBoard.getKillshot());
        assertEquals(TokenColor.PURPLE, playerBoard.getOverkill());
        assertNotEquals(TokenColor.NONE, playerBoard.getFirstBlood());
        assertNotEquals(TokenColor.NONE, playerBoard.getKillshot());
        assertNotEquals(TokenColor.NONE, playerBoard.getOverkill());
    }

    /**
     * Tests the correct reset of the board.
     */
    @Test
    void resetBoardTest(){
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addDamage(TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE);

        playerBoard.resetBoard();
        assertEquals(TokenColor.PURPLE, playerBoard.getRevengeMarks().get(0).getFirstColor());
        assertEquals(0, playerBoard.getDamageIndex());
        assertEquals(AdrenalineZone.DEFAULT, playerBoard.getAdrenalineZone());
    }

    /**
     * Tests the correct evaluation of the score of each player.
     */
    @Test
    void scoringTest(){
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE);
        ArrayList<TokenColor> playerColors = new ArrayList<>();
        playerColors.add(TokenColor.YELLOW);
        playerColors.add(TokenColor.BLUE);
        playerColors.add(TokenColor.PURPLE);
        playerColors.add(TokenColor.GREY);
        Map<TokenColor, Score> newScore = playerBoard.scoring(playerColors);

        assertEquals(9, newScore.get(TokenColor.BLUE).getScore());
        assertEquals(6, newScore.get(TokenColor.PURPLE).getScore());
        assertEquals(4, newScore.get(TokenColor.YELLOW).getScore());
        assertEquals(0, newScore.get(TokenColor.GREY).getScore());
    }

    /**
     * Tests the correct evaluation of the score of each player including overkills.
     */
    @Test
    void scoringWithOverkillTest(){
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW);
        ArrayList<TokenColor> playerColors = new ArrayList<>();
        playerColors.add(TokenColor.YELLOW);
        playerColors.add(TokenColor.BLUE);
        playerColors.add(TokenColor.PURPLE);
        playerColors.add(TokenColor.GREY);
        Map<TokenColor, Score> newScore = playerBoard.scoring(playerColors);

        assertEquals(9, newScore.get(TokenColor.BLUE).getScore());
        assertEquals(6, newScore.get(TokenColor.YELLOW).getScore());
        assertEquals(4, newScore.get(TokenColor.PURPLE).getScore());
        assertEquals(0, newScore.get(TokenColor.GREY).getScore());
    }

    /**
     * Tests the correct evaluation of the score of each player in the final frenzy.
     */
    @Test
    void scoringFinalFrenzyTest(){
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setFinalFrenzy(true);
        playerBoard.addDamage(TokenColor.BLUE, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.PURPLE,
                TokenColor.YELLOW, TokenColor.BLUE, TokenColor.YELLOW);
        ArrayList<TokenColor> playerColors = new ArrayList<>();
        playerColors.add(TokenColor.YELLOW);
        playerColors.add(TokenColor.BLUE);
        playerColors.add(TokenColor.PURPLE);
        playerColors.add(TokenColor.GREY);
        Map<TokenColor, Score> newScore = playerBoard.scoring(playerColors);

        assertEquals(2, newScore.get(TokenColor.BLUE).getScore());
        assertEquals(1, newScore.get(TokenColor.YELLOW).getScore());
        assertEquals(1, newScore.get(TokenColor.PURPLE).getScore());
        assertEquals(0, newScore.get(TokenColor.GREY).getScore());
    }
}
