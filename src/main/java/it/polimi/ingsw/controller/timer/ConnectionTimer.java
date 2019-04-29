package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.rmi.RemoteException;

public class ConnectionTimer extends Thread {
    private ClientInterface client;
    private ServerInterface server;
    public ConnectionTimer(ClientInterface client, ServerInterface server){
        this.client = client;
        this.server = server;
    }

    @Override
    public void run(){
        boolean loop = true;
        while(loop){
            try {
                client.testConnection();
            } catch (RemoteException e) {
                Printer.err(e);
            }
            try {
                sleep(Config.DISCONNECTION_TIME);
            } catch (InterruptedException e) {
                try {
                    server.disconnect();
                } catch (RemoteException e1) {
                    Printer.err(e1);
                }
                loop = false;
            }
        }

    }
}
