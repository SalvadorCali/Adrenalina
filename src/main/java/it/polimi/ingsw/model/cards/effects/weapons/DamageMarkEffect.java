package it.polimi.ingsw.model.cards.effects.weapons;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.TokenColor;

public class DamageMarkEffect extends BasicEffect {

    private int damagePower;

    private int markPower;

    private boolean sameSquare;

    private int redAmmos, blueAmmos, yellowAmmos;

    public DamageMarkEffect(int damagePower, int markPower, boolean sameSquare, int redAmmos, int blueAmmos, int yellowAmmos ){

        this.damagePower = damagePower;
        this.markPower = markPower;
        this.sameSquare = sameSquare;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        //victim = actionInterface.getVictim();

        if(sameSquare){
            return (ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && actionInterface.sameSquare(TokenColor.BLUE)); // Spada Fotonica || Martello Ionico

        }else {
            if (((damagePower == 2) && (markPower == 1)) || ((damagePower == 1) && (markPower == 2)))
                return (super.ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && actionInterface.isVisible(TokenColor.BLUE)); // Distruttore || Torpedine || Fucile al plasma// ZX2
            if ((damagePower == 3) && (markPower == 0) && (redAmmos == 1) && (yellowAmmos == 1)) //Razzo Termico
                return (super.ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && !actionInterface.isVisible(TokenColor.BLUE));
        }
        return false;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(damagePower > 0)
            actionInterface.playerDamage(TokenColor.BLUE, damagePower);
        if(markPower > 0)
            actionInterface.playerMark(TokenColor.BLUE, markPower);

    }

}
