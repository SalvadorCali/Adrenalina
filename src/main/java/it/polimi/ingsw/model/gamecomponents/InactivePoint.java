package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;

public class InactivePoint extends Square implements Serializable {
    public InactivePoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east, false);
    }

    @Override
    public void grab(ActionInterface actionInterface, int choice) {
    }

    @Override
    public void fill(ActionInterface actionInterface) {

    }

}