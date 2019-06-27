package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.effects.powerups.PowerupEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.cards.effects.Effect;

/**
 * Class representing the Powerup Cards in the game.
 */
public class PowerupCard extends Card{
    /**
     * Effect of the powerup.
     */
    private Effect effect;

    /**
     * Class constructor of the powerup cards.
     * @param name name of the powerup.
     * @param color color of the powerup.
     * @param effect effect of the powerup.
     */
    public PowerupCard(String name, Color color, String effect) {
        super(name, color, effect);
        setEffect(name);
    }

    /**
     * Sets the different effects of the powerup.
     * @param name name of the card.
     */
    private void setEffect(String name){
        switch (name){
            case "TARGETING SCOPE":
                effect = new PowerupEffect("Targeting Scope");
                break;
            case "NEWTON":
                effect = new PowerupEffect("Newton");
                break;
            case "TAGBACK GRENADE":
                effect = new PowerupEffect("Tagback Grenade");
                break;
            case "TELEPORTER":
                effect = new PowerupEffect("Teleporter");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
    }

    /**
     * Getter of the effect.
     * @return the effect.
     */
    public Effect getEffect(){
        return effect;
    }

    /**
     * toString.
     * @return the string.
     */
    @Override
    public String toString(){
        StringBuilder card = new StringBuilder();
        card.append("Name: " + getName()).append("\nColor: " + getColor()).append("\n" + getCardEffect());
        return card.toString();
    }
}
