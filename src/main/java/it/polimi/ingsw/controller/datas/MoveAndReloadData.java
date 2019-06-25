package it.polimi.ingsw.controller.datas;

import it.polimi.ingsw.model.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class MoveAndReloadData {
    private Direction firstDirection;
    private Direction secondDirection;
    private List<String> weapons = new ArrayList<>();
    public MoveAndReloadData(Direction firstDirection, Direction secondDirection, String...weaponNames){
        this.firstDirection = firstDirection;
        this.secondDirection = secondDirection;
        for(String weapon : weaponNames){
            weapons.add(weapon);
        }
    }

    public Direction getFirstDirection() {
        return firstDirection;
    }

    public void setFirstDirection(Direction firstDirection) {
        this.firstDirection = firstDirection;
    }

    public Direction getSecondDirection() {
        return secondDirection;
    }

    public void setSecondDirection(Direction secondDirection) {
        this.secondDirection = secondDirection;
    }

    public List<String> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }
}
