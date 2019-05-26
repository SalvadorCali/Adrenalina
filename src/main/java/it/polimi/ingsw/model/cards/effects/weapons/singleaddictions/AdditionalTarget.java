package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.gamecomponents.Player;

public class AdditionalTarget extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private Player currentPlayer, victim, additionalVictim, thirdVictim;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse, basicFirst;

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

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        setData(actionInterface);
        if(basicFirst) {
            canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
            if (canUse) {
                if (effectName.equals("Lock Rifle")) {
                    canUse = actionInterface.isVisible(currentPlayer, additionalVictim);
                } else if (effectName.equals("T.H.O.R. Single")) {
                    canUse = actionInterface.isVisible(victim, additionalVictim);
                } else if (effectName.equals("Machine Gun Double")) {
                    canUse = actionInterface.isVisible(currentPlayer, thirdVictim);
                } else if (effectName.equals("T.H.O.R. Double")) {
                    canUse = actionInterface.isVisible(additionalVictim, thirdVictim);
                } else {
                    canUse = actionInterface.sameSquare(currentPlayer, additionalVictim);
                }
            }
        }else {
            canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && actionInterface.sameSquare(currentPlayer,additionalVictim) && super.effect.canUseEffect(actionInterface) ;
        }
        return canUse;
    }

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

    private void setData(ActionInterface actionInterface){

        this.basicFirst = actionInterface.basicFirst();
        if(!basicFirst)
            actionInterface.getClientData().setAmmos();
        if(effectName.equals("Cyberblade Double"))
            currentPlayer = actionInterface.getClientData().getFakePlayer();
        else
            currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getVictim();
        additionalVictim = actionInterface.getSecondVictim();
        thirdVictim = actionInterface.getThirdVictim();
    }
}
