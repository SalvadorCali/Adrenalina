package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<>();
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public Card draw(){
        return cards.remove(cards.size() - 1);
    }
}
