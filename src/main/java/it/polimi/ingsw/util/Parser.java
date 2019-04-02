package it.polimi.ingsw.util;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.WeaponCard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser {

    private Parser(){}

    public static Deck createWeapons(){
        Deck weapons = new Deck();
        String cardName;
        Color cardColor;
        for(int i=0; i < 21; i++){
            InputStream input = Parser.class.getClassLoader().getResourceAsStream("cardconfig.json");
            Object fileReader = null;
            try {
                fileReader = new JSONParser().parse(new InputStreamReader(input));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject firstObject = (JSONObject) fileReader;
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
}