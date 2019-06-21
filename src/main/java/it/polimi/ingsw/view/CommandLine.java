package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.*;
import it.polimi.ingsw.model.gamecomponents.*;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private WeaponHandler weaponHandler;


    public CommandLine(ClientInterface client){
        this.client = client;
        this.userInputStream = new BufferedReader(new InputStreamReader(System.in));
        this.weaponHandler = new WeaponHandler();
    }

    @Override
    public void start(){
        Printer.println(StringCLI.SERVER + StringCLI.INSERT_COMMAND);
        Printer.print(StringCLI.LOGIN_COMMAND);
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
                case StringCLI.HELP:
                    help();
                    break;
                case StringCLI.LOGIN:
                    if(!login(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.DISCONNECT:
                    disconnect();
                    break;
                case StringCLI.BOARD:
                    if(!board(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.DROP:
                    if(!drop(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.DISCARD:
                    if(!discard(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.CHOOSE:
                    if(!choose(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.SHOW:
                    if(!show(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.MOVE:
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
                case StringCLI.GRAB:
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
                case StringCLI.SHOOT:
                    if(playerController.isFinalFrenzy()){
                        if(!shootFinalFrenzy(string)){
                            Printer.print(StringCLI.INVALID_COMMAND);
                        }
                    }else{
                        if(!shoot(string)){
                            Printer.print(StringCLI.INVALID_COMMAND);
                        }
                    }
                    break;
                case StringCLI.POWERUP:
                    if(!powerup(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.RELOAD:
                    if(!reload(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.RESPAWN:
                    if(!respawn(string)){
                        Printer.print(StringCLI.INVALID_COMMAND);
                    }
                    break;
                case StringCLI.END:
                    endTurn();
                    break;
                default:
                    Printer.println(StringCLI.INVALID_COMMAND);
                    break;
            }
        }
    }

    private void help(){
        Printer.println(StringCLI.COMMANDS_LIST);
        Printer.println(StringCLI.HELP_COMMAND);
        Printer.println(StringCLI.LOGIN_COMMAND);
        Printer.println(StringCLI.DISCONNECT_COMMAND);
        Printer.println(StringCLI.SHOW_COMMAND);
        Printer.println(StringCLI.MOVE_COMMAND);
        Printer.println(StringCLI.GRAB_COMMAND);
        Printer.println(StringCLI.SHOOT_COMMAND);
        Printer.println(StringCLI.END_COMMAND);
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
                dropWeapon(Converter.fromStringToInt(input.nextToken()));
                return true;
            }else if(whatToDrop.equals("powerup")){
                dropPowerup(Converter.fromStringToInt(input.nextToken()));
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
            discardPowerup(Converter.fromStringToInt(input.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    private boolean board(StringTokenizer input){
        if(input.countTokens() == 2){
            try {
                client.board(Converter.fromStringToInt(input.nextToken()), Converter.fromStringToInt(input.nextToken()));
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
            int choice = Converter.fromStringToInt(input.nextToken());
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
                client.respawn(Converter.fromStringToInt(input.nextToken()));
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
                            client.showSquare(Converter.fromStringToInt(input.nextToken()), Converter.fromStringToInt(input.nextToken()));
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
        int choice = Converter.fromStringToInt(input.nextToken());
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
        if(input.countTokens() == 1){
            client.grab(Converter.fromStringToInt(input.nextToken()));
            return true;
        }else if(input.countTokens() == 2){
            client.grab(Converter.fromStringToInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 3 && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)){
            client.grab(Converter.fromStringToInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
                    Converter.fromStringToDirection(input.nextToken()));
            return true;
        }else if(input.countTokens() == 4 && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){ //&& firstPlayer
            client.grab(Converter.fromStringToInt(input.nextToken()), Converter.fromStringToDirection(input.nextToken()),
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
                    else{
                        return false;
                    }
                    Printer.println("Do you want to reload?: <yes> <no>");
                    String reload = userInputStream.readLine();
                    if(reload.equals("yes")){
                        Printer.println("Choose weapon to reload:");
                        moveAndReload = new StringTokenizer(userInputStream.readLine());
                        if(moveAndReload.countTokens()==1){
                            client.moveAndReload(first, second, moveAndReload.nextToken());
                        }else if(moveAndReload.countTokens()==2){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken());
                        }else if(moveAndReload.countTokens()==3){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken(),
                                    moveAndReload.nextToken());
                        }else{
                            return false;
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
                        }else if(moveAndReload.countTokens()==2){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken());
                        }else if(moveAndReload.countTokens()==3){
                            client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken(),
                                    moveAndReload.nextToken());
                        }else{
                            return false;
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

    private boolean weaponEffect(String weapon) throws IOException {
        switch(weapon){
            case "lockrifle":
                return weaponHandler.lockRifle(client, weapon);
            case "machinegun":
                return weaponHandler.machineGun(client, weapon);
            case "thor":
                return weaponHandler.thor(client, weapon);
            case "plasmagun":
                return weaponHandler.plasmaGun(client, weapon);
            case "whisper":
                return weaponHandler.whisper(client, weapon);
            case "electroscythe":
                return weaponHandler.electroscythe(client, weapon);
            case "tractorbeam":
                return weaponHandler.tractorBeam(client, weapon);
            case "vortexcannon":
                return weaponHandler.vortexCannon(client, weapon);
            case "furnace":
                return weaponHandler.furnace(client, weapon);
            case "heatseeker":
                return weaponHandler.heatseeker(client, weapon);
            case "hellion":
                return weaponHandler.hellion(client, weapon);
            case "flamethrower":
                return weaponHandler.flamethrower(client, weapon);
            case "grenadelauncher":
                return weaponHandler.grenadeLauncher(client, weapon);
            case "rocketlauncher":
                return weaponHandler.rocketLauncher(client, weapon);
            case "railgun":
                return weaponHandler.railgun(client, weapon);
            case "cyberblade":
                return weaponHandler.cyberblade(client, weapon);
            case "zx-2":
                return weaponHandler.zx_2(client, weapon);
            case "shotgun":
                return weaponHandler.shotgun(client, weapon);
            case "powerglove":
                return weaponHandler.powerGlove(client, weapon);
            case "shockwave":
                return weaponHandler.shockwave(client, weapon);
            case "sledgehammer":
                return weaponHandler.sledgehammer(client, weapon);
            default:
                break;
        }
        return false;
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
                    client.powerup(powerup, TokenColor.NONE, Color.NONE, Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()));
                    return true;
                }else{
                    return false;
                }
            default:
                break;
        }
        return false;
    }

    private boolean reload(StringTokenizer input) throws IOException {
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
                Printer.print(StringCLI.SERVER + StringCLI.SPACE + StringCLI.WRONG_USERNAME);
                break;
            case RIGHT:
            case ALL:
                Printer.println(StringCLI.SERVER + StringCLI.SPACE + username + StringCLI.SPACE + StringCLI.CONNECTED);
                break;
            default:
                break;
        }
    }

    public void notifySpawnLocation(List<Card> powerups){
        Printer.print(StringCLI.NEW_LINE);
        Printer.println(StringCLI.SERVER + StringCLI.SPACE + StringCLI.CHOOSE_POWERUP);
        powerups.forEach(p -> {
            Printer.println(p);
            Printer.print(StringCLI.NEW_LINE);
        });
        Printer.println(StringCLI.SERVER + StringCLI.INSERT_COMMAND);
        Printer.print(StringCLI.CHOOSE_COMMAND);
    }

    public void notifyDisconnection(Outcome outcome, String username){
        switch (outcome){
            case ALL:
                Printer.println(username + StringCLI.SPACE + StringCLI.DISCONNECTED);
                break;
            default:
                break;
        }
    }

    public void notifyColor(Outcome outcome, TokenColor color){
        switch(outcome){
            case WRONG:
                Printer.print(StringCLI.SERVER + StringCLI.WRONG_COLOR);
                break;
            case RIGHT:
                Printer.println(StringCLI.YOUR_COLOR + StringCLI.SPACE + Converter.fromTokenColorToString(color));
                break;
            default:
                break;
        }
    }

    private void notifyBoard(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            int i = 1;
            Printer.println(StringCLI.SERVER + StringCLI.CHOOSE_BOARD);
            for(BoardType boardType : BoardType.values()){
                Printer.print("   " + i + ": ");
                Printer.println(boardType);
                i++;
            }
            Printer.println(StringCLI.SERVER + StringCLI.INSERT_COMMAND);
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

    private void notifyShowSquare(Outcome outcome, SquareData squareData){
        if(outcome.equals(Outcome.RIGHT)){
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
        }else{
            Printer.println("Invalid square!");
        }
    }

    private void notifyScore(Map<TokenColor, Integer> scoreList){
        Printer.println("Score:");
        scoreList.forEach((c,i)->Printer.println(c + ": " + i));
    }

    private void notifyReload(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(playerController.getWeapon() + " reloaded!");
        }else{
            Printer.println(playerController.getWeapon() + " not reloaded!");
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
        if(playerController.isPlayerBoardFinalFrenzy()){
            damageBoardPrinter.setFinalFrenzy(true);
        }
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
            case RELOAD:
                notifyReload(outcome);
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
                notifyShowSquare(outcome, (SquareData) object);
                break;
            case SCORE:
                notifyScore((Map<TokenColor, Integer>) object);
                break;
            default:
                break;
        }
    }
}
