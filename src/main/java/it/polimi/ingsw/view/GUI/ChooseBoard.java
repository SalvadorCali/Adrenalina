package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseBoard extends Application implements Initializable {

    @FXML
    private ImageView firstBoard;

    @FXML
    private ImageView secondBoard;

    @FXML
    private ImageView thirdBoard;

    @FXML
    private ImageView fourthBoard;
    private LoginGUI loginGUI;
    private Integer boardType;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent popupBoard = FXMLLoader.load(getClass().getClassLoader().getResource("ChooseBoard.fxml"));
        Scene scene = new Scene(popupBoard, 600, 400);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Choose Board");
        stage.show();
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        firstBoard.setOnMouseClicked(event -> {

            LoginGUI.getInstance().setBoardType(1);
            closeChooseBoard();
        });

        secondBoard.setOnMouseClicked(event -> {

            LoginGUI.getInstance().setBoardType(2);
            closeChooseBoard();
        });

        thirdBoard.setOnMouseClicked(event -> {

            LoginGUI.getInstance().setBoardType(3);
            closeChooseBoard();
        });

        fourthBoard.setOnMouseClicked(event -> {

            LoginGUI.getInstance().setBoardType(4);
            closeChooseBoard();
        });


    }

    private void closeChooseBoard() {
        Stage stage = (Stage) firstBoard.getScene().getWindow();
        stage.close();
    }

}
