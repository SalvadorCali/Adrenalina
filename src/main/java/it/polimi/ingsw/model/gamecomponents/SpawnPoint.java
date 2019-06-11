package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Cardinal;
import it.polimi.ingsw.model.enums.TokenColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * class which represents the spawn point squares in the arena.
 */
public class SpawnPoint extends Square implements Serializable {

    private List<WeaponCard> weapons;

    /**
     * constructor of the SpawnPoint class.
     * @param color indicates the color of the spawn point.
     * @param north indicates the cardinal on the upper side of the square.
     * @param south indicates the cardinal on the lower side of the square.
     * @param west indicates the cardinal on the left side of the square
     * @param east indicates the cardinal on the right side of the square.
     */
    public SpawnPoint(TokenColor color, Cardinal north, Cardinal south, Cardinal west, Cardinal east) {
        super(color, north, south, west, east, true);
        this.weapons = new ArrayList<>();
    }

    /**
     * getter for the list of weapons present in the spawn point.
     * @return the list of weapons present in the spawn point.
     */
    @Override
    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    /**
     * setter for the list of weapons present in the spawn point.
     * @param weapons chosen weapons to set in the spawn point.
     */
    public void setWeapons(List<WeaponCard> weapons) {
        this.weapons = weapons;
    }

    /**
     * verifies if a weapon in the spawn point can be grabbed.
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     * @return the result of the control, true if the weapon can be grabbed, false if can't.
     */
    @Override
    public boolean canGrab(ActionInterface actionInterface, int choice){
        return choice >= 1 && choice <= 3 && !isEmpty() && weapons.get(choice - 1).ammoControl(actionInterface.getCurrentPlayer()) &&
                actionInterface.getCurrentPlayer().getWeapons().size() < 3;
    }

    /**
     * grabs the chosen weapon from the spawn point.
     * @param actionInterface contains the methods to access the game.
     * @param choice int which indicates the choice of the player.
     */
    @Override
    public void grab(ActionInterface actionInterface, int choice) {
        weapons.get(choice - 1).load();
        actionInterface.addWeapon(weapons.get(choice - 1));
        actionInterface.updateAmmoBox(weapons.get(choice - 1).getGrabRedAmmos(),weapons.get(choice - 1).getGrabBlueAmmos(),weapons.get(choice - 1).getGrabYellowAmmos());
        weapons.remove(choice-1);
        setEmpty(true);
    }

    /**
     * fills the spawn point with new weapons.
     * @param actionInterface contains the methods to access the game.
     */
    @Override
    public void fill(ActionInterface actionInterface){
        int size = weapons.size();
        for(int i=0; i<(3 - size); i++){
            if(actionInterface.canGetWeapon()){
                weapons.add(actionInterface.getWeapon());
            }
        }
        setEmpty(false);
        /*
        List<WeaponCard> newWeapons = new ArrayList<>();
        newWeapons.add(actionInterface.getWeapon());
        newWeapons.add(actionInterface.getWeapon());
        newWeapons.add(actionInterface.getWeapon());
        setWeapons(newWeapons);
        */
    }

    /**
     * fills the spawn point with a new weapon
     * @param weapon indicates the weapon to add in the spawn point
     */
    @Override
    public void drop(Card weapon){
        weapons.add((WeaponCard)weapon);
    }

    /**
     * getter of the ammo card present in the square(Never Used)
     * @return always null
     */
    @Override
    public AmmoCard getAmmoCard() {
        return null;
    }

    /**
     * controls it the square is an active square.
     * @return true, cause the spawn point is an active square.
     */
    @Override
    public boolean isActive(){
        return true;
    }

    public void spawn(Player player){
        //to implement
    }

}
