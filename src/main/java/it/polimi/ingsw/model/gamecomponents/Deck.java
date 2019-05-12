package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
    private List<Card> cards = new ArrayList<>();

    //methods
    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card draw(){
        return cards.remove(cards.size() - 1);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public int size(){
        return cards.size();
    }

    public Card get(int i){
        return cards.get(i);
    }
}
