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
 * Class which represents the ammo points in the arena.
 */
public class AmmoPoint extends Square implements Serializable {

    /**
     * AmmoCard present in the ammo point.
     */
    private AmmoCard ammoCard;

    /**
     * Constructor of the AmmoPoint
     * @param color indicates the color chosen to construct the ammo point.
     * @param north indicates the Cardinal in the upper side of the ammo point.
     * @param south indicates the Cardinal in the lower side of the ammo point.
     * @param west indicates the Cardinal in the left side of the ammo point.
     * @param east indicates the Cardinal in the right side of the ammo point.
     */
    public AmmoPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east, false);
    }

    /**
     * Getter of the ammo card present in the square.
     * @return the ammo card present in the square.
     */
    public AmmoCard getAmmoCard() {
        return ammoCard;
    }

    /**
     * Getter of the List of weapons present in the square
     * @return null: no Weapons are present in ammo points.
     */
    @Override
    public List<WeaponCard> getWeapons() {
        return null;
    }

    /**
     * Setter of the ammo card present in the square.
     * @param ammoCard ammo card to set in the ammo point.
     */
    void setAmmoCard(AmmoCard ammoCard) {
        this.ammoCard = ammoCard;
    }

    /**
     * Verifies if the ammo card present in the square can be grabbed by a player.
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     * @return the result of the control: true if the player can grab, false if he can't.
     */
    @Override
    public boolean canGrab(ActionInterface actionInterface, int choice){
        return choice == 0 && !isEmpty() ;
    }

    /**
     * Grabs the ammo card present in the ammo point.
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     */
    @Override
    public void grab(ActionInterface actionInterface, int choice) {
        if(ammoCard.isPowerup()){
            actionInterface.addAmmo(ammoCard.getFirstAmmo(), ammoCard.getSecondAmmo());
            actionInterface.addPowerup();
            ammoCard = null;
            setEmpty(true);
        }
        else{
            actionInterface.addAmmo(ammoCard.getFirstAmmo(), ammoCard.getSecondAmmo(), ammoCard.getThirdAmmo());
            ammoCard = null;
            setEmpty(true);
        }
    }

    /**
     * Fills the ammo point with a new ammo card.
     * @param actionInterface contains the methods to access the game.
     */
    @Override
    public void fill(ActionInterface actionInterface){
        setAmmoCard(actionInterface.getAmmo());
        setEmpty(false);
    }

    /**
     * Controls if the Square is an active square.
     * @return always true, cause the ammo point is an active square.
     */
    @Override
    public boolean isActive(){
        return true;
    }
    @Override
    public void drop(Card card) {

    }
}
