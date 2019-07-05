package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.cards.StringCards;
import it.polimi.ingsw.view.cli.StringCLI;

/**
 * this class is helpful for Weapon infos
 */
public class WeaponInfoHandler {
    /**
     * get Info of every weapon, what is needed when you shoot
     * @param nameWeapon
     * @return info about weapons
     */
    public String getInfoWeapon(String nameWeapon){
        switch(nameWeapon){
            case StringCards.LOCKRIFLECAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_SECOND_LOCK + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM;
            case StringCards.MACHINEGUNCAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE  + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_FOCUS_SHOT + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_FOCUS_SHOT + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_TURRET_TRIPOD + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_TURRET_TRIPOD + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM;
            case StringCards.THORCAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_CHAIN_REACTION + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_HIGH_VOLTAGE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM;
            case StringCards.PLASMAGUNCAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_PHASE_GLIDE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_PHASE_GLIDE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_CHARGED_SHOT + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_CHARGED_SHOT + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_CHARGED_SHOT + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION;
            case StringCards.WHISPERCAPS:
                return StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM;
            case StringCards.ELECTROSCYTHECAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.NEW_LINE +
                        StringCLI.IN_REAPER_MODE + StringCLI.SPACE + StringCLI.TWO;
            case StringCards.TRACTORBEAMCAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_PUNISHER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM;
            case StringCards.VORTEXCANNONCAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y + StringCLI.NEW_LINE +
                        StringCLI.WITH_BLACK_HOLE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y + StringCLI.NEW_LINE +
                        StringCLI.WITH_BLACK_HOLE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y;
            case StringCards.FURNACECAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y + StringCLI.NEW_LINE +
                        StringCLI.IN_COZY_FIRE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y;
            case StringCards.HEATSEEKERCAPS:
                return StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM;
            case StringCards.HELLIONCAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_NANO_TRACER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM;
            case StringCards.FLAMETHROWERCAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_BARBECUE_MODE + StringCLI.SPACE + StringCLI.DIRECTION;
            case StringCards.GRENADELAUNCHERCAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_EXTRA_GRENADE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y + StringCLI.NEW_LINE +
                        StringCLI.WITH_EXTRA_GRENADE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y + StringCLI.SPACE + StringCLI.DIRECTION;
            case StringCards.ROCKETLAUNCHERCAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.SHOOTER_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.SHOOTER_DIRECTION + StringCLI.SPACE + StringCLI.SHOOTER_DIRECTION;
            case StringCards.RAILGUNCAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_PIERCING_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_PIERCING_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION;
            case StringCards.CYBERBLADECAPS:
                return StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.WITH_SHADOW_STEP + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.WITH_SLICE_AND_DICE + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION;
            case StringCards.ZX2CAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_SCANNER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_SCANNER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_SCANNER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM;
            case StringCards.SHOTGUNCAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_LONG_BARREL_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM;
            case StringCards.POWERGLOVECAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION;
            case StringCards.SHOCKWAVECAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_TSUNAMI_MODE + StringCLI.SPACE + StringCLI.TWO;
            case StringCards.SLEDGEHAMMERCAPS:
                return StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_PULVERIZE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.NEW_LINE +
                        StringCLI.IN_PULVERIZE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.IN_PULVERIZE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION;
            default:
                return StringCLI.ERROR;
        }
    }

    /**
     * get Info of every powerup
     * @param namePowerup info about powerup
     */
    public String getInfoPowerUp(String namePowerup){
        switch (namePowerup){
            case StringCards.TARGETINGSCOPECAPS:
                return StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.AMMO;
            case StringCards.NEWTONCAPS:
                return StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.NEW_LINE +
                        StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION;
            case StringCards.TAGBACKGRENADECAPS:
                return StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM;
            case StringCards.TELEPORTERCAPS:
                return StringCLI.EFFECT + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y;
            default:
                return StringCLI.ERROR;
        }
    }
}
