package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.util.Converter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;


public class LoginGUI extends Application implements Initializable {

    @FXML
    RadioButton socketButton;

    @FXML
    RadioButton rmiButton;

    @FXML
    TextField nicknameField;

    @FXML
    TextField addressField;

    @FXML
    TextField colorField;

    @FXML
    Label statusConnectionLabel;

    @FXML
    Button loginButton;

    @FXML
    Label connectionErrorLabel;

    private String playerName;
    private String address;
    private String colorPlayer;
    boolean isRunning = true;
    private ClientInterface client;
    private ChoosePowerup choosePowerup;

    public synchronized void start(Stage primaryStage) throws Exception {

        Parent adrenalineLog = FXMLLoader.load(getClass().getClassLoader().getResource("LoginGUI.fxml"));
        Scene scene = new Scene(adrenalineLog, 600, 470);

        primaryStage.setTitle("Adrenaline Login");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public synchronized void clickButton(String playerName, String address, String colorPlayer){

        if(rmiButton.isSelected()){
            connectToRMI(playerName, address, colorPlayer);
        }

        if(socketButton.isSelected()){
            connectToSocket(playerName, address, colorPlayer);
        }

        if(!rmiButton.isSelected() && !socketButton.isSelected()){
            connectionErrorLabel.setText("Choose connection method");
        }
    }

    public synchronized void connectToRMI(String name, String host, String color){

        try{
            client = new RMIClient(host);
            //client.start();
            client.login(name, Converter.fromStringToTokenColor(color));


        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void connectToSocket(String name, String host, String color){

        System.out.println(name + host + color);

        try {
            client = new SocketClient(host);
            //client.start();
            client.login(name, Converter.fromStringToTokenColor(color));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Thread thread = new Thread(this::handleThread);
        thread.setDaemon(true);
        thread.start();

        statusConnectionLabel.setText("Offline");
    }

    public void handleThread(){

        while(isRunning){

            Platform.runLater(() -> {

                loginButton.setOnAction(actionEvent ->  {

                    statusConnectionLabel.setText("Waiting...");
                    tryConnection();
                });

            });


            try{

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void tryConnection(){

        playerName = nicknameField.getText();
        address = addressField.getText();
        colorPlayer = colorField.getText();

        if(playerName.equals("")){
            connectionErrorLabel.setText("Userfield can't be empty");
        }

        else if(address.equals("")){
            connectionErrorLabel.setText("Ip can't be empty");
        }

        else if(colorPlayer.equals("")){
            connectionErrorLabel.setText("Color can't be empty");
        }

        else{
            clickButton(playerName, address, colorPlayer);
        }

    }

    public void dispose(){

        isRunning = false;
        statusConnectionLabel.setText("Closing Connection");
    }



    public synchronized void ifConnected(){

        statusConnectionLabel.setText("Online");
        //implement launch choosePowerup

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

