package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.BasicEffect;
import it.polimi.ingsw.model.gamecomponents.Player;

public class SquareDamageEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private int x, y;

    private Player victim;

    private boolean canUse;

    public SquareDamageEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        this.canUse = true;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        //victim = actionInterface.getVictim();
        //x = actionInterface.getX();
        //y = actionInterface.getY();
        //x = victim.getPosition().getX();
        //y = victim.getPosition().getY()

        canUse = actionInterface.ammoControl(redAmmos, yellowAmmos, blueAmmos); // Electroscythe

        if(canUse) {
            if (effectName.equals("Furnace") || effectName.equals("Hellion"))
                canUse = actionInterface.isVisibleDifferentSquare(x, y);
            else if (effectName.equals("Furnace2") && (actionInterface.distanceControl(x, y) == 1)) {
                    canUse = true;
            }
        }
        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        if (effectName.equals("Furnace")) {
            actionInterface.roomDamage(x, y, damagePower, markPower);
        } else {
            if (effectName.equals("Hellion")) {
                actionInterface.playerDamage(victim.getColor(), damagePower);
                damagePower = 0;
            }
            actionInterface.squareDamage(x, y, damagePower, markPower);
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }
}
