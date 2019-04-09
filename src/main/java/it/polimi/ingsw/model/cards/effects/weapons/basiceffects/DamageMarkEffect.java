package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.TokenColor;

public class DamageMarkEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private TokenColor victim;

    private boolean canUse;

    public DamageMarkEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos ){

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

        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);

        if(effectName.equals("Cyberblade")|| effectName.equals("Sledgehammer"))
            canUse = actionInterface.sameSquare(victim);
        else if(effectName.equals("Lock Rifle")|| effectName.equals("T.H.O.R") || effectName.equals("Plasma Gun") || effectName.equals("ZX-2"))
            canUse = actionInterface.isVisible(victim);
        else
            canUse = !actionInterface.isVisible(victim);

        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        actionInterface.playerDamage(victim, damagePower);
        actionInterface.playerMark(victim, markPower);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }



}
