package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.cards.effects.Effect;

import java.io.Serializable;

public class PowerupCard extends Card{

    private int powerupId;
    private Effect effect;

    public PowerupCard(String name, Color color) {
        super(name, color);
    }
}
