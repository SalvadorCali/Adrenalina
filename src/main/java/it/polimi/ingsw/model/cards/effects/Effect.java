package it.polimi.ingsw.model.cards.effects;

import java.io.Serializable;

/**
 * Abstract class representing the effects contained in the cards.
 */
public abstract class Effect implements Serializable {

    /**
     * Controls that the effect can be applied.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    public abstract boolean canUseEffect(ActionInterface actionInterface);

    /**
     * Applies the effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    public abstract void useEffect(ActionInterface actionInterface);
}
