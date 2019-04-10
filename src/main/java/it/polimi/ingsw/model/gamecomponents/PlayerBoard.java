package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Printer;

import java.util.*;

public class PlayerBoard {
    private Token[] damageBoard;
    private static final int MAX_DAMAGE = 12;
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
        scoreList = new EnumMap<>(TokenColor.class);
        createScoreList();
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
    private void createScoreList(){
        for(TokenColor color : TokenColor.values()){
            scoreList.put(color, new Score(color, 0));
        }
    }
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
            if(damageIndex == 2){
                adrenalineZone = AdrenalineZone.FIRST;
            }
            else if(damageIndex == 5){
                adrenalineZone = AdrenalineZone.SECOND;
            }
            else if(damageIndex == 11){
                dead = true;
            }
            else if(damageIndex == 12){
                overkill = true;
            }
        }
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

    public Map<TokenColor, Score> scoring(ArrayList<TokenColor> playerColors){
        Score score;
        int maxDamage;
        if(isOverkill()){
            maxDamage = 12;
        }else{
            maxDamage = 11;
        }
        scoreList = new HashMap<>();
        for(TokenColor color : playerColors){
            scoreList.put(color, new Score(color, 0));
        }
        for(int i=0; i<maxDamage; i++){
            score = scoreList.get(damageBoard[i].getFirstColor());
            score.setScore(score.getScore() + 1);
            scoreList.replace(damageBoard[i].getFirstColor(), score);
        }
        List<Score> scores = new ArrayList<>();
        scoreList.forEach((key, value) -> scores.add(value));

        int scoreValuesIndex = 0;
        ArrayList<Integer> scoreValues = getScoreValues();
        int number = scores.size();
        for(int k=0; k<number; k++){
            int max = 0;
            for(int i=0; i<scores.size(); i++){
                max = Math.max(max, scores.get(i).getScore());
            }
            if(max != 0){
                ArrayList<Score> maximumScores = new ArrayList<>();
                for(int i=0; i<scores.size(); i++){
                    if(max == scores.get(i).getScore()){
                        maximumScores.add(scores.get(i));
                    }
                }
                if(maximumScores.size() > 1){
                    evaluate_max:
                    for(int i=0; i<maxDamage; i++){
                        for(int j=0; j<maximumScores.size(); j++){
                            if(damageBoard[i].getFirstColor().equals(maximumScores.get(j).getColor())){
                                scoreList.replace(damageBoard[i].getFirstColor(), new Score(damageBoard[i].getFirstColor(), scoreValues.get(scoreValuesIndex)));
                                scoreValuesIndex++;
                                for(int l=0; l<scores.size(); l++){
                                    if(maximumScores.get(j).equals(scores.get(l))){
                                        scores.remove(l);
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
                    for(int l=0; l<scores.size(); l++){
                        if(maximumScores.get(0).equals(scores.get(l))){
                            scores.remove(l);
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

    public void givePoints(){
        int blueScore = 0;
        int greenScore = 0;
        int greyScore = 0;
        int purpleScore = 0;
        int yellowScore = 0;

        for(int i=0; i<MAX_DAMAGE; i++){
            switch (damageBoard[i].getFirstColor()){
                case BLUE:
                    blueScore++;
                    break;
                case GREEN:
                    greenScore++;
                    break;
                case GREY:
                    greyScore++;
                    break;
                case PURPLE:
                    purpleScore++;
                    break;
                case YELLOW:
                    yellowScore++;
                    break;
                default:
                    break;
            }
        }

        int max = Math.max(Math.max(Math.max(Math.max(blueScore, greenScore), greyScore), purpleScore), yellowScore);

    }

    private void findMax(Integer value){

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
