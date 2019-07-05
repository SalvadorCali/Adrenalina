package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * class that show the Board Choose
 */
public class ChooseBoard extends Application {

    /**
     * the ChooseBoard's Stage
     */
    private Stage primaryStage;

    /**
     * standard start method that load chooseboard.fxml
     * @param primaryStage main stage
     * @throws Exception if doesn't load chooseBoard.fxml
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent popupBoard = FXMLLoader.load(getClass().getClassLoader().getResource("ChooseBoard.fxml"));
        Scene scene = new Scene(popupBoard, 600, 400);

        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("Choose Board");
        this.primaryStage.show();

    }


}
