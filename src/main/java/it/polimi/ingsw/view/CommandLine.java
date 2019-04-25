package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

public class CommandLine implements ViewInterface {
    private ClientInterface client;
    private PlayerController playerController;
    private BufferedReader userInputStream;
    private MapCLI mapCLI;

    public CommandLine(ClientInterface client){
        this.client = client;
        this.userInputStream = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void start(){
        Printer.print("[CLIENT]Please, insert a command:");
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

    private void readInput(String message) throws RemoteException {
        StringTokenizer string = new StringTokenizer(message);
        switch(string.nextToken()){
            case "help":
                help();
                break;
            case "login":
                login(string);
                //Printer.println(Config.INVALID_COMMAND);
                break;
            case "disconnect":
                //method
                break;
            case "show":
                show(string);
                //Printer.println(Config.INVALID_COMMAND);
                break;
            case "move":
                move(string);
                //Printer.println(Config.INVALID_COMMAND);
                break;
            case "grab":
                grab(string);
                //Printer.println(Config.INVALID_COMMAND);
                break;
            case "shoot":
                shoot(string);
                break;
            case "end":
                endTurn();
                break;
            default:
                Printer.println(Config.INVALID_COMMAND);
                break;
        }
    }

    private void help(){
        Printer.println("[CLIENT]List of Commands");
        Printer.println("help :");
        Printer.println("login <username> :");
        Printer.println("color <character_color> :");
        Printer.println("disconnect :");
        Printer.println("show <object> :");
        Printer.println("move <first_direction, ..., last_direction> :");
        Printer.println("grab <direction> <0, 1, 2, 3>:");
        Printer.println("shoot <victim> <weapon_name> <weapon_effect_number...>");
    }

    private void login(StringTokenizer input){
        if(input.hasMoreTokens()){
            String username = input.nextToken();
            if(input.hasMoreTokens()){
                String color = input.nextToken();
                try {
                    client.login(username, Parser.castStringToTokenColor(color));
                } catch (RemoteException e) {
                    Printer.err(e);
                }
            }
        }
    }

    private void show(StringTokenizer input){
        if(input.hasMoreTokens()){
            String next = input.nextToken();
            switch (next){
                case "score":
                    Printer.println("Your score is: " + playerController.getScore());
                    break;
                case "playerboard":
                    Printer.println("Your board is:");
                    Printer.println(playerController.getPlayerBoard());
                    break;
                case "ammos":
                    Printer.println("Your ammos are:");
                    Printer.println(playerController.getAmmos());
                    break;
                case "powerups":
                    Printer.println("Your powerups are:");
                    Printer.println(playerController.getPowerups());
                    break;
                case "weapons":
                    Printer.println("Your weapons are:");
                    Printer.println(playerController.getWeapons());
                    break;
                case "map":
                    mapCLI.printMap();
                    break;
                default:
                    break;
            }
        }
    }

    private void move(StringTokenizer input){
        Direction first, second, third;
        if(input.hasMoreTokens()){
            first = Converter.fromStringToDirection(input.nextToken());
            if(input.hasMoreTokens()){
                second = Converter.fromStringToDirection(input.nextToken());
                if(input.hasMoreTokens()){
                    third = Converter.fromStringToDirection(input.nextToken());
                    try {
                        client.move(first, second, third);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }else{
                    try {
                        client.move(first, second);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else{
                try {
                    client.move(first);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
    }

    private void grab(StringTokenizer input) throws RemoteException {
        if(input.hasMoreTokens()) {
            String next = input.nextToken();
            int choice = Integer.parseInt(next);
            if (input.hasMoreTokens()) {
                Direction firstD = Converter.fromStringToDirection(input.nextToken());
                if (!client.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) {
                    if (input.hasMoreTokens()) {
                        try {
                            client.grab(choice, firstD, Converter.fromStringToDirection(input.nextToken()));
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    } else {
                        try {
                            client.grab(choice, firstD);
                        } catch (IOException e) {
                            Printer.err(e);
                        }
                    }
                } else {
                    try {
                        client.grab(choice, firstD);
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            } else {
                try {
                    client.grab(0);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
    }

    private void shoot(StringTokenizer input){
        String victim;
        String weapon;
        if(input.hasMoreTokens()){
            victim = input.nextToken();
            if(input.hasMoreTokens()){
                weapon = input.nextToken();
                weaponEffect(weapon);
            }
        }
    }

    private void weaponEffect(String weapon){
        switch(weapon){
            case "lock rifle":
                break;
            case "machine gun":
                break;
            case "thor":
                break;
            case "plasma gun":
                break;
            case "whisper":
                break;
            case "electroscythe":
                break;
            case "tractor beam":
                break;
            case "vortex cannon":
                break;
            case "furnace":
                break;
            case "heatseeker":
                break;
            case "hellion":
                break;
            case "flamethrower":
                break;
            case "grenade launcher":
                break;
            case "rocket launcher":
                break;
            case "railgun":
                break;
            case "cyberblade":
                break;
            case "zx-2":
                break;
            case "shotgun":
                break;
            case "power glove":
                break;
            case "shockwave":
                break;
            case "sledgehammer":
                break;
            default:
                break;
        }
    }

    private void endTurn(){
        try {
            client.endTurn();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    @Override
    public void notifyLogin(Subject subject, String username){
        switch(subject){
            case WRONG:
                Printer.print("Username already used! Please choose another username:");
                break;
            case RIGHT:
                Printer.println(username + " connected!");
                Printer.print("[CLIENT]Please, insert a command:");
                break;
            case ALL:
                Printer.println(username + " connected!");
                break;
            default:
                break;
        }
    }

    public void notifyColor(Subject subject, TokenColor color){
        switch(subject){
            case WRONG:
                Printer.print("Color already chosen! Please choose another color:");
                break;
            case RIGHT:
                Printer.println("Your color is " + Converter.fromTokenColorToString(color));
                Printer.print("[CLIENT]Please, insert a command:");
                break;
            default:
                break;
        }
    }

    private void notifyEndTurn(Subject subject){
        Printer.println("Your turn is ended!");
    }

    public void notify(Message message, Subject subject){
        switch (message){
            case END_TURN:
                notifyEndTurn(subject);
                break;
            default:
                break;
        }
    }

    public void notify(Message message, Subject subject, Object object){
        switch(message){
            case USERNAME:
                notifyLogin(subject, (String) object);
                break;
            case LOGIN:
                notifyLogin(subject, (String) object);
                break;
            case COLOR:
                notifyColor(subject, (TokenColor) object);
                break;
            default:
                break;
        }
    }

    @Override
    public void printMessage(Advise advise){
        switch(advise){
            case COLOR:
                Printer.print("Please, choose a color:");
                break;
            default:
                break;
        }
    }
}
