package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Printer;

/**
 * Class representing the effects which add damages to new victims.
 */
public class AdditionalTarget extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private Player currentPlayer, victim, additionalVictim, thirdVictim;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse, basicFirst;

    /**
     * Class constructor.
     * @param effectName name of the effect.
     * @param damagePower number of damages to add to the new victims.
     * @param markPower number of marks to add to the new victims.
     * @param redAmmos red ammos required to apply the additional effect.
     * @param blueAmmos blue ammos required to apply the additional effect.
     * @param yellowAmmos yellow ammos required to apply the additional effect.
     * @param effect basic effect that is completed by the additional effect.
     */
    public AdditionalTarget(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos, Effect effect){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        this.basicFirst = true;
        super.effect = effect;
    }

    /**
     * Controls that the additional damages/marks can be added without errors.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        setData(actionInterface);
        if(basicFirst) {
            Printer.println(actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos));
            canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
            if (canUse && !effectName.equals("Plasma Gun Double") && !effectName.equals("Machine Gun")) {
                if (effectName.equals("Lock Rifle")) {
                    canUse = actionInterface.isVisible(currentPlayer, additionalVictim);
                } else if (effectName.equals("T.H.O.R. Single")) {
                    canUse = actionInterface.isVisible(victim, additionalVictim);
                } else if (effectName.equals("Machine Gun Double")) {
                    canUse = actionInterface.isVisible(currentPlayer, thirdVictim);
                } else if (effectName.equals("T.H.O.R. Double")) {
                    canUse = actionInterface.isVisible(additionalVictim, thirdVictim);
                } else {
                    Printer.print("SHOOTER:" +currentPlayer.getPosition().getX());
                    Printer.println(currentPlayer.getPosition().getX());
                    Printer.print("VICTIM:"+additionalVictim.getPosition().getX());
                    Printer.println(additionalVictim.getPosition().getY());
                    canUse = actionInterface.sameSquare(currentPlayer, additionalVictim);
                }
            }
        }else {
            if(effectName.equals("Cyberblade")) {
                canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && actionInterface.sameSquare(currentPlayer, additionalVictim);
                actionInterface.getClientData().setBasicFirst(true);
                if(canUse)
                    super.effect.canUseEffect(actionInterface);
            }else if(effectName.equals("Plasma Gun Double"))
                canUse = actionInterface.ammoControl(redAmmos,blueAmmos,yellowAmmos) && super.effect.canUseEffect(actionInterface);
        }
        return canUse;
    }

    /**
     * Applies the additional effect and the basic effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public void useEffect(ActionInterface actionInterface) {

        super.effect.useEffect(actionInterface);
        if(effectName.equals("Plasma Gun Double")){
            actionInterface.playerDamage(victim.getColor(), damagePower);
        }
        if(effectName.equals("Machine Gun Double") || effectName.equals("T.H.O.R. Double")){
            actionInterface.playerDamage(thirdVictim.getColor(), damagePower);
        }else{
            actionInterface.playerDamage(additionalVictim, damagePower);
            actionInterface.playerMark(additionalVictim, markPower);
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    /**
     * Sets the data getting them from the ClientData class.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void setData(ActionInterface actionInterface){
        this.basicFirst = actionInterface.basicFirst();
        if(!basicFirst)
            actionInterface.getClientData().setAmmos();
        if(effectName.equals("Cyberblade") && basicFirst)
            currentPlayer = actionInterface.getClientData().getFakePlayer();
        else
            currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getVictim();
        additionalVictim = actionInterface.getSecondVictim();
        thirdVictim = actionInterface.getThirdVictim();
    }
}
