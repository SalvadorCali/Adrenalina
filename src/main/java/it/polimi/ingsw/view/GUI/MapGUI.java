package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.enums.BoardType;
import it.polimi.ingsw.model.gamecomponents.Square;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MapGUI extends Application implements Initializable {

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

    private static final int ROWS = 3;
    private static final int COLUMNS = 4;
    private static final int NUM_SQUARES=12;
    private static final int MAX_NUM_PLAYER=5;

    private GameController gameController;
    private String currentPlayer;
    private BoardType boardType;
    private Square[][] arena = new Square[ROWS][COLUMNS];

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

        setMapImage();
        updatePlayerTurnLabel();
    }

    public void updatePlayerTurnLabel(){

        Platform.runLater(() -> {

            currentPlayer = gameController.getGame().getCurrentPlayer().getUsername();
            playerTurnLabel.setText(currentPlayer);
        });
    }


    public void setMapImage(){

        //fill
    }


    public void placePlayers(){

        arena = gameController.getGame().getBoard().getArena();

        if(!arena[0][0].getPlayers().isEmpty()){
            for(int index = 0; index < arena[0][0].getPlayers().size(); index ++){

                
            }
        }
    }
}
