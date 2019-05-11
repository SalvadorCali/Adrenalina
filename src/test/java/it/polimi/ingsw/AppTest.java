package it.polimi.ingsw;


import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.AmmoBoxReserveCLI;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
        Player player = new Player(TokenColor.GREY);
        player.getAmmoReserve().add(new Ammo(Color.BLUE));
        player.getAmmoReserve().add(new Ammo(Color.BLUE));
        player.getAmmoReserve().add(new Ammo(Color.BLUE));
        player.getAmmoReserve().add(new Ammo(Color.RED));
        player.getAmmoReserve().add(new Ammo(Color.RED));
        player.getAmmoReserve().add(new Ammo(Color.RED));
        player.getAmmoReserve().add(new Ammo(Color.YELLOW));
        player.getAmmoReserve().add(new Ammo(Color.YELLOW));
        player.getAmmoReserve().add(new Ammo(Color.YELLOW));
        player.addAmmo(new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.RED));
        player.getAmmoReserve().forEach(a -> Printer.println(a.getColor()));
        player.getAmmoBox().forEach(a -> Printer.println(a.getColor()));
        AmmoBoxReserveCLI ammos = new AmmoBoxReserveCLI(player);
        //ammos.printAmmoBox();
        //ammos.printAmmoReserve();
    }

}
