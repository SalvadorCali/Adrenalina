package it.polimi.ingsw.model.cards.effects;

public abstract class Effect {

    public abstract boolean canUseEffect(ActionInterface actionInterface);

    public abstract void useEffect(ActionInterface actionInterface);

}
