package it.polimi.ingsw.util;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.enums.*;

/**
 * This class has methods that takes an object and returns another type of object.
 */
public class Converter {
    private static final String B = "B";
    private static final String R = "R";
    private static final String Y = "Y";
    private static final String N = "N";
    private static final String P = "P";
    private static final String NOTHING = "";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String ANSI_BLUE = "\033[0;34m";
    private static final String ANSI_CYAN = "\033[0;36m";
    private static final String ANSI_GREY = "\033[0;37m";
    private static final String ANSI_GREEN = "\033[0;32m";
    private static final String ANSI_PURPLE = "\033[0;35m";
    private static final String ANSI_RED = "\033[0;31m";
    private static final String ANSI_YELLOW = "\033[0;33m";
    private static final String COLOR_BLUE = "blue";
    private static final String COLOR_GREY = "grey";
    private static final String COLOR_GREEN = "green";
    private static final String COLOR_PURPLE = "purple";
    private static final String COLOR_RED = "red";
    private static final String COLOR_YELLOW = "yellow";
    private static final String COLOR_SKULL = "skull";
    private static final String COLOR_NONE = "none";
    private static final String BOARD_BASIC = "basic";
    private static final String BOARD_GENERIC = "generic";
    private static final String BOARD_3_4 = "3_4";
    private static final String BOARD_4_5 = "4_5";
    private static final String DOOR = "door";
    private static final String ROOM = "room";
    private static final String WALL = "wall";
    private static final String LOCK_RIFLE = "lockrifle";
    private static final String MACHINE_GUN = "machinegun";
    private static final String THOR = "thor";
    private static final String PLASMA_GUN = "plasmagun";
    private static final String WHISPER = "whisper";
    private static final String ELECTROSCYTHE = "electroscythe";
    private static final String TRACTOR_BEAM = "tractorbeam";
    private static final String VORTEX_CANNON = "vortexcannon";
    private static final String FURNACE = "furnace";
    private static final String HEATSEEKER = "heatseeker";
    private static final String HELLION = "hellion";
    private static final String FLAMETHROWER = "flamethrower";
    private static final String GRENADE_LAUNCHER = "grenadelauncher";
    private static final String ROCKET_LAUNCHER = "rocketlauncher";
    private static final String RAILGUN = "railgun";
    private static final String CYBERBLADE = "cyberblade";
    private static final String ZX_2 = "zx-2";
    private static final String SHOTGUN = "shotgun";
    private static final String POWER_GLOVE = "powerglove";
    private static final String SHOCKWAVE = "shockwave";
    private static final String SLEDGEHAMMER = "sledgehammer";
    private static final String LOCKRIFLECAPS = "LOCK RIFLE";
    private static final String ELECTROSCYTHECAPS = "ELECTROSCYTHE";
    private static final String MACHINEGUNCAPS = "MACHINE GUN";
    private static final String TRACTORBEAMCAPS = "TRACTOR BEAM";
    private static final String THORCAPS = "T.H.O.R.";
    private static final String VORTEXCANNONCAPS = "VORTEX CANNON";
    private static final String FURNACECAPS = "FURNACE";
    private static final String PLASMAGUNCAPS = "PLASMA GUN";
    private static final String HEATSEEKERCAPS = "HEATSEEKER";
    private static final String WHISPERCAPS = "WHISPER";
    private static final String HELLIONCAPS = "HELLION";
    private static final String FLAMETHROWERCAPS = "FLAMETHROWER";
    private static final String ZX2CAPS = "ZX-2";
    private static final String GRENADELAUNCHERCAPS = "GRENADE LAUNCHER";
    private static final String SHOTGUNCAPS = "SHOTGUN";
    private static final String ROCKETLAUNCHERCAPS = "ROCKET LAUNCHER";
    private static final String POWERGLOVECAPS = "POWER GLOVE";
    private static final String RAILGUNCAPS = "RAILGUN";
    private static final String CYBERBLADECAPS = "CYBERBLADE";
    private static final String SLEDGEHAMMERCAPS = "SLEDGEHAMMER";
    private static final String SHOCKWAVECAPS = "SHOCKWAVE";
    private static final String TARGETING_SCOPE = "targetingscope";
    private static final String TAGBACK_GRENADE = "tagbackgrenade";
    private static final String NEWTON = "newton";
    private static final String TELEPORTER = "teleporter";
    private static final String TARGETINGSCOPECAPS = "TARGETING SCOPE";
    private static final String NEWTONCAPS = "NEWTON";
    private static final String TAGBACKGRENADECAPS = "TAGBACK GRENADE";
    private static final String TELEPORTERCAPS = "TELEPORTER";
    /**
     * Class constructor.
     */
    private Converter(){}

    /**
     * Takes a string and converts it to an integer.
     * @param number a string that will be converted.
     * @return an integer.
     */
    public static int fromStringToInt(String number){
        try{
            Integer.parseInt(number);
        }catch(NumberFormatException e){
            return -1;
        }
        return Integer.parseInt(number);
    }

    /**
     * Takes a direction and returns the opposite direction.
     * @param direction a direction that will be converted.
     * @return the opposite direction.
     */
    public static Direction fromDirectionToOpposite(Direction direction){
        switch(direction){
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case RIGHT:
                return Direction.LEFT;
            case LEFT:
                return Direction.RIGHT;
            default:
                return null;
        }
    }

    /**
     * Takes an ammo card and returns a String that represents the ammos.
     * @param ammoCard the ammo card that will be converted.
     * @return a string of letters that represent the ammo card.
     */
    public static String fromAmmoCardToString(AmmoCard ammoCard){
        String first = new String();
        String second = new String();
        String third = new String();
            switch (ammoCard.getFirstAmmo().getColor()){
                case BLUE:
                    first = B;
                    break;
                case RED:
                    first = R;
                    break;
                case YELLOW:
                    first = Y;
                    break;
                default:
                    break;
            }
        switch (ammoCard.getSecondAmmo().getColor()){
            case BLUE:
                second = B;
                break;
            case RED:
                second = R;
                break;
            case YELLOW:
                second = Y;
                break;
            default:
                break;
        }
        if(!ammoCard.isPowerup()) {
            switch (ammoCard.getThirdAmmo().getColor()) {
                case BLUE:
                    third = B;
                    break;
                case RED:
                    third = R;
                    break;
                case YELLOW:
                    third = Y;
                    break;
                case NONE:
                    third = NOTHING;
                    break;
                default:
                    break;
            }
        }else{
            third = P;
        }

        return first+second+third;
    }

    /**
     * Takes a String and return the relative Direction.
     * @param direction a String that will be converted.
     * @return a Direction.
     */
    public static Direction fromStringToDirection(String direction){
        switch(direction){
            case UP:
                return Direction.UP;
            case DOWN:
                return Direction.DOWN;
            case LEFT:
                return Direction.LEFT;
            case RIGHT:
                return Direction.RIGHT;
            default:
                return null;
        }
    }

    /**
     * Takes a TokenColor and returns a String that represents it.
     * @param tokenColor a TokenColor.
     * @return a String.
     */
    public static String fromTokenColorToString(TokenColor tokenColor){
        switch(tokenColor){
            case NONE:
                return COLOR_NONE;
            case SKULL:
                return COLOR_SKULL;
            case BLUE:
                return COLOR_BLUE;
            case GREEN:
                return COLOR_GREEN;
            case GREY:
                return COLOR_GREY;
            case PURPLE:
                return COLOR_PURPLE;
            case RED:
                return COLOR_RED;
            case YELLOW:
                return COLOR_YELLOW;
            default:
                return COLOR_NONE;
        }
    }

    /**
     * Takes a String and returns a Color.
     * @param color a String.
     * @return a Color.
     */
    public static Color fromStringToColor(String color){
        switch(color){
            case B:
                return Color.BLUE;
            case R:
                return Color.RED;
            case Y:
                return Color.YELLOW;
            case COLOR_BLUE:
                return Color.BLUE;
            case COLOR_RED:
                return Color.RED;
            case COLOR_YELLOW:
                return Color.YELLOW;
            default:
                return Color.NONE;
        }
    }

    /**
     * Takes a Color and returns the relative String.
     * @param color a String.
     * @return a Color.
     */
    public static TokenColor fromStringToTokenColor(String color){
        switch(color){
            case COLOR_BLUE:
                return TokenColor.BLUE;
            case COLOR_GREEN:
                return TokenColor.GREEN;
            case COLOR_GREY:
                return TokenColor.GREY;
            case COLOR_PURPLE:
                return TokenColor.PURPLE;
            case COLOR_RED:
                return TokenColor.RED;
            case COLOR_YELLOW:
                return TokenColor.YELLOW;
            default:
                return TokenColor.NONE;
        }
    }

    /**
     * Takes a String and returns the relative BoardType.
     * @param boardType a String.
     * @return the relative BoardType.
     */
    public static BoardType fromStringToBoardType(String boardType){
        switch(boardType){
            case BOARD_BASIC:
                return BoardType.BASIC;
            case BOARD_GENERIC:
                return BoardType.GENERIC;
            case BOARD_3_4:
                return BoardType.PLAYERS_3_4;
            case BOARD_4_5:
                return BoardType.PLAYERS_4_5;
            default:
                return BoardType.BASIC;
        }
    }

    /**
     * Takes a BoardType and returns a number.
     * @param boardType a BoardType.
     * @return a number that represents the BoardType object.
     */
    public static Integer fromBoardTypeToInt(BoardType boardType){
        switch (boardType){
            case BASIC:
                return 0;
            case GENERIC:
                return 1;
            case PLAYERS_3_4:
                return 2;
            case PLAYERS_4_5:
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Takes a String and returns the relative Cardinal object.
     * @param cardinal a String.
     * @return the relative Cardinal object.
     */
    public static Cardinal fromStringToCardinal(String cardinal){
        switch (cardinal){
            case DOOR:
                return Cardinal.DOOR;
            case ROOM:
                return Cardinal.ROOM;
            case WALL:
                return Cardinal.WALL;
            default:
                return Cardinal.NONE;
        }
    }

    /**
     * Takes a Color and returns the relative letter.
     * @param color a Color.
     * @return the relative letter.
     */
    public static String fromColorToLetter(Color color){
        switch (color){
            case BLUE:
                return B;
            case RED:
                return R;
            case YELLOW:
                return Y;
            default:
                return N;
        }
    }

    /**
     * Takes a TokenColor and returns the relative ANSI String.
     * @param tokenColor a TokenColor.
     * @return the relative ANSI String.
     */
    public static String fromTokenColorToCLIColor(TokenColor tokenColor){

        switch (tokenColor){

            case NONE:
                return ANSI_CYAN;

            case BLUE:
                return ANSI_BLUE;

            case SKULL:
                return ANSI_CYAN;

            case RED:
                return ANSI_RED;

            case PURPLE:
                return ANSI_PURPLE;

            case GREEN:
                return ANSI_GREEN;

            case YELLOW:
                return ANSI_YELLOW;

            case GREY:
                return ANSI_GREY;

            default:
                return ANSI_CYAN;
        }
    }

    /**
     * Takes a Color and returns the relative ANSI String.
     * @param color a Color.
     * @return an ANSI String.
     */
    public static String fromColorToCLIColor(Color color){

        switch (color){
            case NONE:
                return ANSI_CYAN;

            case YELLOW:
                return ANSI_YELLOW;

            case RED:
                return ANSI_RED;

            case BLUE:
                return ANSI_BLUE;

            default:
                return ANSI_CYAN;
        }
    }

    /**
     * Takes a powerup name and convert it to uppercase.
     * @param powerup a powerup name.
     * @return the name in uppercase.
     */
    public static String powerupName(String powerup){
        switch (powerup){
            case TARGETING_SCOPE:
                return TARGETINGSCOPECAPS;
            case NEWTON:
                return NEWTONCAPS;
            case TAGBACK_GRENADE:
                return TAGBACKGRENADECAPS;
            case TELEPORTER:
                return TELEPORTERCAPS;
            default:
                return null;
        }
    }

    public static String powerupNameInvert(String powerup){
        switch (powerup){
            case TARGETINGSCOPECAPS:
                return TARGETING_SCOPE;
            case NEWTONCAPS:
                return NEWTON;
            case TAGBACKGRENADECAPS:
                return TAGBACK_GRENADE;
            case TELEPORTERCAPS:
                return TELEPORTER;
            default:
                return null;
        }
    }

    /**
     * Takes a weapon name and converts it to uppercase.
     * @param weapon a weapon name.
     * @return the name in uppercase.
     */
    public static String weaponName(String weapon){
        switch (weapon){
            case LOCK_RIFLE:
                return LOCKRIFLECAPS;
            case ELECTROSCYTHE:
                return ELECTROSCYTHECAPS;
            case FURNACE:
                return FURNACECAPS;
            case HEATSEEKER:
                return HEATSEEKERCAPS;
            case WHISPER:
                return WHISPERCAPS;
            case HELLION:
                return HELLIONCAPS;
            case ZX_2:
                return ZX2CAPS;
            case TRACTOR_BEAM:
                return TRACTORBEAMCAPS;
            case SHOTGUN:
                return SHOTGUNCAPS;
            case PLASMA_GUN:
                return PLASMAGUNCAPS;
            case MACHINE_GUN:
                return MACHINEGUNCAPS;
            case FLAMETHROWER:
                return FLAMETHROWERCAPS;
            case SHOCKWAVE:
                return SHOCKWAVECAPS;
            case VORTEX_CANNON:
                return VORTEXCANNONCAPS;
            case THOR:
                return THORCAPS;
            case GRENADE_LAUNCHER:
                return GRENADELAUNCHERCAPS;
            case ROCKET_LAUNCHER:
                return ROCKETLAUNCHERCAPS;
            case RAILGUN:
                return RAILGUNCAPS;
            case CYBERBLADE:
                return CYBERBLADECAPS;
            case POWER_GLOVE:
                return POWERGLOVECAPS;
            case SLEDGEHAMMER:
                return SLEDGEHAMMERCAPS;
            default:
                return null;
        }
    }

    /**
     * Takes a weapon name in uppercase and returns it in lowercase.
     * @param weapon a weapon name.
     * @return the name in lowercase.
     */
    public static String weaponNameInvert(String weapon){
        switch (weapon){
            case LOCKRIFLECAPS:
                return LOCK_RIFLE;
            case ELECTROSCYTHECAPS:
                return ELECTROSCYTHE;
            case FURNACECAPS:
                return FURNACE;
            case HEATSEEKERCAPS:
                return HEATSEEKER;
            case WHISPERCAPS:
                return WHISPER;
            case HELLIONCAPS:
                return HELLION;
            case ZX2CAPS:
                return ZX_2;
            case TRACTORBEAMCAPS:
                return TRACTOR_BEAM;
            case SHOTGUNCAPS:
                return SHOTGUN;
            case PLASMAGUNCAPS:
                return PLASMA_GUN;
            case MACHINEGUNCAPS:
                return MACHINE_GUN;
            case FLAMETHROWERCAPS:
                return FLAMETHROWER;
            case SHOCKWAVECAPS:
                return SHOCKWAVE;
            case VORTEXCANNONCAPS:
                return VORTEX_CANNON;
            case THORCAPS:
                return THOR;
            case GRENADELAUNCHERCAPS:
                return GRENADE_LAUNCHER;
            case ROCKETLAUNCHERCAPS:
                return ROCKET_LAUNCHER;
            case RAILGUNCAPS:
                return RAILGUN;
            case CYBERBLADECAPS:
                return CYBERBLADE;
            case POWERGLOVECAPS:
                return POWER_GLOVE;
            case SLEDGEHAMMERCAPS:
                return SLEDGEHAMMER;
            default:
                return null;
        }
    }

    /**
     * Takes a Color and returns the relative String.
     * @param color a Color.
     * @return the relative String.
     */
    public static String fromColorToString(Color color){
        switch (color){
            case BLUE:
                return COLOR_BLUE;
            case RED:
                return COLOR_RED;
            case YELLOW:
                return COLOR_YELLOW;
            default:
                return COLOR_NONE;
        }
    }

    public static javafx.scene.paint.Color fromColorEnumsToColorJFX(Color color){
        switch (color){
            case BLUE:
                return javafx.scene.paint.Color.BLUE;
            case RED:
                return javafx.scene.paint.Color.RED;
            case YELLOW:
                return javafx.scene.paint.Color.YELLOW;
            case NONE:
                return javafx.scene.paint.Color.TRANSPARENT;
            default:
                return javafx.scene.paint.Color.TRANSPARENT;
        }
    }
}
