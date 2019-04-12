package it.polimi.ingsw.network;

import it.polimi.ingsw.util.Printer;

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

    }
}
