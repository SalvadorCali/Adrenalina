package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.gamecomponents.Player;

public class AdditionalTarget extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private Player currentPlayer, victim, additionalVictim;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    public AdditionalTarget(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos, Effect effect){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        super.effect = effect;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getVictim();
        additionalVictim = actionInterface.getSecondVictim();


        canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) ;
        if(canUse) {
            if (effectName.equals("Lock Rifle")){
                canUse = actionInterface.isVisible(currentPlayer, additionalVictim);
            }
            else if(effectName.equals("T.H.O.R.")) {
                canUse = actionInterface.isVisible(victim, additionalVictim);
            }
        }

        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        super.effect.useEffect(actionInterface);
        actionInterface.playerDamage(additionalVictim.getColor(), damagePower);
        actionInterface.playerMark(additionalVictim.getColor(), markPower);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }
}
