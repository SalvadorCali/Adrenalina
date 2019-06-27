package it.polimi.ingsw.view.cli;

/**
 * This class contains Strings used by the Command Line Interface.
 */
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
    static final String COMMANDS = "Please, insert <help> to see a list of commands.";

    //insert commands
    static final String COMMANDS_LIST = "List of Commands:";
    static final String HELP_COMMAND = "help : gives you the list of commands";
    static final String LOGIN_COMMAND = "login <username> <color> :";
    static final String LOGIN_COMMAND_2 = "login <username> <color> : connects you to the game";
    static final String DISCONNECT_COMMAND = "disconnect : disconnects you from the game";
    static final String SHOW_COMMAND = "show <object> : shows you the requested object";
    static final String MOVE_COMMAND = "move <first_direction, ..., last_direction> : moves your player";
    static final String SHOOT_COMMAND = "shoot <weapon_name> : shoots using the requested weapon";
    static final String GRAB_COMMAND = "grab <0, 1, 2, 3> <direction> : grabs (0 for ammos, 1-2-3 for weapons)";
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
    static final String MOVED = "moved!";
    static final String NOT_MOVED = "not moved!";

    //grab
    static final String GRABBED = "grabbed!";
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
    static final String RECONNECTED = "reconnected!";

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
    static final String YES = "yes";
    static final String YES_INPUT = "<yes>";
    static final String NO_INPUT = "<no>";

    //weapons
    static final String LOCK_RIFLE = "lockrifle";
    static final String MACHINE_GUN = "machinegun";
    static final String THOR = "thor";
    static final String PLASMA_GUN = "plasmagun";
    static final String WHISPER = "whisper";
    static final String ELECTROSCYTHE = "electroscythe";
    static final String TRACTOR_BEAM = "tractorbeam";
    static final String VORTEX_CANNON = "vortexcannon";
    static final String FURNACE = "furnace";
    static final String HEATSEEKER = "heatseeker";
    static final String HELLION = "hellion";
    static final String FLAMETHROWER = "flamethrower";
    static final String GRENADE_LAUNCHER = "grenadelauncher";
    static final String ROCKET_LAUNCHER = "rocketlauncher";
    static final String RAILGUN = "railgun";
    static final String CYBERBLADE = "cyberblade";
    static final String CYBERBLADE_2 = "cyberblade2";
    static final String ZX_2 = "zx2";
    static final String SHOTGUN = "shotgun";
    static final String POWER_GLOVE = "powerglove";
    static final String SHOCKWAVE = "shockwave";
    static final String SLEDGEHAMMER = "sledgehammer";

    //weapons' effects
    static final String EFFECT = "Effect:";
    static final String BASIC_EFFECT = "Basic effect:";
    static final String WITH_SECOND_LOCK = "With second lock:";
    static final String WITH_FOCUS_SHOT = "With focus shot:";
    static final String WITH_TURRET_TRIPOD = "With turret tripod:";
    static final String WITH_CHAIN_REACTION = "With chain reaction:";
    static final String WITH_HIGH_VOLTAGE = "With high voltage:";
    static final String WITH_PHASE_GLIDE = "With phase glide:";
    static final String WITH_CHARGED_SHOT = "With charged shot:";
    static final String WITH_BLACK_HOLE = "With black hole:";
    static final String WITH_EXTRA_GRENADE = "With extra grenade:";
    static final String WITH_ROCKET_JUMP = "With rocket jump:";
    static final String WITH_SHADOW_STEP = "With shadow step:";
    static final String WITH_SLICE_AND_DICE = "With slice and dice:";

    //weapons' modes
    static final String BASIC_MODE = "Basic mode:";
    static final String IN_REAPER_MODE = "In reaper mode:";
    static final String IN_PUNISHER_MODE = "In punisher mode:";
    static final String IN_COZY_FIRE_MODE = "In cozy fire mode:";
    static final String IN_NANO_TRACER_MODE = "In nano-tracer mode:";
    static final String IN_BARBECUE_MODE = "In barbecue mode:";
    static final String IN_PIERCING_MODE = "In piercing mode:";
    static final String IN_SCANNER_MODE = "In scanner mode:";
    static final String IN_LONG_BARREL_MODE = "In long barrel mode:";
    static final String IN_ROCKET_FIST_MODE = "In rocket fist mode:";
    static final String IN_TSUNAMI_MODE = "In tsunami mode:";
    static final String IN_PULVERIZE_MODE = "In pulverize mode:";

    //effects
    static final String VICTIM = "<victim>";
    static final String FIRST_VICTIM = "<first_victim>";
    static final String SECOND_VICTIM = "<second_victim>";
    static final String THIRD_VICTIM = "<third_victim>";
    static final String ONE = "<1>";
    static final String TWO = "<2>";
    static final String THREE = "<3>";
    static final String DIRECTION = "<direction>";
    static final String FIRST_DIRECTION = "<first_direction>";
    static final String SECOND_DIRECTION = "<second_direction>";
    static final String SQUARE_X = "<squareX>";
    static final String SQUARE_Y = "<squareY>";
    static final String VICTIM_DIRECTION = "<victim_direction>";
    static final String SHOOTER_DIRECTION = "<shooter_direction>";

    //do you want
    static final String CHOOSE_EFFECT = "Choose your effect:";
    static final String MOVE_FIRST = "Do you want to move first?:";
    static final String SECOND_EFFECT_FIRST = "Do you want to use the second effect first?:";
    static final String MOVE_AFTER_THIRD = "Do you want to move after the third effect?:";

    //generic
    static final String SPACE = " ";
    static final String MULTIPLE_SPACE = "   ";
    static final String COLON = ":";
    static final String NEW_LINE = "\n";
    static final String OR = "or";
}
