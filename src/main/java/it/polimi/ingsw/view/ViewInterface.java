package it.polimi.ingsw.view;

import it.polimi.ingsw.network.enums.Advise;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Subject;

public interface ViewInterface {
    void start();
    void notifyLogin(Subject subject, String username);
    void notify(Message message, Subject subject);
    void notify(Message message, Subject subject, Object object);
    void printMessage(Advise advise);
}
