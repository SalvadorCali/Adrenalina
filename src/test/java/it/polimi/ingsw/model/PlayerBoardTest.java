package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.gamecomponents.PlayerBoard;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.model.enums.TokenColor;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class PlayerBoardTest {
    @Test
    public void addAndResetDamageTest(){
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

    @Test
    public void addAndResetDamageMarks(){
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

    @Test
    public void getFirstBloodKillshotAndOverkillTest(){
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

    @Test
    public void resetBoardTest(){
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

    @Test
    public void scoringTest(){
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.setDamageBoard(createDamageBoard());
        playerBoard.scoring();

        assertEquals(9, playerBoard.getScoreList().get(TokenColor.BLUE).intValue());
        assertEquals(1, playerBoard.getScoreList().get(TokenColor.YELLOW).intValue());
        assertEquals(1, playerBoard.getScoreList().get(TokenColor.GREEN).intValue());
        assertEquals(1, playerBoard.getScoreList().get(TokenColor.GREY).intValue());
        assertEquals(2, playerBoard.getScoreList().get(TokenColor.PURPLE).intValue());
    }

    private Token[] createDamageBoard(){
        Token[] damageBoard = damageBoard = new Token[12];
        damageBoard[0] = new Token(TokenColor.BLUE);
        damageBoard[1] = new Token(TokenColor.YELLOW);
        damageBoard[2] = new Token(TokenColor.GREEN);
        damageBoard[3] = new Token(TokenColor.BLUE);
        damageBoard[4] = new Token(TokenColor.BLUE);
        damageBoard[5] = new Token(TokenColor.GREY);
        damageBoard[6] = new Token(TokenColor.PURPLE);
        damageBoard[7] = new Token(TokenColor.PURPLE);
        damageBoard[8] = new Token(TokenColor.BLUE);
        damageBoard[9] = new Token(TokenColor.BLUE);
        damageBoard[10] = new Token(TokenColor.BLUE);
        damageBoard[11] = new Token(TokenColor.BLUE);

        return damageBoard;
    }
}
