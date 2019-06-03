package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.*;

import java.io.Serializable;

/**
 * class which represents the game board of the game.
 */
public class GameBoard implements Serializable {
    private BoardType type;
    private Square[][] arena;
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final long serialVersionUID = 1L;

    /**
     * GameBoard class constructor.
     * @param type the type of board selected for the game.
     * @param arena the structure of the array selected for the game.
     */
    public GameBoard(BoardType type, Square[][] arena){
        this.type = type;
        this.arena = arena;
    }

    /**
     * getter of the type of the board.
     * @return the type of the board.
     */
    public BoardType getType() {
        return type;
    }

    /**
     * getter of the structure of the arena.
     * @return the structure of the arena.
     */
    public Square[][] getArena() {
        return arena;
    }

    /**
     * verifies if the player can move in consecutive directions.
     * @param player the player which moves are controlled.
     * @param directions the directions of the moves that need to be checked.
     * @return the result of the movement control.
     */
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

    /**
     * generates the player in a square of the arena.
     * @param x int which indicates the row of the square.
     * @param y int which indicates the column of the square.
     * @param player player which is generated.
     */
    public void generatePlayer(int x, int y, Player player){
        arena[x][y].move(player);
        player.setPosition(x, y);
    }

    /**
     * generates the player in the spawn point of the selected color.
     * @param player player which is generated in the spawn point.
     * @param color color of the spawn point where the player is generated.
     */
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

    /**
     * moves the player in a choosen direction.
     * @param direction direction of the move applied to the player.
     * @param shooter player which is moved.
     */
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

    /**
     * moves the player in a square of the arena.
     * @param x int that indicates the row of the square.
     * @param y int that indicates the column of the square.
     * @param player player wich is moved in the choosen square.
     */
    public void move(int x, int y, Player player){
        arena[player.getPosition().getX()][player.getPosition().getY()].moveAway(player);
        arena[x][y].move(player);
        player.setPosition(x, y);
    }

    /**
     * evaluates if the victim is visible from the shooter.
     * @param shooter player that is shooting.
     * @param victim player that is attempted to be shooted.
     * @return return the result of the visibility control.
     */
    public boolean isVisible(Player shooter, Player victim) {
        int x = shooter.getPosition().getX();
        int y = shooter.getPosition().getY();
        if(victim == null)
            return false;
        int x_2 = victim.getPosition().getX();
        int y_2 = victim.getPosition().getY();
        return (sameRoom(x, y, x_2, y_2) || throughDoor(x, y, x_2, y_2));
    }

    /**
     * evaluates if a square is visible from the shooter who isn't located in that square.
     * @param shooter player that is shooting.
     * @param x_2 int that indicates the row of the square.
     * @param y_2 int that indicates the column of the square.
     * @return the result of the visibility control.
     */
    public boolean isVisibleDifferentSquare(Player shooter, int x_2, int y_2){
        int x = shooter.getPosition().getX();
        int y = shooter.getPosition().getY();
        return (throughDoor(x, y, x_2, y_2));
    }

    /**
     * evaluates the distance between the shooter and a choosen square.
     * @param shooter Player which distance from the square is evaluated.
     * @param x_2 int which indicates the row of the choosen square.
     * @param y_2 int which indicates the column of the choosen square.
     * @return the number of moves which separates the shooter from the square.
     */
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

    /**
     * gives damages and marks of the shooter's color to all the players in the room of the choosen square.
     * @param x int which indicates the row of the choosen square.
     * @param y int which indicates the column of the choosen square.
     * @param damagePower int which indicates the number of damages to give to all the players in the room.
     * @param markPower int which indicates the number of marks to give to all the players in the room.
     * @param shooterColor tokencolor which indicates the color of the shooter.
     */
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

    /**
     * evaluates if the two selected squares are in the same room.
     * @param x int which indicates the row of the first selected square.
     * @param y int which indicates the column of the first selected square.
     * @param x_2 int which indicates the row of the second selected square.
     * @param y_2 int which indicates the column of the second selected square.
     * @return the result of the control: true if the squares are in the same room, false if aren't.
     */
    private boolean sameRoom ( int x, int y, int x_2, int y_2){
            return (getArena()[x][y].getColor().equals(getArena()[x_2][y_2].getColor()));
    }

    /**
     * evaluate if the second selected square is visible from the first square which isn't in the same room.
     * @param x int which indicates the row of the first square.
     * @param y int which indicates the column of the first square.
     * @param x_2 int which indicates the row of the second square.
     * @param y_2 int which indicates the column of the second square
     * @return the result of the control, true if the second square is visible through door by the first square.
     */
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

    /**
     * converts a color into a tokencolor
     * @param color color that needs to be converted.
     * @return the tokencolor that corresponds to the selected color.
     */
    private TokenColor castColorToTokenColor(Color color){
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

    /**
     * evaluates if the player isn't going out of the arena if moved in the chosen direction.
     * @param player player which move is evaluated.
     * @param direction direction of the move that needs to be evaluated.
     * @return the result of the evaluation.
     */
    public boolean noOutofBounds(Player player, Direction direction) {
        return arena[player.getPosition().getX()][player.getPosition().getY()].noOutofBounds(direction);
    }

    //???
    public boolean canMove(int x, int y){
        return !arena[x][y].getColor().equals(TokenColor.NONE);
    }
}
