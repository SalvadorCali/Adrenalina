package it.polimi.ingsw;


import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.GameBoard;
import it.polimi.ingsw.model.Square;
import it.polimi.ingsw.util.Parser;
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

    /*
    @Test
    public void test(){
        List<GameBoard> boards = Parser.createGameBoards();

        for(int i = 0; i<4; i++){
            System.out.println(boards.get(i).getType());
            for(int j = 0; j<3; j++){
                for(int k=0; k<4; k++){
                    System.out.println(boards.get(i).getArena()[j][k].getColor());
                }
            }
        }

    }
    */
}
