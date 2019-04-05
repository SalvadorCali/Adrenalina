package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.util.ArrayList;
import java.util.List;


public abstract class Square {
    private TokenColor tokenColor;
    private Cardinal north;
    private Cardinal south;
    private Cardinal west;
    private Cardinal east;
    private List<Player> players;
    private Color color;

    public Square(TokenColor tokenColor, Cardinal north, Cardinal south, Cardinal west, Cardinal east){
        this.tokenColor = tokenColor;
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
        players = new ArrayList<>();
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTokenColor(TokenColor tokenColor) {
        this.tokenColor = tokenColor;
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
            if(players.get(i).getColor().equals(player.getColor()))
                players.remove(i);
        }
    }

    public abstract void grab(Player player, int choice);


}
