package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Converter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * this class shows the Score of the game and it appears every time one player dies or at the end of the game
 */
public class ScorePopup {

    /**
     * these label contains player's name
     */
    @FXML private Label firstScoreLabel;
    @FXML private Label secondScoreLabel;
    @FXML private Label thirdScoreLabel;
    @FXML private Label fourthScoreLabel;
    @FXML private Label fifthScoreLabel;

    /**
     * these label contains player's score number
     */
    @FXML private Label firstPlayerScoreNum;
    @FXML private Label secondPlayerScoreNum;
    @FXML private Label thirdPlayerScoreNum;
    @FXML private Label fourthPlayerScoreNum;
    @FXML private Label fifthPlayerScoreNum;


    public void setScore(Map<TokenColor, Integer> score) {

        List<TokenColor> colors = new ArrayList<>();
        score.forEach((c, i) ->{
            colors.add(c);
        });

        Integer numPlayer = score.size();

        Platform.runLater(() -> {


            if (numPlayer >= 2) {

                firstPlayerScoreNum.setText(String.valueOf(score.get(colors.get(0))));
                secondPlayerScoreNum.setText(String.valueOf(score.get(colors.get(1))));
                firstScoreLabel.setText(Converter.fromTokenColorToString(colors.get(0)));
                secondScoreLabel.setText(Converter.fromTokenColorToString(colors.get(1)));
            }

            if (numPlayer >= 3) {
                thirdScoreLabel.setVisible(true);
                thirdPlayerScoreNum.setVisible(true);
                thirdPlayerScoreNum.setText(String.valueOf((score.get(colors.get(2)))));
                thirdScoreLabel.setText(Converter.fromTokenColorToString(colors.get(2)));
            }

            if (numPlayer >= 4) {
                fourthScoreLabel.setVisible(true);
                fourthPlayerScoreNum.setVisible(true);
                fourthPlayerScoreNum.setText(String.valueOf((score.get(colors.get(3)))));
                fourthScoreLabel.setText(Converter.fromTokenColorToString(colors.get(3)));
            }

            if (numPlayer == 5) {
                fifthScoreLabel.setVisible(true);
                fifthPlayerScoreNum.setVisible(true);
                fifthPlayerScoreNum.setText(String.valueOf((score.get(colors.get(4)))));
                fifthScoreLabel.setText(Converter.fromTokenColorToString(colors.get(4)));
            }
        });
    }
}
