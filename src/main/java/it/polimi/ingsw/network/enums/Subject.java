package it.polimi.ingsw.network.enums;

public enum Subject {
    WRONG(0), RIGHT(1), ALL(2);
    private final int number;
    Subject(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
