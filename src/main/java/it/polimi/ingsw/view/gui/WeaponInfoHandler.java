package it.polimi.ingsw.view.gui;

public class WeaponInfoHandler {

    public String getInfoWeapon(String nameWeapon){
        switch(nameWeapon){
            case "lockrifle":
                return "Basic effect: 1 victim \n With second lock: 1 or 2 victims";
            case "machinegun":
                return "Basic effect: 1 or 2 victims \n With focus shot: 1 or 2 victims \n With turret tripod: 2 or 3 victims";
            case "thor":
                return "Basic effect: 1 victim \n With chain reaction: 2 victims \n With high voltage: 3 victims";
            case "plasmagun":
                return "Basic effect: 1 victim \n With phase glide: 1 victim, 1 or 2 directions \n With charged shot: 1 victim, from 0 to 2 directions";
            case "whisper":
                return "Effect: 1 victim";
            case "electroscythe":
                return "Only effect";
            case "tractorbeam":
                return "Basic mode: 1 victim, from 0 to 2 directions \n In punisher mode: 1 victim";
            case "vortexcannon":
                return "Basic effect: 1 victim, square position \n With black hole: 2 or 3 victims, square position";
            case "furnace":
                return "Basic mode: square position \n In cozy fire mode: square position";
            case "heatseeker":
                return "Effect: 1 victim";
            case "hellion":
                return "Basic mode: 1 victim \n In nano-tracer mode: 1 victim";
            case "flamethrower":
                return "Basic mode: 1 or 2 victim, 1 direction \n In barbecue mode: 1 direction";
            case "grenadelauncher":
                return "Basic effect: 1 victim, 0 or 1 direction \n With extra grenade: 1 victim, 0 or 1 direction, square position";
            case "rocketlauncher":
                return "Basic effect: 1 victim, 0 or 1 direction \n With rocket jump: 1 victim, 0 or 1 direction, \n With rocket jump: 1 or 2 directions";
            case "railgun":
                return "Basic mode: 1 victim, 1 direction \n In piercing mode: 1 or 2 victims, 1 direction";
            case "cyberblade":
                return "Basic effect: 1 victim \n With shadow step: 1 victim, 1 direction \n With slice and dice: 2 victim, 1 direction";
            case "zx-2":
                return "Basic mode: 1 victim \n In scanner mode: from 1 to 3 victims";
            case "shotgun":
                return "Basic mode: 1 victim, 0 or 1 direction \n In long barrel mode: 1 victim";
            case "powerglove":
                return "Basic mode: 1 victim \n In rocket fist mode: from 0 to 2 victims, 1 or 2 directions";
            case "shockwave":
                return "Basic mode: from 0 to 3 victims \n In tsunami mode: only effect";
            case "sledgehammer":
                return "Basic mode: victim \n In pulverize mode: 1 victim, from 0 to 2 directions";
            default:
                return "no Weapon";
        }
    }


    public String getInfoPowerUp(String namePowerup){
        switch (namePowerup){
            case "Targeting Scope":
                return "1 victim, 1 ammo";
            case "Newton":
                return "1 victim, 1 or 2 directions";
            case "Tagback Grenade":
                return "1 victim";
            case "Teleporter":
                return "square position";
            default:
                return "no Powerup";
        }
    }
}
