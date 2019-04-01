package it.polimi.ingsw.model;

import java.util.List;

public class Player {
    private TokenColor color;
    private int score;
    private List<Card> weapons;
    private List<Ammo> ammoBox;
    private List<Ammo> ammoReserve;
    private PlayerBoard playerBoard;
    private Position position;

    public TokenColor getColor() {
        return color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
