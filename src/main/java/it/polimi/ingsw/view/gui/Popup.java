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

/**
 * this class shows a popup that display the disconnected player's name
 */
public class Popup extends Application implements Initializable {

    /**
     * this label contains the disconnected player's name
     */
    @FXML Label labelPlayerDisconnected;

    /**
     * standard start method that load popup.fxml
     * @param stage main stage of popup class
     * @throws Exception if doesn't load popup.fxml
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent popup = FXMLLoader.load(getClass().getClassLoader().getResource("Popup.fxml"));
        Scene scene = new Scene(popup, 250, 217);

        stage.setScene(scene);
        stage.setTitle("Disconnection");
        stage.show();
    }

    /**
     * standard initialize method
     * @param url location used to resolve relative paths for roots objects
     * @param resourceBundle used to localize the root object or null if it was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * method that setText
     * @param object name's player
     */
    public void showPopup1(String object) {

        labelPlayerDisconnected.setText(object);
    }
}
