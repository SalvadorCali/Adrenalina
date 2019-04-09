package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.BasicEffect;

public class SquareDamageEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private int x, y;

    boolean canUse = true;

    public SquareDamageEffect(String effectName, int damagePower, int redAmmos, int blueAmmos, int yellowAmmos){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
    }

    //x = actionInterface.getX();
    //y = actionInterface.getY();

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        //x = actionInterface.getX();
        //y = actionInterface.getY();
        canUse = actionInterface.ammoControl(redAmmos, yellowAmmos, blueAmmos); // Electroscythe

        if(effectName.equals("Furnace"))
            canUse = actionInterface.isVisibleDifferentSquare(x, y);
        else if(effectName.equals("Furnace2")) {
            //canUse = actionInterface.distanceControl(x, y);
        }
        return canUse;

    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(effectName.equals("Furnace")) {
            //actionInterface.roomDamage(x,y, damagePower, markPower);
        }else
            //actionInterface.squareDamage(x,y, damagePower, markPower);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);

    }
}
