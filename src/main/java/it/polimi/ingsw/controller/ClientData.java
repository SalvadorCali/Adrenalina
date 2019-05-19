package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;

public class ClientData {

    private Player victim, secondVictim, thirdVictim, currentPlayer;

    private Player fakePlayer = new Player(TokenColor.NONE);

    private Player fakeVictim = new Player(TokenColor.NONE);

    private Direction firstMove, secondMove;

    private Position square = new Position(0, 0);

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

    public void setFirstMove(Direction direction){
        this.firstMove = direction;
    }

    public void setSecondMove(Direction direction){
        this.secondMove = direction;
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
}
