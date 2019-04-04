package it.polimi.ingsw.model;

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


}
