package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;

/**
 * Class which represents the Damage/Mark tokens.
 */
public class Token implements Serializable {
    /**
     * First color of the token.
     */
    private TokenColor firstColor;
    /**
     * Second color of the token.
     */
    private TokenColor secondColor;

    /**
     * Constructor of the class.
     * @param firstColor first color.
     */
    public Token(TokenColor firstColor){
        this.firstColor = firstColor;
        this.secondColor = TokenColor.NONE;
    }

    /**
     * Getter of the first color.
     * @return the first color.
     */
    public TokenColor getFirstColor() {
        return firstColor;
    }

    /**
     * Setter of the first color.
     * @param firstColor chosen first color.
     */
    public void setFirstColor(TokenColor firstColor) {
        this.firstColor = firstColor;
    }

    /**
     * Getter of the second color.
     * @return the second color.
     */
    public TokenColor getSecondColor() {
        return secondColor;
    }

    /**
     * Setter of the second color.
     * @param secondColor chosen second color.
     */
    void setSecondColor(TokenColor secondColor) {
        this.secondColor = secondColor;
    }
}
