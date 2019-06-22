package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.*;

/**
 * class which represents the personal information regarding damages and marks of every player in the game.
 */
public class PlayerBoard implements Serializable {
    private Token[] damageBoard;
    private static final int MAX_DAMAGE = 12;
    private int maxDamage;
    private int damageIndex;
    private int deathNumber;
    private List<Token> revengeMarks;
    private AdrenalineZone adrenalineZone;
    private boolean dead;
    private boolean overkill;
    private boolean finalFrenzy;
    private Map<TokenColor, Score> scoreList;

    /**
     * constructor of the playerBoard class.
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
     * getter of the player's damageboard.
     * @return the player's damageboard.
     */
    public Token[] getDamageBoard() {
        return damageBoard;
    }

    /**
     * getter of the int damageIndex.
     * @return the value of the int damageIndex.
     */
    int getDamageIndex() {
        return damageIndex;
    }

    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    void setFinalFrenzy(boolean finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * getter of the list of revenge marks on the player's damage board.
     * @return the list of revenge marks on the player's damage board.
     */
    public List<Token> getRevengeMarks() {
        return revengeMarks;
    }

    /**
     * getter of the number of deaths collected by the player.
     * @return the number of deaths collected by the player.
     */
    public int getDeathNumber() {
        return deathNumber;
    }

    /**
     * set the number of deaths collected by the player.
     * @param deathNumber the chosen value to set.
     */
    public void setDeathNumber(int deathNumber) {
        this.deathNumber = deathNumber;
    }

    /**
     * getter of the adrenalineZone.
     * @return the adrenalineZone.
     */
    public AdrenalineZone getAdrenalineZone(){
        return adrenalineZone;
    }

    /**
     * setter of the adrenalineZone.
     * @param adrenalineZone chosen value to set the adrenalineZone.
     */
    void setAdrenalineZone(AdrenalineZone adrenalineZone) {
        this.adrenalineZone = adrenalineZone;
    }

    /**
     * getter of the boolean dead.
     * @return the value of the boolean dead: true if the player is dead, false if he's alive.
     */
    boolean isDead() {
        return dead;
    }

    /**
     * setter of the boolean dead.
     * @param dead chosen value to set the boolean dead.
     */
    void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * getter of the boolean overkill.
     * @return the value of the boolean overkill.
     */
    boolean isOverkill() {
        return overkill;
    }

    /**
     * adds a revenge mark to the player's list of revenge marks.
     * @param color indicates the color of the player who inflicted the mark.
     */
    public void addRevengeMarks(TokenColor color){
        revengeMarks.add(new Token(color));
    }

    /**
     * reset the mark on the damage board to zero.
     */
    void resetRevengeMarks(){
        revengeMarks.clear();
    }

    /**
     * adds a damage to the player's array of damages.
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
     * finds if the player has marks of a certain color, and adds the same number of damages to the player's damageboard.
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
     * resets the damages present in the damage board to zero.
     */
    void resetDamage(){
        for(int i=0; i<MAX_DAMAGE; i++){
            damageBoard[i] = new Token(TokenColor.NONE);
        }
        damageIndex = 0;
    }

    /**
     * getter of the color of the token present in the first position of the damage board.
     * @return the color of the first token in the damage board.
     */
    TokenColor getFirstBlood(){
        return damageBoard[0].getFirstColor();
    }

    /**
     * getter of the color of the token present in the tenth position of the damage board.
     * @return the color of the tenth token in the damage board.
     */
    TokenColor getKillshot(){
        return damageBoard[10].getFirstColor();
    }

    /**
     * getter of the color of the token present in the last position of the damage board.
     * @return the color of the last token in the damage board.
     */
    TokenColor getOverkill(){
        return damageBoard[11].getFirstColor();
    }

    /**
     * creates the standing of the players at the end of the game.
     * @param playerColors indicates the list of the colors of the players that played the game.
     * @return a score list representing the final standing.
     */
    private Map<TokenColor, Score> createScoreList(ArrayList<TokenColor> playerColors){
        Score score;
        for(TokenColor color : playerColors){
            scoreList.put(color, new Score(color, 0));
        }
        for(int i=0; i<maxDamage; i++){
            score = scoreList.get(damageBoard[i].getFirstColor());
            score.setScore(score.getScore() + 1);
            scoreList.replace(damageBoard[i].getFirstColor(), score);
        }
        return scoreList;
    }

    /**
     * ???
     * @return ???
     */
    private ArrayList<Integer> getScoreValues(){
        ArrayList<Integer> scoreValues;
        Integer[] numbers;
        if(finalFrenzy){
            numbers = new Integer[]{2,1,1,1};
            scoreValues = new ArrayList<>(Arrays.asList(numbers));
        }else{
            if(deathNumber == 1){
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
     * ???
     * @param playerColors ???
     * @return ???
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

        score = scoreList.get(getFirstBlood());
        score.setScore(score.getScore() + 1);
        scoreList.replace(getFirstBlood(), score);
        /*
        score = scoreList.get(getKillshot());
        score.setScore(score.getScore() + 1);
        scoreList.replace(getKillshot(), score);
        */
        return scoreList;
    }
    /**
     * ???
     */
    public void resetBoard(){
        if(!getOverkill().equals(TokenColor.NONE)){
            addRevengeMarks(getOverkill());
        }
        resetDamage();
        adrenalineZone = AdrenalineZone.DEFAULT;
        dead = false;
    }
    public void setDamageBoard(Token[] damageBoard) {
        this.damageBoard = damageBoard;
    }
    public void setDamageIndex(int damageIndex) {
        this.damageIndex = damageIndex;
    }
    public void setRevengeMarks(List<Token> revengeMarks) {
        this.revengeMarks = revengeMarks;
    }
    public void setOverkill(boolean overkill) {
        this.overkill = overkill;
    }
    public Map<TokenColor, Score> getScoreList() {
        return scoreList;
    }
    public void setScoreList(Map<TokenColor, Score> scoreList) {
        this.scoreList = scoreList;
    }
}
