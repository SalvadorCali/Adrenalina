package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.controller.PowerupData;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                //int val = objectInputStream.readInt();
                //handle the message
            } catch (IOException e) {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    socket.close();
                    thisThread = null;
                    serverController.disconnect(clientName);
                    Printer.println("disconnnesss");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void readRequest(Message message){
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
            case SQUARE_2:
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
            case SHOOT_1:
                shootA();
                break;
            case SHOOT_2:
                shootB();
                break;
            case SHOOT_3:
                shootC();
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

    public void showSquare(){
        serverController.showSquare(clientName);
    }

    public void showOtherSquare(){
        try {
            int x = objectInputStream.readInt();
            int y = objectInputStream.readInt();
            serverController.showSquare(clientName, x, y);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void move(){
        Direction first, second, third;
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
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    public void shootA(){
        try {
            String weaponName = objectInputStream.readUTF();
            TokenColor victim1, victim2;
            int effectNumber = objectInputStream.readInt();
            int victimSize = objectInputStream.readInt();
            if(victimSize == 1){
                victim1 = (TokenColor) objectInputStream.readObject();
                serverController.shoot(clientName, weaponName, effectNumber, victim1);
            }else{
                victim1 = (TokenColor) objectInputStream.readObject();
                victim2 = (TokenColor) objectInputStream.readObject();
                serverController.shoot(clientName, weaponName, effectNumber, victim1, victim2);
            }
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    public void shootB(){
        try {
            String weaponName = objectInputStream.readUTF();
            int effectNumber = objectInputStream.readInt();
            TokenColor victim = (TokenColor) objectInputStream.readObject();
            int x = objectInputStream.readInt();
            int y = objectInputStream.readInt();
            serverController.shoot(clientName, weaponName, effectNumber, victim, x, y);
        } catch (IOException | ClassNotFoundException e) {
            Printer.err(e);
        }
    }

    public void shootC(){
        try {
            Direction first, second;
            String weaponName = objectInputStream.readUTF();
            TokenColor victim = (TokenColor) objectInputStream.readObject();
            int effectNumber = objectInputStream.readInt();
            int directionsSize = objectInputStream.readInt();
            if(directionsSize == 1){
                first = (Direction) objectInputStream.readObject();
                serverController.shoot(clientName, weaponName,  victim, effectNumber, first);
            }else{
                first = (Direction) objectInputStream.readObject();
                second = (Direction) objectInputStream.readObject();
                serverController.shoot(clientName, weaponName,  victim, effectNumber, first, second);
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

    public void powerupAmmos() {
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

    public void reload(){
        try {
            String weaponName = objectInputStream.readUTF();
            serverController.reload(clientName, weaponName);
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
    public void notify(Message message, Outcome outcome, Object object) throws IOException {
        objectOutputStream.reset();
        objectOutputStream.writeObject(Message.NOTIFY);
        objectOutputStream.flush();
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.writeObject(outcome);
        objectOutputStream.flush();
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
    }
}
