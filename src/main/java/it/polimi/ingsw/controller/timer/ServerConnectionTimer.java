package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class represents a timer to handles the Server's connection.
 */
public class ServerConnectionTimer extends Thread implements Serializable {
    /**
     * The relative Client.
     */
    private ClientInterface client;
    /**
     * The relative Server.
     */
    private ServerInterface server;

    /**
     * Class constructor.
     * @param client the relative client.
     * @param server the relative server.
     */
    public ServerConnectionTimer(ClientInterface client, ServerInterface server){
        this.client = client;
        this.server = server;
    }

    /**
     * Sleeps for a while and then calls the {@link ServerInterface#testConnection()} method. If a remote exception is thrown, disconnects the Client.
     */
    @Override
    public void run(){
        boolean loop = true;
        while(loop){
            try {
                server.testConnection();
            } catch (RemoteException e) {
                Printer.println("server disconnected!");
                System.exit(1);
                loop = false;
            }
            try {
                sleep(Config.DISCONNECTION_TIME);
            } catch (InterruptedException e) {
                loop = false;
                Thread.currentThread().interrupt();
            }
        }
    }
}
