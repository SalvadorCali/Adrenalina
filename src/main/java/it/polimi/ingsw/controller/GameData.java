package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Token;

import java.io.Serializable;
import java.util.List;

public class GameData implements Serializable {
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameBoard getGameBoard(){
        return game.getBoard();
    }

    public List<Token> getKillshotTrack(){
        return game.getKillshotTrack();
    }
}
