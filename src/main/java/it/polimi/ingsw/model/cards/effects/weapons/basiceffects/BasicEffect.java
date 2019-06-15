package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.gamecomponents.Player;

public abstract class BasicEffect extends Effect {

    public abstract boolean canUseEffect(ActionInterface actionInterface);

    public abstract void useEffect(ActionInterface actionInterface);

    protected boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos, ActionInterface actionInterface){
        return (actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos));
    }

    protected boolean noAutoShoot(ActionInterface actionInterface){
        Player currentPlayer, victim, secondVictim, thirdVictim;
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getClientData().getVictim();
        secondVictim = actionInterface.getSecondVictim();
        thirdVictim = actionInterface.getThirdVictim();
        if(victim == null)
            return true;
        if(thirdVictim == null && secondVictim == null)
            return !currentPlayer.equals(victim);
        if(thirdVictim == null)
            return !currentPlayer.equals(victim) && !currentPlayer.equals(secondVictim) && !secondVictim.equals(victim);
        else
            return  !currentPlayer.equals(victim) && !currentPlayer.equals(secondVictim) && !currentPlayer.equals(thirdVictim) && !secondVictim.equals(victim) && !secondVictim.equals(thirdVictim) && !thirdVictim.equals(victim);
    }

}
