package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;

public class ThirdAmmo extends AmmoCard {

    private Ammo thirdAmmo;

    public ThirdAmmo(Ammo firstAmmo, Ammo secondAmmo, Ammo thirdAmmo) {
        super(firstAmmo, secondAmmo);
        this.thirdAmmo = thirdAmmo;
    }

    public Ammo getThirdAmmo() {
        return thirdAmmo;
    }
}
