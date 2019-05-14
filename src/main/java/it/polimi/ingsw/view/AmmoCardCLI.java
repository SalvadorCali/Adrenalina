package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.gamecomponents.AmmoPoint;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Square;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.util.List;

public class AmmoCardCLI {

    public static final String SPACE = " ";  // Space
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";     // RED
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String POWERUP = "P"; //Powerup
    public static final String AMMO = "A"; //ammo
    public static final String GREY = "\033[0;37m";   // WHITE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final Integer AMMO_ELEM = 3;
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;


    private List<AmmoCard> ammos;
    private Square[][] arena = new Square[ROWS][COLUMNS];
    private int playerXPosition;
    private int playerYPosition;
    private AmmoPoint ammoPoint;

    public AmmoCardCLI(Player player, GameController gameController){

        playerXPosition = player.getPosition().getX();
        playerYPosition = player.getPosition().getY();
        arena = gameController.getGame().getBoard().getArena();
    }


    public void AmmoCard(){

        String ammoCol[] = new String[AMMO_ELEM];
        String ammoVal[] = new String[AMMO_ELEM];

        //inizialize ammo
        for(int i = 0; i< AMMO_ELEM; i++){

            ammoVal[i] = AMMO;
            ammoCol[i] = CYAN;
        }

        ammoCol[0] = Converter.fromColorToCLIColor(arena[playerXPosition][playerYPosition].getAmmoCard().getFirstAmmo().getColor());
        ammoCol[1] = Converter.fromColorToCLIColor(arena[playerXPosition][playerYPosition].getAmmoCard().getSecondAmmo().getColor());
        ammoCol[2] = Converter.fromColorToCLIColor(arena[playerXPosition][playerYPosition].getAmmoCard().getThirdAmmo().getColor());

        for(int i = 0; i < AMMO_ELEM; i++){

            if(ammoCol[i].equals(CYAN)){

                ammoVal[i] = POWERUP;
            }
        }

        //print ammo
        Printer.println(SPACE + "_____");
        Printer.println("|" + ammoCol[0] + ammoVal[0] + SPACE + ammoCol[1] + ammoVal[1] + SPACE + ammoCol[2] + ammoVal[2] + GREY + "|" + RESET);
        Printer.println(SPACE + "¯¯¯¯¯");
    }
}
