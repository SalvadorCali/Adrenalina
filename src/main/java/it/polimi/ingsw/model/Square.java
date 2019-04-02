package it.polimi.ingsw.model;

import java.util.List;

import static it.polimi.ingsw.model.Direction.LEFT;

public abstract class Square {
    private Color color;
    private Cardinal north;
    private Cardinal south;
    private Cardinal west;
    private Cardinal east;
    private List<Player> players;

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
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


}
