package it.polimi.ingsw.view;

import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Response;

public interface ViewInterface {
    void start();
    void notifyLogin(Response response, String username);
    void printMessage(Advise advise);
}
