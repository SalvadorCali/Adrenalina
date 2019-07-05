package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.network.client.ClientInterface;

/**
 * this class implements the Singleton pattern and save the most important data for the Gui
 */
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

    /**
     * the private constructor denies object's instance from other extern classes
     */
    private Data(){
    }

    /**
     * class' method used for login to the singleton
     * @return instance of the singleton
     */
    public static Data getInstance() {
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    /**
     * setBoardType Int
     * @param boardType int represents the number of boardtype
     */
    public void setBoardType(Integer boardType) {
        this.boardType = boardType;
    }

    /**
     * get BoardType Int
     * @return int that represents the number of boardtype
     */
    public Integer getBoardType(){
        return this.boardType;
    }

    /**
     * set skull Int
     * @param skull represents turn's number
     */
    public void setSkull(Integer skull) {
        this.skull = skull;
    }

    /**
     * get skull Int
     * @return skull represents turn's number
     */
    public Integer getSkull(){
        return this.skull;
    }

    /**
     * set Int Powerup
     * @param powerup represent the powerup's choose
     */
    public void setPowerup(Integer powerup) {
        this.powerup = powerup;
    }

    /**
     * get Int powerup
     * @return the choose of powerup
     */
    public Integer getPowerup() {
        return this.powerup;
    }

    /**
     * set Client
     * @param client from clientInterface
     */
    public void setClient(ClientInterface client){
        this.client = client;
    }

    /**
     * get Client
     * @return client requested
     */
    public ClientInterface getClient() {
        return client;
    }

    /**
     * get playerController
     * @return playercontroller
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * set PlayerController
     * @param playerController
     */
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * get GuiHandler Controller
     * @return guiHandler class
     */
    public GUIHandler getGuiHandler() {
        return guiHandler;
    }

    /**
     * set guiHandler
     * @param guiHandler
     */
    public void setGuiHandler(GUIHandler guiHandler) {
        this.guiHandler = guiHandler;
    }

    /**
     * get MoveGrab string
     * @return the string of movegrab
     */
    public String getMoveGrab() {
        return moveGrab;
    }

    /**
     * set moveGrab String
     * @param moveGrab movegrab String
     */
    public void setMoveGrab(String moveGrab) {
        this.moveGrab = moveGrab;
    }

    /**
     * get Weapon choosen for shoot
     * @return weapon chosen
     */
    public Integer getWeaponShoot() {
        return weaponShoot;
    }

    /**
     * set Weapon choosen for shoot
     */
    public void setWeaponShoot(Integer weaponShoot) {
        this.weaponShoot = weaponShoot;
    }

    /**
     * get guiHandler Controller
     * @return the controller of the showWeapon
     */
    public GUIHandler getGuiHandlerWeapon() {
        return guiHandlerWeapon;
    }

    /**
     * set guiHandler Controller
     * @param guiHandlerWeapon
     */
    public void setGuiHandlerWeapon(GUIHandler guiHandlerWeapon) {
        this.guiHandlerWeapon = guiHandlerWeapon;
    }

    /**
     * get powerup choosen
     * @return chosen powerup
     */
    public Integer getNumPowerup() {
        return numPowerup;
    }

    /**
     * set powerup choosen
     * @param numPowerup
     */
    public void setNumPowerup(Integer numPowerup) {
        this.numPowerup = numPowerup;
    }

    /**
     * get final frenzy int
     * @return 1 if it is final frenzy, 0 otherwise
     */
    public Integer getFinalFrenzy() {
        return finalFrenzy;
    }

    /**
     * set final frenzy int
     * @param finalFrenzy
     */
    public void setFinalFrenzy(Integer finalFrenzy) {
        this.finalFrenzy = finalFrenzy;
    }

    /**
     * get first weapon reloaded
     * @return c
     */
    public String getWeaponReloaded1() {
        return weaponReloaded1;
    }

    /**
     * set first weapon reloaded
     */
    public void setWeaponReloaded1(String weaponReloaded1) {
        this.weaponReloaded1 = weaponReloaded1;
    }

    /**
     * get second weapon reloaded
     * @return second weapon reloaded
     */
    public String getWeaponReloaded2() {
        return weaponReloaded2;
    }

    /**
     * set second weapon reloaded
     */
    public void setWeaponReloaded2(String weaponReloaded2) {
        this.weaponReloaded2 = weaponReloaded2;
    }

    /**
     * get third weapon reloaded
     * @return third weapon reloaded
     */
    public String getWeaponReloaded3() {
        return weaponReloaded3;
    }

    /**
     * set third weapon reloaded
     */
    public void setWeaponReloaded3(String weaponReloaded3) {
        this.weaponReloaded3 = weaponReloaded3;
    }

    /**
     * get moveGrab arrayString Two
     * @return array for the movegrab of two actions
     */
    public String[] getMoveGrabTwoActions() {
        return moveGrabTwoActions;
    }

    /**
     * set moveGrab arrayString Two
     */
    public void setMoveGrabTwoActions(String[] moveGrabTwoActions) {
        this.moveGrabTwoActions = moveGrabTwoActions;
    }

    /**
     * get moveGrab arrayString One
     * @return array for the movegrab of one action
     */
    public String[] getMoveGrabOneAction() {
        return moveGrabOneAction;
    }

    /**
     * set moveGrab arrayString One
     */
    public void setMoveGrabOneAction(String[] moveGrabOneAction) {
        this.moveGrabOneAction = moveGrabOneAction;
    }

    /**
     * get moveRel arrayString
     * @return moveReload array string
     */
    public String[] getMoveRel() {
        return moveRel;
    }

    /**
     * set moveRel arrayString
     * @param moveRel
     */
    public void setMoveRel(String[] moveRel) {
        this.moveRel = moveRel;
    }
}
