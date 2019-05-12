package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

public interface ViewInterface {
    void start();
    void setPlayerController(PlayerController playerController);
    void notify(Message message);
    void notify(Message message, Outcome outcome);
    void notify(Message message, Outcome outcome, Object object);
}
