package it.polimi.ingsw.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PopupReload {

    @FXML private TextField txtWeaponReload1;
    @FXML private TextField txtWeaponReload2;
    @FXML private TextField txtWeaponReload3;
    @FXML private Button buttonReload;


    public void reload(MouseEvent mouseEvent) {
        if(txtWeaponReload1.getText() == "yes") {
            Data.getInstance().setWeaponReloaded1("yes");
        }
        if(txtWeaponReload2.getText() == "yes"){
            Data.getInstance().setWeaponReloaded2("yes");
        }
        if(txtWeaponReload3.getText() == "yes"){
            Data.getInstance().setWeaponReloaded3("yes");
        }

        Stage stage = (Stage) buttonReload.getScene().getWindow();
        stage.close();
    }
}
