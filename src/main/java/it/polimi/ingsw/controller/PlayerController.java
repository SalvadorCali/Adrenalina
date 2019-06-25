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

    public List<Token> getKillshotTrack() {
        return killshotTrack;
    }

    public void setKillshotTrack(List<Token> killshotTrack) {
        this.killshotTrack = killshotTrack;
    }

    public int getScore(){
        return player.getScore();
    }

    public PlayerBoard getPlayerBoard(){
        return player.getPlayerBoard();
    }

    public List<Ammo> getAmmos(){
        return player.getAmmoBox();
    }

    public List<PowerupCard> getPowerups(){
        return player.getPowerups();
    }

    public List<WeaponCard> getWeapons(){
        return player.getWeapons();
    }

    public AdrenalineZone getAdrenalineZone(){
        return player.getPlayerBoard().getAdrenalineZone();
    }

    public void addPowerup(PowerupCard powerup){
        player.addPowerup(powerup);
    }

    public void incrementMoves(){
        moves++;
    }

    public boolean canUseAction(){
        return moves < Config.MAX_ACTIONS;
    }

    public List<Player> getVictims() {
        return victims;
    }

    public void setVictims(List<Player> victims) {
        this.victims = victims;
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

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    public boolean isPlayerBoardFinalFrenzy() {
        return playerBoardFinalFrenzy;
    }

    public void setPlayerBoardFinalFrenzy(boolean playerBoardFinalFrenzy) {
        this.playerBoardFinalFrenzy = playerBoardFinalFrenzy;
    }

    public FinalFrenzyAction getFinalFrenzyActions(){
        return player.getFinalFrenzyActions();
    }
}
