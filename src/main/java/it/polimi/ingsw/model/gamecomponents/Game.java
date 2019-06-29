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

    /**
     * Number of rows in the board.
     */
    private static final int  ROWS = 3;
    /**
     * Number of columns in the board.
     */
    private static final int COLUMNS = 4;
    /**
     * The board of the game.
     */
    private GameBoard board;
    /**
     * The player who's playing the turn.
     */
    private Player currentPlayer;
    /**
     * List of players who are joining the game.
     */
    private List<Player> players;
    /**
     * List of the players' colors.
     */
    private ArrayList<TokenColor> playerColors;
    /**
     * Maps of players and relative colors.
     */
    private Map<String, TokenColor> playersMap;
    /**
     * List of skulls that determines the lenght of the game.
     */
    private List<Token> killshotTrack;
    /**
     * Index of the killshot track.
     */
    private int killshotIndex;
    /**
     * Number of skulls chosen for the game.
     */
    private int skullsNumber;
    /**
     * Deck of weapons
     */
    private Deck weapons;
    /**
     * Deck of powerups.
     */
    private Deck powerups;
    /**
     * List of ammo cards in the game.
     */
    private List<AmmoCard> ammos;
    /**
     * Turns of final frenzy.
     */
    private int finalFrenzyTurns;
    /**
     * Boolean which is true if the game is in the final frenzy stage.
     */
    private boolean finalFrenzy;
    /**
     * Boolean which is true if the game is in game phase.
     */
    private boolean gamePhase;
    /**
     * Boolean which is true if the game is in the color selection phase.
     */
    private boolean colorSelection;
    /**
     * Boolean which is true if the game is in the login phase.
     */
    private boolean loginPhase;
    /**
     * Boolean which is true if the game is in the spawn location phase.
     */
    private boolean spawnLocationPhase;
    /**
     * Boolean which is true if the game is in the board type phase.
     */
    private boolean boardTypePhase;
    /**
     * Boolean which is true if the game is in the respawn phase.
     */
    private boolean respawnPhase;
    /**
     * Boolean which is true if the game is in the end phase.
     */
    private boolean endPhase;
    private Map<TokenColor, Integer> scoreList;

    /**
     * Constructor of the Game class.
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
     * Getter of the boolean respawnPhase.
     * @return the value of the boolean respawnPhase.
     */
    public boolean isRespawnPhase() {
        return respawnPhase;
    }

    /**
     * Setter of the boolean respawnPhase.
     * @param respawnPhase chosen value of the boolean respawnPhase.
     */
    public void setRespawnPhase(boolean respawnPhase) {
        this.respawnPhase = respawnPhase;
    }

    /**
     * Getter of the boolean gamePhase.
     * @return the value of the boolean gamePhase.
     */
    public boolean isGamePhase() {
        return gamePhase;
    }

    /**
     * Setter of the boolean gamePhase.
     * @param gamePhase chosen value of the boolean gamePhase.
     */
    public void setGamePhase(boolean gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Getter of the boolean spawnLocationPhase.
     * @return the value of the boolean spawnLocationPhase.
     */
    public boolean isSpawnLocationPhase() {
        return spawnLocationPhase;
    }

    /**
     * Setter of the boolean spawnLocationPhase.
     * @param spawnLocationPhase chosen value of the boolean spawnLocationPhase.
     */
    public void setSpawnLocationPhase(boolean spawnLocationPhase) {
        this.spawnLocationPhase = spawnLocationPhase;
    }

    /**
     * Getter of the boolean boardTypePhase.
     * @return the value of the boolean boardTypePhase.
     */
    public boolean isBoardTypePhase() {
        return boardTypePhase;
    }

    /**
     * Setter of the boolean boardTypePhase.
     * @param boardTypePhase chosen value of the boolean boardTypePhase.
     */
    public void setBoardTypePhase(boolean boardTypePhase) {
        this.boardTypePhase = boardTypePhase;
    }

    /**
     * Getter of the boolean colorSelection.
     * @return the value of the boolean colorSelection.
     */
    boolean isColorSelection() {
        return colorSelection;
    }

    /**
     * Setter of the boolean colorSelection.
     * @param colorSelection chosen value for the boolean colorSelection.
     */
    void setColorSelection(boolean colorSelection) {
        this.colorSelection = colorSelection;
    }

    /**
     * Getter of the board of the game.
     * @return the board of the game.
     */
    public GameBoard getBoard() {
        return board;
    }

    /**
     * Setter of the board of the game.
     * @param board chosen board to set as the board of the game.
     */
    public void setBoard(GameBoard board) {
        this.board = board;
    }

    /**
     * Getter of the player that is currently playing.
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter of the player that is currently playing.
     * @param currentPlayer chosen player to set as current player.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter of the deck of weapons present in the game.
     * @return the deck of weapons present in the game.
     */
    public Deck getWeapons() {
        return weapons;
    }

    /**
     * Setter of the deck of weapons present in the game.
     * @param weapons the deck of weapons to set in the game.
     */
    public void setWeapons(Deck weapons) {
        this.weapons = weapons;
    }

    /**
     * Getter of the deck of powerups present in the game.
     * @return the deck of powerups present in the game.
     */
    public Deck getPowerup() {
        return powerups;
    }

    /**
     * Setter of the deck of powerups present in the game.
     * @param powerups the deck of powerup to set in the game.
     */
    public void setPowerup(Deck powerups) {
        this.powerups = powerups;
    }

    /**
     * Getter of the ammo cards present in the game.
     * @return the ammo cards present in the game.
     */
    public List<AmmoCard> getAmmos() {
        return ammos;
    }

    /**
     * Getter of the boolean finalFrenzy.
     * @return the value of the boolean finalFrenzy: true if the game is in the final frenzy phase, false if isn't.
     */
    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * Getter of the final standing of the game.
     * @return the final standing of the game.
     */
    public Map<TokenColor, Integer> getScoreList() {
        return scoreList;
    }

    /**
     * Getter of the list of players that are playing the game.
     * @return the list of players that are playing the game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Setter of the list of players that are going to play the game.
     * @param players list of players that are going to play the game.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Getter of the killshot track.
     * @return the killshot track of the game.
     */
    public List<Token> getKillshotTrack() {
        return killshotTrack;
    }

    /**
     * Getter of the list of colors of the players that are playing the game.
     * @return the list of colors of the players that are playing the game.
     */
    ArrayList<TokenColor> getPlayerColors() {
        return playerColors;
    }

    /**
     * Sets the skulls present in the killshot track, that corresponds to the lenght of the game.
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
     * Fills the squares of the game board with cards when the game starts.
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
     * Creates the standing of the game.
     */
    public void createScoreList(){
        for(Player player : players){
            scoreList.put(player.getColor(), 0);
            playerColors.add(player.getColor());
        }
    }

    /**
     * Adds a player to the game.
     * @param player to add to the game.
     */
    void addPlayer(Player player){
        players.add(player);
        if(players.size() == Config.MIN_PLAYERS){
            gamePhase = true;
        }
    }

    /**
     * Sets the ammo box and the ammo reserve of every player at the beginning of the game.
     */
    public void giveAmmos(){
        for(Player player : players){
            player.addAmmo(new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.YELLOW));
            player.addAmmoToReserve(new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.YELLOW),
                    new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.YELLOW));
        }
    }

    /**
     * Update the standing of the players in the game.
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
        doubleKill();
    }

    /**
     * Update the game status at the end of the turn.
     * @param player finishing the turn.
     * @param actionInterface used to do some actions on the game.
     */
    public void endTurn(Player player, ActionInterface actionInterface){
        refillSquares(actionInterface);
        players.forEach(p->p.setDamaged(false));
        for(int i=0; i<players.size(); i++){
            if(players.get(i).equals(player)){
                player.setMyTurn(false);
                player.resetPowerupAmmos();
                player.resetActionNumber();
                int index = i;
                do{
                    index = nextPlayer(index);
                    currentPlayer = players.get(index);
                }while(currentPlayer.isDisconnected());
                currentPlayer.setMyTurn(true);
                break;
            }
        }
    }

    /**
     * Refills the squares of the game board with cards at the end of the turn.
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
     * Finds the index of the player that has to play in the incoming turn.
     * @param index index of the current player in the players list.
     * @return the index of the next player.
     */
    private int nextPlayer(int index){
        if(index == players.size() - 1){
            return 0;
        }else{
            return (index + 1);
        }
    }

    /**
     * Controls if the current player can move in one or more consecutive directions.
     * @param directions directions of the move to control.
     * @return the result of the control: true if the player can move, false if can't.
     */
    public boolean move(Direction...directions){
         return getBoard().canMove(getCurrentPlayer(), directions);
    }

    /**
     * Finds a player in the players list searching his color.
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
     * Controls if the victim is visible from the current player.
     * @param victim player that is the victim of the control.
     * @return the result of the control: true if the current player can see the victim, false if can't.
     */
    public boolean isVisible(Player victim){
        return getBoard().isVisible(getCurrentPlayer(), victim);
    }

    /**
     * Controls if the shooter and the victim are in the same square.
     * @param shooter indicates the shooter.
     * @param victim indicates the victim.
     * @return the result of the control: true if the victim and the shooter are in the same square, false if they aren't.
     */
    public boolean sameSquare(Player shooter, Player victim){
        return (shooter.getPosition().getX() == (victim.getPosition().getX()) && shooter.getPosition().getY() == victim.getPosition().getY());
    }

    /**
     * Draws a random powerup from the powerup deck.
     * @return a random powerup.
     */
    public Card drawPowerup(){
        powerups.shuffle();
        return powerups.draw();
    }

    /**
     * Sets up the final frenzy mod.
     */
    public void finalFrenzy(){
        finalFrenzyTurns = players.size() + 1;
        players.forEach(p->{
            if(p.getPlayerBoard().getDamageIndex()==0){
                p.getPlayerBoard().setFinalFrenzy(true);
            }
            //p.getPlayerBoard().resetDamage();
            p.getPlayerBoard().setDead(false);
            p.getPlayerBoard().setAdrenalineZone(AdrenalineZone.DEFAULT);
        });
    }

    /**
     * Substitutes the skull on the killshot track with the token of the shooter after a death. Then, if the killshot is the last of the game, calls the {@link #finalFrenzySetup()} method.
     * @param player the player that is dead.
     */
    public void setKillAndDoubleKill(Player player){
        killshotTrack.get(killshotIndex).setFirstColor(player.getPlayerBoard().getKillshot());
        if(player.getPlayerBoard().isOverkill()){
            killshotTrack.get(killshotIndex).setSecondColor(player.getPlayerBoard().getOverkill());
        }
        killshotIndex++;
        if(killshotIndex == skullsNumber){
            finalFrenzySetup();
        }
    }

    /**
     * Sets the final frenzy.
     */
    private void finalFrenzySetup(){
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

    /**
     * Adds the revenge marks after a player's death.
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

    /**
     * Getter for the loginPhase value.
     * @return true if the game is in its login phase.
     */
    public boolean isLoginPhase() {
        return loginPhase;
    }

    /**
     * Setter for the loginPhase value.
     * @param loginPhase the value that will be set.
     */
    public void setLoginPhase(boolean loginPhase) {
        this.loginPhase = loginPhase;
    }

    /**
     * Setter for final frenzy.
     * @param finalFrenzy the value that will be set.
     */
    public void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * Adds an additional point to the player who realized two or more killshots in the same turn.
     */
    void doubleKill(){
        Map<TokenColor, Integer> killshots = new HashMap<>();
        for(Player player : players){
            if(player.getPlayerBoard().isDead()){
                TokenColor tokenColor = player.getPlayerBoard().getKillshot();
                if(killshots.containsKey(tokenColor)){
                    int kills = killshots.get(tokenColor) + 1;
                    killshots.replace(tokenColor, kills);
                }else{
                    killshots.put(tokenColor, 1);
                }
            }
        }
        killshots.forEach((c,i)->{
            if(i>1){
                int actualScore = scoreList.get(c);
                actualScore += 1;
                scoreList.replace(c, actualScore);
            }
        });
    }

    /**
     * Ends the game and prints the score.
     */
    public void endGame(){
        players.forEach(p->p.setMyTurn(false));
        gamePhase = false;
        endPhase = true;
        scoring();
        Printer.println(scoreList);
    }

}
