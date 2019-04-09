package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;

import java.util.*;

public class PlayerBoard {
    private Token[] damageBoard;
    private static final int MAX_DAMAGE = 12;
    private int damageIndex;
    private int deathNumber;
    private List<Token> revengeMarks;
    private AdrenalineZone adrenalineZone;
    private boolean dead;
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
            else if(damageIndex == 10){
                dead = true;
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
/*
    public Map<TokenColor, Score> scoring(){
        Score score;
        for(int i=0; i<MAX_DAMAGE; i++){
            score = scoreList.get(damageBoard[i].getFirstColor());
            score.setScore(score.getScore() + 1);
            scoreList.replace(damageBoard[i].getFirstColor(), score);
        }

        List<Score> scores = new ArrayList<>();
        scoreList.forEach((key, value) -> scores.add(value));

        for(int k=0; k<scores.size(); k++){

        }

        int max = 0;
        for(int i=0; i<scores.size(); i++){
            max = Math.max(max, scores.get(i).getScore());
        }

        ArrayList<Score> maximumScores = new ArrayList<>();
        for(int i=0; i<scores.size(); i++){
            if(max == scores.get(i).getScore()){
                maximumScores.add(scores.get(i));
            }
        }
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(8);
        numbers.add(6);
        numbers.add(4);
        numbers.add(2);
        if(maximumScores.size() > 1){
            evaluate_max:
            for(int i=0; i<MAX_DAMAGE; i++){
                for(int j=0; j<maximumScores.size(); j++){
                    if(damageBoard[i].getFirstColor().equals(maximumScores.get(j).getColor())){
                        scoreList.replace(damageBoard[i].getFirstColor(), new Score(damageBoard[i].getFirstColor(), numbers.get(0)));
                        maximumScores.remove(j);
                        numbers.remove(0);
                        break evaluate_max;
                    }
                }
            }
        }else{
            scoreList.replace(maximumScores.get(0).getColor(), new Score(maximumScores.get(0).getColor(), numbers.get(0)));
        }



        score = scoreList.get(getFirstBlood());
        scoreList.replace(getFirstBlood(), score, score + 1);
        score = scoreList.get(getKillshot());
        scoreList.replace(getKillshot(), score, score + 1);
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

*/
}
