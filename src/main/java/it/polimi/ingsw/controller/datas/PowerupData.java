package it.polimi.ingsw.controller.datas;

import it.polimi.ingsw.model.enums.Color;

import java.io.Serializable;

public class PowerupData implements Serializable {
    private String name;
    private Color color;

    public PowerupData(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
