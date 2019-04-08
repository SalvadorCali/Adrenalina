package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

public class MovementEffect extends BasicEffect {

    private int damagePower;

    private int redAmmos, blueAmmos, yellowAmmos;

    Direction firstMove, secondMove;

    private TokenColor victim;

    boolean canUse = true;

    //victim = actionInterface.getVictim();
    //firstMove = actionInterface.getMove(1);
    //secondMove = actionInterface.getMove(2);


    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        if(redAmmos + blueAmmos + yellowAmmos == 0)
            canUse = actionInterface.canMove(victim, firstMove, secondMove)&& ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface); //isVisible(newPosition); RaggioTraente(Mod1) || Lanciagranate
        if(redAmmos == 0 && blueAmmos == 0 && yellowAmmos == 1)
            return ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && actionInterface.sameSquare(victim) && actionInterface.canMove(victim, firstMove, secondMove); //Fucile a Pompa
        if(redAmmos == 1 && blueAmmos == 0 && yellowAmmos == 0)
            return ammoControl(redAmmos,blueAmmos,yellowAmmos,actionInterface)&& !actionInterface.sameSquare(victim) && actionInterface.canMove(victim, firstMove, secondMove); //Lanciarazzi

        return false;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

    }
}
