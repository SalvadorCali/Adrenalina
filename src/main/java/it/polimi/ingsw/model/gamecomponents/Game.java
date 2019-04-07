package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.util.*;

public class Game {
    private GameBoard board;
    private Player currentPlayer;
    private List<Player> players;
    private List<Token> killshotTrack;
    private Deck weapons;
    private Deck powerups;
    private List<AmmoCard> ammos;
    private boolean finalFrenzy;
    private boolean inGame;
    private Map<TokenColor, Integer> scoreList;

    public Game(GameBoard board, Deck weapons, Deck powerups, List<AmmoCard> ammos){
        this.board = board;
        players = new ArrayList<>();
        killshotTrack = new ArrayList<>();
        scoreList = new EnumMap<>(TokenColor.class);
        createScoreList();
        this.weapons = weapons;
        this.powerups = powerups;
        this.ammos = ammos;
        finalFrenzy = false;
    }

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
        return powerups;
    }

    public void setPowerup(Deck powerups) {
        this.powerups = powerups;
    }

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    //methods
    private void createScoreList(){
        for(TokenColor color : TokenColor.values()){
            scoreList.put(color, 0);
        }
    }
    public void addPlayer(Player player){
        players.add(player);
        if(players.size()==5){
            inGame = true;
        }
    }

    public void scoring(){
        Map<TokenColor, Integer> tmpScoreList;
        int score;
        for(Player player : players){
            if(player.getPlayerBoard().isDead()){
                tmpScoreList = player.getPlayerBoard().scoring();
                for(TokenColor color : TokenColor.values()){
                    score = scoreList.get(color);
                    scoreList.replace(color, score, score + tmpScoreList.get(color));
                }
            }
        }
    }

    public void endTurn(){
    }

    public boolean move(Direction...directions){
         return getBoard().canMove(getCurrentPlayer(), directions);
    }

    public Player findPlayer(TokenColor color){
        for(Player player: players)
            if(player.getColor().equals(color))
                return player;

        return null;
    }

    public boolean isVisible(Player victim){

        return getBoard().isVisible(getCurrentPlayer(), victim);
    }

    public boolean sameSquare(TokenColor color){
        return currentPlayer.getPosition().equals(findPlayer(color).getPosition());
    }



}
