package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.controller.datas.SquareData;
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

/**
 * This is the main class of the CLI. It takes inputs and prints information about the game.
 */
public class CommandLine implements ViewInterface {
    /**
     * The relative Client.
     */
    private ClientInterface client;
    /**
     * The username of the player.
     */
    private String username;
    /**
     * The PlayerController that contains datas about the player.
     */
    private PlayerController playerController;
    /**
     * The user input stream.
     */
    private BufferedReader userInputStream;
    /**
     * The playerboard's printer.
     */
    private DamageBoardCLI damageBoardPrinter;
    /**
     * The gameboard's printer.
     */
    private MapCLI gameBoardPrinter;
    /**
     * The killshot track's printer.
     */
    private KillshotTrackCLI killshotTrackPrinter;
    /**
     * The weapon's handler.
     */
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
     * Sets the player controller for the client.
     * @param playerController that will be set.
     */
    public void setPlayerController(PlayerController playerController){
        this.playerController = playerController;
    }

    /**
     * Analyzes the user's input and calls the right method or return an error.
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
     * Shows the list of commands.
     */
    private void help(){
        Printer.println(StringCLI.COMMANDS_LIST);
        Printer.println(StringCLI.LOGIN_COMMAND_2);
        Printer.println(StringCLI.DISCONNECT_COMMAND);
        Printer.println(StringCLI.SHOW_COMMAND);
        Printer.println(StringCLI.MOVE_COMMAND);
        Printer.println(StringCLI.GRAB_COMMAND);
        Printer.println(StringCLI.SHOOT_COMMAND);
        Printer.println(StringCLI.END_COMMAND);
    }

    /**
     * Shows the list of commands during final frenzy.
     */
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
            if(whatToDrop.equals(StringCLI.WEAPON)){
                dropWeapon(Converter.fromStringToInt(input.nextToken()));
                return true;
            }else if(whatToDrop.equals(StringCLI.POWERUP)){
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
                    if(movement.equals(StringCLI.YES)){
                        Printer.print(StringCLI.CHOOSE_DIRECTION + StringCLI.SPACE);
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
            Printer.println(StringCLI.WANT_MOVE + StringCLI.COLON + StringCLI.SPACE + StringCLI.YES_INPUT + StringCLI.SPACE + StringCLI.NO_INPUT);
            try {
                String movement = userInputStream.readLine();
                if(movement.equals(StringCLI.YES)){
                    Printer.println(StringCLI.CHOOSE_DIRECTIONS + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
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
                    Printer.println(StringCLI.WANT_RELOAD + StringCLI.SPACE + StringCLI.YES_INPUT + StringCLI.SPACE + StringCLI.NO_INPUT);
                    String reload = userInputStream.readLine();
                    if(reload.equals(StringCLI.YES)){
                        Printer.println(StringCLI.CHOOSE_WEAPON);
                        moveAndReload = new StringTokenizer(userInputStream.readLine());
                        if(moveAndReload.countTokens()==1){
                            if(second != null){
                                client.moveAndReload(first, second, moveAndReload.nextToken());
                            }else{
                                client.moveAndReload(first, moveAndReload.nextToken());
                            }
                        }else if(moveAndReload.countTokens()==2){
                            if(second != null){
                                client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken());
                            }else{
                                client.moveAndReload(first, moveAndReload.nextToken(), moveAndReload.nextToken());
                            }
                        }else if(moveAndReload.countTokens()==3){
                            if(second != null){
                                client.moveAndReload(first, second, moveAndReload.nextToken(), moveAndReload.nextToken(),
                                        moveAndReload.nextToken());
                            }else{
                                client.moveAndReload(first, moveAndReload.nextToken(), moveAndReload.nextToken(),
                                        moveAndReload.nextToken());
                            }
                        }else{
                            return false;
                        }
                    }
                }else{
                    Printer.println(StringCLI.WANT_RELOAD + StringCLI.SPACE + StringCLI.YES_INPUT + StringCLI.SPACE + StringCLI.NO_INPUT);
                    String reload = userInputStream.readLine();
                    if(reload.equals(StringCLI.YES)){
                        Printer.println(StringCLI.CHOOSE_WEAPON);
                        moveAndReload = new StringTokenizer(userInputStream.readLine());
                        if(moveAndReload.countTokens()==1){
                            client.moveAndReload(first, moveAndReload.nextToken());
                        }else if(moveAndReload.countTokens()==2){
                            client.moveAndReload(first, moveAndReload.nextToken(), moveAndReload.nextToken());
                        }else if(moveAndReload.countTokens()==3){
                            client.moveAndReload(first, moveAndReload.nextToken(), moveAndReload.nextToken(),
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
            case StringCLI.LOCK_RIFLE:
                return weaponHandler.lockRifle(client, weapon);
            case StringCLI.MACHINE_GUN:
                return weaponHandler.machineGun(client, weapon);
            case StringCLI.THOR:
                return weaponHandler.thor(client, weapon);
            case StringCLI.PLASMA_GUN:
                return weaponHandler.plasmaGun(client, weapon);
            case StringCLI.WHISPER:
                return weaponHandler.whisper(client, weapon);
            case StringCLI.ELECTROSCYTHE:
                return weaponHandler.electroscythe(client, weapon);
            case StringCLI.TRACTOR_BEAM:
                return weaponHandler.tractorBeam(client, weapon);
            case StringCLI.VORTEX_CANNON:
                return weaponHandler.vortexCannon(client, weapon);
            case StringCLI.FURNACE:
                return weaponHandler.furnace(client, weapon);
            case StringCLI.HEATSEEKER:
                return weaponHandler.heatseeker(client, weapon);
            case StringCLI.HELLION:
                return weaponHandler.hellion(client, weapon);
            case StringCLI.FLAMETHROWER:
                return weaponHandler.flamethrower(client, weapon);
            case StringCLI.GRENADE_LAUNCHER:
                return weaponHandler.grenadeLauncher(client, weapon);
            case StringCLI.ROCKET_LAUNCHER:
                return weaponHandler.rocketLauncher(client, weapon);
            case StringCLI.RAILGUN:
                return weaponHandler.railgun(client, weapon);
            case StringCLI.CYBERBLADE:
                return weaponHandler.cyberblade(client, weapon);
            case StringCLI.ZX_2:
                return weaponHandler.zx2(client, weapon);
            case StringCLI.SHOTGUN:
                return weaponHandler.shotgun(client, weapon);
            case StringCLI.POWER_GLOVE:
                return weaponHandler.powerGlove(client, weapon);
            case StringCLI.SHOCKWAVE:
                return weaponHandler.shockwave(client, weapon);
            case StringCLI.SLEDGEHAMMER:
                return weaponHandler.sledgehammer(client, weapon);
            default:
                break;
        }
        return false;
    }

    /**
     * Takes a StringTokenizer and then calls the {@link #powerupEffect(String)} method that handles the powerup.
     * @param input a powerup name.
     * @return true if the input is correct.
     */
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

    /**
     * Takes a powerup name and handles it. It takes the parameters for the action an then calls the {@link ClientInterface#powerup(String, TokenColor, Color, int, int, Direction...)} method.
     * @param powerup the chosen powerup.
     * @return if the inputs are correct.
     * @throws IOException caused by the streams.
     */
    private boolean powerupEffect(String powerup) throws IOException {
        StringTokenizer string;
        switch(powerup){
            case StringCLI.TARGETING_SCOPE:
                Printer.println(StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.AMMO);
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 2){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToColor(string.nextToken()),
                            -1, -1);
                    return true;
                }else{
                    return false;
                }
            case StringCLI.NEWTON:
                Printer.println(StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION);
                Printer.println(StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
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
                Printer.println(StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM);
                string = new StringTokenizer(userInputStream.readLine());
                if(string.countTokens() == 1){
                    client.powerup(powerup, Converter.fromStringToTokenColor(string.nextToken()), Color.NONE, -1, -1);
                    return true;
                }else{
                    return false;
                }
            case StringCLI.TELEPORTER:
                Printer.println(StringCLI.EFFECT + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y);
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

    /**
     * Takes a weapon name and then calls the relative Client method to reload a weapon.
     * @param input a weapon name.
     * @return true if the input is correct.
     * @throws IOException caused by the streams.
     */
    private boolean reload(StringTokenizer input) throws IOException {
        if(input.countTokens() == 1){
            client.reload(input.nextToken());
            return true;
        }else{
            return false;
        }
    }

    /**
     * Calls the relative Client method to end the turn.
     */
    private void endTurn(){
        try {
            client.endTurn();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Prints the result of the login action.
     * @param outcome the result of the action.
     * @param username the username of the player who tried to login.
     */
    private void notifyLogin(Outcome outcome, String username){
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

    /**
     * Prints a list of two powerups that the Client could choice for the spawn.
     * @param powerups two powerups.
     */
    private void notifySpawnLocation(List<Card> powerups){
        Printer.print(StringCLI.NEW_LINE);
        Printer.println(StringCLI.SERVER + StringCLI.SPACE + StringCLI.CHOOSE_POWERUP);
        powerups.forEach(p -> {
            Printer.println(p);
            Printer.print(StringCLI.NEW_LINE);
        });
        Printer.println(StringCLI.SERVER + StringCLI.INSERT_COMMAND);
        Printer.print(StringCLI.CHOOSE_COMMAND);
    }

    /**
     * Prints the username of the player who is disconnected.
     * @param outcome the result of the action.
     * @param username the username of the disconnected player.
     */
    private void notifyDisconnection(Outcome outcome, String username){
        if (outcome == Outcome.ALL) {
            Printer.println(StringCLI.SERVER + username + StringCLI.SPACE + StringCLI.DISCONNECTED);
        }
    }

    /**
     * Prints to the user the result of the choice of the color.
     * @param outcome the result of the action.
     * @param color the chosen color.
     */
    private void notifyColor(Outcome outcome, TokenColor color){
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

    /**
     * Shows to the first player the boards and he has to choice one of them.
     * @param outcome the result of the action.
     */
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

    /**
     * Shows the information about the new turn, like the board and the playerboard.
     * @param outcome the result of the action.
     */
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

    /**
     * Shows to the user that his turn is end.
     */
    private void notifyEndTurn(){
        Printer.println(StringCLI.SERVER + StringCLI.END_TURN);
    }

    /**
     * Shows to the user that is not his turn.
     */
    private void notifyNotTurn(){
        Printer.println(StringCLI.SERVER + StringCLI.NOT_TURN);
    }

    /**
     * Notify the beginning of the game or that it's already begun.
     * @param outcome the result of the action.
     */
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

    /**
     * Notify the result of the movement action, showing the board.
     * @param outcome the result of the action.
     */
    private void notifyMovement(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)){
            Printer.println(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.MOVED);
            printGameBoard();
            printSquare();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.NOT_MOVED);
        }
    }

    /**
     * Notify to the user the use of a powerup.
     * @param outcome the result of the action.
     */
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

    /**
     * Notify the grab action to the user, showing the updated playerboard.
     * @param outcome the result of the action.
     */
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

    /**
     * Notify the shoot action, showing the playerboard of the victims.
     * @param outcome the result of the action.
     */
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

    /**
     * Shows the cards that are inside a square.
     * @param outcome the result of the action.
     * @param squareData the datas of the square.
     */
    private void notifyShowSquare(Outcome outcome, SquareData squareData){
        if(outcome.equals(Outcome.RIGHT)){
            if(squareData.getAmmoCard() != null){
                Printer.println(StringCLI.AMMOS_SQUARE);
                Printer.print(squareData.getAmmoCard().getFirstAmmo().getColor() + ", " +
                        squareData.getAmmoCard().getSecondAmmo().getColor() + ", ");
                if(squareData.getAmmoCard().isPowerup()){
                    Printer.println(StringCLI.POWERUP_CAPS);
                }else{
                    Printer.println(squareData.getAmmoCard().getThirdAmmo().getColor());
                }
            }
            if(squareData.getWeapons() != null){
                Printer.println(StringCLI.WEAPONS_SQUARE);
                squareData.getWeapons().forEach(Printer::println);
            }
        }else{
            Printer.println(StringCLI.INVALID_SQUARE);
        }
    }

    /**
     * Prints the score.
     * @param scoreList the score of the game.
     */
    private void notifyScore(Map<TokenColor, Integer> scoreList){
        Printer.println(StringCLI.SERVER + StringCLI.KILLSHOT_SCORE);
        scoreList.forEach((c,i)->Printer.println(c + StringCLI.COLON + StringCLI.SPACE + i));
    }

    /**
     * Prints the score and that the game is end.
     * @param scoreList the score of the game.
     */
    private void notifyEndGame(Map<TokenColor, Integer> scoreList){
        Printer.println(StringCLI.SERVER + StringCLI.GAME_END);
        Printer.println(StringCLI.SERVER + StringCLI.KILLSHOT_SCORE);
        scoreList.forEach((c,i)->Printer.println(c + StringCLI.COLON + StringCLI.SPACE + i));
    }

    /**
     * Notify the reload action, showing if a weapon card was reloaded or not.
     * @param outcome the result of the action.
     */
    private void notifyReload(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(playerController.getWeapon() + StringCLI.SPACE + StringCLI.RELOADED);
        }else{
            Printer.println(playerController.getWeapon() + StringCLI.SPACE + StringCLI.NOT_RELOADED);
        }
    }

    /**
     * Prints to the user the name of the reconnected player and the game datas.
     * @param outcome the result of the action.
     * @param username the name of the player.
     */
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

    /**
     * Notify the discard action.
     * @param outcome the result of the action.
     */
    private void notifyDiscardPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.DISCARDED);
            printPowerups();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.NOT_DISCARDED);
        }
    }

    /**
     * Notify the drop action.
     * @param outcome the result of the action.
     */
    private void notifyDropPowerup(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.DROPPED);
            printPowerups();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.NOT_DROPPED);
        }
    }

    /**
     * Notify the drop action.
     * @param outcome the result of the action.
     */
    private void notifyDropWeapon(Outcome outcome){
        if(outcome.equals(Outcome.RIGHT)){
            Printer.println(StringCLI.SERVER + StringCLI.WEAPON_DROP + StringCLI.SPACE + StringCLI.DROPPED);
            printWeapons();
        }else{
            Printer.println(StringCLI.SERVER + StringCLI.WEAPON_DROP + StringCLI.SPACE + StringCLI.NOT_DROPPED);
        }
    }

    /**
     * Notify the beginning of the final frenzy.
     */
    private void notifyFinalFrenzy(){
        Printer.println(StringCLI.SERVER + StringCLI.FINAL_FRENZY);
        if(playerController.isPlayerBoardFinalFrenzy()){
            damageBoardPrinter.setFinalFrenzy(true);
        }
    }

    /**
     * Shows to the player his powerup for the respawn.
     * @param outcome the result of the action.
     */
    private void notifyRespawn(Outcome outcome){
        Printer.println(StringCLI.SERVER + StringCLI.RESPAWN_POWERUP);
        playerController.getPowerups().forEach(Printer::println);
        Printer.println(StringCLI.RESPAWN_COMMAND);
    }

    /**
     * Handles the messages from the Client to notify actions.
     * @param message a message.
     */
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

    /**
     * Handles the messagers from the Client to notify actions.
     * @param message a message.
     * @param outcome the outcome of the action.
     */
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

    /**
     * Handles the messagers from the Client to notify actions.
     * @param message a message.
     * @param outcome the outcome of the action.
     * @param object an object.
     */
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
            case END_GAME:
                notifyEndGame((Map<TokenColor, Integer>) object);
                break;
            case RECONNECTION:
                notifyReconnection(outcome, (String) object);
                break;
            default:
                break;
        }
    }

    /**
     * Creates the objects that print the game board, the playerboard and the killshot track.
     */
    private void createCliPrinters(){
        gameBoardPrinter = new MapCLI(playerController.getGameBoard());
        damageBoardPrinter = new DamageBoardCLI(playerController.getPlayer());
        killshotTrackPrinter = new KillshotTrackCLI(playerController.getKillshotTrack());
    }

    /**
     * Prints the game board with the killshot track.
     */
    private void printGameBoard(){
        killshotTrackPrinter.setKillshotTrack(playerController.getKillshotTrack());
        killshotTrackPrinter.printKillshotTrack();
        gameBoardPrinter.setGameBoard(playerController.getGameBoard());
        gameBoardPrinter.printMap();
    }

    /**
     * Prints the playerboard.
     */
    private void printPlayerBoard(){
        damageBoardPrinter.setPlayer(playerController.getPlayer());
        damageBoardPrinter.printDamageBoard();
    }

    /**
     * Prints the playerboard of the other players.
     */
    private void printOthersPlayerBoards(){
        damageBoardPrinter.setVictims(playerController.getOtherPlayers());
        damageBoardPrinter.printVictimsDamageBoard();
    }

    /**
     * Prints the victims' playerboards.
     */
    private void printVictims(){
        damageBoardPrinter.setVictims(playerController.getVictims());
        damageBoardPrinter.printVictimsDamageBoard();
    }

    /**
     * Prints the user's weapons.
     */
    private void printWeapons(){
        if(!playerController.getWeapons().isEmpty()){
            Printer.println(StringCLI.YOUR_WEAPONS);
            playerController.getWeapons().forEach(Printer::println);
            Printer.print(StringCLI.NEW_LINE);
        }
    }

    /**
     * Prints the user's powerups.
     */
    private void printPowerups(){
        if(!playerController.getPowerups().isEmpty()){
            Printer.println(StringCLI.YOUR_POWERUPS);
            playerController.getPowerups().forEach(Printer::println);
            Printer.print(StringCLI.NEW_LINE);
        }
    }

    /**
     * Prints the user's weapons and powerups.
     */
    private void printWeaponsAndPowerups(){
        printWeapons();
        printPowerups();
    }

    /**
     * Prints a square, with weapons or ammo cards.
     */
    private void printSquare(){
        Printer.println(StringCLI.YOUR_SQUARE);
        Square square = playerController.getGameBoard().getArena()[playerController.getPlayer().getPosition().getX()][playerController.getPlayer().getPosition().getY()];
        if(square.getAmmoCard() != null){
            Printer.print(StringCLI.AMMO_CARD + StringCLI.SPACE);
            Printer.print(square.getAmmoCard().getFirstAmmo().getColor() + ", " +
                    square.getAmmoCard().getSecondAmmo().getColor() + ", ");
            if(square.getAmmoCard().isPowerup()){
                Printer.println(StringCLI.POWERUP_CAPS);
            }else{
                Printer.println(square.getAmmoCard().getThirdAmmo().getColor());
            }
        }
        if(square.getWeapons() != null){
            square.getWeapons().forEach(Printer::println);
        }
    }
}
