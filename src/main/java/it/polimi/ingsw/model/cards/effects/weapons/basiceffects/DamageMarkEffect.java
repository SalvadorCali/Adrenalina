package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.gamecomponents.Player;

public class DamageMarkEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private Player currentPlayer, victim, secondVictim, thirdVictim;

    private boolean canUse;

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

        setData(actionInterface);
        canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
        if (canUse) {
            if (effectName.equals("Cyberblade") || effectName.equals("Sledgehammer1"))
                canUse = actionInterface.sameSquare(currentPlayer, victim);
            else if (effectName.equals("Lock Rifle") || effectName.equals("T.H.O.R.") || effectName.equals("Plasma Gun") || effectName.equals("ZX-21") || effectName.equals("Whisper") || effectName.equals("Machine Gun"))
                visibilityCheck(actionInterface);
            else if (effectName.equals("Heatseeker") && actionInterface.isVisible(currentPlayer, victim)) {
                canUse = false;
            }else if (effectName.equals("ZX-22"))
                canUse = actionInterface.isVisible(currentPlayer, victim) && actionInterface.isVisible(currentPlayer, secondVictim) && actionInterface.isVisible(currentPlayer, thirdVictim);
        }
        return canUse;
    }
    @Override
    public void useEffect(ActionInterface actionInterface) {

        actionInterface.playerDamage(victim, damagePower);
        actionInterface.playerMark(victim, markPower);
        if (effectName.equals("Machine Gun")) {
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        }else if (effectName.equals("ZX-22")){
            actionInterface.playerMark(secondVictim, markPower);
            actionInterface.playerMark(thirdVictim, markPower);
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    private void visibilityCheck(ActionInterface actionInterface) {

        canUse = actionInterface.isVisible(currentPlayer, victim);
        if (canUse) {
            if (effectName.equals("Whisper")) {
                if (actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) < 2)
                    canUse = false;
            } else if (effectName.equals("Machine Gun") && secondVictim != null) {
                canUse = (!secondVictim.getColor().equals(victim.getColor())) && actionInterface.isVisible(currentPlayer, secondVictim);
            }
        }
    }

    private void setData(ActionInterface actionInterface){

        if(actionInterface.basicFirst()) {
            currentPlayer = actionInterface.getClientData().getCurrentPlayer();
            actionInterface.getClientData().setAmmos();
        }
        else
            currentPlayer = actionInterface.getClientData().getFakePlayer();
        victim = actionInterface.getVictim();
        secondVictim = actionInterface.getSecondVictim();
        thirdVictim = actionInterface.getThirdVictim();
    }
}
