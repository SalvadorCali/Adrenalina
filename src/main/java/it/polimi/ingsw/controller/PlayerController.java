package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.FinalFrenzyAction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.*;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.util.Config;

import java.util.List;

/**
 * This class contains datas and methods of each player.
 */
public class PlayerController {
    /**
     * The relative Client.
     */
    private ClientInterface client;
    /**
     * The relative player.
     */
    private Player player;
    /**
     * The game board of the current game.
     */
    private GameBoard gameBoard;
    /**
     * The current killshot track.
     */
    private List<Token> killshotTrack;
    /**
     * The current victims of an action.
     */
    private List<Player> victims;
    /**
     * The other players.
     */
    private List<Player> otherPlayers;
    /**
     * The number of moves.
     */
    private int moves;
    /**
     * The name of a powerup.
     */
    private String powerup;
    /**
     * The name of a weapon.
     */
    private String weapon;
    /**
     * The username of the current player.
     */
    private String currentPlayer;
    /**
     * True if an action used a movement.
     */
    private boolean movement;
    /**
     * True during the final frenzy.
     */
    private boolean finalFrenzy;
    /**
     * True if the playerboard during the final frenzy was flipped.
     */
    private boolean playerBoardFinalFrenzy;

    /**
     * Getter for the other players.
     * @return a list of players.
     */
    public List<Player> getOtherPlayers() {
        return otherPlayers;
    }

    /**
     * Setter for the other players.
     * @param otherPlayers a list of players that will be set.
     */
    public void setOtherPlayers(List<Player> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    /**
     * Class constructor.
     * @param client the client that will be set.
     */
    public PlayerController(ClientInterface client){
        this.client = client;
    }

    /**
     * Getter for the player.
     * @return a player.
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Setter for the player.
     * @param player the player that will be set.
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Getter for the gameboard.
     * @return the gameboard.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Setter for the gameboard.
     * @param gameBoard the gameboard that will be set.
     */
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Getter for the killshot track.
     * @return the killshot track.
     */
    public List<Token> getKillshotTrack() {
        return killshotTrack;
    }

    /**
     * Setter for the killshot track.
     * @param killshotTrack the killshot track that will be set.
     */
    public void setKillshotTrack(List<Token> killshotTrack) {
        this.killshotTrack = killshotTrack;
    }

    /**
     * Getter for the score of the player.
     * @return the score of the player.
     */
    public int getScore(){
        return player.getScore();
    }

    /**
     * Getter for the playerboard.
     * @return the playerboard.
     */
    public PlayerBoard getPlayerBoard(){
        return player.getPlayerBoard();
    }

    /**
     * Getter for the ammos of the player.
     * @return the ammos of the player.
     */
    public List<Ammo> getAmmos(){
        return player.getAmmoBox();
    }

    /**
     * Getter for the player's powerups.
     * @return the player's powerups.
     */
    public List<PowerupCard> getPowerups(){
        return player.getPowerups();
    }

    /**
     * Getter for the player's weapons.
     * @return the player's weapons.
     */
    public List<WeaponCard> getWeapons(){
        return player.getWeapons();
    }

    /**
     * Getter for the player's adrenaline zone.
     * @return the player's adrenaline zone.
     */
    public AdrenalineZone getAdrenalineZone(){
        return player.getPlayerBoard().getAdrenalineZone();
    }

    /**
     * Adds a powerup to the player.
     * @param powerup the powerup card that will be added.
     */
    public void addPowerup(PowerupCard powerup){
        player.addPowerup(powerup);
    }

    /**
     * Increments the player's moves.
     */
    public void incrementMoves(){
        moves++;
    }

    /**
     * True if the player can use actions.
     * @return a boolean value, true if the player can use actions.
     */
    public boolean canUseAction(){
        return moves < Config.MAX_ACTIONS;
    }

    /**
     * Getter for the victims.
     * @return the victims.
     */
    public List<Player> getVictims() {
        return victims;
    }

    /**
     * Setter for the victims.
     * @param victims the victims that will be set.
     */
    public void setVictims(List<Player> victims) {
        this.victims = victims;
    }

    /**
     * Getter fot the powerup.
     * @return the powerup.
     */
    public String getPowerup() {
        return powerup;
    }

    /**
     * Setter for the powerup.
     * @param powerup the powerup that will be set.
     */
    public void setPowerup(String powerup) {
        this.powerup = powerup;
    }

    /**
     * Getter for the weapon.
     * @return the weapon.
     */
    public String getWeapon() {
        return weapon;
    }

    /**
     * Setter for the weapon.
     * @param weapon the weapon that will be set.
     */
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    /**
     * Getter for the name of the current player.
     * @return the current player's name.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter for the current player's name.
     * @param currentPlayer the current player's name that will be set.
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter for the movement value.
     * @return the movement value.
     */
    public boolean isMovement() {
        return movement;
    }

    /**
     * Setter for the movement value.
     * @param movement the boolean that will be set.
     */
    public void setMovement(boolean movement) {
        this.movement = movement;
    }

    /**
     * Getter for the final frenzy.
     * @return the final frenzy.
     */
    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * Setter for the final frenzy.
     * @param finalFrenzy the boolean value that will be set.
     */
    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * Getter for the value that is true if the playerboard was flipped during the final frenzy.
     * @return a boolean value.
     */
    public boolean isPlayerBoardFinalFrenzy() {
        return playerBoardFinalFrenzy;
    }

    /**
     * Setter for the boolean value.
     * @param playerBoardFinalFrenzy the boolean value that will be set.
     */
    public void setPlayerBoardFinalFrenzy(boolean playerBoardFinalFrenzy) {
        this.playerBoardFinalFrenzy = playerBoardFinalFrenzy;
    }

    /**
     * Getter for the current player's final frenzy action.
     * @return the final frenzy action of the player.
     */
    public FinalFrenzyAction getFinalFrenzyActions(){
        return player.getFinalFrenzyActions();
    }
}
