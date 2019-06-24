package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.PlayerController;
import it.polimi.ingsw.network.enums.Message;
import it.polimi.ingsw.network.enums.Outcome;

/**
 * Methods of this class will be implemented by the cli and the gui main classes.
 */
public interface ViewInterface {
    /**
     * Starts the view interface.
     */
    void start();

    /**
     * Setter for the PlayerController.
     * @param playerController the PlayerControlle that will be set.
     */
    void setPlayerController(PlayerController playerController);

    /**
     * Notify an action with a message.
     * @param message a message.
     */
    void notify(Message message);

    /**
     * Notify an action with a message and an outcome.
     * @param message a message.
     * @param outcome the outcome of the action.
     */
    void notify(Message message, Outcome outcome);

    /**
     * Notify an action with a message, an outcome and an object.
     * @param message a message.
     * @param outcome the outcome of the action.
     * @param object an object.
     */
    void notify(Message message, Outcome outcome, Object object);
}
