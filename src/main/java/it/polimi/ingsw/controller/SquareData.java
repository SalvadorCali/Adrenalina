package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;

import java.io.Serializable;
import java.util.List;

public class SquareData implements Serializable {
    private AmmoCard ammoCard;
    private List<WeaponCard> weapons;
    public SquareData(){
    }

    public AmmoCard getAmmoCard() {
        return ammoCard;
    }

    public void setAmmoCard(AmmoCard ammoCard) {
        this.ammoCard = ammoCard;
    }

    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<WeaponCard> weapons) {
        this.weapons = weapons;
    }
}
