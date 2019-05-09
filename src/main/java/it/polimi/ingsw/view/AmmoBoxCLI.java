package it.polimi.ingsw.view;

import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.util.List;



public class AmmoBoxCLI {

    public static final String SPACE = " ";  // Space
    public static final Integer RESERVE_LENGTH = 9;
    public static final String AMMO = "A";
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String GREY = "\033[0;37m";   // WHITE
    public static final String CYAN = "\033[0;36m";    // CYAN


    private List<Ammo> ammoBox;

    public AmmoBoxCLI(Player player){
        
        ammoBox = player.getAmmoBox();
    }


    public void printAmmoReserve(){

        String ammoColor[] = new String[RESERVE_LENGTH];
        String ammoVal[] = new String[RESERVE_LENGTH];


        //give color to ammo
        for (int i = 0; i< RESERVE_LENGTH; i++){
            ammoColor[i] = Converter.fromColorToCLIColor(ammoBox.get(i).getColor());
        }

        for(int i = 0; i < RESERVE_LENGTH; i++){

            ammoVal[i] = AMMO;

            if(ammoColor[i].isEmpty()){

                ammoVal[i] = SPACE;
            }
        }



        Printer.println(SPACE + "_________________");
        Printer.println("|" + ammoColor[0] + ammoVal[0] + SPACE + ammoColor[1] + ammoVal[1] + SPACE + ammoColor[2] + ammoVal[2] + SPACE + ammoColor[3] + ammoVal[3] + SPACE + ammoColor[4] + ammoVal[4] + SPACE + ammoColor[5] + ammoVal[5] + SPACE + ammoColor[6] + ammoVal[6] + SPACE + ammoColor[7] + ammoVal[7] + SPACE + ammoColor[8] + ammoVal[8] + GREY + "|" + RESET);
        Printer.println(SPACE + "‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
    }
}
