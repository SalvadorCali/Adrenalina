package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Square;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.MapCLI;
import it.polimi.ingsw.view.ViewInterface;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import java.net.URL;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class GUIHandler extends Application implements ViewInterface, Initializable {

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
    private GridPane gridSkulls;

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
    @FXML private Button enterMove;
    @FXML private ImageView upArrowGrab;
    @FXML private ImageView downArrowGrab;
    @FXML private ImageView rightArrowGrab;
    @FXML private ImageView leftArrowGrab;
    @FXML private Button enterMoveGrab;
    @FXML private ImageView firstWeapon;
    @FXML private ImageView secondWeapon;
    @FXML private ImageView thirdWeapon;
    @FXML private Label labelGrab;


    @FXML
    RadioButton socketButton;

    @FXML
    RadioButton rmiButton;

    @FXML
    TextField nicknameField;

    @FXML
    TextField addressField;

    @FXML
    TextField colorField;

    @FXML
    Label statusConnectionLabel;

    @FXML
    Button loginButton;

    @FXML
    Label connectionErrorLabel;

    @FXML private ImageView firstBoard;
    @FXML private ImageView secondBoard;
    @FXML private ImageView thirdBoard;
    @FXML private ImageView fourthBoard;
    @FXML private Label labelErrorSkull;
    @FXML private TextField skullText;
    @FXML private Button enterButton;
    @FXML private ImageView powerupImg1;
    @FXML private ImageView powerupImg2;
    @FXML private Label labelEndTurn;

    @FXML private ImageView firstPlayerBoard;
    @FXML private ImageView secondPlayerBoard;
    @FXML private ImageView thirdPlayerBoard;
    @FXML private ImageView fourthPlayerBoard;
    @FXML private ImageView fifthPlayerBoard;
    @FXML private GridPane firstDamageGrid;
    @FXML private GridPane secondDamageGrid;
    @FXML private GridPane thirdDamageGrid;
    @FXML private GridPane fourthDamageGrid;
    @FXML private GridPane fifthDamageGrid;
    @FXML private GridPane ammoBoxGrid;
    @FXML private GridPane ammoReserveGrid;
    @FXML private GridPane firstDeathCounterGrid;
    @FXML private GridPane secondDeathCounterGrid;
    @FXML private GridPane thirdDeathCounterGrid;
    @FXML private GridPane fourthDeathCounterGrid;
    @FXML private GridPane fifthDeathCounterGrid;


    @FXML private ImageView firstWeaponHad;
    @FXML private ImageView secondWeaponHad;
    @FXML private ImageView thirdWeaponHad;
    @FXML private ImageView firstPowerupHad;
    @FXML private ImageView secondPowerupHad;
    @FXML private ImageView thirdPowerupHad;

    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final int NUM_SQUARES=12;
    private static final int MAX_NUM_PLAYER=5;
    private static final int MAX_MOVEMENT = 3;
    private static final double GRID_WIDTH = 25;
    private static final double GRID_HEIGHT = 25;

    private GameController gameController;
    private String currentPlayer;
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
    private boolean checkTurn = true;
    private String playerName;
    private String address;
    private String colorPlayer;
    private boolean isRunning = true;
    private Integer boardType;
    private Integer skull;
    private int startedGame = 0;
    private Integer powerup;
    private GUIHandler guiHandler;
    private Stage primaryStage;

    //starting methods
    //
    //
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoginGUI.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 470);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }


    //login methods
    //
    //
    public synchronized void clickButton(String playerName, String address, String colorPlayer){

        if(rmiButton.isSelected()){

            connectToRMI(playerName, address, colorPlayer);
        }

        if(socketButton.isSelected()){

            connectToSocket(playerName, address, colorPlayer);
        }

        if(!rmiButton.isSelected() && !socketButton.isSelected()){
            connectionErrorLabel.setText("Choose connection method");
        }
    }

    public synchronized void tryConnection(){

        playerName = nicknameField.getText();
        address = addressField.getText();
        colorPlayer = colorField.getText();

        if(playerName.equals("")){
            connectionErrorLabel.setText("Userfield can't be empty");
        }

        else if(address.equals("")){
            connectionErrorLabel.setText("Ip can't be empty");
        }

        else if(colorPlayer.equals("")){
            connectionErrorLabel.setText("Color can't be empty");
        }

        else{
            clickButton(playerName, address, colorPlayer);
        }

    }


    public void setConnectionText(String text){
        Platform.runLater(() ->{
            statusConnectionLabel.setText(text);
        });
    }

    public void setErrorText(String text){
        Platform.runLater(() -> {
            connectionErrorLabel.setText(text);
        });
    }




    //chooseBoard methods
    //
    //
    public void chooseBoard0(){
        Platform.runLater(() ->{

            this.boardType = 0;
            Data.getInstance().setBoardType(0);

        });
    }

    public void chooseBoard1(){
        Platform.runLater(() ->{

            this.boardType = 1;
            Data.getInstance().setBoardType(1);

        });
    }

    public void chooseBoard2(){
        Platform.runLater(() ->{
            this.boardType = 2;
            Data.getInstance().setBoardType(2);

        });
    }

    public void chooseBoard3(){
        Platform.runLater(() ->{
            this.boardType = 3;
            Data.getInstance().setBoardType(3);
        });
    }


    public void chooseBoardButton(){

        Platform.runLater(() -> {

            skull = Integer.valueOf(skullText.getText());
            Data.getInstance().setSkull(skull);
            System.out.println(Data.getInstance().getSkull());

            if (boardType == null) {

                labelErrorSkull.setText("Board not chosen");

            } else if (skull > 8 || skull < 5) {

                labelErrorSkull.setText("Wrong skull number");

            } else {
                try {
                    setBoard();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) enterButton.getScene().getWindow();
                stage.close();
            }
        });
    }



    //choosePowerup methods
    //
    //
    public void choosePowerup1() throws IOException {
        Platform.runLater(() ->{
            Data.getInstance().setPowerup(1);
            handleCloseAction1();
            try {
                setPowerup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void choosePowerup2() throws IOException {
        Platform.runLater(() ->{
            Data.getInstance().setPowerup(2);
            handleCloseAction2();
            try {
                setPowerup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void handleCloseAction1() {
        Stage stage = (Stage) powerupImg1.getScene().getWindow();
        stage.close();
    }

    public void handleCloseAction2() {
        Stage stage = (Stage) powerupImg2.getScene().getWindow();
        stage.close();
    }


    //viewInterface methods
    //
    //

    @Override
    public void start() {

    }

    @Override
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void notify(Message message) {
        Platform.runLater(() ->{
            switch (message){
                case END_TURN:
                    notifyEndTurn();
                    break;
                default:
                    break;
            }
        });
    }

    private void notifyEndTurn() {
        Platform.runLater(() ->{

            playerController = Data.getInstance().getPlayerController();
            Printer.print(playerController.getCurrentPlayer() + " new turn");
            setLabelStatement(playerController.getCurrentPlayer() + " new turn");
        });
    }

    @Override
    public void notify(Message message, Outcome outcome) {
        Platform.runLater(() -> {
            switch (message) {
                case NEW_TURN:
                    try {
                        notifyNewTurn(outcome);
                        } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case BOARD:
                    notifyBoard(outcome);
                    break;
                case GAME:
                    Printer.println("New Game");
                    break;
                case MOVE:
                    notifyMovement(outcome);
                    break;
                case GRAB:
                    notifyGrab(outcome);
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

    private void notifyGrab(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();
        if(outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)){
            guiHandler.setLabelStatement("Grabbed");

        }else{
            guiHandler.setLabelStatement("Not grabbed");
        }
    }

    private void notifyMovement(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();
        if (outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)) {
            guiHandler.setLabelStatement("Player moved");

        } else {
            guiHandler.setLabelStatement("Player didn't move");
            guiHandler.resetMovement();
        }
    }

    private void setLabelStatement(String move) {
        labelStatusPlayer.setVisible(true);
        labelStatusPlayer.setText(move);
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


    private void notifyNewTurn(Outcome outcome) throws Exception {
        Platform.runLater(() -> {
            if(outcome.equals(Outcome.RIGHT)) {
                if(startedGame == 0) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapGUI.fxml"));

                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    playerController = Data.getInstance().getPlayerController();

                    guiHandler = loader.getController();
                    guiHandler.setMapImage();
                    //guiHandler.setSkulls();

                    Data.getInstance().setGuiHandler(guiHandler);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 1189, 710));
                    stage.setTitle("Adrenaline's Board");
                    stage.show();

                    Thread thread = new Thread(this::checkPosition);
                    thread.setDaemon(true);
                    thread.start();

                    System.out.println(this.startedGame);

                    startedGame++;
                }else{
                    enableButtons();
                }


            }else{
                if(startedGame == 0) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapGUI.fxml"));

                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    playerController = Data.getInstance().getPlayerController();

                    guiHandler = loader.getController();
                    guiHandler.setMapImage();
                    //guiHandler.setSkulls();

                    Data.getInstance().setGuiHandler(guiHandler);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 1189, 710));
                    stage.setTitle("Adrenaline's Board");
                    stage.show();

                    Thread thread = new Thread(this::checkPosition);
                    thread.setDaemon(true);
                    thread.start();


                    System.out.println(this.startedGame);

                    startedGame++;
                }else{
                    disableButtons();
                }
            }
        });
    }

    public void setSkulls() {

        playerController = Data.getInstance().getPlayerController();
        int skulls = playerController.getKillshotTrack().size();

        Image imageSkull = new Image("boardElem/skull.png");

        for(int i = 0; i < skulls; i++) {
            gridSkulls.add(new ImageView(imageSkull), i, 0);
        }
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


    private void connectToRMI(String name, String host, String color) {
        Platform.runLater(() -> {

            try {
                client = new RMIClient(host);
                client.setView(this);
                playerController = client.getPlayerController();
                client.login(name, Converter.fromStringToTokenColor(color));
                Data.getInstance().setClient(client);
                Data.getInstance().setPlayerController(playerController);

            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void connectToSocket(String name, String host, String color) {
        Platform.runLater(() -> {


            try {
                client = new SocketClient(host);
                client.setView(this);
                playerController = client.getPlayerController();
                client.login(name, Converter.fromStringToTokenColor(color));
                Data.getInstance().setClient(client);
                Data.getInstance().setPlayerController(playerController);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void notifyLogin(Outcome outcome, String object) {
        Platform.runLater(() -> {
            switch (outcome) {
                case WRONG:{
                    setErrorText("Username already used");
                }
                case RIGHT: {
                    setConnectionText(object + " connected, waiting for other players");
                }
                case ALL:{
                    setConnectionText(object + " connected, waiting...");
                }
            }
        });
    }


    private void notifyColor(Outcome outcome, TokenColor object) {
        Platform.runLater(() -> {
            switch (outcome) {
                case WRONG:
                    setErrorText("Invalid color");
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

                guiHandler = loader.getController();
                guiHandler.setPowerupImage(object);


                Stage stage = new Stage();
                stage.setScene(new Scene(root, 490, 386));
                stage.setTitle("Choose Powerup");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setPowerupImage(List<Card> powerup) {
        Platform.runLater(() -> {
            for (int i = 0; i < powerup.size(); i++) {

                String color = Converter.fromColorToLetter(powerup.get(i).getColor());
                String name = powerup.get(i).getName();

                Image image = new Image("powerup/" + color + "/" + name + ".png");

                if(i == 0){
                    powerupImg1.setImage(image);
                }else{
                    powerupImg2.setImage(image);
                }
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void checkPosition() {
        while(checkTurn){
            Platform.runLater(() -> {


                guiHandler = Data.getInstance().getGuiHandler();
                playerController = Data.getInstance().getPlayerController();
                guiHandler.placePlayers(playerController.getGameBoard().getArena());
                guiHandler.removeImg();
                guiHandler.addWeapon();
                guiHandler.addAmmo();
            });

            try{

                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void addAmmo() {
        playerController = Data.getInstance().getPlayerController();
        arena = playerController.getGameBoard().getArena();
        for(int i=0; i < ROWS ; i++){
            for(int j=0; j<COLUMNS; j++){
                if(arena[i][j].isActive() && !arena[i][j].isSpawn()){
                    AmmoCard ammoCard = arena[i][j].getAmmoCard();
                    String url = "ammo/" + Converter.fromAmmoCardToString(ammoCard) + ".png";
                    ImageView imageView = new ImageView(url);
                    imageView.setFitHeight(GRID_HEIGHT);
                    imageView.setFitWidth(GRID_WIDTH);

                    if(i == 0 && j == 0){
                        grid00.add(imageView, 1, 2);

                    } else if(i == 0 && j == 1){
                        grid01.add(imageView, 1, 2);

                    }else if(i == 0 && j == 2){
                        grid02.add(imageView, 1, 2);

                    }else if(i == 0 && j == 3){
                        grid03.add(imageView, 1, 2);

                    }else if(i == 1 && j == 0){
                        grid10.add(imageView, 1, 2);

                    }else if(i == 1 && j == 1){
                        grid11.add(imageView, 1, 2);

                    }else if(i == 1 && j == 2){
                        grid12.add(imageView, 1, 2);

                    }else if(i == 1 && j == 3){
                        grid13.add(imageView, 1, 2);

                    }else if(i == 2 && j == 0){
                        grid20.add(imageView, 1, 2);

                    }else if(i == 2 && j == 1){
                        grid21.add(imageView, 1, 2);

                    }else if(i == 2 && j == 2){
                        grid22.add(imageView, 1, 2);

                    }else if(i == 2 && j == 3){
                        grid23.add(imageView, 1, 2);
                    }
                }
            }
        }



    }

    private void addWeapon() {
        playerController = Data.getInstance().getPlayerController();
        arena = playerController.getGameBoard().getArena();

        int k = 0;
        for(int i = 0; i < ROWS ; i++){
            for(int j = 0; j< COLUMNS; j++){
                if(arena[i][j].isSpawn()){
                    if(arena[i][j].getColor().equals(TokenColor.BLUE)){

                        String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                        String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";
                        String url3 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(2).getName()) + ".png";

                        weaponBlue1.setImage(new Image(url1));
                        weaponBlue2.setImage(new Image(url2));
                        weaponBlue3.setImage(new Image(url3));
                    }

                    if(arena[i][j].getColor().equals(TokenColor.RED)){

                        String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                        String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";
                        String url3 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(2).getName()) + ".png";

                        weaponRed1.setImage(new Image(url1));
                        weaponRed2.setImage(new Image(url2));
                        weaponRed3.setImage(new Image(url3));
                    }

                    if(arena[i][j].getColor().equals(TokenColor.YELLOW)){

                        String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                        String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";
                        String url3 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(2).getName()) + ".png";

                        weaponYellow1.setImage(new Image(url1));
                        weaponYellow2.setImage(new Image(url2));
                        weaponYellow3.setImage(new Image(url3));
                    }
                }
            }
        }
    }

    private void removeImg() {

        for(int i = 0; i< grid00.getChildren().size(); i++){
            grid00.getChildren().remove(i);
        }
        for(int i = 0; i< grid01.getChildren().size(); i++){
            grid01.getChildren().remove(i);
        }
        for(int i = 0; i< grid02.getChildren().size(); i++){
            grid02.getChildren().remove(i);
        }
        for(int i = 0; i< grid03.getChildren().size(); i++){
            grid03.getChildren().remove(i);
        }
        for(int i = 0; i< grid10.getChildren().size(); i++){
            grid10.getChildren().remove(i);
        }
        for(int i = 0; i< grid11.getChildren().size(); i++){
            grid11.getChildren().remove(i);
        }
        for(int i = 0; i< grid12.getChildren().size(); i++){
            grid12.getChildren().remove(i);
        }
        for(int i = 0; i< grid13.getChildren().size(); i++){
            grid13.getChildren().remove(i);
        }
        for(int i = 0; i< grid20.getChildren().size(); i++){
            grid20.getChildren().remove(i);
        }
        for(int i = 0; i< grid21.getChildren().size(); i++){
            grid21.getChildren().remove(i);
        }
        for(int i = 0; i< grid22.getChildren().size(); i++){
            grid22.getChildren().remove(i);
        }
        for(int i = 0; i< grid23.getChildren().size(); i++){
            grid23.getChildren().remove(i);
        }
    }

    public void setLabelTurn() {
        Platform.runLater(() -> {

            playerController = Data.getInstance().getPlayerController();
            playerTurnLabel.setText(playerController.getCurrentPlayer());
        });
    }


    public void setBoard() throws IOException, InterruptedException {
        Platform.runLater(() -> {
            try {
                client = Data.getInstance().getClient();
                System.out.println(Data.getInstance().getBoardType());
                client.board(Data.getInstance().getBoardType() + 1, Data.getInstance().getSkull());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void setMapImage() {
        playerController = Data.getInstance().getPlayerController();
        Integer mapNum = Converter.fromBoardTypeToInt(playerController.getGameBoard().getType());
        mapImage.setImage(new Image("boardImg/" + mapNum +".png"));
    }

    public void setPowerup() throws IOException{
        Platform.runLater(() ->{
            try {
                client = Data.getInstance().getClient();
                client.choose(Data.getInstance().getPowerup());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }




    @FXML
    private void moveUp(MouseEvent event) throws IOException {
        saveMovement("up");
    }

    @FXML
    private void moveDown(MouseEvent event) throws IOException {
        saveMovement("down");
    }

    @FXML
    private void moveLeft(MouseEvent event) throws IOException {
        saveMovement("left");
    }

    @FXML
    private void moveRight(MouseEvent event) throws IOException {
        saveMovement("right");
    }

    public void saveMovement(String move){
        if(countMove < MAX_MOVEMENT) {

            movement[countMove] = move;
            countMove++;
        }
    }

    public void resetMovement(){
        for(int i = 0; i < MAX_MOVEMENT; i++){
            this.movement[i] = null;
        }

        this.countMove = 0;
    }

    public void confirmMovement() throws IOException {
        client = Data.getInstance().getClient();

        if(movement[0] == null){
            labelStatusPlayer.setText("Player didn't move");

        }else if(movement[1] == null){
            client.move(Converter.fromStringToDirection(movement[0]));

        }else if(movement[2] == null){
            client.move(Converter.fromStringToDirection(movement[0]), Converter.fromStringToDirection(movement[1]));

        }else{
            client.move(Converter.fromStringToDirection(movement[0]), Converter.fromStringToDirection(movement[1]), Converter.fromStringToDirection(movement[2]));

        }

    }

    public void endTurn(){
        Platform.runLater(() -> {
            try {
                
                client = Data.getInstance().getClient();
                client.endTurn();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void placePlayers(Square[][] arena){
        Platform.runLater(() -> {
            playerController = Data.getInstance().getPlayerController();
            MapCLI mapCLI = new MapCLI(playerController.getGameBoard());
            mapCLI.printMap();


            if (!arena[0][0].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[0][0].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][0].getPlayers().get(index).getColor()) + ".jpg");
                    grid00.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }



            if (!arena[0][1].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[0][1].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][1].getPlayers().get(index).getColor()) + ".jpg");
                    grid01.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[0][2].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[0][2].getPlayers().size(); index++) {


                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][2].getPlayers().get(index).getColor()) + ".jpg");
                    grid02.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[0][3].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[0][3].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][3].getPlayers().get(index).getColor()) + ".jpg");
                    grid03.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[1][0].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[1][0].getPlayers().size(); index++) {


                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][0].getPlayers().get(index).getColor()) + ".jpg");
                    grid10.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[1][1].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[1][1].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][1].getPlayers().get(index).getColor()) + ".jpg");
                    grid11.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[1][2].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[1][2].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][2].getPlayers().get(index).getColor()) + ".jpg");
                    grid12.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[1][3].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[1][3].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][3].getPlayers().get(index).getColor()) + ".jpg");
                    grid13.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[2][0].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[2][0].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][0].getPlayers().get(index).getColor()) + ".jpg");
                    grid20.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[2][1].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[2][1].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][1].getPlayers().get(index).getColor()) + ".jpg");
                    grid21.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[2][2].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[2][2].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][2].getPlayers().get(index).getColor()) + ".jpg");
                    grid22.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }


            if (!arena[2][3].getPlayers().isEmpty()) {
                for (int index = 0, row = 0; index < arena[2][3].getPlayers().size(); index++) {

                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][3].getPlayers().get(index).getColor()) + ".jpg");
                    grid23.add(new ImageView(image), index, row);
                    if (index == 2) {
                        row++;
                        index = 0;
                    }
                }
            }
        });
    }

    public void grab(){
        Platform.runLater(() ->{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GrabMove.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 222, 256));
            stage.setTitle("Grab Popup");
            stage.show();
        });
    }

    public void moveUpGrab(MouseEvent mouseEvent) {
        Data.getInstance().setMoveGrab("up");
    }

    public void moveRightGrab(MouseEvent mouseEvent) {
        Data.getInstance().setMoveGrab("right");
    }

    public void moveDownGrab(MouseEvent mouseEvent) {
        Data.getInstance().setMoveGrab("down");
    }

    public void moveLeftGrab(MouseEvent mouseEvent) {
        Data.getInstance().setMoveGrab("left");
    }

    public void confirmMovementGrab(MouseEvent mouseEvent) {
        Platform.runLater(() -> {

            String moveGrab = Data.getInstance().getMoveGrab();
            client = Data.getInstance().getClient();
            playerController = Data.getInstance().getPlayerController();
            int x = playerController.getPlayer().getPosition().getX();
            int y = playerController.getPlayer().getPosition().getY();

            if(moveGrab == null){
                if(!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        client.grab(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Data.getInstance().setMoveGrab(null);
                    guiHandler = Data.getInstance().getGuiHandler();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 269, 496));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }

            }else {
                if(moveGrab == "up"){
                    if(playerController.getGameBoard().getArena()[x][y].canMove(Direction.UP)){
                        if(!playerController.getGameBoard().getArena()[x--][y].isSpawn()) {
                            try {
                                client.grab(0, Direction.UP);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Data.getInstance().setMoveGrab("up");

                            List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x--][y].getWeapons();
                            guiHandler.setWeaponImage(weapon);


                            Stage stage = new Stage();
                            stage.setScene(new Scene(root, 269, 496));
                            stage.setTitle("Choose Weapon");
                            stage.show();
                        }
                    }

                } else if(moveGrab == "down"){
                    if(playerController.getGameBoard().getArena()[x][y].canMove(Direction.DOWN)){
                        if(!playerController.getGameBoard().getArena()[x++][y].isSpawn()) {
                            try {
                                client.grab(0, Direction.DOWN);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Data.getInstance().setMoveGrab("down");

                            List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x++][y].getWeapons();
                            guiHandler.setWeaponImage(weapon);


                            Stage stage = new Stage();
                            stage.setScene(new Scene(root, 269, 496));
                            stage.setTitle("Choose Weapon");
                            stage.show();
                        }
                    }

                } if(moveGrab == "left"){
                    if(playerController.getGameBoard().getArena()[x][y].canMove(Direction.LEFT)){
                        if(!playerController.getGameBoard().getArena()[x][y--].isSpawn()) {
                            try {
                                client.grab(0, Direction.LEFT);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else{
                            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Data.getInstance().setMoveGrab("left");

                            List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y--].getWeapons();
                            guiHandler.setWeaponImage(weapon);


                            Stage stage = new Stage();
                            stage.setScene(new Scene(root, 269, 496));
                            stage.setTitle("Choose Weapon");
                            stage.show();
                        }
                    }

                }if(moveGrab == "right"){
                    if(playerController.getGameBoard().getArena()[x][y].canMove(Direction.RIGHT)){
                        if(!playerController.getGameBoard().getArena()[x][y++].isSpawn()) {
                            try {
                                client.grab(0, Direction.RIGHT);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Data.getInstance().setMoveGrab("right");

                            List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y++].getWeapons();
                            guiHandler.setWeaponImage(weapon);

                            Stage stage = new Stage();
                            stage.setScene(new Scene(root, 269, 496));
                            stage.setTitle("Choose Weapon");
                            stage.show();
                        }
                    }
                }
            }

            Stage stage = (Stage) upArrowGrab.getScene().getWindow();
            stage.close();
        });
    }

    private void setWeaponImage(List<WeaponCard> weapon) {
        Platform.runLater(() ->{
            firstWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(0).getName()) + ".png"));
            secondWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(1).getName()) + ".png"));
            thirdWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(2).getName()) + ".png"));
        });
    }

    public void grabFirstImg(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            client = Data.getInstance().getClient();
            String move = Data.getInstance().getMoveGrab();

            if (move != null) {
                try {
                    client.grab(0, Converter.fromStringToDirection(move));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    client.grab(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Stage stage = (Stage) firstWeapon.getScene().getWindow();
            stage.close();
        });
    }

    public void grabSecondImg(MouseEvent mouseEvent){
        Platform.runLater(() -> {
            client = Data.getInstance().getClient();
            String move = Data.getInstance().getMoveGrab();

            if (move != null) {
                try {
                    client.grab(1, Converter.fromStringToDirection(move));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    client.grab(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Stage stage = (Stage) firstWeapon.getScene().getWindow();
            stage.close();
        });
    }

    public void grabThirdImg(MouseEvent mouseEvent) {
        Platform.runLater(() -> {
            client = Data.getInstance().getClient();
            String move = Data.getInstance().getMoveGrab();

            if (move != null) {
                try {
                    client.grab(2, Converter.fromStringToDirection(move));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    client.grab(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Stage stage = (Stage) firstWeapon.getScene().getWindow();
            stage.close();
        });
    }

    public void disableButtons(){
        Platform.runLater(() ->{

            //disable move
            upArrow.setDisable(true);
            downArrow.setDisable(true);
            rightArrow.setDisable(true);
            leftArrow.setDisable(true);
            enterMove.setDisable(true);

            //disable grab
            bannerGrab.setDisable(true);
            labelGrab.setDisable(true);

            //disable endturn
            bannerEndTurn.setDisable(true);
            labelEndTurn.setDisable(true);

            //disable shoot
        });
    }

    public void enableButtons(){
        Platform.runLater(() ->{

            //disable move
            upArrow.setDisable(false);
            downArrow.setDisable(false);
            rightArrow.setDisable(false);
            leftArrow.setDisable(false);
            enterMove.setDisable(false);

            //disable grab
            bannerGrab.setDisable(false);
            labelGrab.setDisable(false);

            //disable endturn
            bannerEndTurn.setDisable(false);
            labelEndTurn.setDisable(false);

            //disable shoot
        });
    }

    public void showDamageBoard(MouseEvent mouseEvent) {
        Platform.runLater(() ->{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("PlayerBoard.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setPlayerBoardImage();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1218, 755));
            stage.setTitle("DamageBoards");
            stage.show();
        });
    }

    private void setPlayerBoardImage() {
        Platform.runLater(() ->{

            playerController = Data.getInstance().getPlayerController();
            firstBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(playerController.getPlayer().getColor()) + ".jpg"));

            List<Player> otherPlayers = playerController.getOtherPlayers();
            if(otherPlayers.size() == 1){

                secondBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                secondBoard.setVisible(true);

            }else if(otherPlayers.size() == 2){

                secondBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                thirdBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(1).getColor()) + ".jpg"));
                secondBoard.setVisible(true);
                thirdBoard.setVisible(true);
            }
        });
    }

    public void showWeapon(MouseEvent mouseEvent) {
        Platform.runLater(() ->{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ShowWeapon.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setWeaponHad();
            guiHandler.setPowerupHad();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 522, 518));
            stage.setTitle("Weapons");
            stage.show();
        });
    }

    private void setPowerupHad() {
        Platform.runLater(() -> {
            playerController = Data.getInstance().getPlayerController();
            List<PowerupCard> powerupsHad = playerController.getPowerups();

            if(powerupsHad.size() == 1){

                firstPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(1).getColor()) + "/" + powerupsHad.get(1).getName() + ".png"));
                firstPowerupHad.setVisible(true);

            }else if(powerupsHad.size() == 2){

                firstPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(1).getColor()) + "/" + powerupsHad.get(1).getName() + ".png"));
                secondPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(2).getColor()) + "/" + powerupsHad.get(2).getName() + ".png"));
                firstPowerupHad.setVisible(true);
                secondPowerupHad.setVisible(true);

            }else if(powerupsHad.size() == 3){

                firstPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(1).getColor()) + "/" + powerupsHad.get(1).getName() + ".png"));
                secondPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(2).getColor()) + "/" + powerupsHad.get(2).getName() + ".png"));
                thirdPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(3).getColor()) + "/" + powerupsHad.get(3).getName() + ".png"));
                firstPowerupHad.setVisible(true);
                secondPowerupHad.setVisible(true);
                thirdPowerupHad.setVisible(true);
            }
        });
    }

    private void setWeaponHad() {
        Platform.runLater(() ->{

            playerController = Data.getInstance().getPlayerController();
            List<WeaponCard> weaponsHad = playerController.getWeapons();

            if(weaponsHad.size() == 1){
                firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                firstWeaponHad.setVisible(true);

            }else if(weaponsHad.size() == 2){

                firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                secondWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(1).getName()) + ".png"));
                firstWeaponHad.setVisible(true);
                secondWeaponHad.setVisible(true);

            }else if(weaponsHad.size() == 3){

                firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                secondWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(1).getName()) + ".png"));
                thirdWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(2).getName()) + ".png"));
                firstWeaponHad.setVisible(true);
                secondWeaponHad.setVisible(true);
                thirdWeaponHad.setVisible(true);
            }
        });
    }
}
