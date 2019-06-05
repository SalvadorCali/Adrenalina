package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Square;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.ViewInterface;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.network.enums.Message.DISCONNECT;
import static it.polimi.ingsw.network.enums.Message.SPAWN;

public class GUIHandler extends Application implements Initializable, ViewInterface{

    @FXML
    private ImageView mapImage;

    @FXML
    private ImageView weaponRed1;

    @FXML
    private ImageView weaponRed2;

    @FXML
    private ImageView weaponRed3;

    @FXML
    private ImageView weaponYellow1;

    @FXML
    private ImageView weaponYellow2;

    @FXML
    private ImageView weaponYellow3;

    @FXML
    private ImageView weaponBlue1;

    @FXML
    private ImageView weaponBlue2;

    @FXML
    private ImageView weaponBlue3;

    @FXML
    private ImageView deckPowerup;

    @FXML
    private ImageView deckWeapon;

    @FXML
    private ImageView deckAmmo;

    @FXML
    private HBox hboxDeath;

    @FXML
    private GridPane grid00;

    @FXML
    private GridPane grid10;

    @FXML
    private GridPane grid20;

    @FXML
    private GridPane grid03;

    @FXML
    private GridPane grid01;

    @FXML
    private GridPane grid11;

    @FXML
    private GridPane grid02;

    @FXML
    private GridPane grid12;

    @FXML
    private GridPane grid21;

    @FXML
    private GridPane grid22;

    @FXML
    private GridPane grid13;

    @FXML
    private GridPane grid23;

    @FXML
    private ImageView bannerShoot;

    @FXML
    private ImageView bannerGrab;

    @FXML
    private ImageView bannerEndTurn;

    @FXML
    private ImageView upArrow;

    @FXML
    private ImageView rightArrow;

    @FXML
    private ImageView downArrow;

    @FXML
    private ImageView leftArrow;

    @FXML
    private ImageView bannerShowWeapon;

    @FXML
    private ImageView bannerShowDamage1;

    @FXML
    private Label playerTurnLabel;

    @FXML
    private Label labelStatusPlayer;

    @FXML private Label labelDisconnect;
    @FXML private ImageView bannerDisconnect;

    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final int NUM_SQUARES=12;
    private static final int MAX_NUM_PLAYER=5;
    private static final int MAX_MOVEMENT = 3;

    private GameController gameController;
    private String currentPlayer;
    private BoardType boardType;
    private Square[][] arena = new Square[ROWS][COLUMNS];
    private Stage scene;
    private boolean disconnected = false;
    private ChoosePowerup choosePowerup = new ChoosePowerup();
    private Popup popup = new Popup();
    private String movement[] = new String[MAX_MOVEMENT];
    private Integer countMove = 0;
    private PlayerController playerController;
    private ClientInterface client;
    private ChooseBoard chooseBoard = new ChooseBoard();
    private LoginGUI loginGUI;
    public boolean connected = false;

    //starting methods

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoginGUI.fxml"));
        Parent root = loader.load();

        this.loginGUI = loader.getController();

        Scene scene = new Scene(root, 600, 470);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    //viewInterface methods
    @Override
    public void start() {

    }

    @Override
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void notify(Message message) {
        switch (message){
            case NEW_TURN:
                //notifyNewTurn();
                break;
            case END_TURN:
                //notifyEndTurn();
                break;
            default:
                break;
        }
    }

    @Override
    public void notify(Message message, Outcome outcome) {
        Platform.runLater(() -> {
            switch (message) {
                case BOARD:
                    notifyBoard(outcome);
                    break;
                case GAME:
                    try {
                        notifyGame(outcome);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MOVE:
                    //notifyMovement(outcome);
                    break;
                case GRAB:
                    //notifyGrab(outcome);
                    break;
                case SHOOT:
                    //notifyShoot(outcome);
                    break;
                case POWERUP:
                    //notifyPowerup(outcome);
                    break;
                default:
                    break;
            }
        });
    }

    private void notifyBoard(Outcome outcome){
        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                try {
                    FXMLLoader popupBoard = new FXMLLoader(getClass().getClassLoader().getResource("ChooseBoard.fxml"));
                    Parent pop = popupBoard.load();


                    Stage stage = new Stage();
                    stage.setScene(new Scene(pop, 600, 474));
                    stage.setTitle("Choose Board");
                    stage.show();

                    setBoard();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                try {
                    FXMLLoader popupBoard = new FXMLLoader(getClass().getClassLoader().getResource("Boardwaiting.fxml"));
                    Parent pop = popupBoard.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(pop, 234, 153));
                    stage.setTitle("Board choosing...");
                    stage.show();

                    PauseTransition delay = new PauseTransition(Duration.seconds(5));
                    delay.setOnFinished( event -> stage.close() );
                    delay.play();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void notifyGame(Outcome outcome) throws Exception {
        Platform.runLater(() -> {
            switch (outcome) {
                case ALL:

                    Parent adrenaline = null;
                    try {
                        adrenaline = FXMLLoader.load(getClass().getClassLoader().getResource("MapGUI.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    PauseTransition delay = new PauseTransition(Duration.seconds(5));
                    delay.setOnFinished( event -> {
                        setMap(Data.getInstance().getBoardType(), Data.getInstance().getSkull());

                    });
                    delay.play();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(adrenaline, 1189, 710));
                    stage.setTitle("Adrenaline's Board");
                    stage.show();
                    break;
                default:
                    break;
            }
        });
    }





    @Override
    public void notify(Message message, Outcome outcome, Object object) {
        Platform.runLater(() ->{
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
        });
    }

    public synchronized void connectToServer(){
        Platform.runLater(() -> {
            String connection = Data.getInstance().getConnectionMethod();

            if (connection == "rmi") {
                connectToRMI();
            } else if (connection == "socket") {
                connectToSocket();
            }
        });
    }

    private void connectToRMI() {
        Platform.runLater(() -> {
            String name = Data.getInstance().getNamePlayer();
            String host = Data.getInstance().getHost();
            String color = Data.getInstance().getColorPlayer();

            try {
                client = new RMIClient(host);
                client.setView(this);
                client.login(name, Converter.fromStringToTokenColor(color));

            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void connectToSocket() {
        Platform.runLater(() -> {
            String name = Data.getInstance().getNamePlayer();
            String host = Data.getInstance().getHost();
            String color = Data.getInstance().getColorPlayer();

            try {
                client = new SocketClient(host);
                client.setView(this);
                client.login(name, Converter.fromStringToTokenColor(color));


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void notifyLogin(Outcome outcome, String object) {
        Platform.runLater(() -> {
            switch (outcome) {
                case WRONG:{
                    this.loginGUI.setErrorText("Username already used");
                }
                case RIGHT: {
                    this.loginGUI.setConnectionText(object + " connected, waiting for other players");
                }
                case ALL:{
                    this.loginGUI.setConnectionText(object + " connected, waiting...");
                }
            }
        });
    }


    private void notifyColor(Outcome outcome, TokenColor object) {
        Platform.runLater(() -> {
            switch (outcome) {
                case WRONG:
                    loginGUI.setErrorText("Invalid color");
            }
        });
    }

    private void notifyDisconnection(Outcome outcome, String object) {
        Platform.runLater(() -> {
            switch (outcome) {
                case ALL:{
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Popup.fxml"));
                        Parent root = loader.load();

                        popup = loader.getController();
                        popup.showPopup1(object);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 490, 386));
                        stage.setTitle("Disconnection Popup");
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    break;
            }
        });
    }


    private void notifySpawnLocation(List<Card> object) {
        Platform.runLater(()-> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChoosePowerup.fxml"));
                Parent root = loader.load();

                choosePowerup = loader.getController();
                choosePowerup.launchChoosePowerup(object);

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 490, 386));
                stage.setTitle("Choose Powerup");
                stage.show();

                PauseTransition delay = new PauseTransition(Duration.seconds(10));
                delay.setOnFinished( event -> {
                    try {
                        setPowerup();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                delay.play();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{

            placePlayers();
            //move();
        });
    }


    public void setBoard() throws IOException {

        client.board(Data.getInstance().getBoardType(), Data.getInstance().getSkull());
    }

    public void setPowerup() throws IOException{

        client.choose(Data.getInstance().getPowerup());
    }

    /*
    private void closeThis(){
        Stage stage = (Stage) labelDisconnect.getScene().getWindow();
        stage.close();
    }*/

    public void setMap(Integer board, Integer skulls){
        Platform.runLater(() -> {
            System.out.println(board);
            mapImage.setImage(new Image("boardImg/" + board + ".png"));
        });
    }

    public void move(){
        Platform.runLater(() -> {

            upArrow.setOnMouseClicked(event -> {
                saveMovement("up");
            });

            downArrow.setOnMouseClicked(event -> {
                saveMovement("down");
            });

            leftArrow.setOnMouseClicked(event -> {
                saveMovement("left");
            });

            rightArrow.setOnMouseClicked(event -> {
                saveMovement("right");
            });

        });
    }

    public void saveMovement(String move){
        if(countMove < MAX_MOVEMENT - 1) {

            this.movement[countMove] = move;
            countMove++;
            System.out.println(movement[countMove]);
        }else{
            this.movement[countMove] = move;
            moveClient(this.movement);
            Printer.print("ended moves");
        }
    }

    private void moveClient(String[] movement) {
        try {
            client.move(Converter.fromStringToDirection(this.movement[0]),Converter.fromStringToDirection(this.movement[1]), Converter.fromStringToDirection(this.movement[2]));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void placePlayers(){
        Platform.runLater(() -> {

        arena = playerController.getGameBoard().getArena();

        if(!arena[0][0].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][0].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][0].getPlayers().get(index).getColor())+ ".jpg");
                grid00.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[0][1].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][1].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][1].getPlayers().get(index).getColor())+ ".jpg");
                grid01.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[0][2].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][2].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][2].getPlayers().get(index).getColor())+ ".jpg");
                grid02.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[0][3].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][3].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][3].getPlayers().get(index).getColor())+ ".jpg");
                grid03.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[1][0].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][0].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][0].getPlayers().get(index).getColor())+ ".jpg");
                grid10.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[1][1].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][1].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][1].getPlayers().get(index).getColor())+ ".jpg");
                grid11.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[1][2].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][2].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][2].getPlayers().get(index).getColor())+ ".jpg");
                grid12.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[1][3].getPlayers().isEmpty()){
            for(int index = 0; index < arena[1][3].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][3].getPlayers().get(index).getColor())+ ".jpg");
                grid13.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[2][0].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][0].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][0].getPlayers().get(index).getColor())+ ".jpg");
                grid20.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[2][1].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][1].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][1].getPlayers().get(index).getColor())+ ".jpg");
                grid21.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[2][2].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][2].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][2].getPlayers().get(index).getColor())+ ".jpg");
                grid22.getChildren().add(index, new ImageView(image));
            }
        }

        if(!arena[2][3].getPlayers().isEmpty()){
            for(int index = 0; index < arena[2][3].getPlayers().size(); index ++){

                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][3].getPlayers().get(index).getColor())+ ".jpg");
                grid23.getChildren().add(index, new ImageView(image));
            }
        }
        });
    }


}
