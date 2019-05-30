package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.*;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Score;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CommandLine implements ViewInterface {
    private ClientInterface client;
    private String username;
    private PlayerController playerController;
    private BufferedReader userInputStream;
    private GameController game = new GameController();
    //private MapCLI mapCLI = new MapCLI(game);
    //private DamageBoardCLI dmgBoard = new DamageBoardCLI(game);
    private CLIPrinter cliPrinter;
    private GameBoard gameBoard;
    private Player player;
    private AmmoBoxReserveCLI ammoPrinter;
    private DamageBoardCLI damageBoardPrinter;
    private MapCLI gameBoardPrinter;
    private KillshotTrackCLI killshotTrackPrinter;



    public CommandLine(ClientInterface client){
        this.client = client;
        this.userInputStream = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void start(){
        Printer.println("[SERVER]Please, insert the following command ->");
        Printer.print("login <username> <color> : ");
        Thread receiveMessage = new Thread(() -> {
            while(true){
                try {
                    readInput(userInputStream.readLine());
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        });
        receiveMessage.start();
    }

    public void setPlayerController(PlayerController playerController){
        this.playerController = playerController;
    }

    private void readInput(String message) throws IOException {
        StringTokenizer string = new StringTokenizer(message);
        switch(string.nextToken()){
            case "help":
                help();
                break;
            case "login":
                if(!login(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "disconnect":
                disconnect();
                break;
            case "board":
                if(!board(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "choose":
                if(!choose(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "show":
                if(!show(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "move":
                if(!move(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "grab":
                if(!grab(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "shoot":
                if(!shoot(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "powerup":
                if(!powerup(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "reload":
                if(!reload(string)){
                    Printer.print(StringCLI.INVALID_COMMAND);
                }
                break;
            case "end":
                endTurn();
                break;
            default:
                Printer.println(StringCLI.INVALID_COMMAND);
                break;
        }
    }

    private void help(){
        Printer.println("List of Commands:");
        Printer.println("help : gives you the list of commands");
        Printer.println("login <username> <color>:");
        Printer.println("disconnect :");
        Printer.println("show <object> :");
        Printer.println("move <first_direction, ..., last_direction> :");
        Printer.println("grab <direction> <0, 1, 2, 3>:");
        Printer.println("shoot <victim> <weapon_name> <weapon_effect_number...>");
        Printer.println("end : ends your turn");
    }

    private boolean login(StringTokenizer input){
        boolean result = false;
        if(input.hasMoreTokens()){
            username = input.nextToken();
            if(input.hasMoreTokens()){
                String color = input.nextToken();
                try {
                    client.login(username, Converter.fromStringToTokenColor(color));
                    result = true;
                } catch (RemoteException e) {
                    Printer.err(e);
                }
            }
        }
        return result;
    }

    private void disconnect(){
        try {
            client.disconnect();
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    private boolean board(StringTokenizer input){
        if(input.countTokens() == 2){
            try {
                client.board(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
            } catch (IOException e) {
                Printer.err(e);
            }
            return true;
        }else{
            return false;
        }
    }

    private boolean choose(StringTokenizer input){
        boolean result = false;
        if(input.hasMoreTokens()){
            int choice = Integer.parseInt(input.nextToken());
            try {
                if(choice == 1){
                    //playerController.addPowerup();
                    client.choose(2);
                }else{
                    client.choose(1);
                }
            } catch (IOException e) {
                Printer.err(e);
            }
            result = true;
        }
        return result;
    }

    private boolean show(StringTokenizer input){
        boolean result = false;
        if(input.hasMoreTokens()){
            String next = input.nextToken();
            switch (next){
                case StringCLI.SCORE:
                    Printer.println("Your score is: " + playerController.getScore());
                    break;
                case StringCLI.PLAYERBOARD:
                    damageBoardPrinter.printDamageBoard();
                    break;
                case StringCLI.AMMOS:
                    Printer.println("Your ammos are:");
                    ammoPrinter.printAmmoBox();
                    break;
                case StringCLI.POWERUPS:
                    Printer.println("Your powerups are:");
                    Printer.println(playerController.getPowerups());
                    break;
                case StringCLI.WEAPONS:
                    Printer.println("Your weapons are:");
                    Printer.println(playerController.getWeapons());
                    break;
                case "square":
                    if(input.countTokens() == 2){
                        try {
                            client.showSquare(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    }else{
                        try {
                            client.showSquare();
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    }
                    break;
                case "map":
                    Printer.println("The Game's Board:");
                    gameBoardPrinter.printMap();
                    break;
                default:
                    break;
            }
            result = true;
        }
        return result;
    }

    private boolean move(StringTokenizer input) throws IOException {
        if(input.countTokens() == 1){
            client.move(Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 2){
            client.move(Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 3){
            client.move(Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                        Converter.fromStringToDirection(input.nextToken()));
            return true;
        }
        return false;
    }

    /*
    private boolean move(StringTokenizer input){
        boolean result = false;
        Direction first, second, third;
        if(input.hasMoreTokens()){
            first = Converter.fromStringToDirection(input.nextToken());
            if(input.hasMoreTokens()){
                second = Converter.fromStringToDirection(input.nextToken());
                if(input.hasMoreTokens()){
                    third = Converter.fromStringToDirection(input.nextToken());
                    try {
                        client.move(first, second, third);
                        result = true;
                        return result;
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }else{
                    try {
                        client.move(first, second);
                        result = true;
                        return result;
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else{
                try {
                    client.move(first);
                    result = true;
                    return result;
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            return result;
        }
        return result;
    }

     */
    /*
    private boolean moveFinalFrenzy(StringTokenizer input) throws IOException {
        Direction first, second, third, fourth;
        if(input.hasMoreTokens()){
            first = Converter.fromStringToDirection(input.nextToken());
            if(input.hasMoreTokens()){
                second = Converter.fromStringToDirection(input.nextToken());
                if(input.hasMoreTokens()){
                    third = Converter.fromStringToDirection(input.nextToken());
                    if(input.hasMoreTokens()){
                        fourth = Converter.fromStringToDirection(input.nextToken());
                        client.move(first, second, third, fourth);
                        return true;
                    }else{
                        client.move(first, second, third);
                        return true;
                    }
                }else{
                    client.move(first, second);
                    return true;
                }
            }else{
                client.move(first);
                return true;
            }
        }
        return false;
    }
    */
    private boolean moveFinalFrenzy(StringTokenizer input) throws IOException {
        if(input.countTokens() == 1){
            client.move(Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 2){
            client.move(Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 3){
            client.move(Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 4){
            client.move(Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }
        return false;
    }

    private boolean grab(StringTokenizer input) throws IOException {
        int choice = Integer.parseInt(input.nextToken());
        if(choice != 0){
            handlePowerup();
        }
        if(input.countTokens() == 0){
            client.grab(choice);
            return true;
        }else if(input.countTokens() == 1){
            client.grab(choice, Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 2 && !playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)){
            client.grab(choice, Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()));
            return true;
        }
        return false;
    }

    /*
    private boolean grab(StringTokenizer input) throws RemoteException {
        if(input.hasMoreTokens()) {
            String next = input.nextToken();
            int choice = Integer.parseInt(next);
            if (input.hasMoreTokens()) {
                Direction firstD = Converter.fromStringToDirection(input.nextToken());
                if (!client.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) {
                    if (input.hasMoreTokens()) {
                        try {
                            Direction secondD = Converter.fromStringToDirection(input.nextToken());
                            handlePowerup();
                            client.grab(choice, firstD, secondD);
                            return true;
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    } else {
                        try {
                            handlePowerup();
                            client.grab(choice, firstD);
                            return true;
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    }
                } else {
                    try {
                        handlePowerup();
                        client.grab(choice, firstD);
                        return true;
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            } else {
                try {
                    client.grab(choice);
                    return true;
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
        return false;
    }
    */

    private boolean grabFinalFrenzy(StringTokenizer input) throws IOException {
        handlePowerup();
        if(input.countTokens() == 1){
            client.grab(Integer.parseInt(input.nextToken()));
            return true;
        }else if(input.countTokens() == 2){
            client.grab(Integer.parseInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 3){
            client.grab(Integer.parseInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 4){
            client.grab(Integer.parseInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }
        return false;
    }

    private boolean shoot(StringTokenizer input){
        String weapon;
        if(input.hasMoreTokens()){
            weapon = input.nextToken();
            try {
                if(input.hasMoreTokens() && playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND)){
                    Direction direction = Converter.fromStringToDirection(input.nextToken());
                    return weaponEffect(weapon, direction);
                }else{
                    return weaponEffect(weapon);
                }
            } catch (IOException e) {
                Printer.err(e);
            }
        }
        return false;
    }

    private boolean shootFinalFrenzy(StringTokenizer input) throws IOException {
        if(input.countTokens() == 1){
            weaponEffect(input.nextToken());
            return true;
        }else if(input.countTokens() == 2){
            weaponEffect(input.nextToken(), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 3){
            weaponEffect(input.nextToken(), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()));
            return true;
        }
        return false;
    }

    private boolean weaponEffect(String weapon, Direction...directions) throws IOException {
        StringTokenizer string;
        switch(weapon){
            case "lockrifle":
                Printer.println("Basic effect: <victim>");
                Printer.println("With second lock: <first_victim> <second_victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 2){
                    client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "machinegun":
                Printer.println("Basic effect: <1> <first_victim>");
                Printer.println("Basic effect: <1> <first_victim> <second_victim>");
                Printer.println("With focus shot: <2> <first_victim>");
                Printer.println("With focus shot: <2> <first_victim> <second_victim>");
                Printer.println("With turret tripod: <3> <victim> <victim>");
                Printer.println("With turret tripod: <3> <victim> <victim> <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "thor":
                Printer.println("Basic effect: <victim>");
                Printer.println("With chain reaction: <victim> <victim>");
                Printer.println("With high voltage: <victim> <victim> <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 2){
                    client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3) {
                    client.shoot(weapon, 3, true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                            -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "plasmagun":
                Printer.println("Basic effect: <1> <victim>");
                Printer.println("With phase glide: <2> <victim> <direction>");
                Printer.println("With phase glide: <2> <victim> <direction> <direction>");
                Printer.println("With charged shot: <3> <victim>");
                Printer.println("With charged shot: <3> <victim> <direction>");
                Printer.println("With charged shot: <3> <victim> <direction> <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    boolean basicFirst = basicFirst();
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    boolean basicFirst = basicFirst();
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()),
                            Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "whisper":
                Printer.println("Effect: <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                            TokenColor.NONE, -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "electroscythe":
                Printer.println("Basic mode: <1>");
                Printer.println("In reaper mode: <2>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE,
                            TokenColor.NONE, -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "tractorbeam":
                Printer.println("Basic mode: <1> <victim> <directions...>");
                Printer.println("In punisher mode: <2> <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()),
                            Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "vortexcannon":
                Printer.println("Basic effect: <victim> <squareX> <squareY>");
                Printer.println("With black hole: <victim> <victim> <squareX> <squareY>");
                Printer.println("With black hole: <victim> <victim> <victim> <squareX> <squareY>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 3){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                            Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 5){
                    client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                            Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "furnace":
                Printer.println("Basic mode: <1> <squareX> <squareY>");
                Printer.println("In cozy fire mode: <2> <squareX> <squareY>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE,
                            Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "heatseeker":
                Printer.println("Effect: <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, TokenColor.NONE,
                            -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "hellion":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("In nano-tracer mode: <2> <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "flamethrower":
                Printer.println("Basic mode: <victim1> <victim2> <direction>");
                Printer.println("In barbecue mode: <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, 2, true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE,
                            -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "grenadelauncher":
                Printer.println("Basic effect: <victim>");
                Printer.println("Basic effect: <victim> <direction>");
                Printer.println("With extra grenade: <victim> <squareX> <squareY>");
                Printer.println("With extra grenade: <victim> <squareX> <squareY> <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 2){
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    boolean basicFirst = basicFirst();
                    client.shoot(weapon, 2, basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()),
                            Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "rocketlauncher": //da terminare
                Printer.println("Choose your effect: <1> or <2> or <3>");
                int choice2 = Integer.parseInt(userInputStream.readLine());
                if(choice2 == 1){
                    Printer.println("Basic effect: <victim>");
                    Printer.println("Basic effect: <victim> <direction>");
                    return true;
                }else if(choice2 == 2){
                    Printer.println("With rocket jump: <victim> <null> <direction>");
                    Printer.println("With rocket jump: <victim> <null> <direction> <direction>");
                    Printer.println("With rocket jump: <victim> <victim_direction> <shooter_direction>");
                    Printer.println("With rocket jump: <victim> <victim_direction> <shooter_direction> <shooter_direction>");
                    return true;
                }else if(choice2 == 3){
                    Printer.println("With fragmenting warhead: <victim> <null> <direction>");
                    Printer.println("With fragmenting warhead: <victim> <null> <direction> <direction>");
                    Printer.println("With fragmenting warhead: <victim> <victim_direction> <shooter_direction>");
                    Printer.println("With fragmenting warhead: <victim> <victim_direction> <shooter_direction> <shooter_direction>");
                    return true;
                }else{
                    return false;
                }
            case "railgun":
                Printer.println("Basic mode: <1> <victim> <direction>");
                Printer.println("In piercing mode: <2> <victim> <direction>");
                Printer.println("In piercing mode: <2> <victim1> <victim2> <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "cyberblade":
                Printer.println("Basic effect: <1> <victim>");
                Printer.println("With shadow step: <2> <victim> <direction>");
                Printer.println("With slice and dice: <3> <victim> <direction>");
                //da terminare
                break;
            case "zx-2":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("In scanner mode: <2> <victim>");
                Printer.println("In scanner mode: <2> <victim> <victim>");
                Printer.println("In scanner mode: <2> <victim> <victim> <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "shotgun":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("Basic mode: <1> <victim> <direction>");
                Printer.println("In long barrel mode: <2> <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "powerglove":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("In rocket fist mode: <2> <direction>");

                Printer.println("In rocket fist mode: <2> <direction> <direction>");
                Printer.println("In rocket fist mode: <2> <victim> <direction>");

                Printer.println("In rocket fist mode: <2> <victim> <direction> <direction>");
                Printer.println("In rocket fist mode: <2> <victim> <victim> <direction>");

                Printer.println("In rocket fist mode: <2> <victim> <victim> <direction> <direction>");

                string = new StringTokenizer(userInputStream.readLine());
                int choice = Integer.parseInt(string.nextToken());
                if(string.countTokens() == 1 && choice == 1){
                    client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                            TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 1 && choice == 2){
                    client.shoot(weapon, choice, true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE, -1, -1,
                            Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 2){
                    String move = string.nextToken();
                    if(Converter.fromStringToTokenColor(move).equals(TokenColor.NONE)){
                        client.shoot(weapon, choice, true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE, -1, -1,
                                Converter.fromStringToDirection(move));
                    }else{
                        client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(move), TokenColor.NONE, TokenColor.NONE,
                                -1, -1);
                    }
                    return true;
                }else if(string.countTokens() == 3){
                    TokenColor firstVictim = Converter.fromStringToTokenColor(string.nextToken());
                    String move = string.nextToken();
                    if(Converter.fromStringToTokenColor(move).equals(TokenColor.NONE)){
                        client.shoot(weapon, choice, true, firstVictim, TokenColor.NONE, TokenColor.NONE, -1, -1,
                                Converter.fromStringToDirection(move), Converter.fromStringToDirection(string.nextToken()));
                    }else{
                        client.shoot(weapon, choice, true, firstVictim, Converter.fromStringToTokenColor(move), TokenColor.NONE,
                                -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    }
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()), Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "shockwave":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("Basic mode: <1> <victim> <victim>");
                Printer.println("Basic mode: <1> <victim> <victim> <victim>");
                Printer.println("In tsunami mode: <2>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE,
                            TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "sledgehammer":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("In pulverize mode: <2> <victim>");
                Printer.println("In pulverize mode: <2> <victim> <direction>");
                Printer.println("In pulverize mode: <2> <victim> <direction> <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()), Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            default:
                break;
        }
        return false;
    }

    private boolean basicFirst(){
        Printer.println("Do you want to use the movement effect after the others?: <yes> <no>");
        try {
            String basicFirst = userInputStream.readLine();
            if(basicFirst.equals("yes")){
                return true;
            }
        } catch (IOException e) {
            Printer.err(e);
        }
        return false;
    }

    private boolean powerup(StringTokenizer input){
        if(input.hasMoreTokens()){
            String powerup = input.nextToken();
            try {
                powerupEffect(powerup);
            } catch (IOException e) {
                Printer.err(e);
            }
            return true;
        }else{
            return false;
        }
    }

    private void powerupEffect(String powerup) throws IOException {
        StringTokenizer string;
        switch(powerup){
            case "newton":
                Printer.println("Choose a direction and a movement: <direction> <1 or 2>");
                string = new StringTokenizer(userInputStream.readLine());
                int value;
                Direction direction;
                if(string.hasMoreTokens()){
                    direction = Converter.fromStringToDirection(string.nextToken());
                    if(string.hasMoreTokens()){
                        value = Integer.parseInt(string.nextToken());
                        client.powerup(powerup, direction, value);
                    }
                }
                break;
            case "teleporter":
                Printer.println("Choose a square: <square_x> <square_y>");
                string = new StringTokenizer(userInputStream.readLine());
                int x, y;
                if(string.hasMoreTokens()){
                    x = Integer.parseInt(string.nextToken());
                    if(string.hasMoreTokens()){
                        y = Integer.parseInt(string.nextToken());
                        client.powerup(powerup, x, y);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void handlePowerup() throws IOException {
        Printer.println("Please, insert powerups or write <no>: <first_powerup_name> <first_color> <second_powerup_name> <second_color>:");
        StringTokenizer string = new StringTokenizer(userInputStream.readLine());
        if(string.hasMoreTokens()){
            String firstPowerup = string.nextToken();
            if(string.hasMoreTokens() && !firstPowerup.equals("no")){
                Color firstColor = Converter.fromStringToColor(string.nextToken());
                if(string.hasMoreTokens()){
                    String secondPowerup = string.nextToken();
                    if(string.hasMoreTokens()){
                        Color secondColor = Converter.fromStringToColor(string.nextToken());
                        client.powerupAmmos(new PowerupData(firstPowerup, firstColor), new PowerupData(secondPowerup, secondColor));
                    }
                }else{
                    client.powerupAmmos(new PowerupData(firstPowerup, firstColor));
                }
            }
        }
    }

    private boolean reload(StringTokenizer input) throws IOException {
        if(input.countTokens() == 1){
            client.reload(Integer.parseInt(input.nextToken()));
            return true;
        }else if(input.countTokens() == 2){
            client.reload(Integer.parseInt(input.nextToken()), Integer.parseInt(input.nextToken()));
            return true;
        }
        return false;
    }

    private void endTurn(){
        try {
            client.endTurn();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void notifyLogin(Outcome outcome, String username){
        switch(outcome){
            case WRONG:
                Printer.print(StringCLI.SERVER + " Username already used! Please choose another username:");
                break;
            case RIGHT:
                Printer.println(username + " connected!");
                //Printer.print("[CLIENT]Please, insert a command:");
                break;
            case ALL:
                Printer.println(username + " connected!");
                break;
            default:
                break;
        }
    }

    public void notifySpawnLocation(List<Card> powerups){
        Printer.println("");
        Printer.println("[SERVER]Please, choose one of these powerups:");
        powerups.forEach(p -> {
            Printer.println(p);
            Printer.println("");
        });
        Printer.println("[SERVER]Please, insert the following command ->");
        Printer.print("choose <choosen_powerup> : ");
    }

    public void notifyDisconnection(Outcome outcome, String username){
        switch (outcome){
            case ALL:
                Printer.println(username + " disconnected!");
                break;
            default:
                break;
        }
    }

    public void notifyColor(Outcome outcome, TokenColor color){
        switch(outcome){
            case WRONG:
                Printer.print(StringCLI.SERVER + "Invalid color! Please choose another color:");
                break;
            case RIGHT:
                Printer.println("Your color is " + Converter.fromTokenColorToString(color));
                //Printer.print("[CLIENT]Please, insert a command:");
                break;
            default:
                break;
        }
    }

    private void notifyBoard(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            int i = 1;
            Printer.println("[SERVER]Please, choose one of these boards:");
            for(BoardType boardType : BoardType.values()){
                Printer.print("   " + i + ": ");
                Printer.println(boardType);
                i++;
            }
            Printer.println("[SERVER]Please, insert the following command ->");
            Printer.print("board <choosen_board> <skulls number>: ");
        }else{
            Printer.println("[SERVER]The first player is choosing the board...");
        }
    }

    private void notifyNewTurn(){
        Printer.println("It's your turn!");
        killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
        killshotTrackPrinter.printKillshotTrack();
        gameBoardPrinter.setGameBoard(playerController.getGameBoard());
        gameBoardPrinter.printMap();
        //Printer.print("         ");
        damageBoardPrinter.setPlayer(playerController.getPlayer());
        damageBoardPrinter.printDamageBoard();
    }

    private void notifyEndTurn(){
        Printer.println("Your turn is ended!");
    }

    private void notifyGame(Outcome outcome){
        switch (outcome){
            case WRONG:
                Printer.println("Game is already begun!");
                break;
            case ALL:
                gameBoardPrinter = new MapCLI(playerController.getGameBoard());
                ammoPrinter = new AmmoBoxReserveCLI(playerController.getPlayer());
                damageBoardPrinter = new DamageBoardCLI(playerController.getPlayer());
                killshotTrackPrinter = new KillshotTrackCLI(playerController.getKillshotTrack());
                Printer.println("Game is started!");
                break;
            default:
                break;
        }
    }

    private void notifyMovement(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("[SERVER]Moved!");
        }else{
            Printer.println("[SERVER]Not moved!");
        }
        killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
        killshotTrackPrinter.printKillshotTrack();
        gameBoardPrinter.setGameBoard(playerController.getGameBoard());
        gameBoardPrinter.printMap();
    }

    private void notifyPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("[SERVER]Powerup used!");
        }else{
            Printer.println("[SERVER]Powerup not used!");
        }
        killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
        killshotTrackPrinter.printKillshotTrack();
        gameBoardPrinter.setGameBoard(playerController.getGameBoard());
        gameBoardPrinter.printMap();
    }

    private void notifyGrab(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("[SERVER]Grabbed!");
        }else{
            Printer.println("[SERVER]Not grabbed!");
        }
        ammoPrinter.setPlayer(playerController.getPlayer());
        ammoPrinter.printAmmoBox();
        ammoPrinter.printAmmoReserve();
        playerController.getPlayer().getWeapons().forEach(Printer::println);
        playerController.getPlayer().getPowerups().forEach(Printer::println);
    }

    private void notifyShoot(Outcome outcome){
        switch(outcome){
            case RIGHT:
                Printer.println("[SERVER]Shoot!");
                break;
            case ALL:
                Printer.println("[SERVER]Shot!");
                Printer.println(playerController.getPlayer().getUsername());
                killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
                killshotTrackPrinter.printKillshotTrack();
                damageBoardPrinter.setPlayer(playerController.getPlayer());
                damageBoardPrinter.printDamageBoard();
                break;
            case WRONG:
                Printer.println("[SERVER]Not shoot!");
                break;
            default:
                Printer.println("[SERVER]Not shoot!");
                break;
        }
    }

    private void notifyShowSquare(SquareData squareData){
        if(squareData.getAmmoCard() != null){
            Printer.println("Ammos:");
            Printer.print(squareData.getAmmoCard().getFirstAmmo().getColor() + ", " +
                    squareData.getAmmoCard().getSecondAmmo().getColor() + ", ");
            if(squareData.getAmmoCard().isPowerup()){
                //Printer.println(squareData.getAmmoCard().);
            }else{
                Printer.println(squareData.getAmmoCard().getThirdAmmo().getColor());
            }
        }
        if(squareData.getWeapons() != null){
            Printer.println("Weapons:");
            squareData.getWeapons().forEach(Printer::println);
        }
    }

    private void notifyScore(Map<TokenColor, Integer> scoreList){
        Printer.println("Score:");
        scoreList.forEach((c,i)->Printer.println(c + ": " + i));
    }

    public void notify(Message message){
        switch (message){
            case NEW_TURN:
                notifyNewTurn();
                break;
            case END_TURN:
                notifyEndTurn();
                break;
            default:
                break;
        }
    }

    public void notify(Message message, Outcome outcome){
        switch (message){
            case BOARD:
                notifyBoard(outcome);
                break;
            case GAME:
                notifyGame(outcome);
                break;
            case MOVE:
                notifyMovement(outcome);
                break;
            case GRAB:
                notifyGrab(outcome);
                break;
            case SHOOT:
                notifyShoot(outcome);
                break;
            case POWERUP:
                notifyPowerup(outcome);
                break;
            default:
                break;
        }
    }

    public void notify(Message message, Outcome outcome, Object object){
        switch(message){
            case LOGIN:
                notifyLogin(outcome, (String) object);
                break;
            case COLOR:
                notifyColor(outcome, (TokenColor) object);
                break;
            case DISCONNECT:
                notifyDisconnection(outcome, (String) object);
                break;
            case SPAWN:
                notifySpawnLocation((List<Card>) object);
                break;
            case SQUARE:
                notifyShowSquare((SquareData) object);
                break;
            case SCORE:
                notifyScore((Map<TokenColor, Integer>) object);
                break;
            default:
                break;
        }
    }
}
