package it.polimi.ingsw.model.cards.effects.weapons;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.TokenColor;

public class DamageMarkEffect extends BasicEffect {

    private int damagePower;

    private int markPower;

    private int distanceLimit;

    public DamageMarkEffect(int damagePower, int markPower, int distanceLimit){

        this.damagePower = damagePower;
        this.markPower = markPower;
        this.distanceLimit = distanceLimit;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        return false;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(damagePower > 0)
            actionInterface.playerDamage(TokenColor.BLUE, damagePower);
        if(markPower > 0)
            actionInterface.playerMark(TokenColor.BLUE, markPower);

    }
}
