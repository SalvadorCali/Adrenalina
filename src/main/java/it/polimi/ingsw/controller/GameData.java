package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GameData implements Serializable {
    private Game game;
    private Map<String, Player> users;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayers(Map<String, Player> users){
        this.users = users;
    }

    public GameBoard getGameBoard(){
        return game.getBoard();
    }

    public List<Token> getKillshotTrack(){
        return game.getKillshotTrack();
    }

    public Player getPlayer(String username){
        return users.get(username);
    }
}
