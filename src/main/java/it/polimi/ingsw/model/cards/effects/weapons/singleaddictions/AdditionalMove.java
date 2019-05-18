package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;

public class AdditionalMove extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    private Player player = new Player(TokenColor.NONE);

    private Player currentPlayer;

    private boolean basicFirst;

    private Direction firstMove, secondMove;



    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        actionInterface.generatePlayer(player, currentPlayer);

            if(basicFirst) {
                canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
                if (canUse) {
                    if(effectName.equals("Plasma Gun") || effectName.equals("Rocket Launcher"))
                        movementControl(actionInterface);
                    else
                        oneMovementControl(actionInterface);
                }
                actionInterface.removePlayer(player);
            }else{
                if(effectName.equals("Plasma Gun") || effectName.equals("Rocket Launcher"))
                    movementControl(actionInterface);
                else
                    oneMovementControl(actionInterface);
                if(canUse){
                    actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), currentPlayer);
                    canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && super.effect.canUseEffect(actionInterface);
                }
            }

        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        super.effect.useEffect(actionInterface);
        actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), actionInterface.getCurrentPlayer());
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    private void movementControl(ActionInterface actionInterface) {
        if (firstMove != null) {
            canUse = actionInterface.canMove(player, firstMove);
            actionInterface.move(firstMove, player);
            if (secondMove != null) {
                canUse = actionInterface.canMove(player, secondMove);
                actionInterface.move(secondMove, player);
            }
        }
    }

    private void oneMovementControl(ActionInterface actionInterface){

        if(firstMove != null && secondMove == null)
            canUse = actionInterface.canMove(player,firstMove);
        else if (firstMove!= null && secondMove!= null){
            canUse = false;
        }
    }
}
