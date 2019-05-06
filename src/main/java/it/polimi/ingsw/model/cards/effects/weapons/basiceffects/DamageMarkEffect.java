package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.gamecomponents.Player;

public class DamageMarkEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private Player victim, secondVictim;

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
        //secondVictim = actionInterface.getSecondVictim();

        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);

        if (canUse) {

            if (effectName.equals("Cyberblade") || effectName.equals("Sledgehammer"))
                canUse = actionInterface.sameSquare(victim.getColor());
            else if (effectName.equals("Lock Rifle") || effectName.equals("T.H.O.R") || effectName.equals("Plasma Gun") || effectName.equals("ZX-2") || effectName.equals("Whisper") || effectName.equals("Machine Gun")) {
                canUse = actionInterface.isVisible(actionInterface.getCurrentPlayer().getColor(),victim.getColor()); //getPlayerInClientInfo
                if (canUse && effectName.equals("Whisper") && actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) >= 2)
                    canUse = true;
                else if(canUse && effectName.equals("Machine Gun") && secondVictim!= null)
                    canUse = actionInterface.isVisible(secondVictim.getColor());
            }else
                canUse = !actionInterface.isVisible(victim.getColor()); //Heatseeker
        }
        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        actionInterface.playerDamage(victim.getColor(), damagePower);
        actionInterface.playerMark(victim.getColor(), markPower);
        if(effectName.equals("Machine Gun"))
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);

    }

}
