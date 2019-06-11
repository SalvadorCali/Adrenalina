package it.polimi.ingsw.model.gamecomponents;

import java.io.Serializable;

/**
 * class which represents the position of a player in the arena.
 */

public class Position implements Serializable {
    private int x;
    private int y;

    /**
     * constructor of the Position class.
     * @param x represents the row.
     * @param y represents the column.
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     *  getter of the row.
     */
    public int getX() {
        return x;
    }

    /**
     * setter of the row
     * @param x indicates the chosen row to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * getter of the column.
     * @return the column
     */
    public int getY() {
        return y;
    }

    /**
     * setter of the column.
     * @param y indicates the chosen column.
     */
    public void setY(int y) {
        this.y = y;
    }
}
