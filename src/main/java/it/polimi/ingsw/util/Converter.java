package it.polimi.ingsw.util;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.enums.*;

/**
 * This class has methods that takes an object and returns another type of object.
 */
public class Converter {
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
                    first = "B";
                    break;
                case RED:
                    first = "R";
                    break;
                case YELLOW:
                    first = "Y";
                    break;
                default:
                    break;
            }
        switch (ammoCard.getSecondAmmo().getColor()){
            case BLUE:
                second = "B";
                break;
            case RED:
                second = "R";
                break;
            case YELLOW:
                second = "Y";
                break;
            default:
                break;
        }
        if(!ammoCard.isPowerup()) {
            switch (ammoCard.getThirdAmmo().getColor()) {
                case BLUE:
                    third = "B";
                    break;
                case RED:
                    third = "R";
                    break;
                case YELLOW:
                    third = "Y";
                    break;
                case NONE:
                    third = "";
                    break;
                default:
                    break;
            }
        }else{
            third = "P";
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
            case "up":
                return Direction.UP;
            case "down":
                return Direction.DOWN;
            case "left":
                return Direction.LEFT;
            case "right":
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
                return "none";
            case SKULL:
                return "skull";
            case BLUE:
                return "blue";
            case GREEN:
                return "green";
            case GREY:
                return "grey";
            case PURPLE:
                return "purple";
            case RED:
                return "red";
            case YELLOW:
                return "yellow";
            default:
                return "none";
        }
    }

    /**
     * Takes a String and returns a Color.
     * @param color a String.
     * @return a Color.
     */
    public static Color fromStringToColor(String color){
        switch(color){
            case "B":
                return Color.BLUE;
            case "R":
                return Color.RED;
            case "Y":
                return Color.YELLOW;
            case "blue":
                return Color.BLUE;
            case "red":
                return Color.RED;
            case "yellow":
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
            case "blue":
                return TokenColor.BLUE;
            case "green":
                return TokenColor.GREEN;
            case "grey":
                return TokenColor.GREY;
            case "purple":
                return TokenColor.PURPLE;
            case "red":
                return TokenColor.RED;
            case "yellow":
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
            case "basic":
                return BoardType.BASIC;
            case "generic":
                return BoardType.GENERIC;
            case "3_4":
                return BoardType.PLAYERS_3_4;
            case "4_5":
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
            case "door":
                return Cardinal.DOOR;
            case "room":
                return Cardinal.ROOM;
            case "wall":
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
                return "B";
            case RED:
                return "R";
            case YELLOW:
                return "Y";
            default:
                return "N";
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
                return "\033[0;36m";

            case BLUE:
                return "\033[0;34m";

            case SKULL:
                return "\033[0;36m";

            case RED:
                return "\033[0;31m";

            case PURPLE:
                return "\033[0;35m";

            case GREEN:
                return "\033[0;32m";

            case YELLOW:
                return "\033[0;33m";

            case GREY:
                return "\033[0;37m";

            default:
                return "\033[0;36m";
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
                return "\033[0;36m";

            case YELLOW:
                return "\033[0;33m";

            case RED:
                return "\033[0;31m";

            case BLUE:
                return "\033[0;34m";

            default:
                return "\033[0;36m";
        }
    }

    /**
     * Takes a powerup name and convert it to uppercase.
     * @param powerup a powerup name.
     * @return the name in uppercase.
     */
    public static String powerupName(String powerup){
        switch (powerup){
            case "targetingscope":
                return "TARGETING SCOPE";
            case "newton":
                return "NEWTON";
            case "tagbackgrenade":
                return "TAGBACK GRENADE";
            case "teleporter":
                return "TELEPORTER";
            default:
                return null;
        }
    }

    public static String powerupNameInvert(String powerup){
        switch (powerup){
            case "TARGETING SCOPE":
                return "targetingscope";
            case "NEWTON":
                return "newton";
            case "TAGBACK GRENADE":
                return "tagbackgrenade";
            case "TELEPORTER":
                return "teleporter";
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
            case "lockrifle":
                return "LOCK RIFLE";
            case "electroscythe":
                return "ELECTROSCYTHE";
            case "furnace":
                return "FURNACE";
            case "heatseeker":
                return "HEATSEEKER";
            case "whisper":
                return "WHISPER";
            case "hellion":
                return "HELLION";
            case "zx-2":
                return "ZX-2";
            case "tractorbeam":
                return "TRACTOR BEAM";
            case "shotgun":
                return "SHOTGUN";
            case "plasmagun":
                return "PLASMA GUN";
            case "machinegun":
                return "MACHINE GUN";
            case "flamethrower":
                return "FLAMETHROWER";
            case "shockwave":
                return "SHOCKWAVE";
            case "vortexcannon":
                return "VORTEX CANNON";
            case "thor":
                return "T.H.O.R.";
            case "grenadelauncher":
                return "GRENADE LAUNCHER";
            case "rocketlauncher":
                return "ROCKET LAUNCHER";
            case "railgun":
                return "RAILGUN";
            case "cyberblade":
                return "CYBERBLADE";
            case "powerglove":
                return "POWER GLOVE";
            case "sledgehammer":
                return "SLEDGEHAMMER";
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
            case "LOCK RIFLE":
                return "lockrifle";
            case "ELECTROSCYTHE":
                return "electroscythe";
            case "FURNACE":
                return "furnace";
            case "HEATSEEKER":
                return "heatseeker";
            case "WHISPER":
                return "whisper";
            case "HELLION":
                return "hellion";
            case "ZX-2":
                return "zx-2";
            case "TRACTOR BEAM":
                return "tractorbeam";
            case "SHOTGUN":
                return "shotgun";
            case "PLASMA GUN":
                return "plasmagun";
            case "MACHINE GUN":
                return "machinegun";
            case "FLAMETHROWER":
                return "flamethrower";
            case "SHOCKWAVE":
                return "shockwave";
            case "VORTEX CANNON":
                return "vortexcannon";
            case "T.H.O.R.":
                return "thor";
            case "GRENADE LAUNCHER":
                return "grenadelauncher";
            case "ROCKET LAUNCHER":
                return "rocketlauncher";
            case "RAILGUN":
                return "railgun";
            case "CYBERBLADE":
                return "cyberblade";
            case "POWER GLOVE":
                return "powerglove";
            case "SLEDGEHAMMER":
                return "sledgehammer";
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
                return "blue";
            case RED:
                return "red";
            case YELLOW:
                return "yellow";
            default:
                return "none";
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
