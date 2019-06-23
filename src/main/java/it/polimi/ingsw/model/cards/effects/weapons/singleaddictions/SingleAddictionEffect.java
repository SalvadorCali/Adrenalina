package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.weapons.DecoratedEffect;

/**
 * Abstract class which extends the decorated effect.
 */
public abstract class SingleAddictionEffect extends DecoratedEffect {

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
