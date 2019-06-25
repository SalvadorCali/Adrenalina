package it.polimi.ingsw.controller.datas;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains data about a square.
 */
public class SquareData implements Serializable {
    /**
     * The ammocard of the square.
     */
    private AmmoCard ammoCard;
    /**
     * The weapons of the square.
     */
    private List<WeaponCard> weapons;

    /**
     * Class constructor.
     */
    public SquareData(){
    }

    /**
     * Getter for the ammocard.
     * @return an ammocard.
     */
    public AmmoCard getAmmoCard() {
        return ammoCard;
    }

    /**
     * Setter for the ammocard.
     * @param ammoCard an ammocard that will be set.
     */
    public void setAmmoCard(AmmoCard ammoCard) {
        this.ammoCard = ammoCard;
    }

    /**
     * Getter for the weapons.
     * @return a list of weapons.
     */
    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    /**
     * Setter for the weapons.
     * @param weapons a list of weapons that will be set.
     */
    public void setWeapons(List<WeaponCard> weapons) {
        this.weapons = weapons;
    }
}
