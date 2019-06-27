package it.polimi.ingsw.model.cards.effects.weapons;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;

/**
 * Abstract class which extends the Effect class used to apply the decorator pattern.
 */
public abstract class DecoratedEffect extends Effect {

    /**
     * Basic effect inside the Decorated effect.
     */
    protected Effect effect;

    /**
     * Controls if the additional effect can be applied.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    @Override
    public abstract boolean canUseEffect(ActionInterface actionInterface);

    /**
     * Applies the additional effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public abstract void useEffect(ActionInterface actionInterface);
}
