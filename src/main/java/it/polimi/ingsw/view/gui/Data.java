package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.network.client.ClientInterface;

public class Data{

    private static Data instance;
    private Integer boardType;
    private Integer skull;
    private Integer powerup;
    private ClientInterface client;
    private PlayerController playerController;
    private GUIHandler guiHandler;
    private String moveGrab;
    private Integer weaponShoot;
    private GUIHandler guiHandlerWeapon;
    private String weaponReloaded;


    private Data(){
    }

    public static Data getInstance() {
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    public void setBoardType(Integer boardType) {
        this.boardType = boardType;
    }

    public Integer getBoardType(){
        return this.boardType;
    }

    public void setSkull(Integer skull) {
        this.skull = skull;
    }

    public Integer getSkull(){
        return this.skull;
    }

    public void setPowerup(Integer powerup) {
        this.powerup = powerup;
    }

    public Integer getPowerup() {
        return this.powerup;
    }

    public void setClient(ClientInterface client){
        this.client = client;
    }

    public ClientInterface getClient() {
        return client;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public GUIHandler getGuiHandler() {
        return guiHandler;
    }

    public void setGuiHandler(GUIHandler guiHandler) {
        this.guiHandler = guiHandler;
    }

    public String getMoveGrab() {
        return moveGrab;
    }

    public void setMoveGrab(String moveGrab) {
        this.moveGrab = moveGrab;
    }

    public Integer getWeaponShoot() {
        return weaponShoot;
    }

    public void setWeaponShoot(Integer weaponShoot) {
        this.weaponShoot = weaponShoot;
    }

    public GUIHandler getGuiHandlerWeapon() {
        return guiHandlerWeapon;
    }

    public void setGuiHandlerWeapon(GUIHandler guiHandlerWeapon) {
        this.guiHandlerWeapon = guiHandlerWeapon;
    }

    public String getWeaponReloaded() {
        return weaponReloaded;
    }

    public void setWeaponReloaded(String weaponReloaded) {
        this.weaponReloaded = weaponReloaded;
    }
}
