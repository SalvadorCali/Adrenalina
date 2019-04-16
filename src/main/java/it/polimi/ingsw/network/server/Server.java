package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ServerController;
import it.polimi.ingsw.network.ConnectionManager;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {
    public static void main(String[] args) {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            Printer.println("[SERVER]Current ip address: " + ip);
            System.setProperty("java.rmi.server.hostname", ip);
        } catch (UnknownHostException e) {
            Printer.err(e);
        }
        try {
            ConnectionManager connection = new ConnectionManager(new ServerController());
            connection.start();
        } catch (IOException e) {
            Printer.err(e);
        }
    }
}
