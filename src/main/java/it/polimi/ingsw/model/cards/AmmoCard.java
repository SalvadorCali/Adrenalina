package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.gamecomponents.Ammo;

public class AmmoCard {

    private Ammo firstAmmo;
    private Ammo secondAmmo;
    private boolean powerup;

    public AmmoCard(Ammo firstAmmo, Ammo secondAmmo) {
        this.firstAmmo = firstAmmo;
        this.secondAmmo = secondAmmo;
        this.powerup = false;
    }

    public Ammo getFirstAmmo() {
        return firstAmmo;
    }

    public Ammo getSecondAmmo() {
        return secondAmmo;
    }

    public boolean isPowerup() {
        return powerup;
    }

    public void setPowerup(boolean powerup) {
        this.powerup = powerup;
    }

    public void pick(){
        //implements after parsing ammo methods
    }
}
