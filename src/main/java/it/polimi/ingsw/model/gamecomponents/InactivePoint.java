package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.List;

public class InactivePoint extends Square implements Serializable {
    public InactivePoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east, false);
    }

    @Override
    public boolean canGrab(ActionInterface actionInterface, int choice) {
        return false;
    }

    @Override
    public void grab(ActionInterface actionInterface, int choice) {
    }

    @Override
    public void fill(ActionInterface actionInterface) {

    }

    @Override
    public AmmoCard getAmmoCard() {
        return null;
    }

    @Override
    public List<WeaponCard> getWeapons() {
        return null;
    }

    @Override
    public boolean isActive(){
        return false;
    }

}