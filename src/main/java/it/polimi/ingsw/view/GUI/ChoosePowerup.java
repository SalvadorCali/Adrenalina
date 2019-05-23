package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.cards.Card;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.List;

public class ChoosePowerup extends Application {

    @FXML
    private ImageView powerupImg1;

    @FXML
    private ImageView powerupImg2;

    private LoginGUI loginGUI;
    private boolean closing = false;

    @Override
    public void start(Stage powerupStage) throws Exception {

        Parent choosePowerup = FXMLLoader.load(getClass().getClassLoader().getResource("ChoosePowerup.fxml"));
        Scene scene = new Scene(choosePowerup, 490, 386);

        powerupStage.setScene(scene);
        powerupStage.setTitle("Choose Powerup");
        powerupStage.show();

        if(closing){
         powerupStage.close();
        }
    }

    public void launchChoosePowerup(List<Card> powerup) throws Exception {

        start(new Stage());

        //powerupImg1.setImage();
    }

    public void selectPowerup(){

        powerupImg1.setOnMouseClicked(e -> {

            try {
                closing = true;
                loginGUI.launchBoard();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        powerupImg2.setOnMouseClicked(e -> {
            try {
                closing = true;
                loginGUI.launchBoard();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

}
