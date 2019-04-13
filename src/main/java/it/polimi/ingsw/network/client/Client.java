package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.rmi.RMIClient;
import it.polimi.ingsw.network.client.socket.SocketClient;
import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Client {
    public static void main(String[] args){
        Printer.print("[CLIENT]Choose 'rmi' or 'socket':");
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        String choice = "default";
        try {
            choice = userInputStream.readLine();
        } catch (IOException e) {
            Printer.err(e);
        }
        ClientInterface client = null;
        while(!(choice.equalsIgnoreCase("rmi") || choice.equalsIgnoreCase("socket"))){
            if(choice.equals("rmi")){
                client = new RMIClient();
            }else{
                try {
                    client = new SocketClient();
                } catch (IOException e) {
                    Printer.err(e);
                }
            }
        }
        if(Objects.nonNull(client)){
            client.start();
        }
    }
}
