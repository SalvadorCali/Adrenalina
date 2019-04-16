package it.polimi.ingsw.view;

public enum Response {
    WRONG(0), RIGHT(1);
    private final int number;
    Response(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
