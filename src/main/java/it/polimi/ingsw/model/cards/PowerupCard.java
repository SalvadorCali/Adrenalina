package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.effects.powerups.PowerupEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.cards.effects.Effect;

public class PowerupCard extends Card{

    private int powerupId;
    private Effect effect;

    public PowerupCard(String name, Color color, String effect) {
        super(name, color, effect);
        setEffect(name);
    }
    private void setEffect(String name){
        switch (name){
            case "TARGETING SCOPE":
                Effect targetingScope = new PowerupEffect("Targeting Scope");
                effect = targetingScope;
                break;
            case "NEWTON":
                Effect newton = new PowerupEffect("Newton");
                effect = newton;
                break;
            case "TAGBACK GRENADE":
                Effect tagbackGrenade = new PowerupEffect("Tagback Grenade");
                effect = tagbackGrenade;
                break;
            case "TELEPORTER":
                Effect teleporter = new PowerupEffect("Teleporter");
                effect = teleporter;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
    }
    public Effect getEffect(){
        return effect;
    }

}
