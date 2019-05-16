package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Color;
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

    public ActionInterface getActionInterface() {
        return actionInterface;
    }

    //methods
    public void startGame(List<Player> players){
        game.setPlayers(players);
        //game.setGamePhase(true);
        game.createKillshotTrack(8);
        game.fillSquares(actionInterface);
        game.createScoreList();
    }

    public void setGamePhase(boolean gamePhase){
        game.setGamePhase(gamePhase);
    }

    public boolean isGamePhase(){
        return game.isGamePhase();
    }

    public void setSpawnLocationPhase(boolean spawnLocationPhase){
        game.setSpawnLocationPhase(spawnLocationPhase);
    }

    public boolean isSpawnLocationPhase(){
        return game.isSpawnLocationPhase();
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
        if(player.canUseAction()){
            for(Direction direction : directions){
                game.getBoard().move(direction, player);
            }
            player.increaseActionNumber();
        }
    }

    public SquareData showSquare(Player player){
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        SquareData squareData = new SquareData();
        squareData.setAmmoCard(game.getBoard().getArena()[x][y].getAmmoCard());
        squareData.setWeapons(game.getBoard().getArena()[x][y].getWeapons());
        return squareData;
    }

    public boolean grab(Player player, int choice, Direction...directions){
        if(player.canUseAction()){
            if(directions.length > 0){
                if(canMove(player, directions)){
                    move(player, directions);
                }
                else{
                    return false;
                }
            }
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();
            if(game.getBoard().getArena()[x][y].canGrab(choice)){
                game.getBoard().getArena()[x][y].grab(actionInterface, choice);
                player.increaseActionNumber();
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void shoot(Player player, Player victim){
        if(player.canUseAction()){
            actionInterface.getClientData().setVictim(victim);
            //player.getWeapons()...
            player.increaseActionNumber();
        }

    }

    public void endTurn(Player player){
        player.resetActionNumber();
        game.endTurn(player);
    }

    public Card drawPowerup(){
        return game.drawPowerup();
    }

    public void setPlayer(Player player, Color color){
        game.getBoard().setPlayer(player, color);
    }
}
