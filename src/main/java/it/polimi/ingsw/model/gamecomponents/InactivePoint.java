package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

public class InactivePoint extends Square {
    public InactivePoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
    }

    @Override
    public void grab(ActionInterface actionInterface, int choice) {
    }

}