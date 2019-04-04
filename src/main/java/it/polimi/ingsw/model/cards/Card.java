package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.gamecomponents.Color;

public abstract class Card {
    private String name;
    private Color color;

    public Card(String name, Color color){
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
