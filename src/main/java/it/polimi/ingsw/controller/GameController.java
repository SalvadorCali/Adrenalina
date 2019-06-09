package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.*;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameController {

    public int timerTurn;
    public int numPlayers;
    Deck weapons;
    Deck powerups;
    List<AmmoCard> ammoCards;
    List<GameBoard> gameBoards;
    private Game game;
    private ActionInterface actionInterface;

    public GameController() {
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

    public GameController(int board) {
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
    public void startGame(List<Player> players) {
        game.setPlayers(players);
        //game.setGamePhase(true);
        game.giveAmmos();
        //game.createKillshotTrack(8);
        game.fillSquares(actionInterface);
        game.createScoreList();
    }

    public void startGame(List<Player> players, int board, int skulls) {
        game.setPlayers(players);
        game.giveAmmos();
        game.createKillshotTrack(skulls);
        game.setBoard(gameBoards.get(board - 1));
        game.fillSquares(actionInterface);
        game.createScoreList();
    }

    public void setGamePhase(boolean gamePhase) {
        game.setGamePhase(gamePhase);
    }

    public boolean isGamePhase() {
        return game.isGamePhase();
    }

    public void setSpawnLocationPhase(boolean spawnLocationPhase) {
        game.setSpawnLocationPhase(spawnLocationPhase);
    }

    public boolean isSpawnLocationPhase() {
        return game.isSpawnLocationPhase();
    }

    public void setBoardTypePhase(boolean boardTypePhase){
        game.setBoardTypePhase(boardTypePhase);
    }

    public boolean isBoardTypePhase(){
        return game.isBoardTypePhase();
    }

    public void setColorSelection(boolean colorSelection) {
        game.setColorSelection(colorSelection);
    }

    public boolean isColorSelection() {
        return game.isColorSelection();
    }

    public void addPlayer(Player player) {
        game.addPlayer(player);
    }

    public void setBoard(int boardType, int skulls){
        game.setBoard(gameBoards.get(boardType - 1));
        game.createKillshotTrack(skulls);
    }

    public boolean canMove(Player player, Direction... directions) {
        return game.getBoard().canMove(player, directions);
    }


    public boolean canMove(int x, int y) {
        return game.getBoard().canMove(x, y);
    }


    public ArrayList<TokenColor> getPlayerColors() {
        return game.getPlayerColors();
    }

    public boolean move(Player player, Direction... directions) {
        if(game.isFinalFrenzy()){
            if(player.canUseActionFinalFrenzy()){
                for (Direction direction : directions) {
                    game.getBoard().move(direction, player);
                }
                player.increaseActionNumber();
                return true;
            }
            return false;
        }else{
            if (player.canUseAction()) {
                for (Direction direction : directions) {
                    game.getBoard().move(direction, player);
                }
                player.increaseActionNumber();
                return true;
            }
            return false;
        }
    }

    public void move(Player player, int x, int y) {
        game.getBoard().move(x, y, player);
    }

    public boolean moveAndReload(Player player, Direction firstDirection, Direction secondDirection, String...weapons){
        boolean hasWeapon = false;
        boolean hasAmmo = false;
        boolean reloadResult = true;
        boolean reload[] = new boolean[weapons.length];
        if(canMove(player, firstDirection, secondDirection)){
            for(int i=0; i<weapons.length; i++){
                String weaponNameUpp;
                weaponNameUpp = Converter.weaponName(weapons[i]);
                for(WeaponCard w : player.getWeapons()){
                    if(w.getName().equals(weaponNameUpp)){
                        hasWeapon = true;
                        if(w.reloadAmmoControl(player)) {
                            hasAmmo = true;
                        }
                    }
                    if(hasWeapon && hasAmmo){
                        reload[i] = true;
                        hasWeapon = false;
                        hasAmmo = false;
                        break;
                    }
                }
            }
            for(int i=0; i<weapons.length; i++){
                reloadResult = reloadResult && reload[i];
            }
            if(reloadResult){

            }
        }else{
            return false;
        }
return false;
    }

    public SquareData showSquare(Player player) {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        SquareData squareData = new SquareData();
        squareData.setAmmoCard(game.getBoard().getArena()[x][y].getAmmoCard());
        squareData.setWeapons(game.getBoard().getArena()[x][y].getWeapons());
        return squareData;
    }

    public SquareData showSquare(Player player, int x, int y) {
        SquareData squareData = new SquareData();
        squareData.setAmmoCard(game.getBoard().getArena()[x][y].getAmmoCard());
        squareData.setWeapons(game.getBoard().getArena()[x][y].getWeapons());
        return squareData;
    }

    public boolean reload(Player player, String weaponName) {
        final String weaponNameUpp;
        weaponNameUpp = Converter.weaponName(weaponName);
        for(WeaponCard w : player.getWeapons()){
            if(w.getName().equals(weaponNameUpp)){
                if(w.reloadAmmoControl(player)) {
                    Printer.println(weaponNameUpp + " RELOADED");
                    player.updateAmmoBox(w.getReloadRedAmmos(), w.getReloadBlueAmmos(),w.getReloadYellowAmmos());
                    w.load();
                    return true;
                }else{
                    Printer.println(weaponNameUpp + " NOT RELOADED");
                    return false;
                }
            }
        }
        return false;
    }

    public boolean grab(Player player, int choice, Direction... directions) {
        if(game.isFinalFrenzy()){
            if(player.canUseActionFinalFrenzy()){
                if (directions.length > 0) {
                    if (canMove(player, directions)) {
                        move(player, directions);
                    } else {
                        return false;
                    }
                }
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();
                if (game.getBoard().getArena()[x][y].canGrab(actionInterface, choice)) {
                    game.getBoard().getArena()[x][y].grab(actionInterface, choice);
                    player.increaseActionNumber();
                    return true;
                } else {
                    return false;
                }
            }
        }else{
            if (player.canUseAction()) {
                if (directions.length > 0) {
                    if (canMove(player, directions)) {
                        move(player, directions);
                    } else {
                        return false;
                    }
                }
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();
                if (game.getBoard().getArena()[x][y].canGrab(actionInterface, choice)) {
                    game.getBoard().getArena()[x][y].grab(actionInterface, choice);
                    player.increaseActionNumber();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public void drop(Player player, String weapon){
        final String weaponNameUpp;
        weaponNameUpp = Converter.weaponName(weapon);
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        for(int i=0; i<player.getWeapons().size(); i++){
            if(player.getWeapons().get(i).getName().equals(weaponNameUpp)){
                game.getBoard().getArena()[x][y].drop(player.getWeapons().get(i));
                player.getWeapons().remove(i);
            }
        }
    }

    public boolean shoot(String weaponName, int mod, boolean basicfirst, Player shooter, Player firstVictim, Player secondVictim, Player thirdVictim, int x, int y, Direction...directions) {

        final String weaponNameUpp;
        weaponNameUpp = Converter.weaponName(weaponName);
        for (WeaponCard w : shooter.getWeapons()) {
            if (w.getName().equals(weaponNameUpp) && w.isLoaded() && shooter.canUseAction()) {
                setData(basicfirst, shooter, firstVictim, secondVictim, thirdVictim, x, y, directions);
                if (w.getEffects().get(mod).canUseEffect(actionInterface)) {
                    Printer.println(weaponNameUpp + "USED");
                    w.getEffects().get(mod).useEffect(actionInterface);
                    w.unload();
                    shooter.increaseActionNumber();
                    return true;
                } else {
                    Printer.println(weaponNameUpp + "NOT USED");
                    return false;
                }
            }
        }
        return false;
    } //metodo completo

    private void setData(boolean basicfirst, Player shooter, Player firstVictim, Player secondVictim, Player thirdVictim, int x, int y, Direction...directions) {

        actionInterface.getClientData().setBasicFirst(basicfirst);
        actionInterface.getClientData().setCurrentPlayer(shooter);
        actionInterface.getClientData().setVictim(firstVictim);
        actionInterface.getClientData().setSecondVictim(secondVictim);
        actionInterface.getClientData().setThirdVictim(thirdVictim);
        switch (directions.length) {
            case 1:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                break;
            case 2:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                getActionInterface().getClientData().setSecondMove(directions[1]);
                break;
            case 3:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                getActionInterface().getClientData().setSecondMove(directions[1]);
                getActionInterface().getClientData().setThirdMove(directions[2]);
                break;
            case 4:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                getActionInterface().getClientData().setSecondMove(directions[1]);
                getActionInterface().getClientData().setThirdMove(directions[2]);
                getActionInterface().getClientData().setFourthMove(directions[3]);
                break;
            default:
                break;
        }
        actionInterface.getClientData().setSquare(x, y);
    }

    public boolean usePowerup(String powerupName, Player shooter, Player victim, Color ammo, int x, int y, Direction...directions){

        final String powerupNameUpp;
        powerupNameUpp = Converter.powerupName(powerupName);
        for (PowerupCard p : shooter.getPowerups()) {
            if (p.getName().equals(powerupNameUpp)){
                setData(shooter, victim, x, y, ammo, directions); //to fix
                if (p.getEffect().canUseEffect(actionInterface)) {
                    Printer.println(powerupNameUpp + "USED");
                    p.getEffect().useEffect(actionInterface);
                    //rimuovere powerup
                    return true;
                } else {
                    Printer.println(powerupName + "NOT USED");
                    return false;
                }
            }
        }
        return false;
    }

    private void setData(Player shooter, Player victim, int x, int y, Color ammo, Direction...directions){
        getActionInterface().getClientData().setCurrentPlayer(shooter);
        getActionInterface().getClientData().setPowerupVictim(victim);
        getActionInterface().getClientData().setSquare(x,y);
        getActionInterface().getClientData().setAmmoColor(ammo);
        switch (directions.length){
            case 1:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                break;
            case 2:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                getActionInterface().getClientData().setFirstMove(directions[1]);
                break;
        }


    }

    public void shoot(Player player, Player victim){
        if(player.canUseAction()){
            actionInterface.getClientData().setVictim(victim);
            //player.getWeapons()...
            player.increaseActionNumber();
        }
    }

    public void deathAndRespawn(List<Player> players){
        game.scoring();
        for(Player player : players){
            if(player.isDead()){
                game.setKillAndDoubleKill(player);
                player.getPlayerBoard().resetBoard();
            }
        }
    }

    public void endTurn(Player player){
        player.resetActionNumber();
        game.endTurn(player, actionInterface);
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

    public Map<TokenColor, Integer> getScoreList(){
        return game.getScoreList();
    }

    public boolean isFinalFrenzy(){
        return game.isFinalFrenzy();
    }
    public void finalFrenzy(){
        game.finalFrenzy();
    }

    public void addPowerup(Player player, Card powerupCard){
        player.addPowerup((PowerupCard) powerupCard);
    }

}
