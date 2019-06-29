package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Converter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerBoardGui extends Application {

    private static final double STANDARD_HEIGHT = 40;
    private static final double SMALL_HEIGHT = 20;
    @FXML private ImageView firstPlayerBoard;
    @FXML private ImageView secondPlayerBoard;
    @FXML private ImageView thirdPlayerBoard;
    @FXML private ImageView fourthPlayerBoard;
    @FXML private ImageView fifthPlayerBoard;
    @FXML private GridPane firstDamageGrid;
    @FXML private GridPane secondDamageGrid;
    @FXML private GridPane thirdDamageGrid;
    @FXML private GridPane fourthDamageGrid;
    @FXML private GridPane fifthDamageGrid;
    @FXML private GridPane firstDeathCounterGrid;
    @FXML private GridPane secondDeathCounterGrid;
    @FXML private GridPane thirdDeathCounterGrid;
    @FXML private GridPane fourthDeathCounterGrid;
    @FXML private GridPane fifthDeathCounterGrid;
    @FXML private GridPane marksGrid;
    @FXML private GridPane secondPlayerMarks;
    @FXML private GridPane thirdPlayerMarks;
    @FXML private GridPane fourthPlayerMarks;
    @FXML private GridPane fifthPlayerMarks;

    private PlayerController playerController;
    private int numOtherPlayers;

    @Override
    public void start(Stage stage) throws Exception {

        Parent playerBoard = FXMLLoader.load(getClass().getClassLoader().getResource("PlayerBoard.fxml"));
        Scene scene = new Scene(playerBoard, 1000, 293);

        stage.setScene(scene);
        stage.setTitle("PlayerBoardGui");
        stage.show();
    }

    public void setMarksGridFirstPlayer() {

        playerController = Data.getInstance().getPlayerController();
        List<Token> marks = playerController.getPlayerBoard().getRevengeMarks();

        for (int i = 0, row = 0; i < marks.size(); i++) {
            if (!marks.get(i).getFirstColor().equals(TokenColor.NONE)) {
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(marks.get(i).getFirstColor()) + ".png");
                addMarkGrid(image, marksGrid, i, row);
            }
        }
    }

    public void setOtherPlayersDeath(){

        playerController = Data.getInstance().getPlayerController();
        this.numOtherPlayers = playerController.getOtherPlayers().size();

        if(numOtherPlayers >= 1){
            int deathNum = playerController.getOtherPlayers().get(0).getPlayerBoard().getDeathNumber();
            for (int i = 0, row = 0; i < deathNum; i++) {
                Image image = new Image("boardElem/skull.png");
                addMarkGrid2(secondDeathCounterGrid, image, i, row);
            }
        }

        if(numOtherPlayers >= 2){
            int deathNum = playerController.getOtherPlayers().get(1).getPlayerBoard().getDeathNumber();
            for (int i = 0, row = 0; i < deathNum; i++) {
                Image image = new Image("boardElem/skull.png");
                addMarkGrid2(thirdDeathCounterGrid, image, i, row);
            }
        }

        if(numOtherPlayers >= 3){
            int deathNum = playerController.getOtherPlayers().get(2).getPlayerBoard().getDeathNumber();
            for (int i = 0, row = 0; i < deathNum; i++) {
                Image image = new Image("boardElem/skull.png");
                addMarkGrid2(fourthDeathCounterGrid, image, i, row);
            }
        }

        if(numOtherPlayers >= 4){
            int deathNum = playerController.getOtherPlayers().get(3).getPlayerBoard().getDeathNumber();
            for (int i = 0, row = 0; i < deathNum; i++) {
                Image image = new Image("boardElem/skull.png");
                addMarkGrid2(fifthDeathCounterGrid, image, i, row);
            }
        }
    }

    public void setFirstPlayerDeathCounter(){

        playerController = Data.getInstance().getPlayerController();
        List<Token> kills = playerController.getKillshotTrack();

        for (int i = 0, row = 0; i < kills.size(); i++) {
            if (!kills.get(i).getFirstColor().equals(TokenColor.NONE)) {
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(kills.get(i).getFirstColor()) + ".png");
                addDamageOnGrid(firstDeathCounterGrid, image, i, row);
            }
        }
    }

    public void setMarksGridOtherPlayers(){
        playerController = Data.getInstance().getPlayerController();
        List<Player> otherPlayers = playerController.getOtherPlayers();
        this.numOtherPlayers = otherPlayers.size();

        if(numOtherPlayers >= 1){
            List<Token> marks = playerController.getOtherPlayers().get(0).getPlayerBoard().getRevengeMarks();
            for (int i = 0, row = 0; i < marks.size(); i++) {
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(marks.get(i).getFirstColor()) + ".png");
                addMarkGrid2(secondPlayerMarks, image, i, row);
            }
        }

        if(numOtherPlayers >= 2){
            List<Token> marks = playerController.getOtherPlayers().get(1).getPlayerBoard().getRevengeMarks();
            for (int i = 0, row = 0; i < marks.size(); i++) {
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(marks.get(i).getFirstColor()) + ".png");
                addMarkGrid2(thirdPlayerMarks, image, i, row);
            }
        }

        if(numOtherPlayers >= 3){
            List<Token> marks = playerController.getOtherPlayers().get(2).getPlayerBoard().getRevengeMarks();
            for (int i = 0, row = 0; i < marks.size(); i++) {
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(marks.get(i).getFirstColor()) + ".png");
                addMarkGrid2(fourthPlayerMarks, image, i, row);
            }
        }

        if(numOtherPlayers >= 4){
            List<Token> marks = playerController.getOtherPlayers().get(3).getPlayerBoard().getRevengeMarks();
            for (int i = 0, row = 0; i < marks.size(); i++) {
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(marks.get(i).getFirstColor()) + ".png");
                addMarkGrid2(fifthPlayerMarks, image, i, row);
            }
        }
    }

    private void addMarkGrid(Image image, GridPane marksGrid, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(STANDARD_HEIGHT);
        imv.setFitHeight(STANDARD_HEIGHT);
        Platform.runLater(() ->{
            marksGrid.add(new ImageView(image), i, row);
        });
    }

    private void addMarkGrid2(GridPane marksGrid, Image image, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(SMALL_HEIGHT);
        imv.setFitHeight(SMALL_HEIGHT);
        Platform.runLater(() ->{
            marksGrid.add(new ImageView(image), i, row);
        });
    }

    public void setFirstDamageGrid(){

        playerController = Data.getInstance().getPlayerController();
        Token[] damageBoard = playerController.getPlayerBoard().getDamageBoard();

        for (int i = 0, row = 0; i < damageBoard.length; i++) {
            if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                Image image = new Image("damageTears/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".png");
                addDamageOnGrid(firstDamageGrid, image, i, row);
            }
        }
    }

    private void addDamageOnGrid(GridPane damageGrid, Image image, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(STANDARD_HEIGHT);
        imv.setFitHeight(STANDARD_HEIGHT);
        Platform.runLater(() ->{
            damageGrid.add(imv, i, row);
        });
    }

    private void addDamageOnGrid2(GridPane damageGrid, Image image, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(SMALL_HEIGHT);
        imv.setFitHeight(SMALL_HEIGHT);
        Platform.runLater(() ->{
            damageGrid.add(imv, i, row);
        });
    }


    public void setOthersDamage(){

        playerController = Data.getInstance().getPlayerController();
        this.numOtherPlayers = playerController.getOtherPlayers().size();

        if(numOtherPlayers >= 1){
            Token[] damageBoard = playerController.getOtherPlayers().get(0).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("damageTears/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".png");
                    addDamageOnGrid2(secondDamageGrid, image, i, row);
                }
            }
        }

        if(numOtherPlayers >= 2){
            Token[] damageBoard = playerController.getOtherPlayers().get(1).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("damageTears/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".png");
                    addDamageOnGrid2(thirdDamageGrid, image, i, row);
                }
            }
        }

        if(numOtherPlayers >= 3){
            Token[] damageBoard = playerController.getOtherPlayers().get(2).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("damageTears/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".png");
                    addDamageOnGrid2(fourthDamageGrid, image, i, row);
                }
            }
        }

        if(numOtherPlayers >= 4){
            Token[] damageBoard = playerController.getOtherPlayers().get(3).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("damageTears/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".png");
                    addDamageOnGrid2(fifthDamageGrid, image, i, row);
                }
            }
        }
    }


    public void setPlayerBoardImage() {

        playerController = Data.getInstance().getPlayerController();
        firstPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(playerController.getPlayer().getColor()) + ".jpg"));


        List<Player> otherPlayers = playerController.getOtherPlayers();
        if(otherPlayers.size() == 1){
            Platform.runLater(() -> {

                secondPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                secondPlayerBoard.setVisible(true);
            });

        }else if (otherPlayers.size() == 2) {

            Platform.runLater(() -> {

                secondPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                thirdPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(1).getColor()) + ".jpg"));
                secondPlayerBoard.setVisible(true);
                thirdPlayerBoard.setVisible(true);
            });

        } else if (otherPlayers.size() == 3) {

            Platform.runLater(() -> {

                secondPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                thirdPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(1).getColor()) + ".jpg"));
                fourthPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(2).getColor()) + ".jpg"));
                secondPlayerBoard.setVisible(true);
                thirdPlayerBoard.setVisible(true);
                fourthPlayerBoard.setVisible(true);
            });

        } else {

            Platform.runLater(() -> {

                secondPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                thirdPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(1).getColor()) + ".jpg"));
                fourthPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(2).getColor()) + ".jpg"));
                fifthPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(3).getColor()) + ".jpg"));
                secondPlayerBoard.setVisible(true);
                thirdPlayerBoard.setVisible(true);
                fourthPlayerBoard.setVisible(true);
                fifthPlayerBoard.setVisible(true);
            });
        }
    }


    public void setAll() {

        Thread thread2 = new Thread(this::checkPlayerBoards);
        thread2.setDaemon(true);
        thread2.start();
    }


    public void checkPlayerBoards(){
        while(true) {
            Platform.runLater(() -> {
                setPlayerBoardImage();
                setFirstDamageGrid();
                setOthersDamage();
                setMarksGridFirstPlayer();
                setMarksGridOtherPlayers();
                setFirstPlayerDeathCounter();
                setOtherPlayersDeath();
            });

            try {

                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
