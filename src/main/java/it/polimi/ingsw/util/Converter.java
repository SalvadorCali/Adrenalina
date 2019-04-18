package it.polimi.ingsw.util;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;

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
}
