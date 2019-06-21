package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.GameBoard;

public class CLIController {
    private static CLIController instance;
    private static String s;
    private GameBoard gameBoard;
    private CLIController(){ }
    public void setGameBoard(GameBoard gb){
        this.gameBoard = gameBoard;
    }
    public GameBoard getGameBoard(){
        return gameBoard;
    }
    public static void setA(String a){
        s = a;
    }
    public static String getA(){
        return s;
    }
}
