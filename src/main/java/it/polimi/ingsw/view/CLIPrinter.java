package it.polimi.ingsw.view;

import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;

import java.util.Map;

public class CLIPrinter {
    private GameBoard gameBoard;
    private Map<String, Player> players;

    public CLIPrinter(GameBoard gameBoard, Map<String, Player> players){
        this.gameBoard = gameBoard;
        this.players = players;
    }

    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public Map<String, Player> getPlayers(){
        return players;
    }
}
