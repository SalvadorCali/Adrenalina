package it.polimi.ingsw.model.cards.effects.powerups;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;
import it.polimi.ingsw.util.Printer;

public class PowerupEffect extends Effect {

    private String powerupName;

    private boolean canUse;

    private Player victim = new Player(TokenColor.NONE);

    private Position square;

    public PowerupEffect(String powerupName){
        this.powerupName = powerupName;
        this.canUse = true;
    }

    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        switch (powerupName) {
            case ("Targeting Scope"):
                targetingScope(actionInterface);
                break;
            case ("Newton"):
                newton(actionInterface);
                break;
            case ("Tagback Grenade"):
                tagbackGrenade(actionInterface);
                break;
            case ("Teleporter"):
                teleporter(actionInterface);
                break;
            default:
                break;
        }
        return  canUse;
    }

    @Override
    public void useEffect(ActionInterface actionInterface) {

        switch (powerupName){
            case ("Targeting Scope"):
                targetingScopeUse(actionInterface);
                break;
            case("Newton"):
                newtonUse(actionInterface);
                break;
            case("Tagback Grenade"):
                tagbackGrenadeUse(actionInterface);
                break;
            case("Teleporter"):
                teleporterUse(actionInterface);
                break;
            default:
                break;
        }
    }

    private void targetingScope(ActionInterface actionInterface){
        actionInterface.getClientData().setAmmos();
        canUse = !actionInterface.getClientData().getAmmoColor().equals(Color.NONE) && actionInterface.isDamaged();
        if(canUse) {
            if (actionInterface.getClientData().getAmmoColor().equals(Color.RED))
                canUse = actionInterface.ammoControl(1, 0, 0);
            else if (actionInterface.getClientData().getAmmoColor().equals(Color.BLUE))
                canUse = actionInterface.ammoControl(0, 1, 0);
            else
                canUse = actionInterface.ammoControl(0, 0, 1);
        }
    }

    private void targetingScopeUse(ActionInterface actionInterface){
        actionInterface.playerDamage(actionInterface.getClientData().getPowerupVictim(), 1);
        if (actionInterface.getClientData().getAmmoColor().equals(Color.RED))
            actionInterface.updateAmmoBox(1, 0, 0);
        else if (actionInterface.getClientData().getAmmoColor().equals(Color.BLUE))
            actionInterface.updateAmmoBox(0, 1, 0);
        else
            actionInterface.updateAmmoBox(0, 0, 1);
    }

    private void newton(ActionInterface actionInterface){

        Direction direction = actionInterface.getClientData().getFirstMove();
        Direction secondDirection = actionInterface.getClientData().getSecondMove();
        actionInterface.generatePlayer(actionInterface.getClientData().getPowerupVictim(),victim);
        canUse = ((direction.equals(secondDirection)||secondDirection == null) && actionInterface.canMove(victim, direction));
        if(canUse){
            actionInterface.move(direction, victim);
            if(direction.equals(actionInterface.getSecondMove()) && actionInterface.canMove(victim, direction))
                actionInterface.move(direction,victim);
            else if(actionInterface.getSecondMove() != null)
                canUse = false;
        }
        actionInterface.removePlayer(victim);
    }

    private void newtonUse(ActionInterface actionInterface){
        actionInterface.move(victim.getPosition().getX(),victim.getPosition().getY(), actionInterface.getClientData().getPowerupVictim());
    }

    private void tagbackGrenade(ActionInterface actionInterface){
        victim = actionInterface.getClientData().getPowerupVictim();
        int count = -1;
        for(int i = 0; i < actionInterface.getClientData().getCurrentPlayer().getPlayerBoard().getDamageBoard().length; i++){
            if(actionInterface.getClientData().getCurrentPlayer().getPlayerBoard().getDamageBoard()[i].getFirstColor().equals(TokenColor.NONE)){
                count = i - 1;
                break;
            }
        }
        canUse = count!= -1 && actionInterface.getClientData().getCurrentPlayer().isDamaged() && actionInterface.getClientData().getCurrentPlayer().getPlayerBoard().getDamageBoard()[count].getFirstColor().equals(victim.getColor());
    }

    private void tagbackGrenadeUse(ActionInterface actionInterface){
        actionInterface.playerMark(victim, 1);
    }

    private void teleporter(ActionInterface actionInterface){
        square = actionInterface.getClientData().getSquare();
        canUse = (square.getX() >= 0 && square.getX() < 3) && (square.getY() >= 0 && square.getY() < 4) && actionInterface.isActive(square);
    }

    private void teleporterUse(ActionInterface actionInterface){
        actionInterface.move(square.getX(), square.getY(), actionInterface.getCurrentPlayer());
    }

}
