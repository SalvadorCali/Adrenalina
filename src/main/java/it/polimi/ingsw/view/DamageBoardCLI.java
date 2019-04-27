package it.polimi.ingsw.view;

import it.polimi.ingsw.model.gamecomponents.PlayerBoard;
import it.polimi.ingsw.model.gamecomponents.Token;

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


    private Token[] damageBoard;
    private PlayerBoard playerBoard;


    public void main(String[] args) {

        Integer damage[] = new Integer[MAX_DAMAGE];
        String namePlayer = "gianni";
        String colorDamage[] = new String[MAX_DAMAGE];



        //initialize damage and colorDamage
        for(int i = 0; i < damage.length; i++){

            damage[i] = 0;
            colorDamage[i] = BLACK;
        }

        damageBoard = playerBoard.getDamageBoard();

        //color and assign damage
        for(int i = 0; i < damage.length; i++){

            colorDamage[i] = damageBoard[i].toString();
            damage[i]= 1;

            if(colorDamage[i] == null){

                damage[i] = 0;
                colorDamage[i] = GREY;
            }
        }



        System.out.println("DamageBoard of " + namePlayer);
        System.out.println(" _ _ _ _ _ _ _ _ _ _ _ _" + RESET);
        System.out.println("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + SPACE + colorDamage[8] + damage[8] + SPACE + colorDamage[9] + damage[9] + SPACE + colorDamage[10] + damage[10] + SPACE + colorDamage[11] + damage[11] + GREY + "|" + RESET);
        System.out.println(" ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯" + RESET);
    }
}
