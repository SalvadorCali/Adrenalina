package it.polimi.ingsw.network.enums;

public enum Response {
    WRONG(0), RIGHT(1), ALL(2);
    private final int number;
    Response(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
