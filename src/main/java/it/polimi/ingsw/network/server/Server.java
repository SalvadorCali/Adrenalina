package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.CLIController;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.network.ConnectionManager;
import it.polimi.ingsw.util.Connection;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Server {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        /*
        List<InetAddress> addresses = Connection.getAddresses();
        Printer.println("[SERVER]Current ip addresses:");
        addresses.forEach(a -> Printer.println("    " + a));
        if(!addresses.isEmpty()){
            if(System.getProperty("os.name").contains("ind")){
                System.setProperty("java.rmi.server.hostname", addresses.get(1).toString().substring(1));
            }else{
                System.setProperty("java.rmi.server.hostname", addresses.get(0).toString().substring(1));
            }
            //Printer.println(addresses.get(0).toString().substring(1));

        }
        */

        //Printer.println("Corretto:");
        InetAddress address = Connection.getAddress();
        Printer.println("[SERVER]Current ip address:");
        Printer.println("   " + address);
        System.setProperty("java.rmi.server.hostname", address.toString().substring(1));
        try {
            ConnectionManager connection = new ConnectionManager(new ServerController());
            connection.start();
        } catch (IOException e) {
            Printer.err(e);
        }
    }
}
