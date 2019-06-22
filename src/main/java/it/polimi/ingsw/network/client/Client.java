package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.NetworkString;
import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.util.Connection;
import it.polimi.ingsw.util.Printer;
import it.polimi.ingsw.view.gui.GUIHandler;
import javafx.application.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.rmi.RemoteException;
import java.util.Objects;

public class Client {
    private static final String DEFAULT = "default";
    private static final String CLI = "cli";
    private static final String GUI = "gui";
    private static final String RMI = "rmi";
    private static final String SOCKET = "socket";

    public static void main(String[] args) throws SocketException {
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        InetAddress address = Connection.getAddress();
        System.setProperty(NetworkString.PROPERTY, address.toString().substring(1));

        String graphicInterface = DEFAULT;
        while(!(graphicInterface.equalsIgnoreCase(CLI) || graphicInterface.equalsIgnoreCase(GUI))) {
            try {
                Printer.print(NetworkString.UI_CHOOSE);
                graphicInterface = userInputStream.readLine();
            } catch (IOException e) {
                Printer.err(e);
            }
        }
        if(graphicInterface.equals(GUI)){
            Application.launch(GUIHandler.class, args);
        }else{
            String choice = DEFAULT;
            while(!(choice.equalsIgnoreCase(RMI) || choice.equalsIgnoreCase(SOCKET))) {
                try {
                    Printer.print(NetworkString.CONNECTION_CHOOSE);
                    choice = userInputStream.readLine();
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
            ClientInterface client = null;
            if(choice.equals(RMI)){
                try {
                    client = new RMIClient();
                } catch (RemoteException e) {
                    Printer.err(e);
                }
            }else{
                    client = new SocketClient();
            }
            if(Objects.nonNull(client)){
                try {
                    client.start();
                } catch (RemoteException e) {
                    Printer.err(e);
                }
            }
        }
    }
}
