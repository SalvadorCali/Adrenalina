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

public class PlayerController {
    private ClientInterface client;
    private Player player;
    private GameBoard gameBoard;
    private List<Token> killshotTrack;
    private List<Player> victims;
    private List<Player> otherPlayers;
    private int moves;
    private String powerup;
    private String weapon;
    private String currentPlayer;
    private boolean movement;
    private boolean finalFrenzy;

    public List<Player> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(List<Player> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }

    public PlayerController(ClientInterface client){
        this.client = client;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

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

    public FinalFrenzyAction getFinalFrenzyActions(){
        return player.getFinalFrenzyActions();
    }
}
