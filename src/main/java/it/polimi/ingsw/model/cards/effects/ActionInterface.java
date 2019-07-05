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

    /**
     * Controls if the player has enough ammos to apply an action.
     * @param redAmmos red ammos required for the action.
     * @param blueAmmos blue ammos required for the action.
     * @param yellowAmmos yellow ammos required for the action.
     * @return the result of the control.
     */
    boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos);

    /**
     * Damages a player of a certain color.
     * @param color color of the player to damage.
     * @param damagePower number of damages given to the player.
     */
    void playerDamage(TokenColor color, int damagePower);

    /**
     * Gives marks to the victim.
     * @param shooter player who gives the marks.
     * @param victim victim receiving the marks.
     */
    void playerMark(Player shooter, Player victim);

    /**
     * Controls if the player of a certain color is visible from the current player.
     * @param victim color of the victim.
     * @return the result of the control.
     */
    boolean isVisible(TokenColor victim);

    /**
     * Control if the victim is visible from the shooter.
     * @param shooter player who's shooting.
     * @param victim victim.
     * @return the result of the control.
     */
    boolean isVisible(Player shooter, Player victim);

    /**
     * Adds ammos to the current player.
     * @param ammos ammos to add.
     */
    void addAmmo(Ammo...ammos);

    /**
     * Adds a powerup to the current player's powerups.
     */
    void addPowerup();

    /**
     * Adds a weapon to the current player's weapon cards.
     * @param weaponCard card to add.
     */
    void addWeapon(WeaponCard weaponCard);

    /**
     * Controls if the shooter and the victim are in the same square.
     * @param shooter player who's shooting.
     * @param victim player who's getting shooted.
     * @return the result of the control.
     */
    boolean sameSquare(Player shooter, Player victim);

    /**
     * Controls if a square is visible, but isn't the same square as the shooter's one.
     * @param x row of the square.
     * @param y column of th square.
     * @return the result of the control.
     */
    boolean isVisibleDifferentSquare(int x, int y);

    /**
     * Updates the ammo box of the current player after an action.
     * @param redAmmos red ammos required by the action.
     * @param blueAmmos blue ammos required by the action.
     * @param yellowAmmos yellow ammos required by the action.
     */
    void updateAmmoBox(int redAmmos, int blueAmmos, int yellowAmmos);

    /**
     * Controls if the victim can be moved in some consecutive directions.
     * @param victim player to move.
     * @param directions consecutive directions of the move.
     * @return the result of the control.
     */
    boolean canMove(Player victim, Direction... directions);

    /**
     * Controls the distance of a square from the current player.
     * @param x row of the square.
     * @param y column of the square.
     * @return the result of the control.
     */
    int distanceControl(int x, int y);

    /**
     * Damages all the players present in a room.
     * @param x row of a square in the room.
     * @param y column of a square in the room.
     * @param damagePower number of damages given by the action.
     * @param markPower number of marks given by the action.
     */
    void roomDamage(int x, int y, int damagePower, int markPower);

    /**
     * Damages all the players present in a square.
     * @param x row of the square.
     * @param y column of the square.
     * @param damagePower number of damages given by the action.
     * @param markPower number of marks given by the action.
     */
    void squareDamage(int x, int y, int damagePower, int markPower);

    /**
     * Moves the victim in a square on the board.
     * @param x row of the square.
     * @param y column of the square.
     * @param victim victim of the movement.
     */
    void move(int x, int y, Player victim);

    /**
     * Getter of the ammo card.
     * @return the ammo card.
     */
    AmmoCard getAmmo();

    /**
     * Controls if the player can grab a weapon.
     * @return the result of the control.
     */
    boolean canGetWeapon();

    /**
     * Draws a weapon card.
     * @return a weapon card.
     */
    WeaponCard getWeapon();

    /**
     * Getter of the current player of the game.
     * @return the current player.
     */
    Player getCurrentPlayer();

    /**
     * Controls that a square contains a certain player.
     * @param x row of the square.
     * @param y column of the square.
     * @param player player in the square.
     * @return the result of the control.
     */
    boolean squareControl(int x, int y, Player player);

    /**
     * Gets the first victim from the client data.
     * @return the first victim saved in the client data.
     * */
    Player getVictim();

    /**
     * Gets the second victim from the client data.
     * @return the second victim saved in the client data.
     */
    Player getSecondVictim();

    /**
     * Getter of the clientData.
     * @return clientData.
     */
    ClientData getClientData();

    /**
     * Generates a player in the position of a weapon victim.
     * @param victim victim that gives the position.
     * @param player player to set on the board.
     */
    void generatePlayer(Player victim, Player player);

    /**
     * Moves a player in a direction.
     * @param direction chosen direction.
     * @param player player to move.
     */
    void move(Direction direction, Player player);

    /**
     * Gets the first move saved in the client data.
     * @return the first move of the client data.
     */
    Direction getFirstMove();

    /**
     * Gets the second move saved in the client data.
     * @return the second move saved in the client data.
     */
    Direction getSecondMove();

    /**
     * Gets the third move saved in the client data.
     * @return the third move saved in the client data.
     */
    Direction getThirdMove();

    /**
     * Gets the fourth move saved in the client data.
     * @return the fourth move saved in the client data.
     */
    Direction getFourthMove();

    /**
     * Removes a player from the game board.
     * @param player player to remove from the game board.
     */
    void removePlayer(Player player);

    /**
     * Gets the position saved in the client data.
     * @return the position saved in the client data.
     */
    Position getSquare();

    /**
     * Gets the third victim saved in the client data.
     * @return the third victim saved in the client data.
     */
    Player getThirdVictim();

    /**
     * Controls that the player isn't going out of bounds.
     * @param player player that wants to move.
     * @param direction direction of the move to control.
     * @return the result of the control.
     */
    boolean noOutOfBounds(Player player, Direction direction);

    /**
     * Gets the fake player from the client data.
     * @return the fake player saved in the client data.
     */
    Player getFakePlayer();

    /**
     * Gets the value of basic first saved in client data.
     * @return the value of basic first.
     */
    boolean basicFirst();

    /**
     * Gives damages to the victim.
     * @param victim player who is damaged.
     * @param damagePower number of damages.
     */
    void playerDamage(Player victim, int damagePower);

    /**
     * Gives marks to the victim.
     * @param victim player who is marked.
     * @param markPower number of marks.
     */
    void playerMark(Player victim, int markPower);

    /**
     * Controls the distance of a square from the current player.
     * @param x row of the square.
     * @param y column of the square.
     * @return the result of the control.
     */
    int distanceControl(Player player, int x, int y);

    /**
     * Controls if the victim of the powerup is damaged.
     * @return the result of the control.
     */
    boolean isDamaged();

    /**
     * Controls if a square is active.
     * @param position position of the square to control.
     * @return the result of the control.
     */
    boolean isActive(Position position);
}
