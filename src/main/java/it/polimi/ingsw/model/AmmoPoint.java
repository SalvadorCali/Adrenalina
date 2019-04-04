package it.polimi.ingsw.model;

public class AmmoPoint extends Square {
    private Card ammoCard;
    public AmmoPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
    }

    @Override
    public Card grab(int choice) {
        return ammoCard;
    }
}
