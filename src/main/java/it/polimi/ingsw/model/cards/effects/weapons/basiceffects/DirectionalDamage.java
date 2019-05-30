package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;

public class DirectionalDamage extends BasicEffect {

    private String effectName;

    private Player currentPlayer, victim, secondVictim, player;

    private Direction direction;

    private Position firstSquare;

    private int squares;

    private int damagePower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    public DirectionalDamage(String effectName, int damagePower, int redAmmos, int blueAmmos, int yellowAmmos){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        canUse = true;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        setData(actionInterface);
        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);
        if(effectName.equals("Sledgehammer"))
            actionInterface.generatePlayer(victim, player);
        else
            actionInterface.generatePlayer(currentPlayer, player);
        if(canUse && effectName.equals("Sledgehammer"))
            canUse = actionInterface.sameSquare(currentPlayer,victim);
        if(canUse) {
            firstMoveControl(actionInterface);
            if(canUse) {
                actionInterface.move(direction, player);
                victimControl(actionInterface);
                if (canUse && !effectName.equals("Railgun1")) {
                    secondMoveControl(actionInterface);
                    if (!effectName.equals("Flamethrower2") && !effectName.equals("Sledgehammer")&& secondVictim != null)
                        canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), secondVictim);
                    else
                        squares = 2;
                }
            }
        }
        actionInterface.removePlayer(player);
        return canUse;
    }
    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(!effectName.equals("Flamethrower2"))
            actionInterface.playerDamage(victim.getColor(), damagePower);
        if(secondVictim != null && !effectName.equals("Flamethrower2"))
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        if(effectName.equals("Power Glove2"))
            actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), currentPlayer);
        if(effectName.equals("Sledgehammer"))
            actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), victim);
        if(effectName.equals("Flamethrower2")){
            actionInterface.squareDamage(firstSquare.getX(), firstSquare.getY(), damagePower, 0);
            if (squares == 2)
                actionInterface.squareDamage(player.getPosition().getX(), player.getPosition().getY(), 1, 0);
        }
        actionInterface.updateAmmoBox(redAmmos,blueAmmos,yellowAmmos);
    }

    private void setData(ActionInterface actionInterface){
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        actionInterface.getClientData().setAmmos();
        player = actionInterface.getFakePlayer();
        victim = actionInterface.getVictim();
        secondVictim = actionInterface.getSecondVictim();
        direction = actionInterface.getFirstMove();
    }

    private void victimControl(ActionInterface actionInterface){

        firstSquare = new Position(player.getPosition().getX(), player.getPosition().getY());
        if ((!effectName.equals("Flamethrower2")) && !effectName.equals("Sledgehammer"))
            canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), victim);
    }

    private void firstMoveControl(ActionInterface actionInterface){

        if(!effectName.equals("Railgun1") && !effectName.equals("Railgun2"))
            canUse = actionInterface.canMove(player, direction);
        else {
            canUse = actionInterface.noOutOfBounds(player, direction);
        }
    }

    private void secondMoveControl(ActionInterface actionInterface){

        if((!effectName.equals("Railgun1")) && !effectName.equals("Railgun2")){
            if(actionInterface.canMove(player, direction))
                actionInterface.move(direction, player);
        }else if(effectName.equals("Railgun2") && actionInterface.noOutOfBounds(player, direction)){
            actionInterface.move(direction, player);
        }
    }
}
