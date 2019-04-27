package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

import java.io.IOException;

public interface ServerInterface {
    void disconnect();
    void notify(Message message) throws IOException;
    void notify(Message message, Outcome outcome) throws IOException;
    void notify(Message message, Outcome outcome, Object object) throws IOException;
}
