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

import java.util.List;
import java.util.Map;

/**
 * Class which represents the controller of an entire game.
 */
public class GameController {

    /**
     * Weapons present in the game.
     */
    private Deck weapons;
    /**
     * Powerups present in the game.
     */
    private Deck powerups;
    /**
     * Ammo cards present in the game.
     */
    private List<AmmoCard> ammoCards;
    /**
     * All the game boards.
     */
    private List<GameBoard> gameBoards;
    /**
     * Database of the infos needed from the client to move and reload.
     */
    private MoveAndReloadData moveAndReloadData;
    /**
     * Current game.
     */
    private Game game;
    /**
     * Gives access to some restricted methods of the game/clientData to the card controls.
     */
    private ActionInterface actionInterface;
    /**
     * Boolean which indicates if the player can shoot or can't.
     */
    private boolean canShoot = true;
    /**
     * List of dropped weapons.
     */
    private Card droppedWeapon = null;

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

    /**
     * Controls if the player can move one direction and reload his weapons.
     * @param player player who wants to move and reload.
     * @param firstDirection direction of the move.
     * @param weapons list of weapons that the player wants to reload.
     * @return the result of the control.
     */
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

    /**
     * Controls if the player can move two directions and reload his weapons.
     * @param player the player who wants to move.
     * @param firstDirection first move.
     * @param secondDirection second move.
     * @param weapons weapons to reload.
     * @return the result of the control.
     */
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

    /**
     * Moves the player in a direction and reloads the selected weapons.
     * @param player the player who wants to move and reload.
     * @param firstDirection direction of the movement.
     * @param weapons weapons to reload.
     */
    public void moveAndReload(Player player, Direction firstDirection, String...weapons){
        game.getBoard().move(firstDirection, player);
        for(String weapon : weapons){
            reload(player, weapon);
        }
        moveAndReloadData = new MoveAndReloadData(firstDirection, null, weapons);
        player.setMoveAndReload(true);
    }

    /**
     * Moves the player in two consecutive directions and reloads the selected weapons.
     * @param player player who wants to move.
     * @param firstDirection first direction of the movement.
     * @param secondDirection second direction of the movement.
     * @param weapons weapons to reload.
     */
    public void moveAndReload(Player player, Direction firstDirection, Direction secondDirection, String...weapons){
        game.getBoard().move(firstDirection, player);
        game.getBoard().move(secondDirection, player);
        for(String weapon : weapons){
            reload(player, weapon);
        }
        moveAndReloadData = new MoveAndReloadData(firstDirection, secondDirection, weapons);
        player.setMoveAndReload(true);
    }

    /**
     * Controls if the square info can be shown or not.
     * @param player player who wants to see the info.
     * @return the result of the control.
     */
    boolean canShowSquare(Player player){
        return (player.getPosition().getX() >= 0 && player.getPosition().getX() <= 2) && (player.getPosition().getY() >= 0 && player.getPosition().getY() <= 3);
    }

    /**
     * Shows to the player the square info.
     * @param player the player who wants to see the info.
     * @return the info of the square.
     */
    SquareData showSquare(Player player) {
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        SquareData squareData = new SquareData();
        squareData.setAmmoCard(game.getBoard().getArena()[x][y].getAmmoCard());
        squareData.setWeapons(game.getBoard().getArena()[x][y].getWeapons());
        return squareData;
    }

    /**
     * Reloads the weapon chosen by the player.
     * @param player the player who wants to reload his weapon.
     * @param weaponName the weapon that the player wants to reload.
     * @return true if the weapon was reloaded, false if wasn't.
     */
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

    /**
     * Controls if the player can grab a weapon/ammo card from the board.
     * @param player player who wants to grab.
     * @param choice chosen weapon/ammo card.
     * @param directions directions of the movement that the player wants to do before grabbing.
     * @return true if the card was grabbed, false if wasn't.
     */
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

    /**
     * Drops a weapon.
     * @param player player who wants to drop a weapon.
     * @param weapon weapon that the player wants to drop.
     */
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

    /**
     * Controls if the player has the chosen weapon, if its loaded and if the effect can be applied, it then shoots.
     * @param weaponName name of the weapon the player wants to use.
     * @param mod represents the effect that the player wants to apply.
     * @param basicfirst is true if the player wants to apply the basic effect first.
     * @param shooter player who wants to shoot.
     * @param firstVictim first victim of the weapon.
     * @param secondVictim second victim of the weapon.
     * @param thirdVictim third victim of the weapon.
     * @param x row of the square in which the player wants to apply the effect.
     * @param y column of the square in which the player wants to apply the effect.
     * @param directions directions needed to apply certain effects.
     * @return the result of the control: true if the player can shoot, false if can't.
     */
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

    /**
     * Sets the data given from the client in ClientData.
     * @param basicfirst is true if the player wants to apply the basic effect first.
     * @param shooter player who wants to shoot.
     * @param firstVictim first victim of the weapon.
     * @param secondVictim second victim of the weapon.
     * @param thirdVictim third victim of the weapon.
     * @param x row of the square in which the player wants to apply the effect.
     * @param y column of the square in which the player wants to apply the effect.
     * @param directions directions needed to apply certain effects.
     */
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

    /**
     * Controls if the player can use a powerup, and use it if he can.
     * @param powerupName name of the powerup that the player wants to play.
     * @param shooter player who wants to play the powerup.
     * @param victim victim of the powerup.
     * @param ammo color of the powerup.
     * @param x row where the player wants to move when using Teleporter.
     * @param y column where the player wants to move when using Teleporter.
     * @param directions directions of the movement in Newton.
     * @return The result of the control.
     */
    boolean usePowerup(String powerupName, Player shooter, Player victim, Color ammo, int x, int y, Direction...directions){
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

    /**
     * Sets the data chosen by the client in the class ClientData.
     * @param shooter player who uses the powerup.
     * @param victim victim of the powerup.
     * @param x row of the teleporter powerup.
     * @param y column of the teleporter powerup.
     * @param ammo color of the powerup.
     * @param directions directions of the newton powerup.
     */
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

    /**
     * Respawns every dead player and gives him a new powerup.
     * @param players list of the players to respawn.
     */
    void deathAndRespawn(List<Player> players){
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

    /**
     * Resets the action number of the current player and ends the turn.
     * @param player the current player.
     */
    public void endTurn(Player player){
        player.resetActionNumber();
        droppedWeapon = null;
        game.endTurn(player, actionInterface);
    }

    /**
     * Draws a powerup.
     * @return the Drawn powerup.
     */
    Card drawPowerup(){
        return game.drawPowerup();
    }

    /**
     * Sets a player on the game board.
     * @param player player who needs to be setted.
     * @param color color of the player.
     */
    public void setPlayer(Player player, Color color){
        game.getBoard().setPlayer(player, color);
    }

    /**
     * Getter of the score list.
     * @return the score list.
     */
    Map<TokenColor, Integer> getScoreList(){
        return game.getScoreList();
    }

    /**
     * Getter of the boolean isFinalFrenzy.
     * @return the value of the boolean isFinalFrenzy.
     */
    boolean isFinalFrenzy(){
        return game.isFinalFrenzy();
    }

    /**
     * Sets the final frenzy stage.
     */
    void finalFrenzy(){
        game.finalFrenzy();
    }

    /**
     * Adds a powerup to the powerups of a player.
     * @param player player who wants to add the powerup.
     * @param powerupCard the chosen powerup to add.
     */
    void addPowerup(Player player, Card powerupCard){
        player.addPowerup((PowerupCard) powerupCard);
    }

    /**
     * Controls that a player can drop a certain powerup.
     * @param player the player who wants to drop a powerup.
     * @param powerup the index of the powerup that the player wants to drop.
     * @return the result of the control.
     */
    boolean canDropPowerup(Player player, int powerup){
        return powerup <= player.getPowerups().size() && powerup > 0;
    }

    /**
     * Drops a powerup.
     * @param player player who wants to drop the powerup.
     * @param powerup index of the powerup to drop.
     */
    void dropPowerup(Player player, int powerup){
        player.getPowerups().remove(powerup - 1);
    }

    /**
     * Controls if a player can drop one of his weapons.
     * @param player player who wants to drop his weapons.
     * @param weapon weapons that the player wants to drop.
     * @return the result of the control.
     */
    boolean canDropWeapon(Player player, int weapon){
        return weapon <= player.getWeapons().size() && weapon > 0 && game.getBoard().getArena()[player.getPosition().getX()][player.getPosition().getY()].isSpawn();
    }

    /**
     * Drops a weapon.
     * @param player player who wants to drop the weapon.
     * @param weapon weapon that the player wants to drop.
     */
    void dropWeapon(Player player, int weapon){
        droppedWeapon = player.getWeapons().get(weapon - 1);
        player.getWeapons().remove(weapon - 1);
    }

    /**
     * Controls if a player can discard a powerup and convert it in an ammo.
     * @param player player who wants to discard the powerup.
     * @param powerup index of the powerup to discard.
     * @return the result of the control.
     */
    boolean canDiscardPowerup(Player player, int powerup){
        return powerup <= player.getPowerups().size() && powerup > 0;
    }

    /**
     * Discards a powerup and adds an ammo to the player's ammo box.
     * @param player player who wants to discard the powerup.
     * @param powerup index of the powerup to discard.
     */
    void discardPowerup(Player player, int powerup){
        Color color = player.getPowerups().get(powerup - 1).getColor();
        player.increasePowerupAmmoNumber(color);
        player.getPowerups().remove(powerup - 1);
        player.setPowerupAsAmmo(true);
    }

    /**
     * Control if the player can respawn.
     * @param player player who wants to respawn.
     * @param powerup index of the powerup to control.
     * @return the result of the control.
     */
    boolean canRespawn(Player player, int powerup){
        return player.getPowerups().size() >= powerup;
    }

    /**
     * Respawns a player in a new spawn point.
     * @param player player to respawn.
     * @param powerup index of the powerup.
     */
    public void respawn(Player player, int powerup){
        Color color = player.getPowerups().get(powerup - 1).getColor();
        player.getPowerups().remove(powerup - 1);
        game.getBoard().removePlayer(player);
        game.getBoard().setPlayer(player, color);
        player.setRespawned(true);
    }

    /**
     * Ends the game.
     */
    void endGame(){
        game.endGame();
    }

    /**
     * ???
     * @param player
     * @param x
     * @param y
     * @return
     */
    boolean canShowSquare(Player player, int x, int y){
        return (x >= 0 && x <= 2) && (y >= 0 && y <= 3);
    }

    /**
     * ???
     * @param player
     * @param x
     * @param y
     * @return
     */
    SquareData showSquare(Player player, int x, int y) {
        SquareData squareData = new SquareData();
        squareData.setAmmoCard(game.getBoard().getArena()[x][y].getAmmoCard());
        squareData.setWeapons(game.getBoard().getArena()[x][y].getWeapons());
        return squareData;
    }
}
