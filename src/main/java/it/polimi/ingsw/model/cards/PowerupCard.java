package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.effects.powerups.PowerupEffect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.cards.effects.Effect;

import static it.polimi.ingsw.model.cards.StringCards.*;

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
            case TARGETINGSCOPECAPS:
                effect = new PowerupEffect(TARGETINGSCOPEEFFECT);
                break;
            case NEWTONCAPS:
                effect = new PowerupEffect(NEWTONEFFECT);
                break;
            case TAGBACKGRENADECAPS:
                effect = new PowerupEffect(TAGBACKGRENADEEFFECT);
                break;
            case TELEPORTERCAPS:
                effect = new PowerupEffect(TELEPORTEREFFECT);
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
