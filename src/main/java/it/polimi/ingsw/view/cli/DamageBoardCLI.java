package it.polimi.ingsw.view.cli;

//import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
//import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Printer;

import java.util.List;

/**
 * This class prints the playerboard for the CLI.
 */
public class DamageBoardCLI {
    public static final String SPACE = " ";  // Space
    public static final String RESET = "\033[0m";  // Text Reset
    public static final Integer MAX_DAMAGE = 12;
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String GREY = "\033[0;37m";   // WHITE
    private final static Integer MAX_MARK = 3;
    private final static String MARK = "M";
    public static final Integer RESERVE_LENGTH = 9;
    public static final String AMMO = "A";
    public static final Integer POINTS_LENGTH = 6;

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
        //currentPlayer = player.getUsername();
        markBoard = player.getPlayerBoard().getRevengeMarks();
        ammoBox = player.getAmmoBox();
        deathNumber = player.getPlayerBoard().getDeathNumber();
        finalFrenzy = false;
        //damageBoard = gameController.getGame().getCurrentPlayer().getPlayerBoard().getDamageBoard();
        //currentPlayer = gameController.getGame().getCurrentPlayer().getUsername();
        //markBoard = gameController.getGame().getCurrentPlayer().getPlayerBoard().getRevengeMarks();
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
     * @param victims
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

        Integer damage[] = new Integer[MAX_DAMAGE];
        String colorDamage[] = new String[MAX_DAMAGE];
        String mark[] = new String[MAX_MARK];
        String colorMark[] = new String[MAX_MARK];
        String ammoColor[] = new String[RESERVE_LENGTH];
        String ammoVal[] = new String[RESERVE_LENGTH];
        String points[] = new String[POINTS_LENGTH];

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

            if(colorDamage[i] != CYAN){

                damage[i] = 1;
            }


        }

        if(!markBoard.isEmpty()) {
            for (int i = 0; i < markBoard.size(); i++) {

                if (markBoard.get(i).getFirstColor() != TokenColor.NONE) {

                    mark[i] = MARK;

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

                }
            }
        }

        for(int i = 0; i < RESERVE_LENGTH; i++){

            ammoColor[i] = CYAN;
            ammoVal[i] = AMMO;
        }

        //give color to ammo
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

        for(int i = 0; i < RESERVE_LENGTH; i++){


            if(ammoColor[i].equals(CYAN)){

                ammoVal[i] = SPACE;
            }
        }

        points[0] = "8";
        points[1] = "6";
        points[2] = "4";
        points[3] = "2";
        points[4] = "1";
        points[5] = "1";

        for(int i=0; i<deathNumber; i++){
            points[i] = "X";
        }

        if(finalFrenzy){
            Printer.println("Frenesia");
            points[0] = " ";
            points[1] = "2";
            points[2] = "1";
            points[3] = "1";
            points[4] = "1";
            points[5] = " ";
        }

        //give name current player
        //namePlayer = currentPlayer;

        //Printer.print(namePlayer + "'s damageBoard\n");
        /*
        Printer.print("        ");
        //Printer.print("                      ");
        Printer.print(" _Ammos_ _ _ _ _ _ _ _ _Marks_\n" + RESET);
        Printer.print("        ");
        Printer.print("|" + " " + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + " " + RESET);
        //Printer.print("                      ");
        Printer.print("| ");
        Printer.print("|" + " " + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + RESET + "|\n");
        Printer.print("        ");
        Printer.print("|_ _ _ _ _ _ _ _ _ _|_|_ _ _ _|\n" + RESET);
        Printer.print("        ");
        Printer.print("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + GREY + "|" + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + GREY + "|" + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + SPACE + colorDamage[8] + damage[8] + SPACE + colorDamage[9] + damage[9] + SPACE + GREY + "|" + SPACE + colorDamage[10] + damage[10] + SPACE + colorDamage[11] + damage[11] + RESET + "|\n");
        Printer.print("        ");
        Printer.print("|¯ ¯ ¯ ¯ ¯ ¯|¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);
        Printer.print("        ");
        Printer.print("|" + points[0] + SPACE + points[1] + SPACE + points[2] + SPACE + points[3] + SPACE + points[4] + SPACE + points[5] + "|\n");
        Printer.print("        ");
        Printer.print(" ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);*/

        Printer.print("        ");
        //Printer.print("                      ");
        Printer.print(" -Ammos- - - - - - - - -Marks-\n" + RESET);
        Printer.print("        ");
        Printer.print("|" + " " + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + " " + RESET);
        //Printer.print("                      ");
        Printer.print("| ");
        Printer.print("|" + " " + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + RESET + "|\n");
        Printer.print("        ");
        Printer.print("|- - - - - - - - - -|-|- - - -|\n" + RESET);
        Printer.print("        ");
        Printer.print("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + GREY + "|" + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + GREY + "|" + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + SPACE + colorDamage[8] + damage[8] + SPACE + colorDamage[9] + damage[9] + SPACE + GREY + "|" + SPACE + colorDamage[10] + damage[10] + SPACE + colorDamage[11] + damage[11] + RESET + "|\n");
        Printer.print("        ");
        Printer.print("|- - - - - -|- - - - - - - - -\n" + RESET);
        Printer.print("        ");
        Printer.print("|" + points[0] + SPACE + points[1] + SPACE + points[2] + SPACE + points[3] + SPACE + points[4] + SPACE + points[5] + "|\n");
        Printer.print("        ");
        Printer.print(" - - - - - -\n" + RESET);

        /*
        Printer.println(SPACE + "_________________");
        Printer.println("|" + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + "|" + RESET);
        Printer.println(SPACE + "¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

         */

/*
        Printer.print("Marks:\n");

        Printer.print(" _ _ _ _\n" + RESET);
        Printer.print("|" + " " + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + GREY + "|\n" + RESET);
        Printer.print(" ¯ ¯ ¯ ¯\n" + RESET);
*/
    }

    /**
     * Main method of this class. Prints the victim's playerboard, with ammos, damages and marks of the right color.
     */
    public void printVictimsDamageBoard() {
        for(int k = 0; k<victims.size(); k++){
            Integer damage[] = new Integer[MAX_DAMAGE];
            String colorDamage[] = new String[MAX_DAMAGE];
            String mark[] = new String[MAX_MARK];
            String colorMark[] = new String[MAX_MARK];
            String ammoColor[] = new String[RESERVE_LENGTH];
            String ammoVal[] = new String[RESERVE_LENGTH];
            String points[] = new String[POINTS_LENGTH];
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

                if(colorDamage[i] != CYAN){

                    damage[i] = 1;
                }


            }

            if(!victimMarkBoard.isEmpty()) {
                for (int i = 0; i < victimMarkBoard.size(); i++) {

                    if (victimMarkBoard.get(i).getFirstColor() != TokenColor.NONE) {

                        mark[i] = MARK;

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

                    }
                }
            }

            for(int i = 0; i < RESERVE_LENGTH; i++){

                ammoColor[i] = CYAN;
                ammoVal[i] = AMMO;
            }

            //give color to ammo
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

            for(int i = 0; i < RESERVE_LENGTH; i++){


                if(ammoColor[i].equals(CYAN)){

                    ammoVal[i] = SPACE;
                }
            }

            points[0] = "8";
            points[1] = "6";
            points[2] = "4";
            points[3] = "2";
            points[4] = "1";
            points[5] = "1";

            for(int i=0; i<victimDeathNumber; i++){
                points[i] = "X";
            }

            if(finalFrenzy){
                points[0] = " ";
                points[1] = "2";
                points[2] = "1";
                points[3] = "1";
                points[4] = "1";
                points[5] = " ";
            }

            //give name current player
            //namePlayer = currentPlayer;

/*            Printer.println(victims.get(k).getUsername() + ": " + victims.get(k).getColor());
            Printer.print("        ");
            //Printer.print("                      ");
            Printer.print(" _Ammos_ _ _ _ _ _ _ _ _Marks_\n" + RESET);
            Printer.print("        ");
            Printer.print("|" + " " + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + " " + RESET);
            //Printer.print("                      ");
            Printer.print("| ");
            Printer.print("|" + " " + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + RESET + "|\n");
            Printer.print("        ");
            Printer.print("|_ _ _ _ _ _ _ _ _ _|_|_ _ _ _|\n" + RESET);
            Printer.print("        ");
            Printer.print("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + GREY + "|" + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + GREY + "|" + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + SPACE + colorDamage[8] + damage[8] + SPACE + colorDamage[9] + damage[9] + SPACE + GREY + "|" + SPACE + colorDamage[10] + damage[10] + SPACE + colorDamage[11] + damage[11] + RESET + "|\n");
            Printer.print("        ");
            Printer.print("|¯ ¯ ¯ ¯ ¯ ¯|¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);
            Printer.print("        ");
            Printer.print("|" + points[0] + SPACE + points[1] + SPACE + points[2] + SPACE + points[3] + SPACE + points[4] + SPACE + points[5] + "|\n");
            Printer.print("        ");
            Printer.print(" ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);*/

            Printer.println(victims.get(k).getUsername() + ": " + victims.get(k).getColor());
            Printer.print("        ");
            //Printer.print("                      ");
            Printer.print(" -Ammos- - - - - - - - -Marks-\n" + RESET);
            Printer.print("        ");
            Printer.print("|" + " " + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + " " + RESET);
            //Printer.print("                      ");
            Printer.print("| ");
            Printer.print("|" + " " + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + RESET + "|\n");
            Printer.print("        ");
            Printer.print("|- - - - - - - - - -|-|- - - -|\n" + RESET);
            Printer.print("        ");
            Printer.print("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + GREY + "|" + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + GREY + "|" + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + SPACE + colorDamage[8] + damage[8] + SPACE + colorDamage[9] + damage[9] + SPACE + GREY + "|" + SPACE + colorDamage[10] + damage[10] + SPACE + colorDamage[11] + damage[11] + RESET + "|\n");
            Printer.print("        ");
            Printer.print("|- - - - - -|- - - - - - - - -\n" + RESET);
            Printer.print("        ");
            Printer.print("|" + points[0] + SPACE + points[1] + SPACE + points[2] + SPACE + points[3] + SPACE + points[4] + SPACE + points[5] + "|\n");
            Printer.print("        ");
            Printer.print(" - - - - - -\n" + RESET);

        /*
        Printer.println(SPACE + "_________________");
        Printer.println("|" + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + "|" + RESET);
        Printer.println(SPACE + "¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");

         */

/*
        Printer.print("Marks:\n");

        Printer.print(" _ _ _ _\n" + RESET);
        Printer.print("|" + " " + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + GREY + "|\n" + RESET);
        Printer.print(" ¯ ¯ ¯ ¯\n" + RESET);
*/
        }

    }
}
