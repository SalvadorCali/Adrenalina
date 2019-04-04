package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Card;

public class AmmoPoint extends Square {
    private Card ammoCard;
    public AmmoPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
    }

    @Override
    public void grab(Player player, int choice) {

    }
}
