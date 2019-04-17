package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Response;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

public class CommandLine implements ViewInterface {
    private ClientInterface client;
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
                    } catch (RemoteException e) {
                        Printer.err(e);
                    }
                }
                break;
            case "color":
                if(string.hasMoreTokens()){
                    try {
                        client.chooseColor(Parser.castStringToTokenColor(string.nextToken()));
                    } catch (RemoteException e) {
                        Printer.err(e);
                    }
                }
                break;
            case "disconnect":
                //method
                break;
            case "show":
                //method
                break;
            case "move":
                //method
                break;
            case "grab":
                //method
                break;
            case "shoot":
                //method
                break;
            default:
                //method
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
    public void notifyLogin(Response response, String username){
        switch(response){
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
