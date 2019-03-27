package it.polimi.ingsw.model;

public abstract class Square {
    private Color color;
    private Cardinal north;
    private Cardinal south;
    private Cardinal west;
    private Cardinal east;

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

    public abstract Card grab();
}
