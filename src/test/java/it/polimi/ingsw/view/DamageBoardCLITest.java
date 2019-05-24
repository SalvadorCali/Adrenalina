package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.PlayerBoard;
import org.junit.jupiter.api.Test;


public class DamageBoardCLITest {

    @Test
    public void tryPrintDmgBoard(){
        PlayerBoard playerBoard = new PlayerBoard();
        Player player1 = new Player(TokenColor.BLUE);
        Player player2 = new Player(TokenColor.RED);
        Player player3 = new Player(TokenColor.YELLOW);
        String name = "gianni";
        //Token [] damageBoard = new Token[12];

        playerBoard.addDamage(player1.getColor(), player1.getColor(), player3.getColor(), player2.getColor(), player2.getColor(), player1.getColor());
        //damageBoard = playerBoard.getDamageBoard();
        player1.setPlayerBoard(playerBoard);
        player1.setUsername(name);

        playerBoard.addRevengeMarks(player1.getColor());
        playerBoard.addRevengeMarks(player3.getColor());
        playerBoard.addRevengeMarks(player2.getColor());

        DamageBoardCLI damageBoardCLI = new DamageBoardCLI(player1);

        damageBoardCLI.printDamageBoard();

        DamageBoardCLI damageBoardCLI1 = new DamageBoardCLI(player2);
        damageBoardCLI1.printDamageBoard();
    }
}
