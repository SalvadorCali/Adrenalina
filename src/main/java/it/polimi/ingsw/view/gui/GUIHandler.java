package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.*;
import it.polimi.ingsw.model.gamecomponents.Square;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.cli.MapCLI;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
    @FXML private ImageView imageWeapon;

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
    @FXML private Label labelShoot;
    @FXML private Label labelShowMove;
    @FXML private Label labelErrorMoveReload;
    @FXML private TextField txtWeaponReload1;
    @FXML private TextField txtWeaponReload2;
    @FXML private TextField txtWeaponReload3;
    @FXML private Button buttonReload;

    @FXML private ImageView firstPowerUpD;
    @FXML private ImageView secondPowerUpD;
    @FXML private ImageView thirdPowerUpD;
    @FXML private ImageView fourthPowerUpD;

    @FXML private TextField directionTxtField2;
    @FXML private TextField directionTxtField3;
    @FXML private TextField directionTxtField4;


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

    @FXML private ImageView firstWeaponHad;
    @FXML private ImageView secondWeaponHad;
    @FXML private ImageView thirdWeaponHad;
    @FXML private ImageView firstPowerupHad;
    @FXML private ImageView secondPowerupHad;
    @FXML private ImageView thirdPowerupHad;
    @FXML private Button shootButton1;
    @FXML private Button shootButton2;
    @FXML private Button shootButton3;

    @FXML private TextField modeTxtField;
    @FXML private TextField firstVictimTxtField;
    @FXML private TextField secondVictimTxtField;
    @FXML private TextField thirdVictimTxtField;
    @FXML private TextField directionTxtField;
    @FXML private TextField xTxtField;
    @FXML private TextField yTxtField;
    @FXML private TextField basicTxtField;
    @FXML private Button shootDataButton;
    @FXML private Label labelInfoWeapon;

    @FXML private Label labelReload;
    @FXML private ImageView bannerReload;

    @FXML private ImageView upArrowMoveReload;
    @FXML private ImageView leftArrowMoveReload;
    @FXML private ImageView rightArrowMoveReload;
    @FXML private ImageView downArrowMoveReload;
    @FXML private Button enterMoveReload;
    @FXML private Label labelShowMoveRel;

    @FXML private Button usePowerup1;
    @FXML private Button usePowerup2;
    @FXML private Button usePowerup3;

    @FXML private TextField victimTxtFieldPowerUp;
    @FXML private TextField ammoTxtFieldPowerUp;
    @FXML private TextField yTxtFieldPowerUp;
    @FXML private TextField firstDirTxtFieldPowerUp;
    @FXML private TextField secondDirTxtFieldPowerUp;
    @FXML private TextField xTxtFieldPowerUp;
    @FXML private Label labelInfoPowerup;
    @FXML private Button buttonUsePowerUpData;

    @FXML private Label labelShowMoveGrab;

    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final int NUM_SQUARES=12;
    private static final int MAX_NUM_PLAYER=5;
    private static final int MAX_MOVEMENT = 3;
    private static final double GRID_WIDTH = 25;
    private static final double GRID_HEIGHT = 25;
    private static final int MAX_WEAPONS = 3;
    private static final double STANDARD_HEIGHT = 50;

    private GameController gameController;
    private String currentPlayer;
    private Square[][] arena = new Square[ROWS][COLUMNS];
    private Stage scene;
    private boolean disconnected = false;
    private ChoosePowerup choosePowerup = new ChoosePowerup();
    private Popup popup = new Popup();
    private String movement[] = new String[MAX_MOVEMENT + 1];
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
    private PlayerBoardGui playerboard;
    private ScorePopup scorePopup;
    private Integer countMovementTwoAction = 0;
    private String moveFrenzyTwoActions[] = new String[MAX_MOVEMENT - 1];
    private Integer countMovementOneAction = 0;
    private String moveFrenzyOneActions[] = new String[MAX_MOVEMENT];
    private String moveReload[] = new String[MAX_MOVEMENT -1];
    private Integer countMoveRel = 0;
    private WeaponInfoHandler weaponInfoHandler = new WeaponInfoHandler();
    private AmmoGUI ammoGUI;
    private String weaponReload1 = " ";
    private String weaponReload2 = " ";
    private String weaponReload3 = " ";

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

        stage.setResizable(false);
    }


    //login methods

    /**
     * Check what button is selected and starts
     * @param playerName
     * @param address
     * @param colorPlayer
     */
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

         this.boardType = 0;
         Data.getInstance().setBoardType(0);
    }

    public void chooseBoard1(){

        this.boardType = 1;
        Data.getInstance().setBoardType(1);
    }

    public void chooseBoard2(){
        this.boardType = 2;
        Data.getInstance().setBoardType(2);
    }

    public void chooseBoard3(){
        this.boardType = 3;
        Data.getInstance().setBoardType(3);
    }

    public void reload(MouseEvent mouseEvent) throws IOException {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy()) {
            if (this.txtWeaponReload1.getText().equals("yes")) {
                reloadClient1();
            }
            if (this.txtWeaponReload2.getText().equals("yes")) {
                reloadClient2();
            }
            if (this.txtWeaponReload3.getText().equals("yes")) {
                reloadClient3();
            }
        }

        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)){

            if (this.txtWeaponReload1.getText().equals("yes")) {
                this.weaponReload1 = "yes";
            }
            if (this.txtWeaponReload2.getText().equals("yes")) {
                this.weaponReload2 = "yes";
            }
            if (this.txtWeaponReload3.getText().equals("yes")) {
                this.weaponReload3 = "yes";
            }

            reloadClientTwoActions();
        }

        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){

            if (this.txtWeaponReload1.getText().equals("yes")) {
                this.weaponReload1 = "yes";
            }
            if (this.txtWeaponReload2.getText().equals("yes")) {
                this.weaponReload2 = "yes";
            }
            if (this.txtWeaponReload3.getText().equals("yes")) {
                this.weaponReload3 = "yes";
            }

            reloadClientOneAction();
        }

        Stage stage = (Stage) buttonReload.getScene().getWindow();
        stage.close();
    }

    public void chooseBoardButton(){

        skull = Integer.valueOf(skullText.getText());
        Data.getInstance().setSkull(skull);

        Platform.runLater(() -> {

            if (boardType == null) {

                labelErrorSkull.setText("Board not chosen");

            } else if (skull > 8 || skull < 1) {

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

        Data.getInstance().setPowerup(2);
        handleCloseAction1();
        try {
            setPowerup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void choosePowerup2() throws IOException {

        Data.getInstance().setPowerup(1);
        handleCloseAction2();
        try {
            setPowerup();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        switch (message){
            case END_TURN:
                notifyEndTurn();
                break;
            case NOT_TURN:
                notifyNotTurn();
                break;
            case FINAL_FRENZY:
                notifyFinalFrenzy();
                break;
            default:
                break;
        }
    }

    private void notifyFinalFrenzy() {
        Platform.runLater(() ->{
            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setLabelStatement("final frenzy");
        });
    }

    private void notifyNotTurn() {
        Platform.runLater(() ->{
            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setLabelStatement("it's not your turn");
        });
    }

    @FXML
    private void notifyEndTurn() {
        Platform.runLater(() ->{
            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setLabelStatement("your turn is ended");
            guiHandler.disableButtons();
        });
    }

    @Override
    public void notify(Message message, Outcome outcome) {
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
                notifyShoot(outcome);
                break;
            case POWERUP:
                notifyPowerup(outcome);
                break;
            case DISCARD_POWERUP:
                notifyDiscardPowerup(outcome);
                break;
            case DROP_POWERUP:
                notifyDropPowerup(outcome);
                break;
            case DROP_WEAPON:
                notifyDropWeapon(outcome);
                break;
            case RESPAWN:
                notifyRespawn(outcome);
                break;
            case RELOAD:
                notifyReload(outcome);
                break;
            default:
                break;
        }
    }

    public void notifyRespawn(Outcome outcome) {
        Platform.runLater(() ->{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiscardPowerUpRespawn.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            guiHandler = loader.getController();
            guiHandler.setPowerupImageRespawn();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 399));
            stage.setTitle("Choose Powerup to Discard");
            stage.show();

            PauseTransition delay = new PauseTransition(Duration.millis(Config.RESPAWN_TIME));
            delay.setOnFinished( event -> {
                stage.close();
            } );
            delay.play();
        });
    }


    private void setPowerupImageRespawn() {

        playerController = Data.getInstance().getPlayerController();
        List<PowerupCard> powerupRespawn = playerController.getPlayer().getPowerups();
        Printer.println("psize: " + playerController.getPlayer().getPowerups().size());
        Printer.println("posize: " + powerupRespawn.size());

        Platform.runLater(()->{

            for (int i = 0; i < powerupRespawn.size(); i++) {

                String color = Converter.fromColorToLetter(powerupRespawn.get(i).getColor());
                Printer.println("color: " + i + " " + color);
                String name = powerupRespawn.get(i).getName();
                Printer.println(name);

                Image image = new Image("powerup/" + color + "/" + name + ".png");

                if(i == 0){
                    firstPowerUpD.setImage(image);
                }
                if (i == 1){
                    secondPowerUpD.setImage(image);
                    secondPowerUpD.setVisible(true);
                }
                if(i == 2){
                    thirdPowerUpD.setImage(image);
                    thirdPowerUpD.setVisible(true);
                }
                if(i == 3){
                    fourthPowerUpD.setImage(image);
                    fourthPowerUpD.setVisible(true);
                }
            }
        });
    }

    private void notifyReload(Outcome outcome) {
        Platform.runLater(() -> {
            if (outcome.equals(Outcome.RIGHT)) {
                setLabelStatement("reloaded!");
                guiHandler = Data.getInstance().getGuiHandler();
                guiHandler.disableButtonWhenReload();

            } else {
                setLabelStatement("not reloaded!");
            }
        });
    }


    private void notifyDropWeapon(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();
        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                guiHandler.setLabelStatement("Weapon dropped");
            }else{
                guiHandler.setLabelStatement("Weapon not dropped");
            }
        });
    }

    private void notifyDropPowerup(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                guiHandler.setLabelStatement("Powerup dropped");
            }else{
                guiHandler.setLabelStatement("Powerup not dropped");
            }
        });
    }

    private void notifyDiscardPowerup(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                guiHandler.setLabelStatement("Powerup discarded");

            }else{
                guiHandler.setLabelStatement("Powerup not discarded");
            }
        });
    }

    private void notifyReconnection(Outcome outcome, String object) {

        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                //aggiunto
                Stage stagelogin = (Stage) loginButton.getScene().getWindow();
                stagelogin.close();

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapGUI.fxml"));

                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                client = Data.getInstance().getClient();
                try {
                    Data.getInstance().setPlayerController(client.getPlayerController());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                guiHandler = loader.getController();
                guiHandler.setMapImage();
                //guiHandler.addWeapon();
                guiHandler.setLabelTurn();

                Data.getInstance().setGuiHandler(guiHandler);

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1189, 710));
                stage.setTitle("Adrenaline's Board");
                stage.show();

                Thread thread = new Thread(this::checkPosition);
                thread.setDaemon(true);
                thread.start();

                disableButtons();
                guiHandler.setLabelStatement(object + " reconnected"); //gia presente
                this.startedGame++;

            }else{
                guiHandler.setLabelStatement(object + " reconnected");
            }
        });
    }

    private void notifyPowerup(Outcome outcome) {
        playerController = Data.getInstance().getPlayerController();
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)){
                guiHandler.setLabelStatement( playerController.getPowerup() + " used!");
            }else if(outcome.equals(Outcome.WRONG)){
                guiHandler.setLabelStatement( playerController.getPowerup() + " not used!");
            }
        });
    }

    private void notifyShoot(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();
        Platform.runLater(() -> {
            switch(outcome){
                case RIGHT:
                    guiHandler.setLabelStatement("shoot");
                    break;
                case ALL:
                    guiHandler.setLabelStatement("shoot");
                    break;
                case WRONG:
                    guiHandler.setLabelStatement("didn't shoot");
                    break;
                default:
                    guiHandler.setLabelStatement("didn't shoot");
                    break;
            }
        });
    }

    private void notifyGrab(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();
        if(outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)){
            guiHandler.setLabelStatement("Grabbed");
            resetMoveGrab();
        }else{
            guiHandler.setLabelStatement("Not grabbed");
            resetMoveGrab();
        }
    }

    private void notifyMovement(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();
        if (outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)) {
            guiHandler.setLabelStatement("Player moved");
            guiHandler.resetMovement();

        } else {
            guiHandler.setLabelStatement("Player didn't move");
            guiHandler.resetMovement();
        }
    }

    private void setLabelStatement(String move) {
        guiHandler = Data.getInstance().getGuiHandler();
        Platform.runLater(() ->{
            guiHandler.labelStatusPlayer.setVisible(true);
            guiHandler.labelStatusPlayer.setText(move);
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

                    PauseTransition delay = new PauseTransition(Duration.millis(Config.BOARD_TYPE_TIME));
                    delay.setOnFinished( event -> {
                        stage.close();
                    } );
                    delay.play();

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

                    PauseTransition delay = new PauseTransition(Duration.millis(Config.BOARD_TYPE_TIME));
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

                if(this.startedGame == 0) {
                    Stage stagelogin = (Stage) loginButton.getScene().getWindow();
                    stagelogin.close();
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
                    //guiHandler.addWeapon();
                    guiHandler.setLabelTurn();

                    Data.getInstance().setGuiHandler(guiHandler);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 1189, 710));
                    stage.setTitle("Adrenaline's Board");
                    stage.show();

                    Thread thread = new Thread(this::checkPosition);
                    thread.setDaemon(true);
                    thread.start();

                    this.startedGame++;

                }else{
                    guiHandler.enableButtons();
                    guiHandler.setLabelTurn();
                }


            }else{
                if(this.startedGame == 0) {
                    Stage stagelogin = (Stage) loginButton.getScene().getWindow();
                    stagelogin.close();
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
                    //guiHandler.addWeapon();
                    guiHandler.setLabelTurn();


                    Data.getInstance().setGuiHandler(guiHandler);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 1189, 710));
                    stage.setTitle("Adrenaline's Board");
                    stage.show();

                    Thread thread = new Thread(this::checkPosition);
                    thread.setDaemon(true);
                    thread.start();


                    guiHandler.disableButtons();
                    this.startedGame++;

                }else{
                    guiHandler.disableButtons();
                    guiHandler.setLabelTurn();
                }
            }
        });
    }


    public void setSkulls() {

        playerController = Data.getInstance().getPlayerController();
        int skulls = playerController.getKillshotTrack().size();
        List<Token> killShot = playerController.getKillshotTrack();
        Image imageSkull = new Image("boardElem/skull.png");

        for (int i = 0; i < skulls; i++) {
            if(killShot.get(i).getFirstColor().equals(TokenColor.SKULL)) {
                addImgOnKillshot(imageSkull, i, 0);

            } else if(!killShot.get(i).getFirstColor().equals(TokenColor.NONE)){

                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(killShot.get(i).getFirstColor()) + ".png");
                removeSkullImgOnKillshot(i, 0);
                addImgOnKillshot(image, i, 0);
            }
        }

    }

    private void removeSkullImgOnKillshot(int col, int row) {
        Platform.runLater(() ->{
            gridSkulls.getChildren().remove(col, row);
        });
    }

    public void addImgOnKillshot(Image image, int col, int row){
        ImageView imv = new ImageView(image);
        imv.setFitWidth(STANDARD_HEIGHT);
        imv.setFitHeight(STANDARD_HEIGHT);

        Platform.runLater(() ->{
            gridSkulls.add(imv, col, row);
        });
    }

    @Override
    public void notify(Message message, Outcome outcome, Object object) {
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
            case SCORE:
                try {
                    notifyScore((Map<TokenColor, Integer>) object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case RECONNECTION:
                notifyReconnection(outcome, (String) object);
                break;
            default:
                break;
        }
    }

    private void notifyScore(Map<TokenColor, Integer> object) throws IOException {
        Platform.runLater(() ->{
            FXMLLoader loader3 = new FXMLLoader(getClass().getClassLoader().getResource("ScorePopup.fxml"));
            Parent root = null;
            try {
                root = loader3.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            scorePopup = loader3.getController();
            scorePopup.setScore(object);

            Stage stage3 = new Stage();
            stage3.setScene(new Scene(root, 411, 311));
            stage3.setTitle("Score");
            stage3.show();
        });
    }


    private void connectToRMI(String name, String host, String color) {

        try {
            client = new RMIClient(host);
            client.setView(this);
            playerController = client.getPlayerController();
            client.login(name, Converter.fromStringToTokenColor(color));
            Data.getInstance().setClient(client);
            Data.getInstance().setPlayerController(playerController);

        } catch (NotBoundException e) {
            setErrorText("Wrong Host");
        } catch (IOException e) {
            setErrorText("Wrong Host");
        }
    }

    private void connectToSocket(String name, String host, String color) {
        try {
            client = new SocketClient(host);
            client.setView(this);
            playerController = client.getPlayerController();
            client.login(name, Converter.fromStringToTokenColor(color));
            Data.getInstance().setClient(client);
            Data.getInstance().setPlayerController(playerController);

        } catch (IOException e) {
            setErrorText("Wrong Host");
        }
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

        switch (outcome) {
            case ALL:{
                Platform.runLater(() -> {
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
                });
                break;
            }
            default:
                break;
        }
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

                PauseTransition delay = new PauseTransition(Duration.millis(Config.SPAWN_LOCATION_TIME));
                delay.setOnFinished( event -> {
                    stage.close();
                } );
                delay.play();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
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

    @FXML
    private void checkPosition() {
        while(checkTurn){
            guiHandler = Data.getInstance().getGuiHandler();
            playerController = Data.getInstance().getPlayerController();

            Platform.runLater(() -> {
                guiHandler.setSkulls();
                guiHandler.setLabelTurn();
                guiHandler.removeImg();
                guiHandler.placePlayers(playerController.getGameBoard().getArena());
                guiHandler.addAmmo();
                guiHandler.addWeapon();
                //guiHandler.removeWeapon();
            });

            try{

                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void checkAmmo() {
        while(checkTurn){
            guiHandler = Data.getInstance().getGuiHandler();
            playerController = Data.getInstance().getPlayerController();

            Platform.runLater(() -> {

                guiHandler.removeAmmo();
            });

            try{

                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addAmmo() {

        playerController = Data.getInstance().getPlayerController();
        arena = playerController.getGameBoard().getArena();
        for(int i=0; i < ROWS ; i++){
            for(int j=0; j<COLUMNS; j++){
                if(arena[i][j].isActive() && !arena[i][j].isSpawn() && arena[i][j].getAmmoCard() != null){
                    AmmoCard ammoCard = arena[i][j].getAmmoCard();
                    String url = "ammo/" + Converter.fromAmmoCardToString(ammoCard) + ".png";
                    ImageView imageView = new ImageView(url);
                    imageView.setFitHeight(GRID_HEIGHT);
                    imageView.setFitWidth(GRID_WIDTH);

                    if(i == 0 && j == 0){
                        Platform.runLater(() ->{
                            grid00.add(imageView, 1, 2);
                        });

                    } else if(i == 0 && j == 1){
                        Platform.runLater(() -> {
                            grid01.add(imageView, 1, 2);
                        });

                    }else if(i == 0 && j == 2){
                        Platform.runLater(() -> {
                            grid02.add(imageView, 1, 2);
                        });

                    }else if(i == 0 && j == 3){
                        Platform.runLater(() -> {
                            grid03.add(imageView, 1, 2);
                        });

                    }else if(i == 1 && j == 0){
                        Platform.runLater(() -> {
                            grid10.add(imageView, 1, 2);
                        });

                    }else if(i == 1 && j == 1){
                        Platform.runLater(() -> {
                            grid11.add(imageView, 1, 2);
                        });

                    }else if(i == 1 && j == 2){
                        Platform.runLater(() -> {
                            grid12.add(imageView, 1, 2);
                        });

                    }else if(i == 1 && j == 3){
                        Platform.runLater(() -> {
                            grid13.add(imageView, 1, 2);
                        });

                    }else if(i == 2 && j == 0){
                        Platform.runLater(() -> {
                            grid20.add(imageView, 1, 2);
                        });

                    }else if(i == 2 && j == 1){
                        Platform.runLater(() -> {
                            grid21.add(imageView, 1, 2);
                        });

                    }else if(i == 2 && j == 2){
                        Platform.runLater(() -> {
                            grid22.add(imageView, 1, 2);
                        });

                    }else if(i == 2 && j == 3){
                        Platform.runLater(() -> {
                            grid23.add(imageView, 1, 2);
                        });
                    }
                }
            }
        }
    }


    public void removeAmmo(){

        playerController = Data.getInstance().getPlayerController();
        arena = playerController.getGameBoard().getArena();

        if(arena[0][0].getAmmoCard() == null && !arena[0][0].isSpawn() && arena[0][0].isActive()){
            Platform.runLater(() -> {
                this.grid00.getChildren().remove(6);
            });
        }
        if(arena[0][1].getAmmoCard() == null && !arena[0][1].isSpawn() && arena[0][1].isActive()){
            Platform.runLater(() -> {
                this.grid01.getChildren().remove(6);
            });
        }
        if(arena[0][2].getAmmoCard() == null && !arena[0][2].isSpawn() && arena[0][2].isActive()){
            Platform.runLater(() -> {
                this.grid02.getChildren().remove(6);
            });
        }
        if(arena[0][3].getAmmoCard() == null && !arena[0][3].isSpawn() && arena[0][3].isActive()){
            Platform.runLater(() -> {
                this.grid03.getChildren().remove(6);
            });
        }
        if(arena[1][0].getAmmoCard() == null && !arena[1][0].isSpawn() && arena[1][0].isActive()){
            Platform.runLater(() -> {
                this.grid10.getChildren().remove(6);
            });
        }
        if(arena[1][1].getAmmoCard() == null && !arena[1][1].isSpawn() && arena[1][1].isActive()){
            Platform.runLater(() -> {
                this.grid11.getChildren().remove(6);
            });
        }
        if(arena[1][2].getAmmoCard() == null && !arena[1][2].isSpawn() && arena[1][2].isActive()){
            Platform.runLater(() -> {
                this.grid12.getChildren().remove(6);
            });
        }
        if(arena[1][3].getAmmoCard() == null && !arena[1][3].isSpawn() && arena[1][3].isActive()){
            Platform.runLater(() -> {
                this.grid13.getChildren().remove(6);
            });
        }
        if(arena[2][0].getAmmoCard() == null && !arena[2][0].isSpawn() && arena[2][0].isActive()){
            Platform.runLater(() -> {
                this.grid20.getChildren().remove(6);
            });
        }
        if(arena[2][1].getAmmoCard() == null && !arena[2][1].isSpawn() && arena[2][1].isActive()){
            Platform.runLater(() -> {
                this.grid21.getChildren().remove(6);
            });
        }
        if(arena[2][2].getAmmoCard() == null && !arena[2][2].isSpawn() && arena[2][2].isActive()){
            Platform.runLater(() -> {
                this.grid22.getChildren().remove(6);
            });
        }
        if(arena[2][3].getAmmoCard() == null && !arena[2][3].isSpawn() && arena[2][3].isActive()){
            Platform.runLater(() -> {
                this.grid23.getChildren().remove(6);
            });
        }
    }


    public void addWeapon() {

        playerController = Data.getInstance().getPlayerController();
        arena = playerController.getGameBoard().getArena();

        Platform.runLater(() -> {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    if (arena[i][j].isSpawn()) {
                        if (arena[i][j].getColor().equals(TokenColor.BLUE)) {
                            if(arena[i][j].getWeapons().size() == 3) {

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                                String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";
                                String url3 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(2).getName()) + ".png";

                                this.weaponBlue1.setImage(new Image(url1));
                                this.weaponBlue2.setImage(new Image(url2));
                                this.weaponBlue3.setImage(new Image(url3));

                            } else if(arena[i][j].getWeapons().size() == 2){

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                                String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";

                                this.weaponBlue1.setImage(new Image(url1));
                                this.weaponBlue2.setImage(new Image(url2));
                                this.weaponBlue3.setImage(null);

                            } else if(arena[i][j].getWeapons().size() == 1){
                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";

                                this.weaponBlue1.setImage(new Image(url1));
                                this.weaponBlue2.setImage(null);
                                this.weaponBlue3.setImage(null);
                            }
                        }

                        if (arena[i][j].getColor().equals(TokenColor.RED)) {
                            if(arena[i][j].getWeapons().size() == 3) {

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                                String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";
                                String url3 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(2).getName()) + ".png";

                                this.weaponRed1.setImage(new Image(url1));
                                this.weaponRed2.setImage(new Image(url2));
                                this.weaponRed3.setImage(new Image(url3));

                            } else if(arena[i][j].getWeapons().size() == 2){

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                                String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";

                                this.weaponRed1.setImage(new Image(url1));
                                this.weaponRed2.setImage(new Image(url2));
                                this.weaponRed3.setImage(null);

                            }else if(arena[i][j].getWeapons().size() == 1){

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";

                                this.weaponRed1.setImage(new Image(url1));
                                this.weaponRed2.setImage(null);
                                this.weaponRed3.setImage(null);
                            }
                        }

                        if (arena[i][j].getColor().equals(TokenColor.YELLOW)) {
                            if(arena[i][j].getWeapons().size() == 3) {

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                                String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";
                                String url3 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(2).getName()) + ".png";

                                this.weaponYellow1.setImage(new Image(url1));
                                this.weaponYellow2.setImage(new Image(url2));
                                this.weaponYellow3.setImage(new Image(url3));

                            } else if(arena[i][j].getWeapons().size() == 2){

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";
                                String url2 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(1).getName()) + ".png";

                                this.weaponYellow1.setImage(new Image(url1));
                                this.weaponYellow2.setImage(new Image(url2));
                                this.weaponYellow3.setImage(null);

                            } else if(arena[i][j].getWeapons().size() == 1){

                                String url1 = "weapon/" + Converter.weaponNameInvert(arena[i][j].getWeapons().get(0).getName()) + ".png";

                                this.weaponYellow1.setImage(new Image(url1));
                                this.weaponYellow2.setImage(null);
                                this.weaponYellow3.setImage(null);
                            }

                        }
                    }
                }
            }
        });
    }

    @FXML
    private void removeImg() {
        grid00.getChildren().clear();
        grid01.getChildren().clear();
        grid02.getChildren().clear();
        grid03.getChildren().clear();
        grid10.getChildren().clear();
        grid11.getChildren().clear();
        grid12.getChildren().clear();
        grid13.getChildren().clear();
        grid20.getChildren().clear();
        grid21.getChildren().clear();
        grid22.getChildren().clear();
        grid23.getChildren().clear();
    }

    public void setLabelTurn() {
        Platform.runLater(() -> {
            guiHandler = Data.getInstance().getGuiHandler();
            playerController = Data.getInstance().getPlayerController();
            guiHandler.playerTurnLabel.setText(playerController.getCurrentPlayer());
        });
    }


    public void setBoard() throws IOException, InterruptedException {
        try {
            client = Data.getInstance().getClient();
            this.client.board(Data.getInstance().getBoardType() + 1, Data.getInstance().getSkull());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMapImage() {
        playerController = Data.getInstance().getPlayerController();
        Platform.runLater(() -> {
            Integer mapNum = Converter.fromBoardTypeToInt(playerController.getGameBoard().getType());
            mapImage.setImage(new Image("boardImg/" + mapNum +".png"));
        });
    }

    public void setPowerup() throws IOException{
        try {
            this.client = Data.getInstance().getClient();
            this.client.choose(Data.getInstance().getPowerup());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }



    @FXML
    private void moveUp(MouseEvent event) throws IOException {
        saveMovement("up");
        setLabelMovement();
    }

    @FXML
    private void moveDown(MouseEvent event) throws IOException {
        saveMovement("down");
        setLabelMovement();
    }

    @FXML
    private void moveLeft(MouseEvent event) throws IOException {
        saveMovement("left");
        setLabelMovement();
    }

    @FXML
    private void moveRight(MouseEvent event) throws IOException {
        saveMovement("right");
        setLabelMovement();
    }

    private void setLabelMovement() {
        for(int i = 0; i < movement.length; i++) {
            if(this.movement[3] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText("  " + this.movement[0] + "  " + this.movement[1] + "  " + this.movement[2] + "  " + this.movement[3]);
                });
            }else if(this.movement[2] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText("  " + this.movement[0] + "  " + this.movement[1] + "  " + this.movement[2]);
                });
            } else if(this.movement[1] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText("  " + this.movement[0] + "  " + this.movement[1]);
                });
            } else if(this.movement[0] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText("  " + this.movement[0]);
                });
            }
        }
    }

    public void saveMovement(String move){
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION))) {
            if (countMove < MAX_MOVEMENT) {

                this.movement[countMove] = move;
                Printer.println("1 or def:" + move);
                this.countMove++;
            }
        }
        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)) {
            if(countMove < MAX_MOVEMENT + 1){

                this.movement[countMove] = move;
                Printer.println("2:" + move);
                this.countMove++;
            }
        }
    }

    public void resetMovement(){
        for(int i = 0; i < MAX_MOVEMENT + 1; i++){
            this.movement[i] = null;
        }

        this.countMove = 0;
    }

    public void confirmMovement() throws IOException {
        client = Data.getInstance().getClient();

        if(movement[0] == null){
            labelStatusPlayer.setText("Player didn't move");

        }else if(movement[1] == null){
            this.client.move(Converter.fromStringToDirection(movement[0]));

        }else if(movement[2] == null){
            this.client.move(Converter.fromStringToDirection(movement[0]), Converter.fromStringToDirection(movement[1]));

        }else if(movement[3] == null){
            this.client.move(Converter.fromStringToDirection(movement[0]), Converter.fromStringToDirection(movement[1]), Converter.fromStringToDirection(movement[2]));

        }else {
            this.client.move(Converter.fromStringToDirection(movement[0]), Converter.fromStringToDirection(movement[1]), Converter.fromStringToDirection(movement[2]), Converter.fromStringToDirection(movement[3]));
        }
    }

    public void reloadPopup(){
        Platform.runLater(() ->{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ReloadPopup.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 338, 208));
            stage.setTitle("Reload Popup");
            stage.show();

        });
    }

    public void reloadClient1(){
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        try {
            client.reload(Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadClient2(){
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();

        try {
            client.reload(Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadClient3(){
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();

        try {
            client.reload(Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void reloadClientTwoActions() throws IOException {
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        String[] moveRel = Data.getInstance().getMoveRel();

        if(moveRel[0] != null) {
            if (this.weaponReload1.equals("yes") && this.weaponReload2.equals(" ") && this.weaponReload3.equals(" ")) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.weaponReload1.equals("yes") && this.weaponReload2.equals("yes") && this.weaponReload3.equals(" ")) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.weaponReload1.equals("yes") && this.weaponReload2 .equals("yes") && this.weaponReload3.equals("yes")) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.weaponReload1.equals(" ") && this.weaponReload2.equals("yes") && this.weaponReload3.equals("yes")) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.weaponReload1.equals(" ") && this.weaponReload2.equals("yes") && this.weaponReload3.equals(" ")) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.weaponReload1.equals("yes") && this.weaponReload2.equals(" ") && this.weaponReload3.equals("yes")) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(this.moveReload[0]), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (this.weaponReload1.equals("yes") && this.weaponReload2.equals(" ") && this.weaponReload3.equals("yes")) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //inserito da cali
        Platform.runLater(() ->{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ShowWeapon.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            guiHandler = loader.getController();
            Data.getInstance().setGuiHandlerWeapon(guiHandler);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 522, 554));
            stage.setTitle("Weapons");
            stage.show();


            Thread thread2 = new Thread(this::checkWeapon);
            thread2.setDaemon(true);
            thread2.start();

        });
    }

    public void reloadClientOneAction(){

        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        String [] moveRel = Data.getInstance().getMoveRel();
        Printer.println("moverel0" + moveRel[0]);
        Printer.println("moverel1" + moveRel[1]);
        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)) {
            if (moveRel[0] != null && moveRel[1] != null) {
                if (this.weaponReload1.equals("yes") && this.weaponReload2.equals(" ") && this.weaponReload3.equals(" ")) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (this.weaponReload1.equals("yes") && this.weaponReload2.equals("yes") && this.weaponReload3.equals(" ")) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (this.weaponReload1.equals("yes") && this.weaponReload2.equals("yes") && this.weaponReload3.equals("yes")) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (this.weaponReload1.equals(" ") && this.weaponReload2.equals("yes") && this.weaponReload3.equals("yes")) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (this.weaponReload1.equals(" ") && this.weaponReload2.equals("yes") && this.weaponReload3.equals(" ")) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (this.weaponReload1.equals(" ") && this.weaponReload2.equals(" ") && this.weaponReload3.equals("yes")) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (this.weaponReload1.equals("yes") && this.weaponReload2 == null && this.weaponReload3.equals("yes")) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //inserito da cali
            Platform.runLater(() ->{

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ShowWeapon.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                guiHandler = loader.getController();
                Data.getInstance().setGuiHandlerWeapon(guiHandler);

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 522, 554));
                stage.setTitle("Weapons");
                stage.show();


                Thread thread2 = new Thread(this::checkWeapon);
                thread2.setDaemon(true);
                thread2.start();

            });
        }
    }


    private void disableButtonWhenReload() {
        Platform.runLater(() ->{

            guiHandler = Data.getInstance().getGuiHandler();

            //disable move
            guiHandler.upArrow.setDisable(true);
            guiHandler.downArrow.setDisable(true);
            guiHandler.rightArrow.setDisable(true);
            guiHandler.leftArrow.setDisable(true);
            guiHandler.enterMove.setDisable(true);

            //disable grab
            guiHandler.bannerGrab.setDisable(true);
            guiHandler.labelGrab.setDisable(true);

            //disable shoot
            guiHandler.bannerShoot.setDisable(true);
            guiHandler.labelShoot.setDisable(true);

        });
    }

    public void endTurn(){
        try {

            client = Data.getInstance().getClient();
            this.client.endTurn();
            setLabelTurn();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void placePlayers(Square[][] arena){
        playerController = Data.getInstance().getPlayerController();
        //MapCLI mapCLI = new MapCLI(playerController.getGameBoard());
        //mapCLI.printMap();


        if(!arena[0][0].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[0][0].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][0].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid00, image, index, row);

            }
        }

        if(!arena[0][1].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[0][1].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][1].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid01, image, index, row);

            }
        }

        if(!arena[0][2].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[0][2].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][2].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid02, image, index, row);

            }
        }

        if(!arena[0][3].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[0][3].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[0][3].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid03, image, index, row);

            }
        }

        if(!arena[1][0].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[1][0].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][0].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid10, image, index, row);

            }
        }

        if(!arena[1][1].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[1][1].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][1].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid11, image, index, row);

            }
        }

        if(!arena[1][2].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[1][2].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][2].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid12, image, index, row);

            }
        }

        if(!arena[1][3].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[1][3].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[1][3].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid13, image, index, row);

            }
        }

        if(!arena[2][0].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[2][0].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][0].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid20, image, index, row);

            }
        }

        if(!arena[2][1].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[2][1].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][1].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid21, image, index, row);

            }
        }

        if(!arena[2][2].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[2][2].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][2].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid22, image, index, row);

            }
        }

        if(!arena[2][3].getPlayers().isEmpty()){
            for(int index = 0, row = 0; index <  arena[2][3].getPlayers().size(); index++){
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(arena[2][3].getPlayers().get(index).getColor()) + ".png");
                addPlayer(grid23, image, index, row);

            }
        }
    }

    private void addPlayer(GridPane grid, Image image, Integer index, Integer row) {
        
        if(index == 2){
            index = 0;
            row++;
        }

        Integer col, roww;
        col = index;
        roww = row;

        ImageView imv = new ImageView(image);
        imv.setFitHeight(30);
        imv.setFitWidth(30);

        Platform.runLater(() ->{
            grid.add(imv, col, roww);
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
            stage.setScene(new Scene(root, 256, 222));
            stage.setTitle("Grab Popup");
            stage.show();

            stage.setResizable(false);
            stage.setOnCloseRequest(event -> resetMoveGrab());
        });
    }

    public void moveUpGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();
        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("up");
            labelShowMoveGrab.setText("  " + "up");
        } else {
            saveMovementFinalFrenzy("up");
        }
    }

    public void moveRightGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("right");
            labelShowMoveGrab.setText("  " + "right");
        } else {
            saveMovementFinalFrenzy("right");
        }
    }

    public void moveDownGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("down");
            labelShowMoveGrab.setText("  " + "down");
        } else {
            saveMovementFinalFrenzy("down");
        }
    }

    public void moveLeftGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("left");
            labelShowMoveGrab.setText("  " + "left");
        } else {
            saveMovementFinalFrenzy("left");
        }
    }

    public void saveMovementFinalFrenzy(String movement){
        playerController = Data.getInstance().getPlayerController();

        if((playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)|| !playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) && this.countMovementTwoAction < MAX_MOVEMENT - 1){
            this.moveFrenzyTwoActions[this.countMovementTwoAction] = movement;
            this.countMovementTwoAction ++;
            Data.getInstance().setMoveGrabTwoActions(moveFrenzyTwoActions);
            setLabelMoveGrabTwoActions();

        } else if(playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION) && this.countMovementOneAction < MAX_MOVEMENT){
            this.moveFrenzyOneActions[this.countMovementOneAction] = movement;
            this.countMovementOneAction ++;
            Data.getInstance().setMoveGrabOneAction(moveFrenzyOneActions);
            setLabelMoveGrabOneAction();
        }
    }


    public void confirmMovementGrab(MouseEvent mouseEvent) throws IOException {

        String moveGrab = Data.getInstance().getMoveGrab();
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) {
            showGrabMove();
            Platform.runLater(() -> {

                int x = playerController.getPlayer().getPosition().getX();
                int y = playerController.getPlayer().getPosition().getY();
                guiHandler = Data.getInstance().getGuiHandler();


                if (moveGrab == null) {
                    if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                        try {
                            client.grab(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        guiHandler = loader.getController();

                        Data.getInstance().setMoveGrab(null);

                        List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();

                        guiHandler.setWeaponImage(weapon);

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 496, 269));
                        stage.setTitle("Choose Weapon");
                        stage.show();

                        stage.setResizable(false);
                    }

                } else {
                    if (moveGrab == "up") {
                        if (playerController.getGameBoard().getArena()[x][y].canMove(Direction.UP)) {
                            x--;
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    client.grab(0, Direction.UP);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Data.getInstance().setMoveGrab("up");
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponImage(weapon);


                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
                                stage.setTitle("Choose Weapon");
                                stage.show();
                            }
                        }

                    } else if (moveGrab == "down") {
                        if (playerController.getGameBoard().getArena()[x][y].canMove(Direction.DOWN)) {
                            x++;
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    client.grab(0, Direction.DOWN);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                guiHandler = loader.getController();

                                Data.getInstance().setMoveGrab("down");

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponImage(weapon);


                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
                                stage.setTitle("Choose Weapon");
                                stage.show();
                            }
                        }

                    }
                    if (moveGrab == "left") {
                        if (playerController.getGameBoard().getArena()[x][y].canMove(Direction.LEFT)) {
                            y--;
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    this.client.grab(0, Direction.LEFT);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                guiHandler = loader.getController();

                                Data.getInstance().setMoveGrab("left");

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponImage(weapon);


                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
                                stage.setTitle("Choose Weapon");
                                stage.show();
                            }
                        }

                    }
                    if (moveGrab == "right") {
                        if (playerController.getGameBoard().getArena()[x][y].canMove(Direction.RIGHT)) {
                            y++;
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    this.client.grab(0, Direction.RIGHT);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Data.getInstance().setMoveGrab("right");
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponImage(weapon);

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
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
        if((playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)) || (!playerController.isFinalFrenzy() && !playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT))){

            int x = playerController.getPlayer().getPosition().getX();
            int y = playerController.getPlayer().getPosition().getY();
            guiHandler = Data.getInstance().getGuiHandler();

            if(this.moveFrenzyTwoActions[0] == null) {
                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        client.grab(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){

                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    guiHandler = loader.getController();

                    Data.getInstance().setMoveGrab(null);

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();

                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }

            } else if(this.moveFrenzyTwoActions[1] == null) {
                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyTwoActions[0]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyTwoActions[0]);

                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyTwoActions[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }
            } else {

                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyTwoActions[0]);
                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyTwoActions[1]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyTwoActions[0]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyTwoActions[1]);

                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyTwoActions[0]), Converter.fromStringToDirection(this.moveFrenzyTwoActions[1]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }
            }

            Stage stage = (Stage) upArrowGrab.getScene().getWindow();
            stage.close();

        }
        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){

            int x = playerController.getPlayer().getPosition().getX();
            int y = playerController.getPlayer().getPosition().getY();
            guiHandler = Data.getInstance().getGuiHandler();

            if(this.moveFrenzyOneActions[0] == null) {
                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }
            } else if(this.moveFrenzyOneActions[1] == null) {

                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[0]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[0]);

                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyOneActions[0]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }

            } else if(this.moveFrenzyOneActions[2] == null){

                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[0]);
                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[1]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[0]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[1]);

                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyOneActions[0]), Converter.fromStringToDirection(this.moveFrenzyOneActions[1]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }

            } else {
                Printer.println(this.moveFrenzyOneActions[0]);
                Printer.println(this.moveFrenzyOneActions[1]);
                Printer.println(this.moveFrenzyOneActions[2]);

                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[0]);
                x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[1]);
                x = x +  Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[2]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[0]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[1]);
                y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[2]);

                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyOneActions[0]), Converter.fromStringToDirection(this.moveFrenzyOneActions[1]), Converter.fromStringToDirection(this.moveFrenzyOneActions[2]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }
            }

            Stage stage = (Stage) upArrowGrab.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void setWeaponImage(List<WeaponCard> weapon) {
        Platform.runLater(() ->{
            this.firstWeapon.setVisible(true);
            this.secondWeapon.setVisible(true);
            this.thirdWeapon.setVisible(true);

            if(weapon.size() >= 1) {
                if (weapon.get(0) != null) {
                    this.firstWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(0).getName()) + ".png"));
                }
            }
            if(weapon.size() >= 2) {
                if (weapon.get(1) != null) {
                    this.secondWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(1).getName()) + ".png"));
                }
            }
            if(weapon.size() >= 3) {
                if (weapon.get(2) != null) {
                    this.thirdWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(2).getName()) + ".png"));
                }
            }

        });
    }

    public void grabFirstImg(MouseEvent mouseEvent) {

        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        String move = Data.getInstance().getMoveGrab();
        String[] moveFrenzyOne = Data.getInstance().getMoveGrabOneAction();
        String[] moveFrenzyTwo = Data.getInstance().getMoveGrabTwoActions();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) {
            if (move != null) {
                try {
                    this.client.grab(1, Converter.fromStringToDirection(move));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    this.client.grab(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else if((!playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS))){
            if(moveFrenzyTwo[0] == null){

                try {
                    client.grab(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(moveFrenzyTwo[1] == null){
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyTwo[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyTwo[0]), Converter.fromStringToDirection(moveFrenzyTwo[1]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            if(moveFrenzyOne[0] == null){
                try {
                    client.grab(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(moveFrenzyOne[1] == null){
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyOne[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(moveFrenzyOne[2] == null) {
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]), Converter.fromStringToDirection(moveFrenzyOne[2]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        Stage stage = (Stage) firstWeapon.getScene().getWindow();
        stage.close();
    }

    public void grabSecondImg(MouseEvent mouseEvent){

        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        String move = Data.getInstance().getMoveGrab();
        String[] moveFrenzyOne = Data.getInstance().getMoveGrabOneAction();
        String[] moveFrenzyTwo = Data.getInstance().getMoveGrabTwoActions();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) {
            if (move != null) {
                try {
                    this.client.grab(2, Converter.fromStringToDirection(move));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    this.client.grab(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if((!playerController.isFinalFrenzy() && !playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS))){
            if(moveFrenzyTwo[0] == null){

                try {
                    client.grab(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(moveFrenzyTwo[1] == null){
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyTwo[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyTwo[0]), Converter.fromStringToDirection(moveFrenzyTwo[1]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            if(moveFrenzyOne[0] == null){

                try {
                    client.grab(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(moveFrenzyOne[1] == null){
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyOne[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(moveFrenzyOne[2] == null) {
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]), Converter.fromStringToDirection(moveFrenzyOne[2]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Stage stage = (Stage) firstWeapon.getScene().getWindow();
        stage.close();
    }

    public void grabThirdImg(MouseEvent mouseEvent) {

        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        String move = Data.getInstance().getMoveGrab();
        String[] moveFrenzyOne = Data.getInstance().getMoveGrabOneAction();
        String[] moveFrenzyTwo = Data.getInstance().getMoveGrabTwoActions();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) {

            if (move != null) {
                try {
                    this.client.grab(3, Converter.fromStringToDirection(move));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    this.client.grab(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if((!playerController.isFinalFrenzy() && !playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS))){
            if(moveFrenzyTwo[0] == null){

                try {
                    client.grab(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(moveFrenzyTwo[1] == null){
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyTwo[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyTwo[0]), Converter.fromStringToDirection(moveFrenzyTwo[1]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            if(moveFrenzyOne[0] == null){

                try {
                    client.grab(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(moveFrenzyOne[1] == null){
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyOne[0]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(moveFrenzyOne[2] == null) {
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]), Converter.fromStringToDirection(moveFrenzyOne[2]));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        Stage stage = (Stage) firstWeapon.getScene().getWindow();
        stage.close();
    }

    public void disableButtons(){
        guiHandler = Data.getInstance().getGuiHandler();
        Platform.runLater(() ->{

            //disable move
            guiHandler.upArrow.setDisable(true);
            guiHandler.downArrow.setDisable(true);
            guiHandler.rightArrow.setDisable(true);
            guiHandler.leftArrow.setDisable(true);
            guiHandler.enterMove.setDisable(true);

            //disable grab
            guiHandler.bannerGrab.setDisable(true);
            guiHandler.labelGrab.setDisable(true);

            //disable endturn
            guiHandler.bannerEndTurn.setDisable(true);
            guiHandler.labelEndTurn.setDisable(true);

            //disable shoot
            guiHandler.bannerShoot.setDisable(true);
            guiHandler.labelShoot.setDisable(true);

            //disable reload
            guiHandler.bannerReload.setDisable(true);
            guiHandler.labelReload.setDisable(true);
        });
    }

    public void enableButtons(){
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{

            //enable move
            guiHandler.upArrow.setDisable(false);
            guiHandler.downArrow.setDisable(false);
            guiHandler.rightArrow.setDisable(false);
            guiHandler.leftArrow.setDisable(false);
            guiHandler.enterMove.setDisable(false);

            //enable grab
            guiHandler.bannerGrab.setDisable(false);
            guiHandler.labelGrab.setDisable(false);

            //enable endturn
            guiHandler.bannerEndTurn.setDisable(false);
            guiHandler.labelEndTurn.setDisable(false);

            //enable shoot
            guiHandler.bannerShoot.setDisable(false);
            guiHandler.labelShoot.setDisable(false);

            //enable reload
            guiHandler.bannerReload.setDisable(false);
            guiHandler.labelReload.setDisable(false);
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

            playerboard = loader.getController();
            playerboard.setAll();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1218, 755));
            stage.setTitle("PlayerBoards");
            stage.show();

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

            guiHandler = loader.getController();
            Data.getInstance().setGuiHandlerWeapon(guiHandler);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 522, 554));
            stage.setTitle("Weapons");
            stage.show();


            Thread thread2 = new Thread(this::checkWeapon);
            thread2.setDaemon(true);
            thread2.start();

        });
    }

    private void checkWeapon() {
        while (checkTurn) {
            Platform.runLater(() -> {

                guiHandler = Data.getInstance().getGuiHandlerWeapon();
                guiHandler.setWeaponHad();
                guiHandler.setPowerupHad();
            });

            try {

                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void setPowerupHad() {
        playerController = Data.getInstance().getPlayerController();
        List<PowerupCard> powerupsHad = playerController.getPowerups();

        if(powerupsHad.size() == 1){
            Platform.runLater(() -> {

                this.firstPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(0).getColor()) + "/" + powerupsHad.get(0).getName() + ".png"));
                this.firstPowerupHad.setVisible(true);
            });

        }else if(powerupsHad.size() == 2){
            Platform.runLater(() -> {

                this.firstPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(0).getColor()) + "/" + powerupsHad.get(0).getName() + ".png"));
                this.secondPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(1).getColor()) + "/" + powerupsHad.get(1).getName() + ".png"));
                this.firstPowerupHad.setVisible(true);
                this.secondPowerupHad.setVisible(true);
            });

        }else if(powerupsHad.size() == 3){
            Platform.runLater(() -> {

                this.firstPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(0).getColor()) + "/" + powerupsHad.get(0).getName() + ".png"));
                this.secondPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(1).getColor()) + "/" + powerupsHad.get(1).getName() + ".png"));
                this.thirdPowerupHad.setImage(new Image("powerup/" + Converter.fromColorToLetter(powerupsHad.get(2).getColor()) + "/" + powerupsHad.get(2).getName() + ".png"));
                this.firstPowerupHad.setVisible(true);
                this.secondPowerupHad.setVisible(true);
                this.thirdPowerupHad.setVisible(true);
            });
        }
    }

    @FXML
    private void setWeaponHad() {

        playerController = Data.getInstance().getPlayerController();
        List<WeaponCard> weaponsHad = playerController.getWeapons();

        if(weaponsHad.size() == 1){
            Platform.runLater(() -> {

                this.firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                this.firstWeaponHad.setVisible(true);
            });

        }else if(weaponsHad.size() == 2){
            Platform.runLater(() -> {

                this.firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                this.secondWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(1).getName()) + ".png"));
                this.firstWeaponHad.setVisible(true);
                this.secondWeaponHad.setVisible(true);
            });

        }else if(weaponsHad.size() == 3){
            Platform.runLater(() -> {

                this.firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                this.secondWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(1).getName()) + ".png"));
                this.thirdWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(2).getName()) + ".png"));
                this.firstWeaponHad.setVisible(true);
                this.secondWeaponHad.setVisible(true);
                this.thirdWeaponHad.setVisible(true);
            });
        }
    }


    public void shoot(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && !playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND)) {
            this.guiHandler = Data.getInstance().getGuiHandler();
            this.guiHandler.showWeapon(mouseEvent);

        }

        if(playerController.isFinalFrenzy() || (!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND))){
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MoveAndReload.fxml"));

                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 222, 256));
                stage.setTitle("Move and Reload Popup");
                stage.show();

                stage.setResizable(false);
            });
        }
    }


    public void shootFirstWeapon(MouseEvent mouseEvent) {
        Data.getInstance().setWeaponShoot(0);
        launchDataShoot();
    }

    public void shootSecondWeapon(MouseEvent mouseEvent) {
        Data.getInstance().setWeaponShoot(1);
        launchDataShoot();
    }

    public void shootThirdWeapon(MouseEvent mouseEvent) {
        Data.getInstance().setWeaponShoot(2);
        launchDataShoot();
    }

    @FXML
    private void launchDataShoot() {
        playerController = Data.getInstance().getPlayerController();
        Integer weaponNum = Data.getInstance().getWeaponShoot();

        if(!playerController.getWeapons().isEmpty()){
        Platform.runLater(() -> {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DataShoot.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            guiHandler = loader.getController();
            guiHandler.setLabelWeapon(playerController.getWeapons().get(weaponNum).getName());

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 522, 454));
            stage.setTitle("Data Shoot");
            stage.show();
        });
        }
    }

    private void setLabelWeapon(String name) {
        String textWeapon = weaponInfoHandler.getInfoWeapon(name);
        Platform.runLater(() ->{
            labelInfoWeapon.setText(textWeapon);
        });
    }

    public void setShoot(MouseEvent mouseEvent) {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();
        Integer weaponNum = Data.getInstance().getWeaponShoot();

        boolean basicFirst = true;
        String firstVictim = null;
        String secondVictim = null;
        String thirdVictim = null;
        Direction direction = null;
        Direction direction2 = null;
        Direction direction3 = null;
        Direction direction4 = null;
        Integer x = -1;
        Integer y = -1;
        Integer mode = 1;


        if(modeTxtField.getText().equals("1") || modeTxtField.getText().equals("2") || modeTxtField.getText().equals("3")) {
            mode = Integer.valueOf(modeTxtField.getText());
            Printer.println(mode);
        }

        if(basicTxtField.getText().equals("true") || basicTxtField.getText().equals("false")) {
            basicFirst = Boolean.parseBoolean(basicTxtField.getText());
            Printer.println(basicFirst);
        }

        if (firstVictimTxtField.getText().equals("blue") || firstVictimTxtField.getText().equals("green") || firstVictimTxtField.getText().equals("purple") || firstVictimTxtField.getText().equals("grey") || firstVictimTxtField.getText().equals("yellow")) {
            firstVictim = firstVictimTxtField.getText();
            Printer.println(firstVictim);
        }

        if (secondVictimTxtField.getText().equals("blue") || secondVictimTxtField.getText().equals("green") || secondVictimTxtField.getText().equals("purple") || secondVictimTxtField.getText().equals("grey") || secondVictimTxtField.getText().equals("yellow")) {
            secondVictim = secondVictimTxtField.getText();
            Printer.println(secondVictim);
        }

        if(thirdVictimTxtField.getText().equals("blue") || thirdVictimTxtField.getText().equals("green") || thirdVictimTxtField.getText().equals("purple") || thirdVictimTxtField.getText().equals("grey") || thirdVictimTxtField.getText().equals("yellow")) {
            thirdVictim = thirdVictimTxtField.getText();
            Printer.println(thirdVictim);
        }

        if(directionTxtField.getText().equals("up") || directionTxtField.getText().equals("down") || directionTxtField.getText().equals("left") || directionTxtField.getText().equals("right")) {
            direction = Converter.fromStringToDirection(directionTxtField.getText());
            Printer.println(direction);
        }

        if(directionTxtField2.getText().equals("up") || directionTxtField2.getText().equals("down") || directionTxtField2.getText().equals("left") || directionTxtField2.getText().equals("right")) {
            direction2 = Converter.fromStringToDirection(directionTxtField2.getText());
            Printer.println(direction2);
        }

        if(directionTxtField3.getText().equals("up") || directionTxtField3.getText().equals("down") || directionTxtField3.getText().equals("left") || directionTxtField3.getText().equals("right")) {
            direction3 = Converter.fromStringToDirection(directionTxtField3.getText());
            Printer.println(direction3);
        }

        if(directionTxtField4.getText().equals("up") || directionTxtField4.getText().equals("down") || directionTxtField4.getText().equals("left") || directionTxtField4.getText().equals("right")) {
            direction4 = Converter.fromStringToDirection(directionTxtField4.getText());
            Printer.println(direction4);
        }

        if(!xTxtField.getText().isEmpty() && !yTxtField.getText().isEmpty()) {
            x = Integer.valueOf(xTxtField.getText());
            y = Integer.valueOf(yTxtField.getText());
            Printer.println(x);
            Printer.println(y);
        }

        try {
            if(firstVictim != null && secondVictim != null && thirdVictim != null) {
                client.shoot(Converter.weaponNameInvert(playerController.getWeapons().get(weaponNum).getName()), mode, basicFirst, Converter.fromStringToTokenColor(firstVictim), Converter.fromStringToTokenColor(secondVictim), Converter.fromStringToTokenColor(thirdVictim), x, y, direction, direction2, direction3, direction4);

            } else if(firstVictim != null && secondVictim != null && thirdVictim == null){
                client.shoot(Converter.weaponNameInvert(playerController.getWeapons().get(weaponNum).getName()), mode, basicFirst, Converter.fromStringToTokenColor(firstVictim), Converter.fromStringToTokenColor(secondVictim), TokenColor.NONE, x, y, direction, direction2, direction3, direction4);

            } else if(firstVictim != null && secondVictim == null && thirdVictim == null) {
                client.shoot(Converter.weaponNameInvert(playerController.getWeapons().get(weaponNum).getName()), mode, basicFirst, Converter.fromStringToTokenColor(firstVictim), TokenColor.NONE, TokenColor.NONE, x, y, direction, direction2, direction3, direction4);

            } else if(firstVictim == null && secondVictim == null && thirdVictim == null){
                client.shoot(Converter.weaponNameInvert(playerController.getWeapons().get(weaponNum).getName()), mode, basicFirst, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE, x, y, direction, direction2, direction3, direction4);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) modeTxtField.getScene().getWindow();
        stage.close();
    }

    public void disconnect(MouseEvent mouseEvent) {

        client = Data.getInstance().getClient();
        try {
            client.disconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void upMoveRel(MouseEvent mouseEvent){
        setMoveRel("up");
        setLabelMoveRel();
    }

    public void rightMoveRel(MouseEvent mouseEvent){
        setMoveRel("right");
        setLabelMoveRel();
    }

    public void downMoveRel(MouseEvent mouseEvent){
        setMoveRel("down");
        setLabelMoveRel();
    }

    public void leftMoveRel(MouseEvent mouseEvent){
        setMoveRel("left");
        setLabelMoveRel();
    }

    private void setMoveRel(String move) {
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS) || playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND)) {
            this.moveReload[0] = move;
            Printer.println("2963: " + this.moveReload[0]);

        } else if(playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){
            if (this.countMoveRel < MAX_MOVEMENT - 1) {
                this.moveReload[this.countMoveRel] = move;
                this.countMoveRel++;
                Data.getInstance().setMoveRel(moveReload);
            }
        }
    }

    public void confirmMoveRel(MouseEvent mouseEvent) throws IOException {
        playerController = Data.getInstance().getPlayerController();
        guiHandler = Data.getInstance().getGuiHandler();

        if(playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND)){

            moveReloadZoneTwo();

            Platform.runLater(() ->{

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ShowWeapon.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                guiHandler = loader.getController();
                Data.getInstance().setGuiHandlerWeapon(guiHandler);

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 522, 554));
                stage.setTitle("Weapons");
                stage.show();


                Thread thread2 = new Thread(this::checkWeapon);
                thread2.setDaemon(true);
                thread2.start();

            });

        } else{

            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.reloadPopup();
        }

        Stage stage = (Stage) enterMoveReload.getScene().getWindow();
        stage.close();
    }

    private void moveReloadZoneTwo() throws IOException {
        client = Data.getInstance().getClient();
        String[] moveRel = Data.getInstance().getMoveRel();

        if(moveRel[0] == null) {
            client.moveAndReload(null);
        }else {
            client.moveAndReload(Converter.fromStringToDirection(moveRel[0]));
        }
    }

    public void showErrorMoveRel(){
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            guiHandler.labelErrorMoveReload.setVisible(true);
        });
    }

    public void useThirdPowerup(MouseEvent mouseEvent) throws IOException {
        Data.getInstance().setNumPowerup(2);
        launchPowerupData();
    }

    public void useSecondPowerup(MouseEvent mouseEvent) throws IOException {
        Data.getInstance().setNumPowerup(1);
        launchPowerupData();
    }

    public void useFirstPowerup(MouseEvent mouseEvent) throws IOException {
        Data.getInstance().setNumPowerup(0);
        launchPowerupData();
    }

    public void usePowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        String victim = null;
        String colorAmmo = null;
        String x = "-1";
        String y = "-1";
        String firstDir = null;
        String secondDir = null;
        String namePowerup = playerController.getPowerups().get(Data.getInstance().getNumPowerup()).getName();

        if(victimTxtFieldPowerUp.getText().equals("blue") || victimTxtFieldPowerUp.getText().equals("green") || victimTxtFieldPowerUp.getText().equals("purple") || victimTxtFieldPowerUp.getText().equals("grey") || victimTxtFieldPowerUp.getText().equals("yellow")){
            victim = victimTxtFieldPowerUp.getText();
        }
        if(ammoTxtFieldPowerUp.getText().equals("blue") || ammoTxtFieldPowerUp.getText().equals("red") || ammoTxtFieldPowerUp.getText().equals("yellow")){
            colorAmmo = ammoTxtFieldPowerUp.getText();
        }
        if(firstDirTxtFieldPowerUp.getText().equals("up") || firstDirTxtFieldPowerUp.getText().equals("down") || firstDirTxtFieldPowerUp.getText().equals("left") || firstDirTxtFieldPowerUp.getText().equals("right")){
            firstDir = firstDirTxtFieldPowerUp.getText();
        }
        if(secondDirTxtFieldPowerUp.getText().equals("up") || secondDirTxtFieldPowerUp.getText().equals("down") || secondDirTxtFieldPowerUp.getText().equals("left") || secondDirTxtFieldPowerUp.getText().equals("right")){
            secondDir = secondDirTxtFieldPowerUp.getText();
        }
        if(!xTxtFieldPowerUp.getText().isEmpty()){
            x = xTxtFieldPowerUp.getText();
        }
        if(!yTxtFieldPowerUp.getText().isEmpty()){
            y = yTxtFieldPowerUp.getText();
        }

        switch (namePowerup){
            case "TARGETING SCOPE":
                client.powerup(Converter.powerupNameInvert(namePowerup), Converter.fromStringToTokenColor(victim), Converter.fromStringToColor(colorAmmo), -1, -1);
            case "NEWTON":
                if(secondDir == null) {
                    client.powerup(Converter.powerupNameInvert(namePowerup), Converter.fromStringToTokenColor(victim), Color.NONE, -1, -1, Converter.fromStringToDirection(firstDir));
                } else{
                    client.powerup(Converter.powerupNameInvert(namePowerup), Converter.fromStringToTokenColor(victim), Color.NONE, -1, -1, Converter.fromStringToDirection(firstDir), Converter.fromStringToDirection(secondDir));
                }
            case "TAGBACK GRENADE":
                client.powerup(Converter.powerupNameInvert(namePowerup), Converter.fromStringToTokenColor(victim), Color.NONE, -1, -1);
            case "TELEPORTER":
                client.powerup(Converter.powerupNameInvert(namePowerup), TokenColor.NONE, Color.NONE, Integer.valueOf(x), Integer.valueOf(y));
            default:
                break;
        }

        guiHandler = Data.getInstance().getGuiHandler();
        guiHandler.handleCloseShowData();
        guiHandler.handleCloseDataPowerUp();
    }

    private void handleCloseDataPowerUp() {
        Stage stage = (Stage) xTxtFieldPowerUp.getScene().getWindow();
        stage.close();
    }

    private void handleCloseShowData() {
        Stage stage = (Stage) this.shootButton1.getScene().getWindow();
        stage.close();
    }

    private void launchPowerupData() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DataPowerUp.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setLabelPowerup();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 469, 379));
        stage.setTitle("Data PowerUp");
        stage.show();
    }

    private void setLabelPowerup() {
        playerController = Data.getInstance().getPlayerController();
        String namePowerup = playerController.getPowerups().get(Data.getInstance().getNumPowerup()).getName();
        String infoPowerup = weaponInfoHandler.getInfoPowerUp(namePowerup);

        Platform.runLater(() ->{
            labelInfoPowerup.setText(infoPowerup);
        });
    }


    public void showWeaponRed1(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponRed1.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponRed2(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponRed2.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponRed3(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponRed3.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponYellow1(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponYellow1.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponYellow2(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponYellow2.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponYellow3(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponYellow3.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponBlue1(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponBlue1.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponBlue2(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponBlue2.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void showWeaponBlue3(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeaponDetail.fxml"));
        Parent root = loader.load();

        guiHandler = loader.getController();
        guiHandler.setImageWeapon(weaponBlue3.getImage());

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 188, 319));
        stage.setTitle("Weapon Popup");
        stage.show();
    }

    public void setImageWeapon(Image image){
        Platform.runLater(() ->{
            this.imageWeapon.setImage(image);
        });
    }

    public void launchAmmo(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AmmoGrids.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ammoGUI = loader.getController();
        ammoGUI.setAmmo();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 608, 266));
        stage.setTitle("Ammo");
        stage.show();
    }

    public void dropFirstWeapon(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getWeapons().size() == 3) {
            client.dropWeapon(1);
        }
    }

    public void dropSecondWeapon(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropWeapon(2);
        }
    }

    public void dropThirdWeapon(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropWeapon(3);
        }
    }

    public void dropFirstPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropPowerup(1);
        }
    }

    public void dropSecondPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropPowerup(2);
        }
    }

    public void dropThirdPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropPowerup(3);
        }
    }

    public void discardFirstPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() >= 1) {
            client.discardPowerup(1);
        }
    }

    public void discardSecondPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() >= 2) {
            client.discardPowerup(2);
        }
    }

    public void discardThirdPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.discardPowerup(3);
        }
    }

    public void discardPw1(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void discardPw2(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void discardPw3(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void discardPw4(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetMoveGrab(){
        int moveLength = this.moveFrenzyTwoActions.length;
        int moveLength2 = this.moveFrenzyOneActions.length;

        for(int i= 0; i < moveLength; i++){

            this.moveFrenzyTwoActions[i] = null;
        }

        for(int i = 0; i < moveLength2; i++){

            this.moveFrenzyOneActions[i] = null;
        }

        Data.getInstance().setMoveGrab(null);
    }

    private void setLabelMoveGrabTwoActions() {
        for(int i = 0; i < this.moveFrenzyTwoActions.length; i++) {
            //if(this.moveFrenzyTwoActions[2] != null){
                //Platform.runLater(() ->{
                  //  labelShowMoveGrab.setText("  " + this.moveFrenzyTwoActions[0] + "  " + this.moveFrenzyTwoActions[1] + "  " + this.moveFrenzyTwoActions[2]);
                //});
            //} else
                if(this.moveFrenzyTwoActions[1] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText("  " + this.moveFrenzyTwoActions[0] + "  " + this.moveFrenzyTwoActions[1]);
                });
            } else if(this.moveFrenzyTwoActions[0] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText("  " + this.moveFrenzyTwoActions[0]);
                });
            }
                //else if(this.moveFrenzyTwoActions[3] != null){
                //Platform.runLater(() ->{
                  //  labelShowMoveGrab.setText("  " + this.moveFrenzyTwoActions[0] + "  " + this.moveFrenzyTwoActions[1] + "  " + this.moveFrenzyTwoActions[2] + "  " + this.moveFrenzyTwoActions[3]);
                //});
            //}
        }
    }

    private void setLabelMoveGrabOneAction() {
        for(int i = 0; i < this.moveFrenzyOneActions.length; i++) {
            if(this.moveFrenzyOneActions[2] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText("  " + this.moveFrenzyOneActions[0] + "  " + this.moveFrenzyOneActions[1] + "  " + this.moveFrenzyOneActions[2]);
                });
            } else if(this.moveFrenzyOneActions[1] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText("  " + this.moveFrenzyOneActions[0] + "  " + this.moveFrenzyOneActions[1]);
                });
            } else if(this.moveFrenzyOneActions[0] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText("  " + this.moveFrenzyOneActions[0]);
                });
            }
        }
    }

    private void showGrabMove(){
        Platform.runLater(()->{
            labelShowMoveGrab.setText(Data.getInstance().getMoveGrab());
        });
    }

    private void setLabelMoveRel() {
        String[] moveRel = Data.getInstance().getMoveRel();
        for(int i = 0; i < moveRel.length; i++) {
            if(moveRel[1] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText("  " + moveRel[0] + "  " + moveRel[1]);
                });
            } else if(moveRel[0] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText("  " + moveRel[0]);
                });
            }
        }
    }
}
