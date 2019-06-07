package it.polimi.ingsw.view.GUI;

public class Data{

    private static Data instance;
    private Integer boardType;
    private Integer skull;
    private Integer powerup;

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


}
