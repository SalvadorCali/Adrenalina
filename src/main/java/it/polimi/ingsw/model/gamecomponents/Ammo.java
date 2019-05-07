package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.util.Converter;

public class Ammo {
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
        ammo.append(" _ _ ");
        ammo.append("\n| " + Converter.fromColorToLetter(this.color) + " | ");
        ammo.append("\n ¯ ¯ ");
        return ammo.toString();
    }
}
