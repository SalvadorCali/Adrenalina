package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.util.Converter;

import java.io.Serializable;

public class Ammo implements Serializable {
    private Color color;

    public Ammo(Color color){
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String toString(){
        StringBuilder ammo = new StringBuilder();
        ammo.append("\n\033[0;34m _ _ ");
        ammo.append("\n| " + Converter.fromColorToLetter(this.color) + " | ");
        ammo.append("\n ¯ ¯ \033[0m");
        return ammo.toString();
    }
}
