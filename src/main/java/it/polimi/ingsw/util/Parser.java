package it.polimi.ingsw.util;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static it.polimi.ingsw.util.Converter.*;

public class Parser {

    private Parser(){}

    public static Deck createWeapons(){
        Deck weapons = new Deck();
        String cardName;
        Color cardColor;
        int grabBlueAmmos, grabRedAmmos, grabYellowAmmos, reloadBlueAmmos, reloadRedAmmos, reloadYellowAmmos;
        for(int i=0; i < 21; i++){
            InputStream input = Parser.class.getClassLoader().getResourceAsStream("cardconfig.json");
            Object reader = null;
            try {
                reader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException e) {
                Printer.err(e);
            } catch (IOException e) {
                Printer.err(e);
            }
            JSONObject firstObject = (JSONObject) reader;
            firstObject = (JSONObject)firstObject.get("cardconfig");
            JSONArray firstArray = (JSONArray) firstObject.get("elements");
            JSONObject secondObject = (JSONObject) firstArray.get(0);
            JSONArray secondArray = (JSONArray) secondObject.get("element");
            JSONObject thirdObject = (JSONObject) secondArray.get(i);

            cardName = (String) thirdObject.get("name");
            cardColor = fromStringToColor((String) thirdObject.get("colour"));
            JSONArray grabAmmos = (JSONArray) thirdObject.get("grabAmmos");
            JSONObject grabAmmosObject = (JSONObject) grabAmmos.get(0);
            //JSONObject grabAmmos = (JSONObject) thirdObject.get("grabAmmos");
            grabBlueAmmos = (int)(long) grabAmmosObject.get("blue");
            grabRedAmmos = (int)(long) grabAmmosObject.get("red");
            grabYellowAmmos = (int)(long) grabAmmosObject.get("yellow");
            JSONArray reloadAmmos = (JSONArray) thirdObject.get("reloadAmmos");
            JSONObject reloadAmmosObject = (JSONObject) reloadAmmos.get(0);
            //JSONObject reloadAmmos = (JSONObject) thirdObject.get("reloadAmmos");
            reloadBlueAmmos = (int)(long) reloadAmmosObject.get("blue");
            reloadRedAmmos = (int)(long) reloadAmmosObject.get("red");
            reloadYellowAmmos = (int)(long) reloadAmmosObject.get("yellow");
            Card card = new WeaponCard(cardName, cardColor, grabRedAmmos, grabBlueAmmos, grabYellowAmmos, reloadRedAmmos, reloadBlueAmmos, reloadYellowAmmos);
            weapons.addCard(card);
        }
        weapons.shuffle();
        return weapons;
    }

    public static List<AmmoCard> createAmmos(){
        List<AmmoCard> ammos = new ArrayList<>();
        String combination;
        AmmoCard ammoCard;
        for(int i=0; i < 12; i++) {
            InputStream input = Parser.class.getClassLoader().getResourceAsStream("cardconfig.json");
            Object reader = null;
            try {
                reader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException e) {
                Printer.err(e);
            } catch (IOException e) {
                Printer.err(e);
            }
            JSONObject firstObject = (JSONObject) reader;
            firstObject = (JSONObject) firstObject.get("cardconfig");
            JSONArray firstArray = (JSONArray) firstObject.get("elements");
            JSONObject secondObject = (JSONObject) firstArray.get(1);
            JSONArray secondArray = (JSONArray) secondObject.get("element");
            JSONObject thirdObject = (JSONObject) secondArray.get(i);

            for(int j=0; j<3; j++){
                combination = (String) thirdObject.get("value");
                ammoCard = generateAmmoCard(combination);
                ammos.add(ammoCard);
            }
        }
        Collections.shuffle(ammos);
        return ammos;
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
                Printer.err(e);
            } catch (IOException e) {
                Printer.err(e);
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

        InputStream input = Parser.class.getClassLoader().getResourceAsStream("gameboard.json");
        Object reader = null;
        try {
            reader = new JSONParser().parse(new InputStreamReader(input));
        } catch (ParseException e) {
            Printer.err(e);
        } catch (IOException e) {
            Printer.err(e);
        }
        JSONObject firstObject = (JSONObject) reader;
        firstObject = (JSONObject)firstObject.get("gameboard");
        JSONArray firstArray = (JSONArray) firstObject.get("elements");


        for(int i=0; i < 4; i++){
            Square[][] arena = new Square[3][4];
            JSONObject secondObject = (JSONObject) firstArray.get(i);
            boardType = fromStringToBoardType((String) secondObject.get("type"));
            JSONArray secondArray = (JSONArray) secondObject.get("squares");

            int l = 0;
            for(int j=0; j < 3; j++){
                for(int k=0; k < 4; k++){
                    JSONObject thirdObject = (JSONObject) secondArray.get(l);
                    color = fromStringToTokenColor((String) thirdObject.get("color"));
                    north = fromStringToCardinal((String) thirdObject.get("north"));
                    south = fromStringToCardinal((String) thirdObject.get("south"));
                    east = fromStringToCardinal((String) thirdObject.get("east"));
                    west = fromStringToCardinal((String) thirdObject.get("west"));
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
            gameBoards.add(new GameBoard(boardType, arena));
        }
        return gameBoards;
    }

    private static AmmoCard generateAmmoCard(String combination){
        switch(combination){
            case "YBB":
                return new AmmoCard(new Ammo(Color.YELLOW), new Ammo(Color.BLUE), new Ammo(Color.BLUE));
            case "YRR":
                return new AmmoCard(new Ammo(Color.YELLOW), new Ammo(Color.RED), new Ammo(Color.RED));
            case "RBB":
                return new AmmoCard(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.BLUE));
            case "RYY":
                return new AmmoCard(new Ammo(Color.RED), new Ammo(Color.YELLOW), new Ammo(Color.YELLOW));
            case "BYY":
                return new AmmoCard(new Ammo(Color.BLUE), new Ammo(Color.YELLOW), new Ammo(Color.YELLOW));
            case "BRR":
                return new AmmoCard(new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.RED));
            case "PRR":
                return new AmmoCard(new Ammo(Color.RED), new Ammo(Color.RED));
            case "PBB":
                return new AmmoCard(new Ammo(Color.BLUE), new Ammo(Color.BLUE));
            case "PYY":
                return new AmmoCard(new Ammo(Color.YELLOW), new Ammo(Color.YELLOW));
            case "PRB":
                return new AmmoCard(new Ammo(Color.RED), new Ammo(Color.BLUE));
            case "PRY":
                return new AmmoCard(new Ammo(Color.RED), new Ammo(Color.YELLOW));
            case "PBY":
                return new AmmoCard(new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
            default:
                return new AmmoCard(new Ammo(Color.NONE), new Ammo(Color.NONE));
        }
    }
}