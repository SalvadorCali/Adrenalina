package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private TokenColor color;
    private int score;
    private List<WeaponCard> weapons;
    private List<PowerupCard> powerups;
    private List<Ammo> ammoBox;
    private int yellowAmmo;
    private int blueAmmo;
    private int redAmmo;
    private List<Ammo> ammoReserve;
    private PlayerBoard playerBoard;
    private Position position;
    private int actionNumber;
    private boolean myTurn;
    private String username;
    private boolean disconnected;

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

        return (redAmmos >= redAmmo && blueAmmos >= blueAmmo && yellowAmmos >= yellowAmmo);
    }

    public void updateAmmoBox(int redAmmos, int blueAmmos, int yellowAmmos) {
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
                case DOWN:
                    getPosition().setX(getPosition().getX() + 1);
                case RIGHT:
                    getPosition().setY(getPosition().getY() + 1);
                case LEFT:
                    getPosition().setY(getPosition().getY() - 1);
            }
        }
    }


}
