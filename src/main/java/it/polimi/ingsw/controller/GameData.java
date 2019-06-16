package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData implements Serializable {
    private Game game;
    private List<Player> victims;
    private Map<String, Player> users;
    private String powerup;
    private String weapon;
    private String currentPlayer;
    private boolean movement;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setVictims(List<Player> victims){
        this.victims = victims;
    }

    public List<Player> getVictims() {
        return victims;
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

    public String getPowerup() {
        return powerup;
    }

    public void setPowerup(String powerup) {
        this.powerup = powerup;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isMovement() {
        return movement;
    }

    public void setMovement(boolean movement) {
        this.movement = movement;
    }

    public Map<String, Player> getOtherPlayers(String username){
        Map<String, Player> players = new HashMap<>(users);
        players.remove(username);
        return players;
    }

    public List<Player> getPlayers(String username){
        Map<String, Player> playersWithoutUser = new HashMap<>(users);
        playersWithoutUser.remove(username);
        List<Player> players = new ArrayList<>(playersWithoutUser.values());
        return players;
    }
}
