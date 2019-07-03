package it.polimi.ingsw.view.gui;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * class that launch login, extends application from javafx
 */
public class LoginGUI extends Application {

    /**
     * primary stage's login
     */
    Stage primaryStage;

    /**
     * standard start method that load logingui.fxml
     * @param primaryStage
     * @throws Exception
     */
    public synchronized void start(Stage primaryStage) throws Exception {

        Parent adrenalineLog = FXMLLoader.load(getClass().getClassLoader().getResource("LoginGUI.fxml"));
        Scene scene = new Scene(adrenalineLog, 600, 470);

        this.primaryStage.setTitle("Adrenaline Login");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

    }
    
}

