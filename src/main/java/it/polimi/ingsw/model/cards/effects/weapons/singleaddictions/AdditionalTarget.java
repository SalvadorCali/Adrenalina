package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.gamecomponents.Player;

public class AdditionalTarget extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private Player victim, additionalVictim, player;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    public AdditionalTarget(String effectName, int damagePower, int markPower, Player victim, Player additionalVictim, int redAmmos, int blueAmmos, int yellowAmmos){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.victim = victim;
        this.additionalVictim = additionalVictim;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        //victim = actionInterface.getVictim();
        //additionalVictim = actionInterface.getAdditionalVictim();

        canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) ;
        if(canUse) {
            if (effectName.equals("Lock Rifle")){
                canUse = !victim.getColor().equals(additionalVictim.getColor()) && actionInterface.isVisible(additionalVictim.getColor());
            }
            else if(effectName.equals("T.H.O.R.")) {
                canUse = actionInterface.isVisible(victim.getColor(), additionalVictim.getColor());
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
