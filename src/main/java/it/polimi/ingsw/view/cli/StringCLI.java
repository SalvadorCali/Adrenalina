package it.polimi.ingsw.view.cli;

/**
 * This class contains Strings used by the Command Line Interface.
 */
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
    public static final String WEAPON = "weapon";
    public static final String SQUARE = "square";
    public static final String OTHERS = "others";

    //messages
    public static final String INVALID_COMMAND = "Invalid Command. Please insert a valid command:";
    public static final String INSERT_COMMAND = "Please, insert the following command ->";
    public static final String SERVER = "[SERVER]";
    public static final String COMMANDS = "Please, insert <help> to see a list of commands.";
    public static final String CHOOSE_DIRECTION = "Choose a direction:";
    public static final String ERROR = "Error";

    //insert commands
    public static final String COMMANDS_LIST = "List of Commands:";
    public static final String HELP_COMMAND = "help : gives you the list of commands";
    public static final String LOGIN_COMMAND = "login <username> <color> :";
    public static final String LOGIN_COMMAND_2 = "login <username> <color> : connects you to the game";
    public static final String DISCONNECT_COMMAND = "disconnect : disconnects you from the game";
    public static final String SHOW_COMMAND = "show <object> : shows you the requested object";
    public static final String MOVE_COMMAND = "move <first_direction, ..., last_direction> : moves your player";
    public static final String SHOOT_COMMAND = "shoot <weapon_name> : shoots using the requested weapon";
    public static final String GRAB_COMMAND = "grab <0, 1, 2, 3> <direction> : grabs (0 for ammos, 1-2-3 for weapons)";
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

    //square
    public static final String YOUR_SQUARE = "In your square:";
    public static final String WEAPONS_SQUARE = "Weapons:";
    public static final String AMMOS_SQUARE = "Ammos:";
    public static final String AMMO_CARD = "AmmoCard:";
    public static final String POWERUP_CAPS = "POWERUP";
    public static final String INVALID_SQUARE = "Invalid square!";

    //board
    public static final String CHOOSE_BOARD = "Please, choose one of these boards:";
    public static final String BOARD_COMMAND = "board <choosen_board> <skulls number>: ";
    public static final String FIRST_PLAYER_BOARD = "The first player is choosing the board...";

    //turn
    public static final String NEW_TURN = "It's your turn!";
    public static final String END_TURN = "Your turn is ended!";
    public static final String NOT_TURN = "It's not your turn!";

    //game
    public static final String GAME_BEGUN = "Game is already begun!";
    public static final String GAME_STARTED = "Game is started!";
    public static final String GAME_END = "Game end!";

    //move
    public static final String MOVED = "moved!";
    public static final String NOT_MOVED = "not moved!";

    //grab
    public static final String GRABBED = "grabbed!";
    public static final String NOT_GRABBED = "Not grabbed!";
    public static final String USER_GRABBED = "grabbed!";

    //shoot
    public static final String SHOT = "Shot!";
    public static final String NOT_SHOT = "Not shot!";
    public static final String WANT_MOVE = "Do you want to move?";
    public static final String CHOOSE_DIRECTIONS = "Choose up to 2 directions:";
    public static final String WANT_RELOAD = "Do you want to reload?:";
    public static final String CHOOSE_WEAPON = "Choose weapon to reload:";

    //powerup
    public static final String USED = "used!";
    public static final String NOT_USED = "not used!";
    public static final String TARGETING_SCOPE = "targetingscope";
    public static final String TAGBACK_GRENADE = "tagbackgrenade";
    public static final String NEWTON = "newton";
    public static final String TELEPORTER = "teleporter";

    //reload
    public static final String RELOADED = "reloaded!";
    public static final String NOT_RELOADED = "not reloaded!";

    //score
    public static final String KILLSHOT_SCORE = "Score:";

    //reconnection
    public static final String RECONNECTED = "reconnected!";

    //discard and drop
    public static final String WEAPON_DROP = "Weapon";
    public static final String POWERUP_DROP = "Powerup";
    public static final String DROPPED = "dropped!";
    public static final String NOT_DROPPED = "not dropped!";
    public static final String DISCARDED = "discarded!";
    public static final String NOT_DISCARDED = "not discarded!";

    //final frenzy
    public static final String FINAL_FRENZY = "Final Frenzy!!!";

    //respawn
    public static final String RESPAWN_POWERUP = "Please, discard a powerup to respawn:";
    public static final String RESPAWN_COMMAND = "respawn <powerup_number> :";

    //yours
    public static final String YOUR_WEAPONS = "Your weapons:";
    public static final String YOUR_POWERUPS = "Your powerups:";

    //inputs
    public static final String YES = "yes";
    public static final String YES_INPUT = "<yes>";
    public static final String NO_INPUT = "<no>";

    //weapons
    public static final String LOCK_RIFLE = "lockrifle";
    public static final String MACHINE_GUN = "machinegun";
    public static final String THOR = "thor";
    public static final String PLASMA_GUN = "plasmagun";
    public static final String WHISPER = "whisper";
    public static final String ELECTROSCYTHE = "electroscythe";
    public static final String TRACTOR_BEAM = "tractorbeam";
    public static final String VORTEX_CANNON = "vortexcannon";
    public static final String FURNACE = "furnace";
    public static final String HEATSEEKER = "heatseeker";
    public static final String HELLION = "hellion";
    public static final String FLAMETHROWER = "flamethrower";
    public static final String GRENADE_LAUNCHER = "grenadelauncher";
    public static final String ROCKET_LAUNCHER = "rocketlauncher";
    public static final String RAILGUN = "railgun";
    public static final String CYBERBLADE = "cyberblade";
    public static final String CYBERBLADE_2 = "cyberblade2";
    public static final String ZX_2 = "zx-2";
    public static final String SHOTGUN = "shotgun";
    public static final String POWER_GLOVE = "powerglove";
    public static final String SHOCKWAVE = "shockwave";
    public static final String SLEDGEHAMMER = "sledgehammer";

    //weapons' effects
    public static final String EFFECT = "Effect:";
    public static final String BASIC_EFFECT = "Basic effect:";
    public static final String WITH_SECOND_LOCK = "With second lock:";
    public static final String WITH_FOCUS_SHOT = "With focus shot:";
    public static final String WITH_TURRET_TRIPOD = "With turret tripod:";
    public static final String WITH_CHAIN_REACTION = "With chain reaction:";
    public static final String WITH_HIGH_VOLTAGE = "With high voltage:";
    public static final String WITH_PHASE_GLIDE = "With phase glide:";
    public static final String WITH_CHARGED_SHOT = "With charged shot:";
    public static final String WITH_BLACK_HOLE = "With black hole:";
    public static final String WITH_EXTRA_GRENADE = "With extra grenade:";
    public static final String WITH_ROCKET_JUMP = "With rocket jump:";
    public static final String WITH_SHADOW_STEP = "With shadow step:";
    public static final String WITH_SLICE_AND_DICE = "With slice and dice:";

    //weapons' modes
    public static final String BASIC_MODE = "Basic mode:";
    public static final String IN_REAPER_MODE = "In reaper mode:";
    public static final String IN_PUNISHER_MODE = "In punisher mode:";
    public static final String IN_COZY_FIRE_MODE = "In cozy fire mode:";
    public static final String IN_NANO_TRACER_MODE = "In nano-tracer mode:";
    public static final String IN_BARBECUE_MODE = "In barbecue mode:";
    public static final String IN_PIERCING_MODE = "In piercing mode:";
    public static final String IN_SCANNER_MODE = "In scanner mode:";
    public static final String IN_LONG_BARREL_MODE = "In long barrel mode:";
    public static final String IN_ROCKET_FIST_MODE = "In rocket fist mode:";
    public static final String IN_TSUNAMI_MODE = "In tsunami mode:";
    public static final String IN_PULVERIZE_MODE = "In pulverize mode:";

    //effects
    public static final String PARAMETERS = "Parameters:";
    public static final String VICTIM = "<victim>";
    public static final String FIRST_VICTIM = "<first_victim>";
    public static final String SECOND_VICTIM = "<second_victim>";
    public static final String THIRD_VICTIM = "<third_victim>";
    public static final String ONE = "<1>";
    public static final String TWO = "<2>";
    public static final String THREE = "<3>";
    public static final String DIRECTION = "<direction>";
    public static final String FIRST_DIRECTION = "<first_direction>";
    public static final String SECOND_DIRECTION = "<second_direction>";
    public static final String SQUARE_X = "<squareX>";
    public static final String SQUARE_Y = "<squareY>";
    public static final String VICTIM_DIRECTION = "<victim_direction>";
    public static final String SHOOTER_DIRECTION = "<shooter_direction>";
    public static final String AMMO = "<ammo>";

    //descriptions
    public static final String LOCK_RIFLE_BASIC = "Deal 2 damage and 1 mark to 1 target you can see.";
    public static final String LOCK_RIFLE_SECOND = "Deal 1 mark to a different target you can see.";
    public static final String MACHINE_GUN_BASIC = "Choose 1 or 2 targets you can see and deal 1 damage to each";
    public static final String MACHINE_GUN_SECOND = "Deal 1 additional damage to one of those targets.";
    public static final String MACHINE_GUN_THIRD = "Deal 1 additional damage to the other of those targets and/or deal 1 damage to a different target you can see.";
    public static final String THOR_BASIC = "Deal 2 damage to 1 target you can see";
    public static final String THOR_SECOND = "Deal 1 damage to a second target that your first target can see.";
    public static final String THOR_THIRD = "Deal 2 damage to a third target that your second target can see. You cannot use this effect unless you first use the second effect.";
    public static final String PLASMA_GUN_BASIC = "Deal 2 damage to 1 target you can see";
    public static final String PLASMA_GUN_SECOND = "Move 1 or 2 squares. This effect can be used either before or after the basic effect.";
    public static final String PLASMA_GUN_THIRD = "Deal 1 additional damage to your target.";
    public static final String WHISPER_BASIC = "Deal 3 damage and 1 mark to 1 target you can see.Your target must be at least 2 moves away from you.";
    public static final String ELECTROSCYTHE_BASIC = "Deal 1 damage to every other player on your square";
    public static final String ELECTROSCYTHE_SECOND = "Deal 2 damage to every other player on your square.";
    public static final String TRACTOR_BEAM_FIRST = "Move a target 0,1 or 2 squares to a square you can see, and give it 1 damage";
    public static final String TRACTOR_BEAM_SECOND = "Choose a target 0, 1, or 2 moves away from you. Move the target to your square and deal 3 damage to it.";
    public static final String VORTEX_CANNON_BASIC = "Choose a square you can see, but not your square.Call it the vortex. Choose a target on the vortex or 1 move away from it.Move it onto the vortex and give it 2 damage";
    public static final String VORTEX_CANNON_SECOND = "Choose up to 2 other targets on the vortex or 1 move away from it. Move them onto the vortex and give them each 1 damage.";
    public static final String FURNACE_FIRST = "Choose a room you can see, but not the room you are in.Deal 1 damage to every";
    public static final String FURNACE_SECOND = "Choose a square exactly one move away. Deal 1 damage and 1 mark to everyone on that square.";
    public static final String HEATSEEKER_BASIC = "Choose 1 target you cannot see and deal 3 damage to it. Notes: Yes,this can only hit targets you cannot see.";
    public static final String HELLION_FIRST = "Deal 1 damage to 1 target you can see at least 1 move away.Then give 1 mark to that target and everyone else on that square.";
    public static final String HELLION_SECOND = "Deal 1 damage to 1 target you can see at least 1 move away. Then give 2 marks to that target and everyone else on that square.";
    public static final String FLAMETHROWER_FIRST = "Choose a square 1 move away and possibly a second square 1 more move away in the same direction. On each square, you may choose 1 target and give it 1 damage.";
    public static final String FLAMETHROWER_SECOND = "Choose 2 squares as above. Deal 2 damage to everyone on the first square and 1 damage to everyone on the second square.";
    public static final String GRENADE_LAUNCHER_BASIC = "Deal 1 damage to 1 target you can see.Then you may move the target 1 square.";
    public static final String GRENADE_LAUNCHER_SECOND = " Deal 1 damage to every player on a square you can see. You can use this before or after the basic effect's move.";
    public static final String ROCKET_LAUNCHER_BASIC = "Deal 2 damage to 1 target you can see that is not on your square.Then you may move the target 1 square.";
    public static final String ROCKET_LAUNCHER_SECOND = "Move 1 or 2 squares. This effect can be used either before or after the basic effect.";
    public static final String ROCKET_LAUNCHER_THIRD = "During the basic effect, deal 1 damage to every player on your target's original square – including the target, even if you move it.";
    public static final String RAILGUN_FIRST = "Choose a cardinal direction and 1 target in that direction.Deal 3 damage to it.";
    public static final String RAILGUN_SECOND = "Choose a cardinal direction and 1 or 2 targets in that direction. Deal 2 damage to each.";
    public static final String CYBERBLADE_BASIC = "Deal 2 damage to 1 target on your square.";
    public static final String CYBERBLADE_SECOND = "Move 1 square before or after the basic effect.";
    public static final String CYBERBLADE_THIRD = "Deal 2 damage to a different target on your square. The shadowstep may be used before or after this effect.";
    public static final String ZX2_FIRST = "Deal 1 damage and 2 marks to 1 target you can see.";
    public static final String ZX2_SECOND = "Choose up to 3 targets you can see and deal 1 mark to each.";
    public static final String SHOTGUN_FIRST = "Deal 3 damage to 1 target on your square. If you want, you may then move the target 1 square.";
    public static final String SHOTGUN_SECOND =  "Deal 2 damage to 1 target on any square exactly one move away.";
    public static final String POWER_GLOVE_FIRST = "Choose 1 target on any square exactly 1 move away. Move onto that square and give the target 1 damage and 2 marks.";
    public static final String POWER_GLOVE_SECOND = "Choose a square exactly 1 move away. Move onto that square. You may deal 2 damage to 1 target there. If you want, you may move 1 more square in that same direction (but only if it is a legal move). You may deal 2 damage to 1 target there, as well.";
    public static final String SHOCKWAVE_FIRST = "Choose up to 3 targets on different squares, each exactly 1 move away.Deal 1 damage to each target.";
    public static final String SHOCKWAVE_SECOND = "Deal 1 damage to all targets that are exactly 1 move away.";
    public static final String SLEDGEHAMMER_FIRST = "Deal 2 damage to 1 target on your square.";
    public static final String SLEDGEHAMMER_SECOND = "Deal 3 damage to 1 target on your square, then move that target 0, 1, or 2 squares in one direction.";


    //do you want
    public static final String CHOOSE_EFFECT = "Choose your effect:";
    public static final String MOVE_FIRST = "Do you want to move first?:";
    public static final String SECOND_EFFECT_FIRST = "Do you want to use the second effect first?:";
    public static final String MOVE_AFTER_THIRD = "Do you want to move after the third effect?:";

    //generic
    public static final String SPACE = " ";
    public static final String MULTIPLE_SPACE = "   ";
    public static final String COLON = ":";
    public static final String NEW_LINE = "\n";
    public static final String OR = "or";
}
