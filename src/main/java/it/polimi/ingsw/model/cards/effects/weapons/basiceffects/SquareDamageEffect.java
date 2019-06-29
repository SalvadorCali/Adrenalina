package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;
import static it.polimi.ingsw.model.cards.StringCards.*;

/**
 * Class representing the basic effects which damage all the players in a square/room.
 */
public class SquareDamageEffect extends BasicEffect {

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
     * Cost of the effect.
     */
    private int redAmmos, blueAmmos, yellowAmmos;

    /**
     * Square that the player wants to damage.
     */
    private Position square;

    /**
     * Players involved in the effect.
     */
    private Player currentPlayer, victim;

    /**
     * Booleans to control which squares to damage.
     */
    private boolean up, down, right, left;

    /**
     * Indicates if the player can use the effect.
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
    public SquareDamageEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos){
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
        canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && noAutoShoot(actionInterface);// Electroscythe
        if(canUse) {
            if (effectName.equals(HELLIONEFFECT))
                canUse = actionInterface.isVisible(currentPlayer,victim) && (actionInterface.distanceControl(victim.getPosition().getX(),victim.getPosition().getY())>= 1);
            else if(effectName.equals(FURNACEMOD1EFFECT))
                canUse = actionInterface.isVisibleDifferentSquare(square.getX(),square.getY());
            else if (effectName.equals(FURNACEMOD2EFFECT) && (actionInterface.distanceControl(square.getX(), square.getY()) != 1))
                    canUse = false;
            else if(effectName.equals(SHOCKWAVEEFFECT)){
                if(actionInterface.canMove(currentPlayer, Direction.UP))
                    up = true;
                if(actionInterface.canMove(currentPlayer,Direction.DOWN))
                    down = true;
                if(actionInterface.canMove(currentPlayer,Direction.RIGHT))
                    right = true;
                if(actionInterface.canMove(currentPlayer, Direction.LEFT))
                    left = true;
            }else if(effectName.equals(VORTEXCANNONEFFECT)){
                if(actionInterface.distanceControl(currentPlayer, square.getX(),square.getY()) == 0 || actionInterface.distanceControl(victim, square.getX(), square.getY()) > 1)
                    canUse = false;
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
        if (effectName.equals(FURNACEMOD1EFFECT)) {
            actionInterface.roomDamage(square.getX(), square.getY(), damagePower, markPower);
        }else if(effectName.equals(SHOCKWAVEEFFECT)){
            if(up)
                actionInterface.squareDamage(currentPlayer.getPosition().getX() - 1, currentPlayer.getPosition().getY(), damagePower, markPower);
            if(down)
                actionInterface.squareDamage(currentPlayer.getPosition().getX() + 1, currentPlayer.getPosition().getY(), damagePower, markPower);
            if(right)
                actionInterface.squareDamage(currentPlayer.getPosition().getX(), currentPlayer.getPosition().getY() + 1, damagePower, markPower);
            if(left)
                actionInterface.squareDamage(currentPlayer.getPosition().getX(), currentPlayer.getPosition().getY() - 1, damagePower, markPower);
            up = false;
            down = false;
            right = false;
            left = false;
        }else if(effectName.equals(VORTEXCANNONEFFECT)) {
            actionInterface.playerDamage(victim, damagePower);
            actionInterface.move(square.getX(),square.getY(),victim);
        }else {
            if (effectName.equals(HELLIONEFFECT)) {
                actionInterface.playerDamage(victim, damagePower);
                damagePower--;
            }
            actionInterface.squareDamage(square.getX(), square.getY(), damagePower, markPower);
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    /**
     * Sets the data getting them from the ClientData class.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void setData(ActionInterface actionInterface){
        actionInterface.getClientData().setAmmos();
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getVictim();
        square = actionInterface.getSquare();
        if(effectName.equals(HELLIONEFFECT))
            actionInterface.getClientData().setSquare(victim.getPosition().getX(), victim.getPosition().getY());
        if(effectName.equals(ELECTROSCYTHEEFFECT) || effectName.equals(SHOCKWAVEEFFECT))
            actionInterface.getClientData().setSquare(currentPlayer.getPosition().getX(), currentPlayer.getPosition().getY());
    }
}
