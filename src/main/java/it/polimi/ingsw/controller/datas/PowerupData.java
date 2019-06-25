package it.polimi.ingsw.controller.datas;

import it.polimi.ingsw.model.enums.Color;

import java.io.Serializable;

/**
 * This class contains datas about powerups, using during the choice of spawn.
 */
public class PowerupData implements Serializable {
    /**
     * The name of the powerup.
     */
    private String name;
    /**
     * The color of the powerup.
     */
    private Color color;

    /**
     * Class constructor.
     * @param name the name of the powerup.
     * @param color the color of the powerup.
     */
    public PowerupData(String name, Color color){
        this.name = name;
        this.color = color;
    }

    /**
     * Getter for the name.
     * @return the name of the powerup.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name.
     * @param name the name that will be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the color.
     * @return the color of the powerup.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for the color.
     * @param color the color that will be set.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
