package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.gamecomponents.Ammo;
import java.io.Serializable;

/**
 * Class representing the Ammo Card.
 */
public class AmmoCard implements Serializable {

    /**
     * First ammo contained in the card.
     */
    private Ammo firstAmmo;
    /**
     * Second ammo contained in the card.
     */
    private Ammo secondAmmo;
    /**
     * Third ammo contained in the card.
     */
    private Ammo thirdAmmo;
    /**
     * Indicates if the ammo card contains a powerup.
     */
    private boolean powerup;

    /**
     * Class constructor of an ammo card containing a powerup.
     * @param firstAmmo first ammo contained in the ammo card.
     * @param secondAmmo second ammo contained in the ammo card.
     */
    public AmmoCard(Ammo firstAmmo, Ammo secondAmmo) {
        this.firstAmmo = firstAmmo;
        this.secondAmmo = secondAmmo;
        this.powerup = true;
    }

    /**
     * Class constructor of an ammo containing three ammos.
     * @param firstAmmo first ammo contained in the ammo card.
     * @param secondAmmo second ammo contained in the ammo card.
     * @param thirdAmmo third ammo contained in the ammo card.
     */
    public AmmoCard(Ammo firstAmmo, Ammo secondAmmo, Ammo thirdAmmo) {
        this.firstAmmo = firstAmmo;
        this.secondAmmo = secondAmmo;
        this.thirdAmmo = thirdAmmo;
        this.powerup = false;
    }

    /**
     * Getter of the first ammo contained in the ammo card.
     * @return the first ammo contained in the ammo card.
     */
    public Ammo getFirstAmmo() {
        return firstAmmo;
    }

    /**
     * Getter of the second ammo contained in the ammo card.
     * @return the second ammo contained in the ammo card.
     */
    public Ammo getSecondAmmo() {
        return secondAmmo;
    }

    /**
     * Getter of the third ammo contained in the ammo card.
     * @return the third ammo contained in the ammo card.
     */
    public Ammo getThirdAmmo() {
        return thirdAmmo;
    }

    /**
     * Controls if the ammo card contains a powerup.
     * @return true if the ammo card contains a powerup, false if doesn't.
     */
    public boolean isPowerup() {
        return powerup;
    }

    /**
     * Setter of the boolean powerup.
     * @param powerup the chosen value to be setted.
     */
    public void setPowerup(boolean powerup) {
        this.powerup = powerup;
    }
}
