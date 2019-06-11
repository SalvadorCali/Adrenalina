package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.List;

/**
 * class which represents an inactive square of the arena.
 */
public class InactivePoint extends Square implements Serializable {

    /**
     * constructor of the InactivePoint.
     * @param color indicates the color chosen to construct the inactive point.
     * @param north indicates the Cardinal in the upper side of the inactive point.
     * @param south indicates the Cardinal in the lower side of the inactive point.
     * @param west indicates the Cardinal in the left side of the inactive point.
     * @param east indicates the Cardinal in the right side of the inactive point.
     */
    public InactivePoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east, false);
    }

    /**
     * returns if the player can grab something from the inactive point (Never used).
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     * @return always false.
     */
    @Override
    public boolean canGrab(ActionInterface actionInterface, int choice) {
        return false;
    }

    /**
     * method to grab something from the inactive point(Never used).
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     */
    @Override
    public void grab(ActionInterface actionInterface, int choice) {
    }

    /**
     * method to fill the inactive point(Never used).
     * @param actionInterface contains the methods to access the game.
     */
    @Override
    public void fill(ActionInterface actionInterface) {
    }

    /**
     * method to get the ammo card present in the inactive point(Never Used).
     * @return always null.
     */
    @Override
    public AmmoCard getAmmoCard() {
        return null;
    }

    /**
     * method to get the list of the weapon cards present in the inactive point(Never Used).
     * @return always null.
     */
    @Override
    public List<WeaponCard> getWeapons() {
        return null;
    }

    /**
     * method to control if the square is an active point.
     * @return false, cause the Inactive point is not an active point.
     */
    @Override
    public boolean isActive(){
        return false;
    }

    @Override
    public void drop(Card card) {

    }
}