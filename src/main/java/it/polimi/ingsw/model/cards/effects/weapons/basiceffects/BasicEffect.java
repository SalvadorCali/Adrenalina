package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.enums.Color;

public abstract class BasicEffect extends Effect {

    public abstract boolean canUseEffect(ActionInterface actionInterface);

    public abstract void useEffect(ActionInterface actionInterface);

    protected boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos, ActionInterface actionInterface){
        return (actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos));
    }

}