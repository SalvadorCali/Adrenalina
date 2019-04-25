package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;

public interface ServerInterface {
    void notifyLogin(Outcome outcome, String username) throws IOException;
    void notify(Message message) throws IOException;
    void notify(Message message, Outcome outcome) throws IOException;
    void notify(Message message, Outcome outcome, Object object) throws IOException;
    void sendMessage(Advise advise) throws IOException;
}
