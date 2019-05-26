package it.polimi.ingsw.model.cards.effects.weapons.basiceffects;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
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
                //canUse = actionInterface.isVisibleDifferentSquare(square.getX(), square.getY());
            else if (effectName.equals("Furnace2") && (actionInterface.distanceControl(square.getX(), square.getY()) != 1))
                    canUse = false;
        }
        return canUse;
    }
    @Override
    public void useEffect(ActionInterface actionInterface) {

        if (effectName.equals("Furnace1")) {
            actionInterface.roomDamage(square.getX(), square.getY(), damagePower, markPower);
        } else {
            if (effectName.equals("Hellion")) {
                actionInterface.playerDamage(victim.getColor(), damagePower);
                damagePower = 0;
            }
            actionInterface.squareDamage(square.getX(), square.getY(), damagePower, markPower);
        }
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    private void setData(ActionInterface actionInterface){
        actionInterface.getClientData().setAmmos();
        currentPlayer = actionInterface.getCurrentPlayer();
        victim = actionInterface.getVictim();
        square = actionInterface.getSquare();
        if(effectName.equals("Hellion"))
            actionInterface.getClientData().setSquare(victim.getPosition().getX(), victim.getPosition().getY());
        if(effectName.equals("Electroscythe"))
            actionInterface.getClientData().setSquare(currentPlayer.getPosition().getX(), currentPlayer.getPosition().getY());

    }


}
