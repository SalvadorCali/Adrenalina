package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Response;

import java.io.IOException;

public interface ServerInterface {
    void notifyLogin(Response response, String username) throws IOException;
    void sendMessage(Advise advise) throws IOException;
}
