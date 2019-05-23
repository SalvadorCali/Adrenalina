package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.util.Converter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChoosePowerup extends Application implements Initializable {

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

    public void launchChoosePowerup(List<Card> powerup) throws Exception {

        start(new Stage());

        Platform.runLater(() -> {
            for (int i = 0; i < powerup.size(); i++) {

                String color = Converter.fromColorToLetter(powerup.get(i).getColor());
                String name = powerup.get(i).getName();

                Image image = new Image("powerup/" + color + "/" + name + ".png");
                powerupImg1.setImage(image);
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        powerupImg1.setOnMouseClicked(e -> {

            try {
                GUIHandler guiHandler = new GUIHandler();
                guiHandler.launchMainBoard();
                handleCloseAction1();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        powerupImg2.setOnMouseClicked(e -> {
            try {
                GUIHandler guiHandler = new GUIHandler();
                guiHandler.launchMainBoard();
                handleCloseAction2();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    public void handleCloseAction1() {
        Stage stage = (Stage) powerupImg1.getScene().getWindow();
        stage.close();
    }

    public void handleCloseAction2() {
        Stage stage = (Stage) powerupImg2.getScene().getWindow();
        stage.close();
    }
}
