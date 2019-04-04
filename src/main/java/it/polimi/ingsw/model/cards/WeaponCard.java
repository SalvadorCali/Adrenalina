package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.gamecomponents.Color;
import it.polimi.ingsw.model.cards.effects.Effect;

import java.util.List;

public class WeaponCard extends Card {

    private int weaponId;
    private List<Effect> effects;

    public WeaponCard(String name, Color color) {
        super(name, color);
    }
}
