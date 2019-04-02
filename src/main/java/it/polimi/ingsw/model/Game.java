package it.polimi.ingsw.model;

import java.util.List;

public class Game {
    private GameBoard board;
    private Player currentPlayer;
    private List<Player> players;
    private List<Token> killshotTrack;
    private Deck weapons;
    private Deck powerup;
    private Deck ammo;
    private boolean finalFrenzy;

    //getters and setters

    public GameBoard getBoard() {
        return board;
    }

    public void setBoard(GameBoard board) {
        this.board = board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Deck getWeapons() {
        return weapons;
    }

    public void setWeapons(Deck weapons) {
        this.weapons = weapons;
    }

    public Deck getPowerup() {
        return powerup;
    }

    public void setPowerup(Deck powerup) {
        this.powerup = powerup;
    }

    public Deck getAmmo() {
        return ammo;
    }

    public void setAmmo(Deck ammo) {
        this.ammo = ammo;
    }

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    public void endTurn(){
    }

    public boolean move(Direction...directions){
         return getBoard().canMove(getCurrentPlayer(), directions);
    }

    //methods

}
