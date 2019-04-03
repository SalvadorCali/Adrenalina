package it.polimi.ingsw.model;

import java.util.List;

public class SpawnPoint extends Square {
    private List<Card> weapons;

    public SpawnPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
    }
}
