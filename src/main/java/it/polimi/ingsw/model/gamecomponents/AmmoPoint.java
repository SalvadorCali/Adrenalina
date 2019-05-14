package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.List;

public class AmmoPoint extends Square implements Serializable {
    private AmmoCard ammoCard;
    public AmmoPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east, false);
    }

    //getters and setters
    public AmmoCard getAmmoCard() {
        return ammoCard;
    }

    @Override
    public List<WeaponCard> getWeapons() {
        return null;
    }

    public void setAmmoCard(AmmoCard ammoCard) {
        this.ammoCard = ammoCard;
    }

    //methods
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
    @Override
    public void fill(ActionInterface actionInterface){
        setAmmoCard(actionInterface.getAmmo());
    }
}
