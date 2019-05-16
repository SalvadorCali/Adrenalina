package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Printer;

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
    @Override
    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<WeaponCard> weapons) {
        this.weapons = weapons;
    }

    //methods
    @Override
    public boolean canGrab(int choice){
        return choice >= 1 && choice <= 3 && !isEmpty();
    }

    @Override
    public void grab(ActionInterface actionInterface, int choice) {
        actionInterface.addWeapon(weapons.get(choice - 1));
        weapons.remove(choice-1);
        setEmpty(true);
    }

    @Override
    public void fill(ActionInterface actionInterface){
        int size = weapons.size();
        for(int i=0; i<(3 - size); i++){
            weapons.add(actionInterface.getWeapon());
        }
        /*
        List<WeaponCard> newWeapons = new ArrayList<>();
        newWeapons.add(actionInterface.getWeapon());
        newWeapons.add(actionInterface.getWeapon());
        newWeapons.add(actionInterface.getWeapon());
        setWeapons(newWeapons);
        */
    }

    @Override
    public AmmoCard getAmmoCard() {
        return null;
    }

    public void spawn(Player player){
        //to implement
    }

}
