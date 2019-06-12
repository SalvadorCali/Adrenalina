package it.polimi.ingsw.network.client;

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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

public class Client {
    public static void main(String[] args) throws SocketException {
        /*
        Printer.print("[CLIENT]Please, set an ip address:");
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        try {
            String host = userInputStream.readLine();
            System.setProperty("java.rmi.server.hostname", host);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        /*
        List<InetAddress> addresses = Connection.getAddresses();
        if(!addresses.isEmpty()){
            //Printer.println(addresses.get(0).toString().substring(1));
            if(System.getProperty("os.name").contains("ind")){
                System.setProperty("java.rmi.server.hostname", addresses.get(1).toString().substring(1));
            }else{
                System.setProperty("java.rmi.server.hostname", addresses.get(0).toString().substring(1));
            }
        }
        */
        InetAddress address = Connection.getAddress();
        System.setProperty("java.rmi.server.hostname", address.toString().substring(1));

        String graphicInterface = "default";
        while(!(graphicInterface.equalsIgnoreCase("cli") || graphicInterface.equalsIgnoreCase("gui"))) {
            try {
                Printer.print("[CLIENT]Choose 'cli' or 'gui':");
                graphicInterface = userInputStream.readLine();
            } catch (IOException e) {
                Printer.err(e);
            }
        }

        if(graphicInterface.equals("gui")){
            Application.launch(GUIHandler.class, args);
        }else{
            String choice = "default";
            while(!(choice.equalsIgnoreCase("rmi") || choice.equalsIgnoreCase("socket"))) {
                try {
                    Printer.print("[CLIENT]Choose 'rmi' or 'socket':");
                    choice = userInputStream.readLine();
                } catch (IOException e) {
                    Printer.err(e);
                }
            }

            ClientInterface client = null;

            if(choice.equals("rmi")){
                try {
                    client = new RMIClient();
                } catch (IOException | NotBoundException e) {
                    Printer.err(e);
                }
            }else{
                try {
                    client = new SocketClient();
                } catch (IOException e) {
                    Printer.err(e);
                }
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
