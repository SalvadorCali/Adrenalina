package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Direction;

import java.io.Serializable;

/**
 * Class which represents the position of a player in the arena.
 */
public class Position implements Serializable {
    /**
     * Row of the position.
     */
    private int x;
    /**
     * Column of the position.
     */
    private int y;

    /**
     * Constructor of the Position class.
     * @param x represents the row.
     * @param y represents the column.
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     *  Getter of the row.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter of the row
     * @param x indicates the chosen row to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter of the column.
     * @return the column
     */
    public int getY() {
        return y;
    }

    /**
     * Setter of the column.
     * @param y indicates the chosen column.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Modifies a position after some moves.
     * @param directions directions of the move.
     */
    public void incrementPosition(Direction...directions){
        for(Direction direction : directions){
            switch(direction){
                case UP:
                    x--;
                    break;
                case DOWN:
                    x++;
                    break;
                case LEFT:
                    y--;
                    break;
                case RIGHT:
                    y++;
                    break;
                default:
                    break;
            }
        }
    }
}
