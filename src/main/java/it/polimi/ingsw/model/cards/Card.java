package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enums.Color;

import java.io.Serializable;

/**
 * Abstract class representing the cards of the game.
 */
public abstract class Card implements Serializable {
    private String name;
    private Color color;
    private String effect;

    /**
     * Constructor of the card.
     * @param name name of the card.
     * @param color color of the card.
     */
    public Card(String name, Color color){
        this.name = name;
        this.color = color;
    }

    /**
     * Constructor of the card.
     * @param name name of the card.
     * @param color color of the card.
     * @param effect effect contained in the card.
     */
    public Card(String name, Color color, String effect){
        this.name = name;
        this.color = color;
        this.effect = effect;
    }

    /**
     * Getter of the name of the card.
     * @return the name of the card.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name of the card.
     * @param name chosen name of the card.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the color of the card.
     * @return the color of the card.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter of the color of the card.
     * @param color color of the card.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * getter of the effect of the card.
     * @return the effect of the card.
     */
    String getCardEffect() {
        return effect;
    }

    /*
    public String toString(){
        StringBuilder card = new StringBuilder();
        card.append("Name: " + this.name).append("\nColor: " + this.color).append("\n" + this.effect);
        return card.toString();
    }
     */
}
