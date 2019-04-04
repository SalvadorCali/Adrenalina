package it.polimi.ingsw.util;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.gamecomponents.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Parser(){}

    public static Deck createWeapons(){
        Deck weapons = new Deck();
        String cardName;
        Color cardColor;
        for(int i=0; i < 21; i++){
            InputStream input = Parser.class.getClassLoader().getResourceAsStream("cardconfig.json");
            Object reader = null;
            try {
                reader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject firstObject = (JSONObject) reader;
            firstObject = (JSONObject)firstObject.get("cardconfig");
            JSONArray firstArray = (JSONArray) firstObject.get("elements");
            JSONObject secondObject = (JSONObject) firstArray.get(0);
            JSONArray secondArray = (JSONArray) secondObject.get("element");
            JSONObject thirdObject = (JSONObject) secondArray.get(i);

            cardName = (String) thirdObject.get("name");
            cardColor = castStringToColor((String) thirdObject.get("colour"));
            Card card = new WeaponCard(cardName, cardColor);
            weapons.addCard(card);
        }
        weapons.shuffle();
        return weapons;
    }

    public static Deck createPowerups(){
        Deck powerups = new Deck();
        String cardName;
        for(int i=0; i < 4; i++){
            InputStream input = Parser.class.getClassLoader().getResourceAsStream("cardconfig.json");
            Object reader = null;
            try {
                reader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject firstObject = (JSONObject) reader;
            firstObject = (JSONObject)firstObject.get("cardconfig");
            JSONArray firstArray = (JSONArray) firstObject.get("elements");
            JSONObject secondObject = (JSONObject) firstArray.get(2);
            JSONArray secondArray = (JSONArray) secondObject.get("element");
            JSONObject thirdObject = (JSONObject) secondArray.get(i);

            cardName = (String) thirdObject.get("name");
            Card card = new PowerupCard(cardName, Color.BLUE);
            powerups.addCard(card);
            card = new PowerupCard(cardName, Color.YELLOW);
            powerups.addCard(card);
            card = new PowerupCard(cardName, Color.RED);
            powerups.addCard(card);
        }
        powerups.shuffle();
        return powerups;
    }

    public static List<GameBoard> createGameBoards(){
        List<GameBoard> gameBoards = new ArrayList<>();
        BoardType boardType;
        TokenColor color;
        Cardinal north, south, east, west;
        //Square[][] arena = new Square[3][4];

        InputStream input = Parser.class.getClassLoader().getResourceAsStream("gameboard.json");
        Object reader = null;
        try {
            reader = new JSONParser().parse(new InputStreamReader(input));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject firstObject = (JSONObject) reader;
        firstObject = (JSONObject)firstObject.get("gameboard");
        JSONArray firstArray = (JSONArray) firstObject.get("elements");


        for(int i=0; i < 4; i++){
            Square[][] arena = new Square[3][4];
            JSONObject secondObject = (JSONObject) firstArray.get(i);
            boardType = castStringToBoardType((String) secondObject.get("type"));
            JSONArray secondArray = (JSONArray) secondObject.get("squares");

            int l = 0;
            for(int j=0; j < 3; j++){
                for(int k=0; k < 4; k++){
                    JSONObject thirdObject = (JSONObject) secondArray.get(l);
                    color = castStringToTokenColor((String) thirdObject.get("color"));
                    north = castStringToCardinal((String) thirdObject.get("north"));
                    south = castStringToCardinal((String) thirdObject.get("south"));
                    east = castStringToCardinal((String) thirdObject.get("east"));
                    west = castStringToCardinal((String) thirdObject.get("west"));
                    switch((String) thirdObject.get("type")){
                        case "ammo":
                            arena[j][k] = new AmmoPoint(color, north, south, west, east);
                            break;
                        case "spawn":
                            arena[j][k] = new SpawnPoint(color, north, south, west, east);
                            break;
                        default:
                            arena[j][k] = new InactivePoint(color, north, south, west, east);
                            break;
                    }
                    l++;
                }
            }
            /*
            for(int j = 0; j<3; j++){
                for(int k=0; k<4; k++){
                    System.out.println(arena[j][k].getColor());
                }
            }
            */
            gameBoards.add(new GameBoard(boardType, arena));
            //System.out.println(gameBoards.get(i).getType());
        }
/*
        for(int i = 0; i<4; i++){
            for(int j = 0; j<3; j++){
                for(int k=0; k<4; k++){
                    System.out.println(gameBoards.get(i).getArena()[j][k].getColor());
                }
            }
        }
        */
        return gameBoards;
    }

    private static Color castStringToColor(String color){
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

    private static TokenColor castStringToTokenColor(String color){
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

    private static BoardType castStringToBoardType(String boardType){
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

    private static Cardinal castStringToCardinal(String cardinal){
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
}