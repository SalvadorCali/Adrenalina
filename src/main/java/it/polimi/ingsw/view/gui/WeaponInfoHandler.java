package it.polimi.ingsw.view.gui;

/**
 * this class is helpful for Weapon infos
 */
public class WeaponInfoHandler {
    /**
     * get Info of every weapon, what is needed when you shoot
     * @param nameWeapon
     * @return
     */
    public String getInfoWeapon(String nameWeapon){
        switch(nameWeapon){
            case "LOCK RIFLE":
                return "Basic effect: 1 victim \n With second lock: 1 or 2 victims";
            case "MACHINE GUN":
                return "Basic effect: 1 or 2 victims \n With focus shot: 1 or 2 victims \n With turret tripod: 2 or 3 victims";
            case "T.H.O.R.":
                return "Basic effect: 1 victim \n With chain reaction: 2 victims \n With high voltage: 3 victims";
            case "PLASMA GUN":
                return "Basic effect: 1 victim \n With phase glide: 1 victim, 1 or 2 directions \n With charged shot: 1 victim, from 0 to 2 directions";
            case "WHISPER":
                return "Effect: 1 victim";
            case "ELECTROSCYTHE":
                return "Only effect";
            case "TRACTOR BEAM":
                return "Basic mode: 1 victim, from 0 to 2 directions \n In punisher mode: 1 victim";
            case "VORTEX CANNON":
                return "Basic effect: 1 victim, square position \n With black hole: 2 or 3 victims, square position";
            case "FURNACE":
                return "Basic mode: square position \n In cozy fire mode: square position";
            case "HEATSEEKER":
                return "Effect: 1 victim";
            case "HELLION":
                return "Basic mode: 1 victim \n In nano-tracer mode: 1 victim";
            case "FLAMETHROWER":
                return "Basic mode: 1 or 2 victim, 1 direction \n In barbecue mode: 1 direction";
            case "GRENADE LAUNCHER":
                return "Basic effect: 1 victim, 0 or 1 direction \n With extra grenade: 1 victim, 0 or 1 direction, square position";
            case "ROCKET LAUNCHER":
                return "Basic effect: 1 victim, 0 or 1 direction \n With rocket jump: 1 victim, 0 or 1 direction, \n With rocket jump: 1 or 2 directions";
            case "RAILGUN":
                return "Basic mode: 1 victim, 1 direction \n In piercing mode: 1 or 2 victims, 1 direction";
            case "CYBERBLADE":
                return "Basic effect: 1 victim \n With shadow step: 1 victim, 1 direction \n With slice and dice: 2 victim, 1 direction";
            case "ZX-2":
                return "Basic mode: 1 victim \n In scanner mode: from 1 to 3 victims";
            case "SHOTGUN":
                return "Basic mode: 1 victim, 0 or 1 direction \n In long barrel mode: 1 victim";
            case "POWER GLOVE":
                return "Basic mode: 1 victim \n In rocket fist mode: from 0 to 2 victims, 1 or 2 directions";
            case "SHOCKWAVE":
                return "Basic mode: from 0 to 3 victims \n In tsunami mode: only effect";
            case "SLEDGEHAMMER":
                return "Basic mode: victim \n In pulverize mode: 1 victim, from 0 to 2 directions";
            default:
                return "Wrong Name";
        }
    }

    /**
     * get Info of every powerup
     * @param namePowerup
     * @return
     */

    public String getInfoPowerUp(String namePowerup){
        switch (namePowerup){
            case "TARGETING SCOPE":
                return "1 victim, 1 ammo";
            case "NEWTON":
                return "1 victim, 1 or 2 directions";
            case "TAGBACK GRENADE":
                return "1 victim";
            case "TELEPORTER":
                return "square position";
            default:
                return "Wrong Name";
        }
    }
}
