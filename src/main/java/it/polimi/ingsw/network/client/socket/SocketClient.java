package it.polimi.ingsw.network.client.socket;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.controller.SquareData;
import it.polimi.ingsw.controller.timer.ConnectionTimer;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.AdrenalineZone;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.GameBoard;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.CommandLine;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.view.MapCLI;
import it.polimi.ingsw.view.ViewInterface;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class SocketClient implements ClientInterface, Runnable, Serializable {
    private PlayerController playerController;
    private Thread thisThread;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ConnectionTimer connectionTimer;
    private ViewInterface view;
    private Socket clientSocket;
    private String username;

    public SocketClient() throws IOException {
        playerController = new PlayerController(this);
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        Printer.print("[CLIENT]Please, set an ip address:");
        String host = userInputStream.readLine();
        clientSocket = new Socket(host, Config.SOCKET_PORT);

        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        view = new CommandLine(this);
        view.start();
        view.setPlayerController(playerController);
    }

    public void start() {
        thisThread = new Thread(this);
        thisThread.start();
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (this.thisThread == currentThread){
            try {
                Message message = (Message) objectInputStream.readObject();
                readRequest(message);
            } catch (IOException | ClassNotFoundException e) {
                try {
                    objectInputStream.close();
                    objectOutputStream.close();
                    clientSocket.close();
                    thisThread = null;
                    Printer.println("Server disconnesso");
                    //handle disconnection
                } catch (IOException e1) {
                    Printer.err(e1);
                }
            }
        }
    }

    public void readRequest(Message message){
        switch(message){
            case NOTIFY:
                try {
                    notifyMessage();
                } catch (IOException | ClassNotFoundException e) {
                    Printer.err(e);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void login(String username, TokenColor color){
        try {
            connectionTimer = new ConnectionTimer(this);
            objectOutputStream.writeObject(Message.LOGIN);
            objectOutputStream.flush();
            objectOutputStream.writeUTF(username);
            objectOutputStream.flush();
            objectOutputStream.writeObject(color);
            objectOutputStream.flush();
            //objectOutputStream.writeObject(connectionTimer);
            //objectOutputStream.flush();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    @Override
    public void disconnect(){
        try {
            objectOutputStream.writeObject(Message.DISCONNECT);
            objectOutputStream.flush();
            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    @Override
    public void choose(int choice) throws IOException {
        objectOutputStream.writeObject(Message.SPAWN);
        objectOutputStream.flush();
        objectOutputStream.writeInt(choice);
        objectOutputStream.flush();
    }

    @Override
    public void showSquare() throws IOException{
        objectOutputStream.writeObject(Message.SQUARE);
        objectOutputStream.flush();
    }

    @Override
    public void move(Direction... directions) throws IOException {
        objectOutputStream.writeObject(Message.MOVE);
        objectOutputStream.flush();
        int directionsSize = directions.length;
        objectOutputStream.writeInt(directionsSize);
        objectOutputStream.flush();
        for (Direction direction : directions) {
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    @Override
    public void grab(int choice, Direction...directions) throws IOException {
        int directionsSize = directions.length;
        objectOutputStream.writeObject(Message.GRAB);
        objectOutputStream.flush();
        objectOutputStream.writeInt(choice);
        objectOutputStream.flush();
        objectOutputStream.writeInt(directionsSize);
        objectOutputStream.flush();
        for (Direction direction : directions) {
            objectOutputStream.writeObject(direction);
            objectOutputStream.flush();
        }
    }

    @Override
    public void shoot(TokenColor color) throws IOException {
        objectOutputStream.writeObject(Message.SHOOT);
        objectOutputStream.flush();
        objectOutputStream.writeObject(color);
        objectOutputStream.flush();
    }

    @Override
    public void powerup(String powerup, int x, int y) throws IOException{
        objectOutputStream.writeObject(Message.POWERUP);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(powerup);
        objectOutputStream.flush();
        objectOutputStream.writeInt(x);
        objectOutputStream.flush();
        objectOutputStream.writeInt(y);
        objectOutputStream.flush();
    }

    @Override
    public void powerup(String powerup, Direction direction, int value) throws IOException{
        objectOutputStream.writeObject(Message.POWERUP);
        objectOutputStream.flush();
        objectOutputStream.writeUTF(powerup);
        objectOutputStream.flush();
        objectOutputStream.writeObject(direction);
        objectOutputStream.flush();
        objectOutputStream.writeInt(value);
        objectOutputStream.flush();
    }

    @Override
    public void endTurn() throws IOException {
        objectOutputStream.writeObject(Message.END_TURN);
        objectOutputStream.flush();
    }

    public void notifyMessage() throws IOException, ClassNotFoundException {
        Message message = (Message) objectInputStream.readObject();
        Outcome outcome;
        Object object;
        switch(message){
            case NEW_TURN:
                outcome = (Outcome) objectInputStream.readObject();
                GameBoard gameBoard = (GameBoard) objectInputStream.readObject();
                playerController.setGameBoard(gameBoard);
                view.notify(message);
                break;
            case END_TURN:
                view.notify(message);
                break;
            case GAME:
                outcome = (Outcome) objectInputStream.readObject();
                object = objectInputStream.readObject();
                playerController.setGameBoard((GameBoard) object);
                view.notify(message, outcome);
                break;
            case LOGIN:
                outcome = (Outcome) objectInputStream.readObject();
                if(outcome.equals(Outcome.RIGHT)){
                    connectionTimer.start();
                }
                object = (String) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case COLOR:
                outcome = (Outcome) objectInputStream.readObject();
                object = (TokenColor) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case PLAYER:
                outcome = (Outcome) objectInputStream.readObject();
                object = objectInputStream.readObject();
                playerController.setPlayer((Player) object);
                break;
            case DISCONNECT:
                outcome = (Outcome) objectInputStream.readObject();
                object = (String) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case SPAWN:
                outcome = (Outcome) objectInputStream.readObject();
                object = (List<Card>) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            case MOVE:
                outcome = (Outcome) objectInputStream.readObject();
                object = (GameBoard) objectInputStream.readObject();
                playerController.setGameBoard((GameBoard) object);
                view.notify(message, outcome);
                break;
            case GRAB:
                outcome = (Outcome) objectInputStream.readObject();
                object = (Player) objectInputStream.readObject();
                playerController.setPlayer((Player) object);
                view.notify(message, outcome);
                break;
            case SQUARE:
                outcome = (Outcome) objectInputStream.readObject();
                object = (SquareData) objectInputStream.readObject();
                view.notify(message, outcome, object);
                break;
            default:
                break;
        }
    }

    @Override
    public void testConnection(){}

    @Override
    public AdrenalineZone getAdrenalineZone(){
        return playerController.getAdrenalineZone();
    }
}
