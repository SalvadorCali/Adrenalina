package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Token;
import it.polimi.ingsw.util.Converter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

/**
 * this class shows all the playerBoard and displays damages, marks
 */
public class PlayerBoardGui extends Application {

    /**
     * these two variable are used for damage and marks dimension
     */
    private static final double STANDARD_HEIGHT = 40;
    private static final double SMALL_HEIGHT = 20;

    /**
     * time used for updating the playerBoardGUI thread
     */
    private static final int TIME_CHECK = 10000;

    /**
     * these imageView contain image of playerboard
     */
    @FXML private ImageView firstPlayerBoard;
    @FXML private ImageView secondPlayerBoard;
    @FXML private ImageView thirdPlayerBoard;
    @FXML private ImageView fourthPlayerBoard;
    @FXML private ImageView fifthPlayerBoard;

    /**
     * these gridPane contain players' damage
     */
    @FXML private GridPane firstDamageGrid;
    @FXML private GridPane secondDamageGrid;
    @FXML private GridPane thirdDamageGrid;
    @FXML private GridPane fourthDamageGrid;
    @FXML private GridPane fifthDamageGrid;

    /**
     * these gridPane contain players' death counter
     */
    @FXML private GridPane firstDeathCounterGrid;
    @FXML private GridPane secondDeathCounterGrid;
    @FXML private GridPane thirdDeathCounterGrid;
    @FXML private GridPane fourthDeathCounterGrid;
    @FXML private GridPane fifthDeathCounterGrid;

    /**
     * these gridPane contain players' marks
     */
    @FXML private GridPane marksGrid;
    @FXML private GridPane secondPlayerMarks;
    @FXML private GridPane thirdPlayerMarks;
    @FXML private GridPane fourthPlayerMarks;
    @FXML private GridPane fifthPlayerMarks;

    /**
     * playerController that contains datas about the player.
     */
    private PlayerController playerController;

    /**
     * count total player - 1
     */
    private int numOtherPlayers;

    /**
     * standard start method that loads playerboard.fxml
     * @param stage stage of the playerboard.fxml
     * @throws Exception if doesn't load fxml file
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent playerBoard = FXMLLoader.load(getClass().getClassLoader().getResource("PlayerBoard.fxml"));
        Scene scene = new Scene(playerBoard, 1000, 293);

        stage.setScene(scene);
        stage.setTitle("PlayerBoardGui");
        stage.show();
    }

    /**
     * this method sets marks on the first playerBoard
     */
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

    /**
     * this method sets number of deaths on every player except for the first
     */
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

    /**
     * this method sets number of deaths for the first player
     */
    public void setFirstPlayerDeathCounter(){

        playerController = Data.getInstance().getPlayerController();
        int kills = playerController.getPlayer().getPlayerBoard().getDeathNumber();

        for (int i = 0, row = 0; i < kills; i++) {
            Image image = new Image("boardElem/skull.png");
            addDamageOnGrid(firstDeathCounterGrid, image, i, row);
        }
    }

    /**
     * this method sets marks on every player except for the first
     */
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

    /**
     * this method takes an image and add that on the first player's marksGrid
     * @param image image of a mark
     * @param marksGrid right marksGrid of the player
     * @param i represent column of the gridpane
     * @param row represent row of the gridpane
     */
    private void addMarkGrid(Image image, GridPane marksGrid, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(STANDARD_HEIGHT);
        imv.setFitHeight(STANDARD_HEIGHT);
        Platform.runLater(() ->{
            marksGrid.add(imv, i, row);
        });
    }

    /**
     * this method takes an image and add that on every player's marksGrid
     * @param marksGrid right marksGrid of the player
     * @param image image of a mark
     * @param i represent column of the gridpane
     * @param row represent row of the gridpane
     */
    private void addMarkGrid2(GridPane marksGrid, Image image, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(SMALL_HEIGHT);
        imv.setFitHeight(SMALL_HEIGHT);
        Platform.runLater(() ->{
            marksGrid.add(imv, i, row);
        });
    }

    /**
     * set damage on the first Grid
     */
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

    /**
     * add image to the first player's damageGrid
     * @param damageGrid first player Damage Grid
     * @param image of damageTears
     * @param i represent column of the gridpane
     * @param row represent row of the gridpane
     */
    private void addDamageOnGrid(GridPane damageGrid, Image image, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(STANDARD_HEIGHT);
        imv.setFitHeight(STANDARD_HEIGHT);
        Platform.runLater(() ->{
            damageGrid.add(imv, i, row);
        });
    }

    /**
     * add image to every player's damageGrid except for the first one
     * @param damageGrid other players Damage Grid
     * @param image of damageTears
     * @param i represent column of the gridpane
     * @param row represent row of the gridpane
     */
    private void addDamageOnGrid2(GridPane damageGrid, Image image, int i, int row) {
        ImageView imv = new ImageView(image);
        imv.setFitWidth(SMALL_HEIGHT);
        imv.setFitHeight(SMALL_HEIGHT);
        Platform.runLater(() ->{
            damageGrid.add(imv, i, row);
        });
    }

    /**
     * set damage for every players except for the first one
     */
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

    /**
     * set all the playerBoard's images
     */
    public void setPlayerBoardImage() {
        playerController = Data.getInstance().getPlayerController();

        if(!playerController.isPlayerBoardFinalFrenzy()) {
            firstPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(playerController.getPlayer().getColor()) + ".jpg"));

        } else {
            Platform.runLater(() ->{
                playerController = Data.getInstance().getPlayerController();
                firstPlayerBoard.setImage(new Image("playerBoardFF/" + Converter.fromTokenColorToString(playerController.getPlayer().getColor()) + ".jpg"));
                resetSkulls(firstDeathCounterGrid);
            });
        }

        List<Player> otherPlayers = playerController.getOtherPlayers();
        if (otherPlayers.size() >= 1) {
            if(!otherPlayers.get(0).getPlayerBoard().isFinalFrenzy()) {
                Platform.runLater(() -> {

                    secondPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                    secondPlayerBoard.setVisible(true);
                });
            } else{
                Platform.runLater(() -> {

                    secondPlayerBoard.setImage(new Image("playerBoardFF/" + Converter.fromTokenColorToString(otherPlayers.get(0).getColor()) + ".jpg"));
                    secondPlayerBoard.setVisible(true);
                    resetSkulls(secondDeathCounterGrid);
                });
            }
        }
        if (otherPlayers.size() >= 2) {
            if(!otherPlayers.get(1).getPlayerBoard().isFinalFrenzy()) {
                Platform.runLater(() -> {

                    thirdPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(1).getColor()) + ".jpg"));
                    thirdPlayerBoard.setVisible(true);
                });
            } else{
                Platform.runLater(() -> {

                    thirdPlayerBoard.setImage(new Image("playerBoardFF/" + Converter.fromTokenColorToString(otherPlayers.get(1).getColor()) + ".jpg"));
                    thirdPlayerBoard.setVisible(true);
                    resetSkulls(thirdDeathCounterGrid);
                });
            }
        }
        if (otherPlayers.size() >= 3) {
            if(!otherPlayers.get(2).getPlayerBoard().isFinalFrenzy()) {
                Platform.runLater(() -> {

                    fourthPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(2).getColor()) + ".jpg"));
                    fourthPlayerBoard.setVisible(true);
                });
            } else {
                Platform.runLater(() -> {

                    fourthPlayerBoard.setImage(new Image("playerBoardFF/" + Converter.fromTokenColorToString(otherPlayers.get(2).getColor()) + ".jpg"));
                    fourthPlayerBoard.setVisible(true);
                    resetSkulls(fourthDeathCounterGrid);
                });
            }

        }
        if(otherPlayers.size() == 4){
            if(!otherPlayers.get(3).getPlayerBoard().isFinalFrenzy()) {

                Platform.runLater(() -> {

                    fifthPlayerBoard.setImage(new Image("playerBoard/" + Converter.fromTokenColorToString(otherPlayers.get(3).getColor()) + ".jpg"));
                    fifthPlayerBoard.setVisible(true);
                });
            } else {
                Platform.runLater(() -> {

                    fifthPlayerBoard.setImage(new Image("playerBoardFF/" + Converter.fromTokenColorToString(otherPlayers.get(3).getColor()) + ".jpg"));
                    fifthPlayerBoard.setVisible(true);
                    resetSkulls(fifthDeathCounterGrid);
                });
            }
        }
    }

    /**
     * reset every gridPane's elements
     * @param gridPane generic gridpane of various players
     */
    private void resetSkulls(GridPane gridPane) {
        Platform.runLater(() ->{
            gridPane.getChildren().clear();
        });
    }

    /**
     * this method is used to start the playerBoardGUI thread
     */
    public void setAll() {

        Thread thread2 = new Thread(this::checkPlayerBoards);
        thread2.setDaemon(true);
        thread2.start();
    }

    /**
     * this method check every playerBoard elements periodically, it sleeps every 10 seconds
     */
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

                Thread.sleep(TIME_CHECK);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
