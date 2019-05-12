package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpawnPoint extends Square implements Serializable {
    private List<WeaponCard> weapons;

    public SpawnPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east, true);
        this.weapons = new ArrayList<>();
    }

    //getters and setters
    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<WeaponCard> weapons) {
        this.weapons = weapons;
    }

    //methods
    @Override
    public void grab(ActionInterface actionInterface, int choice) {
        actionInterface.addWeapon(weapons.get(choice));
    }
    @Override
    public void fill(ActionInterface actionInterface){
        List<WeaponCard> newWeapons = new ArrayList<>();
        newWeapons.add(actionInterface.getWeapon());
        newWeapons.add(actionInterface.getWeapon());
        newWeapons.add(actionInterface.getWeapon());
        setWeapons(newWeapons);
    }

    public void spawn(Player player){
        //to implement
    }

}
