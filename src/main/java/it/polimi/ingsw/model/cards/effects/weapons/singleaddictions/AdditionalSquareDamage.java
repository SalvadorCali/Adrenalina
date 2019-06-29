package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import static it.polimi.ingsw.model.cards.StringCards.*;


/**
 * Class representing the additional damages involving squares/rooms.
 */
public class AdditionalSquareDamage extends SingleAddictionEffect{

    /**
     * Name of the effect.
     */
    private String effectName;

    /**
     * Additional square to damage.
     */
    private int x, y;

    /**
     * Number of damages given by the effect.
     */
    private int damagePower;

    /**
     * Cost of the effect.
     */
    private int redAmmos, blueAmmos, yellowAmmos;

    /**
     * Indicates if the player wants to use the additional effect before the basic one.
     */
    private boolean basicFirst;

    /**
     * Victims of the effect.
     */
    private boolean victim1, victim2;

    /**
     * Indicates if the effect can be used.
     */
    private boolean canUse;


    /**
     * Class constructor.
     * @param effectName name of the effect.
     * @param damagePower number of damages to add to the victims on the square.
     * @param redAmmos red ammos required to apply the additional effect.
     * @param blueAmmos blue ammos required to apply the additional effect.
     * @param yellowAmmos yellow ammos required to apply the additional effect.
     * @param effect basic effect that is completed by the additional effect
     */
    public AdditionalSquareDamage(String effectName, int damagePower, int redAmmos, int blueAmmos, int yellowAmmos, Effect effect){
        this.effectName = effectName;
        this.damagePower = damagePower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        super.effect = effect;
    }

    /**
     * Controls that the additional square damage can be applied.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        setData(actionInterface);
        if (basicFirst) {
            canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
            if (canUse && !effectName.equals(ROCKETLAUNCHEREFFECT) && !effectName.equals(VORTEXCANNONEFFECT))
                canUse = actionInterface.isVisibleDifferentSquare(x, y);
            if(canUse && effectName.equals(VORTEXCANNONEFFECT)) {
                if(actionInterface.distanceControl(actionInterface.getClientData().getSecondVictim(), x, y)  >= 2)
                    canUse = false;
                if(actionInterface.distanceControl(actionInterface.getClientData().getSecondVictim(), x, y)  < 2)
                    victim1 = true;
                if(actionInterface.getClientData().getThirdVictim()!= null){
                    if(actionInterface.distanceControl(actionInterface.getClientData().getThirdVictim(), x, y)  < 2)
                        victim2 = true;
                    else
                        canUse = false;
                }
            }
        } else {
            canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
            if (canUse) {
                if (!effectName.equals(ROCKETLAUNCHEREFFECT)) {
                    canUse = actionInterface.isVisibleDifferentSquare(x, y) || (x == actionInterface.getClientData().getCurrentPlayer().getPosition().getX() && y == actionInterface.getClientData().getCurrentPlayer().getPosition().getY());
                }
                if (canUse)
                    canUse = super.effect.canUseEffect(actionInterface);
            }
        }
        return canUse;
    }

    /**
     * Applies the additional and the basic effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public void useEffect(ActionInterface actionInterface) {
        super.effect.useEffect(actionInterface);
        if(!effectName.equals(VORTEXCANNONEFFECT)) {
            actionInterface.squareDamage(x, y, damagePower, 0);
            if (effectName.equals(ROCKETLAUNCHEREFFECT) && (actionInterface.getVictim().getPosition().getX() != x || actionInterface.getVictim().getPosition().getY() != y))
                actionInterface.playerDamage(actionInterface.getVictim().getColor(), damagePower);
        }else{
            if(victim1){
                actionInterface.playerDamage(actionInterface.getSecondVictim(), damagePower);
                actionInterface.move(x, y, actionInterface.getSecondVictim());
                victim1 = false;
            }
            if(victim2){
                actionInterface.playerDamage(actionInterface.getThirdVictim(), damagePower);
                actionInterface.move(x, y, actionInterface.getThirdVictim());
                victim2 = false;
            }
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    /**
     * Sets the data getting them from the ClientData class.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void setData(ActionInterface actionInterface){
        if(effectName.equals(ROCKETLAUNCHEREFFECT)){
            actionInterface.getClientData().setSquare(actionInterface.getClientData().getVictim().getPosition().getX(), actionInterface.getClientData().getVictim().getPosition().getY());
            this.x = actionInterface.getClientData().getSquare().getX();
            this.y = actionInterface.getClientData().getSquare().getY();
        }else {
            this.x = actionInterface.getClientData().getSquare().getX();
            this.y = actionInterface.getClientData().getSquare().getY();
        }
        this.basicFirst = actionInterface.basicFirst();
        if(!basicFirst)
            actionInterface.getClientData().setAmmos();
    }
}
