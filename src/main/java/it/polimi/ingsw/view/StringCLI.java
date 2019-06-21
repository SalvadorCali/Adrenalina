package it.polimi.ingsw.view;

public class StringCLI {
    //commands
    public static final String HELP = "help";
    public static final String LOGIN = "login";
    public static final String DISCONNECT = "disconnect";
    public static final String BOARD = "board";
    public static final String DROP = "drop";
    public static final String DISCARD = "discard";
    public static final String CHOOSE = "choose";
    public static final String SHOW = "show";
    public static final String MOVE = "move";
    public static final String GRAB = "grab";
    public static final String SHOOT = "shoot";
    public static final String POWERUP = "powerup";
    public static final String RELOAD = "reload";
    public static final String RESPAWN = "respawn";
    public static final String END = "end";

    //components
    public static final String SCORE = "score";
    public static final String PLAYERBOARD = "playerboard";
    public static final String AMMOS = "ammos";
    public static final String POWERUPS = "powerups";
    public static final String WEAPONS = "weapons";

    //messages
    public static final String INVALID_COMMAND = "Invalid Command. Please insert a valid command:";
    public static final String INSERT_COMMAND = "Please, insert the following command ->";
    public static final String SERVER = "[SERVER]";

    //insert commands
    public static final String COMMANDS_LIST = "List of Commands:";
    public static final String HELP_COMMAND = "help : gives you the list of commands";
    public static final String LOGIN_COMMAND = "login <username> <color> :";
    public static final String DISCONNECT_COMMAND = "disconnect :";
    public static final String SHOW_COMMAND = "show <object> :";
    public static final String MOVE_COMMAND = "move <first_direction, ..., last_direction> :";
    public static final String SHOOT_COMMAND = "shoot <victim> <weapon_name> :";
    public static final String GRAB_COMMAND = "grab <direction> <0, 1, 2, 3> :";
    public static final String END_COMMAND = "end : ends your turn";
    public static final String CHOOSE_COMMAND = "choose <choosen_powerup> :";

    //login
    public static final String WRONG_USERNAME = "Username already used! Please choose another username:";
    public static final String CONNECTED = "connected!";

    //spawn
    public static final String CHOOSE_POWERUP = "Please, choose one of these powerups:";

    //disconnect
    public static final String DISCONNECTED = "disconnected!";

    //color
    public static final String WRONG_COLOR = "Invalid color! Please choose another color:";
    public static final String YOUR_COLOR = "Your color is";

    //board
    public static final String CHOOSE_BOARD = "Please, choose one of these boards:";

    //
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
}
