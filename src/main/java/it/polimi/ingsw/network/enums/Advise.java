package it.polimi.ingsw.network.enums;

public enum Advise {
    COLOR(0);
    private final int number;
    Advise(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
