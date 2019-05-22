package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Deck;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;

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
        game.giveAmmos();
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

    public boolean canMove(int x, int y){
        return game.getBoard().canMove(x,y);
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

    public void move(Player player, int x, int y){
        game.getBoard().move(x, y, player);
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
            Printer.println("ccccj");
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
            Printer.println("porva");
            game.getBoard().getArena()[x][y].getWeapons().forEach(w-> Printer.println(w.getName()));
            Printer.println("porva2");
            if(game.getBoard().getArena()[x][y].canGrab(choice)){
                Printer.println("dentro if");
                game.getBoard().getArena()[x][y].grab(actionInterface, choice);
                player.increaseActionNumber();
                return true;
            }else{
                return false;
            }
        }else{
            Printer.println("cccc");
            return false;
        }
    }

    public void shoot(Player shooter, String weaponName, int mod, Player...victims){

        final String weaponNameUpp;
        weaponNameUpp = Converter.weaponName(weaponName);
        shooter.getWeapons().forEach(w -> {
            if (w.getName().equals(weaponNameUpp)) {
                setData(shooter, victims);
                if (w.getEffects().get(mod).canUseEffect(actionInterface)) {
                    Printer.println(weaponNameUpp + "USED");
                    w.getEffects().get(mod).useEffect(actionInterface);
                }else
                    Printer.println(weaponNameUpp + "NOT USED");
            }
        });

/*
        shooter.getWeapons().forEach(w -> {
            if(w.getName().equals("LOCK RIFLE")) {
                actionInterface.getClientData().setCurrentPlayer(shooter);
                if (victims.length == 1) {
                    actionInterface.getClientData().setVictim(victims[0]);
                    if (w.getEffects().get(0).canUseEffect(actionInterface)) {
                        Printer.println("LOCK RIFLE USED");
                        w.getEffects().get(0).useEffect(actionInterface);
                    } else {
                        Printer.println("LOCK RIFLE NOT USED");
                    }
                } else {
                    actionInterface.getClientData().setVictim(victims[0]);
                    actionInterface.getClientData().setSecondVictim(victims[1]);
                    if (w.getEffects().get(1).canUseEffect(actionInterface))
                        w.getEffects().get(1).useEffect(actionInterface);

                }
            } else if(w.getName().equals("ELECTROSCYTHE")){
                actionInterface.getClientData().setCurrentPlayer(shooter);
                if(mod == 1){
                    if(w.getEffects().get(0).canUseEffect(actionInterface)){
                        Printer.println("ELECTROSCYTHE1 USED");
                        w.getEffects().get(1).useEffect(actionInterface);
                    }else{
                        Printer.println("ELECTROSCYTHE1 NOT USED");
                    }
                }else{
                    if(w.getEffects().get(1).canUseEffect(actionInterface)){
                        Printer.println("ELECTROSCYTHE2 USED");
                        w.getEffects().get(1).useEffect(actionInterface);
                    }else{
                        Printer.println("ELECTROSCYTHE2 NOT USED");
                    }
                }
            }else if(w.getName().equals("MACHINE GUN")){




            }
        });
        Printer.println("eskereeeeee2");

 */
    }

    private void setData(Player shooter, Player...victims) {

        getActionInterface().getClientData().setCurrentPlayer(shooter);

        switch (victims.length){
            case 1:
                getActionInterface().getClientData().setVictim(victims[0]);
                getActionInterface().getClientData().setSecondVictim(null);
                getActionInterface().getClientData().setThirdVictim(null);
                break;
            case 2:
                getActionInterface().getClientData().setVictim(victims[0]);
                getActionInterface().getClientData().setSecondVictim(victims[1]);
                getActionInterface().getClientData().setThirdVictim(null);
            case 3:
                getActionInterface().getClientData().setVictim(victims[0]);
                getActionInterface().getClientData().setSecondVictim(victims[1]);
                getActionInterface().getClientData().setThirdVictim(victims[2]);
        }
    }

    public void shoot(Player shooter, String weaponName, int mod, Player victim, int x, int y){
        final String weaponNameUpp;
        weaponNameUpp = Converter.weaponName(weaponName);
        shooter.getWeapons().forEach(w -> {
            if (w.getName().equals(weaponNameUpp)) {
                setData(shooter, victim, x, y);
                if (w.getEffects().get(mod).canUseEffect(actionInterface)) {
                    Printer.println(weaponNameUpp + "USED");
                    w.getEffects().get(mod).useEffect(actionInterface);
                }else
                    Printer.println(weaponNameUpp + "NOT USED");
            }
        });

    }

    private void setData(Player shooter, Player victim, int x, int y) {

        actionInterface.getClientData().setCurrentPlayer(shooter);
        if(victim!=null)
            actionInterface.getClientData().setVictim(victim);
        else
            actionInterface.getClientData().setVictim(null);
        actionInterface.getClientData().setSquare(x, y);
    }

    public void shoot(Player shooter, String weaponName, int mod, Player victim, Direction...directions) {
        final String weaponNameUpp;
        weaponNameUpp = Converter.weaponName(weaponName);
        shooter.getWeapons().forEach(w -> {
            if (w.getName().equals(weaponNameUpp)) {
                setData(shooter, victim, directions);
                if (w.getEffects().get(mod).canUseEffect(actionInterface)) {
                    Printer.println(weaponNameUpp + "USED");
                    w.getEffects().get(mod).useEffect(actionInterface);
                } else
                    Printer.println(weaponNameUpp + "NOT USED");
            }
        });
    }

    private void setData(Player shooter, Player victim, Direction... directions) {
        actionInterface.getClientData().setCurrentPlayer(shooter);
        actionInterface.getClientData().setVictim(victim);
        actionInterface.getClientData().setFirstMove(directions[0]);
        if(directions.length > 1)
            actionInterface.getClientData().setSecondMove(directions[1]);
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

    public boolean havePowerup(Player player, String powerup){
        String powerupName = Converter.powerupName(powerup);
        List<PowerupCard> powerupCards = player.getPowerups();
        for(int i=0; i<powerupCards.size(); i++){
            if(powerupCards.get(i).getName().equals(powerupName)){
                return true;
            }
        }
        return false;
    }

    public Card drawPowerup(){
        return game.drawPowerup();
    }

    public void setPlayer(Player player, Color color){
        game.getBoard().setPlayer(player, color);
    }


}
