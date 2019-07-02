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
    private String weaponReloaded1;
    private String weaponReloaded2;
    private String weaponReloaded3;
    private Integer numPowerup;
    private Integer finalFrenzy = 0;
    private String moveGrabOneAction [] = new String[3];
    private String moveGrabTwoActions [] = new String[2];
    private String moveRel [] = new String[2];

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

    public Integer getNumPowerup() {
        return numPowerup;
    }

    public void setNumPowerup(Integer numPowerup) {
        this.numPowerup = numPowerup;
    }

    public Integer getFinalFrenzy() {
        return finalFrenzy;
    }

    public void setFinalFrenzy(Integer finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    public String getWeaponReloaded1() {
        return weaponReloaded1;
    }

    public void setWeaponReloaded1(String weaponReloaded1) {
        this.weaponReloaded1 = weaponReloaded1;
    }

    public String getWeaponReloaded2() {
        return weaponReloaded2;
    }

    public void setWeaponReloaded2(String weaponReloaded2) {
        this.weaponReloaded2 = weaponReloaded2;
    }

    public String getWeaponReloaded3() {
        return weaponReloaded3;
    }

    public void setWeaponReloaded3(String weaponReloaded3) {
        this.weaponReloaded3 = weaponReloaded3;
    }

    public String[] getMoveGrabTwoActions() {
        return moveGrabTwoActions;
    }

    public void setMoveGrabTwoActions(String[] moveGrabTwoActions) {
        this.moveGrabTwoActions = moveGrabTwoActions;
    }

    public String[] getMoveGrabOneAction() {
        return moveGrabOneAction;
    }

    public void setMoveGrabOneAction(String[] moveGrabOneAction) {
        this.moveGrabOneAction = moveGrabOneAction;
    }

    public String[] getMoveRel() {
        return moveRel;
    }

    public void setMoveRel(String[] moveRel) {
        this.moveRel = moveRel;
    }
}
