package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;

public class ClientData {

    private Player currentPlayer = new Player(TokenColor.NONE);

    private Player victim = new Player(TokenColor.NONE);

    private Player secondVictim = new Player(TokenColor.NONE);

    private Player thirdVictim = new Player(TokenColor.NONE);

    private Player fakePlayer = new Player(TokenColor.NONE);

    private Player fakeVictim = new Player(TokenColor.NONE);

    private Direction firstMove = null, secondMove = null, thirdMove = null, fourthMove = null;

    private Position square = new Position(0, 0);

    private int redAmmo = 0, blueAmmo = 0, yellowAmmo = 0;

    private boolean basicFirst = true;



    public Player getVictim() {
        return victim;
    }

    public Player getSecondVictim() {
        return secondVictim;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setVictim(Player victim) {
        this.victim = victim;
    }

    public void setSecondVictim(Player secondVictim) {
        this.secondVictim = secondVictim;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Direction getFirstMove() {
        return firstMove;
    }

    public Direction getSecondMove() {
        return secondMove;
    }

    public Direction getThirdMove(){return thirdMove;}

    public Direction getFourthMove() {return fourthMove;}

    public void setFirstMove(Direction direction){
        this.firstMove = direction;
    }

    public void setSecondMove(Direction direction){
        this.secondMove = direction;
    }

    public void setThirdMove(Direction direction){
        this.thirdMove = direction;
    }

    public void setFourthMove(Direction direction){
        this.fourthMove = direction;
    }

    public Position getSquare() {
        return square;
    }

    public void setSquare(int x, int y) {

        this.square.setX(x);
        this.square.setY(y);
    }

    public Player getThirdVictim() {
        return thirdVictim;
    }

    public Player getFakePlayer() {
        return fakePlayer;
    }

    public boolean basicFirst() {
        return basicFirst;
    }

    public void setBasicFirst(boolean basicFirst){
        this.basicFirst = basicFirst;
    }

    public Player getFakeVictim() {
        return fakeVictim;
    }

    public void setThirdVictim(Player thirdVictim) {
        this.thirdVictim = thirdVictim;
    }

    public void setAmmos(){
        redAmmo = currentPlayer.getRedAmmo();
        blueAmmo = currentPlayer.getBlueAmmo();
        yellowAmmo = currentPlayer.getYellowAmmo();
    }

    public boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos){

        if(redAmmos <= redAmmo && blueAmmos <= blueAmmo && yellowAmmos <= yellowAmmo){
            redAmmo = redAmmo - redAmmos;
            blueAmmo = blueAmmo - blueAmmos;
            yellowAmmo = yellowAmmo - yellowAmmos;
            return true;
        }
        return false;
    }
}
