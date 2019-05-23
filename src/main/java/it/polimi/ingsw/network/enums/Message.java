package it.polimi.ingsw.network.enums;

public enum Message {
    HELP(0), LOGIN(1), DISCONNECT(2), SHOW(3),
    MOVE(4), GRAB(5), SHOOT(6), NOTIFY(7),
    MESSAGE(8), COLOR(9), MOVE_GRAB(10),
    USERNAME(11), PLAYER(12), END_TURN(13),
    NEW_TURN(14), GAME(15), RECONNECTION(16),
    SPAWN(17), SQUARE(18), POWERUP(19),
    POWERUP_AMMOS(20), RELOAD(21),
    SHOOT_1(22), SHOOT_2(23), SHOOT_3(24);
    private final int number;
    Message(int number){
        this.number = number;
    }
    public int getNumber(){
        return number;
    }
}
