package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.util.Converter;

import java.io.Serializable;

/**
 * class that represents the ammo components
 */
public class Ammo implements Serializable {
    private Color color;

    /**
     * constructor of the ammo class
     * @param color indicates the color chosen to construct the Ammo
     */
    public Ammo(Color color){
        this.color = color;
    }

    /**
     * setter of the ammo color.
     * @param color indicates the color chosen to set the ammo color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * getter of the ammo color.
     * @return the color of the ammo.
     */
    public Color getColor() {
        return color;
    }

    /**
     * toString of the Color class.
     * @return the printable string.
     */
    public String toString(){
        StringBuilder ammo = new StringBuilder();
        ammo.append("\n\033[0;34m _ _ ");
        ammo.append("\n| " + Converter.fromColorToLetter(this.color) + " | ");
        ammo.append("\n ¯ ¯ \033[0m");
        return ammo.toString();
    }
}
