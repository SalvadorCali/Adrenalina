package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.util.List;

public class KillshotTrackCLI {
    public static final String SPACE = " ";  // Space
    public static final String RESET = "\033[0m";  // Text Reset
    public static final Integer MAX_SKULLS = 8;
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String GREY = "\033[0;37m";   // WHITE
    private final static String MARK = "M";

    private List<Token> killshotTrack;

    public KillshotTrackCLI(List<Token> killshotTrack){
        this.killshotTrack = killshotTrack;

    }

    public void setKillshotTrack(List<Token> killshotTrack){
        this.killshotTrack = killshotTrack;
    }


    public void printKillshotTrack() {

        String damage[] = new String[MAX_SKULLS];
        String colorDamage[] = new String[MAX_SKULLS];
        String overkill[] = new String[MAX_SKULLS];
        String colorOverkill[] = new String[MAX_SKULLS];

        //initialize damage and colorDamage
        for(int i = 0; i < MAX_SKULLS; i++){
            damage[i] = "-";
            colorDamage[i] = BLACK;
        }

        //
        for(int i = 0; i < MAX_SKULLS; i++){
            overkill[i] = " ";
            colorOverkill[i] = BLACK;
        }

        //color and assign damage
        if(!killshotTrack.isEmpty()) {
            for (int i = 0; i < killshotTrack.size(); i++) {

                if (killshotTrack.get(i).getFirstColor() != TokenColor.NONE) {

                    damage[i] = "S";

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.BLUE))
                        colorDamage[i] = BLUE;

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.RED))
                        colorDamage[i] = RED;

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.YELLOW))
                        colorDamage[i] = YELLOW;

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.GREY))
                        colorDamage[i] = GREY;

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.GREEN))
                        colorDamage[i] = GREEN;

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.PURPLE))
                        colorDamage[i] = PURPLE;

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.SKULL))
                        colorDamage[i] = BLACK;

                    if (killshotTrack.get(i).getFirstColor().equals(TokenColor.NONE))
                        colorDamage[i] = CYAN;

                    if(colorDamage[i] != BLACK){
                        //damage[i] = Converter.fromTokenColorToCLIColor(killshotTrack.get(i).getFirstColor());
                        damage[i] = "X";
                    }
                }
                if (killshotTrack.get(i).getSecondColor() != TokenColor.NONE) {

                    overkill[i] = "X";

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.BLUE))
                        colorOverkill[i] = BLUE;

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.RED))
                        colorOverkill[i] = RED;

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.YELLOW))
                        colorOverkill[i] = YELLOW;

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.GREY))
                        colorOverkill[i] = GREY;

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.GREEN))
                        colorOverkill[i] = GREEN;

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.PURPLE))
                        colorOverkill[i] = PURPLE;

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.SKULL))
                        colorOverkill[i] = BLACK;

                    if (killshotTrack.get(i).getSecondColor().equals(TokenColor.NONE))
                        colorOverkill[i] = CYAN;

                    if(colorDamage[i] != BLACK){
                        //damage[i] = Converter.fromTokenColorToCLIColor(killshotTrack.get(i).getFirstColor());
                        overkill[i] = "X";
                    }
                }
            }
        }

        Printer.print(" _ _ _ _ _ _ _ _\n" + RESET);
        Printer.print("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + RESET + "|" + "\n");
        //Printer.print(" ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);
        //Printer.print("| | | | | | | | |\n" + RESET);
        //Printer.print(" _ _ _ _ _ _ _ _\n" + RESET);
        Printer.print("|" + colorOverkill[0] + overkill[0] + SPACE + colorOverkill[1] + overkill[1] + SPACE + colorOverkill[2] + overkill[2] + SPACE + colorOverkill[3] + overkill[3] + SPACE + colorOverkill[4] + overkill[4] + SPACE + colorOverkill[5] + overkill[5] + SPACE + colorOverkill[6] + overkill[6] + SPACE + colorOverkill[7] + overkill[7] + RESET + "|" + "\n");
        Printer.print(" ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);

    }
}
