package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.client.ClientInterface;

public class PlayerController {
    private ClientInterface client;
    private Player player;

    public PlayerController(ClientInterface client){
        this.client = client;
    }
}
