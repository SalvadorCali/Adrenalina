package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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


    public Cardinal getNorth() {
        return north;
    }

    public void setNorth(Cardinal north) {
        this.north = north;
    }

    public Cardinal getSouth() {
        return south;
    }

    public void setSouth(Cardinal south) {
        this.south = south;
    }

    public Cardinal getWest() {
        return west;
    }

    public void setWest(Cardinal west) {
        this.west = west;
    }

    public Cardinal getEast() {
        return east;
    }

    public void setEast(Cardinal east) {
        this.east = east;
    }

    public TokenColor getColor() {
        return color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }

    public boolean isSpawn() {
        return spawn;
    }

    public void setSpawn(boolean spawn) {
        this.spawn = spawn;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public boolean canMove(Direction direction) {
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

    public void move(Player player) {
        players.add(player);
    }

    public void moveAway(Player player) {

        for(int i = 0 ; i < players.size(); i++){
            if(players.get(i).equals(player))
                players.remove(i);
        }
    }

    public void squareDamage(int damagePower, int markPower, TokenColor color) {
        for(Player player: getPlayers()){
            for (int i = 0; i < damagePower; i++){
                player.getPlayerBoard().addDamage(color);
            }
            for(int j = 0; j < markPower; j++){
                player.getPlayerBoard().addRevengeMarks(color);
            }
        }
    }

    public void addPlayer(Player player){
        players.add(player);
    }
    public abstract boolean canGrab(ActionInterface actionInterface, int choice);
    public abstract void grab(ActionInterface actionInterface, int choice);
    public abstract void fill(ActionInterface actionInterface);
    public abstract AmmoCard getAmmoCard();
    public abstract List<WeaponCard> getWeapons();


    public boolean noOutofBounds(Direction direction) {

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
