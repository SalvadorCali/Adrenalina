package it.polimi.ingsw.view;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

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
                    handle(userInputStream.readLine());
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        });
        receiveMessage.start();
    }

    private void handle(String message){
        StringTokenizer string = new StringTokenizer(message);
        switch(string.nextToken()){
            case "help":
                help();
                break;
            case "login":
                //method
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
        Printer.println("disconnect :");
        Printer.println("show <object> :");
        Printer.println("move <first direction, ..., last direction> :");
    }
}
