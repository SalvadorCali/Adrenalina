package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;

public class Score implements Serializable {
    private TokenColor color;
    private int score;
    private boolean max;

    public Score(TokenColor color, int score) {
        this.color = color;
        this.score = score;
        this.max = false;
    }

    public TokenColor getColor() {
        return color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isMax() {
        return max;
    }

    public void setMax(boolean max) {
        this.max = max;
    }
}
