package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.Card;

import java.util.List;

public class SpawnPoint extends Square {

    private List<Card> weapons;

    public SpawnPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
    }

    @Override
    public void grab(Player player, int choice) {

    }

    public List<Card> getWeapons() {
        return weapons;
    }

    @Override
    public Color getColor() {
        return super.getColor();
    }

    public void spawn(Player player){
        //to implement
   }
}
