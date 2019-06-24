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
    @FXML private GridPane ammoBoxGrid;
    @FXML private GridPane ammoReserveGrid;
    @FXML private GridPane firstDeathCounterGrid;
    @FXML private GridPane secondDeathCounterGrid;
    @FXML private GridPane thirdDeathCounterGrid;
    @FXML private GridPane fourthDeathCounterGrid;
    @FXML private GridPane fifthDeathCounterGrid;
    @FXML private GridPane marksGrid;

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

    public void setMarksGrid() {

        playerController = Data.getInstance().getPlayerController();
        List<Token> marks = playerController.getPlayerBoard().getRevengeMarks();

        for (int i = 0, row = 0; i < marks.size(); i++) {
            if (!marks.get(i).getFirstColor().equals(TokenColor.NONE)) {
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(marks.get(i).getFirstColor()) + ".jpg");
                addMarkGrid(image, marksGrid, i, row);
            }
        }
    }

    private void addMarkGrid(Image image, GridPane marksGrid, int i, int row) {
        Platform.runLater(() ->{
            marksGrid.add(new ImageView(image), i, row);
        });
    }

    public void setFirstDamageGrid(){

        playerController = Data.getInstance().getPlayerController();
        Token[] damageBoard = playerController.getPlayerBoard().getDamageBoard();

        for (int i = 0, row = 0; i < damageBoard.length; i++) {
            if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".jpg");
                addDamageOnGrid(firstDamageGrid, image, i, row);
            }
        }
    }

    private void addDamageOnGrid(GridPane damageGrid, Image image, int i, int row) {
        Platform.runLater(() ->{
            damageGrid.add(new ImageView(image), i, row);
        });
    }

    public void setAmmoBoxGrid(){

        playerController = Data.getInstance().getPlayerController();
        List<Ammo> ammobox = playerController.getPlayer().getAmmoBox();

        for (int i = 0, row = 0; i < ammobox.size(); i++) {
            if (!ammobox.get(i).getColor().equals(Color.NONE)) {
                Image image = new Image("singleAmmo/" + Converter.fromColorToString(ammobox.get(i).getColor()) + ".jpg");
                addAmmoToBox(image, i, row, ammoBoxGrid);

                if (i == 2) {
                    row++;
                    i = 0;
                }
            }
        }
    }

    private void addAmmoToBox(Image image, int i, int row, GridPane ammoGrid) {
        Platform.runLater(() ->{
            ammoGrid.add(new ImageView(image), i, row);
        });
    }

    public void setAmmoReserveGrid(){

        playerController = Data.getInstance().getPlayerController();
        List<Ammo> ammoReserve = playerController.getPlayer().getAmmoReserve();

        for (int i = 0, row = 0; i < ammoReserve.size(); i++) {
            if (!ammoReserve.get(i).getColor().equals(Color.NONE)) {
                Image image = new Image("singleAmmo/" + Converter.fromColorToString(ammoReserve.get(i).getColor()) + ".jpg");
                addAmmoToBox(image, i, row, ammoReserveGrid);

                if (i == 2) {
                    row++;
                    i = 0;
                }
            }
        }
    }

    public void setOthersDamage(){

        playerController = Data.getInstance().getPlayerController();
        this.numOtherPlayers = playerController.getOtherPlayers().size();

        if(numOtherPlayers >= 1){
            Token[] damageBoard = playerController.getOtherPlayers().get(0).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".jpg");
                    addDamageOnGrid(secondDamageGrid, image, i, row);
                }
            }
        }

        if(numOtherPlayers >= 2){
            Token[] damageBoard = playerController.getOtherPlayers().get(1).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".jpg");
                    addDamageOnGrid(thirdDamageGrid, image, i, row);
                }
            }
        }

        if(numOtherPlayers >= 3){
            Token[] damageBoard = playerController.getOtherPlayers().get(2).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".jpg");
                    addDamageOnGrid(fourthDamageGrid, image, i, row);
                }
            }
        }

        if(numOtherPlayers >= 4){
            Token[] damageBoard = playerController.getOtherPlayers().get(3).getPlayerBoard().getDamageBoard();
            for (int i = 0, row = 0; i < damageBoard.length; i++) {
                if (!damageBoard[i].getFirstColor().equals(TokenColor.NONE)) {
                    Image image = new Image("colorPlayer/" + Converter.fromTokenColorToString(damageBoard[i].getFirstColor()) + ".jpg");
                    addDamageOnGrid(fifthDamageGrid, image, i, row);
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
                //setAmmoBoxGrid();
                setOthersDamage();
                //setAmmoReserveGrid();
                //setMarksGrid();
            });

            try {

                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
