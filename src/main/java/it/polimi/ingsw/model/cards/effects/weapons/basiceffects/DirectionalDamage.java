package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
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
        player = new Player(TokenColor.NONE);
        this.damagePower = damagePower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        canUse = true;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getVictim();
        secondVictim = actionInterface.getSecondVictim();
        direction = actionInterface.getFirstMove();

        canUse = ammoControl(redAmmos, blueAmmos, yellowAmmos, actionInterface);

        if(canUse) {
            actionInterface.generatePlayer(currentPlayer, player);
            if(!effectName.equals("Railgun1") && !effectName.equals("Railgun2"))
                canUse = actionInterface.canMove(player, direction);
            else{
                canUse = actionInterface.noOutOfBounds(player,direction);
            }
            if(canUse) {
                actionInterface.move(direction, player);
                firstSquare = new Position(player.getPosition().getX(), player.getPosition().getY());
                if ((!effectName.equals("Flamethrower2"))){
                    canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), victim);
                }
                if (canUse) {
                    if((!effectName.equals("Railgun1")) && !effectName.equals("Railgun2")){
                        if(actionInterface.canMove(player, direction))
                            actionInterface.move(direction, player);
                    }else if(effectName.equals("Railgun2")){
                        if(actionInterface.noOutOfBounds(player, direction))
                            actionInterface.move(direction, player);
                    }
                    if (!effectName.equals("Flamethrower2") && secondVictim != null) {
                        canUse = actionInterface.squareControl(player.getPosition().getX(), player.getPosition().getY(), secondVictim);
                    } else {
                        squares = 2;
                    }
                }
            }
        }
        actionInterface.removePlayer(player);
        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        if(!effectName.equals("Flamethrower2")) {
            actionInterface.playerDamage(victim.getColor(), damagePower);
            if(secondVictim != null)
                actionInterface.playerDamage(secondVictim.getColor(), damagePower);
            if(effectName.equals("Power Glove2"))
                actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), currentPlayer);
        }else {
            actionInterface.squareDamage(firstSquare.getX(), firstSquare.getY(), damagePower, 0);
            if (squares == 2)
                actionInterface.squareDamage(player.getPosition().getX(), player.getPosition().getY(), 1, 0);
        }
    }
}
