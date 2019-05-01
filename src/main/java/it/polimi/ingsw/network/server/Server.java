package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.ConnectionManager;
import it.polimi.ingsw.util.Printer;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Server {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        //1st method
        List<InetAddress> addresses = getAddresses();
        Printer.println("[SERVER]Current ip addresses:");
        addresses.forEach(a -> Printer.println("    " + a));
        if(!addresses.isEmpty()){
            //Printer.println(addresses.get(0).toString().substring(1));
            System.setProperty("java.rmi.server.hostname", addresses.get(0).toString());
        }
        try {
            ConnectionManager connection = new ConnectionManager();
            connection.start();
        } catch (IOException e) {
            Printer.err(e);
        }
    }

    private static List<InetAddress> getAddresses() throws SocketException{
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        List<InetAddress> addressesList = new ArrayList<>();
        while(networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while(addresses.hasMoreElements()){
                InetAddress address = addresses.nextElement();
                if(address instanceof Inet4Address && !address.isLoopbackAddress()){
                    addressesList.add(address);
                }
            }
        }
        return addressesList;
    }
}
