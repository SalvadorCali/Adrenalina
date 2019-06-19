package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PopupReload {

    @FXML private TextField txtWeaponReload;
    @FXML private Button endReloadButton;
    @FXML private Button buttonReload;


    public void closeReload(MouseEvent mouseEvent) {

        Stage stage = (Stage) buttonReload.getScene().getWindow();
        stage.close();
    }


    public void reload(MouseEvent mouseEvent) {
        if(txtWeaponReload.getText() == "first" || txtWeaponReload.getText() == "second" || txtWeaponReload.getText() == "third") {
            String weaponReload = txtWeaponReload.getText();
            Data.getInstance().setWeaponReloaded(weaponReload);
        }
    }
}
