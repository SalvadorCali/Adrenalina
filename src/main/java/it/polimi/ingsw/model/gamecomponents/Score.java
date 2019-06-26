package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;

/**
 * Class which represents the score of the game.
 */
public class Score implements Serializable {
    /**
     * Color of a certain player.
     */
    private TokenColor color;
    /**
     * Score of the player.
     */
    private int score;

    /**
     * Class constructor.
     * @param color color of the player.
     * @param score score of the player.
     */
    public Score(TokenColor color, int score) {
        this.color = color;
        this.score = score;
    }

    /**
     * Getter of the color.
     * @return the color.
     */
    public TokenColor getColor() {
        return color;
    }

    /**
     * Setter of the color.
     * @param color chosen value to set.
     */
    public void setColor(TokenColor color) {
        this.color = color;
    }

    /**
     * Getter of the score.
     * @return the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter of the score.
     * @param score value to set.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
