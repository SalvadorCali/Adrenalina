package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.MovementEffect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.SquareDamageEffect;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalSquareDamage;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalTarget;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.cards.effects.Effect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeaponCard extends Card{

    private int weaponId;

    private int grabRedAmmos, grabBlueAmmos, grabYellowAmmos;

    private int reloadRedAmmos, reloadBlueAmmos, reloadYellowAmmos;

    private List<Effect> effects = new ArrayList<>();

    public WeaponCard(String name, Color color, int grabRedAmmos, int grabBlueAmmos, int grabYellowAmmos, int reloadRedAmmos, int reloadBlueAmmos, int reloadYellowAmmos) {

        super(name, color);
        this.grabRedAmmos = grabRedAmmos;
        this.grabBlueAmmos = grabBlueAmmos;
        this.grabYellowAmmos = grabYellowAmmos;
        this.reloadRedAmmos = reloadRedAmmos;
        this.reloadBlueAmmos = reloadBlueAmmos;
        this.reloadYellowAmmos = reloadYellowAmmos;
    }


    private void setEffects(String name){

        switch (name){
            case ("LOCK RIFLE"):
                Effect lockRifle = new DamageMarkEffect("Lock Rifle", 2,1, 0,0,0);
                Effect lockRifleAdd = new AdditionalTarget("Lock Rifle", 0, 1,1,0,0, lockRifle);
                break;
            case ("ELECTROSCYTHE"):
                Effect electroscythe1 = new SquareDamageEffect("Electroscythe", 1,0, 0,0,0);
                Effect electroscythe2 = new SquareDamageEffect("Electroscythe", 2,0,1,1,0);
                break;
            case ("MACHINE GUN"):
                Effect machineGun = new DamageMarkEffect("Machine Gun", 1,0, 0,0,0);
                Effect machineGunAdd = new AdditionalTarget("Machine Gun", 1,0,0,0,1, machineGun);
                Effect machineGunAdd2 = new AdditionalTarget("Machine Gun Double", 1,0,0,1,0, machineGunAdd);
                break;
            case("TRACTOR BEAM"):
                Effect tractorBeam1 = new MovementEffect("Tractor Beam1",1,0,0,0,0);
                Effect tractorBeam2 = new MovementEffect("Tractor Beam2", 3,0,1,0,1);
                break;
        }

    }

    public List<Effect> getEffects() {
        return effects;
    }
}
