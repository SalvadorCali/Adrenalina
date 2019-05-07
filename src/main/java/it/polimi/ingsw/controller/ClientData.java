package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Player;

public class ClientData {

    private Player victim, secondVictim, currentPlayer;

    public Player getVictim(){
        return victim;
    }

    public Player getSecondVictim() {return secondVictim;}

    public void setVictim(Player player) {
        this.victim = victim;
    }

    public void setSecondVictim(Player player){
        this.secondVictim = player;
    }

    public void setCurrentPlayer(Player player){
        this.currentPlayer = currentPlayer;
    }
}
