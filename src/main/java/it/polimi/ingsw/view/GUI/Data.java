package it.polimi.ingsw.view.GUI;

public class Data{

    private static Data instance;
    private Integer boardType;
    private Integer skull;
    private Integer powerup;
    private String namePlayer;
    private String host;
    private String colorPlayer;
    private String connectionMethod;
    private Object lock = new Object();

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

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getColorPlayer() {
        return colorPlayer;
    }

    public void setColorPlayer(String colorPlayer) {
        this.colorPlayer = colorPlayer;
    }

    public String getConnectionMethod() {
        return connectionMethod;
    }

    public void setConnectionMethod(String connectionMethod) {
        this.connectionMethod = connectionMethod;
    }

    public Object getLock() {
        return lock;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }
}
