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

/**
 * this class check the ammos of a player
 */
public class AmmoGUI implements Initializable {

    /**
     * visible blueAmmo box
     */
    @FXML private HBox blueHBox;
    /**
     * visible redAmmo box
     */
    @FXML private HBox redHBox;
    /**
     * visible yellowAmmo box
     */
    @FXML private HBox yellowHBox;

    /**
     * the PlayerController that contains datas about the player.
     */
    private PlayerController playerController;
    /**
     * ammo's height
     */
    private static final Integer HEIGHT_R = 40;
    /**
     * ammo's width
     */
    private static final Integer WIDTH_R = 40;
    /**
     * boolean used for updating thread
     */
    private boolean connected = true;
    /**
     * spacing between every ammo
     */
    private static final Double SPACING = 10.0;

    /**
     * time occurs to update ammo
     */
    private static final Integer TIME_CHECK = 10000;

    /**
     * method that runs ammo's thread
     */
    public void setAmmo() {

        Thread thread2 = new Thread(this::checkAmmo);
        thread2.setDaemon(true);
        thread2.start();
    }

    /**
     * method that updates ammoBox every 10 seconds
     */
    public void checkAmmo(){
        while(connected) {
            Platform.runLater(() -> {
                resetAmmos();
                setAmmoBoxGrid();
            });

            try {

                Thread.sleep(TIME_CHECK);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method that update every ammo's box
     */
    public void setAmmoBoxGrid(){

        playerController = Data.getInstance().getPlayerController();
        Integer redAmmo = playerController.getPlayer().getRedAmmo();
        Integer blueAmmo = playerController.getPlayer().getBlueAmmo();
        Integer yellowAmmo = playerController.getPlayer().getYellowAmmo();

        int blueAmmoP = playerController.getPlayer().getPowerupBlueAmmo();
        int yellowAmmoP = playerController.getPlayer().getPowerupYellowAmmo();
        int redAmmoP = playerController.getPlayer().getPowerupRedAmmo();

        if(blueAmmoP != 0){
            blueAmmo += blueAmmoP;
        }
        if(redAmmoP != 0) {
            redAmmo += redAmmoP;
        }
        if(yellowAmmoP != 0) {
            yellowAmmo += yellowAmmoP;
        }
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

    /**
     * method that make easy to add ammo to HBox
     * @param box Hbox can be blue, red, yellow. Can contains ammo
     * @param color represents ammo's color
     */
    public void setBox(HBox box, String color){
        Image image = new Image("singleAmmo/" + color + ".png");
        ImageView imv = new ImageView(image);
        imv.setFitWidth(WIDTH_R);
        imv.setFitHeight(HEIGHT_R);

        Platform.runLater(() ->{
            box.getChildren().add(imv);
        });
    }

    /**
     * method that reset all the ammoBox
     */
    public void resetAmmos(){
        Platform.runLater(() ->{
            redHBox.getChildren().clear();
            blueHBox.getChildren().clear();
            yellowHBox.getChildren().clear();
        });
    }

    /**
     * method that set spacing between every ammo
     * @param box receive the HBox that need the spacing
     */
    public void setSpacing(HBox box){
        box.setSpacing(SPACING);
    }

    /**
     * standard initialize method
     * @param url location used to resolve relative paths for roots objects
     * @param resourceBundle used to localize the root object or null if it was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpacing(blueHBox);
        setSpacing(redHBox);
        setSpacing(yellowHBox);
    }
}
