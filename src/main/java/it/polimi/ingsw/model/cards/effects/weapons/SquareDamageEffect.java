package it.polimi.ingsw.model.cards.effects.weapons;

import it.polimi.ingsw.model.cards.effects.ActionInterface;

public class SquareDamageEffect extends BasicEffect {

    private int damagePower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private int x, y;

    boolean firstMode;

    //x = actionInterface.getX();
    //y = actionInterface.getY();

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        //x = actionInterface.getX();
        //y = actionInterface.getY();

        if(redAmmos + blueAmmos + yellowAmmos == 0 || (redAmmos == 1 && blueAmmos == 1 && yellowAmmos == 0))
            return ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface); //Falce Protonica
        if(redAmmos == 0 && blueAmmos == 1 && yellowAmmos == 0)
            if(firstMode)
                return ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && actionInterface.isVisibleDifferentSquare(x, y); //Vulcanizzatore (Mod1)
            else
                return false;
                //return ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && actionInterface.distanceControl(x, y); //Vulcanizzatore(Mod2)

        return false;

    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

    }
}
