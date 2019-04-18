package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;
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

    private void readInput(String message){
        StringTokenizer string = new StringTokenizer(message);
        switch(string.nextToken()){
            case "help":
                help();
                break;
            case "login":
                if(string.hasMoreTokens()){
                    try {
                        client.login(string.nextToken());
                        break;
                    } catch (RemoteException e) {
                        Printer.err(e);
                    }
                }
                Printer.println("Invalid Command. Please insert a command.");
                break;
            case "color":
                if(string.hasMoreTokens()){
                    try {
                        client.chooseColor(Parser.castStringToTokenColor(string.nextToken()));
                        break;
                    } catch (RemoteException e) {
                        Printer.err(e);
                    }
                }
                Printer.println("Invalid Command. Please insert a command.");
                break;
            case "disconnect":
                //method
                break;
            case "show":
                if(string.hasMoreTokens()){
                    String next = string.nextToken();
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
                        default:
                            break;
                    }
                }
                Printer.println("Invalid Command. Please insert a command.");
                break;
            case "move":
                Direction first, second, third;
                if(string.hasMoreTokens()){
                    first = Converter.fromStringToDirection(string.nextToken());
                    if(string.hasMoreTokens()){
                        second = Converter.fromStringToDirection(string.nextToken());
                        if(string.hasMoreTokens()){
                            third = Converter.fromStringToDirection(string.nextToken());
                            //client.move(first, second, third);
                            break;
                        }else{
                            //client.move(first, second);
                            break;
                        }
                    }else{
                        //client.move(first);
                        break;
                    }
                }
                Printer.println("Invalid Command. Please insert a command.");
                break;
            case "grab":
                if(string.hasMoreTokens()){
                    String next = string.nextToken();
                    if(next.equals("weapon")){
                        //client.grabWeapon();
                        break;
                    }else{
                        //client.grabAmmo();
                        break;
                    }
                }
                Printer.println("Invalid Command. Please insert a command.");
                break;
            case "shoot":
                //method
                break;
            default:
                Printer.println("Invalid Command. Please insert a command.");
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

    public void notify(Message message, Subject subject, Object object){
        switch(message){
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
