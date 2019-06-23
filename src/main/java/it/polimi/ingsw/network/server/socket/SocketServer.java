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

public class SocketServer implements Runnable, ServerInterface {
    private Thread thisThread;
    private Socket socket;
    private ConnectionTimer connectionTimer;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ServerController serverController;
    private String clientName;

    public SocketServer(Socket socket, ServerController serverController) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        this.serverController = serverController;
        thisThread = new Thread(this);
        thisThread.start();
    }

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

    @Override
    public void setServerController(ServerController serverController) throws RemoteException {
        this.serverController = serverController;
    }

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
            case POWERUP_AMMOS:
                powerupAmmos();
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
            case DROP:
                drop();
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

    public void board(){
        try {
            int boardType = objectInputStream.readInt();
            int skulls = objectInputStream.readInt();
            serverController.chooseBoardType(boardType, skulls);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void choose(){
        try {
            int choice = objectInputStream.readInt();
            serverController.choose(clientName, choice);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    private void showSquare(){
        serverController.showSquare(clientName);
    }

    private void showOtherSquare(){
        try {
            int x = objectInputStream.readInt();
            int y = objectInputStream.readInt();
            serverController.showSquare(clientName, x, y);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void move(){
        Direction first, second, third, fourth;
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

    public void grab(){
        int choice = 0;
        Direction first, second;
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

    public void drop(){
        try {
            String weaponName = objectInputStream.readUTF();
            serverController.drop(clientName, weaponName);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    private void dropPowerup(){
        try {
            int powerup = objectInputStream.readInt();
            serverController.dropPowerup(clientName, powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    private void dropWeapon(){
        try {
            int weapon = objectInputStream.readInt();
            serverController.dropWeapon(clientName, weapon);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    private void discardPowerup(){
        try {
            int powerup = objectInputStream.readInt();
            serverController.discardPowerup(clientName, powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

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

    private void moveAndReloadOneDirection(){
        try {
            String firstWeapon, secondWeapon, thirdWeapon;
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

    private void moveAndReloadTwoDirections(){
        try {
            String firstWeapon, secondWeapon, thirdWeapon;
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

    public void powerupAmmos(int i) {
        PowerupData first, second;
        try {
            int powerupsSize = objectInputStream.readInt();
            if (powerupsSize == 1) {
                first = (PowerupData) objectInputStream.readObject();
                serverController.powerupAmmos(clientName, first);
            } else if (powerupsSize == 2) {
                first = (PowerupData) objectInputStream.readObject();
                second = (PowerupData) objectInputStream.readObject();
                serverController.powerupAmmos(clientName, first, second);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }

    }

    private void powerupAmmos(){
        int firstPowerup, secondPowerup;
        try {
            int powerupSize = objectInputStream.readInt();
            switch(powerupSize){
                case 1:
                    firstPowerup = objectInputStream.readInt();
                    serverController.powerupAmmos(clientName, firstPowerup);
                    break;
                case 2:
                    firstPowerup = objectInputStream.readInt();
                    secondPowerup = objectInputStream.readInt();
                    serverController.powerupAmmos(clientName, firstPowerup, secondPowerup);
                    break;
            }
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void reload(){
        try {
            String weaponName = objectInputStream.readUTF();
            serverController.reload(clientName, weaponName);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void respawn(){
        try {
            int powerup = objectInputStream.readInt();
            serverController.respawn(clientName, powerup);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void endTurn(){
        serverController.endTurn(clientName);
    }

    @Override
    public void notify(Message message) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

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
