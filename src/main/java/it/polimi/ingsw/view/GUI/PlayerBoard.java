package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PlayerBoard extends Application {

    @FXML
    private HBox damageHBox;

    @FXML
    private HBox deathCounterBox;

    @FXML
    private GridPane ammoBoxGrid;

    @FXML
    private GridPane ammoReserveGrid;


    @Override
    public void start(Stage stage) throws Exception {

        Parent playerBoard = FXMLLoader.load(getClass().getClassLoader().getResource("PlayerBoard.fxml"));
        Scene scene = new Scene(playerBoard, 1000, 293);

        stage.setScene(scene);
        stage.setTitle("PlayerBoard");
        stage.show();
    }

    public void launchPlayerBoard() throws Exception {

        start(new Stage());
    }
}
