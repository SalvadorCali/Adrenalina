package it.polimi.ingsw.controller.datas;

import it.polimi.ingsw.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains datas for the move and reload action, used before the shoot during the final frenzy.
 */
public class MoveAndReloadData {
    /**
     * First direction of the move action.
     */
    private Direction firstDirection;
    /**
     * Second direction of the move action.
     */
    private Direction secondDirection;
    /**
     * A list of weapons to reload.
     */
    private List<String> weapons = new ArrayList<>();

    /**
     * Class constructor.
     * @param firstDirection the first direction.
     * @param secondDirection the second direction.
     * @param weaponNames the list of weapon's names to reload.
     */
    public MoveAndReloadData(Direction firstDirection, Direction secondDirection, String...weaponNames){
        this.firstDirection = firstDirection;
        this.secondDirection = secondDirection;
        for(String weapon : weaponNames){
            weapons.add(weapon);
        }
    }

    /**
     * Getter for the first direction.
     * @return the first direction.
     */
    public Direction getFirstDirection() {
        return firstDirection;
    }

    /**
     * Setter for the first direction.
     * @param firstDirection the direction that will be set.
     */
    public void setFirstDirection(Direction firstDirection) {
        this.firstDirection = firstDirection;
    }

    /**
     * Getter for the second direction.
     * @return the second direction.
     */
    public Direction getSecondDirection() {
        return secondDirection;
    }

    /**
     * Setter for the second direction.
     * @param secondDirection the direction that will be set.
     */
    public void setSecondDirection(Direction secondDirection) {
        this.secondDirection = secondDirection;
    }

    /**
     * Getter for the weapons.
     * @return a list of Strings that represent weapons' names.
     */
    public List<String> getWeapons() {
        return weapons;
    }

    /**
     * Setter for the weapons.
     * @param weapons a list of String that will be set.
     */
    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }
}
