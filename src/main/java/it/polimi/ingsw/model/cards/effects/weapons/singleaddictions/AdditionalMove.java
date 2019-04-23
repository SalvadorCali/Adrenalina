package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;

public class AdditionalMove extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    private Direction firstMove, secondMove;

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        return false;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

    }
}
