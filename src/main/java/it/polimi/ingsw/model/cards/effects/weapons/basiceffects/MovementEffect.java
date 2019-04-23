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

    private boolean canUse;

    private Direction firstMove, secondMove;

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

        if(canUse) {

            player.setPosition(victim.getPosition());

            if (effectName.equals("Tractor Beam1")  || effectName.equals("Tractor Beam2")) {
                if (firstMove != null && secondMove != null) {
                    canUse = actionInterface.canMove(victim.getColor(), firstMove, secondMove);
                    player.updatePosition(firstMove, secondMove);
                } else if (firstMove != null && secondMove == null) {
                    canUse = actionInterface.canMove(victim.getColor(), firstMove);
                    player.updatePosition(firstMove);
                }
                if(canUse) {
                    if (effectName.equals("Tractor Beam1"))
                        canUse = actionInterface.isVisible(player.getColor());
                    else
                        canUse = actionInterface.sameSquare(player.getColor());
                }
            }

            if (effectName.equals("Grenade Launcher") || effectName.equals("Rocket Launcher")) {
                canUse = actionInterface.isVisible(player.getColor());
                if (canUse) {
                    if (effectName.equals("Rocket Launcher"))
                        canUse = actionInterface.sameSquare(player.getColor());
                    if(canUse)
                        oneMovementControl(actionInterface, player);
                }
            }

            if (effectName.equals("Shotgun1")) {
                canUse = actionInterface.sameSquare(victim.getColor());
                if(canUse)
                    oneMovementControl(actionInterface, player);
                }

            if (effectName.equals("Shotgun2")) {
                canUse = actionInterface.distanceControl(player.getPosition().getX(), player.getPosition().getY()) == 1;
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

        if(firstMove != null && secondMove == null)
            canUse = actionInterface.canMove(player.getColor(),firstMove);
        else if (firstMove!= null && secondMove!= null){
            canUse = false;
        }
    }
}
