package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;
import it.polimi.ingsw.util.Printer;

public class SquareDamageEffect extends BasicEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private Position square;

    private Player currentPlayer, victim;

    private boolean up, down, right, left;

    private boolean canUse;

    public SquareDamageEffect(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos){

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
        canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);// Electroscythe
        if(canUse) {
            if (effectName.equals("Hellion"))
                canUse = actionInterface.isVisible(currentPlayer,victim) && (actionInterface.distanceControl(victim.getPosition().getX(),victim.getPosition().getY())>= 1);
            else if(effectName.equals("Furnace1"))
                canUse = actionInterface.isVisibleDifferentSquare(square.getX(),square.getY());
            else if (effectName.equals("Furnace2") && (actionInterface.distanceControl(square.getX(), square.getY()) != 1))
                    canUse = false;
            else if(effectName.equals("Shockwave")){
                if(actionInterface.canMove(currentPlayer, Direction.UP))
                    up = true;
                if(actionInterface.canMove(currentPlayer,Direction.DOWN))
                    down = true;
                if(actionInterface.canMove(currentPlayer,Direction.RIGHT))
                    right = true;
                if(actionInterface.canMove(currentPlayer, Direction.LEFT))
                    left = true;
            }
        }
        return canUse;
    }
    @Override
    public void useEffect(ActionInterface actionInterface) {

        if (effectName.equals("Furnace1")) {
            actionInterface.roomDamage(square.getX(), square.getY(), damagePower, markPower);
        }else if(effectName.equals("Shockwave")){
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
        }
        else {
            if (effectName.equals("Hellion")) {
                actionInterface.playerDamage(victim, damagePower);
                damagePower--;
            }
            actionInterface.squareDamage(square.getX(), square.getY(), damagePower, markPower);
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    private void setData(ActionInterface actionInterface){
        actionInterface.getClientData().setAmmos();
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        victim = actionInterface.getVictim();
        square = actionInterface.getSquare();
        if(effectName.equals("Hellion"))
            actionInterface.getClientData().setSquare(victim.getPosition().getX(), victim.getPosition().getY());
        if(effectName.equals("Electroscythe") || effectName.equals("Shockwave"))
            actionInterface.getClientData().setSquare(currentPlayer.getPosition().getX(), currentPlayer.getPosition().getY());

    }


}
