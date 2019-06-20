package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.enums.TokenColor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;

public class ScorePopup {

    @FXML private Label firstScoreLabel;
    @FXML private Label secondScoreLabel;
    @FXML private Label thirdScoreLabel;
    @FXML private Label fourthScoreLabel;
    @FXML private Label fifthScoreLabel;
    @FXML private Label firstPlayerScoreNum;
    @FXML private Label secondPlayerScoreNum;
    @FXML private Label thirdPlayerScoreNum;
    @FXML private Label fourthPlayerScoreNum;
    @FXML private Label fifthPlayerScoreNum;


    public void setScore(Map<TokenColor, Integer> score) {
        Platform.runLater(() -> {

            Integer numPlayer = score.size();

            if (numPlayer >= 2) {

                firstPlayerScoreNum.setText(String.valueOf((score.keySet().toArray()[0])));
                secondPlayerScoreNum.setText(String.valueOf((score.keySet().toArray()[1])));
            }

            if (numPlayer >= 3) {
                thirdScoreLabel.setVisible(true);
                thirdPlayerScoreNum.setVisible(true);
                thirdPlayerScoreNum.setText(String.valueOf((score.keySet().toArray()[2])));
            }

            if (numPlayer >= 4) {
                fourthScoreLabel.setVisible(true);
                fourthPlayerScoreNum.setVisible(true);
                fourthPlayerScoreNum.setText(String.valueOf((score.keySet().toArray()[3])));
            }

            if (numPlayer == 5) {
                fifthScoreLabel.setVisible(true);
                fifthPlayerScoreNum.setVisible(true);
                fifthPlayerScoreNum.setText(String.valueOf((score.keySet().toArray()[4])));
            }
        });
    }
}
