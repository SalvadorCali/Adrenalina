package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.gamecomponents.Player;
import static it.polimi.ingsw.model.cards.StringCards.*;

/**
 * Basic effects of the cards that are giving damages and marks to specific victims.
 */
public class DamageMarkEffect extends BasicEffect {

    /**
     * Name of the effect.
     */
    private String effectName;

    /**
     * Number of damages given by the effect.
     */
    private int damagePower;

    /**
     * Number of marks given by the effect.
     */
    private int markPower;

    /**
     * Booleans which indicate if the victims are visible.
     */
    private boolean visible1, visible2, visible3;

    /**
     * Cost of the effect.
     */
    private int redAmmos, blueAmmos, yellowAmmos;

    /**
     * Players involved in the effect.
     */
    private Player currentPlayer, victim, secondVictim, thirdVictim;

    /**
     * Boolean which indicates if the player can use the effect.
     */
    private boolean canUse;

    /**
     * Class constructor.
     * @param effectName name of the card.
     * @param damagePower number of damages to add to the victims.
     * @param markPower number of marks to add to the victims.
     * @param redAmmos red ammos cost to use the effect.
     * @param blueAmmos blue ammos cost to use the effect.
     * @param yellowAmmos yellow ammos cost to use the effect.
     */
    public DamageMarkEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos) {
        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        this.canUse = true;
    }

    /**
     * Controls if the player can use the effect without errors.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the evaluation.
     */
    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        setData(actionInterface);
        canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && noAutoShoot(actionInterface);
        if (canUse) {
            if (effectName.equals(CYBERBLADEEFFECT) || effectName.equals(SLEDGEHAMMEREFFECT))
                canUse = actionInterface.sameSquare(currentPlayer, victim);
            else if (effectName.equals(LOCKRIFLEEFFECT) || effectName.equals(THOREFFECT) || effectName.equals(PLASMAGUNEFFECT) || effectName.equals(ZX2MOD1EFFECT) || effectName.equals(WHISPEREFFECT) || effectName.equals(MACHINEGUNEFFECT))
                visibilityCheck(actionInterface);
            else if (effectName.equals(HEATSEEKEREFFECT) && actionInterface.isVisible(currentPlayer, victim)) {
                canUse = false;
            }else if (effectName.equals(ZX2MOD2EFFECT)) {
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
            }else if(effectName.equals(SHOCKWAVEEFFECT)){
                canUse = actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) == 1 || (secondVictim!= null && actionInterface.distanceControl(secondVictim.getPosition().getX(), secondVictim.getPosition().getY()) == 1) || (thirdVictim!= null && actionInterface.distanceControl(thirdVictim.getPosition().getX(), thirdVictim.getPosition().getY()) == 1);
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

    /**
     * Applies the effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(!effectName.equals(SHOCKWAVEEFFECT))
            actionInterface.playerDamage(victim, damagePower);
        if(!effectName.equals(ZX2MOD2EFFECT))
            actionInterface.playerMark(victim, markPower);
        if (effectName.equals(MACHINEGUNEFFECT) && secondVictim!= null) {
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        }else if (effectName.equals(ZX2MOD2EFFECT) || effectName.equals(SHOCKWAVEEFFECT)){
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

    /**
     * Checks if the victims are/aren't visible from the shooter.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void visibilityCheck(ActionInterface actionInterface) {
        canUse = actionInterface.isVisible(currentPlayer, victim);
        if (canUse) {
            if (effectName.equals(WHISPEREFFECT)) {
                if (actionInterface.distanceControl(victim.getPosition().getX(), victim.getPosition().getY()) <= 1)
                    canUse = false;
            } else if (effectName.equals(MACHINEGUNEFFECT) && secondVictim != null) {
                canUse = (!secondVictim.getColor().equals(victim.getColor())) && actionInterface.isVisible(currentPlayer, secondVictim);
            }
        }
    }

    /**
     * Sets the data getting them from the ClientData class.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
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
