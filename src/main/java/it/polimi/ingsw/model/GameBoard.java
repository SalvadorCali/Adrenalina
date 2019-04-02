package it.polimi.ingsw.model;

public class GameBoard {
    private BoardType type;
    private Square[][] arena;
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;

    public BoardType getType() {
        return type;
    }

    public void setType(BoardType type) {
        this.type = type;
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


}
