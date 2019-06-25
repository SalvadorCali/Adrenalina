package it.polimi.ingsw.controller.datas;

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

/**
 * This class contains datas that will be sent to the Clients.
 */
public class GameData implements Serializable {
    /**
     * The current game.
     */
    private Game game;
    /**
     * The victims of an action.
     */
    private List<Player> victims;
    /**
     * A list of powerups.
     */
    private List<Card> powerups;
    /**
     * Users the are currently playing.
     */
    private Map<String, Player> users;
    /**
     * A player's username.
     */
    private String username;
    /**
     * A player's color.
     */
    private TokenColor color;
    /**
     * A powerup's name.
     */
    private String powerup;
    /**
     * A weapon's name.
     */
    private String weapon;
    /**
     * The username of the current player.
     */
    private String currentPlayer;
    /**
     * A player.
     */
    private Player player;
    /**
     * Datas of a square.
     */
    private SquareData squareData;
    /**
     * The current score.
     */
    private Map<TokenColor, Integer> scoreList;
    /**
     * A boolean value, true if an action used a movement.
     */
    private boolean movement;

    /**
     * Getter for the game.
     * @return the current game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Setter for the game.
     * @param game the game that will be set.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Getter for the score.
     * @return the score.
     */
    public Map<TokenColor, Integer> getScoreList() {
        return scoreList;
    }

    /**
     * Setter for the score.
     * @param scoreList the score that will be set.
     */
    public void setScoreList(Map<TokenColor, Integer> scoreList) {
        this.scoreList = scoreList;
    }

    /**
     * Getter for the datas of a square.
     * @return the square's datas.
     */
    public SquareData getSquareData() {
        return squareData;
    }

    /**
     * Setter fot the datas of a square.
     * @param squareData the datas that will be set.
     */
    public void setSquareData(SquareData squareData) {
        this.squareData = squareData;
    }

    /**
     * Getter for the username.
     * @return an username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     * @param username the username that will be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the color.
     * @return the color.
     */
    public TokenColor getColor() {
        return color;
    }

    /**
     * Setter for the color.
     * @param color the color that will be set.
     */
    public void setColor(TokenColor color) {
        this.color = color;
    }

    /**
     * Getter for the player.
     * @return a player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Setter for the player.
     * @param player the player that will be set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Getter for the list of powerups.
     * @return a list of powerups.
     */
    public List<Card> getPowerups() {
        return powerups;
    }

    /**
     * Setter for the list of powerups.
     * @param powerups a list of powerups that will be set.
     */
    public void setPowerups(List<Card> powerups) {
        this.powerups = powerups;
    }

    /**
     * Setter for the victims.
     * @param victims a list of victims that will be set.
     */
    public void setVictims(List<Player> victims){
        this.victims = victims;
    }

    /**
     * Getter for the victims.
     * @return a list of victims.
     */
    public List<Player> getVictims() {
        return victims;
    }

    /**
     * Setter for the users.
     * @param users a map of players.
     */
    public void setPlayers(Map<String, Player> users){
        this.users = users;
    }

    /**
     * Getter for the current game board.
     * @return the current game board.
     */
    public GameBoard getGameBoard(){
        return game.getBoard();
    }

    /**
     * Getter for the killshot track.
     * @return the killshot track.
     */
    public List<Token> getKillshotTrack(){
        return game.getKillshotTrack();
    }

    /**
     * Getter for a player.
     * @param username the username of the requested player.
     * @return a player.
     */
    public Player getPlayer(String username){
        return users.get(username);
    }

    /**
     * Getter for the powerup's name.
     * @return a powerup's name.
     */
    public String getPowerup() {
        return powerup;
    }

    /**
     * Setter for the powerup's name.
     * @param powerup the powerup that will be set.
     */
    public void setPowerup(String powerup) {
        this.powerup = powerup;
    }

    /**
     * Getter for the weapon's name.
     * @return a weapon's name.
     */
    public String getWeapon() {
        return weapon;
    }

    /**
     * Setter for the weapon's name.
     * @param weapon the weapon that will be set.
     */
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    /**
     * Getter for the current player.
     * @return the current player.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter for the current player.
     * @param currentPlayer the player that will be set as current.
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter for the movement value.
     * @return the movement variable.
     */
    public boolean isMovement() {
        return movement;
    }

    /**
     * Setter for the movement variable.
     * @param movement a value that will be set.
     */
    public void setMovement(boolean movement) {
        this.movement = movement;
    }

    /**
     * Getter for other players.
     * @param username the username of the player who requested the list.
     * @return a list of players without the player with username passed as parameter.
     */
    public Map<String, Player> getOtherPlayers(String username){
        Map<String, Player> players = new HashMap<>(users);
        players.remove(username);
        return players;
    }

    /**
     * Getter for other players.
     * @param username the username of the player who requested the list.
     * @return a list of players without the player with username passed as parameter.
     */
    public List<Player> getPlayers(String username){
        Map<String, Player> playersWithoutUser = new HashMap<>(users);
        playersWithoutUser.remove(username);
        List<Player> players = new ArrayList<>(playersWithoutUser.values());
        return players;
    }
}
