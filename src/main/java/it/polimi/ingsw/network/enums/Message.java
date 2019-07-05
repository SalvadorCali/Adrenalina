package it.polimi.ingsw.network.enums;

/**
 * This class contains the messages that Client and Server use to talk.
 */
public enum Message {
    LOGIN, DISCONNECT, MOVE, GRAB, SHOOT, NOTIFY, COLOR, USERNAME, PLAYER, END_TURN, NEW_TURN, GAME, RECONNECTION,
    SPAWN, SQUARE, POWERUP, RELOAD, SQUARE_XY, BOARD, SCORE, FINAL_FRENZY, MOVE_RELOAD_1,
    MOVE_RELOAD_2, NOT_TURN, DROP_POWERUP, DROP_WEAPON, DISCARD_POWERUP, RESPAWN, END_GAME
}
