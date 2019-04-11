package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Printer;

import java.util.*;

public class Game {
    private GameBoard board;
    private Player currentPlayer;
    private List<Player> players;
    private ArrayList<TokenColor> playerColors;
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
        scoreList = new HashMap<>();
        playerColors = new ArrayList<>();
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

    public List<AmmoCard> getAmmos() {
        return ammos;
    }

    public void setAmmos(List<AmmoCard> ammos) {
        this.ammos = ammos;
    }

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    public Map<TokenColor, Integer> getScoreList() {
        return scoreList;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Token> getKillshotTrack() {
        return killshotTrack;
    }

    //methods
    public void createKillshotTrack(int skulls){
        for(int i=0; i<skulls; i++){
            killshotTrack.add(new Token(TokenColor.SKULL));
        }
    }
    public void fillSquares(ActionInterface actionInterface){
        Square[][] arena = board.getArena();
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                if(!arena[i][j].getColor().equals(TokenColor.NONE)){
                    arena[i][j].fill(actionInterface);
                }
            }
        }
    }
    public void createScoreList(){
        for(Player player : players){
            scoreList.put(player.getColor(), 0);
            playerColors.add(player.getColor());
        }
    }
    public void addPlayer(Player player){
        players.add(player);
        if(players.size()==5){
            inGame = true;
        }
    }

    public void setRevengeMarks(){
        for(Player player : players){
            if(player.getPlayerBoard().isOverkill()){
                TokenColor tokenColor = player.getPlayerBoard().getOverkill();
                for(Player player1 : players){
                    if(tokenColor.equals(player1.getColor())){
                        player1.getPlayerBoard().addRevengeMarks(player.getColor());
                    }
                }
            }
        }
    }

    public void doubleKill(){
        List<Score> killshots = new ArrayList<>();
        for(Player player : players){
            if(player.getPlayerBoard().isDead()){
                TokenColor tokenColor = player.getPlayerBoard().getKillshot();
                killshots.add(new Score(tokenColor, 1));
            }
        }
        if(killshots.size()>1){
            for (Score score : killshots){
                if(score.getScore()>1){
                    int actualScore = scoreList.get(score.getColor());
                    actualScore += 1;
                    scoreList.replace(score.getColor(), actualScore);
                }
            }
        }
    }

    public void scoring(){
        Map<TokenColor, Score> tmpScoreList;
        ArrayList<TokenColor> tmpPlayerColors = new ArrayList<>();
        for(Player player : players){
            if(player.getPlayerBoard().isDead()){
                for (TokenColor playerColor : playerColors) {
                    if (!playerColor.equals(player.getColor())) {
                        tmpPlayerColors.add(playerColor);
                    }
                }
                tmpScoreList = player.getPlayerBoard().scoring(tmpPlayerColors);
                tmpPlayerColors.clear();
                for(int i=0; i<playerColors.size(); i++){
                    if(tmpScoreList.containsKey(playerColors.get(i))){
                        int actualScore = scoreList.get(playerColors.get(i));
                        actualScore+= tmpScoreList.get(playerColors.get(i)).getScore();
                        scoreList.replace(players.get(i).getColor(), actualScore);
                    }
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
