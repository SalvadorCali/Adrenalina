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

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the weapon cards in the game.
 */
public class WeaponCard extends Card{

    private int weaponId;

    private int grabRedAmmos, grabBlueAmmos, grabYellowAmmos;

    private int reloadRedAmmos, reloadBlueAmmos, reloadYellowAmmos;

    private boolean loaded;

    private List<Effect> effects = new ArrayList<>();

    /**
     * Class constructor.
     * @param name name of the weapon.
     * @param color color of the weapon.
     * @param effect effect of the weapon.
     * @param grabRedAmmos red ammos required to grab the weapon.
     * @param grabBlueAmmos blue ammos required to grab the weapon.
     * @param grabYellowAmmos yellow ammos required to grab the weapon.
     * @param reloadRedAmmos red ammos required to reload the weapon.
     * @param reloadBlueAmmos blue ammos required to reload the weapon.
     * @param reloadYellowAmmos yellow ammos required to reload the weapon.
     */
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

    /**
     * Constructor of the class.
     * @param name name of the weapon.
     * @param color color of the weapon.
     */
    public WeaponCard(String name, Color color){
        super(name,color);
    }

    /**
     * Sets the effects contained in the weapon.
     * @param name name of the weapon.
     */
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
            case ("T.H.O.R."):
                Effect thor = new DamageMarkEffect("T.H.O.R.",2,0,0,0,0);
                Effect thorAdd = new AdditionalTarget("T.H.O.R. Single",1,0,0,1,0, thor);
                Effect thorAdd2 = new AdditionalTarget("T.H.O.R. Double",2,0,0,1,0, thorAdd);
                effects.add(thor);
                effects.add(thorAdd);
                effects.add(thorAdd2);
                break;
            case ("VORTEX CANNON"):
                Effect vortexCannon = new SquareDamageEffect("Vortex Cannon",2,0,0,0,0);
                Effect vortexCannonAdd = new AdditionalSquareDamage("Vortex Cannon",1,1,0,0, vortexCannon);
                effects.add(vortexCannon);
                effects.add(vortexCannonAdd);
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
                Effect flamethrower2 = new DirectionalDamage("Flamethrower2",2,0,0,2 );
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
                Effect sledgehammer2 = new DirectionalDamage("Sledgehammer",3,1,0,0);
                effects.add(sledgehammer1);
                effects.add(sledgehammer2);
                break;
            case ("SHOCKWAVE"):
                Effect shockwave1 = new DamageMarkEffect("Shockwave",1,0,0,0,0);
                Effect shockwave2 = new SquareDamageEffect("Shockwave",1,0,0, 0,1);
                effects.add(shockwave1);
                effects.add(shockwave2);
                break;
            default:
                break;
        }
    }

    /**
     * Getter of the red ammos required to grab the weapon.
     * @return the red ammos required to grab the weapon.
     */
    public int getGrabRedAmmos(){
        return grabRedAmmos;
    }

    /**
     * Getter of the blue ammos required to grab the weapon.
     * @return the blue ammos required to grab the weapon.
     */
    public int getGrabBlueAmmos(){
        return grabBlueAmmos;
    }

    /**
     * Getter of the yellow ammos required to grab the weapon.
     * @return the yellow ammos required to grab the weapon.
     */
    public int getGrabYellowAmmos(){
        return grabYellowAmmos;
    }

    /**
     * Getter of the red ammos required to reload the weapon.
     * @return the red ammos required to reload the weapon.
     */
    public int getReloadRedAmmos(){
        return reloadRedAmmos;
    }

    /**
     * Getter of the blue ammos required to reload the weapon.
     * @return blue ammos required to reload the weapon.
     */
    public int getReloadBlueAmmos(){
        return reloadBlueAmmos;
    }

    /**
     * Getter of the yellow ammos required to reload the weapon.
     * @return yellow ammos required to reload the weapon.
     */
    public int getReloadYellowAmmos(){
        return reloadYellowAmmos;
    }

    /**
     * Getter of the list of the effects contained in the weapon card.
     * @return the Effects contained in the weapon card.
     */
    public List<Effect> getEffects() {
        return effects;
    }

    /**
     * Positive setter of the boolean loaded.
     */
    public void load(){
        loaded = true;
    }

    /**
     * Negative setter of the boolean loaded.
     */
    public void unload(){
        loaded = false;
    }

    /**
     * Getter of the boolean loaded.
     * @return the boolean loaded.
     */
    public boolean isLoaded(){
        return loaded;
    }

    /**
     * Controls that the weapon can be grabbed by the player.
     * @param currentPlayer player that is trying to grab the weapon.
     * @return the result of the control.
     */
    public boolean ammoControl(Player currentPlayer){
        return grabRedAmmos <= currentPlayer.getRedAmmo() && grabBlueAmmos <= currentPlayer.getBlueAmmo()&& grabYellowAmmos <= currentPlayer.getYellowAmmo();
    }

    /**
     * Controls that the weapon can be reloaded by the player.
     * @param currentPlayer player who is trying to reload the weapon.
     * @return the result of the control.
     */
    public boolean reloadAmmoControl(Player currentPlayer){
        return reloadRedAmmos <= currentPlayer.getRedAmmo() && reloadBlueAmmos <= currentPlayer.getBlueAmmo() && reloadYellowAmmos <= currentPlayer.getYellowAmmo();
    }

    /**
     * toString.
     * @return the string.
     */
    @Override
    public String toString(){
        StringBuilder card = new StringBuilder();
        card.append("Name: " + getName()).append("[Grab: " + grabBlueAmmos + "B" + grabRedAmmos + "R" + grabYellowAmmos + "Y][Reload: " + reloadBlueAmmos + "B" + reloadRedAmmos + "R" + reloadYellowAmmos + "Y]").append("\nColor: " + getColor()).append("\n" + getCardEffect()).append("\nLoaded: " + loaded);
        return card.toString();
    }
}
