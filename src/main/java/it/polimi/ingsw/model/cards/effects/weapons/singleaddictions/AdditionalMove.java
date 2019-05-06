package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;

public class AdditionalMove extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    private Player player;

    private boolean basicFirst;

    private Direction firstMove, secondMove;



    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        player.setPosition(actionInterface.getCurrentPlayer().getPosition());

            if(basicFirst) {
                canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
                if (canUse) {
                    if(effectName.equals("Plasma Gun") || effectName.equals("Rocket Launcher"))
                        movementControl(actionInterface);
                    else
                        oneMovementControl(actionInterface);
                }
            }else{
                if(effectName.equals("Plasma Gun") || effectName.equals("Rocket Launcher"))
                    movementControl(actionInterface);
                else
                    oneMovementControl(actionInterface);
                if(canUse){
                    //actionInterface.updateFakePlayerPosition(player);
                    canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && super.effect.canUseEffect(actionInterface);
                }
            }

        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(effectName.equals("Plasma Gun")) {
            super.effect.useEffect(actionInterface);
            actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), actionInterface.getCurrentPlayer());
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    private void movementControl(ActionInterface actionInterface) {
        if (firstMove != null && secondMove != null){
            canUse = actionInterface.canMove(player.getColor(), firstMove, secondMove);
            player.updatePosition(firstMove, secondMove);
        } else if (firstMove != null && secondMove == null) {
            canUse = actionInterface.canMove(player.getColor(), firstMove);
            player.updatePosition(firstMove);
        }

    }

    private void oneMovementControl(ActionInterface actionInterface){

        if(firstMove != null && secondMove == null)
            canUse = actionInterface.canMove(player.getColor(),firstMove);
        else if (firstMove!= null && secondMove!= null){
            canUse = false;
        }
    }
}
