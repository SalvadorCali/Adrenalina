package it.polimi.ingsw.network.enums;

public enum Outcome {
    WRONG(0), RIGHT(1), ALL(2);
    private final int number;
    Outcome(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
