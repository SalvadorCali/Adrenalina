package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;

public class AmmoGUI implements Initializable {

    @FXML private HBox blueHBox;
    @FXML private HBox redHBox;
    @FXML private HBox yellowHBox;


    private PlayerController playerController;
    private static final Integer HEIGHT_R = 40;
    private static final Integer WIDTH_R = 40;
    private boolean connected = true;
    private static final Double SPACING = 10.0;

    public void setAmmo() {

        Thread thread2 = new Thread(this::checkAmmo);
        thread2.setDaemon(true);
        thread2.start();
    }

    public void checkAmmo(){
        while(connected) {
            Platform.runLater(() -> {
                resetAmmos();
                setAmmoBoxGrid();
            });

            try {

                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void setAmmoBoxGrid(){

        playerController = Data.getInstance().getPlayerController();
        Integer redAmmo = playerController.getPlayer().getRedAmmo();
        Integer blueAmmo = playerController.getPlayer().getBlueAmmo();
        Integer yellowAmmo = playerController.getPlayer().getYellowAmmo();

        for (int i = 0; i < redAmmo; i++) {
            setBox(redHBox, "red");
        }

        for (int i = 0; i < blueAmmo; i++) {
            setBox(blueHBox, "blue");
        }

        for (int i = 0; i < yellowAmmo; i++) {
            setBox(yellowHBox, "yellow");
        }
    }

    public void setBox(HBox box, String color){
        Image image = new Image("singleAmmo/" + color + ".png");
        ImageView imv = new ImageView(image);
        imv.setFitWidth(WIDTH_R);
        imv.setFitHeight(HEIGHT_R);

        Platform.runLater(() ->{
            box.getChildren().add(imv);
        });
    }

    public void resetAmmos(){
        Platform.runLater(() ->{
            redHBox.getChildren().clear();
            blueHBox.getChildren().clear();
            yellowHBox.getChildren().clear();
        });
    }

    public void setSpacing(HBox box){
        box.setSpacing(SPACING);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpacing(blueHBox);
        setSpacing(redHBox);
        setSpacing(yellowHBox);
    }
}
