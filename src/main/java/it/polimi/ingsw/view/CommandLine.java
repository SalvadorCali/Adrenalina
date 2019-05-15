package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.CLIController;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
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

    private void readInput(String message) throws RemoteException {
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
        String weapon;
        if(input.hasMoreTokens()){
            weapon = input.nextToken();
            try {
                weaponEffect(weapon);
            } catch (IOException e) {
                Printer.err(e);
            }
        }
    }

    private void weaponEffect(String weapon) throws IOException {
        StringTokenizer string;
        switch(weapon){
            case "lockrifle":
                Printer.println("Please choose your victims: <victim1> <victim2>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.hasMoreTokens()){
                    TokenColor firstVictim = Converter.fromStringToTokenColor(string.nextToken());
                    if(string.hasMoreTokens()){
                        TokenColor secondVictim = Converter.fromStringToTokenColor(string.nextToken());
                        //
                    }else{
                        client.shoot(firstVictim);
                    }
                }
                break;
            case "machine gun":
                Printer.println("Basic effect: <1> <victim>");
                Printer.println("Basic effect: <1> <victim> <victim>");
                Printer.println("With focus shot: <2> <victim>");
                Printer.println("With focus shot: <2> <victim> <victim> <victim>");
                //da terminare
                break;
            case "thor":
                Printer.println("Basic effect: <victim>");
                Printer.println("With chain reaction: <2>");
                Printer.println("With high voltage: <2>");
                break;
            case "plasma gun":
                Printer.println("Basic effect: <1> <victim>");
                Printer.println("With phase glide: <2> <victim> <direction> <direction>");
                Printer.println("With phase glide: <2> <victim> <direction>");
                Printer.println("With phase glide: <2> <direction> <direction> <victim>");
                Printer.println("With phase glide: <2> <direction> <victim>");
                Printer.println("With phase glide: <3> <victim> <direction> <direction>");
                Printer.println("With phase glide: <3> <victim> <direction>");
                Printer.println("With phase glide: <3> <direction> <direction> <victim>");
                Printer.println("With phase glide: <3> <direction> <victim>");
                //da terminare
                break;
            case "whisper":
                Printer.println("Effect: <victim>");
                break;
            case "electroscythe":
                Printer.println("Basic mode: <1>");
                Printer.println("In reaper mode: <2>");
                string = new StringTokenizer(userInputStream.readLine());
                if(string.hasMoreTokens()){
                    int choice = Integer.parseInt(string.nextToken());
                    //
                }
                break;
            case "tractorbeam":
                Printer.println("Basic mode: <victim> <directions...>");
                Printer.println("In reaper mode: <victim>");
                break;
            case "vortex cannon":
                Printer.println("Basic effect: <1> <squareX> <squareY> <victim>");
                Printer.println("With black hole: <2> <squareX> <squareY> <victim> <victim>");
                Printer.println("With black hole: <2> <squareX> <squareY> <victim> <victim> <victim>");
                break;
            case "furnace":
                Printer.println("Basic mode: <1> <squareX> <squareY>");
                Printer.println("In cozy fire mode: <2> <squareX> <squareY>");
                break;
            case "heatseeker":
                Printer.println("Effect: <victim>");
                break;
            case "hellion":
                Printer.println("Basic mode: <1> <victim> <squareX> <squareY>");
                Printer.println("In nano-tracer mode: <2> <victim> <squareX> <squareY>");
                break;
            case "flamethrower":
                Printer.println("Basic mode: <direction> <victim1> <victim2>");
                Printer.println("In barbecue mode: <direction>");
                break;
            case "grenade launcher":
                Printer.println("Basic effect: <1> <victim>");
                Printer.println("Basic effect: <1> <victim> <direction>");
                Printer.println("With extra grenade: <2> <victim> <direction> <squareX> <squareY>");
                Printer.println("With extra grenade: <2> <squareX> <squareY> <victim> <direction>");
                break;
            case "rocket launcher":
                break;
            case "railgun":
                Printer.println("Basic mode: <1> <direction> <victim>");
                Printer.println("In piercing mode: <2> <direction> <victim>");
                Printer.println("In piercing mode: <2> <direction> <victim1> <victim2>");
                break;
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
                break;
            case "shotgun":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("Basic mode: <1> <victim> <direction>");
                Printer.println("In long barrel mode: <2> <victim>");
                break;
            case "power glove":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("In rocket fist mode: <2> <direction>");
                Printer.println("In rocket fist mode: <2> <direction> <victim>");
                //da terminare
                break;
            case "shockwave":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("Basic mode: <1> <victim> <victim>");
                Printer.println("Basic mode: <1> <victim> <victim>");
                Printer.println("In tsunami mode: <2>");
                break;
            case "sledgehammer":
                Printer.println("Basic mode: <1> <victim>");
                Printer.println("In pulverize mode: <2> <victim>");
                Printer.println("In pulverize mode: <2> <victim> <direction>");
                Printer.println("In pulverize mode: <2> <victim> <direction> <direction>");
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

    private void notifyNewTurn(){
        Printer.println("It's your turn!");
        gameBoardPrinter.setGameBoard(playerController.getGameBoard());
        gameBoardPrinter.printMap();
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
        gameBoardPrinter.setGameBoard(playerController.getGameBoard());
        gameBoardPrinter.printMap();
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
            case GAME:
                notifyGame(outcome);
                break;
            case MOVE:
                notifyMovement(outcome);
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
            default:
                break;
        }
    }
}
