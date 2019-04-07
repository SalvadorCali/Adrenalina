package it.polimi.ingsw.model.cards.effects.weapons;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;

public class DamageMarkEffect extends BasicEffect {

    private int damagePower;

    private int markPower;

    private boolean sameSquare;

    private int ammoNumber;

    private Color ammoColor;

    public DamageMarkEffect(int damagePower, int markPower, boolean sameSquare, int ammoNumber, Color ammoColor){

        this.damagePower = damagePower;
        this.markPower = markPower;
        this.sameSquare = sameSquare;
        this.ammoNumber = ammoNumber;
        this.ammoColor = ammoColor;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        if(sameSquare){
            return (super.ammoControl(ammoNumber, ammoColor, actionInterface) && actionInterface.sameSquare(TokenColor.BLUE)); // Spada Fotonica || Martello Ionico

        }else {
            if (((damagePower == 2) && (markPower == 1)) || ((damagePower == 1) && (markPower == 2))) //Distruttore || Torpedine || Fucile al plasma// ZX2
                return (super.ammoControl(ammoNumber, ammoColor, actionInterface) && actionInterface.isVisible(TokenColor.BLUE));
            if ((damagePower == 3) && (markPower == 0) && (ammoNumber == 2)) //Razzo Termico
                return (super.ammoControl(ammoNumber, ammoColor, actionInterface) && !actionInterface.isVisible(TokenColor.BLUE));
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
