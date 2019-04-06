package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;

public interface ActionInterface {
    
    void playerDamage(TokenColor color, int damagePower);

    void playerMark(TokenColor color, int markPower);

    boolean isVisible(TokenColor blue);

    void addAmmo(Ammo...ammos);

    void addPowerup();

    void addWeapon(WeaponCard weaponCard);
}
