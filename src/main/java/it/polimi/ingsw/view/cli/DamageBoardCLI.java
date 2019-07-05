package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Printer;

import java.util.List;

/**
 * This class prints the playerboard for the CLI.
 */
public class DamageBoardCLI {
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String HORIZONTAL = "-";
    private static final String VERTICAL = "|";
    private static final String COLON = ":";
    private static final String AMMOS = "Ammos";
    private static final String MARKS = "Marks";
    private static final String RESET = "\033[0m";

    //colors
    private static final String BLACK = "\033[0;30m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String PURPLE = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String GREY = "\033[0;37m";

    //size
    private static final Integer MAX_DAMAGE = 12;
    private static final Integer MAX_MARK = 3;
    private static final Integer RESERVE_LENGTH = 9;
    private static final Integer POINTS_LENGTH = 6;

    //letters
    private static final String MARK = "M";
    private static final String AMMO = "A";
    private static final String DEATH = "X";

    //numbers
    private static final String EIGHT = "8";
    private static final String SIX = "6";
    private static final String FOUR = "4";
    private static final String TWO = "2";
    private static final String ONE = "1";

    /**
     * An array of damages.
     */
    private Token[] damageBoard;
    /**
     * The number of death of the player.
     */
    private int deathNumber;
    /**
     * A list of marks.
     */
    private List<Token> markBoard;
    /**
     * A list of ammos.
     */
    private List<Ammo> ammoBox;
    /**
     * A boolean value, true if the player is in his final frenzy.
     */
    private boolean finalFrenzy;

    /**
     * A list of victims.
     */
    private List<Player> victims;
    /**
     * A list that contains victim's damages.
     */
    private Token[] victimDamageBoard;
    /**
     * Victim's number of deaths.
     */
    private int victimDeathNumber;
    /**
     * Victim's list of marks.
     */
    private List<Token> victimMarkBoard;
    /**
     * Victim's list of ammos.
     */
    private List<Ammo> victimAmmoBox;

    /**
     * Class constructor.
     * @param player the player whose playerboard will be printed.
     */
    public DamageBoardCLI(Player player){
        damageBoard = player.getPlayerBoard().getDamageBoard();
        markBoard = player.getPlayerBoard().getRevengeMarks();
        ammoBox = player.getAmmoBox();
        deathNumber = player.getPlayerBoard().getDeathNumber();
        finalFrenzy = false;
    }

    /**
     * Setter for player.
     * @param player the player whose playerboard will be printed.
     */
    public void setPlayer(Player player){
        damageBoard = player.getPlayerBoard().getDamageBoard();
        markBoard = player.getPlayerBoard().getRevengeMarks();
        ammoBox = player.getAmmoBox();
        deathNumber = player.getPlayerBoard().getDeathNumber();
    }

    /**
     * Setter for the victims.
     * @param victims the victims.
     */
    public void setVictims(List<Player> victims){
        this.victims = victims;
    }

    /**
     * Setter for the final frenzy boolean.
     * @param finalFrenzy a boolean value.
     */
    public void setFinalFrenzy(boolean finalFrenzy){
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * Main method of this class. Prints the player's playerboard, with ammos, damages and marks of the right color.
     */
    public void printDamageBoard() {
        Integer[] damage = new Integer[MAX_DAMAGE];
        String[] colorDamage = new String[MAX_DAMAGE];
        String[] mark = new String[MAX_MARK];
        String[] colorMark = new String[MAX_MARK];
        String[] ammoColor = new String[RESERVE_LENGTH];
        String[] ammoVal = new String[RESERVE_LENGTH];
        String[] points = new String[POINTS_LENGTH];

        //initialize damage and colorDamage
        for(int i = 0; i < MAX_DAMAGE; i++){
            damage[i] = 0;
            colorDamage[i] = CYAN;
        }

        //initialize marks
        for(int i = 0; i< MAX_MARK; i++){
            mark[i] = SPACE;
            colorMark[i] = CYAN;
        }

        //color and assign damage
        for(int i = 0; i < damage.length; i++){
            damage[i]= 0;
            colorDamage[i] = setColorDamage(colorDamage, i);
            if(!colorDamage[i].equals(CYAN)){
                damage[i] = 1;
            }
        }

        if(!markBoard.isEmpty()) {
            for (int i = 0; i < markBoard.size(); i++) {
                if (markBoard.get(i).getFirstColor() != TokenColor.NONE) {
                    mark[i] = MARK;
                    colorMark[i] = setColorMark(colorMark, i);
                }
            }
        }

        for(int i = 0; i < RESERVE_LENGTH; i++){
            ammoColor[i] = CYAN;
            ammoVal[i] = AMMO;
        }

        //give color to ammo
        ammoColor = setAmmoColor(ammoColor);
        for(int i = 0; i < RESERVE_LENGTH; i++){
            if(ammoColor[i].equals(CYAN)){
                ammoVal[i] = SPACE;
            }
        }
        points = setPoints(points);
        print(damage, colorDamage, mark, colorMark, ammoColor, ammoVal, points);
    }

    /**
     * Sets the color of each damage.
     * @param colorDamage an array of damages.
     * @param i the index.
     * @return the current damage.
     */
    private String setColorDamage(String[] colorDamage, int i){
        if(damageBoard[i].getFirstColor().equals(TokenColor.BLUE))
            colorDamage[i] = BLUE;
        if(damageBoard[i].getFirstColor().equals(TokenColor.RED))
            colorDamage[i] = RED;
        if(damageBoard[i].getFirstColor().equals(TokenColor.YELLOW))
            colorDamage[i] = YELLOW;
        if(damageBoard[i].getFirstColor().equals(TokenColor.GREY))
            colorDamage[i] = GREY;
        if(damageBoard[i].getFirstColor().equals(TokenColor.PURPLE))
            colorDamage[i] = PURPLE;
        if(damageBoard[i].getFirstColor().equals(TokenColor.GREEN))
            colorDamage[i] = GREEN;
        if(damageBoard[i].getFirstColor().equals(TokenColor.SKULL))
            colorDamage[i] = CYAN;
        if(damageBoard[i].getFirstColor().equals(TokenColor.NONE))
            colorDamage[i] = CYAN;
        return colorDamage[i];
    }

    /**
     * Sets the color of each mark.
     * @param colorMark an array of marks.
     * @param i the index.
     * @return the current damage.
     */
    private String setColorMark(String[] colorMark, int i){
        if (markBoard.get(i).getFirstColor().equals(TokenColor.BLUE))
            colorMark[i] = BLUE;
        if (markBoard.get(i).getFirstColor().equals(TokenColor.RED))
            colorMark[i] = RED;
        if (markBoard.get(i).getFirstColor().equals(TokenColor.YELLOW))
            colorMark[i] = YELLOW;
        if (markBoard.get(i).getFirstColor().equals(TokenColor.GREY))
            colorMark[i] = GREY;
        if (markBoard.get(i).getFirstColor().equals(TokenColor.GREEN))
            colorMark[i] = GREEN;
        if (markBoard.get(i).getFirstColor().equals(TokenColor.PURPLE))
            colorMark[i] = PURPLE;
        if (markBoard.get(i).getFirstColor().equals(TokenColor.SKULL))
            colorMark[i] = CYAN;
        if (markBoard.get(i).getFirstColor().equals(TokenColor.NONE))
            colorMark[i] = CYAN;
        return colorMark[i];
    }

    /**
     * Sets the color of each ammo.
     * @param ammoColor an array of ammos.
     * @return the array of ammos.
     */
    private String[] setAmmoColor(String[] ammoColor){
        for (int i = 0; i < ammoBox.size(); i++){
            if(ammoBox.get(i).getColor().equals(Color.BLUE)){
                ammoColor[i] = BLUE;
            }
            if(ammoBox.get(i).getColor().equals(Color.RED)){
                ammoColor[i] = RED;
            }
            if(ammoBox.get(i).getColor().equals(Color.YELLOW)){
                ammoColor[i] = YELLOW;
            }
            if(ammoBox.get(i).getColor().equals(Color.NONE)){
                ammoColor[i] = CYAN;
            }
        }
        return ammoColor;
    }

    /**
     * Sets the list of points for each death.
     * @param points the list of points.
     * @return the list of points.
     */
    private String[] setPoints(String[] points){
        points[0] = EIGHT;
        points[1] = SIX;
        points[2] = FOUR;
        points[3] = TWO;
        points[4] = ONE;
        points[5] = ONE;

        for(int i=0; i<deathNumber; i++){
            points[i] = DEATH;
        }

        if(finalFrenzy){
            points[0] = SPACE;
            points[1] = TWO;
            points[2] = ONE;
            points[3] = ONE;
            points[4] = ONE;
            points[5] = SPACE;
        }
        return points;
    }

    /**
     * Prints the player's playerboard.
     * @param damage an array of integers.
     * @param colorDamage an array of colors that represent the color of the shooter.
     * @param mark an array of marks.
     * @param colorMark an array of colors that represent the color of the marks.
     * @param ammoColor the color of the ammos.
     * @param ammoVal the value of the ammos.
     * @param points the current maximum points assigned after the death.
     */
    private void print(Integer[] damage, String[] colorDamage, String[] mark, String[] colorMark, String[] ammoColor, String[] ammoVal, String[] points){
        Printer.print(SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE);
        Printer.print(SPACE + HORIZONTAL + AMMOS + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + MARKS + HORIZONTAL + NEW_LINE + RESET);
        Printer.print(SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE);
        Printer.print(VERTICAL + SPACE + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + SPACE + RESET);
        Printer.print(VERTICAL + SPACE);
        Printer.print(VERTICAL + SPACE + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + RESET + VERTICAL + NEW_LINE);
        Printer.print(SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE);
        Printer.print(VERTICAL + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + VERTICAL + HORIZONTAL + VERTICAL + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + VERTICAL + NEW_LINE + RESET);
        Printer.print(SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE);
        Printer.print(VERTICAL + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + GREY + VERTICAL + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + GREY + "|" + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + SPACE + colorDamage[8] + damage[8] + SPACE + colorDamage[9] + damage[9] + SPACE + GREY + VERTICAL + SPACE + colorDamage[10] + damage[10] + SPACE + colorDamage[11] + damage[11] + RESET + VERTICAL + NEW_LINE);
        Printer.print(SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE);
        Printer.print(VERTICAL + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + VERTICAL + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + NEW_LINE + RESET);
        Printer.print(SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE);
        Printer.print(VERTICAL + points[0] + SPACE + points[1] + SPACE + points[2] + SPACE + points[3] + SPACE + points[4] + SPACE + points[5] + VERTICAL + NEW_LINE);
        Printer.print(SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE + SPACE);
        Printer.print(SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + NEW_LINE + RESET);
    }

    /**
     * Main method of this class. Prints the victim's playerboard, with ammos, damages and marks of the right color.
     */
    void printVictimsDamageBoard() {
        for(int k = 0; k<victims.size(); k++){
            Integer[] damage = new Integer[MAX_DAMAGE];
            String[] colorDamage = new String[MAX_DAMAGE];
            String[] mark = new String[MAX_MARK];
            String[] colorMark = new String[MAX_MARK];
            String[] ammoColor = new String[RESERVE_LENGTH];
            String[] ammoVal = new String[RESERVE_LENGTH];
            String[] points = new String[POINTS_LENGTH];
            victimDamageBoard = victims.get(k).getPlayerBoard().getDamageBoard();
            victimMarkBoard = victims.get(k).getPlayerBoard().getRevengeMarks();
            victimAmmoBox = victims.get(k).getAmmoBox();
            victimDeathNumber = victims.get(k).getPlayerBoard().getDeathNumber();

            //initialize damage and colorDamage
            for(int i = 0; i < MAX_DAMAGE; i++){
                damage[i] = 0;
                colorDamage[i] = CYAN;
            }

            //initialize marks
            for(int i = 0; i< MAX_MARK; i++){
                mark[i] = " ";
                colorMark[i] = CYAN;
            }

            //color and assign damage
            for(int i = 0; i < damage.length; i++){
                damage[i]= 0;
                colorDamage[i] = setVictimsColorDamage(colorDamage, i);
                if(!colorDamage[i].equals(CYAN)){
                    damage[i] = 1;
                }
            }

            if(!victimMarkBoard.isEmpty()) {
                for (int i = 0; i < victimMarkBoard.size(); i++) {
                    if (victimMarkBoard.get(i).getFirstColor() != TokenColor.NONE) {
                        mark[i] = MARK;
                        colorMark[i] = setVictimsColorMark(colorMark, i);
                    }
                }
            }

            for(int i = 0; i < RESERVE_LENGTH; i++){
                ammoColor[i] = CYAN;
                ammoVal[i] = AMMO;
            }

            //give color to ammo
            ammoColor = setVictimsAmmoColor(ammoColor);

            for(int i = 0; i < RESERVE_LENGTH; i++){
                if(ammoColor[i].equals(CYAN)){
                    ammoVal[i] = SPACE;
                }
            }
            setVictimsPoints(points);
            Printer.println(victims.get(k).getUsername() + COLON + SPACE + victims.get(k).getColor());
            print(damage, colorDamage, mark, colorMark, ammoColor, ammoVal, points);
        }
    }

    /**
     * Sets the color of each damage.
     * @param colorDamage an array of damages.
     * @param i the index.
     * @return the current damage.
     */
    private String setVictimsColorDamage(String[] colorDamage, int i){
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.BLUE))
            colorDamage[i] = BLUE;
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.RED))
            colorDamage[i] = RED;
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.YELLOW))
            colorDamage[i] = YELLOW;
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.GREY))
            colorDamage[i] = GREY;
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.PURPLE))
            colorDamage[i] = PURPLE;
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.GREEN))
            colorDamage[i] = GREEN;
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.SKULL))
            colorDamage[i] = CYAN;
        if(victimDamageBoard[i].getFirstColor().equals(TokenColor.NONE))
            colorDamage[i] = CYAN;
        return colorDamage[i];
    }

    /**
     * Sets the color of each mark.
     * @param colorMark an array of marks.
     * @param i the index.
     * @return the current damage.
     */
    private String setVictimsColorMark(String[] colorMark, int i){
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.BLUE))
            colorMark[i] = BLUE;
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.RED))
            colorMark[i] = RED;
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.YELLOW))
            colorMark[i] = YELLOW;
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.GREY))
            colorMark[i] = GREY;
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.GREEN))
            colorMark[i] = GREEN;
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.PURPLE))
            colorMark[i] = PURPLE;
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.SKULL))
            colorMark[i] = CYAN;
        if (victimMarkBoard.get(i).getFirstColor().equals(TokenColor.NONE))
            colorMark[i] = CYAN;
        return colorMark[i];
    }

    /**
     * Sets the color of each ammo.
     * @param ammoColor an array of ammos.
     * @return the array of ammos.
     */
    private String[] setVictimsAmmoColor(String[] ammoColor){
        for (int i = 0; i < victimAmmoBox.size(); i++){
            if(victimAmmoBox.get(i).getColor().equals(Color.BLUE)){
                ammoColor[i] = BLUE;
            }
            if(victimAmmoBox.get(i).getColor().equals(Color.RED)){
                ammoColor[i] = RED;
            }
            if(victimAmmoBox.get(i).getColor().equals(Color.YELLOW)){
                ammoColor[i] = YELLOW;
            }
            if(victimAmmoBox.get(i).getColor().equals(Color.NONE)){
                ammoColor[i] = CYAN;
            }
        }
        return ammoColor;
    }

    /**
     * Sets the list of points for each death.
     * @param points the list of points.
     * @return the list of points.
     */
    private String[] setVictimsPoints(String[] points){
        points[0] = EIGHT;
        points[1] = SIX;
        points[2] = FOUR;
        points[3] = TWO;
        points[4] = ONE;
        points[5] = ONE;

        for(int i=0; i<victimDeathNumber; i++){
            points[i] = DEATH;
        }

        if(finalFrenzy){
            points[0] = SPACE;
            points[1] = TWO;
            points[2] = ONE;
            points[3] = ONE;
            points[4] = ONE;
            points[5] = SPACE;
        }
        return points;
    }
}
