package it.polimi.ingsw.model.cards.effects.powerups;

import it.polimi.ingsw.model.cards.effects.ActionInterface;

public class Teleporter extends PowerupEffect {

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        return false;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

    }
}
