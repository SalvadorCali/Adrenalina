package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;

public class MovementEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    boolean canUse;

    Direction firstMove, secondMove;

    private Player victim;

    private Player player = new Player(TokenColor.NONE);

    public MovementEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos ){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        this.canUse = true;

    }


    //victim = actionInterface.getVictim();
    //firstMove = actionInterface.getMove(1);
    //secondMove = actionInterface.getMove(2);


    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);

        player.setPosition(victim.getPosition());

        if(effectName.equals("Tractor Beam1")) {
            if (!firstMove.equals(null) && !secondMove.equals(null)) {
                canUse = actionInterface.canMove(victim.getColor(), firstMove, secondMove);
                player.updatePosition(firstMove, secondMove);
            } else if ((!firstMove.equals(null)) && secondMove.equals(null)) {
                canUse = actionInterface.canMove(victim.getColor(), firstMove);
                player.updatePosition(firstMove);
            }
            canUse = actionInterface.isVisible(player.getColor());

        }

        if(effectName.equals("Grenade Launcher")|| effectName.equals("Rocket Launcher")) {
            canUse = actionInterface.isVisible(player.getColor());
            if (effectName.equals("Rocket Launcher"))
                actionInterface.sameSquare(player.getColor());
            oneMovementControl(actionInterface, player);
        }

        if(effectName.equals("Shotgun1")){
            canUse = actionInterface.sameSquare(victim.getColor());
            oneMovementControl(actionInterface, player);
            }

        if(effectName.equals("Shotgun2")){
            if(actionInterface.distanceControl(player.getPosition().getX(), player.getPosition().getY()) == 1){
                canUse = true;
            }else{
                canUse = false;
            }
        }

        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), victim);
        actionInterface.playerDamage(victim.getColor(), damagePower);

    }

    private void oneMovementControl(ActionInterface actionInterface, Player player){

        if((!firstMove.equals(null)) && secondMove.equals(null))
            canUse = actionInterface.canMove(player.getColor(),firstMove);
        else if (!firstMove.equals(null) && !secondMove.equals(null)){
            canUse = false;
        }
    }
}
