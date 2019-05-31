package it.polimi.ingsw.view.GUI;

public class Data{

    private static Data instance;
    private Integer i;

    private Data(){
    }

    public static Data getInstance() {
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getI(){
        return this.i;
    }
}
