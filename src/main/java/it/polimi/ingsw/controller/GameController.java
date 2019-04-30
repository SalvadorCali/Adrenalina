package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Deck;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Parser;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public int timerTurn;
    public int numPlayers;

    Deck weapons;
    Deck powerups;
    List<AmmoCard> ammoCards;
    List<GameBoard> gameBoards;
    private Game game;
    private ActionInterface actionInterface;

    public GameController(){
        weapons = Parser.createWeapons();
        powerups = Parser.createPowerups();
        ammoCards = Parser.createAmmos();
        gameBoards = Parser.createGameBoards();
        /*
        int gameBoardIndex = (int)(Math.random() * 4);
        game = new Game(gameBoards.get(gameBoardIndex), weapons, powerups, ammoCards);
        */
        game = new Game(gameBoards.get(0), weapons, powerups, ammoCards);
        actionInterface = new ActionController(game);
    }

    //getters and setters
    public Game getGame() {
        return game;
    }

    //methods
    public void startGame(List<Player> players){
        game.setPlayers(players);
        game.setInGame(true);
        game.createKillshotTrack(8);
        game.fillSquares(actionInterface);
        game.createScoreList();
    }

    public void setInGame(boolean inGame){
        game.setInGame(inGame);
    }

    public boolean isInGame(){
        return game.isInGame();
    }

    public void setColorSelection(boolean colorSelection){
        game.setColorSelection(colorSelection);
    }

    public boolean isColorSelection(){
        return game.isColorSelection();
    }

    public void addPlayer(Player player){
        game.addPlayer(player);
    }

    public boolean canMove(Player player, Direction...directions){
        return game.getBoard().canMove(player, directions);
    }

    public ArrayList<TokenColor> getPlayerColors() {
        return game.getPlayerColors();
    }

    public void move(Player player, Direction...directions){
        //game.getBoard().move();
    }

    public void grab(Player player, int choice, Direction...directions){
        if(directions.length > 0){
            if(canMove(player, directions)){
                move(player, directions);
            }
        }
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        game.getBoard().getArena()[x][y].grab(actionInterface, choice);
    }

    public void shoot(){

    }

    public void endTurn(Player player){
        game.endTurn(player);
    }
}
