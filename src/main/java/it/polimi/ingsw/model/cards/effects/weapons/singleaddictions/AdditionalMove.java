package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;
import static it.polimi.ingsw.model.cards.StringCards.*;


/**
 * Class representing the effects which adds movements to the basic effect of the card.
 */
public class AdditionalMove extends SingleAddictionEffect {

    /**
     * Name of the effect.
     */
    private String effectName;

    /**
     * Cost of the effect.
     */
    private int redAmmos, blueAmmos, yellowAmmos;

    /**
     * Indicates if the player can use the effect.
     */
    private boolean canUse;

    /**
     * Fake player used to simulate the moves.
     */
    private Player player;

    /**
     * Current player.
     */
    private Player currentPlayer;

    /**
     * Indicates if the player wants to move before the basic effect.
     */
    private boolean basicFirst;

    /**
     * Directions of the moves.
     */
    private Direction firstMove, secondMove;


    /**
     * Class constructor.
     * @param effectName name of the effect.
     * @param redAmmos red ammos required to apply the effect.
     * @param blueAmmos blue ammos required to apply the effect.
     * @param yellowAmmos yellow ammos required to apply the effect.
     * @param effect basic effect that is completed by the additional effect.
     */
    public AdditionalMove(String effectName, int redAmmos, int blueAmmos, int yellowAmmos, Effect effect){
        this.effectName = effectName;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        this.canUse = true;
        super.effect = effect;
    }

    /**
     * Controls that the player can add the move to the basic effect without errors.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        setData(actionInterface);
        actionInterface.generatePlayer(currentPlayer, player);
        if(basicFirst) {
            canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
            if (canUse){
                if(effectName.equals(PLASMAGUNEFFECT) || effectName.equals(ROCKETLAUNCHEREFFECT))
                    movementControl(actionInterface);
                else
                    oneMovementControl(actionInterface);
            }
        }else{
            if(effectName.equals(PLASMAGUNEFFECT) || effectName.equals(ROCKETLAUNCHEREFFECT))
                movementControl(actionInterface);
            else
                oneMovementControl(actionInterface);
            if(canUse){
                canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && super.effect.canUseEffect(actionInterface);
            }
        }
        actionInterface.removePlayer(player);
        return canUse;
    }

    /**
     * Applies the additional effect and the basic effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public void useEffect(ActionInterface actionInterface) {
        super.effect.useEffect(actionInterface);
        actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), currentPlayer);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    /**
     * Sets the data getting them from the ClientData class.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void setData(ActionInterface actionInterface){
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        basicFirst = actionInterface.basicFirst();
        player = actionInterface.getFakePlayer();
        if(!basicFirst)
            actionInterface.getClientData().setAmmos();
        if(!effectName.equals(ROCKETLAUNCHEREFFECT)){
            firstMove = actionInterface.getFirstMove();
            secondMove = actionInterface.getSecondMove();
        }else{
            firstMove = actionInterface.getThirdMove();
            secondMove = actionInterface.getFourthMove();
        }
    }

    /**
     * Controls if the player can perform the two chosen movements without errors.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void movementControl(ActionInterface actionInterface) {
        if (firstMove != null) {
            canUse = actionInterface.canMove(player, firstMove);
            if(canUse)
                actionInterface.move(firstMove, player);
            if (secondMove != null) {
                canUse = actionInterface.canMove(player, secondMove);
                if(canUse)
                    actionInterface.move(secondMove, player);
            }
        }
    }

    /**
     * Controls if the player can perform the chosen movement without errors.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void oneMovementControl(ActionInterface actionInterface){

        if(firstMove != null && secondMove == null) {
            canUse = actionInterface.canMove(player, firstMove);
            actionInterface.move(firstMove, player);
        }
        else if (firstMove!= null){
            canUse = false;
        }
    }
}
