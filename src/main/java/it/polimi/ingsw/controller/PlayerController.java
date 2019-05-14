package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.PlayerBoard;
import it.polimi.ingsw.network.client.ClientInterface;

import java.util.List;

public class PlayerController {
    private ClientInterface client;
    private Player player;
    private GameBoard gameBoard;

    public PlayerController(ClientInterface client){
        this.client = client;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int getScore(){
        return player.getScore();
    }

    public PlayerBoard getPlayerBoard(){
        return player.getPlayerBoard();
    }

    public List<Ammo> getAmmos(){
        return player.getAmmoBox();
    }

    public List<PowerupCard> getPowerups(){
        return player.getPowerups();
    }

    public List<WeaponCard> getWeapons(){
        return player.getWeapons();
    }

    public AdrenalineZone getAdrenalineZone(){
        return player.getPlayerBoard().getAdrenalineZone();
    }

    public void addPowerup(PowerupCard powerup){
        player.addPowerup(powerup);
    }
}
