package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;

public class AdditionalSquareDamage extends SingleAddictionEffect{

    private int x, y;

    private int damagePower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean basicFirst;

    private boolean canUse;


    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        if(basicFirst){ //Grenade Launcher
            canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
            if(canUse)
                canUse = actionInterface.isVisibleDifferentSquare(x, y);// || actionInterface.sameSquare(actionInterface.getCurrentPlayer().getColor()); //actionInterface.getPlayerPos()
        }else{
            //canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && (actionInterface.isVisibleDifferentSquare(x, y) || actionInterface.sameSquare(actionInterface.getCurrentPlayer().getColor())); //actionInterface.getPlayerPos()
                if(canUse)
                    canUse = super.effect.canUseEffect(actionInterface);
        }
        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        super.effect.useEffect(actionInterface);
        actionInterface.squareDamage(x, y, damagePower, 0);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);

    }
}
