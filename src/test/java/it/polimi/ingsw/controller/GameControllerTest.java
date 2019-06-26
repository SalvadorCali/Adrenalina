package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.cli.MapCLI;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private List<Player> playerList = new ArrayList<>();

    /**
     * Tests that players, ammo and gameboard are correctly setted at the beginning of a game.
     */
    @Test
    void startGameTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        for(int i = 0 ; i < gameController.getGame().getPlayers().size(); i++){
            assertEquals(3,gameController.getGame().getPlayers().get(i).getAmmoBox().size());
            assertEquals(6,gameController.getGame().getPlayers().get(i).getAmmoReserve().size());
            assertEquals(playerList.get(i).getUsername(), gameController.getGame().getPlayers().get(i).getUsername());
        }
        assertNotNull(gameController.getGame().getBoard());
        assertNotNull(gameController.getGame().getScoreList());
    }

    /**
     * Tests that the board and the killshot tracks are correctly setted when the first player makes his choice.
     */
    @Test
    void setBoardTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.setBoard(1, 5);
        assertEquals(BoardType.BASIC, gameController.getGame().getBoard().getType());
        assertEquals(5, gameController.getGame().getKillshotTrack().size());
    }

    /**
     * Tests that the player can move only 2 times in a normal turn, and 1 time in a final frenzy turn.
     */
    @Test
    void moveTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().setFinalFrenzy(false);
        gameController.getGame().getCurrentPlayer().resetActionNumber();
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().generatePlayer(0,0, gameController.getGame().getCurrentPlayer());
        mapCLI.printMap();
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT,Direction.RIGHT, Direction.DOWN));
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.RIGHT));
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT));
        gameController.move(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.RIGHT, Direction.DOWN);
        mapCLI.printMap();
        assertTrue(gameController.canMove(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.DOWN));
        gameController.move(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.DOWN);
        mapCLI.printMap();
        assertFalse(gameController.move(gameController.getGame().getCurrentPlayer(), Direction.LEFT));
        gameController.getGame().setFinalFrenzy(true);
        assertFalse(gameController.move(gameController.getGame().getCurrentPlayer(),Direction.UP));
    }

    /**
     * Tests the movement of a player in a different square of the board.
     */
    @Test
    void moveInSquareTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().setFinalFrenzy(false);
        gameController.getGame().getCurrentPlayer().resetActionNumber();
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        gameController.getGame().getBoard().generatePlayer(0,0, gameController.getGame().getCurrentPlayer());
        mapCLI.printMap();
        gameController.move(gameController.getGame().getCurrentPlayer(), 0, 1);
    }

    /**
     * Tests the shoot action, and the inverse move and reload.
     */
    @Test
    void shootTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().setFinalFrenzy(false);
        gameController.getGame().getCurrentPlayer().resetActionNumber();
        gameController.getGame().getBoard().generatePlayer(0,0, gameController.getGame().getCurrentPlayer());
        gameController.getGame().getBoard().generatePlayer(0,1, gameController.getGame().getPlayers().get(1));
        assertFalse(gameController.shoot("lockrifle",1,true,gameController.getGame().getCurrentPlayer(),null,null,null,-1,-1));
        WeaponCard lockrifle = new WeaponCard("LOCK RIFLE", Color.BLUE, null, 0,0,0,0,0,0);
        gameController.getGame().getCurrentPlayer().addWeapon(lockrifle);
        lockrifle.unload();
        assertFalse(gameController.shoot("lockrifle",1,true,gameController.getGame().getCurrentPlayer(),null,null,null,-1,-1));
        lockrifle.load();
        assertFalse(gameController.shoot("lockrifle",0,true,gameController.getGame().getCurrentPlayer(),null,null,null,-1,-1));
        assertTrue(gameController.shoot("lockrifle",0,true, gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayers().get(1),null,null,-1,-1));
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertFalse(lockrifle.isLoaded());

        gameController.getGame().getCurrentPlayer().setMoveAndReload(true);
        gameController.moveAndReload(gameController.getGame().getCurrentPlayer(),Direction.RIGHT, "lockrifle");
        gameController.shoot("lockrifle",1,true,gameController.getGame().getCurrentPlayer(),null,null,null,-1,-1);
        mapCLI.printMap();
        gameController.moveAndReload(gameController.getGame().getCurrentPlayer(),Direction.RIGHT, "lockrifle");
        gameController.shoot("lockrifle",0,true,gameController.getGame().getCurrentPlayer(),gameController.getGame().getPlayers().get(1),null,null,-1,-1);
        mapCLI.printMap();
        assertFalse(lockrifle.isLoaded());

        gameController.getGame().getCurrentPlayer().resetActionNumber();
        lockrifle.load();
        gameController.moveAndReload(gameController.getGame().getCurrentPlayer(),Direction.RIGHT, Direction.DOWN);
        gameController.shoot("lockrifle",1,true,gameController.getGame().getCurrentPlayer(),gameController.getGame().getPlayers().get(1),null,null,-1,-1);
        mapCLI.printMap();

        gameController.moveAndReload(gameController.getGame().getCurrentPlayer(),Direction.RIGHT, Direction.DOWN);
        gameController.shoot("lockrifle",0,true,gameController.getGame().getCurrentPlayer(),gameController.getGame().getPlayers().get(1),null,null,-1,-1);
        mapCLI.printMap();
    }

    /**
     * Tests the move and reload action.
     */
    @Test
    void moveAndReloadTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().setFinalFrenzy(false);
        gameController.getGame().getCurrentPlayer().resetActionNumber();
        WeaponCard lockrifle = new WeaponCard("LOCK RIFLE", Color.BLUE, null, 0,0,0,0,0,0);
        gameController.getGame().getCurrentPlayer().addWeapon(lockrifle);
        gameController.getGame().getBoard().generatePlayer(0,0, gameController.getGame().getCurrentPlayer());
        gameController.getGame().getBoard().generatePlayer(0,1, gameController.getGame().getPlayers().get(1));
        assertFalse(gameController.canMoveAndReload(gameController.getGame().getCurrentPlayer(), Direction.UP, "lockrifle"));
        lockrifle.unload();
        assertTrue(gameController.canMoveAndReload(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, "lockrifle"));
        lockrifle.unload();
        assertFalse(gameController.canMoveAndReload(gameController.getGame().getCurrentPlayer(), Direction.RIGHT,Direction.UP, "lockrifle"));
        assertTrue(gameController.canMoveAndReload(gameController.getGame().getCurrentPlayer(), Direction.RIGHT, Direction.DOWN, "lockrifle"));
    }

    /**
     * Tests the canReload and reload methods.
     */
    @Test
    void reloadTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().setFinalFrenzy(false);
        gameController.getGame().getCurrentPlayer().resetActionNumber();
        assertFalse(gameController.reload(gameController.getGame().getCurrentPlayer(), "lockrifle"));
        WeaponCard lockrifle = new WeaponCard("LOCK RIFLE", Color.BLUE, null, 0,0,0,3,0,0);
        gameController.getGame().getCurrentPlayer().addWeapon(lockrifle);
        lockrifle.unload();
        assertFalse(gameController.reload(gameController.getGame().getCurrentPlayer(), "lockrifle"));
    }

    /**
     * Tests the canRespawn control and the respawn of a player.
     */
    @Test
    void respawnTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().getBoard().generatePlayer(0,0,gameController.getGame().getCurrentPlayer());
        PowerupCard p = new PowerupCard("TELEPORTER",Color.YELLOW, "Teleporter");
        gameController.getGame().getCurrentPlayer().addPowerup(p);
        MapCLI mapCLI = new MapCLI(gameController.getGame().getBoard());
        mapCLI.printMap();
        assertFalse(gameController.canRespawn(gameController.getGame().getCurrentPlayer(), 2));
        assertTrue(gameController.canRespawn(gameController.getGame().getCurrentPlayer(), 1));
        gameController.respawn(gameController.getGame().getCurrentPlayer(),1);
        assertEquals(2 , gameController.getGame().getCurrentPlayer().getPosition().getX());
        assertEquals(3, gameController.getGame().getCurrentPlayer().getPosition().getY());
        assertTrue(gameController.getGame().getCurrentPlayer().isRespawned());
        mapCLI.printMap();
    }

    /**
     * Tests that the player datas are correct
     */
    @Test
    void discardPowerupTest(){
        GameController gameController = new GameController();
        playerSetup();
        gameController.startGame(playerList);
        gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayers().get(0));
        gameController.setBoard(1, 5);
        gameController.getGame().getBoard().generatePlayer(0,0,gameController.getGame().getCurrentPlayer());
        PowerupCard p = new PowerupCard("TELEPORTER",Color.YELLOW, "Teleporter");
        gameController.getGame().getCurrentPlayer().addPowerup(p);
        assertFalse(gameController.canDiscardPowerup(gameController.getGame().getCurrentPlayer(),0));
        assertFalse(gameController.canDiscardPowerup(gameController.getGame().getCurrentPlayer(),2));
        assertTrue(gameController.canDiscardPowerup(gameController.getGame().getCurrentPlayer(),1));
        assertEquals(1 ,gameController.getGame().getCurrentPlayer().getPowerups().size());
        gameController.discardPowerup(gameController.getGame().getCurrentPlayer(), 1);
        assertEquals(0 ,gameController.getGame().getCurrentPlayer().getPowerups().size());
        assertTrue(gameController.getGame().getCurrentPlayer().canUsePowerupAmmos());
    }


    private void playerSetup(){
        Player player1 = new Player(TokenColor.BLUE);
        player1.setUsername("A");
        Player player2 = new Player(TokenColor.GREEN);
        player2.setUsername("B");
        Player player3 = new Player(TokenColor.YELLOW);
        player3.setUsername("C");
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
    }
}
