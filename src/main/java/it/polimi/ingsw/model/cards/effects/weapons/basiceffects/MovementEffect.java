package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;

public class MovementEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    private Direction firstMove, secondMove;

    private Player currentPlayer, victim;

    private Player player;


    public MovementEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos ){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        this.canUse = true;

    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        setData(actionInterface);

        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);
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
    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(!effectName.equals("Power Glove1"))
            actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), victim);
        else
            actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), actionInterface.getCurrentPlayer());
        actionInterface.playerDamage(victim.getColor(), damagePower);
    }

    private void oneMovementControl(ActionInterface actionInterface, Player player){

        if(firstMove != null && secondMove == null)
            canUse = actionInterface.canMove(player,firstMove);
        else if (firstMove!= null){
            canUse = false;
        }
    }

    private void setData(ActionInterface actionInterface){

        victim = actionInterface.getVictim();
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        player = actionInterface.getFakePlayer();
        firstMove = actionInterface.getFirstMove();
        secondMove = actionInterface.getSecondMove();
    }

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

    private void launchers (ActionInterface actionInterface) {

        canUse = actionInterface.isVisible(currentPlayer, player);
        if (canUse) {
            if (effectName.equals("Rocket Launcher"))
                canUse = !actionInterface.sameSquare(currentPlayer ,player);
            if(canUse)
                oneMovementControl(actionInterface, player);
        }
    }

    private void shotgun1 (ActionInterface actionInterface){

        canUse = actionInterface.sameSquare(currentPlayer, player);
        if(canUse)
            oneMovementControl(actionInterface, player);
    }
}
