package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;

public interface ActionInterface {

    boolean ammoControl(int ammos, Color color);

    void playerDamage(TokenColor color, int damagePower);

    void playerMark(TokenColor color, int markPower);

    boolean isVisible(TokenColor blue);

    void addAmmo(Ammo...ammos);

    void addPowerup();

    void addWeapon(WeaponCard weaponCard);

    boolean sameSquare(TokenColor color);

    boolean isVisibleDifferentSquare(int x, int y);
}
