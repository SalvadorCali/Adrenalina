package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Printer;

import java.util.*;

public class PlayerBoard {
    private Token[] damageBoard;
    private static final int MAX_DAMAGE = 12;
    private int maxDamage;
    private int damageIndex;
    private int deathNumber;
    private List<Token> revengeMarks;
    private AdrenalineZone adrenalineZone;
    private boolean dead;
    private boolean overkill;
    private Map<TokenColor, Score> scoreList;

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

    //getters and setters
    public Token[] getDamageBoard() {
        return damageBoard;
    }

    public void setDamageBoard(Token[] damageBoard) {
        this.damageBoard = damageBoard;
    }

    public int getDamageIndex() {
        return damageIndex;
    }

    public void setDamageIndex(int damageIndex) {
        this.damageIndex = damageIndex;
    }

    public List<Token> getRevengeMarks() {
        return revengeMarks;
    }

    public void setRevengeMarks(List<Token> revengeMarks) {
        this.revengeMarks = revengeMarks;
    }

    public int getDeathNumber() {
        return deathNumber;
    }

    public void setDeathNumber(int deathNumber) {
        this.deathNumber = deathNumber;
    }

    public AdrenalineZone getAdrenalineZone(){
        return adrenalineZone;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isOverkill() {
        return overkill;
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

    //methods
    public void addRevengeMarks(TokenColor color){
        revengeMarks.add(new Token(color));
    }

    public void resetRevengeMarks(){
        revengeMarks.clear();
    }

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
            }
            if(damageIndex == 12){
                maxDamage = 12;
                overkill = true;
            }
        }
    }

    public int findRevengeMarks(TokenColor color, int damageIndex){
        int revengeMarksSize = revengeMarks.size();
        for(int i=0; i<revengeMarksSize; i++){
            if(revengeMarks.get(i).getFirstColor().equals(color)){
                damageBoard[damageIndex] = new Token(color);
                damageIndex++;
                revengeMarks.remove(i);
                i--;
            }
        }
        return damageIndex;
    }



    public void resetDamage(){
        for(int i=0; i<MAX_DAMAGE; i++){
            damageBoard[i] = new Token(TokenColor.NONE);
        }
        damageIndex = 0;
    }

    public TokenColor getFirstBlood(){
        return damageBoard[0].getFirstColor();
    }

    public TokenColor getKillshot(){
        return damageBoard[10].getFirstColor();
    }

    public TokenColor getOverkill(){
        return damageBoard[11].getFirstColor();
    }

    public void resetBoard(){
        if(!getOverkill().equals(TokenColor.NONE)){
            addRevengeMarks(getOverkill());
        }
        resetDamage();
        adrenalineZone = AdrenalineZone.DEFAULT;
        dead = false;
    }

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

    public Map<TokenColor, Score> scoring(ArrayList<TokenColor> playerColors){
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
        score = scoreList.get(getKillshot());
        score.setScore(score.getScore() + 1);
        scoreList.replace(getKillshot(), score);
        return scoreList;
    }

    private ArrayList<Integer> getScoreValues(){
        ArrayList<Integer> scoreValues;
        Integer[] numbers;
        if(deathNumber == 0){
            numbers = new Integer[]{8,6,4,2};
            scoreValues = new ArrayList<>(Arrays.asList(numbers));
        }else if(deathNumber == 1){
            numbers = new Integer[]{6,4,2,1};
            scoreValues = new ArrayList<>(Arrays.asList(numbers));
        }else if(deathNumber == 2){
            numbers = new Integer[]{4,2,1,1};
            scoreValues = new ArrayList<>(Arrays.asList(numbers));
        }else if(deathNumber == 3){
            numbers = new Integer[]{2,1,1,1};
            scoreValues = new ArrayList<>(Arrays.asList(numbers));
        }else {
            numbers = new Integer[]{1,1,1,1};
            scoreValues = new ArrayList<>(Arrays.asList(numbers));
        }
        return scoreValues;
    }
}
