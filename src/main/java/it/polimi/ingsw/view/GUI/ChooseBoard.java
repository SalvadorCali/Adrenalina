package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseBoard extends Application implements Initializable {

    @FXML private ImageView firstBoard;
    @FXML private ImageView secondBoard;
    @FXML private ImageView thirdBoard;
    @FXML private ImageView fourthBoard;
    @FXML private Label labelErrorSkull;
    @FXML private TextField skullText;
    @FXML private Button enterButton;

    private Integer boardType = 0;
    private Integer skull;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent popupBoard = FXMLLoader.load(getClass().getClassLoader().getResource("ChooseBoard.fxml"));
        Scene scene = new Scene(popupBoard, 600, 400);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Choose Board");
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() ->{

            firstBoard.setOnMouseClicked(event -> {

                boardType = 1;
                Data.getInstance().setBoardType(boardType);
            });

            secondBoard.setOnMouseClicked(event -> {

                boardType = 2;
                Data.getInstance().setBoardType(boardType);
            });

            thirdBoard.setOnMouseClicked(event -> {

                boardType = 3;
                Data.getInstance().setBoardType(boardType);
            });

            fourthBoard.setOnMouseClicked(event -> {

                boardType = 4;
                Data.getInstance().setBoardType(boardType);
            });

            enterButton.setOnAction(event -> {

                skull = Integer.valueOf(skullText.getText());

                if(boardType == 0){

                    labelErrorSkull.setText("Board not chosen");

                }else if(skull > 8 || skull < 5){

                    labelErrorSkull.setText("Wrong skull number");

                }else {

                    Data.getInstance().setSkull(skull);
                    closeChooseBoard();
                }
            });
        });
    }

    private void closeChooseBoard() {
        Stage stage = (Stage) firstBoard.getScene().getWindow();
        stage.close();
    }

}
