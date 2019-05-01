package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ServerInterface {
    void disconnect() throws RemoteException;
    void notify(Message message) throws IOException;
    void notify(Message message, Outcome outcome) throws IOException;
    void notify(Message message, Outcome outcome, Object object) throws IOException;
}
