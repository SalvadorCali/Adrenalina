package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;

public class DamageMarkEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private Player victim;

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
            canUse = actionInterface.sameSquare(victim.getColor());
        else if(effectName.equals("Lock Rifle")|| effectName.equals("T.H.O.R") || effectName.equals("Plasma Gun") || effectName.equals("ZX-2") || effectName.equals("Whisper")) {
            canUse = actionInterface.isVisible(victim.getColor());
            if (effectName.equals("Whisper") && actionInterface.distanceControl(victim.getPosition().getX(),victim.getPosition().getY()) >= 2)
                canUse = true;
        }
        else
            canUse = !actionInterface.isVisible(victim.getColor());

        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        actionInterface.playerDamage(victim.getColor(), damagePower);
        actionInterface.playerMark(victim.getColor(), markPower);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }



}
