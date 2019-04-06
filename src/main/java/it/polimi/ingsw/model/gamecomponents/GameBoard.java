package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Direction;

public class GameBoard {
    private BoardType type;
    private Square[][] arena;
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;

    public GameBoard(BoardType type, Square[][] arena){
        this.type = type;
        this.arena = arena;
    }

    public BoardType getType() {
        return type;
    }

    public void setType(BoardType type) {
        this.type = type;
    }

    public Square[][] getArena() {
        return arena;
    }

    public void setArena(Square[][] arena) {
        this.arena = arena;
    }

    public boolean canMove(Player player, Direction... directions){
        boolean moved = true;
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();

        for(Direction direction : directions){

            moved = arena[x][y].canMove(direction);
            if(!moved)
                return false;

            switch(direction){
                case UP:
                    x--;
                    break;
                case DOWN:
                    x++;
                    break;
                case RIGHT:
                    y++;
                    break;
                case LEFT:
                    y--;
                    break;
                default:
                    break;
            }
        }

        if(moved)
            move(x,y, player);

        return moved;

    }

    public void move(int x, int y, Player player){
        arena[player.getPosition().getX()][player.getPosition().getY()].moveAway(player);
        arena[x][y].move(player);
    }

    public boolean isVisible(Player shooter, Player victim) {

        int x = shooter.getPosition().getX();
        int y = shooter.getPosition().getY();
        int x_2 = victim.getPosition().getX();
        int y_2 = victim.getPosition().getY();

        return (sameRoom(x, y, x_2, y_2) || throughDoor(x, y, x_2, y_2));
    }

    private boolean sameRoom(int x, int y, int x_2, int y_2){

        return (getArena()[x][y].getColor().equals(getArena()[x_2][y_2].getColor()));

    }

    private boolean throughDoor(int x, int y, int x_2, int y_2){

        if(arena[x][y].getNorth().equals(Cardinal.DOOR))
            if (sameRoom(x - 1, y, x_2, y_2))
                return true;

        if(arena[x][y].getSouth().equals(Cardinal.DOOR))
            if (sameRoom(x + 1, y, x_2, y_2))
                return true;

        if(arena[x][y].getEast().equals(Cardinal.DOOR))
            if (sameRoom(x, y + 1, x_2, y_2))
                return true;

        if(arena[x][y].getWest().equals(Cardinal.DOOR))
            if (sameRoom(x, y - 1, x_2, y_2))
                return true;

        return false;
    }




}
