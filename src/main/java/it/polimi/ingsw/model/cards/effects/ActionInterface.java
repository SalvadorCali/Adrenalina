package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;

import java.util.List;

public interface ActionInterface {

    boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos);

    void playerDamage(TokenColor color, int damagePower);

    void playerMark(TokenColor color, int markPower);

    boolean isVisible(TokenColor blue);

    void addAmmo(Ammo...ammos);

    void addPowerup();

    void addWeapon(WeaponCard weaponCard);

    boolean sameSquare(TokenColor color);

    boolean isVisibleDifferentSquare(int x, int y);

    void updateAmmoBox(int redAmmos, int blueAmmos, int redAmmos1);

    boolean canMove(TokenColor victim, Direction... directions);

    int distanceControl(int x, int y);

    void roomDamage(int x, int y, int damagePower, int markPower);

    void squareDamage(int x, int y, int damagePower, int markPower);

    void move(int x, int y, Player victim);

    AmmoCard getAmmo();

    WeaponCard getWeapon();
}
