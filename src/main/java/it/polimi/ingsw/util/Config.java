package it.polimi.ingsw.util;

/**
 * This class contains variables that represent generic values used by the network or the controllers.
 */
public class Config {
    /**
     * Class constructor.
     */
    private Config(){}

    //connection

    /**
     * The port for socket connection.
     */
    public static final int SOCKET_PORT = 4321;
    /**
     * The size of the executors.
     */
    public static final int EXECUTOR_SIZE = 30;
    /**
     * The port for rmi connection.
     */
    public static final int RMI_PORT = 1099;
    /**
     * A number that represents a free port for rmi.
     */
    public static final int RMI_FREE_PORT = 0;

    //timer

    /**
     * Time for the turn of each user.
     */
    public static int TURN_TIME = 500000;

    /**
     * Setter for the turn time.
     * @param turnTime the time that will be set.
     */
    public static void setTurnTime(int turnTime){
        TURN_TIME = turnTime;
    }

    /**
     * Time for the begin of the game.
     */
    public static int START_TIME = 1000;

    /**
     * Setter for the start time.
     * @param startTime the time that will be set.
     */
    public static void setStartTime(int startTime){
        START_TIME = startTime;
    }

    /**
     * Time to handle a disconnection.
     */
    public static final int DISCONNECTION_TIME = 5000;
    /**
     * Time for the choice of the board.
     */
    public static int BOARD_TYPE_TIME = 1000;

    /**
     * Setter for the board type time.
     * @param boardTypeTime the time that will be set.
     */
    public static void setBoardTypeTime(int boardTypeTime){
        BOARD_TYPE_TIME = boardTypeTime;
    }
    /**
     * Time for the choice of the spawn.
     */
    public static int SPAWN_LOCATION_TIME = 1000;

    /**
     * Setter for the spawn location time.
     * @param spawnLocationTime the time that will be set.
     */
    public static void setSpawnLocationTime(int spawnLocationTime){
        SPAWN_LOCATION_TIME = spawnLocationTime;
    }
    /**
     * Time to choose where respawn.
     */
    public static final int RESPAWN_TIME = 30000;

    //game

    /**
     * Minimum number of players.
     */
    public static final int MIN_PLAYERS = 2;
    /**
     * Maximum number of actions for each turn.
     */
    public static final int MAX_ACTIONS = 2;
}
