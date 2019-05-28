package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.gamecomponents.Square;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.view.ViewInterface;
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
import java.net.URL;
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

    private GameController gameController;
    private String currentPlayer;
    private BoardType boardType;
    private Square[][] arena = new Square[ROWS][COLUMNS];
    private Stage scene;
    private boolean disconnected = false;
    private ChoosePowerup choosePowerup = new ChoosePowerup();
    Popup popup = new Popup();



    @Override
    public void start(Stage stage) throws Exception {

        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("MapGUI.fxml"));
        Scene scene = new Scene(parent, 1189, 710);
        stage.setScene(scene);
        stage.setTitle("Main Board");
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bannerDisconnect.setOnMouseClicked(mouseEvent -> {
            try {
                closeThis();
                popup.showPopup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        setMapImage();
    }

    public void launchMainBoard() throws Exception {

        start(new Stage());
    }

    public void updatePlayerTurnLabel(){

        Platform.runLater(() -> {

            currentPlayer = gameController.getGame().getCurrentPlayer().getUsername();
            playerTurnLabel.setText(currentPlayer);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setMapImage(){

        //fill
    }


    public void placePlayers(){
        Platform.runLater(() -> {
        arena = gameController.getGame().getBoard().getArena();

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

    @Override
    public void start() {

    }

    @Override
    public void setPlayerController(PlayerController playerController) {

    }

    @Override
    public void notify(Message message) {

    }

    @Override
    public void notify(Message message, Outcome outcome) {

    }

    @Override
    public void notify(Message message, Outcome outcome, Object object) {
        switch (message){
            case SPAWN:
                notifySpawnLocation((List<Card>) object);
                break;
            case DISCONNECT:
                notifyDisconnection(outcome, (String) object);
                break;
        }
    }

    private void notifyDisconnection(Outcome outcome, String object) {

        Platform.runLater(() -> {
            labelStatusPlayer.setVisible(true);
            disconnected = true;
        });
    }

    private void notifySpawnLocation(List<Card> powerup) {

        Platform.runLater(() -> {
            try {
                //choosePowerup.launchChoosePowerup(powerup);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void closeThis(){
        Stage stage = (Stage) labelDisconnect.getScene().getWindow();
        stage.close();
    }
}
