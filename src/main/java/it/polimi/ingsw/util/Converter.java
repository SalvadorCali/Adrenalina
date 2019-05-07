package it.polimi.ingsw.util;

import it.polimi.ingsw.model.enums.*;

public class Converter {
    private Converter(){}

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
}
