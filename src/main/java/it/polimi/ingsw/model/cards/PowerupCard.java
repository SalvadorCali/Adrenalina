package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.cards.effects.Effect;

public class PowerupCard extends Card {

    private int powerupId;
    private Effect effect;

    public PowerupCard(String name, Color color) {
        super(name, color);
    }
}
