package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class which represents the decks of cards present in the game.
 */
public class Deck implements Serializable {

    /**
     * List of cards present in the deck.
     */
    private List<Card> cards = new ArrayList<>();
    /**
     * List of cards to add to the deck when it turns empty.
     */
    private List<Card> reserve = new ArrayList<>();

    /**
     * Shuffles the cards present in the deck.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the deck.
     * @return the drawn card.
     */
    public Card draw(){
        Card card = cards.remove(cards.size()-1);
        reserve.add(card);
        if(cards.isEmpty()){
            cards.addAll(reserve);
            shuffle();
        }
        return card;
    }

    /**
     * Adds a card to the deck.
     * @param card indicates the chosen card to add to the deck.
     */
    public void addCard(Card card){
        cards.add(card);
    }

    /**
     * Returns the size of the deck.
     * @return the size of the deck
     */
    public int size(){
        return cards.size();
    }

    /**
     * Getter of a card in a position of the deck.
     * @param i indicates the chosen position.
     * @return the card at the chosen position of the deck.
     */
    public Card get(int i){
        return cards.get(i);
    }
}
