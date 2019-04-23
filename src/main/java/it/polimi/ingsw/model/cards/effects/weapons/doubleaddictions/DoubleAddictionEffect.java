package it.polimi.ingsw.model.cards.effects.weapons.doubleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.weapons.DecoratedEffect;

public abstract class DoubleAddictionEffect extends DecoratedEffect {

    @Override
    public abstract boolean canUseEffect(ActionInterface actionInterface);

    @Override
    public abstract void useEffect(ActionInterface actionInterface);
}
