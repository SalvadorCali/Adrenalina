package it.polimi.ingsw.view;

import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

public interface ViewInterface {
    void start();
    void notifyLogin(Outcome outcome, String username);
    void notify(Message message);
    void notify(Message message, Outcome outcome);
    void notify(Message message, Outcome outcome, Object object);
    void printMessage(Advise advise);
}
