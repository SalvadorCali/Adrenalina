package it.polimi.ingsw.model.cards.effects.powerups;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;

public class powerupEffect extends Effect {

    private String powerupName;

    private boolean canUse;

    private Player victim = new Player(TokenColor.NONE);

    private Position square;


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
        canUse = actionInterface.isDamaged();
    }

    private void targetingScopeUse(ActionInterface actionInterface){
        actionInterface.playerDamage(actionInterface.getClientData().getPowerupVictim(), 1);
    }

    private void newton(ActionInterface actionInterface){

        Direction direction = actionInterface.getFirstMove();
        actionInterface.generatePlayer(actionInterface.getClientData().getPowerupVictim(),victim);
        canUse = actionInterface.canMove(victim, direction);
        if(canUse){
            actionInterface.move(direction, victim);
            if(direction.equals(actionInterface.getSecondMove()) && actionInterface.canMove(victim, direction))
                actionInterface.move(direction,victim);
        }
        actionInterface.removePlayer(victim);
    }

    private void newtonUse(ActionInterface actionInterface){
        actionInterface.move(victim.getPosition().getX(),victim.getPosition().getY(), actionInterface.getClientData().getPowerupVictim());
    }

    private void tagbackGrenade(ActionInterface actionInterface){

    }

    private void tagbackGrenadeUse(ActionInterface actionInterface){

    }

    private void teleporter(ActionInterface actionInterface){
        square = actionInterface.getClientData().getSquare();
        canUse = (square.getX() >= 0 && square.getX() < 3) && (square.getY() >= 0 && square.getY() < 4) && actionInterface.isActive(square);
    }

    private void teleporterUse(ActionInterface actionInterface){
        actionInterface.move(square.getX(), square.getY(), actionInterface.getCurrentPlayer());
    }







}
