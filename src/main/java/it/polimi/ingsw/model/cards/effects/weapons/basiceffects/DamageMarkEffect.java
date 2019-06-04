package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.gamecomponents.Player;

public class DamageMarkEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private boolean visible1, visible2, visible3;

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
            }else if (effectName.equals("ZX-22")) {
                canUse = actionInterface.isVisible(currentPlayer, victim) || actionInterface.isVisible(currentPlayer, secondVictim) || actionInterface.isVisible(currentPlayer, thirdVictim);
                if (canUse) {
                    if (actionInterface.isVisible(currentPlayer, victim)) {
                        visible1 = true;
                    }
                    if (actionInterface.isVisible(currentPlayer, secondVictim)) {
                        visible2 = true;
                    }
                    if (actionInterface.isVisible(currentPlayer, thirdVictim)) {
                        visible3 = true;
                    }
                }
            }else if(effectName.equals("Shockwave")){
                canUse = actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) == 1 || actionInterface.distanceControl(secondVictim.getPosition().getX(), secondVictim.getPosition().getY()) == 1 || actionInterface.distanceControl(thirdVictim.getPosition().getX(), thirdVictim.getPosition().getY()) == 1;
                if(canUse){
                    if (actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) == 1) {
                        visible1 = true;
                    }
                    if (secondVictim!=null && actionInterface.distanceControl(secondVictim.getPosition().getX(), secondVictim.getPosition().getY()) == 1) {
                        visible2 = true;
                    }
                    if (thirdVictim!=null && actionInterface.distanceControl(thirdVictim.getPosition().getX(), thirdVictim.getPosition().getY()) == 1) {
                        visible3 = true;
                    }
                }

            }
        }
        return canUse;
    }
    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(!effectName.equals("Shockwave"))
            actionInterface.playerDamage(victim, damagePower);
        if(!effectName.equals("ZX-22"))
            actionInterface.playerMark(victim, markPower);
        if (effectName.equals("Machine Gun")) {
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        }else if (effectName.equals("ZX-22") || effectName.equals("Shockwave")){
            if(visible1) {
                actionInterface.playerMark(victim, markPower);
                actionInterface.playerDamage(victim, damagePower);
                visible1 = false;
            }if(visible2) {
                actionInterface.playerMark(secondVictim, markPower);
                actionInterface.playerDamage(secondVictim, damagePower);
                visible2 = false;
            }if(visible3) {
                actionInterface.playerMark(thirdVictim, markPower);
                actionInterface.playerDamage(thirdVictim, damagePower);
                visible3 = false;
            }
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
