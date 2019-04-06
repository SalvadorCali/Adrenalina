package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

import java.util.ArrayList;
import java.util.List;

public class SpawnPoint extends Square {

    private List<WeaponCard> weapons;

    public SpawnPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east);
        weapons = new ArrayList<>();
    }

    @Override
    public void grab(ActionInterface actionInterface, int choice) {
        actionInterface.addWeapon(weapons.get(choice));
    }

    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    public void spawn(Player player){
        //to implement
   }
}
