package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Popup extends Application implements Initializable {

    @FXML Label labelPlayerDisconnected;

    String object;

    @Override
    public void start(Stage stage) throws Exception {

        Parent popup = FXMLLoader.load(getClass().getClassLoader().getResource("Popup.fxml"));
        Scene scene = new Scene(popup, 250, 217);

        stage.setScene(scene);
        stage.setTitle("Disconnection");
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void showPopup1(String object) throws Exception {

        labelPlayerDisconnected.setText(object);
        //start(new Stage());
    }
}