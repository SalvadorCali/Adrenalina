package it.polimi.ingsw.model.cards.effects;

import it.polimi.ingsw.model.enums.TokenColor;

public interface ActionInterface {
    
    void playerDamage(TokenColor color, int damagePower);

    void playerMark(TokenColor color, int markPower);

    boolean isVisible(TokenColor blue);
}
