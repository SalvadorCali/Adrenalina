package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    public void shuffleTest(){
        Deck weapons = Parser.createWeapons();
        Deck shuffledWeapons = Parser.createWeapons();
        shuffledWeapons.shuffle();
        assertEquals(weapons.size(), shuffledWeapons.size());
    }

    @Test
    public void drawTest(){
        Deck weapons = Parser.createWeapons();
        int firstSize = weapons.size();
        Card card = weapons.draw();
        assertNotEquals(weapons.size(), firstSize);
        assertEquals(weapons.size(), firstSize - 1);
        assertNotEquals(weapons.size(), firstSize - 2);
    }

    @Test
    public void addCardAndSizeAndGetTest(){
        Deck weapons = Parser.createWeapons();
        int firstSize = weapons.size();
        Card firstCard = new WeaponCard("test", Color.BLUE);
        weapons.addCard(firstCard);
        assertNotEquals(weapons.size(), firstSize);
        assertEquals(weapons.size(), firstSize + 1);

        Card secondCard = weapons.get(weapons.size() - 1);
        assertEquals(firstCard, secondCard);
        assertEquals(weapons.size(), firstSize + 1);

    }
}
