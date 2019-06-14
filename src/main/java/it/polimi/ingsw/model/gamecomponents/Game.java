package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.*;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
    public static final int  ROWS = 3;
    public static final int COLUMNS = 4;
    private GameBoard board;
    private Player currentPlayer;
    private List<Player> players;
    private ArrayList<TokenColor> playerColors;
    private Map<String, TokenColor> playersMap;
    private List<Token> killshotTrack;
    private int killshotIndex;
    private int skullsNumber;
    private Deck weapons;
    private Deck powerups;
    private List<AmmoCard> ammos;
    private int finalFrenzyTurns;
    private boolean finalFrenzy;
    private boolean gamePhase;
    private boolean colorSelection;
    private boolean loginPhase;
    private boolean spawnLocationPhase;
    private boolean boardTypePhase;
    private boolean respawnPhase;
    private boolean endPhase;
    private Map<TokenColor, Integer> scoreList;

    /**
     * constructor of the Game class.
     * @param board the arena that was chosen for the game.
     * @param weapons the weapons present in the game.
     * @param powerups the powerups present in the game.
     * @param ammos the ammo cards present in the game.
     */
    public Game(GameBoard board, Deck weapons, Deck powerups, List<AmmoCard> ammos){
        this.board = board;
        players = new ArrayList<>();
        killshotTrack = new ArrayList<>();
        scoreList = new HashMap<>();
        playerColors = new ArrayList<>();
        playersMap = new HashMap<>();
        this.weapons = weapons;
        this.powerups = powerups;
        this.ammos = ammos;
        gamePhase = false;
        finalFrenzy = false;
        killshotIndex = 0;
    }

    /**
     * getter of the boolean respawnPhase.
     * @return the value of the boolean respawnPhase.
     */
    public boolean isRespawnPhase() {
        return respawnPhase;
    }

    /**
     * setter of the boolean respawnPhase.
     * @param respawnPhase chosen value of the boolean respawnPhase.
     */
    public void setRespawnPhase(boolean respawnPhase) {
        this.respawnPhase = respawnPhase;
    }

    /**
     * getter of the boolean gamePhase.
     * @return the value of the boolean gamePhase.
     */
    public boolean isGamePhase() {
        return gamePhase;
    }

    /**
     * setter of the boolean gamePhase.
     * @param gamePhase chosen value of the boolean gamePhase.
     */
    public void setGamePhase(boolean gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * getter of the boolean spawnLocationPhase.
     * @return the value of the boolean spawnLocationPhase.
     */
    public boolean isSpawnLocationPhase() {
        return spawnLocationPhase;
    }

    /**
     * setter of the boolean spawnLocationPhase.
     * @param spawnLocationPhase chosen value of the boolean spawnLocationPhase.
     */
    public void setSpawnLocationPhase(boolean spawnLocationPhase) {
        this.spawnLocationPhase = spawnLocationPhase;
    }

    /**
     * getter of the boolean boardTypePhase.
     * @return the value of the boolean boardTypePhase.
     */
    public boolean isBoardTypePhase() {
        return boardTypePhase;
    }

    /**
     * setter of the boolean boardTypePhase.
     * @param boardTypePhase chosen value of the boolean boardTypePhase.
     */
    public void setBoardTypePhase(boolean boardTypePhase) {
        this.boardTypePhase = boardTypePhase;
    }

    /**
     * getter of the boolean colorSelection.
     * @return the value of the boolean colorSelection.
     */
    public boolean isColorSelection() {
        return colorSelection;
    }

    /**
     * setter of the boolean colorSelection.
     * @param colorSelection chosen value for the boolean colorSelection.
     */
    public void setColorSelection(boolean colorSelection) {
        this.colorSelection = colorSelection;
    }

    /**
     * getter of the board of the game.
     * @return the board of the game.
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * setter of the board of the game.
     * @param board chosen board to set as the board of the game.
     */
    public void setBoard(GameBoard board) {
        this.board = board;
    }

    /**
     * getter of the player that is currently playing.
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * setter of the player that is currently playing.
     * @param currentPlayer chosen player to set as current player.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * getter of the deck of weapons present in the game.
     * @return the deck of weapons present in the game.
     */
    public Deck getWeapons() {
        return weapons;
    }

    /**
     * setter of the deck of weapons present in the game.
     * @param weapons the deck of weapons to set in the game.
     */
    public void setWeapons(Deck weapons) {
        this.weapons = weapons;
    }

    /**
     * getter of the deck of powerups present in the game.
     * @return the deck of powerups present in the game.
     */
    public Deck getPowerup() {
        return powerups;
    }

    /**
     * setter of the deck of powerups present in the game.
     * @param powerups the deck of powerup to set in the game.
     */
    public void setPowerup(Deck powerups) {
        this.powerups = powerups;
    }

    /**
     * getter of the ammo cards present in the game.
     * @return the ammo cards present in the game.
     */
    public List<AmmoCard> getAmmos() {
        return ammos;
    }

    /**
     * getter of the boolean finalFrenzy.
     * @return the value of the boolean finalFrenzy: true if the game is in the final frenzy phase, false if isn't.
     */
    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * getter of the final standing of the game.
     * @return the final standing of the game.
     */
    public Map<TokenColor, Integer> getScoreList() {
        return scoreList;
    }

    /**
     * getter of the list of players that are playing the game.
     * @return the list of players that are playing the game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * setter of the list of players that are going to play the game.
     * @param players list of players that are going to play the game.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * getter of the killshot track.
     * @return the killshot track of the game.
     */
    public List<Token> getKillshotTrack() {
        return killshotTrack;
    }

    /**
     * getter of the list of colors of the players that are playing the game.
     * @return the list of colors of the players that are playing the game.
     */
    public ArrayList<TokenColor> getPlayerColors() {
        return playerColors;
    }

    /**
     * sets the skulls present in the killshot track, that corresponds to the lenght of the game.
     * @param skulls indicates the number of skulls to set in the killshot track.
     */
    public void createKillshotTrack(int skulls){
        if(!killshotTrack.isEmpty()){
            killshotTrack.clear();
        }
        skullsNumber = skulls;
        for(int i=0; i<skulls; i++){
            killshotTrack.add(new Token(TokenColor.SKULL));
        }
    }

    /**
     * fills the squares of the game board with cards when the game starts.
     * @param actionInterface used to do some restricted actions in other parts of the model.
     */
    public void fillSquares(ActionInterface actionInterface){
        Square[][] arena = board.getArena();
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLUMNS; j++){
                if(!arena[i][j].getColor().equals(TokenColor.NONE)){
                    arena[i][j].fill(actionInterface);
                }
            }
        }
    }

    /**
     * creates the standing of the game.
     */
    public void createScoreList(){
        for(Player player : players){
            scoreList.put(player.getColor(), 0);
            playerColors.add(player.getColor());
        }
    }

    /**
     * adds a player to the game.
     * @param player to add to the game.
     */
    public void addPlayer(Player player){
        players.add(player);
        if(players.size() == Config.MIN_PLAYERS){
            gamePhase = true;
        }
    }

    /***
     * sets the ammo box and the ammo reserve of every player at the beginning of the game.
     */
    public void giveAmmos(){
        for(Player player : players){
            player.addAmmo(new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.YELLOW));
            player.addAmmoToReserve(new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.YELLOW),
                    new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.YELLOW));
        }
    }

    /**
     * update the standing of the players in the game.
     */
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

    /**
     * update the game status at the end of the turn.
     * @param player finishing the turn.
     * @param actionInterface used to do some actions on the game.
     */
    public void endTurn(Player player, ActionInterface actionInterface){
        if(isFinalFrenzy()){
            finalFrenzyTurns--;
            if(finalFrenzyTurns == 0){
                players.forEach(p->p.setMyTurn(false));
                gamePhase = false;
                endPhase = true;
                scoring();
            }
        }
        refillSquares(actionInterface);
        players.forEach(p->p.setDamaged(false));
        for(int i=0; i<players.size(); i++){
            if(players.get(i).equals(player)){
                player.setMyTurn(false);
                player.resetPowerupAmmos();
                player.resetActionNumber();
                do{
                    currentPlayer = nextPlayer(i);
                    Printer.println(currentPlayer.getUsername());
                    i++;
                }while(currentPlayer.isDisconnected());
                currentPlayer.setMyTurn(true);
                break;
            }
        }
    }

    /**
     * refills the squares of the game board with cards at the end of the turn.
     * @param actionInterface used to do some restricted actions in other parts of the model.
     */
    private void refillSquares(ActionInterface actionInterface){
        Square[][] arena = board.getArena();
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLUMNS; j++){
                if(!arena[i][j].getColor().equals(TokenColor.NONE) && arena[i][j].isEmpty()){
                    arena[i][j].fill(actionInterface);
                }
            }
        }
    }

    /**
     * finds the player that has to play in the incoming turn.
     * @param index index of the current player in the players list.
     * @return the next player.
     */
    private Player nextPlayer(int index){
        if(index == players.size() - 1){
            return players.get(0);
        }else{
            return players.get(index + 1);
        }
    }

    /**
     * controls if the current player can move in one or more consecutive directions.
     * @param directions directions of the move to control.
     * @return the result of the control: true if the player can move, false if can't.
     */
    public boolean move(Direction...directions){
         return getBoard().canMove(getCurrentPlayer(), directions);
    }

    /**
     * finds a player in the players list searching his color.
     * @param color indicates the color of the player to search.
     * @return the player with the chosen color.
     */
    public Player findPlayer(TokenColor color){
        for(Player player: players)
            if(player.getColor().equals(color))
                return player;

        return null;
    }

    /**
     * controls if the victim is visible from the current player.
     * @param victim player that is the victim of the control.
     * @return the result of the control: true if the current player can see the victim, false if can't.
     */
    public boolean isVisible(Player victim){
        return getBoard().isVisible(getCurrentPlayer(), victim);
    }

    /**
     * controls if the shooter and the victim are in the same square.
     * @param shooter indicates the shooter.
     * @param victim indicates the victim.
     * @return the result of the control: true if the victim and the shooter are in the same square, false if they aren't.
     */
    public boolean sameSquare(Player shooter, Player victim){
        return (shooter.getPosition().getX() == (victim.getPosition().getX()) && shooter.getPosition().getY() == victim.getPosition().getY());
    }

    /**
     * draws a random powerup from the powerup deck.
     * @return a random powerup.
     */
    public Card drawPowerup(){
        powerups.shuffle();
        return powerups.draw();
    }

    /**
     * sets up the final frenzy mod.
     */
    public void finalFrenzy(){
        finalFrenzyTurns = players.size() + 1;
        players.forEach(p->{
            p.getPlayerBoard().resetDamage();
            p.getPlayerBoard().setDead(false);
            p.getPlayerBoard().setAdrenalineZone(AdrenalineZone.DEFAULT);
        });
    }

    /**
     * ???
     * @param player ???
     */
    public void setKillAndDoubleKill(Player player){
        killshotTrack.get(killshotIndex).setFirstColor(player.getPlayerBoard().getKillshot());
        if(player.getPlayerBoard().isOverkill()){
            killshotTrack.get(killshotIndex).setSecondColor(player.getPlayerBoard().getOverkill());
        }
        killshotIndex++;
        if(killshotIndex == skullsNumber){
            finalFrenzy = true;
            int index = 0;
            for(int i=0; i<players.size(); i++){
                if(players.get(i).equals(currentPlayer)){
                    index = i;
                }
            }
            for(int i=0; i<players.size(); i++){
                if(i <= index){
                    players.get(i).setFinalFrenzyActionsNumber(1);
                    players.get(i).setFinalFrenzyActions(FinalFrenzyAction.ONE_ACTION);
                }else{
                    players.get(i).setFinalFrenzyActionsNumber(2);
                    players.get(i).setFinalFrenzyActions(FinalFrenzyAction.TWO_ACTIONS);
                }
            }
        }
    }

    /**
     * ???
     */
    void setRevengeMarks(){
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

    public boolean isLoginPhase() {
        return loginPhase;
    }
    public void setLoginPhase(boolean loginPhase) {
        this.loginPhase = loginPhase;
    }
    public void setAmmos(List<AmmoCard> ammos) {
        this.ammos = ammos;
    }
    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }
    public synchronized void addPlayerColors(TokenColor color){
        playerColors.add(color);
    }
    public synchronized boolean containsColor(TokenColor color){
        for(TokenColor tokenColor : playerColors){
            if(color.equals(tokenColor)){
                return true;
            }
        }
        return false;
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

}
