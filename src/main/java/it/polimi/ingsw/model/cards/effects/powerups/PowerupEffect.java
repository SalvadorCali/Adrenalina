package it.polimi.ingsw.model.cards.effects.powerups;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;

import static it.polimi.ingsw.model.cards.StringCards.*;

/**
 * Class representing the effects of the powerup cards.
 */
public class PowerupEffect extends Effect {

    /**
     * Name of the effect.
     */
    private String powerupName;

    /**
     * Boolean which indicates if the effect can be used.
     */
    private boolean canUse;

    /**
     * Player representing the victim of the powerup.
     */
    private Player victim = new Player(TokenColor.NONE);

    /**
     * Position of the square in which the player wants to move.
     */
    private Position square;

    /**
     * Class constructor.
     * @param powerupName name of the powerup.
     */
    public PowerupEffect(String powerupName){
        this.powerupName = powerupName;
        this.canUse = true;
    }

    /**
     * Controls if the effect of the powerup can be applied.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the control.
     */
    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        switch (powerupName) {
            case (TARGETINGSCOPEEFFECT):
                targetingScope(actionInterface);
                break;
            case (NEWTONEFFECT):
                newton(actionInterface);
                break;
            case (TAGBACKGRENADEEFFECT):
                tagbackGrenade(actionInterface);
                break;
            case (TELEPORTEREFFECT):
                teleporter(actionInterface);
                break;
            default:
                break;
        }
        return  canUse;
    }

    /**
     * Applies the effect of the powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public void useEffect(ActionInterface actionInterface) {

        switch (powerupName){
            case (TARGETINGSCOPEEFFECT):
                targetingScopeUse(actionInterface);
                break;
            case(NEWTONEFFECT):
                newtonUse(actionInterface);
                break;
            case(TAGBACKGRENADEEFFECT):
                tagbackGrenadeUse(actionInterface);
                break;
            case(TELEPORTEREFFECT):
                teleporterUse(actionInterface);
                break;
            default:
                break;
        }
    }

    /**
     * Controls to apply the targeting scope powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void targetingScope(ActionInterface actionInterface){
        actionInterface.getClientData().setAmmos();
        canUse = !actionInterface.getClientData().getAmmoColor().equals(Color.NONE) && actionInterface.isDamaged();
        if(canUse) {
            if (actionInterface.getClientData().getAmmoColor().equals(Color.RED))
                canUse = actionInterface.ammoControl(1, 0, 0);
            else if (actionInterface.getClientData().getAmmoColor().equals(Color.BLUE))
                canUse = actionInterface.ammoControl(0, 1, 0);
            else
                canUse = actionInterface.ammoControl(0, 0, 1);
        }
    }

    /**
     * Applies the targeting scope powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void targetingScopeUse(ActionInterface actionInterface){
        actionInterface.playerDamage(actionInterface.getClientData().getPowerupVictim(), 1);
        if (actionInterface.getClientData().getAmmoColor().equals(Color.RED))
            actionInterface.updateAmmoBox(1, 0, 0);
        else if (actionInterface.getClientData().getAmmoColor().equals(Color.BLUE))
            actionInterface.updateAmmoBox(0, 1, 0);
        else
            actionInterface.updateAmmoBox(0, 0, 1);
    }

    /**
     * Controls to apply the newton powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void newton(ActionInterface actionInterface){
        Direction direction = actionInterface.getClientData().getFirstMove();
        Direction secondDirection = actionInterface.getClientData().getSecondMove();
        if(actionInterface.getClientData().getPowerupVictim() == null) {
            canUse = false;
            return;
        }
        actionInterface.generatePlayer(actionInterface.getClientData().getPowerupVictim(),victim);
        canUse = ((direction.equals(secondDirection)||secondDirection == null) && actionInterface.canMove(victim, direction));
        if(canUse){
            actionInterface.move(direction, victim);
            if(direction.equals(actionInterface.getSecondMove()) && actionInterface.canMove(victim, direction))
                actionInterface.move(direction,victim);
            else if(actionInterface.getSecondMove() != null)
                canUse = false;
        }
        actionInterface.removePlayer(victim);
    }

    /**
     * Applies the newton powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void newtonUse(ActionInterface actionInterface){
        actionInterface.move(victim.getPosition().getX(),victim.getPosition().getY(), actionInterface.getClientData().getPowerupVictim());
    }

    /**
     * Controls to apply the tagback grenade powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void tagbackGrenade(ActionInterface actionInterface){
        victim = actionInterface.getClientData().getPowerupVictim();
        int count = -1;
        for(int i = 0; i < actionInterface.getClientData().getCurrentPlayer().getPlayerBoard().getDamageBoard().length; i++){
            if(actionInterface.getClientData().getCurrentPlayer().getPlayerBoard().getDamageBoard()[i].getFirstColor().equals(TokenColor.NONE)){
                count = i - 1;
                break;
            }
        }
        canUse = count!= -1 && actionInterface.getClientData().getCurrentPlayer().isDamaged() && actionInterface.getClientData().getCurrentPlayer().getPlayerBoard().getDamageBoard()[count].getFirstColor().equals(victim.getColor());
    }

    /**
     * Applies the tagback grenade powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void tagbackGrenadeUse(ActionInterface actionInterface){
        actionInterface.playerMark(actionInterface.getClientData().getCurrentPlayer(),victim);
    }

    /**
     * Controls to apply the teleporter powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void teleporter(ActionInterface actionInterface){
        square = actionInterface.getClientData().getSquare();
        canUse = (square.getX() >= 0 && square.getX() < 3) && (square.getY() >= 0 && square.getY() < 4) && actionInterface.isActive(square);
    }

    /**
     * Applies the teleporter powerup.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void teleporterUse(ActionInterface actionInterface){
        actionInterface.move(square.getX(), square.getY(), actionInterface.getCurrentPlayer());
    }

}
