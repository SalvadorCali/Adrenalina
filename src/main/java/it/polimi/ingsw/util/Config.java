package it.polimi.ingsw.util;

import it.polimi.ingsw.model.enums.BoardType;

public class Config {
    private Config(){}

    //connection
    public static final int SOCKET_PORT = 4321;
    public static final int EXECUTOR_SIZE = 30;
    public static final int RMI_PORT = 1099;
    public static final int RMI_FREE_PORT = 0;

    //timer
    public static final int TURN_TIME = 500000;
    public static final int START_TIME = 1000;
    public static final int DISCONNECTION_TIME = 5000;
    public static final int BOARD_TYPE_TIME = 10000;
    public static final int SPAWN_LOCATION_TIME = 5000;

    //game
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_ACTIONS = 2;
}
