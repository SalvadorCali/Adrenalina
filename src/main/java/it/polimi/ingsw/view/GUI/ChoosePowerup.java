package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ChoosePowerup extends Application {

    @FXML
    private ImageView powerupImg1;

    @FXML
    private ImageView powerupImg2;

    @Override
    public void start(Stage powerupStage) throws Exception {

        Parent choosePowerup = FXMLLoader.load(getClass().getClassLoader().getResource("ChoosePowerup.fxml"));
        Scene scene = new Scene(choosePowerup, 490, 386);

        powerupStage.setScene(scene);
        powerupStage.setTitle("Choose Powerup");
        powerupStage.show();
    }

    public void launchChoosePowerup() throws Exception {

        start(new Stage());
    }

    public void selectPowerup(){

        powerupImg1.setOnMouseClicked(e -> {

            //fill
        });

        powerupImg2.setOnMouseClicked(e -> {

            //fill
        });
    }

}
