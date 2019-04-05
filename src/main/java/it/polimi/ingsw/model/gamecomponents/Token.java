package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.TokenColor;

public class Token {
    private TokenColor firstColor;
    private TokenColor secondColor;
    private boolean overkill;

    public Token(TokenColor firstColor){
        this.firstColor = firstColor;
        this.secondColor = TokenColor.NONE;
        this.overkill = false;
    }

    public Token(TokenColor firstColor, TokenColor secondColor){
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.overkill = true;
    }

    public TokenColor getFirstColor() {
        return firstColor;
    }

    public void setFirstColor(TokenColor firstColor) {
        this.firstColor = firstColor;
    }

    public TokenColor getSecondColor() {
        return secondColor;
    }

    public void setSecondColor(TokenColor secondColor) {
        this.secondColor = secondColor;
    }

    public boolean isOverkill() {
        return overkill;
    }

    public void setOverkill(boolean overkill) {
        this.overkill = overkill;
    }
}
