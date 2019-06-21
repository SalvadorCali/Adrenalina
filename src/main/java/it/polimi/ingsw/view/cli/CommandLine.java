package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.*;
import it.polimi.ingsw.model.gamecomponents.*;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.ViewInterface;

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
    private GameBoard gameBoard;
    private Player player;
    private AmmoBoxReserveCLI ammoPrinter;
    private DamageBoardCLI damageBoardPrinter;
    private MapCLI gameBoardPrinter;
    private KillshotTrackCLI killshotTrackPrinter;
    private WeaponHandler weaponHandler;

    /**
     * Class constructor.
     * @param client the client for which this class takes inputs.
     */
    public CommandLine(ClientInterface client){
        this.client = client;
        this.userInputStream = new BufferedReader(new InputStreamReader(System.in));
        this.weaponHandler = new WeaponHandler();
    }

    /**
     * Creates a thread that takes inputs from the user.
     */
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

    /**
     * sets the player controller for the client.
     * @param playerController that will be set.
     */
    public void setPlayerController(PlayerController playerController){
        this.playerController = playerController;
    }

    /**
     * analyzes the user's input and calls the right method or return an error.
     * @param message the user's input.
     * @throws IOException caused by reading inputs.
     */
    private void readInput(String message) throws IOException {
        StringTokenizer string = new StringTokenizer(message);
        if(string.hasMoreTokens()){
            switch(string.nextToken()){
                case StringCLI.HELP:
                    if(playerController.isFinalFrenzy()){
                        helpFinalFrenzy();
                    }else{
                        help();
                    }
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

    /**
     * shows the list of commands.
     */
    private void help(){
        Printer.println(StringCLI.COMMANDS_LIST);
        Printer.println(StringCLI.LOGIN_COMMAND);
        Printer.println(StringCLI.DISCONNECT_COMMAND);
        Printer.println(StringCLI.SHOW_COMMAND);
        Printer.println(StringCLI.MOVE_COMMAND);
        Printer.println(StringCLI.GRAB_COMMAND);
        Printer.println(StringCLI.SHOOT_COMMAND);
        Printer.println(StringCLI.END_COMMAND);
    }

    private void helpFinalFrenzy(){
        Printer.println(StringCLI.COMMANDS_LIST);
        Printer.println(StringCLI.DISCONNECT_COMMAND);
        Printer.println(StringCLI.SHOW_COMMAND);
        if(playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){
            Printer.println(StringCLI.MOVE_COMMAND);
            Printer.println(StringCLI.GRAB_COMMAND);
            Printer.println(StringCLI.SHOOT_COMMAND);
        }else{
            Printer.println(StringCLI.MOVE_COMMAND);
            Printer.println(StringCLI.GRAB_COMMAND);
            Printer.println(StringCLI.SHOOT_COMMAND);
        }
        Printer.println(StringCLI.END_COMMAND);
    }

    /**
     * Takes an username and a color for the user and calls the relative Client's method.
     * @param input user's input.
     * @return true if the input is correct.
     */
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

    /**
     * Disconnect the client from the game.
     */
    private void disconnect(){
        try {
            client.disconnect();
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes an integer that represents a weapon or a powerup to drop during the turn.
     * @param input user's input.
     * @return true if the input is correct.
     */
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

    /**
     * Takes an integer that represents a powerup to discard and using it as ammo.
     * @param input user's input.
     * @return true if the input is correct.
     */
    private boolean discard(StringTokenizer input){
        if(input.hasMoreTokens() && input.countTokens()==1){
            discardPowerup(Converter.fromStringToInt(input.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Takes two integers that represent the chosen game board and the number of skulls for the game and calls the relative Client's method.
     * @param input user's input.
     * @return true if the input is correct.
     */
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

    /**
     * Takes an integer that represent the first powerup's choice and calls the relative Client's method.
     * @param input user's input.
     * @return true if the input is correct.
     */
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

    /**
     * Takes an integer that represents the chosen powerup to discard to respawn and calls the relative Cleint's method.
     * @param input user's input.
     * @return true if the input is correct.
     */
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

    /**
     * Takes an input that represents what the Client want to see about the game, and calls the relative method that prints the choice.
     * @param input user's input.
     * @return true if the input is correct.
     */
    private boolean show(StringTokenizer input){
        boolean result = false;
        if(input.hasMoreTokens()){
            String next = input.nextToken();
            switch (next){
                case StringCLI.PLAYERBOARD:
                    printPlayerBoard();
                    break;
                case StringCLI.POWERUPS:
                    printPowerups();
                    break;
                case StringCLI.WEAPONS:
                    printWeapons();
                    break;
                case StringCLI.OTHERS:
                    printOthersPlayerBoards();
                    break;
                case StringCLI.SQUARE:
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
                case StringCLI.BOARD:
                    printGameBoard();
                    break;
                default:
                    break;
            }
            result = true;
        }
        return result;
    }

    /**
     * Takes 1, 2 or 3 Direction objects that represent the directions where user wants to move and calls the relative Client's method.
     * @param input user's input.
     * @return true if the input is correct.
     * @throws IOException caused by input.
     */
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

    /**
     * Takes 1, 2, 3 or 4 Direction objects that represent the directions where the user wants to move during the final frenzy and calls the relative Client's method.
     * @param input user's input.
     * @return true if the input is correct.
     * @throws IOException caused by input.
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

    /**
     * Takes an integer and 1 or 2 Direction for the grab action and calls the relative Cleint's method.
     * @param input user's input.
     * @return true if the input is correct.
     * @throws IOException caused by the input.
     */
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

    /**
     * Calls the relative Client's method that drop a weapon.
     * @param weapon the int that represents the chosen weapon.
     */
    private void dropWeapon(int weapon){
        try {
            client.dropWeapon(weapon);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Calls the relative Client's method that drop a powerup.
     * @param powerup the int that represents the chosen powerup.
     */
    private void dropPowerup(int powerup){
        try {
            client.dropPowerup(powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Calls the relative Client's method that discard a powerup to use it as ammo.
     * @param powerup the int that represents the chosen powerup.
     */
    private void discardPowerup(int powerup){
        try {
            client.discardPowerup(powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes an integer and 1, 2, 3 or 4 Direction for the grab action during the final frenzy and calls the relative Cleint's method.
     * @param input user's input.
     * @return true if the input is correct.
     * @throws IOException caused by the input.
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

    /**
     * Takes a String that represents a weapon and calls the {@link #weaponEffect(String)} method. If the player has enough damages, it takes also a Direction and move him calling the relative Client's method.
     * @param input user's input.
     * @return true if the input is correct.
     */
    private boolean shoot(StringTokenizer input){
        Printer.println(playerController.getPlayer().isMoveAndReload());
        String weapon;
        if(input.hasMoreTokens()){
            //weapon = input.nextToken();
            if(playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND)){
                Printer.println(StringCLI.WANT_MOVE + StringCLI.SPACE + StringCLI.YES_INPUT + StringCLI.SPACE + StringCLI.NO_INPUT);
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

    /**
     * Takes a String that represents a weapon and calls the {@link #weaponEffect(String)} method. Then it can take also up to 2 Direction objects, and up to 3 String that represents weapons to call the {@link ClientInterface#moveAndReload(Direction, Direction, String...)} method.
     * @param input user's input.
     * @return true if the input is correct.
     * @throws IOException caused by input.
     */
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

    /**
     * Takes the chosen weapon and calls the relative {@link WeaponHandler} method.
     * @param weapon the chosen weapon.
     * @return true if the input is correct.
     * @throws IOException caused by input.
     */
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
            case StringCLI.TARGETING_SCOPE:
                Printer.println("Effect: <victim> <ammo>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToColor(string.nextToken()),
                            -1, -1);
                    return true;
                }else{
                    return false;
                }
            case StringCLI.NEWTON:
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
            case StringCLI.TAGBACK_GRENADE:
                Printer.println("Effect: <victim>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Color.NONE, -1, -1);
                    return true;
                }else{
                    return false;
                }
            case StringCLI.TELEPORTER:
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
                Printer.println(StringCLI.SERVER + username + StringCLI.SPACE + StringCLI.CONNECTED);
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
                Printer.println(StringCLI.SERVER + username + StringCLI.SPACE + StringCLI.DISCONNECTED);
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
                Printer.print(StringCLI.MULTIPLE_SPACE + i + StringCLI.COLON + StringCLI.SPACE);
                Printer.println(boardType);
                i++;
            }
            Printer.println(StringCLI.SERVER + StringCLI.INSERT_COMMAND);
            Printer.print(StringCLI.BOARD_COMMAND);
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.FIRST_PLAYER_BOARD);
        }
    }

    private void notifyNewTurn(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + StringCLI.NEW_TURN);
            printGameBoard();
            printPlayerBoard();
            printWeaponsAndPowerups();
            printSquare();
            Printer.println(StringCLI.NEW_LINE + StringCLI.SERVER + StringCLI.COMMANDS);
        }

    }

    private void notifyEndTurn(){
        Printer.println(StringCLI.SERVER + StringCLI.END_TURN);
    }

    private void notifyNotTurn(){
        Printer.println(StringCLI.SERVER + StringCLI.NOT_TURN);
    }

    private void notifyGame(Outcome outcome){
        switch (outcome){
            case WRONG:
                Printer.println(StringCLI.SERVER + StringCLI.GAME_BEGUN);
                break;
            case ALL:
                createCliPrinters();
                Printer.println(StringCLI.SERVER + StringCLI.GAME_STARTED);
                break;
            default:
                break;
        }
    }

    private void notifyMovement(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)){
            Printer.println(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.MOVED);
            printGameBoard();
            printSquare();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.NOT_MOVED);
        }
    }

    private void notifyPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + playerController.getPowerup() + StringCLI.SPACE + StringCLI.USED);
        }else{
            Printer.println(StringCLI.SERVER + playerController.getPowerup() + StringCLI.SPACE + StringCLI.NOT_USED);
        }
        switch(playerController.getPowerup()){
            case StringCLI.TARGETING_SCOPE:
            case StringCLI.TAGBACK_GRENADE:
                printVictims();
                break;
            case StringCLI.NEWTON:
            case StringCLI.TELEPORTER:
                printGameBoard();
                break;
            default:
                break;
        }
    }

    private void notifyGrab(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.GRABBED);
            printGameBoard();
            printPlayerBoard();
            printWeaponsAndPowerups();
        }else if(outcome.equals(Outcome.WRONG)){
            Printer.println(StringCLI.SERVER + StringCLI.NOT_GRABBED);
        }else{
            Printer.println(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.USER_GRABBED);
            if(playerController.isMovement()){
                printGameBoard();
                playerController.setMovement(false);
            }
        }
    }

    private void notifyShoot(Outcome outcome){
        switch(outcome){
            case RIGHT:
            case ALL:
                Printer.println(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.SHOT);
                if(playerController.isMovement()){
                    printGameBoard();
                    playerController.setMovement(false);
                }
                printVictims();
                break;
            default:
                Printer.println(StringCLI.SERVER + StringCLI.NOT_SHOT);
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
                    Printer.println("POWERUP");
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
        Printer.println(StringCLI.SERVER + StringCLI.KILLSHOT_SCORE);
        scoreList.forEach((c,i)->Printer.println(c + StringCLI.COLON + StringCLI.SPACE + i));
    }

    private void notifyReload(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(playerController.getWeapon() + StringCLI.SPACE + StringCLI.RELOADED);
        }else{
            Printer.println(playerController.getWeapon() + StringCLI.SPACE + StringCLI.NOT_RELOADED);
        }
    }

    private void notifyReconnection(Outcome outcome, String username){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + username + StringCLI.SPACE + StringCLI.RECONNECTED);
            createCliPrinters();
            killshotTrackPrinter.printKillshotTrack();
            gameBoardPrinter.printMap();
            damageBoardPrinter.printDamageBoard();
        }else{
            Printer.println(StringCLI.SERVER + username + StringCLI.SPACE + StringCLI.RECONNECTED);
        }
    }

    private void notifyDiscardPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.DISCARDED);
            printPowerups();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.NOT_DISCARDED);
        }
    }

    private void notifyDropPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.DROPPED);
            printPowerups();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.NOT_DROPPED);
        }
    }

    private void notifyDropWeapon(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + StringCLI.WEAPON_DROP + StringCLI.SPACE + StringCLI.DROPPED);
            printWeapons();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.WEAPON_DROP + StringCLI.SPACE + StringCLI.NOT_DROPPED);
        }
    }

    private void notifyFinalFrenzy(){
        Printer.println(StringCLI.SERVER + StringCLI.FINAL_FRENZY);
        if(playerController.isPlayerBoardFinalFrenzy()){
            damageBoardPrinter.setFinalFrenzy(true);
        }
    }

    private void notifyRespawn(Outcome outcome){
        Printer.println(StringCLI.SERVER + StringCLI.RESPAWN_POWERUP);
        playerController.getPowerups().forEach(Printer::println);
        Printer.println(StringCLI.RESPAWN_COMMAND);
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
            case RECONNECTION:
                notifyReconnection(outcome, (String) object);
                break;
            default:
                break;
        }
    }

    private void createCliPrinters(){
        gameBoardPrinter = new MapCLI(playerController.getGameBoard());
        ammoPrinter = new AmmoBoxReserveCLI(playerController.getPlayer());
        damageBoardPrinter = new DamageBoardCLI(playerController.getPlayer());
        killshotTrackPrinter = new KillshotTrackCLI(playerController.getKillshotTrack());
    }

    private void printGameBoard(){
        killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
        killshotTrackPrinter.printKillshotTrack();
        gameBoardPrinter.setGameBoard(playerController.getGameBoard());
        gameBoardPrinter.printMap();
    }

    private void printPlayerBoard(){
        damageBoardPrinter.setPlayer(playerController.getPlayer());
        damageBoardPrinter.printDamageBoard();
    }

    private void printOthersPlayerBoards(){
        damageBoardPrinter.setVictims(playerController.getOtherPlayers());
        damageBoardPrinter.printVictimsDamageBoard();
    }

    private void printVictims(){
        damageBoardPrinter.setVictims(playerController.getVictims());
        damageBoardPrinter.printVictimsDamageBoard();
    }

    private void printWeapons(){
        if(!playerController.getWeapons().isEmpty()){
            Printer.println("Your weapons:");
            playerController.getWeapons().forEach(Printer::println);
            Printer.print(StringCLI.NEW_LINE);
        }
    }

    private void printPowerups(){
        if(!playerController.getPowerups().isEmpty()){
            Printer.println("Your powerups:");
            playerController.getPowerups().forEach(Printer::println);
            Printer.print(StringCLI.NEW_LINE);
        }
    }

    private void printWeaponsAndPowerups(){
        printWeapons();
        printPowerups();
    }

    private void printSquare(){
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
