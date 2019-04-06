package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

public class AmmoPoint extends Square {
    private AmmoCard ammoCard;
    public AmmoPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
    }

    public AmmoCard getAmmoCard() {
        return ammoCard;
    }

    public void setAmmoCard(AmmoCard ammoCard) {
        this.ammoCard = ammoCard;
    }

    @Override
    public void grab(ActionInterface actionInterface, int choice) {
        if(ammoCard.isPowerup()){
            actionInterface.addAmmo(ammoCard.getFirstAmmo(), ammoCard.getSecondAmmo());
            actionInterface.addPowerup();
        }
        else{
            actionInterface.addAmmo(ammoCard.getFirstAmmo(), ammoCard.getSecondAmmo(), ammoCard.getThirdAmmo());
        }
    }
}
