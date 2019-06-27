package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;

/**
 * Class which contains the datas given by the client, that are going to be used to apply effects.
 */
public class ClientData {

    /**
     * Current player using the effect.
     */
    private Player currentPlayer = new Player(TokenColor.NONE);

    /**
     * Victim of the effect.
     */
    private Player victim = new Player(TokenColor.NONE);

    /**
     * Second victim of the effect.
     */
    private Player secondVictim = new Player(TokenColor.NONE);

    /**
     * Third victim of the effect.
     */
    private Player thirdVictim = new Player(TokenColor.NONE);

    /**
     * Fake player used to simulate movements.
     */
    private Player fakePlayer = new Player(TokenColor.NONE);

    /**
     * Fake victim used to simulate the victim movement.
     */
    private Player fakeVictim = new Player(TokenColor.NONE);

    /**
     * Victim of the powerup.
     */
    private Player powerupVictim = new Player(TokenColor.NONE);

    /**
     * Directions of the movements required some of the effects.
     */
    private Direction firstMove = null, secondMove = null, thirdMove = null, fourthMove = null;

    /**
     * Position required by some of the effects.
     */
    private Position square = new Position(0, 0);

    /**
     * Color of the ammo.
     */
    private Color ammoColor = Color.NONE;

    /**
     * Values of the current players ammo box.
     * */
    private int redAmmo = 0, blueAmmo = 0, yellowAmmo = 0;

    /**
     * Identifies if the basic effect has to be applied first.
     */
    private boolean basicFirst = true;

    /**
     * Getter of the victim
     * @return the victim.
     */
    public Player getVictim() {
        return victim;
    }

    /**
     * Getter of the second victim.
     * @return the second victim.
     */
    public Player getSecondVictim() {
        return secondVictim;
    }

    /**
     * Getter of the current player.
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter of the victim.
     * @param victim chosen victim.
     */
    public void setVictim(Player victim) {
        this.victim = victim;
    }

    /**
     * Setter of the second victim.
     * @param secondVictim chosen second victim.
     */
    public void setSecondVictim(Player secondVictim) {
        this.secondVictim = secondVictim;
    }

    /**
     * Setter of the current player.
     * @param currentPlayer chosen current player.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Getter of the powerup victim.
     * @return the powerup victim.
     */
    public Player getPowerupVictim(){
        return powerupVictim;
    }

    /**
     * Setter of the powerup victim.
     * @param powerupVictim chosen powerup victim.
     */
    public void setPowerupVictim(Player powerupVictim){
        this.powerupVictim = powerupVictim;
    }

    /**
     * Getter of the first direction.
     * @return the first move.
     */
    public Direction getFirstMove() {
        return firstMove;
    }

    /**
     * Getter of the second direction.
     * @return the second move.
     */
    public Direction getSecondMove() {
        return secondMove;
    }

    /**
     * Getter of the third direction.
     * @return the third move.
     */
    Direction getThirdMove(){return thirdMove;}

    /**
     * Getter of the fourth direction.
     * @return the fourth direction.
     */
    Direction getFourthMove() {return fourthMove;}

    /**
     * Setter of the first move.
     * @param direction chosen direction.
     */
    public void setFirstMove(Direction direction){
        this.firstMove = direction;
    }

    /**
     * Setter of the second move.
     * @param direction chosen direction.
     */
    public void setSecondMove(Direction direction){
        this.secondMove = direction;
    }

    /**
     * Setter of the third move.
     * @param direction chosen direction.
     */
    public void setThirdMove(Direction direction){
        this.thirdMove = direction;
    }

    /**
     * Setter of the fourth move.
     * @param direction chosen direction.
     */
    public void setFourthMove(Direction direction){
        this.fourthMove = direction;
    }

    /**
     * Getter of the square position.
     * @return the square position.
     */
    public Position getSquare() {
        return square;
    }

    /**
     * Setter of the square position.
     * @param x row of the position.
     * @param y column of the position.
     */
    public void setSquare(int x, int y) {
        this.square.setX(x);
        this.square.setY(y);
    }

    /**
     * Getter of the third victim.
     * @return the third victim.
     */
    public Player getThirdVictim() {
        return thirdVictim;
    }

    /**
     * Getter of the fake player.
     * @return the fake player.
     */
    public Player getFakePlayer() {
        return fakePlayer;
    }

    /**
     * Getter of the boolean basicFirst.
     * @return basicFirst.
     */
    public boolean basicFirst() {
        return basicFirst;
    }

    /**
     * Setter of the boolean basicFirst.
     * @param basicFirst chosen value.
     */
    public void setBasicFirst(boolean basicFirst){
        this.basicFirst = basicFirst;
    }

    /**
     * Getter of the fake victim.
     * @return the fake victim.
     */
    public Player getFakeVictim() {
        return fakeVictim;
    }

    /**
     * Setter of the third victim.
     * @param thirdVictim chosen third victim.
     */
    public void setThirdVictim(Player thirdVictim) {
        this.thirdVictim = thirdVictim;
    }

    /**
     * Setter of the ammos.
     */
    public void setAmmos(){
        redAmmo = currentPlayer.getRedAmmo();
        blueAmmo = currentPlayer.getBlueAmmo();
        yellowAmmo = currentPlayer.getYellowAmmo();
    }

    /**
     * Setter of the ammo color.
     * @param color chosen color.
     */
    public void setAmmoColor(Color color){
        this.ammoColor = color;
    }

    /**
     * Controls if the player has enough actions to perform the ammos.
     * @param redAmmos red ammos required.
     * @param blueAmmos blue ammos required.
     * @param yellowAmmos yellow ammos required.
     * @return the result of the control.
     */
    public boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos){
        if(redAmmos <= redAmmo && blueAmmos <= blueAmmo && yellowAmmos <= yellowAmmo){
            redAmmo = redAmmo - redAmmos;
            blueAmmo = blueAmmo - blueAmmos;
            yellowAmmo = yellowAmmo - yellowAmmos;
            return true;
        }
        return false;
    }

    /**
     * Getter of the ammo color.
     * @return the ammo color.
     */
    public Color getAmmoColor() {
        return ammoColor;
    }
}
