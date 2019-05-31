package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DamageMarkEffect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.DirectionalDamage;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.MovementEffect;
import it.polimi.ingsw.model.cards.effects.weapons.basiceffects.SquareDamageEffect;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalMove;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalSquareDamage;
import it.polimi.ingsw.model.cards.effects.weapons.singleaddictions.AdditionalTarget;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.model.gamecomponents.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeaponCard extends Card{

    private int weaponId;

    private int grabRedAmmos, grabBlueAmmos, grabYellowAmmos;

    private int reloadRedAmmos, reloadBlueAmmos, reloadYellowAmmos;

    private List<Effect> effects = new ArrayList<>();

    public WeaponCard(String name, Color color, String effect, int grabRedAmmos, int grabBlueAmmos, int grabYellowAmmos, int reloadRedAmmos, int reloadBlueAmmos, int reloadYellowAmmos) {

        super(name, color, effect);
        this.grabRedAmmos = grabRedAmmos;
        this.grabBlueAmmos = grabBlueAmmos;
        this.grabYellowAmmos = grabYellowAmmos;
        this.reloadRedAmmos = reloadRedAmmos;
        this.reloadBlueAmmos = reloadBlueAmmos;
        this.reloadYellowAmmos = reloadYellowAmmos;
        setEffects(name);
    }

    public WeaponCard(String name, Color color){
        super(name,color);
    }

    private void setEffects(String name){

        switch (name){
            case ("LOCK RIFLE"):
                Effect lockRifle = new DamageMarkEffect("Lock Rifle", 2,1, 0,0,0);
                Effect lockRifleAdd = new AdditionalTarget("Lock Rifle", 0, 1,1,0,0, lockRifle);
                effects.add(lockRifle);
                effects.add(lockRifleAdd);
                break;
            case ("ELECTROSCYTHE"):
                Effect electroscythe1 = new SquareDamageEffect("Electroscythe", 1,0, 0,0,0);
                Effect electroscythe2 = new SquareDamageEffect("Electroscythe", 2,0,1,1,0);
                effects.add(electroscythe1);
                effects.add(electroscythe2);
                break;
            case ("MACHINE GUN"):
                Effect machineGun = new DamageMarkEffect("Machine Gun", 1,0, 0,0,0);
                Effect machineGunAdd = new AdditionalTarget("Machine Gun", 1,0,0,0,1, machineGun);
                Effect machineGunAdd2 = new AdditionalTarget("Machine Gun Double", 1,0,0,1,0, machineGunAdd);
                effects.add(machineGun);
                effects.add(machineGunAdd);
                effects.add(machineGunAdd2);
                break;
            case("TRACTOR BEAM"):
                Effect tractorBeam1 = new MovementEffect("Tractor Beam1",1,0,0,0,0);
                Effect tractorBeam2 = new MovementEffect("Tractor Beam2", 3,0,1,0,1);
                effects.add(tractorBeam1);
                effects.add(tractorBeam2);
                break;
            case ("T.H.O.R"):
                Effect thor = new DamageMarkEffect("T.H.O.R.",2,0,0,0,0);
                Effect thorAdd = new AdditionalTarget("T.H.O.R. Single",1,0,0,1,0, thor);
                Effect thorAdd2 = new AdditionalTarget("T.H.O.R. Double",2,0,0,1,0, thorAdd);
                effects.add(thor);
                effects.add(thorAdd);
                effects.add(thorAdd2);
                break;
            case ("VORTEX CANNON"):
                break;
            case ("FURNACE"):
                Effect furnace1 = new SquareDamageEffect("Furnace1", 1,0, 0,0,0);
                Effect furnace2 = new SquareDamageEffect("Furnace2", 1,1,0,0,0);
                effects.add(furnace1);
                effects.add(furnace2);
                break;
            case("PLASMA GUN"):
                Effect plasmaGun = new DamageMarkEffect("Plasma Gun",2,0,0,0,0);
                Effect plasmaGunAdd = new AdditionalMove("Plasma Gun",0,0,0,0,0, plasmaGun);
                Effect plasmaGunAdd2 = new AdditionalTarget("Plasma Gun Double",1,0,0,1,0, plasmaGunAdd);
                effects.add(plasmaGun);
                effects.add(plasmaGunAdd);
                effects.add(plasmaGunAdd2);
                break;
            case ("HEATSEEKER"):
                Effect heatseeker = new DamageMarkEffect("Heatseeker",3,0,0,0,0);
                effects.add(heatseeker);
                break;
            case ("WHISPER"):
                Effect whisper = new DamageMarkEffect("Whisper", 3,1,0,0,0);
                effects.add(whisper);
                break;
            case("HELLION"):
                Effect hellion1 = new SquareDamageEffect("Hellion",1,1,0,0,0 );
                Effect hellion2 = new SquareDamageEffect("Hellion",1,2,1,0,0 );
                effects.add(hellion1);
                effects.add(hellion2);
                break;
            case ("FLAMETHROWER"):
                Effect flamethrower1 = new DirectionalDamage("Flamethrower1",1,0,0,0 );
                Effect flamethrower2 = new DirectionalDamage("Flamethrower2",1,0,0,2 );
                effects.add(flamethrower1);
                effects.add(flamethrower2);
                break;
            case ("ZX-2"):
                Effect zx21 = new DamageMarkEffect("ZX-21",1,2,0,0,0);
                Effect zx22 = new DamageMarkEffect("ZX-22", 0,1,0,0,0);
                effects.add(zx21);
                effects.add(zx22);
                break;
            case ("GRENADE LAUNCHER"):
                Effect grenadeLauncher = new MovementEffect("Grenade Launcher",1,0,0,0,0);
                Effect grenadeLauncherAdd = new AdditionalSquareDamage("Grenade Launcher",1,1,0,0, grenadeLauncher);
                effects.add(grenadeLauncher);
                effects.add(grenadeLauncherAdd);
                break;
            case("SHOTGUN"):
                Effect shotgun1 = new MovementEffect("Shotgun1",3,0,0,0,0);
                Effect shotgun2 = new MovementEffect("Shotgun2",2,0,0,0,0);
                effects.add(shotgun1);
                effects.add(shotgun2);
                break;
            case("ROCKET LAUNCHER"):
                Effect rocketLauncher = new MovementEffect("Rocket Launcher",2,0,0,0,0);
                Effect rocketLauncherAdd = new AdditionalMove("Rocket Launcher",0,0,0,1,0, rocketLauncher);
                Effect rocketLauncherAdd2 = new AdditionalSquareDamage("Rocket Launcher",1,0,0,1, rocketLauncherAdd);
                effects.add(rocketLauncher);
                effects.add(rocketLauncherAdd);
                effects.add(rocketLauncherAdd2);
                break;
            case ("POWER GLOVE"):
                Effect powerGlove1 = new MovementEffect("Power Glove1", 1,2,0,0,0);
                Effect powerGlove2 = new DirectionalDamage("Power Glove2",2,0,1,0);
                effects.add(powerGlove1);
                effects.add(powerGlove2);
                break;
            case("RAILGUN"):
                Effect railgun1 = new DirectionalDamage("Railgun1",3,0,0,0);
                Effect railgun2 = new DirectionalDamage("Railgun2", 2,0,0,0);
                effects.add(railgun1);
                effects.add(railgun2);
                break;
            case("CYBERBLADE"):
                Effect cyberblade = new DamageMarkEffect("Cyberblade",2,0,0,0,0);
                Effect cyberbladeAdd = new AdditionalMove("Cyberblade",0,0,0,0,0, cyberblade);
                Effect cyberbladeAdd2 = new AdditionalTarget("Cyberblade",2,0,0,0,1,cyberbladeAdd);
                effects.add(cyberblade);
                effects.add(cyberbladeAdd);
                effects.add(cyberbladeAdd2);
                break;
            case ("SLEDGEHAMMER"):
                Effect sledgehammer1 = new DamageMarkEffect("Sledgehammer",2,0,0,0,0);
                Effect sledgehammer2 = new MovementEffect("Sledgehammer",3,0,1,0,0);
                effects.add(sledgehammer1);
                effects.add(sledgehammer2);
                break;
            case ("SHOCKWAVE"):
                Effect shockwave1 = new DamageMarkEffect("Shockwave",1,0,0,0,0);
                effects.add(shockwave1);
                break;
            default:
                break;
        }
    }

    public int getGrabRedAmmos(){
        return grabRedAmmos;
    }

    public int getGrabBlueAmmos(){
        return grabBlueAmmos;
    }

    public int getGrabYellowAmmos(){
        return grabYellowAmmos;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public boolean ammoControl(Player currentPlayer){
        return grabRedAmmos <= currentPlayer.getRedAmmo() && grabBlueAmmos <= currentPlayer.getBlueAmmo()&& grabYellowAmmos <= currentPlayer.getYellowAmmo();
    }
}
