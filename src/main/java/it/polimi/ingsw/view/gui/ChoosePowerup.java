package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.util.Converter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ChoosePowerup {

    @FXML
    private ImageView powerupImg1;

    @FXML
    private ImageView powerupImg2;


    public void launchChoosePowerup(List<Card> powerup) throws Exception {

        Platform.runLater(() -> {
            for (int i = 0; i < powerup.size(); i++) {

                String color = Converter.fromColorToLetter(powerup.get(i).getColor());
                String name = powerup.get(i).getName();

                Image image = new Image("powerup/" + color + "/" + name + ".png");

                if(i == 0){
                    powerupImg1.setImage(image);
                }else{
                    powerupImg2.setImage(image);
                }
            }
        });
    }


}