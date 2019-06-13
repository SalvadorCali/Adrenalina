package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.*;
import it.polimi.ingsw.model.gamecomponents.*;
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
        if(string.hasMoreTokens()){
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
                case "drop":
                    if(!drop(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case "discard":
                    if(!discard(string)){
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
                    if(playerController.isFinalFrenzy()){
                        if(!moveFinalFrenzy(string)){
                            Printer.print(StringCLI.INVALID_COMMAND);
                        }
                    }else{
                        if(!move(string)){
                            Printer.print(StringCLI.INVALID_COMMAND);
                        }
                    }
                    break;
                case "grab":
                    if(playerController.isFinalFrenzy()){
                        if(!grabFinalFrenzy(string)){
                            Printer.print(StringCLI.INVALID_COMMAND);
                        }
                    }else{
                        if(!grab(string)){
                            Printer.print(StringCLI.INVALID_COMMAND);
                        }
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
                case "respawn":
                    if(!respawn(string)){
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

    private boolean drop(StringTokenizer input){
        if(input.hasMoreTokens() && input.countTokens()==2){
            String whatToDrop = input.nextToken();
            if(whatToDrop.equals("weapon")){
                dropWeapon(Integer.parseInt(input.nextToken()));
                return true;
            }else if(whatToDrop.equals("powerup")){
                dropPowerup(Integer.parseInt(input.nextToken()));
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    private boolean discard(StringTokenizer input){
        if(input.hasMoreTokens() && input.countTokens()==1){
            discardPowerup(Integer.parseInt(input.nextToken()));
            return true;
        }else{
            return false;
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

    private boolean respawn(StringTokenizer input){
        if(input.hasMoreTokens() && input.countTokens()==1){
            try {
                client.respawn(Integer.parseInt(input.nextToken()));
            } catch (IOException e) {
                Printer.err(e);
            }
            return true;
        }else{
            return false;
        }
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
        }else if(input.countTokens() == 4 && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)){
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

    private void dropWeapon(int weapon){
        try {
            client.dropWeapon(weapon);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    private void dropPowerup(int powerup){
        try {
            client.dropPowerup(powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    private void discardPowerup(int powerup){
        try {
            client.discardPowerup(powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
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
        }else if(input.countTokens() == 3 && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)){
            client.grab(Integer.parseInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 4 && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){ //&& firstPlayer
            client.grab(Integer.parseInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }
        return false;
    }

    private boolean shoot(StringTokenizer input){
        Printer.println(playerController.getPlayer().isMoveAndReload());
        String weapon;
        if(input.hasMoreTokens()){
            //weapon = input.nextToken();
            if(playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND)){
                Printer.println("Do you want to move? <yes> <no>");
                try {
                    String movement = userInputStream.readLine();
                    if(movement.equals("yes")){
                        Printer.print("Choose a direction: ");
                        Direction direction = Converter.fromStringToDirection(userInputStream.readLine());
                        client.moveAndReload(direction);
                    }
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
            try {
                weapon = input.nextToken();
                return weaponEffect(weapon);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
        return false;
    }

    private boolean shootFinalFrenzy(StringTokenizer input) throws IOException {
        StringTokenizer moveAndReload;
        Direction first = null;
        Direction second = null;
        if(input.countTokens()==1){
            Printer.println("Do you want to move?: <yes> <no>");
            try {
                String movement = userInputStream.readLine();
                if(movement.equals("yes")){
                    Printer.println("Choose up to 2 directions: <direction> <direction>");
                    moveAndReload = new StringTokenizer(userInputStream.readLine());
                    if(moveAndReload.countTokens()==1){
                        first = Converter.fromStringToDirection(moveAndReload.nextToken());
                    }else if (moveAndReload.countTokens()==2){
                        first = Converter.fromStringToDirection(moveAndReload.nextToken());
                        second = Converter.fromStringToDirection(moveAndReload.nextToken());
                    }
                    Printer.println("Do you want to reload?: <yes> <no>");
                    String reload = userInputStream.readLine();
                    if(reload.equals("yes")){
                        Printer.println("Choose weapon to reload:");
                        moveAndReload = new StringTokenizer(userInputStream.readLine());
                        if(moveAndReload.countTokens()==1){
                            client.moveAndReload(first, second, moveAndReload.nextToken());
                            return true;
                        }else if(moveAndReload.countTokens()==2){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken());
                            return true;
                        }else if(moveAndReload.countTokens()==3){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken(),
                                    moveAndReload.nextToken());
                            return true;
                        }
                    }
                }else{
                    Printer.println("Do you want to reload?: <yes> <no>");
                    String reload = userInputStream.readLine();
                    if(reload.equals("yes")){
                        Printer.println("Choose weapon to reload:");
                        moveAndReload = new StringTokenizer(userInputStream.readLine());
                        if(moveAndReload.countTokens()==1){
                            client.moveAndReload(first, second, moveAndReload.nextToken());
                            return true;
                        }else if(moveAndReload.countTokens()==2){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken());
                            return true;
                        }else if(moveAndReload.countTokens()==3){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken(),
                                    moveAndReload.nextToken());
                            return true;
                        }
                    }
                }
            } catch (IOException e) {
                Printer.err(e);
            }
            weaponEffect(input.nextToken());
            return true;
        }else{
            return false;
        }
    }

    private boolean lockRifle(String weapon) throws IOException {
        TokenColor firstVictim, secondVictim;
        Printer.print("Please choose <1> or <2> effect:");
        int effectNumber = Integer.parseInt(userInputStream.readLine());
        if(effectNumber == 1){
            Printer.print("Please choose your victim:");
            firstVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            client.shoot(weapon, effectNumber, true, firstVictim, TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(effectNumber == 2){
            Printer.print("Please choose 1st victim:");
            firstVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            Printer.print("Please choose 2nd victim:");
            secondVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            client.shoot(weapon, effectNumber, true, firstVictim, secondVictim, TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }
/*
    private boolean machineGun(String weapon) throws IOException{
        Printer.print("Please choose <1>, <2> or <3> effect:");
        int effectNumber = Integer.parseInt(userInputStream.readLine());

    }
 */

    private boolean thor(String weapon) throws IOException{
        TokenColor firstVictim, secondVictim, thirdVictim;
        Printer.print("Please choose <1>, <2> or <3> effect:");
        int effectNumber = Integer.parseInt(userInputStream.readLine());
        if(effectNumber == 1){
            Printer.print("Please choose your victim:");
            firstVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            client.shoot(weapon, effectNumber, true, firstVictim, TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(effectNumber == 2){
            Printer.print("Please choose 1st victim:");
            firstVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            Printer.print("Please choose 2nd victim:");
            secondVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            client.shoot(weapon, effectNumber, true, firstVictim, secondVictim, TokenColor.NONE, -1, -1);
            return true;
        }else if(effectNumber == 3){
            Printer.print("Please choose 1st victim:");
            firstVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            Printer.print("Please choose 2nd victim:");
            secondVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            Printer.print("Please choose 3rd victim:");
            thirdVictim = Converter.fromStringToTokenColor(userInputStream.readLine());
            client.shoot(weapon, effectNumber, true, firstVictim, secondVictim, thirdVictim, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    private boolean weaponEffect(String weapon) throws IOException {
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
                    int choice = Integer.parseInt(string.nextToken());
                    switch (choice){
                        case 1:
                        case 2:
                            client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()),
                                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
                            break;
                        case 3:
                            client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()),
                                    TokenColor.NONE, Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
                            break;
                        default:
                            return false;
                    }
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
                    boolean basicFirst = basicFirst(weapon);
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    boolean basicFirst = basicFirst(weapon);
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
                Printer.println("Basic mode: <victim1> <direction>");
                Printer.println("Basic mode: <victim1> <victim2> <direction>");
                Printer.println("In barbecue mode: <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.shoot(weapon, 2, true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE,
                            -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 2) {
                    client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                            TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
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
                    boolean basicFirst = basicFirst(weapon);
                    client.shoot(weapon, 2, basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    boolean basicFirst = basicFirst(weapon);
                    client.shoot(weapon, 2, basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()),
                            Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "rocketlauncher":
                Printer.println("Choose your effect: <1> or <2> or <3>");
                int choice2 = Integer.parseInt(userInputStream.readLine());
                if(choice2 == 1){
                    Printer.println("Basic effect: <victim>");
                    Printer.println("Basic effect: <victim> <victim_direction>");
                    string = new StringTokenizer(userInputStream.readLine());
                    if(string.countTokens()==1){
                        client.shoot(weapon, choice2, true, Converter.fromStringToTokenColor(string.nextToken()),
                                TokenColor.NONE, TokenColor.NONE, -1, -1);
                        return true;
                    }else if(string.countTokens()==2){
                        client.shoot(weapon, choice2, true, Converter.fromStringToTokenColor(string.nextToken()),
                                TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                        return true;
                    }else{
                        return false;
                    }
                }else if(choice2 == 2 || choice2 == 3){
                    boolean basicFirst = basicFirst(weapon);
                    Printer.println("With rocket jump: <victim>");
                    Printer.println("With rocket jump: <victim> <victim_direction>");
                    string = new StringTokenizer(userInputStream.readLine());
                    TokenColor victim = TokenColor.NONE;
                    Direction direction1 = null;
                    if(string.countTokens()==1){
                        victim = Converter.fromStringToTokenColor(string.nextToken());
                    }else if(string.countTokens()==2){
                        victim = Converter.fromStringToTokenColor(string.nextToken());
                        direction1 = Converter.fromStringToDirection(string.nextToken());
                    }else{
                        return false;
                    }
                    Printer.println("With rocket jump: <shooter_direction>");
                    Printer.println("With rocket jump: <shooter_direction> <shooter_direction>");
                    string = new StringTokenizer(userInputStream.readLine());
                    Direction direction3 = null;
                    Direction direction4 = null;
                    if(string.countTokens()==1){
                        direction3 = Converter.fromStringToDirection(string.nextToken());
                    }else if(string.countTokens()==2){
                        direction3 = Converter.fromStringToDirection(string.nextToken());
                        direction4 = Converter.fromStringToDirection(string.nextToken());
                    }else{
                        return false;
                    }
                    client.shoot(weapon, choice2, basicFirst, victim, TokenColor.NONE, TokenColor.NONE, -1, -1,
                            direction1, null, direction3, direction4);
                    return true;
                }else{
                    return false;
                }
            case "railgun":
                Printer.println("Basic mode: <1> <victim> <direction>");
                Printer.println("In piercing mode: <2> <victim> <direction>"); //to test
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
                Printer.println("With slice and dice: <3> <victim> <victim> <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1);
                    return true;
                }else if(string.countTokens() == 3){
                    boolean basicFirst = basicFirst(weapon);
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 4){
                    boolean basicFirst = basicFirst(weapon + "2");
                    client.shoot(weapon, Integer.parseInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
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

    private boolean basicFirst(String weapon){
        switch(weapon){
            case "grenadelauncher":
                Printer.println("Do you want to use the second effect first?: <yes> <no>");
                break;
            case "cyberblade2":
                Printer.println("Do you want to move after the third effect?: <yes> <no>");
                break;
            default:
                Printer.println("Do you want to move first?: <yes> <no>");
                break;
        }
        try {
            String basicFirst = userInputStream.readLine();
            if(basicFirst.equals("yes")){
                return false;
            }
        } catch (IOException e) {
            Printer.err(e);
        }
        return true;
    }

    private boolean powerup(StringTokenizer input){
        if(input.hasMoreTokens()) {
            String powerup = input.nextToken();
            try {
                return powerupEffect(powerup);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
        return false;
    }

    private boolean powerupEffect(String powerup) throws IOException {
        StringTokenizer string;
        switch(powerup){
            case "targetingscope":
                Printer.println("Effect: <victim> <ammo>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToColor(string.nextToken()),
                            -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "newton":
                Printer.println("Effect: <victim> <direction>");
                Printer.println("Effect: <victim> <direction> <direction>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Color.NONE, -1, -1,
                            Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else if(string.countTokens() == 3){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Color.NONE, -1, -1,
                            Converter.fromStringToDirection(string.nextToken()), Converter.fromStringToDirection(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            case "tagbackgrenade":
                Printer.println("Effect: <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Color.NONE, -1, -1);
                    return true;
                }else{
                    return false;
                }
            case "teleporter":
                Printer.println("Effect: <square_x> <square_y>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.powerup(powerup, TokenColor.NONE, Color.NONE, Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            default:
                break;
        }
        return false;
    }

    public void handlePowerup() throws IOException {
        Printer.println("Do you want to discard a powerup as ammo? <yes> <no>");
        String choice = userInputStream.readLine();
        if(choice.equals("yes")){
            Printer.println("Please, insert powerup's number: <1> <2> <3>:");
            StringTokenizer string = new StringTokenizer(userInputStream.readLine());
            if(string.hasMoreTokens()){
                if(string.countTokens()==1){
                    client.powerupAmmos(Integer.parseInt(string.nextToken()));
                }else if(string.countTokens()==2){
                    client.powerupAmmos(Integer.parseInt(string.nextToken()), Integer.parseInt(string.nextToken()));
                }
            }
        }
    }

    private boolean reload(StringTokenizer input) throws IOException {
        handlePowerup();
        if(input.countTokens() == 1){
            client.reload(input.nextToken());
            return true;
        }else{
            return false;
        }

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

    private void notifyNewTurn(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("It's your turn!");
            killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
            killshotTrackPrinter.printKillshotTrack();
            gameBoardPrinter.setGameBoard(playerController.getGameBoard());
            gameBoardPrinter.printMap();
            damageBoardPrinter.setPlayer(playerController.getPlayer());
            damageBoardPrinter.printDamageBoard();
            if(!playerController.getWeapons().isEmpty()){
                Printer.println("Your weapons:");
                playerController.getWeapons().forEach(Printer::println);
            }
            if(!playerController.getPowerups().isEmpty()){
                Printer.println("Your powerups:");
                playerController.getPowerups().forEach(Printer::println);
            }
            Printer.println("In your square:");
            Square square = playerController.getGameBoard().getArena()[playerController.getPlayer().getPosition().getX()][playerController.getPlayer().getPosition().getY()];
            if(square.getAmmoCard() != null){
                Printer.print("AmmoCard: ");
                Printer.print(square.getAmmoCard().getFirstAmmo().getColor() + ", " +
                        square.getAmmoCard().getSecondAmmo().getColor() + ", ");
                if(square.getAmmoCard().isPowerup()){
                    Printer.println("POWERUP");
                }else{
                    Printer.println(square.getAmmoCard().getThirdAmmo().getColor());
                }
            }
            if(square.getWeapons() != null){
                square.getWeapons().forEach(Printer::println);
            }
        }

    }

    private void notifyEndTurn(){
        Printer.println("Your turn is ended!");
    }

    private void notifyNotTurn(){
        Printer.println("[SERVER]It's not your turn!");
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
        if(outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)){
            Printer.println("[SERVER]Moved!");
            killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
            killshotTrackPrinter.printKillshotTrack();
            gameBoardPrinter.setGameBoard(playerController.getGameBoard());
            gameBoardPrinter.printMap();
            Printer.println("In your square:");
            Square square = playerController.getGameBoard().getArena()[playerController.getPlayer().getPosition().getX()][playerController.getPlayer().getPosition().getY()];
            if(square.getAmmoCard() != null){
                Printer.print("AmmoCard: ");
                Printer.print(square.getAmmoCard().getFirstAmmo().getColor() + ", " +
                        square.getAmmoCard().getSecondAmmo().getColor() + ", ");
                if(square.getAmmoCard().isPowerup()){
                    Printer.println("POWERUP");
                }else{
                    Printer.println(square.getAmmoCard().getThirdAmmo().getColor());
                }
            }
            if(square.getWeapons() != null){
                square.getWeapons().forEach(w->Printer.println(w.getName()));
            }
        }else{
            Printer.println("[SERVER]Not moved!");
        }
    }

    private void notifyPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("[SERVER]" + playerController.getPowerup() + " used!");
        }else{
            Printer.println("[SERVER]" + playerController.getPowerup() + " not used!");
        }
        switch(playerController.getPowerup()){
            case "targetingscope":
            case "tagbackgrenade":
                damageBoardPrinter.setVictims(playerController.getVictims());
                damageBoardPrinter.printVictimsDamageBoard();
                break;
            case "newton":
            case "teleporter":
                killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
                killshotTrackPrinter.printKillshotTrack();
                gameBoardPrinter.setGameBoard(playerController.getGameBoard());
                gameBoardPrinter.printMap();
                break;
            default:
                break;
        }
    }

    private void notifyGrab(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("[SERVER]Grabbed!");
            killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
            killshotTrackPrinter.printKillshotTrack();
            gameBoardPrinter.setGameBoard(playerController.getGameBoard());
            gameBoardPrinter.printMap();
            damageBoardPrinter.setPlayer(playerController.getPlayer());
            damageBoardPrinter.printDamageBoard();
            if(!playerController.getWeapons().isEmpty()){
                Printer.println("Your weapons:");
                playerController.getWeapons().forEach(Printer::println);
            }
            if(!playerController.getPowerups().isEmpty()){
                Printer.println("Your powerups:");
                playerController.getPowerups().forEach(Printer::println);
            }
        }else if(outcome.equals(Outcome.WRONG)){
            Printer.println("[SERVER]Not grabbed!");
        }else{
            Printer.println("[SERVER]" + playerController.getCurrentPlayer() + " grabbed!");
            if(playerController.isMovement()){
                killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
                killshotTrackPrinter.printKillshotTrack();
                gameBoardPrinter.setGameBoard(playerController.getGameBoard());
                gameBoardPrinter.printMap();
                playerController.setMovement(false);
            }
        }
    }

    private void notifyShoot(Outcome outcome){
        switch(outcome){
            case RIGHT:
                Printer.println("[SERVER]Shoot!");
                if(playerController.isMovement()){
                    killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
                    killshotTrackPrinter.printKillshotTrack();
                    gameBoardPrinter.setGameBoard(playerController.getGameBoard());
                    gameBoardPrinter.printMap();
                    playerController.setMovement(false);
                }
                damageBoardPrinter.setVictims(playerController.getVictims());
                damageBoardPrinter.printVictimsDamageBoard();
                break;
            case ALL:
                Printer.println("[SERVER]Shoot!");
                if(playerController.isMovement()){
                    killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
                    killshotTrackPrinter.printKillshotTrack();
                    gameBoardPrinter.setGameBoard(playerController.getGameBoard());
                    gameBoardPrinter.printMap();
                    playerController.setMovement(false);
                }
                damageBoardPrinter.setVictims(playerController.getVictims());
                damageBoardPrinter.printVictimsDamageBoard();
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

    private void notifyReload(Outcome outcome, String weaponName){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(weaponName + " reloaded!");
        }else{
            Printer.println(weaponName + " not reloaded!");
        }
    }

    private void notifyReconnection(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("Reconnected");
            killshotTrackPrinter = new KillshotTrackCLI(playerController.getKillshotTrack());
            killshotTrackPrinter.printKillshotTrack();
            gameBoardPrinter = new MapCLI(playerController.getGameBoard());
            gameBoardPrinter.printMap();
            damageBoardPrinter = new DamageBoardCLI(playerController.getPlayer());
            damageBoardPrinter.printDamageBoard();
        }else{
            Printer.println("Reconnected");
        }
    }

    private void notifyDiscardPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("Powerup discard!");
            if(!playerController.getPowerups().isEmpty()){
                Printer.println("Your powerups:");
                playerController.getPowerups().forEach(Printer::println);
            }
        }else{
            Printer.println("Powerup not discard!");
        }
    }

    private void notifyDropPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("Powerup drop!");
            if(!playerController.getPowerups().isEmpty()){
                Printer.println("Your powerups:");
                playerController.getPowerups().forEach(Printer::println);
            }
        }else{
            Printer.println("Powerup not drop!");
        }
    }

    private void notifyDropWeapon(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println("Weapon drop!");
            if(!playerController.getWeapons().isEmpty()){
                Printer.println("Your weapons:");
                playerController.getWeapons().forEach(Printer::println);
            }
        }else{
            Printer.println("Weapon not drop!");
        }
    }

    private void notifyFinalFrenzy(){
        Printer.println("Final Frenzy!!!");
        damageBoardPrinter.setFinalFrenzy(true);
    }

    private void notifyRespawn(Outcome outcome){
        Printer.println("Please, discard a powerup to respawn:");
        playerController.getPowerups().forEach(Printer::println);
        Printer.println("respawn: <powerup_number>");
    }

    public void notify(Message message){
        switch (message){
            case END_TURN:
                notifyEndTurn();
                break;
            case NOT_TURN:
                notifyNotTurn();
                break;
            case FINAL_FRENZY:
                notifyFinalFrenzy();
                break;
            default:
                break;
        }
    }

    public void notify(Message message, Outcome outcome){
        switch (message){
            case NEW_TURN:
                notifyNewTurn(outcome);
                break;
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
            case RECONNECTION:
                notifyReconnection(outcome);
                break;
            case DISCARD_POWERUP:
                notifyDiscardPowerup(outcome);
                break;
            case DROP_POWERUP:
                notifyDropPowerup(outcome);
                break;
            case DROP_WEAPON:
                notifyDropWeapon(outcome);
                break;
            case RESPAWN:
                notifyRespawn(outcome);
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
            case RELOAD:
                notifyReload(outcome, (String) object);
                break;
            default:
                break;
        }
    }
}
