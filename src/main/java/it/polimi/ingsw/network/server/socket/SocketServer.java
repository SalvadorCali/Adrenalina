package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.controller.GameData;
import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * The Server for the socket connection.
 */
public class SocketServer implements Runnable, ServerInterface {
    /**
     * A Thread object that represents this thread.
     */
    private Thread thisThread;
    /**
     * The socket for the connection.
     */
    private Socket socket;
    /**
     * A timer to handle the Client connection.
     */
    private ConnectionTimer connectionTimer;
    /**
     * The input stream.
     */
    private ObjectInputStream objectInputStream;
    /**
     * The output stream.
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * The ServerController that handles the game.
     */
    private ServerController serverController;
    /**
     * The username of the relative Client.
     */
    private String clientName;

    /**
     * Class constructor.
     * @param socket the relative socket.
     * @param serverController the ServerController of the game.
     * @throws IOException caused by the streams.
     */
    public SocketServer(Socket socket, ServerController serverController) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.serverController = serverController;
        thisThread = new Thread(this);
        thisThread.start();
    }

    /**
     * Waits for messages and then handle them, with the {@link #readRequest(Message)} method.
     */
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (thisThread == currentThread) {
            try {
                Message message = (Message) objectInputStream.readObject();
                readRequest(message);
            } catch (IOException e) {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    thisThread = null;
                    serverController.disconnect(clientName);
                } catch (IOException e1) {
                    Printer.err(e1);
                }
            } catch (ClassNotFoundException e) {
                Printer.err(e);
            }
        }
    }

    /**
     * Setter for the ServerController.
     * @param serverController the ServerController that will be set.
     * @throws RemoteException caused by the remote method.
     */
    @Override
    public void setServerController(ServerController serverController) throws RemoteException {
        this.serverController = serverController;
    }

    /**
     * Reads the message sent by the Client and the calls the relative method to handle it.
     * @param message the message sent by the Client.
     */
    private void readRequest(Message message){
        switch (message){
            case LOGIN:
                login();
                break;
            case DISCONNECT:
                disconnect();
                break;
            case BOARD:
                board();
                break;
            case SPAWN:
                choose();
                break;
            case SQUARE:
                showSquare();
                break;
            case SQUARE_XY:
                showOtherSquare();
                break;
            case USERNAME:
                break;
            case MOVE:
                move();
                break;
            case GRAB:
                grab();
                break;
            case POWERUP:
                powerup();
                break;
            case RELOAD:
                reload();
                break;
            case END_TURN:
                endTurn();
                break;
            case SHOOT:
                shoot();
                break;
            case DROP_POWERUP:
                dropPowerup();
                break;
            case DROP_WEAPON:
                dropWeapon();
                break;
            case DISCARD_POWERUP:
                discardPowerup();
                break;
            case MOVE_RELOAD_1:
                moveAndReloadOneDirection();
                break;
            case MOVE_RELOAD_2:
                moveAndReloadTwoDirections();
                break;
            case RESPAWN:
                respawn();
                break;
            default:
                break;
        }
    }

    /**
     * Saves the username and the color of the Client and sends them to the ServerController.
     */
    public void login(){
        TokenColor color = TokenColor.NONE;
        try{
            clientName = objectInputStream.readUTF();
            color = (TokenColor) objectInputStream.readObject();
            //connectionTimer = (ConnectionTimer) objectInputStream.readObject();
            //connectionTimer.setServer(this);
            //connectionTimer.start();
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
        serverController.login(clientName, color, this);
    }

    /**
     * Calls the relative ServerController's method to disconnect the Client.
     */
    public void disconnect(){
        serverController.disconnect(clientName);
        try {
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Saves the board and the number of the skulls and sends them to the ServerController.
     */
    public void board(){
        try {
            int boardType = objectInputStream.readInt();
            int skulls = objectInputStream.readInt();
            serverController.chooseBoardType(boardType, skulls);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Saves the powerup chosen by the Client and sends it to the ServerController.
     */
    public void choose(){
        try {
            int choice = objectInputStream.readInt();
            serverController.choose(clientName, choice);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Calls the relative ServerController's method to show the square to the Client.
     */
    private void showSquare(){
        serverController.showSquare(clientName);
    }

    /**
     * Saves the square's coordinates and sends them to the ServerController to show to the Client a square.
     */
    private void showOtherSquare(){
        try {
            int x = objectInputStream.readInt();
            int y = objectInputStream.readInt();
            serverController.showSquare(clientName, x, y);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Saves the directions where the Client wants to move and then sends them to the ServerController.
     */
    public void move(){
        Direction first;
        Direction second;
        Direction third;
        Direction fourth;
        try {
            int directionsSize = objectInputStream.readInt();
            if(directionsSize == 1){
                first = (Direction) objectInputStream.readObject();
                serverController.move(clientName, first);
            }else if(directionsSize == 2){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                serverController.move(clientName, first, second);
            }else if(directionsSize == 3){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                third = (Direction) objectInputStream.readObject();
                serverController.move(clientName, first, second, third);
            }else if(directionsSize == 4){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                third = (Direction) objectInputStream.readObject();
                fourth = (Direction) objectInputStream.readObject();
                serverController.move(clientName, first, second, third, fourth);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    /**
     * Saves the object to grab and the directions for the grab action and sends them to the ServerController.
     */
    public void grab(){
        int choice = 0;
        Direction first;
        Direction second;
        try {
            choice = objectInputStream.readInt();
            int directionsSize = objectInputStream.readInt();
            if(directionsSize == 0){
                serverController.grab(clientName, choice);
            }else if(directionsSize == 1){
                first = (Direction) objectInputStream.readObject();
                Printer.println(first);
                serverController.grab(clientName, choice, first);
            }else{
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                serverController.grab(clientName, choice, first, second);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the powerup to drop and sends it to the ServerController.
     */
    private void dropPowerup(){
        try {
            int powerup = objectInputStream.readInt();
            serverController.dropPowerup(clientName, powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the weapon to drop and sends it to the ServerController.
     */
    private void dropWeapon(){
        try {
            int weapon = objectInputStream.readInt();
            serverController.dropWeapon(clientName, weapon);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the powerup to discard and use as ammo and sends it to the ServerController.
     */
    private void discardPowerup(){
        try {
            int powerup = objectInputStream.readInt();
            serverController.discardPowerup(clientName, powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the parameters to shoot and sends them to the ServerController.
     */
    public void shoot(){
        try {
            Direction first, second, third, fourth;
            String weaponName = objectInputStream.readUTF();
            Printer.println(weaponName);
            int effectNumber = objectInputStream.readInt();
            Printer.println(effectNumber);
            boolean basicFirst = objectInputStream.readBoolean();
            Printer.println(basicFirst);
            TokenColor firstVictim = (TokenColor) objectInputStream.readObject();
            Printer.println(firstVictim);
            TokenColor secondVictim = (TokenColor) objectInputStream.readObject();
            Printer.println(secondVictim);
            TokenColor thirdVictim = (TokenColor) objectInputStream.readObject();
            Printer.println(thirdVictim);
            int x = objectInputStream.readInt();
            Printer.println(x);
            int y = objectInputStream.readInt();
            Printer.println(y);
            int directionsLength = objectInputStream.readInt();
            Printer.println(directionsLength);
            if(directionsLength == 1){
                first = (Direction) objectInputStream.readObject();
                serverController.shoot(weaponName, effectNumber, basicFirst, clientName, firstVictim, secondVictim, thirdVictim, x, y, first);
            }else if(directionsLength == 2){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                serverController.shoot(weaponName, effectNumber, basicFirst, clientName, firstVictim, secondVictim, thirdVictim, x, y, first, second);
            }else if(directionsLength == 3){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                third = (Direction) objectInputStream.readObject();
                serverController.shoot(weaponName, effectNumber, basicFirst, clientName, firstVictim, secondVictim, thirdVictim, x, y, first, second, third);
            }else if(directionsLength == 4){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                third = (Direction) objectInputStream.readObject();
                fourth = (Direction) objectInputStream.readObject();
                serverController.shoot(weaponName, effectNumber, basicFirst, clientName, firstVictim, secondVictim, thirdVictim, x, y, first, second, third, fourth);
            }else{
                serverController.shoot(weaponName, effectNumber, basicFirst, clientName, firstVictim, secondVictim, thirdVictim, x, y);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes a direction and up to three strings and sends them to the ServerController for the move and reload action.
     */
    private void moveAndReloadOneDirection(){
        try {
            String firstWeapon;
            String secondWeapon;
            String thirdWeapon;
            Direction firstDirection = (Direction) objectInputStream.readObject();
            int weaponsSize = objectInputStream.readInt();
            switch (weaponsSize){
                case 0:
                    serverController.moveAndReload(clientName, firstDirection);
                    break;
                case 1:
                    firstWeapon = objectInputStream.readUTF();
                    serverController.moveAndReload(clientName, firstDirection, firstWeapon);
                    break;
                case 2:
                    firstWeapon = objectInputStream.readUTF();
                    secondWeapon = objectInputStream.readUTF();
                    serverController.moveAndReload(clientName, firstDirection, firstWeapon, secondWeapon);
                    break;
                case 3:
                    firstWeapon = objectInputStream.readUTF();
                    secondWeapon = objectInputStream.readUTF();
                    thirdWeapon = objectInputStream.readUTF();
                    serverController.moveAndReload(clientName, firstDirection, firstWeapon, secondWeapon, thirdWeapon);
                    break;
                default:
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes two directions and up to three strings and sends them to the ServerController for the move and reload action.
     */
    private void moveAndReloadTwoDirections(){
        try {
            String firstWeapon;
            String secondWeapon;
            String thirdWeapon;
            Direction firstDirection = (Direction) objectInputStream.readObject();
            Direction secondDirection = (Direction) objectInputStream.readObject();
            int weaponsSize = objectInputStream.readInt();
            switch (weaponsSize){
                case 0:
                    serverController.moveAndReload(clientName, firstDirection, secondDirection);
                    break;
                case 1:
                    firstWeapon = objectInputStream.readUTF();
                    serverController.moveAndReload(clientName, firstDirection, secondDirection, firstWeapon);
                    break;
                case 2:
                    firstWeapon = objectInputStream.readUTF();
                    secondWeapon = objectInputStream.readUTF();
                    serverController.moveAndReload(clientName, firstDirection, secondDirection, firstWeapon, secondWeapon);
                    break;
                case 3:
                    firstWeapon = objectInputStream.readUTF();
                    secondWeapon = objectInputStream.readUTF();
                    thirdWeapon = objectInputStream.readUTF();
                    serverController.moveAndReload(clientName, firstDirection, secondDirection, firstWeapon, secondWeapon, thirdWeapon);
                    break;
                default:
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the parameters to use powerups during the game and sends them to the ServerController.
     */
    public void powerup(){
        try {
            Direction first, second;
            String powerup = objectInputStream.readUTF();
            TokenColor victim = (TokenColor) objectInputStream.readObject();
            Color ammo = (Color) objectInputStream.readObject();
            int x = objectInputStream.readInt();
            int y = objectInputStream.readInt();
            int directionsSize = objectInputStream.readInt();
            if(directionsSize == 1){
                first = (Direction) objectInputStream.readObject();
                serverController.powerup(clientName, powerup, victim, ammo, x, y, first);
            }else if(directionsSize == 2){
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                serverController.powerup(clientName, powerup, victim, ammo, x, y, first, second);
            }else{
                serverController.powerup(clientName, powerup, victim, ammo, x, y);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the name of the weapon to reload and sends it to the ServerController.
     */
    public void reload(){
        try {
            String weaponName = objectInputStream.readUTF();
            serverController.reload(clientName, weaponName);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Takes the powerup chosen by the user for the respawn and sends it to the ServerController.
     */
    public void respawn(){
        try {
            int powerup = objectInputStream.readInt();
            serverController.respawn(clientName, powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Calls the relative ServerController method to end the turn.
     */
    public void endTurn(){
        serverController.endTurn(clientName);
    }

    /**
     * Writes a message to the Client to notify it an action.
     * @param message the message sent by the ServerController.
     * @throws IOException caused by the streams.
     */
    @Override
    public void notify(Message message) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    /**
     * Writes a message and an outcome to the Client to notify it an action.
     * @param message the message sent by the ServerController.
     * @param outcome the outcome of the action.
     * @throws IOException caused by the streams.
     */
    @Override
    public void notify(Message message, Outcome outcome) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.writeObject(outcome);
        objectOutputStream.flush();
    }

    /**
     * Writes a message, an outcome and a GameData object to the Client to notify it an action.
     * @param message the message sent by the ServerController.
     * @param outcome the outcome of the action.
     * @param gameData datas of the game.
     * @throws IOException caused by the streams.
     */
    @Override
    public void notify(Message message, Outcome outcome, GameData gameData) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.writeObject(outcome);
        objectOutputStream.flush();
        objectOutputStream.writeObject(gameData);
        objectOutputStream.flush();
    }
}
