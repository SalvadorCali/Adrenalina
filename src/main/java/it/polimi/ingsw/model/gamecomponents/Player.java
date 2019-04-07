package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;

import java.util.ArrayList;
import java.util.List;

public class Player {
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

    public Player(TokenColor color){
        this.color = color;
        weapons = new ArrayList<>();
        powerups = new ArrayList<>();
        ammoBox = new ArrayList<>();
        ammoReserve = new ArrayList<>();
        yellowAmmo = 0;
        blueAmmo = 0;
        redAmmo = 0;
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

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public List<Ammo> getAmmoBox() {
        return ammoBox;
    }

    public List<Ammo> getAmmoReserve() {
        return ammoReserve;
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

    public boolean ammoControl(int ammos, Color color){
        switch (color){
            case BLUE:
                return (blueAmmo >= ammos);
            case RED:
                return (redAmmo >= ammos);
            case YELLOW:
                return (yellowAmmo >= ammos);
            default:
                return false;
        }
    }
}
