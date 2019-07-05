package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.*;
import it.polimi.ingsw.model.enums.Color;
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
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.cli.StringCLI;
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

/**
 * This is the main class for the Gui, it shows the mainBoard with all the interactions that can be done by the players.
 * It extends Application from javafx, it implements Initializable always from javafx and ViewInteface used for receiveing message, notify from the server.
 */
public class GUIHandler extends Application implements ViewInterface, Initializable {

    /**
     * these are the elements of the mainBoard that includes: board, weapons, decks, banners for actions, some labels.
     */
    @FXML private ImageView mapImage;
    @FXML private ImageView weaponRed1;
    @FXML private ImageView weaponRed2;
    @FXML private ImageView weaponRed3;
    @FXML private ImageView weaponYellow1;
    @FXML private ImageView weaponYellow2;
    @FXML private ImageView weaponYellow3;
    @FXML private ImageView weaponBlue1;
    @FXML private ImageView weaponBlue2;
    @FXML private ImageView weaponBlue3;
    @FXML private ImageView deckPowerup;
    @FXML private ImageView deckWeapon;
    @FXML private ImageView bannerShoot;
    @FXML private ImageView bannerGrab;
    @FXML private ImageView bannerEndTurn;
    @FXML private ImageView upArrow;
    @FXML private ImageView rightArrow;
    @FXML private ImageView downArrow;
    @FXML private ImageView leftArrow;
    @FXML private ImageView bannerShowWeapon;
    @FXML private ImageView bannerShowDamage1;
    @FXML private Label playerTurnLabel;
    @FXML private Label labelStatusPlayer;
    @FXML private ImageView imageWeapon;
    @FXML private Label mainPlayerLabel;
    @FXML private Label labelDisconnect;
    @FXML private ImageView bannerDisconnect;
    @FXML private ImageView firstWeapon;
    @FXML private ImageView secondWeapon;
    @FXML private ImageView thirdWeapon;
    @FXML private Label labelGrab;
    @FXML private Label labelReload;
    @FXML private ImageView bannerReload;
    @FXML private Label labelShoot;
    @FXML private Label labelShowMove;
    @FXML private Label labelEndTurn;
    @FXML private ImageView playerColorImage;

    /**
     * these are the grids of the mainBoard used for displaying players and ammos or skulls and color that represents the turns
     */
    @FXML private GridPane gridSkulls;
    @FXML private GridPane grid00;
    @FXML private GridPane grid10;
    @FXML private GridPane grid20;
    @FXML private GridPane grid03;
    @FXML private GridPane grid01;
    @FXML private GridPane grid11;
    @FXML private GridPane grid02;
    @FXML private GridPane grid12;
    @FXML private GridPane grid21;
    @FXML private GridPane grid22;
    @FXML private GridPane grid13;
    @FXML private GridPane grid23;
    @FXML private GridPane gridKillshotTrack2;


    /**
     * these elements are used for moveGrab action
     */
    @FXML private Button enterMove;
    @FXML private ImageView upArrowGrab;
    @FXML private ImageView downArrowGrab;
    @FXML private ImageView rightArrowGrab;
    @FXML private ImageView leftArrowGrab;
    @FXML private Button enterMoveGrab;
    @FXML private Label labelShowMoveGrab;

    /**
     * These elements are used for Reload and MoveandReload
     */
    @FXML private Label labelErrorMoveReload;
    @FXML private TextField txtWeaponReload1;
    @FXML private TextField txtWeaponReload2;
    @FXML private TextField txtWeaponReload3;
    @FXML private Button buttonReload;
    @FXML private ImageView upArrowMoveReload;
    @FXML private ImageView leftArrowMoveReload;
    @FXML private ImageView rightArrowMoveReload;
    @FXML private ImageView downArrowMoveReload;
    @FXML private Button enterMoveReload;
    @FXML private Label labelShowMoveRel;


    /**
     * These elements are used for the login window
     */
    @FXML RadioButton socketButton;
    @FXML RadioButton rmiButton;
    @FXML TextField nicknameField;
    @FXML TextField addressField;
    @FXML TextField colorField;
    @FXML Label statusConnectionLabel;
    @FXML Button loginButton;
    @FXML Label connectionErrorLabel;

    /**
     * These elements are used for the ChooseBoard Popup
     */
    @FXML private ImageView firstBoard;
    @FXML private ImageView secondBoard;
    @FXML private ImageView thirdBoard;
    @FXML private ImageView fourthBoard;
    @FXML private Label labelErrorSkull;
    @FXML private TextField skullText;
    @FXML private Button enterButton;

    /**
     * These imgviews are used when player has to choose powerup
     */
    @FXML private ImageView powerupImg1;
    @FXML private ImageView powerupImg2;

    /**
     * These are used for displaying PowerUps, Weapons that players had and for shooting, dropping or discarding.
     */
    @FXML private ImageView firstPowerUpD;
    @FXML private ImageView secondPowerUpD;
    @FXML private ImageView thirdPowerUpD;
    @FXML private ImageView fourthPowerUpD;
    @FXML private ImageView firstWeaponHad;
    @FXML private ImageView secondWeaponHad;
    @FXML private ImageView thirdWeaponHad;
    @FXML private ImageView firstPowerupHad;
    @FXML private ImageView secondPowerupHad;
    @FXML private ImageView thirdPowerupHad;
    @FXML private Button shootButton1;
    @FXML private Button shootButton2;
    @FXML private Button shootButton3;
    @FXML private Label labelLoaded1;
    @FXML private Label labelLoaded2;
    @FXML private Label labelLoaded3;
    @FXML private Button usePowerup1;
    @FXML private Button usePowerup2;
    @FXML private Button usePowerup3;

    /**
     * These elements are used for insert various data when someone is shooting.
     */
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
    @FXML private TextField directionTxtField2;
    @FXML private TextField directionTxtField3;
    @FXML private TextField directionTxtField4;

    /**
     * These elements are used for insert data when using a powerup.
     */
    @FXML private TextField victimTxtFieldPowerUp;
    @FXML private TextField ammoTxtFieldPowerUp;
    @FXML private TextField yTxtFieldPowerUp;
    @FXML private TextField firstDirTxtFieldPowerUp;
    @FXML private TextField secondDirTxtFieldPowerUp;
    @FXML private TextField xTxtFieldPowerUp;
    @FXML private Label labelInfoPowerup;
    @FXML private Button buttonUsePowerUpData;

    /**
     * Some final variables
     */
    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final int MAX_MOVEMENT = 3;
    private static final double GRID_WIDTH = 25;
    private static final double GRID_HEIGHT = 25;
    private static final double STANDARD_HEIGHT = 40;
    private static final int MAX_SKULLS = 8;
    private static final int MIN_SKULLS = 1;
    private static final String SPACE = " ";
    private static final String DOUBLE_SPACE = "  ";
    private static final String YES = "yes";
    private static final String NO = "no";
    private static final int TIME_UPDATING_MAIN_BOARD = 5000;
    private static final String LOADED = "Loaded";
    private static final String UNLOADED = "Unloaded";

    /**
     * main variables
     */
    private Square[][] arena = new Square[ROWS][COLUMNS];
    private Popup popup = new Popup();
    private String movement[] = new String[MAX_MOVEMENT + 1];
    private Integer countMove = 0;
    private PlayerController playerController;
    private ClientInterface client;
    private boolean checkTurn = true;
    private String playerName;
    private String address;
    private String colorPlayer;
    private Integer boardType;
    private Integer skull;
    private int startedGame = 0;
    private GUIHandler guiHandler;
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
    private String weaponReload1 = SPACE;
    private String weaponReload2 = SPACE;
    private String weaponReload3 = SPACE;

    /**
     * Standard method that launches the first window which is the Login
     * @param stage main stage of the login
     * @throws Exception if it doesn't load the LoginGUI.fxml
     */
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
     * Check what button is selected and if is selected one of them it launches the right connection method
     * @param playerName name of the player
     * @param address address of the player
     * @param colorPlayer color of the player
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

    /**
     * This method check if txtfields are empties, if not launches clickButton method
     */
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

    /**
     * This method takes a string and set it to StatusConnectionLabel on the Login
     * @param text info about connection
     */
    public void setConnectionText(String text){
        Platform.runLater(() ->{
            statusConnectionLabel.setText(text);
        });
    }

    /**
     * This method takes a string and set it to ConnectionErrorLabel on the Login
     * @param text info about connection error
     */
    public void setErrorText(String text){
        Platform.runLater(() -> {
            connectionErrorLabel.setText(text);
        });
    }



    //chooseBoard methods

    /**
     * These methods set the player's choose of the right board
     */
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

    /**
     * This method check if the player has choosen the boardType and the right number of skulls (turns).
     * Then set the board and the skulls.
     */
    public void chooseBoardButton(){

        skull = Integer.valueOf(skullText.getText());
        Data.getInstance().setSkull(skull);

        Platform.runLater(() -> {

            if (boardType == null) {

                labelErrorSkull.setText("Board not chosen");

            } else if (skull > MAX_SKULLS || skull < MIN_SKULLS) {

                labelErrorSkull.setText("Wrong skull number");

            } else {
                try {
                    setBoard();
                } catch (IOException | InterruptedException e) {
                    Printer.err(e);
                }
                Stage stage = (Stage) enterButton.getScene().getWindow();
                stage.close();
            }
        });
    }


    /**
     * This method is launched when reloadButton is pressed, check what weapons the player wants to reload
     * @param mouseEvent when player clicks
     * @throws IOException throws IOException if fails to reload with reloadClient
     */
    public void reload(MouseEvent mouseEvent) throws IOException {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy()) {
            if (this.txtWeaponReload1.getText().equals(YES)) {
                reloadClient1();
            }
            if (this.txtWeaponReload2.getText().equals(YES)) {
                reloadClient2();
            }
            if (this.txtWeaponReload3.getText().equals(YES)) {
                reloadClient3();
            }
        }

        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)){

            if (this.txtWeaponReload1.getText().equals(YES)) {
                this.weaponReload1 = YES;
            }
            if (this.txtWeaponReload2.getText().equals(YES)) {
                this.weaponReload2 = YES;
            }
            if (this.txtWeaponReload3.getText().equals(YES)) {
                this.weaponReload3 = YES;
            }

            reloadClientTwoActions();
        }

        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){

            if (this.txtWeaponReload1.getText().equals(YES)) {
                this.weaponReload1 = YES;
            }
            if (this.txtWeaponReload2.getText().equals(YES)) {
                this.weaponReload2 = YES;
            }
            if (this.txtWeaponReload3.getText().equals(YES)) {
                this.weaponReload3 = YES;
            }

            reloadClientOneAction();
        }

        Stage stage = (Stage) buttonReload.getScene().getWindow();
        stage.close();
    }


    //choosePowerup methods

    /**
     * These methods are used for the choose of the powerups
     * @throws IOException
     */
    public void choosePowerup1() throws IOException {

        Data.getInstance().setPowerup(2);
        handleCloseAction1();
        try {
            setPowerup();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    public void choosePowerup2() throws IOException {

        Data.getInstance().setPowerup(1);
        handleCloseAction2();
        try {
            setPowerup();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * These methods helps to close the powerups stages
     */
    public void handleCloseAction1() {
        Stage stage = (Stage) powerupImg1.getScene().getWindow();
        stage.close();
    }

    public void handleCloseAction2() {
        Stage stage = (Stage) powerupImg2.getScene().getWindow();
        stage.close();
    }


    //viewInterface methods

    @Override
    public void start() {

    }

    /**
     *
     * @param playerController the PlayerController that will be set.
     */
    @Override
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * This notify receive only one message from the server.
     * @param message a message.
     */
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

    /**
     * This method set the LabelStatement on the mainBoard with Final Frenzy and disable reload button
     */
    private void notifyFinalFrenzy() {
        Platform.runLater(() ->{
            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.FINAL_FRENZY);
            labelReload.setDisable(true);
            bannerReload.setDisable(true);
        });
    }

    /**
     * This method set the LabelStatement on the mainBoard when is not your turn
     */
    private void notifyNotTurn() {
        Platform.runLater(() ->{
            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.NOT_TURN);
        });
    }

    /**
     * This method set the LabelStatement on the mainBoard when your turn ends
     */
    @FXML
    private void notifyEndTurn() {
        Platform.runLater(() ->{
            guiHandler = Data.getInstance().getGuiHandler();
            guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.END_TURN);
            guiHandler.disableButtons();
        });
    }

    /**
     * These notify takes a message and the outcome of the action from the server.
     * @param message a message.
     * @param outcome the outcome of the action.
     */
    @Override
    public void notify(Message message, Outcome outcome) {
        switch (message) {
            case NEW_TURN:
                try {
                    notifyNewTurn(outcome);
                } catch (Exception e) {
                    Printer.err(e);
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

    /**
     * This method launch DiscardPowerUp.fxml when a player has to respawn, he needs to discard a powerup
     * @param outcome if need to respawn or not
     */
    public void notifyRespawn(Outcome outcome) {
        Platform.runLater(() ->{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiscardPowerUpRespawn.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                Printer.err(e);
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

    /**
     * This method set the powerup's images on the DiscardPowerup.fxml
     */
    private void setPowerupImageRespawn() {

        playerController = Data.getInstance().getPlayerController();
        List<PowerupCard> powerupRespawn = playerController.getPlayer().getPowerups();

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

    /**
     * This method show if the player has reloaded or not a weapon
     * @param outcome if reloaded or not
     */
    private void notifyReload(Outcome outcome) {
        playerController = Data.getInstance().getPlayerController();
        Platform.runLater(() -> {
            if (outcome.equals(Outcome.RIGHT)) {
                setLabelStatement(StringCLI.SERVER + StringCLI.SPACE + playerController.getWeapon() + StringCLI.SPACE + StringCLI.RELOADED);
                guiHandler = Data.getInstance().getGuiHandler();
                guiHandler.disableButtonWhenReload();

            } else {
                setLabelStatement(StringCLI.SERVER + StringCLI.SPACE + playerController.getWeapon() + StringCLI.SPACE + StringCLI.NOT_RELOADED);
            }
        });
    }

    /**
     * This method notify on the mainBoard if the dropWeapon has succeded or not, depends on outcome
     * @param outcome if dropped or not
     */
    private void notifyDropWeapon(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();
        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.WEAPON_DROP + StringCLI.SPACE + StringCLI.DROPPED);
            }else{
                guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.WEAPON_DROP + StringCLI.SPACE + StringCLI.NOT_DROPPED);
            }
        });
    }

    /**
     * This method notify on the mainBoard if the dropPowerup has succeded or not, depends on outcome
     * @param outcome if dropped or not
     */
    private void notifyDropPowerup(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.DROPPED);
            }else{
                guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.NOT_DROPPED);
            }
        });
    }

    /**
     * This method notify on the mainBoard if the discardPowerup has succeded or not, depends on outcome
     * @param outcome if discarded or not
     */
    private void notifyDiscardPowerup(Outcome outcome) {
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT)){
                guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.DISCARDED);

            }else{
                guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.POWERUP_DROP + StringCLI.SPACE + StringCLI.NOT_DISCARDED);
            }
        });
    }

    /**
     * This method is used when a player is trying to reconnect to the match, if outcome is right it launches MapGUI.fxml
     * @param outcome if reconnected or not
     * @param object name of the player that reconnects
     */
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
                    Printer.err(e);
                }

                client = Data.getInstance().getClient();
                try {
                    Data.getInstance().setPlayerController(client.getPlayerController());
                } catch (RemoteException e) {
                    Printer.err(e);
                }

                guiHandler = loader.getController();
                guiHandler.setMapImage();
                //guiHandler.addWeapon();
                guiHandler.setLabelTurn();
                guiHandler.setLabelMainPlayer();
                guiHandler.setPlayerImg();

                Data.getInstance().setGuiHandler(guiHandler);

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 1189, 710));
                stage.setTitle("Adrenaline's Board");
                stage.show();

                Thread thread = new Thread(this::checkPosition);
                thread.setDaemon(true);
                thread.start();

                stage.setResizable(true);

                disableButtons();
                guiHandler.setLabelStatement(StringCLI.SERVER + object + StringCLI.SPACE + StringCLI.RECONNECTED); //gia presente
                this.startedGame++;

                MouseEvent mouseDisconnect = null;
                stage.setOnCloseRequest(event -> disconnect(mouseDisconnect));

            }else{
                guiHandler.setLabelStatement(StringCLI.SERVER + object + StringCLI.SPACE + StringCLI.RECONNECTED);

                if(playerController.isFinalFrenzy()){
                    labelReload.setDisable(true);
                    bannerReload.setDisable(true);
                }
            }
        });
    }

    /**
     * Set the color of the player on the mainBoard
     */
    private void setPlayerImg() {
        playerController = Data.getInstance().getPlayerController();
        Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(playerController.getPlayer().getColor()) + ".png");

        Platform.runLater(() ->{
            playerColorImage.setImage(image);
        });
    }

    /**
     * This method set the mainPlayerLabel with the right name's player
     */
    private void setLabelMainPlayer() {
        playerController = Data.getInstance().getPlayerController();

        Platform.runLater(() ->{
            mainPlayerLabel.setText(playerController.getPlayer().getUsername());
        });
    }

    /**
     * This method notify if powerup is used with success or not
     * @param outcome if used or not the powerup
     */
    private void notifyPowerup(Outcome outcome) {
        playerController = Data.getInstance().getPlayerController();
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            if(outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)){
                guiHandler.setLabelStatement(StringCLI.SERVER + playerController.getPowerup() + StringCLI.SPACE + StringCLI.USED);
            }else if(outcome.equals(Outcome.WRONG)){
                guiHandler.setLabelStatement(StringCLI.SERVER + playerController.getPowerup() + StringCLI.SPACE + StringCLI.NOT_USED);
            }
        });
    }

    /**
     * This method notify if a player shot or not with his weapon
     * @param outcome if shot or not
     */
    private void notifyShoot(Outcome outcome) {
        playerController = Data.getInstance().getPlayerController();
        guiHandler = Data.getInstance().getGuiHandler();
        Platform.runLater(() -> {
            switch(outcome){
                case RIGHT:
                    guiHandler.setLabelStatement(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.SHOT);
                    break;
                case ALL:
                    guiHandler.setLabelStatement(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.SHOT);
                    break;
                case WRONG:
                    guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.NOT_SHOT);
                    break;
                default:
                    guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.NOT_SHOT);
                    break;
            }
        });
    }

    /**
     * This method notify if the player has grabbed or not weapons or ammo
     * @param outcome if grabbed or not
     */
    private void notifyGrab(Outcome outcome) {
        playerController = Data.getInstance().getPlayerController();
        guiHandler = Data.getInstance().getGuiHandler();
        if(outcome.equals(Outcome.RIGHT)){
            guiHandler.setLabelStatement(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.GRABBED);
            resetMoveGrab();
        }else if(outcome.equals(Outcome.ALL)){
            guiHandler.setLabelStatement(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.USER_GRABBED);
            resetMoveGrab();
        }else{
            guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.NOT_GRABBED);
            resetMoveGrab();
        }
    }

    /**
     * This method notify if the player's move has succeded or not
     * @param outcome if movement is done or failed
     */
    private void notifyMovement(Outcome outcome) {
        playerController = Data.getInstance().getPlayerController();
        guiHandler = Data.getInstance().getGuiHandler();
        if (outcome.equals(Outcome.RIGHT) || outcome.equals(Outcome.ALL)) {
            guiHandler.setLabelStatement(StringCLI.SERVER + playerController.getCurrentPlayer() + StringCLI.SPACE + StringCLI.MOVED);
            guiHandler.resetMovement();

        } else {
            guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.NOT_MOVED);
            guiHandler.resetMovement();
        }
    }

    /**
     * This method helps to set labelStatusPlayer when is called
     * @param string various strings
     */
    private void setLabelStatement(String string) {
        guiHandler = Data.getInstance().getGuiHandler();
        Platform.runLater(() ->{
            guiHandler.labelStatusPlayer.setVisible(true);
            guiHandler.labelStatusPlayer.setText(string);
        });
    }

    /**
     * This method loads if outcome is right ChooseBoard.fxml that is created for choosing the BoardType and the Skulls' number
     * If outcome is wrong, it launches a Waiting Popup
     * @param outcome if you have to choose board or not
     */
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
                    Printer.err(e);
                }

            }else{
                try {

                    FXMLLoader popupBoard = new FXMLLoader(getClass().getClassLoader().getResource("Boardwaiting.fxml"));
                    Parent pop = popupBoard.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(pop, 271, 153));
                    stage.setTitle("Board choosing...");
                    stage.show();

                    PauseTransition delay = new PauseTransition(Duration.millis(Config.BOARD_TYPE_TIME));
                    delay.setOnFinished( event -> stage.close() );
                    delay.play();

                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        });
    }

    /**
     * This method is launched when there is a new turn. The first time executed it loads MapGUI.fxml,
     * The next times executed enables if outcome is right or disables the buttons if outcome is wrong
     * @param outcome if is new turn or not
     * @throws Exception if fails to load MapGUI.fxml
     */
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
                        Printer.err(e);
                    }

                    playerController = Data.getInstance().getPlayerController();

                    guiHandler = loader.getController();
                    guiHandler.setMapImage();
                    //guiHandler.addWeapon();
                    guiHandler.setLabelTurn();
                    guiHandler.setLabelMainPlayer();
                    guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.NEW_TURN);
                    guiHandler.setPlayerImg();

                    Data.getInstance().setGuiHandler(guiHandler);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 1189, 710));
                    stage.setTitle("Adrenaline's Board");
                    stage.show();

                    stage.setResizable(true);

                    Thread thread = new Thread(this::checkPosition);
                    thread.setDaemon(true);
                    thread.start();

                    this.startedGame++;

                    MouseEvent mouseDisconnect = null;
                    stage.setOnCloseRequest(event -> disconnect(mouseDisconnect));

                }else{
                    guiHandler.enableButtons();
                    guiHandler.setLabelTurn();
                    guiHandler.setLabelStatement(StringCLI.SERVER + StringCLI.NEW_TURN);

                    if(playerController.isFinalFrenzy()){
                        labelReload.setDisable(true);
                        bannerReload.setDisable(true);
                    }
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
                        Printer.err(e);
                    }

                    playerController = Data.getInstance().getPlayerController();

                    guiHandler = loader.getController();
                    guiHandler.setMapImage();
                    //guiHandler.addWeapon();
                    guiHandler.setLabelTurn();
                    guiHandler.setLabelMainPlayer();
                    guiHandler.setPlayerImg();

                    Data.getInstance().setGuiHandler(guiHandler);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 1189, 710));
                    stage.setTitle("Adrenaline's Board");
                    stage.show();
                    stage.setResizable(true);

                    Thread thread = new Thread(this::checkPosition);
                    thread.setDaemon(true);
                    thread.start();


                    guiHandler.disableButtons();
                    this.startedGame++;

                    MouseEvent mouseDisconnect = null;
                    stage.setOnCloseRequest(event -> disconnect(mouseDisconnect));

                }else{
                    guiHandler.disableButtons();
                    guiHandler.setLabelTurn();

                    if(playerController.isFinalFrenzy()){
                        labelReload.setDisable(true);
                        bannerReload.setDisable(true);
                    }
                }
            }
        });
    }

    /**
     * This method sets the right skulls number and changes the skulls to damages if is the case
     */
    public void setSkulls() {

        playerController = Data.getInstance().getPlayerController();
        int skulls = playerController.getKillshotTrack().size();
        List<Token> killShot = playerController.getKillshotTrack();
        Image imageSkull = new Image("boardElem/skull.png");

        for (int i = 0; i < skulls; i++) {
            if(killShot.get(i).getFirstColor().equals(TokenColor.SKULL)) {
                addImgOnKillshot(imageSkull, i, 0);

            } else if(!killShot.get(i).getFirstColor().equals(TokenColor.SKULL)){
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(killShot.get(i).getFirstColor()) + ".png");
                removeSkullImgOnKillshot(i, 0);
                addImgOnKillshot(image, i, 0);
            }

            if(killShot.get(i).getSecondColor().equals(TokenColor.SKULL)){
                addImgOnKillshot2(imageSkull, i, 0);
            } else if(!killShot.get(i).getSecondColor().equals(TokenColor.SKULL) && !killShot.get(i).getSecondColor().equals(TokenColor.NONE)){
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(killShot.get(i).getSecondColor()) + ".png");
                addImgOnKillshot2(image, i, 0);
            }
        }
    }

    /**
     * This method helps to add elements on the KillShotTrack2
     * @param image of damage
     * @param col represents column
     * @param row represents row
     */
    private void addImgOnKillshot2(Image image, int col, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(STANDARD_HEIGHT);
        imv.setFitHeight(STANDARD_HEIGHT);

        Platform.runLater(() ->{
            gridKillshotTrack2.add(imv, col, row);
        });
    }

    /**
     * This method removes all the elements on the gridSkulls
     * @param col represents columns
     * @param row represents row
     */
    private void removeSkullImgOnKillshot(int col, int row) {
        Platform.runLater(() ->{
            gridSkulls.getChildren().remove(col, row);
        });
    }

    /**
     * This method helps to add the proper Image to the gridSkulls (killshotTrack)
     * @param image of the skulls or damage
     * @param col represents columns
     * @param row represents row
     */
    public void addImgOnKillshot(Image image, int col, int row){
        ImageView imv = new ImageView(image);
        imv.setFitWidth(STANDARD_HEIGHT);
        imv.setFitHeight(STANDARD_HEIGHT);

        Platform.runLater(() ->{
            gridSkulls.add(imv, col, row);
        });
    }

    /**
     * notify that receives three elements from the server
     * @param message a message.
     * @param outcome the outcome of the action.
     * @param object an object.
     */
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
                    Printer.err(e);
                }
                break;
            case END_GAME:
                try {
                    notifyEndGame((Map<TokenColor, Integer>) object);
                } catch (IOException e) {
                    Printer.err(e);
                }
                break;
            case RECONNECTION:
                notifyReconnection(outcome, (String) object);
                break;
            default:
                break;
        }
    }

    /**
     * This method takes the score of the match when is ended the game
     * @param object map that represents the score
     * @throws IOException if is not launched notify score
     */
    private void notifyEndGame(Map<TokenColor, Integer> object) throws IOException {
        disableButtons();
        notifyScore(object);
    }

    /**
     * NotifyScore displays the Score of the match, it takes a Map with TokenColor and Integer for every players
     * @param object map that represents the score
     * @throws IOException when fails to load ScorePopup.fxml
     */
    private void notifyScore(Map<TokenColor, Integer> object) throws IOException {
        Platform.runLater(() ->{
            FXMLLoader loader3 = new FXMLLoader(getClass().getClassLoader().getResource("ScorePopup.fxml"));
            Parent root = null;
            try {
                root = loader3.load();
            } catch (IOException e) {
                Printer.err(e);
            }

            scorePopup = loader3.getController();
            scorePopup.setScore(object);

            Stage stage3 = new Stage();
            stage3.setScene(new Scene(root, 411, 311));
            stage3.setTitle("Score");
            stage3.show();
        });
    }

    /**
     * This method receive three parameters for connecting to the server with RMI
     * @param name name of the player
     * @param host host of the player
     * @param color color of the player
     * if doens't find the server's host it sets the error on the Login
     */
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

    /**
     * This method receive three parameters for connecting to the server with Socket
     * @param name name of the player
     * @param host host of the player
     * @param color color of the player
     * if doens't find the server's host it sets the error on the Login
     */
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

    /**
     * This method check and shows if player has written the right info when he is trying to connect from the Login
     * @param outcome if connected or not
     * @param object username of the Player
     */
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

    /**
     * This method is triggered when the player has written wrong color
     * @param outcome if has written wrong color
     * @param object wrong color
     */
    private void notifyColor(Outcome outcome, TokenColor object) {
        Platform.runLater(() -> {
            switch (outcome) {
                case WRONG:
                    setErrorText("Invalid color");
            }
        });
    }

    /**
     * This method load Popup.fxml when a player has disconnected
     * @param outcome if is disconnected or not
     * @param object name of the Player has disconnected
     */
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
                        Printer.err(e);
                    }
                });
                break;
            }
            default:
                break;
        }
    }

    /**
     * This method launches ChoosePowerup.fxml, that allows to choose a PowerUp
     * @param object list of powerup cards
     */
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
                Printer.err(e);
            }
        });
    }

    /**
     * This method allows to set the right powerup Img
     * @param powerup list of powerups on the choosePowerup window
     */
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

    /**
     * Standard initialize method
     * @param url location used to resolve relative paths for roots objects
     * @param resourceBundle used to localize the root object or null if it was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * This method checks and sets various method for working the gui properly (used on the MapGUI.fxml), it sleeps every 5 seconds
     */
    @FXML
    private void checkPosition() {
        while(checkTurn){
            guiHandler = Data.getInstance().getGuiHandler();
            playerController = Data.getInstance().getPlayerController();

            Platform.runLater(() -> {
                guiHandler.removeSkulls();
                guiHandler.setSkulls();
                guiHandler.setLabelTurn();
                guiHandler.removeImg();
                guiHandler.placePlayers(playerController.getGameBoard().getArena());
                guiHandler.addAmmo();
                guiHandler.addWeapon();
                //guiHandler.removeWeapon();
            });

            try{

                Thread.sleep(TIME_UPDATING_MAIN_BOARD);

            } catch (InterruptedException e) {
                Printer.err(e);
            }
        }
    }

    /**
     * This method remove the skulls on the GridSkulls
     */
    private void removeSkulls() {
        Platform.runLater(()->{
            gridSkulls.getChildren().clear();
        });
    }

    /**
     * This method adds the right ammo's images on the gridPanes of the mainBoard
     */
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

    /**
     *This method removes all the ammos
     */
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

    /**
     * This method helps to add Weapons on the mainBoard on the right SpawnPoints
     */
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

    /**
     * This method helps to remove all the elements on the gridPanes on the mainBoard, both players and ammos
     */
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

    /**
     * This method set the LabelTurn with the name of the current Players
     */
    public void setLabelTurn() {
        Platform.runLater(() -> {
            guiHandler = Data.getInstance().getGuiHandler();
            playerController = Data.getInstance().getPlayerController();
            guiHandler.playerTurnLabel.setText(playerController.getCurrentPlayer());
        });
    }

    /**
     * This method sets the right boardType choosen by the player
     * @throws IOException when it doens't set the boardType
     * @throws InterruptedException when it can't complete the action
     */
    public void setBoard() throws IOException, InterruptedException {
        try {
            client = Data.getInstance().getClient();
            this.client.board(Data.getInstance().getBoardType() + 1, Data.getInstance().getSkull());
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method set the mapImage of the mainBoard
     */
    public void setMapImage() {
        playerController = Data.getInstance().getPlayerController();
        Platform.runLater(() -> {
            Integer mapNum = Converter.fromBoardTypeToInt(playerController.getGameBoard().getType());
            mapImage.setImage(new Image("boardImg/" + mapNum +".png"));
        });
    }

    /**
     * This method allows the player to choose the powerup
     * @throws IOException if it doesn't choose the powerup
     */
    public void setPowerup() throws IOException{
        try {
            this.client = Data.getInstance().getClient();
            this.client.choose(Data.getInstance().getPowerup());
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * Standard main method for being launched from Client
     * @param args standard args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }


    /**
     * These methods are used for set the move on the mainBoard
     * @param event when player clicks
     */
    @FXML
    private void moveUp(MouseEvent event){
        saveMovement("up");
        setLabelMovement();
    }

    @FXML
    private void moveDown(MouseEvent event) {
        saveMovement("down");
        setLabelMovement();
    }

    @FXML
    private void moveLeft(MouseEvent event){
        saveMovement("left");
        setLabelMovement();
    }

    @FXML
    private void moveRight(MouseEvent event) {
        saveMovement("right");
        setLabelMovement();
    }

    /**
     * This method takes movements from the players and it displays them in showMoveLabel
     */
    private void setLabelMovement() {
        for(int i = 0; i < movement.length; i++) {
            if(this.movement[3] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText(DOUBLE_SPACE + this.movement[0] + DOUBLE_SPACE + this.movement[1] + DOUBLE_SPACE + this.movement[2] + DOUBLE_SPACE + this.movement[3]);
                });
            }else if(this.movement[2] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText(DOUBLE_SPACE + this.movement[0] + DOUBLE_SPACE + this.movement[1] + DOUBLE_SPACE + this.movement[2]);
                });
            } else if(this.movement[1] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText(DOUBLE_SPACE + this.movement[0] + DOUBLE_SPACE + this.movement[1]);
                });
            } else if(this.movement[0] != null){
                Platform.runLater(() ->{
                    labelShowMove.setText(DOUBLE_SPACE + this.movement[0]);
                });
            }
        }
    }

    /**
     * This method save the movement done by the players
     * @param move receive move for MoveAction
     */
    public void saveMovement(String move){
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION))) {
            if (countMove < MAX_MOVEMENT) {

                this.movement[countMove] = move;
                this.countMove++;
            }
        }
        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS)) {
            if(countMove < MAX_MOVEMENT + 1){

                this.movement[countMove] = move;
                this.countMove++;
            }
        }
    }

    /**
     * This method reset the movement array
     */
    public void resetMovement(){
        for(int i = 0; i < MAX_MOVEMENT + 1; i++){
            this.movement[i] = null;
        }

        this.countMove = 0;
    }

    /**
     * This method moves the player, it sends to the server the right movement entered by the player
     * @throws IOException if it doesn't succed the move action
     */
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

    /**
     * It loads ReloadPopup.fxml that is used for reloading the weapons
     */
    public void reloadPopup(){
        Platform.runLater(() ->{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ReloadPopup.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                Printer.err(e);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 338, 208));
            stage.setTitle("Reload Popup");
            stage.show();

        });
    }

    /**
     * This method reload the first weapon of the player
     */
    public void reloadClient1(){
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        try {
            client.reload(Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method reload the second weapon of the player
     */
    public void reloadClient2(){
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();

        try {
            client.reload(Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method reload the third weapon of the player
     */
    public void reloadClient3(){
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();

        try {
            client.reload(Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method check which weapons the player wants to reload, then it reloads the desired weapons if possible,
     * also moves the player if there were moveReload moves
     * then launches showWeapon.fxml
     * @throws IOException if moveAndReload.fxml fails
     */
    private void reloadClientTwoActions() throws IOException {
        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        String[] moveRel = Data.getInstance().getMoveRel();

        if(moveRel[0] != null) {
            if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (this.weaponReload1.equals(YES) && this.weaponReload2 .equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));

                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(Converter.fromStringToDirection(moveRel[0]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }else{
            if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));

                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                try {
                    client.moveAndReload(null);
                } catch (IOException e) {
                    Printer.err(e);
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
                Printer.err(e);
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

    /**
     * This method allows to move and reload the player if is in the FinalFrenzy and has one action to do
     * then launches showWeapon.fxml
     */
    public void reloadClientOneAction(){

        playerController = Data.getInstance().getPlayerController();
        client = Data.getInstance().getClient();
        String [] moveRel = Data.getInstance().getMoveRel();
        if(playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)) {
            if (moveRel[0] != null && moveRel[1] != null) {
                if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));

                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.fromStringToDirection(moveRel[1]));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else if(moveRel[1] == null){
                if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));

                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]), Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(Converter.fromStringToDirection(moveRel[0]));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }
            }else{

                if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(1).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));

                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null, Converter.weaponNameInvert(playerController.getWeapons().get(0).getName()), Converter.weaponNameInvert(playerController.getWeapons().get(2).getName()));
                    } catch (IOException e) {
                        Printer.err(e);
                    }
                }

                if (!this.weaponReload1.equals(YES) && !this.weaponReload2.equals(YES) && !this.weaponReload3.equals(YES)) {
                    try {
                        client.moveAndReload(null);
                    } catch (IOException e) {
                        Printer.err(e);
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
                    Printer.err(e);
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

    /**
     * This method disable the buttons when a player reloaded with success
     */
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

    /**
     * This method sends to the server the client has ended his turn
     */
    public void endTurn(){
        try {

            client = Data.getInstance().getClient();
            this.client.endTurn();
            setLabelTurn();
        } catch (IOException e1) {
            Printer.err(e1);
        }
    }

    /**
     * One of the main methods of the mainBoard, it receives the game's arena and check for every position if there are players,
     * then displays players on the board calling addPlayer method
     * @param arena of the current match
     */
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

    /**
     * This method helps to addPlayer to a specific grid, it takes gridPane, Image with color of the player and right position thanks to row and index that represents the coloumn
     * @param grid grid that contains players or ammos
     * @param image image of players or ammos
     * @param index represents column of the grid
     * @param row represents row of the grid
     */
    private void addPlayer(GridPane grid, Image image, Integer index, Integer row) {
        
        if(index == 3){
            index = 0;
            row++;
        }
        if(index > 3){
            index = 1;
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

    /**
     * This method launches GrabMove.fxml
     */
    public void grab(){
        Platform.runLater(() ->{
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GrabMove.fxml"));

            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                Printer.err(e);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 256, 222));
            stage.setTitle("Grab Popup");
            stage.show();

            stage.setResizable(false);
            stage.setOnCloseRequest(event -> resetMoveGrab());
        });
    }

    /**
     * These methods set all the moves for moveAndGrab Action
     * @param mouseEvent when the player clicks
     */
    public void moveUpGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();
        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("up");
            labelShowMoveGrab.setText(DOUBLE_SPACE + "up");
        } else {
            saveMovementFinalFrenzy("up");
        }
    }

    public void moveRightGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("right");
            labelShowMoveGrab.setText(DOUBLE_SPACE + "right");
        } else {
            saveMovementFinalFrenzy("right");
        }
    }

    public void moveDownGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("down");
            labelShowMoveGrab.setText(DOUBLE_SPACE + "down");
        } else {
            saveMovementFinalFrenzy("down");
        }
    }

    public void moveLeftGrab(MouseEvent mouseEvent) {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isFinalFrenzy() && playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT) ) {
            Data.getInstance().setMoveGrab("left");
            labelShowMoveGrab.setText(DOUBLE_SPACE + "left");
        } else {
            saveMovementFinalFrenzy("left");
        }
    }


    /**
     * This method save the movement for moveGrab when there is final frenzy
     * @param movement received for moveGrab action
     */
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

    /**
     * This method checks the moves from the movesGrab and grab the right ammo, if there is a weapon it launches ChooseWeapon.fxml
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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
                            Printer.err(e);
                        }
                    } else {
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            Printer.err(e);
                        }

                        guiHandler = loader.getController();

                        Data.getInstance().setMoveGrab(null);

                        List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                        guiHandler.setWeaponInvisible();
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
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }

                                Data.getInstance().setMoveGrab("up");
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
                                guiHandler.setWeaponImage(weapon);


                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
                                stage.setTitle("Choose Weapon");
                                stage.show();
                            }
                        }else{
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    client.grab(0);
                                } catch (IOException e) {
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }

                                Data.getInstance().setMoveGrab(null);
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
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
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }
                                guiHandler = loader.getController();

                                Data.getInstance().setMoveGrab("down");

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
                                guiHandler.setWeaponImage(weapon);


                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
                                stage.setTitle("Choose Weapon");
                                stage.show();
                            }
                        }else{
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    client.grab(0);
                                } catch (IOException e) {
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }

                                Data.getInstance().setMoveGrab(null);
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
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
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }
                                guiHandler = loader.getController();

                                Data.getInstance().setMoveGrab("left");

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
                                guiHandler.setWeaponImage(weapon);


                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
                                stage.setTitle("Choose Weapon");
                                stage.show();
                            }
                        }else{
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    client.grab(0);
                                } catch (IOException e) {
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }

                                Data.getInstance().setMoveGrab(null);
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
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
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }

                                Data.getInstance().setMoveGrab("right");
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
                                guiHandler.setWeaponImage(weapon);

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root, 496, 269));
                                stage.setTitle("Choose Weapon");
                                stage.show();
                            }
                        }else{
                            if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                                try {
                                    client.grab(0);
                                } catch (IOException e) {
                                    Printer.err(e);
                                }
                            } else {
                                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    Printer.err(e);
                                }

                                Data.getInstance().setMoveGrab(null);
                                guiHandler = loader.getController();

                                List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                                guiHandler.setWeaponInvisible();
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
                        Printer.err(e);
                    }
                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){

                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                    guiHandler = loader.getController();

                    Data.getInstance().setMoveGrab(null);

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponInvisible();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }

            } else if(this.moveFrenzyTwoActions[1] == null) {
                Direction first = Converter.fromStringToDirection(this.moveFrenzyTwoActions[0]);
                if(playerController.getGameBoard().canMove(playerController.getPlayer(), first)){
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyTwoActions[0]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyTwoActions[0]);
                }else{
                    this.moveFrenzyTwoActions[0] = null;
                }
                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyTwoActions[0]));
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponInvisible();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }
            } else{
                Direction first = Converter.fromStringToDirection(this.moveFrenzyTwoActions[0]);
                Direction second = Converter.fromStringToDirection(this.moveFrenzyTwoActions[1]);
                if(playerController.getGameBoard().canMove(playerController.getPlayer(), first, second)){
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyTwoActions[0]);
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyTwoActions[1]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyTwoActions[0]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyTwoActions[1]);
                }else{
                    this.moveFrenzyTwoActions[0] = null;
                    this.moveFrenzyTwoActions[1] = null;
                }
                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyTwoActions[0]), Converter.fromStringToDirection(this.moveFrenzyTwoActions[1]));
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponInvisible();
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
                        Printer.err(e);
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponInvisible();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }
            } else if(this.moveFrenzyOneActions[1] == null) {
                Direction first = Converter.fromStringToDirection(this.moveFrenzyOneActions[0]);
                if(playerController.getGameBoard().canMove(playerController.getPlayer(), first)){
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[0]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[0]);
                }else{
                    this.moveFrenzyOneActions[0] = null;
                }
                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyOneActions[0]));
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponInvisible();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }

            } else if(this.moveFrenzyOneActions[2] == null){
                Direction first = Converter.fromStringToDirection(this.moveFrenzyOneActions[0]);
                Direction second = Converter.fromStringToDirection(this.moveFrenzyOneActions[1]);
                if(playerController.getGameBoard().canMove(playerController.getPlayer(), first, second)){
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[0]);
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[1]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[0]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[1]);
                }else{
                    this.moveFrenzyOneActions[0] = null;
                    this.moveFrenzyOneActions[1] = null;
                }
                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyOneActions[0]), Converter.fromStringToDirection(this.moveFrenzyOneActions[1]));
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponInvisible();
                    guiHandler.setWeaponImage(weapon);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root, 496, 269));
                    stage.setTitle("Choose Weapon");
                    stage.show();
                }

            } else {
                Direction first = Converter.fromStringToDirection(this.moveFrenzyOneActions[0]);
                Direction second = Converter.fromStringToDirection(this.moveFrenzyOneActions[1]);
                Direction third = Converter.fromStringToDirection(this.moveFrenzyOneActions[2]);
                if(playerController.getGameBoard().canMove(playerController.getPlayer(), first, second, third)){
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[0]);
                    x = x + Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[1]);
                    x = x +  Converter.fromStringDirToIntegerX(this.moveFrenzyOneActions[2]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[0]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[1]);
                    y = y + Converter.fromStringDirToIntegerY(this.moveFrenzyOneActions[2]);
                }else{
                    this.moveFrenzyOneActions[0] = null;
                    this.moveFrenzyOneActions[1] = null;
                    this.moveFrenzyOneActions[2] = null;
                }
                if (!playerController.getGameBoard().getArena()[x][y].isSpawn()) {
                    try {
                        this.client.grab(0, Converter.fromStringToDirection(this.moveFrenzyOneActions[0]), Converter.fromStringToDirection(this.moveFrenzyOneActions[1]), Converter.fromStringToDirection(this.moveFrenzyOneActions[2]));
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                } else if(playerController.getGameBoard().getArena()[x][y].isSpawn()){
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ChooseWeapon.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        Printer.err(e);
                    }

                    guiHandler = loader.getController();

                    List<WeaponCard> weapon = playerController.getGameBoard().getArena()[x][y].getWeapons();
                    guiHandler.setWeaponInvisible();
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

    /**
     * Set weapons on the chooseWeapon invisible
     */
    private void setWeaponInvisible() {
        this.firstWeapon.setVisible(false);
        this.secondWeapon.setVisible(false);
        this.thirdWeapon.setVisible(false);
    }

    /**
     * This method takes the lists of player's weapons and it sets the right weapon img
     * @param weapon lists of weapons
     */
    @FXML
    private void setWeaponImage(List<WeaponCard> weapon) {
        Platform.runLater(() ->{


            if(weapon.size() >= 1) {
                if (weapon.get(0) != null) {
                    this.firstWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(0).getName()) + ".png"));
                    this.firstWeapon.setVisible(true);
                }
            }
            if(weapon.size() >= 2) {
                if (weapon.get(1) != null) {
                    this.secondWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(1).getName()) + ".png"));
                    this.secondWeapon.setVisible(true);
                }
            }
            if(weapon.size() >= 3) {
                if (weapon.get(2) != null) {
                    this.thirdWeapon.setImage(new Image("weapon/" + Converter.weaponNameInvert(weapon.get(2).getName()) + ".png"));
                    this.thirdWeapon.setVisible(true);
                }
            }

        });
    }

    /**
     * this method is used for grabbing the first weapon img
     * @param mouseEvent when player clicks
     */
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
                    Printer.err(e);
                }

            } else {
                try {
                    this.client.grab(1);
                } catch (IOException e) {
                    Printer.err(e);
                }

            }

        } else if((!playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS))){
            if(moveFrenzyTwo[0] == null){

                try {
                    client.grab(1);
                } catch (IOException e) {
                    Printer.err(e);
                }

            } else if(moveFrenzyTwo[1] == null){
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyTwo[0]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else {
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyTwo[0]), Converter.fromStringToDirection(moveFrenzyTwo[1]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

        } else {
            if(moveFrenzyOne[0] == null){
                try {
                    client.grab(1);
                } catch (IOException e) {
                    Printer.err(e);
                }

            } else if(moveFrenzyOne[1] == null){
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyOne[0]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else if(moveFrenzyOne[2] == null) {
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else {
                try {
                    client.grab(1, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]), Converter.fromStringToDirection(moveFrenzyOne[2]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }


        Stage stage = (Stage) firstWeapon.getScene().getWindow();
        stage.close();
    }

    /**
     * this method is used for grabbing the second weapon img
     * @param mouseEvent when player clicks
     */
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
                    Printer.err(e);
                }

            } else {
                try {
                    this.client.grab(2);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        } else if((!playerController.isFinalFrenzy() && !playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS))){
            if(moveFrenzyTwo[0] == null){

                try {
                    client.grab(2);
                } catch (IOException e) {
                    Printer.err(e);
                }

            } else if(moveFrenzyTwo[1] == null){
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyTwo[0]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else {
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyTwo[0]), Converter.fromStringToDirection(moveFrenzyTwo[1]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

        } else {
            if(moveFrenzyOne[0] == null){

                try {
                    client.grab(2);
                } catch (IOException e) {
                    Printer.err(e);
                }

            } else if(moveFrenzyOne[1] == null){
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyOne[0]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else if(moveFrenzyOne[2] == null) {
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else {
                try {
                    client.grab(2, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]), Converter.fromStringToDirection(moveFrenzyOne[2]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }

        Stage stage = (Stage) firstWeapon.getScene().getWindow();
        stage.close();
    }

    /**
     * this method is used for grabbing the third weapon img
     * @param mouseEvent when player clicks
     */
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
                    Printer.err(e);
                }

            } else {
                try {
                    this.client.grab(3);
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        } else if((!playerController.isFinalFrenzy() && !playerController.getAdrenalineZone().equals(AdrenalineZone.DEFAULT)) || (playerController.isFinalFrenzy() && playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS))){
            if(moveFrenzyTwo[0] == null){

                try {
                    client.grab(3);
                } catch (IOException e) {
                    Printer.err(e);
                }

            } else if(moveFrenzyTwo[1] == null){
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyTwo[0]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else {
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyTwo[0]), Converter.fromStringToDirection(moveFrenzyTwo[1]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

        } else {
            if(moveFrenzyOne[0] == null){

                try {
                    client.grab(3);
                } catch (IOException e) {
                    Printer.err(e);
                }

            } else if(moveFrenzyOne[1] == null){
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyOne[0]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else if(moveFrenzyOne[2] == null) {
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            } else {
                try {
                    client.grab(3, Converter.fromStringToDirection(moveFrenzyOne[0]), Converter.fromStringToDirection(moveFrenzyOne[1]), Converter.fromStringToDirection(moveFrenzyOne[2]));
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }



        Stage stage = (Stage) firstWeapon.getScene().getWindow();
        stage.close();
    }

    /**
     * This method disable all the buttons that cannot be used if is not your turn
     */
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

    /**
     * This method enable all the buttons that can be used if is your turn
     */
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

    /**
     *This method launches the PlayerBoard.fxml with all the playerBoards
     * @param mouseEvent when player clicks
     */
    public void showDamageBoard(MouseEvent mouseEvent) {
        Platform.runLater(() ->{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("PlayerBoard.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                Printer.err(e);
            }

            playerboard = loader.getController();
            playerboard.setAll();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1130, 722));
            stage.setTitle("PlayerBoards");
            stage.show();

            stage.setResizable(true);
        });
    }

    /**
     * This method launches ShowWeapon.fxml that shows all the weapons and powerups that a player has
     * @param mouseEvent when player clicks
     */
    public void showWeapon(MouseEvent mouseEvent) {
        Platform.runLater(() ->{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ShowWeapon.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                Printer.err(e);
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

    /**
     * This method is useful for the ShowWeapon, it updates your powerups and your weapons
     */
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
                Printer.err(e);
            }
        }
    }

    /**
     * This method is used for setting labelLoade invisible
     */
    private void setLoadedLabelInvisible() {
        Platform.runLater(() ->{
            labelLoaded1.setVisible(false);
            labelLoaded2.setVisible(false);
            labelLoaded3.setVisible(false);
        });
    }

    /**
     * This method sets the correct images of the powerups on the ShowWeapon
     */
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

    /**
     * This method sets the correct images of the weapons on the ShowWeapon and it shows if the weapons are loaded or not
     */
    @FXML
    private void setWeaponHad() {

        playerController = Data.getInstance().getPlayerController();
        List<WeaponCard> weaponsHad = playerController.getWeapons();

        if(weaponsHad.size() == 1){
            Platform.runLater(() -> {

                this.firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                this.firstWeaponHad.setVisible(true);

            });

            labelLoaded1.setVisible(true);
            if(weaponsHad.get(0).isLoaded()){
                Platform.runLater(() ->{
                    setLabelWeaponLoaded1(LOADED);
                });
            } else {
                Platform.runLater(() ->{
                    setLabelWeaponLoaded1(UNLOADED);
                });
            }

        }else if(weaponsHad.size() == 2){
            Platform.runLater(() -> {

                this.firstWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(0).getName()) + ".png"));
                this.secondWeaponHad.setImage(new Image("weapon/" + Converter.weaponNameInvert(weaponsHad.get(1).getName()) + ".png"));
                this.firstWeaponHad.setVisible(true);
                this.secondWeaponHad.setVisible(true);
            });

            Platform.runLater(() -> {
                labelLoaded1.setVisible(true);
                labelLoaded2.setVisible(true);
                if (weaponsHad.get(0).isLoaded()) {
                    setLabelWeaponLoaded1(LOADED);
                } else {
                    setLabelWeaponLoaded1(UNLOADED);
                }
                if (weaponsHad.get(1).isLoaded()) {

                    setLabelWeaponLoaded2(LOADED);
                } else {
                    setLabelWeaponLoaded2(UNLOADED);
                }
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

            Platform.runLater(() -> {
                labelLoaded1.setVisible(true);
                labelLoaded2.setVisible(true);
                labelLoaded3.setVisible(true);
                if (weaponsHad.get(0).isLoaded()) {
                    setLabelWeaponLoaded1(LOADED);
                } else {
                    setLabelWeaponLoaded1(UNLOADED);
                }
                if (weaponsHad.get(1).isLoaded()) {
                    setLabelWeaponLoaded2(LOADED);
                } else {
                    setLabelWeaponLoaded2(UNLOADED);
                }
                if (weaponsHad.get(2).isLoaded()) {
                    setLabelWeaponLoaded3(LOADED);
                } else {
                    setLabelWeaponLoaded3(UNLOADED);
                }
            });
        }
    }

    /**
     * This method set labelLoaded1 of the first weapon
     * @param string contains load String
     */
    private void setLabelWeaponLoaded1(String string) {
        Platform.runLater(() ->{
            labelLoaded1.setText(string);
        });
    }

    /**
     * This method set labelLoaded2 of the second weapon
     * @param string contains load String
     */
    private void setLabelWeaponLoaded2(String string) {
        Platform.runLater(() ->{
            labelLoaded2.setText(string);
        });
    }

    /**
     * This method set labelLoaded3 of the third weapon
     * @param string contains load String
     */
    private void setLabelWeaponLoaded3(String string) {
        Platform.runLater(() ->{
            labelLoaded3.setText(string);
        });
    }

    /**
     * This method is used for shooting action, launch showWeapon method in the first case,
     * in the second launches MoveAndReload.fxml
     * @param mouseEvent when the player clicks
     */
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
                    Printer.err(e);
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 255, 234));
                stage.setTitle("Move and Reload Popup");
                stage.show();

                stage.setResizable(false);
            });
        }
    }

    /**
     * These methods set which weapon is chosen for shooting action
     * @param mouseEvent when the player clicks
     */
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

    /**
     * This method loads DataShoot.fxml that shows the data needed for shooting and allows to write down all the info for shooting
     */
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
                Printer.err(e);
            }

            guiHandler = loader.getController();
            guiHandler.setLabelWeapon(playerController.getWeapons().get(weaponNum).getName());

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 522, 538));
            stage.setTitle("Data Shoot");
            stage.show();
        });
        }
    }


    /**
     * This method sets the info needed for shooting with the choosen weapon
     * @param name name of the weapon
     */
    private void setLabelWeapon(String name) {
        String textWeapon = weaponInfoHandler.getInfoWeapon(name);
        Platform.runLater(() ->{
            labelInfoWeapon.setText(textWeapon);
        });
    }

    /**
     * This method sets and checks the data for shooting
     * @param mouseEvent when the player clicks
     */
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
            Printer.err(e);
        }

        Stage stage = (Stage) modeTxtField.getScene().getWindow();
        stage.close();
    }

    /**
     * This method disconnects the player
     * @param mouseEvent when the player clicks
     */
    public void disconnect(MouseEvent mouseEvent) {

        client = Data.getInstance().getClient();
        try {
            client.disconnect();
        } catch (RemoteException e) {
            Printer.err(e);
        }
    }

    /**
     * These methods set moves for MoveAndReload action
     * @param mouseEvent when the player clicks
     */
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

    /**
     * This method save the moves from the player and for the MoveAndReload action
     * @param move move of the moveAndReload action
     */
    private void setMoveRel(String move) {
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.TWO_ACTIONS) || playerController.getAdrenalineZone().equals(AdrenalineZone.SECOND)) {
            this.moveReload[0] = move;
            Printer.println("2963: " + this.moveReload[0]);
            //aggiunto
            Data.getInstance().setMoveRel(moveReload);

        } else if(playerController.getFinalFrenzyActions().equals(FinalFrenzyAction.ONE_ACTION)){
            if (this.countMoveRel < MAX_MOVEMENT - 1) {
                this.moveReload[this.countMoveRel] = move;
                this.countMoveRel++;
                Data.getInstance().setMoveRel(moveReload);
            }
        }
    }

    /**
     * This method launches ShowWeapon.fxml after the MoveAndReload action
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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
                    Printer.err(e);
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

    /**
     * This method is needed for moveAndReload if a player is in ZoneTwo phase
     * @throws IOException if fails to load fxml file
     */
    private void moveReloadZoneTwo() throws IOException {
        client = Data.getInstance().getClient();
        String[] moveRel = Data.getInstance().getMoveRel();

        if(moveRel[0] == null) {
            client.moveAndReload(null);
        }else {
            client.moveAndReload(Converter.fromStringToDirection(moveRel[0]));
        }
    }

    /**
     * This class set error if you move wrong on the MoveRel
     */
    public void showErrorMoveRel(){
        guiHandler = Data.getInstance().getGuiHandler();

        Platform.runLater(() ->{
            guiHandler.labelErrorMoveReload.setVisible(true);
        });
    }

    /**
     * these methods are used to set which powerup player wants to use and launches launchPowerUpData
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method checks and receives the data when a player is using a powerup
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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
                break;
            case "NEWTON":
                if(secondDir == null) {
                    client.powerup(Converter.powerupNameInvert(namePowerup), Converter.fromStringToTokenColor(victim), Color.NONE, -1, -1, Converter.fromStringToDirection(firstDir));
                } else{
                    client.powerup(Converter.powerupNameInvert(namePowerup), Converter.fromStringToTokenColor(victim), Color.NONE, -1, -1, Converter.fromStringToDirection(firstDir), Converter.fromStringToDirection(secondDir));
                }
                break;
            case "TAGBACK GRENADE":
                client.powerup(Converter.powerupNameInvert(namePowerup), Converter.fromStringToTokenColor(victim), Color.NONE, -1, -1);
                break;
            case "TELEPORTER":
                client.powerup(Converter.powerupNameInvert(namePowerup), TokenColor.NONE, Color.NONE, Integer.valueOf(x), Integer.valueOf(y));
                break;
            default:
                break;
        }

    }

    /**
     * This class loads DataPowerup.fxml, it contains all the powerup Data that the player has to insert
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method sets the info of the powerup must be specified to use it
     */
    private void setLabelPowerup() {
        playerController = Data.getInstance().getPlayerController();
        String namePowerup = playerController.getPowerups().get(Data.getInstance().getNumPowerup()).getName();
        String infoPowerup = weaponInfoHandler.getInfoPowerUp(namePowerup);

        Platform.runLater(() ->{
            labelInfoPowerup.setText(infoPowerup);
        });
    }

    /**
     * This method show the weapon Red1
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Red2
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Red3
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Yellow1
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Yellow2
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Yellow3
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Blue1
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Blue2
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method show the weapon Blue3
     * @param mouseEvent when player clicks
     * @throws IOException if fails to load fxml file
     */
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

    /**
     * This method set the image of the weapons
     * @param image weapon's image
     */
    public void setImageWeapon(Image image){
        Platform.runLater(() ->{
            this.imageWeapon.setImage(image);
        });
    }

    /**
     * This method launches AmmoGrids.fxml that shows all the ammos player have
     */
    public void launchAmmo(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AmmoGrids.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            Printer.err(e);
        }

        ammoGUI = loader.getController();
        ammoGUI.setAmmo();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 608, 266));
        stage.setTitle("Ammo");
        stage.show();
    }

    /**
     * This method drop the first weapon
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to drop weapon
     */
    public void dropFirstWeapon(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getWeapons().size() == 3) {
            client.dropWeapon(1);
        }
    }

    /**
     * This method drop the second weapon
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to drop weapon
     */
    public void dropSecondWeapon(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropWeapon(2);
        }
    }


    /**
     * This method drop the third weapon
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to drop weapon
     */
    public void dropThirdWeapon(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropWeapon(3);
        }
    }


    /**
     * This method drops the first powerup
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to drop powerup
     */
    public void dropFirstPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropPowerup(1);
        }
    }

    /**
     * This method drops the second powerup
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to drop powerup
     */
    public void dropSecondPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropPowerup(2);
        }
    }

    /**
     * This method drops the third powerup
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to drop powerup
     */
    public void dropThirdPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.dropPowerup(3);
        }
    }

    /**
     * This method discards the first powerup
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to discard powerup
     */
    public void discardFirstPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() >= 1) {
            client.discardPowerup(1);
        }
    }

    /**
     * This method discards the second powerup
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to discard powerup
     */
    public void discardSecondPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() >= 2) {
            client.discardPowerup(2);
        }
    }

    /**
     * This method discards the third powerup
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to discard powerup
     */
    public void discardThirdPowerup(MouseEvent mouseEvent) throws IOException {
        client = Data.getInstance().getClient();
        playerController = Data.getInstance().getPlayerController();

        if(playerController.getPowerups().size() == 3) {
            client.discardPowerup(3);
        }
    }

    /**
     * This method discards the first powerup needed for respawning the player
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to respawn
     */
    public void discardPw1(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(1);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method discards the second powerup needed for respawning the player
     * @param mouseEvent when players clicks
     * @throws IOException when client fails to respawn
     */
    public void discardPw2(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(2);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method discards the third powerup needed for respawning the player
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to respawn
     */
    public void discardPw3(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(3);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method discards the fourth powerup needed for respawning the player
     * @param mouseEvent when player clicks
     * @throws IOException when client fails to respawn
     */
    public void discardPw4(MouseEvent mouseEvent) {

        Stage stage = (Stage) firstPowerUpD.getScene().getWindow();
        stage.close();

        client = Data.getInstance().getClient();
        try {
            client.respawn(4);
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    /**
     * This method reset all the moves for the MoveGrab action
     */
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

    /**
     * This method shows the moves on the MoveGrab label
     */
    private void setLabelMoveGrabTwoActions() {
        for(int i = 0; i < this.moveFrenzyTwoActions.length; i++) {
            //if(this.moveFrenzyTwoActions[2] != null){
                //Platform.runLater(() ->{
                  //  labelShowMoveGrab.setText("  " + this.moveFrenzyTwoActions[0] + "  " + this.moveFrenzyTwoActions[1] + "  " + this.moveFrenzyTwoActions[2]);
                //});
            //} else
                if(this.moveFrenzyTwoActions[1] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText(DOUBLE_SPACE + this.moveFrenzyTwoActions[0] + DOUBLE_SPACE + this.moveFrenzyTwoActions[1]);
                });
            } else if(this.moveFrenzyTwoActions[0] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText(DOUBLE_SPACE + this.moveFrenzyTwoActions[0]);
                });
            }
                //else if(this.moveFrenzyTwoActions[3] != null){
                //Platform.runLater(() ->{
                  //  labelShowMoveGrab.setText("  " + this.moveFrenzyTwoActions[0] + "  " + this.moveFrenzyTwoActions[1] + "  " + this.moveFrenzyTwoActions[2] + "  " + this.moveFrenzyTwoActions[3]);
                //});
            //}
        }
    }

    /**
     * This method shows the moves on the MoveGrab label for the players that has one action in the final frenzy
     */
    private void setLabelMoveGrabOneAction() {
        for(int i = 0; i < this.moveFrenzyOneActions.length; i++) {
            if(this.moveFrenzyOneActions[2] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText(DOUBLE_SPACE + this.moveFrenzyOneActions[0] + DOUBLE_SPACE + this.moveFrenzyOneActions[1] + DOUBLE_SPACE + this.moveFrenzyOneActions[2]);
                });
            } else if(this.moveFrenzyOneActions[1] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText(DOUBLE_SPACE + this.moveFrenzyOneActions[0] + DOUBLE_SPACE + this.moveFrenzyOneActions[1]);
                });
            } else if(this.moveFrenzyOneActions[0] != null){
                Platform.runLater(() ->{
                    labelShowMoveGrab.setText(DOUBLE_SPACE + this.moveFrenzyOneActions[0]);
                });
            }
        }
    }

    /**
     * Shows the move on the moveGrabLabel
     */
    private void showGrabMove(){
        Platform.runLater(()->{
            labelShowMoveGrab.setText(Data.getInstance().getMoveGrab());
        });
    }

    /**
     * Shows moves on the moveRel label
     */
    private void setLabelMoveRel() {
        String[] moveRel = Data.getInstance().getMoveRel();
        for(int i = 0; i < moveRel.length; i++) {
            if(moveRel[1] != null){
                Platform.runLater(() ->{
                    labelShowMoveRel.setText(DOUBLE_SPACE + moveRel[0] + DOUBLE_SPACE + moveRel[1]);
                });
            } else if(moveRel[0] != null){
                Platform.runLater(() ->{
                    labelShowMoveRel.setText(DOUBLE_SPACE + moveRel[0]);
                });
            }
        }
    }
}
