package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;

/**
 * Class referring to the basic effects which involve shooter/victim movement.
 */
public class MovementEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    private Direction firstMove, secondMove;

    private Player currentPlayer, victim;

    private Player player;


    /**
     * Class constructor.
     * @param effectName name of the card.
     * @param damagePower number of damages to add to the victims.
     * @param markPower number of marks to add to the victims.
     * @param redAmmos red ammos cost to use the effect.
     * @param blueAmmos blue ammos cost to use the effect.
     * @param yellowAmmos yellow ammos cost to use the effect.
     */
    public MovementEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos ){
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
        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface) && noAutoShoot(actionInterface) && victim!=null;
        if(canUse) {
            actionInterface.generatePlayer(victim, player);
            if(effectName.equals("Tractor Beam1")  || effectName.equals("Tractor Beam2")) {
                tractorBeam(actionInterface);
            }
            if(effectName.equals("Power Glove1") && actionInterface.distanceControl(player.getPosition().getX(), player.getPosition().getY()) != 1)
                canUse = false;
            if(effectName.equals("Grenade Launcher") || effectName.equals("Rocket Launcher")) {
                launchers(actionInterface);
            }
            if(effectName.equals("Shotgun1"))
                shotgun1(actionInterface);
            if(effectName.equals("Shotgun2")) {
                canUse = actionInterface.distanceControl(player.getPosition().getX(), player.getPosition().getY()) == 1;
            }
        }
        actionInterface.removePlayer(player);
        return canUse;
    }

    /**
     * Apply the effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(!effectName.equals("Power Glove1"))
            actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), victim);
        else
            actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), currentPlayer);
        actionInterface.playerDamage(victim.getColor(), damagePower);
        actionInterface.playerMark(victim, markPower);
    }

    /**
     * Controls if the player can do the first move.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     * @param player player who executes the move.
     */
    private void oneMovementControl(ActionInterface actionInterface, Player player){

        if(firstMove != null && secondMove == null) {
            canUse = actionInterface.canMove(player, firstMove);
            if(canUse)
                actionInterface.move(firstMove, player);
        }else if (firstMove!= null){
            canUse = false;
        }
    }

    /**
     * Sets the data getting them from the ClientData class.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void setData(ActionInterface actionInterface){

        victim = actionInterface.getVictim();
        if(actionInterface.basicFirst()){
            currentPlayer = actionInterface.getClientData().getCurrentPlayer();
            actionInterface.getClientData().setAmmos();
        }
        else
            currentPlayer = actionInterface.getClientData().getFakePlayer();

        player = actionInterface.getClientData().getFakeVictim();
        firstMove = actionInterface.getFirstMove();
        secondMove = actionInterface.getSecondMove();
    }

    /**
     * Controls that have to be done to use the tractor beam basic effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void tractorBeam(ActionInterface actionInterface){
        if (firstMove != null && secondMove != null) {
            canUse = actionInterface.canMove(player, firstMove, secondMove);
            actionInterface.move(firstMove, player);
            actionInterface.move(secondMove, player);
        }else if (firstMove != null) {
            canUse = actionInterface.canMove(player, firstMove);
            actionInterface.move(firstMove, player);
        }
        if(canUse) {
            if (effectName.equals("Tractor Beam1"))
                canUse = actionInterface.isVisible(currentPlayer, player);
            else
                canUse = actionInterface.sameSquare(currentPlayer, player);
        }
    }

    /**
     * Controls that have to be done to use the Grenade/Rocket Launcher basic effects.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void launchers (ActionInterface actionInterface) {

        canUse = actionInterface.isVisible(currentPlayer, player);
        if (canUse) {
            if (effectName.equals("Rocket Launcher"))
                canUse = !actionInterface.sameSquare(currentPlayer ,player);
            if(canUse)
                oneMovementControl(actionInterface, player);
        }
    }

    /**
     * Controls that have to be done to use the shotgun first mod basic effect.
     * @param actionInterface give access to some restricted methods of the game/clientData to the card controls.
     */
    private void shotgun1 (ActionInterface actionInterface){

        canUse = actionInterface.sameSquare(currentPlayer, player);
        if(canUse)
            oneMovementControl(actionInterface, player);
    }
}
