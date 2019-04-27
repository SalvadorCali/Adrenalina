package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;

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
            client.testConnection();
            try {
                sleep(Config.DISCONNECTION_TIME);
            } catch (InterruptedException e) {
                server.disconnect();
            }
        }

    }
}
