package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.gamecomponents.Player;

public class DamageMarkEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private Player currentPlayer, victim, secondVictim, thirdVictim;

    private boolean canUse ;

    public DamageMarkEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos) {

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
        thirdVictim = actionInterface.getThirdVictim();

        canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);

        if (canUse) {
            if (effectName.equals("Cyberblade") || effectName.equals("Sledgehammer"))
                canUse = actionInterface.sameSquare(currentPlayer, victim);
            else if (effectName.equals("Lock Rifle") || effectName.equals("T.H.O.R.") || effectName.equals("Plasma Gun") || effectName.equals("ZX-21") || effectName.equals("Whisper") || effectName.equals("Machine Gun")) {
                canUse = actionInterface.isVisible(currentPlayer, victim);
                System.out.println(canUse);
                if (canUse) {
                    if (effectName.equals("Whisper")) {
                        if (actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) < 2)
                            canUse = false;
                    } else if (effectName.equals("Machine Gun") && secondVictim != null) {
                        canUse = (!secondVictim.getColor().equals(victim.getColor())) && actionInterface.isVisible(currentPlayer, secondVictim);
                    }
                }
            } else if (effectName.equals("Heatseeker") && actionInterface.isVisible(currentPlayer, victim)) {
                canUse = false;
            } else if (effectName.equals("ZX-22")) {
                canUse = actionInterface.isVisible(currentPlayer, victim) && actionInterface.isVisible(currentPlayer, secondVictim) && actionInterface.isVisible(currentPlayer, thirdVictim);
            }
        }
        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        actionInterface.playerDamage(victim.getColor(), damagePower);
        actionInterface.playerMark(victim.getColor(), markPower);
        if (effectName.equals("Machine Gun")) {
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        }else if (effectName.equals("ZX-22")){
            actionInterface.playerMark(secondVictim.getColor(), markPower);
            actionInterface.playerMark(thirdVictim.getColor(), markPower);
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }
}
