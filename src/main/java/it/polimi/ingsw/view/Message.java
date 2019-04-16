package it.polimi.ingsw.view;

public enum Message {
    HELP(0), LOGIN(1), DISCONNECT(2), SHOW(3), MOVE(4), GRAB(5), SHOOT(6), NOTIFY(7);
    private final int number;
    Message(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
