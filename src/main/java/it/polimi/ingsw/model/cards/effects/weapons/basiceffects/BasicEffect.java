package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.gamecomponents.Player;

/**
 * Abstract class representing the effects of every weapon.
 */
public abstract class BasicEffect extends Effect {

    /**
     * Controls if the player can use the effect without errors.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the evaluation.
     */
    public abstract boolean canUseEffect(ActionInterface actionInterface);

    /**
     * Apply the effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    public abstract void useEffect(ActionInterface actionInterface);

    /**
     * Controls that the player has enough ammos to play the effect.
     * @param redAmmos red ammos required.
     * @param blueAmmos blue ammos required.
     * @param yellowAmmos yellow ammos required.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    protected boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos, ActionInterface actionInterface){
        return (actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos));
    }

    /**
     * Controls that the victims are different between each other, and that the player isn't in the victims.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    boolean noAutoShoot(ActionInterface actionInterface){
        Player currentPlayer, victim, secondVictim, thirdVictim;
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getClientData().getVictim();
        secondVictim = actionInterface.getSecondVictim();
        thirdVictim = actionInterface.getThirdVictim();
        if(victim == null)
            return true;
        else if(victim!= null && thirdVictim == null && secondVictim == null)
            return !currentPlayer.equals(victim);
        else if(victim!=null && secondVictim != null && thirdVictim == null)
            return !currentPlayer.equals(victim) && !currentPlayer.equals(secondVictim) && !secondVictim.equals(victim);
        else if(victim!=null && secondVictim!= null && thirdVictim!=null)
            return  !currentPlayer.equals(victim) && !currentPlayer.equals(secondVictim) && !currentPlayer.equals(thirdVictim) && !secondVictim.equals(victim) && !secondVictim.equals(thirdVictim) && !thirdVictim.equals(victim);
        else
            return false;
    }
}
