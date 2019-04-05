package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private Token[] damageBoard;
    private static final int MAX_DAMAGE = 12;
    private int damageIndex;
    private int deathNumber;
    private List<Token> revengeMarks;
    private AdrenalineZone adrenalineZone;

    public PlayerBoard(){
        damageBoard = new Token[MAX_DAMAGE];
        for(int i=0; i<MAX_DAMAGE; i++){
            damageBoard[i] = new Token(TokenColor.NONE);
        }
        damageIndex = 0;
        deathNumber = 0;
        revengeMarks = new ArrayList<>();
        adrenalineZone = AdrenalineZone.DEFAULT;
    }

    //getters and setters
    public Token[] getDamageBoard() {
        return damageBoard;
    }

    public void setDamageBoard(Token[] damageBoard) {
        this.damageBoard = damageBoard;
    }

    public int getDamageIndex() {
        return damageIndex;
    }

    public void setDamageIndex(int damageIndex) {
        this.damageIndex = damageIndex;
    }

    public List<Token> getRevengeMarks() {
        return revengeMarks;
    }

    public void setRevengeMarks(List<Token> revengeMarks) {
        this.revengeMarks = revengeMarks;
    }

    public int getDeathNumber() {
        return deathNumber;
    }

    public void setDeathNumber(int deathNumber) {
        this.deathNumber = deathNumber;
    }

    public AdrenalineZone getAdrenalineZone(){
        return adrenalineZone;
    }

    //methods
    public void addRevengeMarks(TokenColor color){
        revengeMarks.add(new Token(color));
    }

    public void resetRevengeMarks(){
        revengeMarks.clear();
    }

    public void addDamage(TokenColor...colors){
        for(TokenColor color : colors){
            damageBoard[damageIndex] = new Token(color);
            damageIndex++;
            if(damageIndex == 2){
                adrenalineZone = AdrenalineZone.FIRST;
            }
            else if(damageIndex == 5){
                adrenalineZone = AdrenalineZone.SECOND;
            }
        }
    }

    public void resetDamage(){
        for(int i=0; i<MAX_DAMAGE; i++){
            damageBoard[i] = new Token(TokenColor.NONE);
        }
        damageIndex = 0;
    }

    public TokenColor getFirstBlood(){
        return damageBoard[0].getFirstColor();
    }

    public TokenColor getKillshot(){
        return damageBoard[10].getFirstColor();
    }

    public TokenColor getOverkill(){
        return damageBoard[11].getFirstColor();
    }

    public void resetBoard(){
        if(!getOverkill().equals(TokenColor.NONE)){
            addRevengeMarks(getOverkill());
        }
        resetDamage();
        adrenalineZone = AdrenalineZone.DEFAULT;
    }
}
