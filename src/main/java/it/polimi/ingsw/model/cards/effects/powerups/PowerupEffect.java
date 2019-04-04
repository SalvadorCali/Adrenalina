package it.polimi.ingsw.model.cards.effects.powerups;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;

public abstract class PowerupEffect extends Effect {

    public abstract boolean canUseEffect(ActionInterface actionInterface);

    public abstract void useEffect(ActionInterface actionInterface);

}
