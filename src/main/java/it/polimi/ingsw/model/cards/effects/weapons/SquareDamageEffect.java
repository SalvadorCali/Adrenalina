package it.polimi.ingsw.model.cards.effects.weapons;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Color;

public class SquareDamageEffect extends BasicEffect {

    private int damagePower;

    private int ammoNumber, secondAmmoNumber;

    private Color ammoColor, secondAmmoColor;

    private int x, y;

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        if(ammoNumber == 0)
            return ammoControl(ammoNumber, ammoColor, actionInterface);
        if(ammoNumber == 1)
            return ammoControl(ammoNumber, ammoColor, actionInterface) && actionInterface.isVisibleDifferentSquare(x, y);
        if(ammoNumber == 2)
            return ammoControl(ammoNumber, ammoColor, actionInterface) && ammoControl(secondAmmoNumber, secondAmmoColor, actionInterface);



        return false;

    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

    }
}
