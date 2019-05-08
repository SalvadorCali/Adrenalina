package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Player;

public class ClientData {

    private Player victim, secondVictim, currentPlayer;


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

}
