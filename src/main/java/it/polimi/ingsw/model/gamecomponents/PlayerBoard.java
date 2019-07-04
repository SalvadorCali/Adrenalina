package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Printer;

import java.io.Serializable;
import java.util.*;

/**
 * Class which represents the personal information regarding damages and marks of every player in the game.
 */
public class PlayerBoard implements Serializable {
    /**
     * Array of damages from other players.
     */
    private Token[] damageBoard;
    /**
     * Max number of damages on the player board.
     */
    private static final int MAX_DAMAGE = 12;
    /**
     * Max damage.
     */
    private int maxDamage;
    /**
     * Number of damages on the player board.
     */
    private int damageIndex;
    /**
     * Number of deaths
     */
    private int deathNumber;
    /**
     * List of revenge marks present on the player board.
     */
    private List<Token> revengeMarks;
    /**
     * Adrenaline zone.
     */
    private AdrenalineZone adrenalineZone;
    /**
     * Boolean which is true when the player is dead.
     */
    private boolean dead;
    /**
     * Boolean which is ture when the player has been overkilled.
     */
    private boolean overkill;
    /**
     * Boolean which is true when the player is in final frenzy.
     */
    private boolean finalFrenzy;
    /**
     * Standings.
     */
    private Map<TokenColor, Score> scoreList;

    /**
     * Constructor of the playerBoard class.
     */
    public PlayerBoard(){
        damageBoard = new Token[MAX_DAMAGE];
        for(int i=0; i<MAX_DAMAGE; i++){
            damageBoard[i] = new Token(TokenColor.NONE);
        }
        damageIndex = 0;
        deathNumber = 0;
        revengeMarks = new ArrayList<>();
        adrenalineZone = AdrenalineZone.DEFAULT;
        scoreList = new HashMap<>();
    }

    /**
     * Getter of the player's damageboard.
     * @return the player's damageboard.
     */
    public Token[] getDamageBoard() {
        return damageBoard;
    }

    /**
     * Getter of the int damageIndex.
     * @return the value of the int damageIndex.
     */
    int getDamageIndex() {
        return damageIndex;
    }

    /**
     * Getter of the boolean finalFrenzy.
     * @return the value of the boolean finalFrenzy.
     */
    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * Setter of the finalFrenzy.
     * @param finalFrenzy chosen value.
     */
    void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * Getter of the list of revenge marks on the player's damage board.
     * @return the list of revenge marks on the player's damage board.
     */
    public List<Token> getRevengeMarks() {
        return revengeMarks;
    }

    /**
     * Getter of the number of deaths collected by the player.
     * @return the number of deaths collected by the player.
     */
    public int getDeathNumber() {
        return deathNumber;
    }

    /**
     * Set the number of deaths collected by the player.
     * @param deathNumber the chosen value to set.
     */
    public void setDeathNumber(int deathNumber) {
        this.deathNumber = deathNumber;
    }

    /**
     * Getter of the adrenalineZone.
     * @return the adrenalineZone.
     */
    public AdrenalineZone getAdrenalineZone(){
        return adrenalineZone;
    }

    /**
     * Setter of the adrenalineZone.
     * @param adrenalineZone chosen value to set the adrenalineZone.
     */
    void setAdrenalineZone(AdrenalineZone adrenalineZone) {
        this.adrenalineZone = adrenalineZone;
    }

    /**
     * Getter of the boolean dead.
     * @return the value of the boolean dead: true if the player is dead, false if he's alive.
     */
    boolean isDead() {
        return dead;
    }

    /**
     * Setter of the boolean dead.
     * @param dead chosen value to set the boolean dead.
     */
    void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Getter of the boolean overkill.
     * @return the value of the boolean overkill.
     */
    boolean isOverkill() {
        return overkill;
    }

    /**
     * Adds a revenge mark to the player's list of revenge marks.
     * @param color indicates the color of the player who inflicted the mark.
     */
    public void addRevengeMarks(TokenColor color){
        revengeMarks.add(new Token(color));
    }

    /**
     * Reset the mark on the damage board to zero.
     */
    void resetRevengeMarks(){
        revengeMarks.clear();
    }

    /**
     * Adds a damage to the player's array of damages.
     * @param colors indicates the color of the player who inflicted the damage.
     */
    public void addDamage(TokenColor...colors){
        for(TokenColor color : colors){
            damageBoard[damageIndex] = new Token(color);
            damageIndex++;
            if(!revengeMarks.isEmpty()){
                damageIndex = findRevengeMarks(color, damageIndex);
            }
            if(damageIndex >= 2 && damageIndex < 5){
                adrenalineZone = AdrenalineZone.FIRST;
            }
            else if(damageIndex >= 5){
                adrenalineZone = AdrenalineZone.SECOND;
            }
            if(damageIndex == 11){
                maxDamage = 11;
                dead = true;
                deathNumber++;
            }
            if(damageIndex == 12){
                maxDamage = 12;
                overkill = true;
            }
        }
    }

    /**
     * Finds if the player has marks of a certain color, and adds the same number of damages to the player's damageboard.
     * @param color color of the player who inflicted the damage.
     * @param damageIndex indicates the position where to put the new damages.
     * @return the damage index.
     */
    private int findRevengeMarks(TokenColor color, int damageIndex){
        int revengeMarksSize = revengeMarks.size();
        for(int i=0; i<revengeMarks.size(); i++){
            if(revengeMarks.get(i).getFirstColor().equals(color)){
                damageBoard[damageIndex] = new Token(color);
                damageIndex++;
                revengeMarks.remove(i);
                i--;
            }
        }
        return damageIndex;
    }

    /**
     * Resets the damages present in the damage board to zero.
     */
    void resetDamage(){
        for(int i=0; i<MAX_DAMAGE; i++){
            damageBoard[i] = new Token(TokenColor.NONE);
        }
        damageIndex = 0;
    }

    /**
     * Getter of the color of the token present in the first position of the damage board.
     * @return the color of the first token in the damage board.
     */
    TokenColor getFirstBlood(){
        return damageBoard[0].getFirstColor();
    }

    /**
     * Getter of the color of the token present in the tenth position of the damage board.
     * @return the color of the tenth token in the damage board.
     */
    TokenColor getKillshot(){
        return damageBoard[10].getFirstColor();
    }

    /**
     * Getter of the color of the token present in the last position of the damage board.
     * @return the color of the last token in the damage board.
     */
    TokenColor getOverkill(){
        return damageBoard[11].getFirstColor();
    }

    /**
     * Creates the standing of the players at the end of the game.
     * @param playerColors indicates the list of the colors of the players that played the game.
     * @return a score list representing the final standing.
     */
    private Map<TokenColor, Score> createScoreList(ArrayList<TokenColor> playerColors){
        Score score;
        for(TokenColor color : playerColors){
            scoreList.put(color, new Score(color, 0));
        }
        for(int i=0; i<damageIndex; i++){
            score = scoreList.get(damageBoard[i].getFirstColor());
            score.setScore(score.getScore() + 1);
            scoreList.replace(damageBoard[i].getFirstColor(), score);
        }
        return scoreList;
    }

    /**
     * Returns the correct list of numbers used for the scoring.
     * @return a list of numbers.
     */
    private ArrayList<Integer> getScoreValues(){
        ArrayList<Integer> scoreValues;
        Integer[] numbers;
        if(finalFrenzy){
            numbers = new Integer[]{2,1,1,1};
            scoreValues = new ArrayList<>(Arrays.asList(numbers));
        }else{
            if(deathNumber == 1 || deathNumber == 0){
                numbers = new Integer[]{8,6,4,2};
                scoreValues = new ArrayList<>(Arrays.asList(numbers));
            }else if(deathNumber == 2){
                numbers = new Integer[]{6,4,2,1};
                scoreValues = new ArrayList<>(Arrays.asList(numbers));
            }else if(deathNumber == 3){
                numbers = new Integer[]{4,2,1,1};
                scoreValues = new ArrayList<>(Arrays.asList(numbers));
            }else if(deathNumber == 4){
                numbers = new Integer[]{2,1,1,1};
                scoreValues = new ArrayList<>(Arrays.asList(numbers));
            }else {
                numbers = new Integer[]{1,1,1,1};
                scoreValues = new ArrayList<>(Arrays.asList(numbers));
            }
        }
        return scoreValues;
    }

    /**
     * Gives to each user the correct score.
     * @param playerColors
     * @return
     */
    Map<TokenColor, Score> scoring(ArrayList<TokenColor> playerColors){
        Score score;
        List<Score> turnScores = new ArrayList<>();
        scoreList = createScoreList(playerColors);
        scoreList.forEach((key, value) -> turnScores.add(value));

        int scoreValuesIndex = 0;
        ArrayList<Integer> scoreValues = getScoreValues();
        int number = turnScores.size();
        for(int k=0; k<number; k++){
            int max = 0;
            for (Score turnScore : turnScores) {
                max = Math.max(max, turnScore.getScore());
            }
            if(max != 0){
                ArrayList<Score> maximumScores = new ArrayList<>();
                for (Score turnScore : turnScores) {
                    if (max == turnScore.getScore()) {
                        maximumScores.add(turnScore);
                    }
                }
                if(maximumScores.size() > 1){
                    evaluate_max:
                    for(int i=0; i<maxDamage; i++){
                        for(int j=0; j<maximumScores.size(); j++){
                            if(damageBoard[i].getFirstColor().equals(maximumScores.get(j).getColor())){
                                scoreList.replace(damageBoard[i].getFirstColor(), new Score(damageBoard[i].getFirstColor(), scoreValues.get(scoreValuesIndex)));
                                scoreValuesIndex++;
                                for(int l=0; l<turnScores.size(); l++){
                                    if(maximumScores.get(j).equals(turnScores.get(l))){
                                        turnScores.remove(l);
                                        break;
                                    }
                                }
                                maximumScores.remove(j);
                                break evaluate_max;
                            }
                        }
                    }
                }else{
                    scoreList.replace(maximumScores.get(0).getColor(), new Score(maximumScores.get(0).getColor(), scoreValues.get(scoreValuesIndex)));
                    scoreValuesIndex++;
                    for(int l=0; l<turnScores.size(); l++){
                        if(maximumScores.get(0).equals(turnScores.get(l))){
                            turnScores.remove(l);
                            break;
                        }
                    }
                    maximumScores.remove(0);
                }
            }
        }
        if(!finalFrenzy){
            score = scoreList.get(getFirstBlood());
            score.setScore(score.getScore() + 1);
            scoreList.replace(getFirstBlood(), score);
        }
        return scoreList;
    }

    /**
     * Finds
     * @param playerColors
     * @return
     */
    Map<TokenColor, Score> scoringFinalFrenzy(ArrayList<TokenColor> playerColors){
        Score score;
        List<Score> turnScores = new ArrayList<>();
        scoreList = createScoreList(playerColors);
        scoreList.forEach((k, j)-> Printer.println(k + " " + j.getScore()));
        scoreList.forEach((key, value) -> turnScores.add(value));

        int scoreValuesIndex = 0;
        ArrayList<Integer> scoreValues = getScoreValues();
        int number = turnScores.size();
        for(int k=0; k<number; k++){
            int max = 0;
            for (Score turnScore : turnScores) {
                max = Math.max(max, turnScore.getScore());
            }
            if(max != 0){
                ArrayList<Score> maximumScores = new ArrayList<>();
                for (Score turnScore : turnScores) {
                    if (max == turnScore.getScore()) {
                        maximumScores.add(turnScore);
                    }
                }
                if(maximumScores.size() > 1){
                    evaluate_max:
                    for(int i=0; i<damageIndex; i++){
                        for(int j=0; j<maximumScores.size(); j++){
                            if(damageBoard[i].getFirstColor().equals(maximumScores.get(j).getColor())){
                                scoreList.replace(damageBoard[i].getFirstColor(), new Score(damageBoard[i].getFirstColor(), scoreValues.get(scoreValuesIndex)));
                                scoreValuesIndex++;
                                for(int l=0; l<turnScores.size(); l++){
                                    if(maximumScores.get(j).equals(turnScores.get(l))){
                                        turnScores.remove(l);
                                        break;
                                    }
                                }
                                maximumScores.remove(j);
                                break evaluate_max;
                            }
                        }
                    }
                }else{
                    scoreList.replace(maximumScores.get(0).getColor(), new Score(maximumScores.get(0).getColor(), scoreValues.get(scoreValuesIndex)));
                    scoreValuesIndex++;
                    for(int l=0; l<turnScores.size(); l++){
                        if(maximumScores.get(0).equals(turnScores.get(l))){
                            turnScores.remove(l);
                            break;
                        }
                    }
                    maximumScores.remove(0);
                }
            }
        }
        return scoreList;
    }

    /**
     * Resets the damages on the board.
     */
    public void resetBoard(){
        if(!getOverkill().equals(TokenColor.NONE)){
            addRevengeMarks(getOverkill());
        }
        resetDamage();
        adrenalineZone = AdrenalineZone.DEFAULT;
        dead = false;
    }
}
