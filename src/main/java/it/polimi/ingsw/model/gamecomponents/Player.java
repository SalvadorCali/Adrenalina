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

public class Player implements Serializable {
    private TokenColor color;
    private int score;
    private List<WeaponCard> weapons;
    private List<WeaponCard> unloadedWeapons;
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
    private boolean damaged;
    private boolean powerupAsAmmo;
    private boolean moveAndReload;
    private static final long serialVersionUID = 1L;

    public Player(TokenColor color){
        this.color = color;
        weapons = new ArrayList<>();
        unloadedWeapons = new ArrayList<>();
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

    public boolean isMoveAndReload() {
        return moveAndReload;
    }

    public void setMoveAndReload(boolean moveAndReload) {
        this.moveAndReload = moveAndReload;
    }

    public boolean isPowerupAsAmmo() {
        return powerupAsAmmo;
    }

    public void setPowerupAsAmmo(boolean powerupAsAmmo) {
        this.powerupAsAmmo = powerupAsAmmo;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public TokenColor getColor() {
        return color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        getPosition().setX(x);
        getPosition().setY(y);
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public List<Ammo> getAmmoBox() {
        return ammoBox;
    }

    public int getBlueAmmo() {
        return blueAmmo;
    }

    public int getYellowAmmo(){
        return yellowAmmo;
    }

    public int getRedAmmo() {
        return redAmmo;
    }

    public int getPowerupYellowAmmo() {
        return powerupYellowAmmo;
    }

    public int getPowerupBlueAmmo() {
        return powerupBlueAmmo;
    }

    public int getPowerupRedAmmo() {
        return powerupRedAmmo;
    }

    public List<Ammo> getAmmoReserve() {
        return ammoReserve;
    }

    public void setAmmoBox(List<Ammo> ammoBox) {
        this.ammoBox = ammoBox;
    }

    public void setAmmoReserve(List<Ammo> ammoReserve) {
        this.ammoReserve = ammoReserve;
    }

    public List<PowerupCard> getPowerups() {
        return powerups;
    }

    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<WeaponCard> weapons) {
        this.weapons = weapons;
    }

    public int getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(int actionNumber) {
        this.actionNumber = actionNumber;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDisconnected() {
        return disconnected;
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    //methods
    public boolean canUsePowerupAmmos(){
        return (powerupBlueAmmo + powerupRedAmmo + powerupYellowAmmo) > 0;
    }

    public void addWeapon(WeaponCard weapon){
        weapons.add(weapon);
    }

    public void addPowerup(PowerupCard powerup){
        powerups.add(powerup);
    }

    public void addAmmo(Ammo...ammos){
        for(Ammo ammo : ammos){
            if(canAddAmmo(ammo.getColor())){
                ammoBox.add(ammo);
                increaseAmmoNumber(ammo.getColor());
            }
        }
    }

    public void addAmmoToReserve(Ammo...ammos){
        ammoReserve.addAll(Arrays.asList(ammos));
    }

    public boolean canAddAmmo(Color color){
        switch (color){
            case BLUE:
                return (blueAmmo < 3);
            case RED:
                return (redAmmo < 3);
            case YELLOW:
                return (yellowAmmo < 3);
            default:
                return false;
        }
    }

    public void resetPowerupAmmos(){
        powerupBlueAmmo = 0;
        powerupRedAmmo = 0;
        powerupYellowAmmo = 0;
        powerupAsAmmo = false;
    }

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

    public boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos){

        return (redAmmos <= redAmmo && blueAmmos <= blueAmmo && yellowAmmos <= yellowAmmo);
    }

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

    public void increaseActionNumber(){
        actionNumber++;
    }

    public void resetActionNumber(){
        actionNumber = 0;
    }

    public boolean canUseAction(){
        return actionNumber < Config.MAX_ACTIONS;
    }

    public boolean canUseActionFinalFrenzy(){
        return actionNumber < finalFrenzyActionsNumber;
    }

    public boolean isDead(){
        return playerBoard.isDead();
    }

    public void canReload(String...weaponNames){
        //
    }

    public void reload(String...weaponNames){
        for(String weaponName : weaponNames){
            for(WeaponCard weapon : unloadedWeapons){
                if(weapon.getName().equals(weaponName)){
                    //
                }
            }
        }
    }

    public boolean isDamaged(){
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public int getFinalFrenzyActionsNumber() {
        return finalFrenzyActionsNumber;
    }

    public void setFinalFrenzyActionsNumber(int finalFrenzyActionsNumber) {
        this.finalFrenzyActionsNumber = finalFrenzyActionsNumber;
    }

    public FinalFrenzyAction getFinalFrenzyActions() {
        return finalFrenzyActions;
    }

    public void setFinalFrenzyActions(FinalFrenzyAction finalFrenzyActions) {
        this.finalFrenzyActions = finalFrenzyActions;
    }
}
