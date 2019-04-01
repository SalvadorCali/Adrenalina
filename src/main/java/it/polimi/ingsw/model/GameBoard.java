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
}
