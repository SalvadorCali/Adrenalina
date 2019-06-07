package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ChooseBoard extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent popupBoard = FXMLLoader.load(getClass().getClassLoader().getResource("ChooseBoard.fxml"));
        Scene scene = new Scene(popupBoard, 600, 400);

        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("Choose Board");
        this.primaryStage.show();

    }


}
