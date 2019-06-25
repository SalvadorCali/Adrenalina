package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.datas.MoveAndReloadData;
import it.polimi.ingsw.controller.datas.SquareData;
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
    Card droppedWeapon = null;

    /**
     * Class constructor.
     */
    public GameController() {
        weapons = Parser.createWeapons();
        powerups = Parser.createPowerups();
        ammoCards = Parser.createAmmos();
        gameBoards = Parser.createGameBoards();
        game = new Game(gameBoards.get(0), weapons, powerups, ammoCards);
        actionInterface = new ActionController(game);
    }

    /**
     * Getter of the current game.
     * @return the current game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Getter of the action interface.
     * @return the action interface.
     */
    public ActionInterface getActionInterface() {
        return actionInterface;
    }

    /**
     * Setter of the boolean respawnPhase.
     * @param respawnPhase chosen value for respawnPhase.
     */
    void setRespawnPhase(boolean respawnPhase) {
        game.setRespawnPhase(respawnPhase);
    }

    /**
     * Getter of the boolean respawnPhase.
     * @return the value of respawnPhase.
     */
    public boolean isRespawnPhase() {
        return game.isRespawnPhase();
    }

    /**
     * Starts the game, adds the players to it and sets their ammos. Creates the gameboard.
     * @param players list of players to add to the game.
     */
    void startGame(List<Player> players) {
        game.setPlayers(players);
        game.giveAmmos();
        game.fillSquares(actionInterface);
        game.createScoreList();
    }

    /**
     * Setter of the boolean gamePhase.
     * @param gamePhase chosen value for gamePhase.
     */
    void setGamePhase(boolean gamePhase) {
        game.setGamePhase(gamePhase);
    }

    /**
     * Getter of the boolean gamePhase.
     * @return the value of gamePhase.
     */
    boolean isGamePhase() {
        return game.isGamePhase();
    }

    /**
     * Setter of the boolean spawnLocationPhase.
     * @param spawnLocationPhase chosen value for spawnLocationPhase.
     */
    void setSpawnLocationPhase(boolean spawnLocationPhase) {
        game.setSpawnLocationPhase(spawnLocationPhase);
    }

    /**
     * Getter of the boolean spawnLocationPhase.
     * @return the value of spawnLocationPhase.
     */
    public boolean isSpawnLocationPhase() {
        return game.isSpawnLocationPhase();
    }

    /**
     * Setter of the boolean boardTypePhase.
     * @param boardTypePhase chosen value for boardTypePhase.
     */
    void setBoardTypePhase(boolean boardTypePhase){
        game.setBoardTypePhase(boardTypePhase);
    }

    /**
     * Getter of the boolean boardTypePhase.
     * @return the value of the boolean boardTypePhase.
     */
    public boolean isBoardTypePhase(){
        return game.isBoardTypePhase();
    }

    /**
     * Sets the board and the number of skulls after the first player's choice.
     * @param boardType chosen board.
     * @param skulls number of the chosen skulls.
     */
    void setBoard(int boardType, int skulls){
        game.setBoard(gameBoards.get(boardType - 1));
        game.createKillshotTrack(skulls);
    }

    /**
     * Calls the game board move control.
     * @param player player who wants to move.
     * @param directions directions of the moves.
     * @return the result of the evaluation, true if the player can move, false if can't.
     */
    public boolean canMove(Player player, Direction... directions) {
        return game.getBoard().canMove(player, directions);
    }

    /**
     * Calls the game board move to a square control.
     * @param x row of the selected square.
     * @param y column of the selected square.
     * @return the result of the control.
     */
    public boolean canMove(int x, int y) {
        return game.getBoard().canMove(x, y);
    }

    /**
     * Controls if the player can move in different consecutive directions on the board, and moves him if he can.
     * @param player the player who wants to move.
     * @param directions directions of the movement.
     * @return the result of the control.
     */
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

    /**
     * Moves a player to a different square on the board.
     * @param player the player who wants to move.
     * @param x row of the square where the player wants to move to.
     * @param y column of the square where the player wants to move to.
     */
    public void move(Player player, int x, int y) {
        game.getBoard().move(x, y, player);
    }

    /**
     * Moves the player back to his previous position and unloads his weapons if the can shoot control fails.
     * @param player the player who wants to do the action.
     */
    private void inverseMoveAndReload(Player player){
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
                    w.unload();
                }
            }
        }
        player.setMoveAndReload(false);
    }

    boolean canMoveAndReload(Player player, Direction firstDirection, String...weapons){
        boolean[] reload = new boolean[weapons.length];
        boolean canReload = true;
        if(canMove(player, firstDirection) || firstDirection == null){
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

    boolean canMoveAndReload(Player player, Direction firstDirection, Direction secondDirection, String...weapons){
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
                    Position position = new Position(player.getPosition().getX(), player.getPosition().getY());
                    if (canMove(player, directions)) {
                        position.incrementPosition(directions);
                        int x = position.getX();
                        int y = position.getY();
                        if(game.getBoard().getArena()[x][y].canGrab(actionInterface, choice)){
                            for(Direction direction : directions){
                                game.getBoard().move(direction, player);
                            }
                        }else{
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();
                if (game.getBoard().getArena()[x][y].canGrab(actionInterface, choice)) {
                    game.getBoard().getArena()[x][y].grab(actionInterface, choice);
                    if(choice != 0 && droppedWeapon != null){
                        game.getBoard().getArena()[x][y].drop(droppedWeapon);
                    }
                    player.increaseActionNumber();
                    return true;
                } else {
                    return false;
                }
            }
        }else{
            if(player.canUseAction()) {
                Printer.println("CanUse: " + player.canUseAction());
                if (directions.length > 0) {
                    Printer.println("Lunghezza: " + directions.length);
                    Position position = new Position(player.getPosition().getX(), player.getPosition().getY());
                    Printer.println("X: " + player.getPosition().getX());
                    Printer.println("Y: " + player.getPosition().getY());
                    if (canMove(player, directions)) {
                        position.incrementPosition(directions);
                        Printer.println("Nuova X: " + position.getX());
                        Printer.println("Nuova Y: " + position.getY());
                        int x = position.getX();
                        int y = position.getY();
                        if(game.getBoard().getArena()[x][y].canGrab(actionInterface, choice)){
                            for(Direction direction : directions){
                                game.getBoard().move(direction, player);
                            }
                        }
                        else{
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                int x = player.getPosition().getX();
                int y = player.getPosition().getY();
                if (game.getBoard().getArena()[x][y].canGrab(actionInterface, choice)) {
                    game.getBoard().getArena()[x][y].grab(actionInterface, choice);
                    if(choice != 0 && droppedWeapon != null){
                        game.getBoard().getArena()[x][y].drop(droppedWeapon);
                    }
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
        if(canShoot){
            for (WeaponCard w : shooter.getWeapons()) {
                Printer.println("carica:" + w.getName() + " " + w.isLoaded());
                Printer.println("canuse:" + shooter.canUseAction());
                Printer.println("canShoot:" + canShoot);
                if (w.getName().equals(weaponNameUpp) && w.isLoaded() && shooter.canUseAction()) {
                    Printer.println("sparo");
                    setData(basicfirst, shooter, firstVictim, secondVictim, thirdVictim, x, y, directions);
                    if (mod >= 0 && mod < w.getEffects().size() && w.getEffects().get(mod).canUseEffect(actionInterface)) {
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
                    if(shooter.isMoveAndReload() && w.getName().equals(weaponNameUpp)){
                        inverseMoveAndReload(shooter);
                        canShoot = true;
                        return false;
                    }
                }
            }
            return false;
        }else{
            canShoot = true;
            return false;
        }
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
        droppedWeapon = null;
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
        return weapon <= player.getWeapons().size() && weapon > 0 && game.getBoard().getArena()[player.getPosition().getX()][player.getPosition().getY()].isSpawn();
    }

    public void dropWeapon(Player player, int weapon){
        droppedWeapon = player.getWeapons().get(weapon - 1);
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

    public void endGame(){
        game.endGame();
    }
}
