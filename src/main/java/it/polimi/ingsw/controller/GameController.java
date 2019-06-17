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
    private MoveAndReloadData moveAndReloadData;
    private Game game;
    private ActionInterface actionInterface;
    private boolean canShoot = true;

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
        canShoot = true;
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
    public void setRespawnPhase(boolean respawnPhase) {
        game.setRespawnPhase(respawnPhase);
    }

    public boolean isRespawnPhase() {
        return game.isRespawnPhase();
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

    public void inverseMoveAndReload(Player player){
        if(moveAndReloadData.getSecondDirection() != null){
            game.getBoard().move(Converter.fromDirectionToOpposite(moveAndReloadData.getSecondDirection()), player);
        }
        if(moveAndReloadData.getFirstDirection() != null){
            game.getBoard().move(Converter.fromDirectionToOpposite(moveAndReloadData.getFirstDirection()), player);
        }
        for(int i=0; i<moveAndReloadData.getWeapons().size(); i++){
            String weapon = Converter.weaponName(moveAndReloadData.getWeapons().get(i));
            for(WeaponCard w : player.getWeapons()){
                if(w.getName().equals(weapon)){
                    player.updateAmmoBoxAdd(w.getReloadRedAmmos(), w.getReloadBlueAmmos(), w.getReloadYellowAmmos());
                    w.unload(); //da controllare
                }
            }
        }
        player.setMoveAndReload(false);
    }

    public boolean canMoveAndReload(Player player, Direction firstDirection, String...weapons){
        boolean[] reload = new boolean[weapons.length];
        boolean canReload = true;
        if(canMove(player, firstDirection)){
            if(weapons.length > 0){
                for(int i=0; i<weapons.length; i++){
                    String weaponNameUpp;
                    weaponNameUpp = Converter.weaponName(weapons[i]);
                    for(WeaponCard w : player.getWeapons()){
                        if(w.getName().equals(weaponNameUpp)){
                            if(w.reloadAmmoControl(player)) {
                                reload[i] = true;
                            }
                        }
                    }
                }
                for(int i=0; i<weapons.length; i++){
                    canReload = canReload && reload[i];
                }
                canShoot = canReload;
                return canReload;
            }else{
                return true;
            }
        }else{
            canShoot = false;
            return false;
        }
    }

    public boolean canMoveAndReload(Player player, Direction firstDirection, Direction secondDirection, String...weapons){
        boolean[] reload = new boolean[weapons.length];
        boolean canReload = true;
        if(canMove(player, firstDirection, secondDirection)){
            if(weapons.length > 0){
                for(int i=0; i<weapons.length; i++){
                    String weaponNameUpp;
                    weaponNameUpp = Converter.weaponName(weapons[i]);
                    for(WeaponCard w : player.getWeapons()){
                        if(w.getName().equals(weaponNameUpp)){
                            if(w.reloadAmmoControl(player)) {
                                reload[i] = true;
                            }
                        }
                    }
                }
                for(int i=0; i<weapons.length; i++){
                    canReload = canReload && reload[i];
                }
                canShoot = canReload;
                return canReload;
            }else{
                return true;
            }
        }else{
            canShoot = false;
            return false;
        }
    }

    public void moveAndReload(Player player, Direction firstDirection, String...weapons){
        game.getBoard().move(firstDirection, player);
        for(String weapon : weapons){
            reload(player, weapon);
        }
        moveAndReloadData = new MoveAndReloadData(firstDirection, null, weapons);
        player.setMoveAndReload(true);
    }

    public void moveAndReload(Player player, Direction firstDirection, Direction secondDirection, String...weapons){
        game.getBoard().move(firstDirection, player);
        game.getBoard().move(secondDirection, player);
        for(String weapon : weapons){
            reload(player, weapon);
        }
        moveAndReloadData = new MoveAndReloadData(firstDirection, secondDirection, weapons);
        player.setMoveAndReload(true);
    }

    public boolean canShowSquare(Player player){
        return (player.getPosition().getX() >= 0 && player.getPosition().getX() <= 2) && (player.getPosition().getY() >= 0 && player.getPosition().getY() <= 3);
    }

    public SquareData showSquare(Player player) {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        SquareData squareData = new SquareData();
        squareData.setAmmoCard(game.getBoard().getArena()[x][y].getAmmoCard());
        squareData.setWeapons(game.getBoard().getArena()[x][y].getWeapons());
        return squareData;
    }

    public boolean canShowSquare(Player player, int x, int y){
        return (x >= 0 && x <= 2) && (y >= 0 && y <= 3);
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
                        for(Direction direction : directions){
                            game.getBoard().move(direction, player);
                        }
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
                        for(Direction direction : directions){
                            game.getBoard().move(direction, player);
                        }
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
            Printer.println("carica:" + w.getName() + " " + w.isLoaded());
            Printer.println("canuse:" + shooter.canUseAction());
            Printer.println("canShoot:" + canShoot);
            if (w.getName().equals(weaponNameUpp) && w.isLoaded() && shooter.canUseAction() && canShoot) {
                Printer.println("sparo");
                setData(basicfirst, shooter, firstVictim, secondVictim, thirdVictim, x, y, directions);
                if (w.getEffects().get(mod).canUseEffect(actionInterface)) {
                    Printer.println(weaponNameUpp + "USED");
                    w.getEffects().get(mod).useEffect(actionInterface);
                    w.unload();
                    shooter.increaseActionNumber();
                    return true;
                } else {
                    if(shooter.isMoveAndReload()){
                        inverseMoveAndReload(shooter);
                        canShoot = true;
                    }
                    Printer.println(weaponNameUpp + "NOT USED");
                    return false;
                }
            }else{
                if(!canShoot){
                    Printer.println("debug");
                    canShoot = true;
                }
                return false;
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
            case 0:
                getActionInterface().getClientData().setFirstMove(null);
                getActionInterface().getClientData().setSecondMove(null);
                getActionInterface().getClientData().setThirdMove(null);
                getActionInterface().getClientData().setFourthMove(null);
                break;
            case 1:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                getActionInterface().getClientData().setSecondMove(null);
                getActionInterface().getClientData().setThirdMove(null);
                getActionInterface().getClientData().setFourthMove(null);
                break;
            case 2:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                getActionInterface().getClientData().setSecondMove(directions[1]);
                getActionInterface().getClientData().setThirdMove(null);
                getActionInterface().getClientData().setFourthMove(null);
                break;
            case 3:
                getActionInterface().getClientData().setFirstMove(directions[0]);
                getActionInterface().getClientData().setSecondMove(directions[1]);
                getActionInterface().getClientData().setThirdMove(directions[2]);
                getActionInterface().getClientData().setFourthMove(null);
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
                setData(shooter, victim, x, y, ammo, directions);
                if (p.getEffect().canUseEffect(actionInterface)) {
                    Printer.println(powerupNameUpp + "USED");
                    p.getEffect().useEffect(actionInterface);
                    shooter.getPowerups().remove(p);
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
                getActionInterface().getClientData().setSecondMove(directions[1]);
                break;
        }


    }


    public void deathAndRespawn(List<Player> players){
        game.scoring();
        for(Player player : players){
            if(player.isDead()){
                game.setKillAndDoubleKill(player);
                player.getPlayerBoard().resetBoard();
                player.addPowerup((PowerupCard) powerups.draw());
                player.setDead(true);
            }
        }
        game.setRespawnPhase(true);
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

    public void powerupAmmos(Player player, int...powerups){
        for(int powerup : powerups){
            if(powerup - 1 < player.getPowerups().size()){
                Color color = player.getPowerups().get(powerup - 1).getColor();
                player.increasePowerupAmmoNumber(color);
                player.getPowerups().remove(powerup - 1);
                player.setPowerupAsAmmo(true);
            }
        }
    }

    public boolean canDropPowerup(Player player, int powerup){
        return powerup <= player.getPowerups().size() && powerup > 0;
    }

    public void dropPowerup(Player player, int powerup){
        player.getPowerups().remove(powerup - 1);
    }

    public boolean canDropWeapon(Player player, int weapon){
        return weapon <= player.getWeapons().size() && weapon > 0;
    }

    public void dropWeapon(Player player, int weapon){
        player.getWeapons().remove(weapon - 1);
    }

    public boolean canDiscardPowerup(Player player, int powerup){
        return powerup <= player.getPowerups().size() && powerup > 0;
    }

    public void discardPowerup(Player player, int powerup){
        Color color = player.getPowerups().get(powerup - 1).getColor();
        player.increasePowerupAmmoNumber(color);
        player.getPowerups().remove(powerup - 1);
        player.setPowerupAsAmmo(true);
    }

    public boolean canRespawn(Player player, int powerup){
        if(player.getPowerups().size() >= powerup){
            return true;
        }else{
            return false;
        }
    }

    public void respawn(Player player, int powerup){
        Color color = player.getPowerups().get(powerup - 1).getColor();
        player.getPowerups().remove(powerup - 1);
        game.getBoard().removePlayer(player);
        game.getBoard().setPlayer(player, color);
        player.setRespawned(true);
    }
}
