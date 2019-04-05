package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.PowerupCard;

public class PowerUpAmmo extends AmmoCard {

    private PowerupCard powerupCard;

    public PowerUpAmmo(Ammo firstAmmo, Ammo secondAmmo) {
        super(firstAmmo, secondAmmo);
        setPowerup(true);
    }

    public PowerupCard getPowerupCard() {
        return powerupCard;
    }
    
}
