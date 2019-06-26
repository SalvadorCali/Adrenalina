package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.util.Converter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class AmmoGUI {

    @FXML private GridPane gridBox;
    @FXML private GridPane gridReserve;

    private PlayerController playerController;
    private Rectangle rectangle = new Rectangle();
    private static final Integer HEIGHT_R = 50;
    private static final Integer WIDTH_R = 50;


    private void setRectangle(){
        this.rectangle.setHeight(HEIGHT_R);
        this.rectangle.setWidth(WIDTH_R);
    }

    public void setAmmo() {
        setRectangle();

        Thread thread2 = new Thread(this::checkAmmo);
        thread2.setDaemon(true);
        thread2.start();
    }

    public void checkAmmo(){
        while(true) {
            Platform.runLater(() -> {
                //setAmmoBoxGrid();
                setAmmoReserveGrid();
            });

            try {

                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void setAmmoBoxGrid(){

        playerController = Data.getInstance().getPlayerController();
        List<Ammo> ammobox = playerController.getPlayer().getAmmoBox();

        for (int i = 0, row = 0; i < ammobox.size(); i++) {
            rectangle.setFill(Converter.fromColorEnumsToColorJFX(ammobox.get(i).getColor()));
            addAmmoToGrid(rectangle, i, row, gridBox);

            if (i == 2) {
                row++;
                i = 0;
            }

        }
    }

    public void setAmmoReserveGrid(){

        playerController = Data.getInstance().getPlayerController();
        List<Ammo> ammoReserve = playerController.getPlayer().getAmmoReserve();

        for (int i = 0, row = 0; i < ammoReserve.size(); i++) {
            rectangle.setFill(Converter.fromColorEnumsToColorJFX(ammoReserve.get(i).getColor()));
            addAmmoToGrid(rectangle, i, row, gridReserve);

            if (i == 2) {
                row++;
                i = 0;
            }
        }
    }

    private void addAmmoToGrid(Rectangle rectangle, int i, int row, GridPane ammoGrid) {
        Platform.runLater(() ->{
            ammoGrid.add(rectangle, i, row);
        });
    }
}
