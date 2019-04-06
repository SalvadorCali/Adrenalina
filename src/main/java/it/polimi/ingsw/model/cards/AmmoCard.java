package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Game;

public class AmmoCard {

    private Ammo firstAmmo;
    private Ammo secondAmmo;
    private Ammo thirdAmmo;
    private boolean powerup;

    public AmmoCard(Ammo firstAmmo, Ammo secondAmmo) {
        this.firstAmmo = firstAmmo;
        this.secondAmmo = secondAmmo;
        this.powerup = true;
    }

    public AmmoCard(Ammo firstAmmo, Ammo secondAmmo, Ammo thirdAmmo) {
        this.firstAmmo = firstAmmo;
        this.secondAmmo = secondAmmo;
        this.thirdAmmo = thirdAmmo;
        this.powerup = false;
    }

    //getters and setters
    public Ammo getFirstAmmo() {
        return firstAmmo;
    }

    public Ammo getSecondAmmo() {
        return secondAmmo;
    }

    public Ammo getThirdAmmo() {
        return secondAmmo;
    }

    public boolean isPowerup() {
        return powerup;
    }

    public void setPowerup(boolean powerup) {
        this.powerup = powerup;
    }

    //methods
    public void pick(){
        //implements after parsing ammo methods
    }
}
