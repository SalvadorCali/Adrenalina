package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract class that represents a square in the arena.
 */
public abstract class Square implements Serializable {
    private TokenColor color;
    private Cardinal north;
    private Cardinal south;
    private Cardinal west;
    private Cardinal east;
    private List<Player> players;
    private boolean empty;
    private boolean spawn;
    private static final long serialVersionUID = 1L;

    /**
     * constructor of the Square class.
     * @param color color of the square.
     * @param north entity of the upper side of the square.
     * @param south entity of the lower side of the square.
     * @param west entity of the left side of the square.
     * @param east entity of the right side of the square.
     * @param spawn boolean that indicates if the square is a spawn point.
     */
    public Square(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east, boolean spawn){
        this.color = color;
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
        this.spawn = spawn;
        players = new ArrayList<>();
        this.empty = false;
    }

    /**
     * getter of the upper side of the square.
     * @return the cardinal of the upper side of the square.
     */
    public Cardinal getNorth() {
        return north;
    }

    /**
     * getter of the lower side of the square.
     * @return the cardinal of the lower side of the square.
     */
    public Cardinal getSouth() {
        return south;
    }

    /**
     * getter of the left side of the square.
     * @return the cardinal of the left side of the square.
     */
    public Cardinal getWest() {
        return west;
    }

    /**
     * getter of the right side of the square.
     * @return the cardinal of the right side of the square.
     */
    public Cardinal getEast() {
        return east;
    }

    /**
     * getter of the color of the square.
     * @return the tokenColor of the square.
     */
    public TokenColor getColor() {
        return color;
    }

    /**
     * setter of the color of the square.
     * @param color indicates the chosen color of the square.
     */
    public void setColor(TokenColor color) {
        this.color = color;
    }

    /**
     * getter of the boolean spawn.
     * @return the boolean spawn: true if the square if a spawn point, false if isn't.
     */
    public boolean isSpawn() {
        return spawn;
    }

    /**
     * setter of the boolean spawn.
     * @param spawn indicates the chosen boolean.
     */
    public void setSpawn(boolean spawn) {
        this.spawn = spawn;
    }

    /**
     * getter of the boolean empty.
     * @return the boolean empty.
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * setter of the boolean empty.
     * @param empty indicates the chosen boolean.
     */
    void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * getter of the list of players that are located in the square.
     * @return the list that indicates the players that are located in the square.
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * evaluates if is possible to move in the chosen direction.
     * @param direction indicates the chosen direction.
     * @return the result of the control: true if the movement is possible, false if isn't.
     */
    public boolean canMove(Direction direction) {
        if(direction == null){
           return false;
        }
        switch (direction) {
            case UP:
                return getNorth().equals(Cardinal.DOOR) || getNorth().equals(Cardinal.ROOM);
            case DOWN:
                return getSouth().equals(Cardinal.DOOR) || getSouth().equals(Cardinal.ROOM);
            case RIGHT:
                return getEast().equals(Cardinal.DOOR) || getEast().equals(Cardinal.ROOM);
            case LEFT:
                return getWest().equals(Cardinal.DOOR) || getWest().equals(Cardinal.ROOM);
            default:
                return false;
        }
    }

    /**
     * add a chosen player to the list of the players present in the square.
     * @param player indicates the chosen player.
     */
    public void move(Player player) {
        players.add(player);
    }

    boolean containsPlayer(Player player){
        return players.contains(player);
    }

    /**
     * remove a chosen player from the list of the players present in the square.
     * @param player indicates the chosen player.
     */
    void moveAway(Player player) {

        for(int i = 0 ; i < players.size(); i++){
            if(players.get(i).equals(player))
                players.remove(i);
        }
    }

    /**
     * adds damages and marks of the chosen color to all the players in the square.
     * @param damagePower int which indicates the number of damages to add to each player in the square.
     * @param markPower int which indicates the number of marks to add to each player in the square.
     * @param color tokenColor that indicates the color of the damages/marks to add to each player in the square.
     */
    public void squareDamage(int damagePower, int markPower, TokenColor color) {
        for(Player player: getPlayers()){
            for (int i = 0; i < damagePower; i++){
                if(!player.getColor().equals(color)) {
                    player.getPlayerBoard().addDamage(color);
                    player.setDamaged(true);
                }
            }
            for(int j = 0; j < markPower; j++){
                if(!player.getColor().equals(color))
                    player.getPlayerBoard().addRevengeMarks(color);
            }
        }
    }

    /**
     * adds the chosen player to the list of the players present in the square.
     * @param player indicates the chosen player.
     */
    void addPlayer(Player player){
        players.add(player);
    }

    /**
     * evaluates if a card in the square can be grabbed by a player.
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     * @return the result of the control.
     */
    public abstract boolean canGrab(ActionInterface actionInterface, int choice);

    /**
     * grabs the card in the square in the chosen position.
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     */
    public abstract void grab(ActionInterface actionInterface, int choice);

    /**
     * fills the square with the missing cards.
     * @param actionInterface contains the methods to access the game.
     */
    public abstract void fill(ActionInterface actionInterface);

    /**
     * drops the chosen card.
     * @param card indicates the chosen card.
     */
    public abstract void drop(Card card);

    /**
     * getter of the ammo card present in the square.
     * @return the ammo card present in the square.
     */
    public abstract AmmoCard getAmmoCard();

    /**
     * getter of the weapon cards present in the square.
     * @return the list of weapons present in the square.
     */
    public abstract List<WeaponCard> getWeapons();

    /**
     * evaluates if the square is an active point.
     * @return the result of the control, true if the square is a spawn point or an ammo point, false if isn't.
     */
    public abstract boolean isActive();

    /**
     * evaluates if a move in the chosen direction brings a player out of the arena.
     * @param direction indicates the chosen direction to control.
     * @return the result of the control, true if the player is still in the arena, false if is out of bounds.
     */
    boolean noOutofBounds(Direction direction) {
        if(direction == null)
            return true;
        switch (direction) {
            case UP:
                return !getNorth().equals(Cardinal.NONE);
            case DOWN:
                return !getSouth().equals(Cardinal.NONE);
            case RIGHT:
                return !getEast().equals(Cardinal.NONE);
            case LEFT:
                return !getWest().equals(Cardinal.NONE);
            default:
                return false;
        }
    }
}
