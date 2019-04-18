package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ServerInterface {
    void notifyLogin(Subject subject, String username) throws IOException;
    void notify(Message message, Subject subject, Object object) throws RemoteException;
    void sendMessage(Advise advise) throws IOException;
}
