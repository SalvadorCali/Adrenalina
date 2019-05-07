package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Player;

public class ClientData {

    private Player victim, secondVictim;

    public Player getVictim(){
        return victim;
    }

    public Player getSecondVictim() {return secondVictim;}
}
