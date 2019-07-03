package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Printer;

import java.util.List;

/**
 * This class prints the killshot track for the CLI.
 */
public class KillshotTrackCLI {
    private static final String SPACE = " ";
    private static final String NEW_LINE = "\n";
    private static final String RESET = "\033[0m";
    private static final String HORIZONTAL = "-";
    private static final String VERTICAL = "|";

    //size
    private static final Integer MAX_SKULLS = 8;

    //colors
    private static final String BLACK = "\u001b[30;1m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String PURPLE = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String GREY = "\033[0;37m";

    //letters
    private static final String KILLSHOT = "X";
    private static final String SKULL = "S";
    private static final String NOT_SKULL = "-";

    /**
     * The killshot track that will be printed.
     */
    private List<Token> killshotTrack;

    /**
     * Class constructor. It sets the killshot track.
     * @param killshotTrack the killshot track that will be printed.
     */
    public KillshotTrackCLI(List<Token> killshotTrack){
        this.killshotTrack = killshotTrack;

    }

    /**
     * Setter for the killshot track.
     * @param killshotTrack the killshot track that will be set.
     */
    public void setKillshotTrack(List<Token> killshotTrack){
        this.killshotTrack = killshotTrack;
    }

    /**
     * Main method of this class. It initializes a rectangle full of "_", and on the basis of number of skulls, it substitutes them with "S".
     * When a player is killed it substitutes the "S" with an "X" colored as the shooter.
     */
    public void printKillshotTrack() {
        String[] damage = new String[MAX_SKULLS];
        String[] colorDamage = new String[MAX_SKULLS];
        String[] overkill = new String[MAX_SKULLS];
        String[] colorOverkill = new String[MAX_SKULLS];

        //initialize damage and colorDamage
        for(int i = 0; i < MAX_SKULLS; i++){
            damage[i] = NOT_SKULL;
            colorDamage[i] = BLACK;
        }

        //initialize overkills
        for(int i = 0; i < MAX_SKULLS; i++){
            overkill[i] = SPACE;
            colorOverkill[i] = BLACK;
        }

        //color and assign damage
        if(!killshotTrack.isEmpty()) {
            for (int i = 0; i < killshotTrack.size(); i++) {
                if (killshotTrack.get(i).getFirstColor() != TokenColor.NONE) {
                    damage[i] = SKULL;
                    colorDamage[i] = assignKillshots(colorDamage, i);
                    if(!colorDamage[i].equals(BLACK)){
                        //damage[i] = Converter.fromTokenColorToCLIColor(killshotTrack.get(i).getFirstColor());
                        damage[i] = KILLSHOT;
                    }
                }
                if (killshotTrack.get(i).getSecondColor() != TokenColor.NONE) {
                    overkill[i] = KILLSHOT;
                    colorOverkill[i] = assignOverkills(colorOverkill, i);
                    if(!colorDamage[i].equals(BLACK)){
                        //damage[i] = Converter.fromTokenColorToCLIColor(killshotTrack.get(i).getFirstColor());
                        overkill[i] = KILLSHOT;
                    }
                }
            }
        }
        print(damage, colorDamage, overkill, colorOverkill);
    }

    /**
     * Assigns the correct color of each killshots.
     * @param colorDamage the list of killshots.
     * @param i the current index.
     * @return the current killshot.
     */
    private String assignKillshots(String[] colorDamage, int i){
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
        return colorDamage[i];
    }

    /**
     * Assigns the correct color of each overkill.
     * @param colorOverkill the list of overkills.
     * @param i the current index.
     * @return the current overkill.
     */
    private String assignOverkills(String[] colorOverkill, int i){
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
        return colorOverkill[i];
    }

    /**
     * Prints the killshot track.
     * @param damage the skulls.
     * @param colorDamage the skulls' color.
     * @param overkill the overkills.
     * @param colorOverkill the overkills' color.
     */
    private void print(String[] damage, String[] colorDamage, String[] overkill, String[] colorOverkill){
        /**
         Printer.print(" _ _ _ _ _ _ _ _\n" + RESET);
         Printer.print("|" + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + RESET + "|" + "\n");
         //Printer.print(" ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);
         //Printer.print("| | | | | | | | |\n" + RESET);
         //Printer.print(" _ _ _ _ _ _ _ _\n" + RESET);
         Printer.print("|" + colorOverkill[0] + overkill[0] + SPACE + colorOverkill[1] + overkill[1] + SPACE + colorOverkill[2] + overkill[2] + SPACE + colorOverkill[3] + overkill[3] + SPACE + colorOverkill[4] + overkill[4] + SPACE + colorOverkill[5] + overkill[5] + SPACE + colorOverkill[6] + overkill[6] + SPACE + colorOverkill[7] + overkill[7] + RESET + "|" + "\n");
         Printer.print(" ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯\n" + RESET);
         */
        Printer.print(SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + NEW_LINE + RESET);
        Printer.print(VERTICAL + colorDamage[0] + damage[0] + SPACE + colorDamage[1] + damage[1] + SPACE + colorDamage[2] + damage[2] + SPACE + colorDamage[3] + damage[3] + SPACE + colorDamage[4] + damage[4] + SPACE + colorDamage[5] + damage[5] + SPACE + colorDamage[6] + damage[6] + SPACE + colorDamage[7] + damage[7] + RESET + VERTICAL + NEW_LINE);
        Printer.print(VERTICAL + colorOverkill[0] + overkill[0] + SPACE + colorOverkill[1] + overkill[1] + SPACE + colorOverkill[2] + overkill[2] + SPACE + colorOverkill[3] + overkill[3] + SPACE + colorOverkill[4] + overkill[4] + SPACE + colorOverkill[5] + overkill[5] + SPACE + colorOverkill[6] + overkill[6] + SPACE + colorOverkill[7] + overkill[7] + RESET + VERTICAL + NEW_LINE);
        Printer.print(SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + SPACE + HORIZONTAL + NEW_LINE + RESET);
    }
}