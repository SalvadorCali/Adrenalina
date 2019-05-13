package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.gamecomponents.Player;

public class ClientData {

    private Player victim, secondVictim, currentPlayer;

    private Direction firstMove, secondMove;


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
}
