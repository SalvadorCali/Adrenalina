package it.polimi.ingsw.view;

public class StringCLI {
    //commands
    static final String HELP = "help";
    static final String LOGIN = "login";
    static final String DISCONNECT = "disconnect";
    static final String BOARD = "board";
    static final String DROP = "drop";
    static final String DISCARD = "discard";
    static final String CHOOSE = "choose";
    static final String SHOW = "show";
    static final String MOVE = "move";
    static final String GRAB = "grab";
    static final String SHOOT = "shoot";
    static final String POWERUP = "powerup";
    static final String RELOAD = "reload";
    static final String RESPAWN = "respawn";
    static final String END = "end";

    //components
    static final String SCORE = "score";
    static final String PLAYERBOARD = "playerboard";
    static final String AMMOS = "ammos";
    static final String POWERUPS = "powerups";
    static final String WEAPONS = "weapons";
    static final String SQUARE = "square";
    static final String OTHERS = "others";

    //messages
    static final String INVALID_COMMAND = "Invalid Command. Please insert a valid command:";
    static final String INSERT_COMMAND = "Please, insert the following command ->";
    static final String SERVER = "[SERVER]";

    //insert commands
    static final String COMMANDS_LIST = "List of Commands:";
    static final String HELP_COMMAND = "help : gives you the list of commands";
    static final String LOGIN_COMMAND = "login <username> <color> :";
    static final String DISCONNECT_COMMAND = "disconnect :";
    static final String SHOW_COMMAND = "show <object> :";
    static final String MOVE_COMMAND = "move <first_direction, ..., last_direction> :";
    static final String SHOOT_COMMAND = "shoot <victim> <weapon_name> :";
    static final String GRAB_COMMAND = "grab <direction> <0, 1, 2, 3> :";
    static final String END_COMMAND = "end : ends your turn";
    static final String CHOOSE_COMMAND = "choose <choosen_powerup> :";

    //login
    static final String WRONG_USERNAME = "Username already used! Please choose another username:";
    static final String CONNECTED = "connected!";

    //spawn
    static final String CHOOSE_POWERUP = "Please, choose one of these powerups:";

    //disconnect
    static final String DISCONNECTED = "disconnected!";

    //color
    static final String WRONG_COLOR = "Invalid color! Please choose another color:";
    static final String YOUR_COLOR = "Your color is";

    //board
    static final String CHOOSE_BOARD = "Please, choose one of these boards:";
    static final String BOARD_COMMAND = "board <choosen_board> <skulls number>: ";
    static final String FIRST_PLAYER_BOARD = "The first player is choosing the board...";

    //turn
    static final String NEW_TURN = "It's your turn!";
    static final String END_TURN = "Your turn is ended!";
    static final String NOT_TURN = "It's not your turn!";

    //game
    static final String GAME_BEGUN = "Game is already begun!";
    static final String GAME_STARTED = "Game is started!";

    //move
    static final String MOVED = "Moved!";
    static final String NOT_MOVED = "Not moved!";

    //grab
    static final String GRABBED = "Grabbed!";
    static final String NOT_GRABBED = "Not grabbed!";
    static final String USER_GRABBED = "grabbed!";

    //shoot
    static final String SHOT = "Shot!";
    static final String NOT_SHOT = "Not shot!";
    static final String WANT_MOVE = "Do you want to move?";

    //powerup
    static final String USED = "used!";
    static final String NOT_USED = "not used!";
    static final String TARGETING_SCOPE = "targetingscope";
    static final String TAGBACK_GRENADE = "tagbackgrenade";
    static final String NEWTON = "newton";
    static final String TELEPORTER = "teleporter";

    //reload
    static final String RELOADED = "reloaded!";
    static final String NOT_RELOADED = "not reloaded!";

    //score
    static final String KILLSHOT_SCORE = "Score:";

    //reconnection
    static final String RECONNECTED = "Reconnected!";

    //discard and drop
    static final String WEAPON_DROP = "Weapon";
    static final String POWERUP_DROP = "Powerup";
    static final String DROPPED = "dropped!";
    static final String NOT_DROPPED = "not dropped!";
    static final String DISCARDED = "discarded!";
    static final String NOT_DISCARDED = "not discarded!";

    //final frenzy
    static final String FINAL_FRENZY = "Final Frenzy!!!";

    //respawn
    static final String RESPAWN_POWERUP = "Please, discard a powerup to respawn:";
    static final String RESPAWN_COMMAND = "respawn <powerup_number> :";

    //inputs
    static final String YES_INPUT = "<yes>";
    static final String NO_INPUT = "<no>";

    //
    static final String SPACE = " ";
    static final String MULTIPLE_SPACE = "   ";
    static final String COLON = ":";
    static final String NEW_LINE = "\n";
}
