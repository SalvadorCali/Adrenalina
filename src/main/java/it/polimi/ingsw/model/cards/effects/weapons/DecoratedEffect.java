package it.polimi.ingsw.model.cards.effects.weapons;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;

public abstract class DecoratedEffect extends Effect {

    protected Effect effect;

    @Override
    public abstract boolean canUseEffect(ActionInterface actionInterface);

    @Override
    public abstract void useEffect(ActionInterface actionInterface);

}
