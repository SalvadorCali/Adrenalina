package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.controller.ClientData;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;


public interface ActionInterface {

    boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos);

    void playerDamage(TokenColor color, int damagePower);

    void playerMark(TokenColor color, int markPower);

    boolean isVisible(TokenColor victim);

    boolean isVisible(Player shooter, Player victim);

    void addAmmo(Ammo...ammos);

    void addPowerup();

    void addWeapon(WeaponCard weaponCard);

    boolean sameSquare(Player shooter, Player victim);

    boolean isVisibleDifferentSquare(int x, int y);

    void updateAmmoBox(int redAmmos, int blueAmmos, int redAmmos1);

    boolean canMove(Player victim, Direction... directions);

    int distanceControl(int x, int y);

    void roomDamage(int x, int y, int damagePower, int markPower);

    void squareDamage(int x, int y, int damagePower, int markPower);

    void move(int x, int y, Player victim);

    AmmoCard getAmmo();

    WeaponCard getWeapon();

    Player getCurrentPlayer();

    boolean squareControl(int x, int y, Player player);

    Player getVictim();

    Player getSecondVictim();

    ClientData getClientData();

    void generatePlayer(Player victim, Player player);

    void move(Direction direction, Player player);

    Direction getFirstMove();

    Direction getSecondMove();

    Direction getThirdMove();

    Direction getFourthMove();

    void removePlayer(Player player);

    Position getSquare();

    Player getThirdVictim();

    boolean noOutOfBounds(Player player, Direction direction);

    Player getFakePlayer();

    boolean basicFirst();


}
