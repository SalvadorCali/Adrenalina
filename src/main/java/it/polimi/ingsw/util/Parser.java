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

/**
 * This class contains method to initialize the game from json files.
 */
public class Parser {
    private static final String CARD_FILE = "cardconfig.json";
    private static final String CARD_CONFIG = "cardconfig";
    private static final String ELEMENTS = "elements";
    private static final String ELEMENT = "element";
    private static final String NAME = "name";
    private static final String COLOUR = "colour";
    private static final String COLOR = "color";
    private static final String EFFECT = "effect";
    private static final String GRAB_AMMOS = "grabAmmos";
    private static final String RELOAD_AMMOS = "reloadAmmos";
    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String YELLOW = "yellow";
    private static final String VALUE = "value";
    private static final String INFO_CARD_EFFECT = "info_card_effect";
    private static final String BOARD_FILE = "gameboard.json";
    private static final String GAMEBOARD = "gameboard";
    private static final String TYPE = "type";
    private static final String SQUARES = "squares";
    private static final String NORTH = "north";
    private static final String SOUTH = "south";
    private static final String EAST = "east";
    private static final String WEST = "west";
    private static final String AMMO = "ammo";
    private static final String SPAWN = "spawn";

    /**
     * Class constructor.
     */
    private Parser(){}

    /**
     * Reads the cards from the file and the creates them. Then creates a deck and shuffles it.
     * @return the weapon's deck.
     */
    public static Deck createWeapons(){
        Deck weapons = new Deck();
        String cardName;
        Color cardColor;
        String cardEffect;
        int grabBlueAmmos, grabRedAmmos, grabYellowAmmos, reloadBlueAmmos, reloadRedAmmos, reloadYellowAmmos;
        for(int i=0; i < 21; i++){
            InputStream input = Parser.class.getClassLoader().getResourceAsStream(CARD_FILE);
            Object reader = null;
            try {
                reader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException e) {
                Printer.err(e);
            } catch (IOException e) {
                Printer.err(e);
            }
            JSONObject firstObject = (JSONObject) reader;
            firstObject = (JSONObject)firstObject.get(CARD_CONFIG);
            JSONArray firstArray = (JSONArray) firstObject.get(ELEMENTS);
            JSONObject secondObject = (JSONObject) firstArray.get(0);
            JSONArray secondArray = (JSONArray) secondObject.get(ELEMENT);
            JSONObject thirdObject = (JSONObject) secondArray.get(i);

            cardName = (String) thirdObject.get(NAME);
            cardColor = fromStringToColor((String) thirdObject.get(COLOUR));
            cardEffect = (String) thirdObject.get(EFFECT);
            JSONArray grabAmmos = (JSONArray) thirdObject.get(GRAB_AMMOS);
            JSONObject grabAmmosObject = (JSONObject) grabAmmos.get(0);
            //JSONObject grabAmmos = (JSONObject) thirdObject.get("grabAmmos");
            grabBlueAmmos = (int)(long) grabAmmosObject.get(BLUE);
            grabRedAmmos = (int)(long) grabAmmosObject.get(RED);
            grabYellowAmmos = (int)(long) grabAmmosObject.get(YELLOW);
            JSONArray reloadAmmos = (JSONArray) thirdObject.get(RELOAD_AMMOS);
            JSONObject reloadAmmosObject = (JSONObject) reloadAmmos.get(0);
            //JSONObject reloadAmmos = (JSONObject) thirdObject.get("reloadAmmos");
            reloadBlueAmmos = (int)(long) reloadAmmosObject.get(BLUE);
            reloadRedAmmos = (int)(long) reloadAmmosObject.get(RED);
            reloadYellowAmmos = (int)(long) reloadAmmosObject.get(YELLOW);
            Card card = new WeaponCard(cardName, cardColor, cardEffect, grabRedAmmos, grabBlueAmmos, grabYellowAmmos, reloadRedAmmos, reloadBlueAmmos, reloadYellowAmmos);
            weapons.addCard(card);
        }
        weapons.shuffle();
        return weapons;
    }

    /**
     * Reads the ammocards from the file and creates them. Then creates a list of them and shuffles it.
     * @return a list of ammocards.
     */
    public static List<AmmoCard> createAmmos(){
        List<AmmoCard> ammos = new ArrayList<>();
        String combination;
        AmmoCard ammoCard;
        for(int i=0; i < 12; i++) {
            InputStream input = Parser.class.getClassLoader().getResourceAsStream(CARD_FILE);
            Object reader = null;
            try {
                reader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException e) {
                Printer.err(e);
            } catch (IOException e) {
                Printer.err(e);
            }
            JSONObject firstObject = (JSONObject) reader;
            firstObject = (JSONObject) firstObject.get(CARD_CONFIG);
            JSONArray firstArray = (JSONArray) firstObject.get(ELEMENTS);
            JSONObject secondObject = (JSONObject) firstArray.get(1);
            JSONArray secondArray = (JSONArray) secondObject.get(ELEMENT);
            JSONObject thirdObject = (JSONObject) secondArray.get(i);

            for(int j=0; j<3; j++){
                combination = (String) thirdObject.get(VALUE);
                ammoCard = generateAmmoCard(combination);
                ammos.add(ammoCard);
            }
        }
        Collections.shuffle(ammos);
        return ammos;
    }

    /**
     * Reads powerup card from the file and creates them. Then creates a deck and shuffles it.
     * @return the powerup's deck.
     */
    public static Deck createPowerups(){
        Deck powerups = new Deck();
        String cardName;
        String cardEffect;
        for(int i=0; i < 4; i++){
            InputStream input = Parser.class.getClassLoader().getResourceAsStream(CARD_FILE);
            Object reader = null;
            try {
                reader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException | IOException e) {
                Printer.err(e);
            }
            JSONObject firstObject = (JSONObject) reader;
            firstObject = (JSONObject)firstObject.get(CARD_CONFIG);
            JSONArray firstArray = (JSONArray) firstObject.get(ELEMENTS);
            JSONObject secondObject = (JSONObject) firstArray.get(2);
            JSONArray secondArray = (JSONArray) secondObject.get(ELEMENT);
            JSONObject thirdObject = (JSONObject) secondArray.get(i);

            cardName = (String) thirdObject.get(NAME);
            cardEffect = (String) thirdObject.get(INFO_CARD_EFFECT);
            Card card = new PowerupCard(cardName, Color.BLUE, cardEffect);
            powerups.addCard(card);
            card = new PowerupCard(cardName, Color.YELLOW, cardEffect);
            powerups.addCard(card);
            card = new PowerupCard(cardName, Color.RED, cardEffect);
            powerups.addCard(card);
        }
        powerups.shuffle();
        return powerups;
    }

    /**
     * Reads the boards from the file and creates them. Then creates a list of them and returns it.
     * @return a list of boards.
     */
    public static List<GameBoard> createGameBoards(){
        List<GameBoard> gameBoards = new ArrayList<>();
        BoardType boardType;
        TokenColor color;
        Cardinal north, south, east, west;

        InputStream input = Parser.class.getClassLoader().getResourceAsStream(BOARD_FILE);
        Object reader = null;
        try {
            reader = new JSONParser().parse(new InputStreamReader(input));
        } catch (ParseException | IOException e) {
            Printer.err(e);
        }
        JSONObject firstObject = (JSONObject) reader;
        firstObject = (JSONObject)firstObject.get(GAMEBOARD);
        JSONArray firstArray = (JSONArray) firstObject.get(ELEMENTS);


        for(int i=0; i < 4; i++){
            Square[][] arena = new Square[3][4];
            JSONObject secondObject = (JSONObject) firstArray.get(i);
            boardType = fromStringToBoardType((String) secondObject.get(TYPE));
            JSONArray secondArray = (JSONArray) secondObject.get(SQUARES);

            int l = 0;
            for(int j=0; j < 3; j++){
                for(int k=0; k < 4; k++){
                    JSONObject thirdObject = (JSONObject) secondArray.get(l);
                    color = fromStringToTokenColor((String) thirdObject.get(COLOR));
                    north = fromStringToCardinal((String) thirdObject.get(NORTH));
                    south = fromStringToCardinal((String) thirdObject.get(SOUTH));
                    east = fromStringToCardinal((String) thirdObject.get(EAST));
                    west = fromStringToCardinal((String) thirdObject.get(WEST));
                    switch((String) thirdObject.get(TYPE)){
                        case AMMO:
                            arena[j][k] = new AmmoPoint(color, north, south, west, east);
                            break;
                        case SPAWN:
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

    /**
     * Takes a combination of colors and creates the relative ammocard.
     * @param combination a String of colors.
     * @return the relative ammocard.
     */
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