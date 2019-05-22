package it.polimi.ingsw.model.cards.effects;

import java.io.Serializable;

public abstract class Effect implements Serializable {

    public abstract boolean canUseEffect(ActionInterface actionInterface);

    public abstract void useEffect(ActionInterface actionInterface);

}
