package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.PlayerBoard;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Printer;

import java.util.List;

public class DamageBoardCLI {

    public static final String SPACE = " ";  // Space
    public static final String RESET = "\033[0m";  // Text Reset
    public static final Integer MAX_DAMAGE = 12;  // Text Reset
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

    private List<Token> damageBoard;
    private String currentPlayer;


    public DamageBoardCLI(GameController gameController){

        damageBoard = gameController.getGame().getKillshotTrack();
        //currentPlayer = gameController.getGame().getCurrentPlayer().toString();
    }


    public void printDamageBoard() {

        Integer damage[] = new Integer[MAX_DAMAGE];
        String namePlayer;
        String colorDamage[] = new String[300];
        String[] mark = new String[MAX_MARK];
        String colorMark[] = new String[MAX_MARK];


        //initialize damage and colorDamage
        for(int i = 0; i < damage.length; i++){

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

//            colorDamage[i] = damageBoard.get(i).getFirstColor().toString();
            damage[i]= 0;

            if(colorDamage[i] == null){

                damage[i] = 0;
                colorDamage[i] = GREY;
            }
        }

        for(int i = 0; i< mark.length; i++){

            //colorMark[i];
            mark[i] = MARK;
        }

        //give name current player
        namePlayer = currentPlayer;

        Printer.print(namePlayer + "'s damageBoard\n");
        Printer.print(" _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\n" + RESET);
        Printer.print("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + GREY + "|" + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + GREY + "|" + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + SPACE + colorDamage[8] + damage[8] + SPACE + colorDamage[9] + damage[9] + SPACE + GREY + "|" + SPACE + colorDamage[10] + damage[10] + SPACE + colorDamage[11] + damage[11] + GREY + "|\n" + RESET);
        Printer.print(" ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);


        Printer.print("Marks:\n");

        Printer.print(" _ _ _ _\n" + RESET);
        Printer.print("|" + " " + colorMark[0] + mark[0] + " " + colorMark[1] + mark[1] + " " + colorMark[2] + mark[2] + " " + GREY + "|\n" + RESET);
        Printer.print(" ¯ ¯ ¯ ¯\n" + RESET);

    }
}
