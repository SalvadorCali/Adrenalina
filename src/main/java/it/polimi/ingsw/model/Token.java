package it.polimi.ingsw.model;

public class Token {
    private TokenColor firstColor;
    private TokenColor secondColor;
    private boolean overkill;

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
