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

import static it.polimi.ingsw.model.cards.StringCards.*;

/**
 * Class representing the weapon cards in the game.
 */
public class WeaponCard extends Card{

    /**
     * Ammos required for grabbing the weapon.
     */
    private int grabRedAmmos, grabBlueAmmos, grabYellowAmmos;

    /**
     * Ammos required for reloading the weapon.
     */
    private int reloadRedAmmos, reloadBlueAmmos, reloadYellowAmmos;

    /**
     * Boolean that indicates if the weapon is loaded.
     */
    private boolean loaded;

    /**
     * Different effects in the weapon.
     */
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
            case (LOCKRIFLECAPS):
                Effect lockRifle = new DamageMarkEffect(LOCKRIFLEEFFECT, 2,1, 0,0,0);
                Effect lockRifleAdd = new AdditionalTarget(LOCKRIFLEEFFECT, 0, 1,1,0,0, lockRifle);
                effects.add(lockRifle);
                effects.add(lockRifleAdd);
                break;
            case (ELECTROSCYTHECAPS):
                Effect electroscythe1 = new SquareDamageEffect(ELECTROSCYTHEEFFECT, 1,0, 0,0,0);
                Effect electroscythe2 = new SquareDamageEffect(ELECTROSCYTHEEFFECT, 2,0,1,1,0);
                effects.add(electroscythe1);
                effects.add(electroscythe2);
                break;
            case (MACHINEGUNCAPS):
                Effect machineGun = new DamageMarkEffect(MACHINEGUNEFFECT, 1,0, 0,0,0);
                Effect machineGunAdd = new AdditionalTarget(MACHINEGUNEFFECT, 1,0,0,0,1, machineGun);
                Effect machineGunAdd2 = new AdditionalTarget(MACHINEGUNDOUBLEEFFECT, 1,0,0,1,0, machineGunAdd);
                effects.add(machineGun);
                effects.add(machineGunAdd);
                effects.add(machineGunAdd2);
                break;
            case(TRACTORBEAMCAPS):
                Effect tractorBeam1 = new MovementEffect(TRACTORBEAMMOD1EFFECT,1,0,0,0,0);
                Effect tractorBeam2 = new MovementEffect(TRACTORBEAMMOD2EFFECT, 3,0,1,0,1);
                effects.add(tractorBeam1);
                effects.add(tractorBeam2);
                break;
            case (THORCAPS):
                Effect thor = new DamageMarkEffect(THOREFFECT,2,0,0,0,0);
                Effect thorAdd = new AdditionalTarget(THORSINGLEEFFECT,1,0,0,1,0, thor);
                Effect thorAdd2 = new AdditionalTarget(THORDOUBLEEFFECT,2,0,0,1,0, thorAdd);
                effects.add(thor);
                effects.add(thorAdd);
                effects.add(thorAdd2);
                break;
            case (VORTEXCANNONCAPS):
                Effect vortexCannon = new SquareDamageEffect(VORTEXCANNONEFFECT,2,0,0,0,0);
                Effect vortexCannonAdd = new AdditionalSquareDamage(VORTEXCANNONEFFECT,1,1,0,0, vortexCannon);
                effects.add(vortexCannon);
                effects.add(vortexCannonAdd);
                break;
            case (FURNACECAPS):
                Effect furnace1 = new SquareDamageEffect(FURNACEMOD1EFFECT, 1,0, 0,0,0);
                Effect furnace2 = new SquareDamageEffect(FURNACEMOD2EFFECT, 1,1,0,0,0);
                effects.add(furnace1);
                effects.add(furnace2);
                break;
            case(PLASMAGUNCAPS):
                Effect plasmaGun = new DamageMarkEffect(PLASMAGUNEFFECT,2,0,0,0,0);
                Effect plasmaGunAdd = new AdditionalMove(PLASMAGUNEFFECT,0,0,0, plasmaGun);
                Effect plasmaGunAdd2 = new AdditionalTarget(PLASMAGUNDOUBLEEFFECT,1,0,0,1,0, plasmaGunAdd);
                effects.add(plasmaGun);
                effects.add(plasmaGunAdd);
                effects.add(plasmaGunAdd2);
                break;
            case (HEATSEEKERCAPS):
                Effect heatseeker = new DamageMarkEffect(HEATSEEKEREFFECT,3,0,0,0,0);
                effects.add(heatseeker);
                break;
            case (WHISPERCAPS):
                Effect whisper = new DamageMarkEffect(WHISPEREFFECT, 3,1,0,0,0);
                effects.add(whisper);
                break;
            case(HELLIONCAPS):
                Effect hellion1 = new SquareDamageEffect(HELLIONEFFECT,1,1,0,0,0 );
                Effect hellion2 = new SquareDamageEffect(HELLIONEFFECT,1,2,1,0,0 );
                effects.add(hellion1);
                effects.add(hellion2);
                break;
            case (FLAMETHROWERCAPS):
                Effect flamethrower1 = new DirectionalDamage(FLAMETHROWERMOD1EFFECT,1,0,0,0 );
                Effect flamethrower2 = new DirectionalDamage(FLAMETHROWERMOD2EFFECT,2,0,0,2 );
                effects.add(flamethrower1);
                effects.add(flamethrower2);
                break;
            case (ZX2CAPS):
                Effect zx21 = new DamageMarkEffect(ZX2MOD1EFFECT,1,2,0,0,0);
                Effect zx22 = new DamageMarkEffect(ZX2MOD2EFFECT, 0,1,0,0,0);
                effects.add(zx21);
                effects.add(zx22);
                break;
            case (GRENADELAUNCHERCAPS):
                Effect grenadeLauncher = new MovementEffect(GRENADELAUNCHEREFFECT,1,0,0,0,0);
                Effect grenadeLauncherAdd = new AdditionalSquareDamage(GRENADELAUNCHEREFFECT,1,1,0,0, grenadeLauncher);
                effects.add(grenadeLauncher);
                effects.add(grenadeLauncherAdd);
                break;
            case(SHOTGUNCAPS):
                Effect shotgun1 = new MovementEffect(SHOTGUNMOD1EFFECT,3,0,0,0,0);
                Effect shotgun2 = new MovementEffect(SHOTGUNMOD2EFFECT,2,0,0,0,0);
                effects.add(shotgun1);
                effects.add(shotgun2);
                break;
            case(ROCKETLAUNCHERCAPS):
                Effect rocketLauncher = new MovementEffect(ROCKETLAUNCHEREFFECT,2,0,0,0,0);
                Effect rocketLauncherAdd = new AdditionalMove(ROCKETLAUNCHEREFFECT,0,0,0, rocketLauncher);
                Effect rocketLauncherAdd2 = new AdditionalSquareDamage(ROCKETLAUNCHEREFFECT,1,0,0,1, rocketLauncherAdd);
                effects.add(rocketLauncher);
                effects.add(rocketLauncherAdd);
                effects.add(rocketLauncherAdd2);
                break;
            case (POWERGLOVECAPS):
                Effect powerGlove1 = new MovementEffect(POWERGLOVEMOD1EFFECT, 1,2,0,0,0);
                Effect powerGlove2 = new DirectionalDamage(POWERGLOVEMOD2EFFECT,2,0,1,0);
                effects.add(powerGlove1);
                effects.add(powerGlove2);
                break;
            case(RAILGUNCAPS):
                Effect railgun1 = new DirectionalDamage(RAILGUNMOD1EFFECT,3,0,0,0);
                Effect railgun2 = new DirectionalDamage(RAILGUNMOD2EFFECT, 2,0,0,0);
                effects.add(railgun1);
                effects.add(railgun2);
                break;
            case(CYBERBLADECAPS):
                Effect cyberblade = new DamageMarkEffect(CYBERBLADEEFFECT,2,0,0,0,0);
                Effect cyberbladeAdd = new AdditionalMove(CYBERBLADEEFFECT,0,0,0, cyberblade);
                Effect cyberbladeAdd2 = new AdditionalTarget(CYBERBLADEEFFECT,2,0,0,0,1,cyberbladeAdd);
                effects.add(cyberblade);
                effects.add(cyberbladeAdd);
                effects.add(cyberbladeAdd2);
                break;
            case (SLEDGEHAMMERCAPS):
                Effect sledgehammer1 = new DamageMarkEffect(SLEDGEHAMMEREFFECT,2,0,0,0,0);
                Effect sledgehammer2 = new DirectionalDamage(SLEDGEHAMMEREFFECT,3,1,0,0);
                effects.add(sledgehammer1);
                effects.add(sledgehammer2);
                break;
            case (SHOCKWAVECAPS):
                Effect shockwave1 = new DamageMarkEffect(SHOCKWAVEEFFECT,1,0,0,0,0);
                Effect shockwave2 = new SquareDamageEffect(SHOCKWAVEEFFECT,1,0,0, 0,1);
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
