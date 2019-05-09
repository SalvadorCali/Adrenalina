package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.gamecomponents.Player;

public class DamageMarkEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private Player currentPlayer, victim, secondVictim;

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

        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getVictim();
        secondVictim = actionInterface.getSecondVictim();

        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);
        //actionInterface.updateFakeAmmoBox(redAmmos,blueAmmos,yellowAmmos);

        if (canUse){

            if (effectName.equals("Cyberblade") || effectName.equals("Sledgehammer"))
                canUse = actionInterface.sameSquare(currentPlayer , victim);

            else if (effectName.equals("Lock Rifle") || effectName.equals("T.H.O.R") || effectName.equals("Plasma Gun") || effectName.equals("ZX-2") || effectName.equals("Whisper") || effectName.equals("Machine Gun")) {
                canUse = actionInterface.isVisible(currentPlayer, victim);
                if (canUse) {
                    if (effectName.equals("Whisper")) {
                        if (actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) < 2)
                            canUse = false;
                        }
                        else if (effectName.equals("Machine Gun") && secondVictim != null) {
                            canUse = actionInterface.isVisible(secondVictim.getColor());
                        }
                        else
                            canUse = !actionInterface.isVisible(victim.getColor()); //Heatseeker
                }
            }
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
