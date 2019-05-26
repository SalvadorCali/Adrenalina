package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.*;

import java.io.Serializable;

public class GameBoard implements Serializable {
    private BoardType type;
    private Square[][] arena;
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final long serialVersionUID = 1L;

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

    public boolean canMove(int x, int y){
        return !arena[x][y].getColor().equals(TokenColor.NONE);
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

        return moved;

    }

    public void generatePlayer(int x, int y, Player player){
        arena[x][y].move(player);
        player.setPosition(x, y);
    }

    public void move(Direction direction, Player shooter) {

        int x, y;

        if(direction == null)
            return;

        x = shooter.getPosition().getX();
        y = shooter.getPosition().getY();

        switch (direction){
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
        }
        move(x, y, shooter);
    }

    public void move(int x, int y, Player player){

        arena[player.getPosition().getX()][player.getPosition().getY()].moveAway(player);
        arena[x][y].move(player);
        player.setPosition(x, y);
    }

    public boolean isVisible(Player shooter, Player victim) {

        int x = shooter.getPosition().getX();
        int y = shooter.getPosition().getY();
        int x_2 = victim.getPosition().getX();
        int y_2 = victim.getPosition().getY();

        return (sameRoom(x, y, x_2, y_2) || throughDoor(x, y, x_2, y_2));
    }

    public boolean isVisibleDifferentSquare(Player shooter, int x_2, int y_2){

        int x = shooter.getPosition().getX();
        int y = shooter.getPosition().getY();

        return (throughDoor(x, y, x_2, y_2));
    }

    public int distance(Player shooter, int x_2, int y_2) {

        Position position = new Position(shooter.getPosition().getX(), shooter.getPosition().getY());
        Position secondPosition = new Position(shooter.getPosition().getX(), shooter.getPosition().getY());

        if (shooter.getPosition().getX() == x_2 && shooter.getPosition().getY() == y_2)
            return 0;

        for (Direction direction : Direction.values()) {
            if (canMove(shooter, direction)) {
                move(direction, shooter);
                if (shooter.getPosition().getX() == x_2 && shooter.getPosition().getY() == y_2) {
                    move(position.getX(), position.getY(), shooter);
                    return 1;
                }
                move(position.getX(), position.getY(), shooter);
            }
        }



        for (Direction direction : Direction.values()) {
            if (canMove(shooter, direction)) {
                move(direction, shooter);
                secondPosition.setX(shooter.getPosition().getX());
                secondPosition.setY(shooter.getPosition().getY());
                for (Direction direction1 : Direction.values()) {
                    if (canMove(shooter, direction1)){
                        move(direction1, shooter);
                        if (shooter.getPosition().getX() == x_2 && shooter.getPosition().getY() == y_2){
                            move(position.getX(), position.getY(), shooter);
                            return 2;
                        }
                        move(secondPosition.getX(), secondPosition.getY(), shooter);
                    }
                }
                move(position.getX(), position.getY(), shooter);
            }
        }
        move(position.getX(), position.getY(), shooter);
        return 3;
    }



    public void roomDamage(int x, int y, int damagePower, int markPower, TokenColor shooterColor){




        TokenColor color = getArena()[x][y].getColor();

        if(type.equals(BoardType.PLAYERS_3_4) && (color.equals(TokenColor.PURPLE)|| color.equals(TokenColor.RED))){
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLUMNS; j++){
                    if(getArena()[i][j].getColor().equals(TokenColor.RED) || getArena()[i][j].getColor().equals(TokenColor.PURPLE) )
                        getArena()[i][j].squareDamage(damagePower, markPower, shooterColor);
                }
            }


        }else{
            for(int i = 0; i < ROWS; i++){
                for(int j = 0; j < COLUMNS; j++){
                    if(getArena()[i][j].getColor().equals(color))
                        getArena()[i][j].squareDamage(damagePower, markPower, shooterColor);
            }
        }
        }
    }

    private boolean sameRoom ( int x, int y, int x_2, int y_2){

            return (getArena()[x][y].getColor().equals(getArena()[x_2][y_2].getColor()));

        }

    private boolean throughDoor ( int x, int y, int x_2, int y_2){


        if (arena[x][y].getNorth().equals(Cardinal.DOOR))
            if (sameRoom(x - 1, y, x_2, y_2))
                return true;
        if (arena[x][y].getSouth().equals(Cardinal.DOOR))
            if (sameRoom(x + 1, y, x_2, y_2))
                return true;
        if (arena[x][y].getEast().equals(Cardinal.DOOR))
            if (sameRoom(x, y + 1, x_2, y_2))
                return true;
        if (arena[x][y].getWest().equals(Cardinal.DOOR)) {
            if (sameRoom(x, y - 1, x_2, y_2))
                return true;
        }


        return false;
    }

    public void setPlayer(Player player, Color color){
        TokenColor tokenColor = castColorToTokenColor(color);
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                if(arena[i][j].isSpawn() && arena[i][j].getColor().equals(tokenColor)){
                    arena[i][j].addPlayer(player);
                    player.setPosition(new Position(i,j));
                    break;
                }
            }
        }
    }

    public TokenColor castColorToTokenColor(Color color){
        switch (color){
            case BLUE:
                return TokenColor.BLUE;
            case RED:
                return TokenColor.RED;
            case YELLOW:
                return TokenColor.YELLOW;
            default:
                return TokenColor.NONE;
        }
    }


    public boolean noOutofBounds(Player player, Direction direction) {

        return arena[player.getPosition().getX()][player.getPosition().getY()].noOutofBounds(direction);
    }
}
