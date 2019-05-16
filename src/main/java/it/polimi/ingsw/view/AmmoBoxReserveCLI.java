package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.util.List;



public class AmmoBoxReserveCLI {

    public static final String SPACE = " ";  // Space
    public static final Integer RESERVE_LENGTH = 9;
    public static final String AMMO = "A";
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREY = "\033[0;37m";   // WHITE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String RED = "\033[0;31m";     // RED


    private List<Ammo> ammoBox;
    private List<Ammo> ammoReserve;

    public void setPlayer(Player player){
        ammoBox = player.getAmmoBox();
        ammoReserve = player.getAmmoReserve();
    }

    public AmmoBoxReserveCLI(Player player){
        
        ammoBox = player.getAmmoBox();
        ammoReserve = player.getAmmoReserve();
    }


    public void printAmmoBox(){

        String ammoColor[] = new String[RESERVE_LENGTH];
        String ammoVal[] = new String[RESERVE_LENGTH];


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


        Printer.println("Your Ammo Box:");
        Printer.println(SPACE + "_________________");
        Printer.println("|" + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + "|" + RESET);
        Printer.println(SPACE + "¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
    }


    public void printAmmoReserve(){

        String ammoColor[] = new String[RESERVE_LENGTH];
        String ammoVal[] = new String[RESERVE_LENGTH];

        for(int i = 0; i < RESERVE_LENGTH; i++){

            ammoColor[i] = CYAN;
            ammoVal[i] = AMMO;
        }

        //give color to ammo
        for (int i = 0; i< ammoReserve.size(); i++){
            ammoColor[i] = Converter.fromColorToCLIColor(ammoReserve.get(i).getColor());
        }

        for(int i = 0; i < RESERVE_LENGTH; i++){

            if(ammoColor[i].equals(CYAN)){

                ammoVal[i] = SPACE;
            }
        }


        Printer.println("Your Ammo Reserve:");
        Printer.println(SPACE + "_________________");
        Printer.println("|" + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + "|" + RESET);
        Printer.println(SPACE + "¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
    }
}
