package it.polimi.ingsw.controller.timer;

import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.util.Config;
import it.polimi.ingsw.util.Printer;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class represents a timer to handles the connection.
 */
public class ConnectionTimer extends Thread implements Serializable {
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
     * @param client the relative Client.
     */
    public ConnectionTimer(ClientInterface client){
        this.client = client;
    }

    /**
     * Setter for the Server.
     * @param server the Server that will be set.
     */
    public void setServer(ServerInterface server){
        this.server = server;
    }

    /**
     * Sleeps for a while and then calls the {@link ClientInterface#testConnection()} method that is an empty method. If an exception is thrown it calls the {@link ServerInterface#disconnect()} method.
     */
    @Override
    public void run(){
        boolean loop = true;
        while(loop){
            try {
                client.testConnection();
            } catch (RemoteException e) {
                try {
                    server.disconnect();
                } catch (RemoteException e1) {
                    Printer.err(e1);
                }
                loop = false;
            }
            try {
                sleep(Config.DISCONNECTION_TIME);
            } catch (InterruptedException e) {
                Printer.err(e);
            }
        }
    }
}
