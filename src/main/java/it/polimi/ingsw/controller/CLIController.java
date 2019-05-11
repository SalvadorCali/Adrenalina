package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;

import java.util.Map;

public class CLIController {
    private static GameBoard gameBoard;
    private static Map<String, Player> players;

    public static void setGameBoard(GameBoard gb){
        gameBoard = gb;
    }
    public static GameBoard getGameBoard(){
        return gameBoard;
    }
    public static void setPlayers(Map<String, Player> p){
        players = p;
    }
    public static Map<String, Player> getPlayers(){
        return players;
    }
}
