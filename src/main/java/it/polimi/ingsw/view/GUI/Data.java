package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.client.ClientInterface;

public class Data{

    private static Data instance;
    private Integer boardType;
    private Integer skull;
    private Integer powerup;
    private ClientInterface client;

    private Data(){
    }

    public static Data getInstance() {
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    public void setBoardType(Integer boardType) {
        this.boardType = boardType;
    }

    public Integer getBoardType(){
        return this.boardType;
    }

    public void setSkull(Integer skull) {
        this.skull = skull;
    }

    public Integer getSkull(){
        return this.skull;
    }

    public void setPowerup(Integer powerup) {
        this.powerup = powerup;
    }

    public Integer getPowerup() {
        return this.powerup;
    }

    public void setClient(ClientInterface client){
        this.client = client;
    }

    public ClientInterface getClient() {
        return client;
    }
}
