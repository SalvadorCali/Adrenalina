package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.util.Converter;
import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * This class handles the input for the weapons.
 */
public class WeaponHandler {
    /**
     * The user input stream.
     */
    private BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
    /**
     * A StringTokenizer object.
     */
    private StringTokenizer string;

    /**
     * Method for Lock Rifle. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean lockRifle(ClientInterface client, String weapon) throws IOException {
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.WITH_SECOND_LOCK + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 2){
            client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Machine Gun. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean machineGun(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM);
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM);
        Printer.println(StringCLI.WITH_FOCUS_SHOT + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM);
        Printer.println(StringCLI.WITH_FOCUS_SHOT + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM);
        Printer.println(StringCLI.WITH_TURRET_TRIPOD + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.WITH_TURRET_TRIPOD + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            int choice = Converter.fromStringToInt(string.nextToken());
            switch (choice){
                case 1:
                case 2:
                    client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()),
                            Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
                    break;
                case 3:
                    client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()),
                            TokenColor.NONE, Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
                    break;
                default:
                    return false;
            }
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for T.H.O.R. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean thor(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.WITH_CHAIN_REACTION + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM);
        Printer.println(StringCLI.WITH_HIGH_VOLTAGE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 2){
            client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3) {
            client.shoot(weapon, 3, true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                    -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Plasma Gun. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean plasmaGun(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.WITH_PHASE_GLIDE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION);
        Printer.println(StringCLI.WITH_PHASE_GLIDE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
        Printer.println(StringCLI.WITH_CHARGED_SHOT + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.WITH_CHARGED_SHOT + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION);
        Printer.println(StringCLI.WITH_CHARGED_SHOT + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            boolean basicFirst = basicFirst(weapon);
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 4){
            boolean basicFirst = basicFirst(weapon);
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()),
                    Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Whisper. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean whisper(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                    TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Electroscythe. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean electroscythe(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE);
        Printer.println(StringCLI.IN_REAPER_MODE + StringCLI.SPACE + StringCLI.TWO);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE,
                    TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Tractor Beam. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean tractorBeam(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION);
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
        Printer.println(StringCLI.IN_PUNISHER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()),
                    Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Vortex Cannon. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean vortexCannon(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y);
        Printer.println(StringCLI.WITH_BLACK_HOLE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y);
        Printer.println(StringCLI.WITH_BLACK_HOLE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 3){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()));
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                    Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()));
            return true;
        }else if(string.countTokens() == 5){
            client.shoot(weapon, 2, true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Furnace. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean furnace(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y);
        Printer.println(StringCLI.IN_COZY_FIRE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE,
                    Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Heatseeker. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean heatseeker(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.EFFECT + StringCLI.SPACE + StringCLI.VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, TokenColor.NONE,
                    -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Hellion. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean hellion(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.IN_NANO_TRACER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Flamethrower. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean flamethrower(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.IN_BARBECUE_MODE + StringCLI.SPACE + StringCLI.DIRECTION);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 2, true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE,
                    -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 2) {
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                    TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 3){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Grenade Launcher. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean grenadeLauncher(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.WITH_EXTRA_GRENADE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y);
        Printer.println(StringCLI.WITH_EXTRA_GRENADE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.SQUARE_X + StringCLI.SPACE + StringCLI.SQUARE_Y + StringCLI.SPACE + StringCLI.DIRECTION);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 2){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 3){
            boolean basicFirst = basicFirst(weapon);
            client.shoot(weapon, 2, basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()));
            return true;
        }else if(string.countTokens() == 4){
            boolean basicFirst = basicFirst(weapon);
            client.shoot(weapon, 2, basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()),
                    Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Rocket Launcher. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean rocketLauncher(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.CHOOSE_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.OR + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.THREE);
        int choice2 = Converter.fromStringToInt(userInputStream.readLine());
        if(choice2 == 1){
            Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM);
            Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM_DIRECTION);
            string = new StringTokenizer(userInputStream.readLine());
            if(string.countTokens()==1){
                client.shoot(weapon, choice2, true, Converter.fromStringToTokenColor(string.nextToken()),
                        TokenColor.NONE, TokenColor.NONE, -1, -1);
                return true;
            }else if(string.countTokens()==2){
                client.shoot(weapon, choice2, true, Converter.fromStringToTokenColor(string.nextToken()),
                        TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
                return true;
            }else{
                return false;
            }
        }else if(choice2 == 2 || choice2 == 3){
            boolean basicFirst = basicFirst(weapon);
            Printer.println(StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.VICTIM);
            Printer.println(StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.VICTIM_DIRECTION);
            string = new StringTokenizer(userInputStream.readLine());
            TokenColor victim = TokenColor.NONE;
            Direction direction1 = null;
            if(string.countTokens()==1){
                victim = Converter.fromStringToTokenColor(string.nextToken());
            }else if(string.countTokens()==2){
                victim = Converter.fromStringToTokenColor(string.nextToken());
                direction1 = Converter.fromStringToDirection(string.nextToken());
            }else{
                return false;
            }
            Printer.println(StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.SHOOTER_DIRECTION);
            Printer.println(StringCLI.WITH_ROCKET_JUMP + StringCLI.SPACE + StringCLI.SHOOTER_DIRECTION + StringCLI.SPACE + StringCLI.SHOOTER_DIRECTION);
            string = new StringTokenizer(userInputStream.readLine());
            Direction direction3 = null;
            Direction direction4 = null;
            if(string.countTokens()==1){
                direction3 = Converter.fromStringToDirection(string.nextToken());
            }else if(string.countTokens()==2){
                direction3 = Converter.fromStringToDirection(string.nextToken());
                direction4 = Converter.fromStringToDirection(string.nextToken());
            }else{
                return false;
            }
            client.shoot(weapon, choice2, basicFirst, victim, TokenColor.NONE, TokenColor.NONE, -1, -1,
                    direction1, null, direction3, direction4);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Railgun. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean railgun(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.IN_PIERCING_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION); //to test
        Printer.println(StringCLI.IN_PIERCING_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Cyberblade. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean cyberblade(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_EFFECT + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.WITH_SHADOW_STEP + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.WITH_SLICE_AND_DICE + StringCLI.SPACE + StringCLI.THREE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            boolean basicFirst = basicFirst(weapon);
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 4){
            boolean basicFirst = basicFirst(weapon + "2");
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), basicFirst, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for ZX-2. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean zx2(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.IN_SCANNER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM);
        Printer.println(StringCLI.IN_SCANNER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM);
        Printer.println(StringCLI.IN_SCANNER_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Shotgun. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean shotgun(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.IN_LONG_BARREL_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Power Glove. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean powerGlove(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
        Printer.println(StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
        Printer.println(StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.IN_ROCKET_FIST_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);

        string = new StringTokenizer(userInputStream.readLine());
        int choice = Converter.fromStringToInt(string.nextToken());
        if(string.countTokens() == 1 && choice == 1){
            client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                    TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 1 && choice == 2){
            client.shoot(weapon, choice, true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE, -1, -1,
                    Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 2){
            String move = string.nextToken();
            if(Converter.fromStringToTokenColor(move).equals(TokenColor.NONE)){
                client.shoot(weapon, choice, true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE, -1, -1,
                        Converter.fromStringToDirection(move));
            }else{
                client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(move), TokenColor.NONE, TokenColor.NONE,
                        -1, -1, Converter.fromStringToDirection(string.nextToken()));
            }
            return true;
        }else if(string.countTokens() == 3){
            TokenColor firstVictim = Converter.fromStringToTokenColor(string.nextToken());
            String move = string.nextToken();
            if(Converter.fromStringToTokenColor(move).equals(TokenColor.NONE)){
                client.shoot(weapon, choice, true, firstVictim, TokenColor.NONE, TokenColor.NONE, -1, -1,
                        Converter.fromStringToDirection(move), Converter.fromStringToDirection(string.nextToken()));
            }else{
                client.shoot(weapon, choice, true, firstVictim, Converter.fromStringToTokenColor(move), TokenColor.NONE,
                        -1, -1, Converter.fromStringToDirection(string.nextToken()));
            }
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, choice, true, Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()), Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Shockwave. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean shockwave(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM);
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM);
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.FIRST_VICTIM + StringCLI.SPACE + StringCLI.SECOND_VICTIM + StringCLI.SPACE + StringCLI.THIRD_VICTIM);
        Printer.println(StringCLI.IN_TSUNAMI_MODE + StringCLI.SPACE + StringCLI.TWO);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE,
                    TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    Converter.fromStringToTokenColor(string.nextToken()), Converter.fromStringToTokenColor(string.nextToken()), -1, -1);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method for Sledgehammer. Takes the parameters to shoot with the StringTokenizer and in the basis of the number of tokens call the relative shoot method.
     * @param client the Client.
     * @param weapon the weapon.
     * @return true if the parameters are correct.
     * @throws IOException caused by the streams.
     */
    boolean sledgehammer(ClientInterface client, String weapon) throws IOException{
        Printer.println(StringCLI.BASIC_MODE + StringCLI.SPACE + StringCLI.ONE + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.IN_PULVERIZE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM);
        Printer.println(StringCLI.IN_PULVERIZE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.DIRECTION);
        Printer.println(StringCLI.IN_PULVERIZE_MODE + StringCLI.SPACE + StringCLI.TWO + StringCLI.SPACE + StringCLI.VICTIM + StringCLI.SPACE + StringCLI.FIRST_DIRECTION + StringCLI.SPACE + StringCLI.SECOND_DIRECTION);
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else if(string.countTokens() == 4){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1, Converter.fromStringToDirection(string.nextToken()), Converter.fromStringToDirection(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * On the basis of the weapon name, it sets the basicFirst value and returns it.
     * @param weapon the name of the weapon.
     * @return a boolean value, true if the user wants to use the basic effect first.
     */
    private boolean basicFirst(String weapon){
        switch(weapon){
            case StringCLI.GRENADE_LAUNCHER:
                Printer.println(StringCLI.SECOND_EFFECT_FIRST + StringCLI.SPACE + StringCLI.YES_INPUT + StringCLI.SPACE + StringCLI.NO_INPUT);
                break;
            case StringCLI.CYBERBLADE_2:
                Printer.println(StringCLI.MOVE_AFTER_THIRD + StringCLI.SPACE + StringCLI.YES_INPUT + StringCLI.SPACE + StringCLI.NO_INPUT);
                break;
            default:
                Printer.println(StringCLI.MOVE_FIRST + StringCLI.SPACE + StringCLI.YES_INPUT + StringCLI.SPACE + StringCLI.NO_INPUT);
                break;
        }
        try {
            String basicFirst = userInputStream.readLine();
            if(basicFirst.equals(StringCLI.YES)){
                return false;
            }
        } catch (IOException e) {
            Printer.err(e);
        }
        return true;
    }
}
