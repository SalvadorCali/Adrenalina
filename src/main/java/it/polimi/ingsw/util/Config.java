package it.polimi.ingsw.util;

public class Config {
    private Config(){}

    //connection
    public static final int SOCKET_PORT = 4321;
    public static final int EXECUTOR_SIZE = 30;
    public static final int RMI_PORT = 1099;
    public static final int RMI_FREE_PORT = 0;

    //timer
    public static final int TURN_TIME = 50000;
    public static final int START_TIME = 2000;
    public static final int DISCONNECTION_TIME = 5000;
    public static final int SPAWN_LOCATION_TIME = 1000;

    //game
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_ACTIONS = 2;
}
