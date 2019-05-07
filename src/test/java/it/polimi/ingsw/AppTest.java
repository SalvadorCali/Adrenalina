package it.polimi.ingsw;


import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;
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
        Ammo ammo = new Ammo(Color.BLUE);
        Ammo ammo2 = new Ammo(Color.RED);
        Printer.print(ammo);
        Printer.print(ammo2);
    }

}
