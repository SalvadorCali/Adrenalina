package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;

public class DirectionalDamage extends BasicEffect {

    private String effectName;

    private Player victim, secondVictim, player;

    private Direction direction;

    private Position firstSquare;

    private int squares;

    private int damagePower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    public DirectionalDamage(String effectName, int damagePower, int redAmmos, int blueAmmos, int yellowAmmos){

        this.effectName = effectName;
        player = new Player(TokenColor.NONE);
        this.damagePower = damagePower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        canUse = true;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {


        player.setPosition(actionInterface.getCurrentPlayer().getPosition());

        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);

        if(canUse) {
                canUse = actionInterface.canMove(player,direction);
                player.updatePosition(direction);
                firstSquare = player.getPosition();
                if(canUse && effectName.equals("Flamethrower1"))
                    canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), victim);
                if(canUse && actionInterface.canMove(player, direction)){
                    player.updatePosition(direction);
                    if(effectName.equals("Flamethrower1") && secondVictim != null)
                        canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), secondVictim);
                    else
                        squares = 2;
                }
        }

        return canUse;
    }


    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(effectName.equals("Flamethrower1")) {
            actionInterface.playerDamage(victim.getColor(), damagePower);
            actionInterface.playerDamage(secondVictim.getColor(), damagePower);
        }else {
            actionInterface.squareDamage(firstSquare.getX(), firstSquare.getY(), damagePower, 0);
            if (squares == 2)
                actionInterface.squareDamage(player.getPosition().getX(), player.getPosition().getY(), 1, 0);
        }

    }
}
