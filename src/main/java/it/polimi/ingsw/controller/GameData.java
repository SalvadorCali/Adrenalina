package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.TokenColor;
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
    private List<Card> powerups;
    private Map<String, Player> users;
    private String username;
    private TokenColor color;
    private String powerup;
    private String weapon;
    private String currentPlayer;
    private Player player;
    private SquareData squareData;
    private Map<TokenColor, Integer> scoreList;
    private boolean movement;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Map<TokenColor, Integer> getScoreList() {
        return scoreList;
    }

    public void setScoreList(Map<TokenColor, Integer> scoreList) {
        this.scoreList = scoreList;
    }

    public SquareData getSquareData() {
        return squareData;
    }

    public void setSquareData(SquareData squareData) {
        this.squareData = squareData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TokenColor getColor() {
        return color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Card> getPowerups() {
        return powerups;
    }

    public void setPowerups(List<Card> powerups) {
        this.powerups = powerups;
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
