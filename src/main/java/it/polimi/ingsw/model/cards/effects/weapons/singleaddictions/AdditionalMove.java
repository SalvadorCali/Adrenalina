package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.view.MapCLI;

public class AdditionalMove extends SingleAddictionEffect {

    private String effectName;

    private int damagePower;

    private int markPower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean canUse;

    private Player player;

    private Player currentPlayer;

    private boolean basicFirst;

    private Direction firstMove, secondMove;


    public AdditionalMove(String effectName, int damagePower, int markPower, int redAmmos, int blueAmmos, int yellowAmmos, Effect effect){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.markPower = markPower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        this.canUse = true;
        super.effect = effect;
    }



    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        setData(actionInterface);
        actionInterface.generatePlayer(player, currentPlayer);

            if(basicFirst) {
                canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
                if (canUse){
                    if(effectName.equals("Plasma Gun") || effectName.equals("Rocket Launcher"))
                        movementControl(actionInterface);
                    else
                        oneMovementControl(actionInterface);
                }
            }else{
                if(effectName.equals("Plasma Gun") || effectName.equals("Rocket Launcher"))
                    movementControl(actionInterface);
                else
                    oneMovementControl(actionInterface);
                if(canUse){
                    canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && super.effect.canUseEffect(actionInterface);
                }
            }
        System.out.print(player.getPosition().getX());
        System.out.println(player.getPosition().getY());
        actionInterface.removePlayer(player);
        return canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        super.effect.useEffect(actionInterface);
        actionInterface.move(player.getPosition().getX(), player.getPosition().getY(), currentPlayer);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    private void setData(ActionInterface actionInterface){
        currentPlayer = actionInterface.getClientData().getCurrentPlayer();
        basicFirst = actionInterface.basicFirst();
        player = actionInterface.getFakePlayer();
        firstMove = actionInterface.getFirstMove();
        secondMove = actionInterface.getSecondMove();
    }

    private void movementControl(ActionInterface actionInterface) {
        if (firstMove != null) {
            canUse = actionInterface.canMove(player, firstMove);
            if(canUse)
                actionInterface.move(firstMove, player);
            if (secondMove != null) {
                canUse = actionInterface.canMove(player, secondMove);
                if(canUse)
                    actionInterface.move(secondMove, player);
            }
        }
    }

    private void oneMovementControl(ActionInterface actionInterface){

        if(firstMove != null && secondMove == null) {
            canUse = actionInterface.canMove(player, firstMove);
            actionInterface.move(firstMove, player);
        }
        else if (firstMove!= null){
            canUse = false;
        }
    }
}
