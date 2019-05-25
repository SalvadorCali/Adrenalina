package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enums.Color;

import java.io.Serializable;

public abstract class Card implements Serializable {
    private String name;
    private Color color;
    private String effect;

    public Card(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public Card(String name, Color color, String effect){
        this.name = name;
        this.color = color;
        this.effect = effect;
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

    public String toString(){
        StringBuilder card = new StringBuilder();
        card.append("Name: " + this.name).append("\nColor: " + this.color).append("\n" + this.effect);
        return card.toString();
    }
}
