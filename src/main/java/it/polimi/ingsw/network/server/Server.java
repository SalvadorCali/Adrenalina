package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.ConnectionManager;
import it.polimi.ingsw.controller.ServerControllerManager;
import it.polimi.ingsw.network.NetworkString;
import it.polimi.ingsw.util.Connection;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.*;

public class Server {
    private ServerControllerManager serverControllerManager = new ServerControllerManager();
    public static void main(String[] args) throws UnknownHostException, SocketException {
        InetAddress address = Connection.getAddress();
        Printer.println(NetworkString.IP_SERVER);
        Printer.println("   " + address);
        System.setProperty(NetworkString.PROPERTY, address.toString().substring(1));
        try {
            ConnectionManager connection = new ConnectionManager();
            connection.start();
        } catch (IOException e) {
            Printer.err(e);
        }
    }
}
