package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.controller.ActionController;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;



public class SpawnPointTest {
    @Test
    public void grabTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        Player player = new Player(TokenColor.YELLOW);
        game.setCurrentPlayer(player);
        Deck weaponCards = Parser.createWeapons();
        List<WeaponCard> weapons = new ArrayList<>();
        weapons.add((WeaponCard)weaponCards.get(0));
        weapons.add((WeaponCard)weaponCards.get(1));
        weapons.add((WeaponCard)weaponCards.get(2));
        WeaponCard weaponCard = weapons.get(1);
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE, Cardinal.NONE);
        square.setWeapons(weapons);
        square.grab(actionInterface, 2);

        assertEquals(weaponCard, player.getWeapons().get(0));
    }

    @Test
    public void fillTest(){
        Game game = new Game(Parser.createGameBoards().get(0), Parser.createWeapons(), Parser.createPowerups(), Parser.createAmmos());
        ActionInterface actionInterface = new ActionController(game);
        SpawnPoint square = new SpawnPoint(TokenColor.BLUE, Cardinal.DOOR, Cardinal.ROOM, Cardinal.WALL, Cardinal.DOOR);
        assertEquals(0, square.getWeapons().size());

        square.fill(actionInterface);
        assertEquals(3, square.getWeapons().size());
    }
}
