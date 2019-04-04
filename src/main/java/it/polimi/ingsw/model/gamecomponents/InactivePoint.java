package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.Card;

public class InactivePoint extends Square {
    public InactivePoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
    }

    @Override
    public Card grab(int choice) {
        return null;
    }
}
