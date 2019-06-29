package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;
import it.polimi.ingsw.util.Printer;

import static it.polimi.ingsw.model.cards.StringCards.*;

/**
 * Basic effects of the cards that are giving damages to victims setted in a single direction.
 */
public class DirectionalDamage extends BasicEffect {

    /**
     * Name of the effect.
     */
    private String effectName;

    /**
     * Players involved in the effect.
     */
    private Player currentPlayer, victim, secondVictim, player;

    /**
     * Direction of the movement/directional shot.
     */
    private Direction direction;

    /**
     * Position after the first move in the direction.
     */
    private Position firstSquare;

    /**
     * Identifies how many square have to be damaged in some effects.
     */
    private int squares;

    /**
     * Number of damages to give to the victims.
     */
    private int damagePower;

    /**
     * Cost of the effect.
     */
    private int redAmmos, blueAmmos, yellowAmmos;

    /**
     * Indicates if the player can use the effect.
     */
    private boolean canUse;

    /**
     * Class constructor.
     * @param effectName name of the card.
     * @param damagePower number of damages to give to the victims.
     * @param redAmmos number or red ammos required to use the effect.
     * @param blueAmmos number of blue ammos required to use the effect.
     * @param yellowAmmos number of yellow ammos required to use the effect.
     */
    public DirectionalDamage(String effectName, int damagePower, int redAmmos, int blueAmmos, int yellowAmmos){
        this.effectName = effectName;
        this.damagePower = damagePower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        canUse = true;
    }

    /**
     * Controls if the player can use the effect without errors.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @return the result of the evaluation.
     */
    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {
        setData(actionInterface);
        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && noAutoShoot(actionInterface);
        if(effectName.equals(SLEDGEHAMMEREFFECT)){
            if(victim!=null)
                actionInterface.generatePlayer(victim, player);
            else
                canUse = false;
        } else
            actionInterface.generatePlayer(currentPlayer, player);
        if(canUse && effectName.equals(SLEDGEHAMMEREFFECT))
            canUse = actionInterface.sameSquare(currentPlayer,victim);
        if(canUse) {
            firstMoveControl(actionInterface);
            Printer.println(canUse);
            if(canUse) {
                actionInterface.move(direction, player);
                victimControl(actionInterface);
                Printer.println(canUse);
                if (canUse && !effectName.equals(RAILGUNMOD1EFFECT)) {
                    secondMoveControl(actionInterface);
                    if (!effectName.equals(FLAMETHROWERMOD2EFFECT) && !effectName.equals(SLEDGEHAMMEREFFECT)&& secondVictim != null)
                        canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), secondVictim);
                    else
                        squares = 2;
                }
            }
        }
        actionInterface.removePlayer(player);
        return canUse;
    }

    /**
     * Applies the effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public void useEffect(ActionInterface actionInterface) {
        if(!effectName.equals(FLAMETHROWERMOD2EFFECT) && victim!=null)
            actionInterface.playerDamage(victim.getColor(), damagePower);
        if(secondVictim != null && !effectName.equals(FLAMETHROWERMOD2EFFECT))
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        if(effectName.equals(POWERGLOVEMOD2EFFECT)) {
            if (actionInterface.getClientData().getSecondMove() == null)
                actionInterface.move(firstSquare.getX(), firstSquare.getY(), currentPlayer);
            else
                actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), currentPlayer);
        }
        if(effectName.equals(SLEDGEHAMMEREFFECT)) {
            if(actionInterface.getClientData().getSecondMove() == null)
                actionInterface.move(firstSquare.getX(), firstSquare.getY(), victim);
            else
                actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), victim);
        }
        if(effectName.equals(FLAMETHROWERMOD2EFFECT)){
            actionInterface.squareDamage(firstSquare.getX(), firstSquare.getY(), damagePower, 0);
            if (squares == 2)
                actionInterface.squareDamage(player.getPosition().getX(), player.getPosition().getY(), 1, 0);
        }
        actionInterface.updateAmmoBox(redAmmos,blueAmmos,yellowAmmos);
    }

    /**
     * Sets the data getting them from the ClientData class.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void setData(ActionInterface actionInterface){
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        actionInterface.getClientData().setAmmos();
        player = actionInterface.getFakePlayer();
        victim = actionInterface.getVictim();
        secondVictim = actionInterface.getSecondVictim();
        direction = actionInterface.getFirstMove();
    }

    /**
     * Controls that the victim are present in the selected squares.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void victimControl(ActionInterface actionInterface){
        firstSquare = new Position(player.getPosition().getX(), player.getPosition().getY());
        if ((!effectName.equals(FLAMETHROWERMOD2EFFECT)) && !effectName.equals(SLEDGEHAMMEREFFECT))
            canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), victim);
    }

    /**
     * Controls if the first move is a legal move.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void firstMoveControl(ActionInterface actionInterface){
        if(!effectName.equals(RAILGUNMOD1EFFECT) && !effectName.equals(RAILGUNMOD2EFFECT) && direction!=null)
            canUse = actionInterface.canMove(player, direction);
        else {
            canUse = actionInterface.noOutOfBounds(player, direction);
        }
    }

    /**
     * Controls if the second move in the same direction is a legal move.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void secondMoveControl(ActionInterface actionInterface){
        if((!effectName.equals(RAILGUNMOD1EFFECT)) && !effectName.equals(RAILGUNMOD2EFFECT)){
            if(actionInterface.canMove(player, direction))
                actionInterface.move(direction, player);
        }else if(effectName.equals(RAILGUNMOD2EFFECT) && actionInterface.noOutOfBounds(player, direction)){
            actionInterface.move(direction, player);
        }
    }
}
