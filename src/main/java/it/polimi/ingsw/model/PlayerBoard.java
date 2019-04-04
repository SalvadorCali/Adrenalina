package it.polimi.ingsw.model;

import java.util.List;

public class PlayerBoard {
    private Token[] damageBoard;
    private int deathNumber;
    private List<Token> revengeMarks;

    //getters and setters
    public int getDeathNumber() {
        return deathNumber;
    }

    public void setDeathNumber(int deathNumber) {
        this.deathNumber = deathNumber;
    }

    //methods
    public void addRevengeMarks(){

    }
}
