package it.polimi.ingsw.util;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.enums.*;

public class Converter {
    private Converter(){}

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
}
