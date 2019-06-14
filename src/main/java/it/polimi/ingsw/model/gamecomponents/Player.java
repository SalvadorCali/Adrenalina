package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.FinalFrenzyAction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * class which represents one of the players present in the game.
 */
public class Player implements Serializable {
    private static final int AMMOBOXLIMIT = 3;
    private TokenColor color;
    private int score;
    private List<WeaponCard> weapons;
    private List<PowerupCard> powerups;
    private List<Ammo> ammoBox;
    private int yellowAmmo;
    private int blueAmmo;
    private int redAmmo;
    private int powerupYellowAmmo;
    private int powerupBlueAmmo;
    private int powerupRedAmmo;
    private List<Ammo> ammoReserve;
    private PlayerBoard playerBoard;
    private Position position;
    private int actionNumber;
    private int finalFrenzyActionsNumber;
    private FinalFrenzyAction finalFrenzyActions;
    private boolean myTurn;
    private String username;
    private boolean disconnected;
    private boolean spawned;
    private boolean respawned;
    private boolean damaged;
    private boolean powerupAsAmmo;
    private boolean moveAndReload;
    private static final long serialVersionUID = 1L;

    /**
     * Player class constructor.
     * @param color color of the player to construct at the beginning of the game.
     */
    public Player(TokenColor color){
        this.color = color;
        weapons = new ArrayList<>();
        powerups = new ArrayList<>();
        ammoBox = new ArrayList<>();
        ammoReserve = new ArrayList<>();
        position = new Position(0,0);
        yellowAmmo = 0;
        blueAmmo = 0;
        redAmmo = 0;
        actionNumber = 0;
        playerBoard = new PlayerBoard();
        finalFrenzyActions = FinalFrenzyAction.NO_FINAL_FRENZY;
    }


    /**
     * getter of the boolean respawned.
     * @return the boolean respawned.
     */
    public boolean isRespawned() {
        return respawned;
    }

    /**
     * setter of the boolean respawned
     * @param respawned chosen value for the boolean respawned.
     */
    public void setRespawned(boolean respawned) {
        this.respawned = respawned;
    }

    /**
     * getter of the boolean moveAndReload.
     * @return the boolean moveAndReload.
     */
    public boolean isMoveAndReload() {
        return moveAndReload;
    }

    /**
     * setter of the boolean moveAndReload
     * @param moveAndReload chosen value for the boolean moveAndReload.
     */
    public void setMoveAndReload(boolean moveAndReload) {
        this.moveAndReload = moveAndReload;
    }

    /**
     * getter of the boolean powerupAsAmmo.
     * @return the boolean powerupAsAmmo
     */
    public boolean isPowerupAsAmmo() {
        return powerupAsAmmo;
    }

    /**
     * setter of the boolean powerupAsAmmo.
     * @param powerupAsAmmo chosen value for the boolean powerupAsAmmo.
     */
    public void setPowerupAsAmmo(boolean powerupAsAmmo) {
        this.powerupAsAmmo = powerupAsAmmo;
    }

    /**
     * getter of the boolean spawned.
     * @return the boolean spawned.
     */
    public boolean isSpawned() {
        return spawned;
    }

    /**
     * setter of the boolean spawned.
     * @param spawned chosen value for the boolean spawned.
     */
    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    /**
     * getter of the player's color.
     * @return the color of the player.
     */
    public TokenColor getColor() {
        return color;
    }

    /**
     * setter of the player's color.
     * @param color chosen color of the player.
     */
    public void setColor(TokenColor color) {
        this.color = color;
    }

    /**
     * getter of the player's score.
     * @return the player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * setter of the player's score.
     * @param score chosen value for the player's score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * getter of the player's position.
     * @return the player's position.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * setter of the player's position.
     * @param position chosen position of the player.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * setter of the player's position.
     * @param x int that indicates the chosen row of the player's position.
     * @param y int that indicates the chosen column of the player's position.
     */
    void setPosition(int x, int y) {
        getPosition().setX(x);
        getPosition().setY(y);
    }

    /**
     * getter of the player's personal board.
     * @return the player's personal board.
     */
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * setter of the player's personal board.
     * @param playerBoard chosen board to set.
     */
    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    /**
     * getter of the player's ammo box.
     * @return the player's ammo box.
     */
    public List<Ammo> getAmmoBox() {
        return ammoBox;
    }

    /**
     * getter of the number of the player's blue ammos.
     * @return the number of the player's blue ammos.
     */
    public int getBlueAmmo() {
        return blueAmmo;
    }

    /**
     * getter of the number of the player's yellow ammos.
     * @return the number of the player's yellow ammos.
     */
    public int getYellowAmmo(){
        return yellowAmmo;
    }

    /**
     * getter of the number of the player's red ammos.
     * @return the number of the player's red ammos.
     */
    public int getRedAmmo() {
        return redAmmo;
    }

    /**
     * getter of the player's ammo reserve.
     * @return the player's ammo reserve.
     */
    public List<Ammo> getAmmoReserve() {
        return ammoReserve;
    }

    /**
     * setter of the player's ammo box.
     * @param ammoBox the chosen list to set as ammo box.
     */
    void setAmmoBox(List<Ammo> ammoBox) {
        this.ammoBox = ammoBox;
    }

    /**
     * setter of the player's ammo reserve.
     * @param ammoReserve the chosen list to set as ammo reserve.
     */
    void setAmmoReserve(List<Ammo> ammoReserve) {
        this.ammoReserve = ammoReserve;
    }

    /**
     * getter of the player's list of powerups.
     * @return the list of the player's powerups.
     */
    public List<PowerupCard> getPowerups() {
        return powerups;
    }

    /**
     * getter of the player's list of weapons.
     * @return the player's list of weapons.
     */
    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    /**
     * setter of the player's list of weapons.
     * @param weapons the list of weapons to set.
     */
    public void setWeapons(List<WeaponCard> weapons) {
        this.weapons = weapons;
    }

    /**
     * setter of the player's executed actions
     * @param actionNumber number of actions executed by the player.
     */
    public void setActionNumber(int actionNumber) {
        this.actionNumber = actionNumber;
    }

    /**
     * getter of the boolean myTurn.
     * @return true if is the player's turn, false if isn't.
     */
    public boolean isMyTurn() {
        return myTurn;
    }

    /**
     * setter of the boolean myTurn.
     * @param myTurn chosen value for the boolean myTurn.
     */
    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * getter of the player's username.
     * @return the player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter of the player's username.
     * @param username chosen name of the player.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter of the boolean disconnected.
     * @return true if the player is disconnected, false if isn't.
     */
    public boolean isDisconnected() {
        return disconnected;
    }

    /**
     * setter of the boolean disconnected.
     * @param disconnected chosen value of the boolean disconnected.
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    /**
     * adds a chosen weapon to the player's list of weapons.
     * @param weapon indicates the chosen weapon.
     */
    public void addWeapon(WeaponCard weapon){
        weapons.add(weapon);
    }

    /**
     * adds a chosen powerup to the player's list of powerups.
     * @param powerup indicates the chosen powerup.
     */
    public void addPowerup(PowerupCard powerup){
        powerups.add(powerup);
    }

    /**
     * adds multiple ammos to the player's ammo box.
     * @param ammos indicates the chosen ammos to add to the player's ammo box.
     */
    public void addAmmo(Ammo...ammos){
        for(Ammo ammo : ammos){
            if(canAddAmmo(ammo.getColor())){
                ammoBox.add(ammo);
                increaseAmmoNumber(ammo.getColor());
            }
        }
    }

    /**
     * adds multiple ammos to the player's ammo reserve.
     * @param ammos indicates the chosen ammos to add to the player's ammo reserve.
     */
    void addAmmoToReserve(Ammo... ammos){
        ammoReserve.addAll(Arrays.asList(ammos));
    }

    /**
     * verifies if an ammo of a certain color can be added to the player's ammo box.
     * @param color indicates the color of the ammo that the player wants to add.
     * @return the result of the control: true if the ammo can be added, false if can't.
     */
    boolean canAddAmmo(Color color){
        switch (color){
            case BLUE:
                return (blueAmmo < AMMOBOXLIMIT);
            case RED:
                return (redAmmo < AMMOBOXLIMIT);
            case YELLOW:
                return (yellowAmmo < AMMOBOXLIMIT);
            default:
                return false;
        }
    }

    /**
     * resets the ammo added using powerups to zero, it's used at the end of every turn.
     */
    public void resetPowerupAmmos(){
        powerupBlueAmmo = 0;
        powerupRedAmmo = 0;
        powerupYellowAmmo = 0;
        powerupAsAmmo = false;
    }

    /**
     * increases the number of red/blue/yellow powerup ammos.
     * @param color indicates the color of the ammos to increment.
     */
    public void increasePowerupAmmoNumber(Color color){
        switch (color){
            case BLUE:
                powerupBlueAmmo++;
                break;
            case RED:
                powerupRedAmmo++;
                break;
            case YELLOW:
                powerupYellowAmmo++;
                break;
            default:
                break;
        }
    }

    /**
     * increases the number of red/blue/yellow ammos.
     * @param color indicates the color of the ammos to increment.
     */
    public void increaseAmmoNumber(Color color){
        switch (color){
            case BLUE:
                blueAmmo++;
                break;
            case RED:
                redAmmo++;
                break;
            case YELLOW:
                yellowAmmo++;
                break;
            default:
                break;
        }
    }

    /**
     * verifies that the player has enough ammos of every color to perform an action.
     * @param redAmmos indicates the number of red ammos required by the action.
     * @param blueAmmos indicates the number of blue ammos required by the action.
     * @param yellowAmmos indicates the number of yellow ammos required by the action.
     * @return the result of the control, true if the player has enough ammos, false if hasn't.
     */
    public boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos){
        return (redAmmos <= redAmmo && blueAmmos <= blueAmmo && yellowAmmos <= yellowAmmo);
    }

    /**
     * updates the ammoBox after the execution of an action that increases ammos.
     * @param redAmmos red ammos required by the executed action.
     * @param blueAmmos blue ammos required by the executed action.
     * @param yellowAmmos yellow ammos required by the executed action.
     */
    public void updateAmmoBoxAdd(int redAmmos, int blueAmmos, int yellowAmmos){
        redAmmo-=redAmmos;
        blueAmmo-=blueAmmos;
        yellowAmmo-=yellowAmmos;

        for(int i=0; i<ammoReserve.size(); i++){
            switch (ammoReserve.get(i).getColor()){
                case RED:
                    if(redAmmos > 0){
                        redAmmos--;
                        ammoBox.add(ammoReserve.get(i));
                        ammoReserve.remove(i);
                    }
                    break;
                case BLUE:
                    if(blueAmmos > 0){
                        blueAmmos--;
                        ammoBox.add(ammoReserve.get(i));
                        ammoReserve.remove(i);
                    }
                    break;
                case YELLOW:
                    if(yellowAmmos > 0){
                        yellowAmmos--;
                        ammoBox.add(ammoReserve.get(i));
                        ammoReserve.remove(i);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * updates the ammoBox after the execution of an action that decreases ammos.
     * @param redAmmos red ammos required by the executed action.
     * @param blueAmmos blue ammos required by the executed action.
     * @param yellowAmmos yellow ammos required by the executed action.
     */
    public void updateAmmoBox(int redAmmos, int blueAmmos, int yellowAmmos) {
        if(powerupAsAmmo){
            if(redAmmos>0){
                while(redAmmos>0 && powerupRedAmmo>0){
                    redAmmos-=1;
                    powerupRedAmmo-=1;
                }
            }
            if(blueAmmos>0){
                while(blueAmmos>0 && powerupBlueAmmo>0){
                    blueAmmos-=1;
                    powerupBlueAmmo-=1;
                }
            }
            if(yellowAmmos>0){
                while(yellowAmmos>0 && powerupYellowAmmo>0){
                    yellowAmmos-=1;
                    powerupYellowAmmo-=1;
                }
            }
        }
        redAmmo-=redAmmos;
        blueAmmo-=blueAmmos;
        yellowAmmo-=yellowAmmos;
        for(int i=0; i<ammoBox.size(); i++){
            switch (ammoBox.get(i).getColor()){
                case RED:
                    if(redAmmos > 0){
                        redAmmos--;
                        ammoReserve.add(ammoBox.get(i));
                        ammoBox.remove(i);
                    }
                    break;
                case BLUE:
                    if(blueAmmos > 0){
                        blueAmmos--;
                        ammoReserve.add(ammoBox.get(i));
                        ammoBox.remove(i);
                    }
                    break;
                case YELLOW:
                    if(yellowAmmos > 0){
                        yellowAmmos--;
                        ammoReserve.add(ammoBox.get(i));
                        ammoBox.remove(i);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * increases the player's number of executed actions.
     */
    public void increaseActionNumber(){
        actionNumber++;
    }

    /**
     * resets the player's number of executed actions to zero at the end of his turn.
     */
    public void resetActionNumber(){
        actionNumber = 0;
    }

    /**
     * controls if the player can perform an action or if he already performed all the possible actions in his turn.
     * @return the result of the control: true if the player can execute another action, false if can't.
     */
    public boolean canUseAction(){
        return actionNumber < Config.MAX_ACTIONS;
    }

    /**
     * controls if the player can perform an action or if he already performed all the possible actions in his final frenzy turn.
     * @return the result of the control: true if the player can execute another action, false if can't.
     */
    public boolean canUseActionFinalFrenzy(){
        return actionNumber < finalFrenzyActionsNumber;
    }

    /**
     * verifies if the player is still alive.
     * @return the result of the control: true if the player is dead, false if he's still alive.
     */
    public boolean isDead(){
        return playerBoard.isDead();
    }

    /**
     * sets the player to dead or alive.
     * @param dead chosen boolean to set.
     */
    public void setDead(boolean dead){
        playerBoard.setDead(dead);
    }

    /**
     * getter of the boolean damaged.
     * @return the value of the boolean isDamaged: true if the player has been damaged during the turn, false if isn't.
     */
    public boolean isDamaged(){
        return damaged;
    }

    /**
     * setter of the boolean damaged.
     * @param damaged indicates the chosen value to set.
     */
    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    /**
     * setter of the number of actions that can be performed by the player in the final frenzy turn.
     * @param finalFrenzyActionsNumber indicates the chosen number of actions to set.
     */
    void setFinalFrenzyActionsNumber(int finalFrenzyActionsNumber) {
        this.finalFrenzyActionsNumber = finalFrenzyActionsNumber;
    }

    /**
     * getter of the number of actions that can be performed by the player in the final frenzy turn.
     * @return the number of actions.
     */
    public FinalFrenzyAction getFinalFrenzyActions() {
        return finalFrenzyActions;
    }
    /**
     * ???
     * @param finalFrenzyActions ???
     */
    void setFinalFrenzyActions(FinalFrenzyAction finalFrenzyActions) {
        this.finalFrenzyActions = finalFrenzyActions;
    }

    public void reload(String...weaponNames){
        /*
        for(String weaponName : weaponNames){
            for(WeaponCard weapon : unloadedWeapons){
                if(weapon.getName().equals(weaponName)){
                    //
                }
            }
        }*/
    }
    public boolean canUsePowerupAmmos(){
        return (powerupBlueAmmo + powerupRedAmmo + powerupYellowAmmo) > 0;
    }
    public void updatePosition(Direction... directions){

        for(Direction direction : directions) {
            switch (direction) {
                case UP:
                    getPosition().setX(getPosition().getX() - 1);
                    break;
                case DOWN:
                    getPosition().setX(getPosition().getX() + 1);
                    break;
                case RIGHT:
                    getPosition().setY(getPosition().getY() + 1);
                    break;
                case LEFT:
                    getPosition().setY(getPosition().getY() - 1);
                    break;
            }
        }
    }
    public int getFinalFrenzyActionsNumber() {
        return finalFrenzyActionsNumber;
    }
}
