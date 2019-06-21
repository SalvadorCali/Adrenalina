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

public class WeaponHandler {
    private BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer string;

    public boolean lockRifle(ClientInterface client, String weapon) throws IOException {
        Printer.println("Basic effect: <victim>");
        Printer.println("With second lock: <first_victim> <second_victim>");
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

    public boolean machineGun(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic effect: <1> <first_victim>");
        Printer.println("Basic effect: <1> <first_victim> <second_victim>");
        Printer.println("With focus shot: <2> <first_victim>");
        Printer.println("With focus shot: <2> <first_victim> <second_victim>");
        Printer.println("With turret tripod: <3> <victim> <victim>");
        Printer.println("With turret tripod: <3> <victim> <victim> <victim>");
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

    public boolean thor(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic effect: <victim>");
        Printer.println("With chain reaction: <victim> <victim>");
        Printer.println("With high voltage: <victim> <victim> <victim>");
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

    public boolean plasmaGun(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic effect: <1> <victim>");
        Printer.println("With phase glide: <2> <victim> <direction>");
        Printer.println("With phase glide: <2> <victim> <direction> <direction>");
        Printer.println("With charged shot: <3> <victim>");
        Printer.println("With charged shot: <3> <victim> <direction>");
        Printer.println("With charged shot: <3> <victim> <direction> <direction>");
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

    public boolean whisper(ClientInterface client, String weapon) throws IOException{
        Printer.println("Effect: <victim>");
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE,
                    TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    public boolean electroscythe(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1>");
        Printer.println("In reaper mode: <2>");
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE,
                    TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    public boolean tractorBeam(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim> <directions...>");
        Printer.println("In punisher mode: <2> <victim>");
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

    public boolean vortexCannon(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic effect: <victim> <squareX> <squareY>");
        Printer.println("With black hole: <victim> <victim> <squareX> <squareY>");
        Printer.println("With black hole: <victim> <victim> <victim> <squareX> <squareY>");
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

    public boolean furnace(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <squareX> <squareY>");
        Printer.println("In cozy fire mode: <2> <squareX> <squareY>");
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 3){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, TokenColor.NONE, TokenColor.NONE, TokenColor.NONE,
                    Converter.fromStringToInt(string.nextToken()), Converter.fromStringToInt(string.nextToken()));
            return true;
        }else{
            return false;
        }
    }

    public boolean heatseeker(ClientInterface client, String weapon) throws IOException{
        Printer.println("Effect: <victim>");
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 1){
            client.shoot(weapon, 1, true, Converter.fromStringToTokenColor(string.nextToken()), TokenColor.NONE, TokenColor.NONE,
                    -1, -1);
            return true;
        }else{
            return false;
        }
    }

    public boolean hellion(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim>");
        Printer.println("In nano-tracer mode: <2> <victim>");
        string = new StringTokenizer(userInputStream.readLine());
        if(string.countTokens() == 2){
            client.shoot(weapon, Converter.fromStringToInt(string.nextToken()), true, Converter.fromStringToTokenColor(string.nextToken()),
                    TokenColor.NONE, TokenColor.NONE, -1, -1);
            return true;
        }else{
            return false;
        }
    }

    public boolean flamethrower(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <victim1> <direction>");
        Printer.println("Basic mode: <victim1> <victim2> <direction>");
        Printer.println("In barbecue mode: <direction>");
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

    public boolean grenadeLauncher(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic effect: <victim>");
        Printer.println("Basic effect: <victim> <direction>");
        Printer.println("With extra grenade: <victim> <squareX> <squareY>");
        Printer.println("With extra grenade: <victim> <squareX> <squareY> <direction>");
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

    public boolean rocketLauncher(ClientInterface client, String weapon) throws IOException{
        Printer.println("Choose your effect: <1> or <2> or <3>");
        int choice2 = Converter.fromStringToInt(userInputStream.readLine());
        if(choice2 == 1){
            Printer.println("Basic effect: <victim>");
            Printer.println("Basic effect: <victim> <victim_direction>");
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
            Printer.println("With rocket jump: <victim>");
            Printer.println("With rocket jump: <victim> <victim_direction>");
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
            Printer.println("With rocket jump: <shooter_direction>");
            Printer.println("With rocket jump: <shooter_direction> <shooter_direction>");
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

    public boolean railgun(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim> <direction>");
        Printer.println("In piercing mode: <2> <victim> <direction>"); //to test
        Printer.println("In piercing mode: <2> <victim1> <victim2> <direction>");
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

    public boolean cyberblade(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic effect: <1> <victim>");
        Printer.println("With shadow step: <2> <victim> <direction>");
        Printer.println("With slice and dice: <3> <victim> <victim> <direction>");
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

    public boolean zx_2(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim>");
        Printer.println("In scanner mode: <2> <victim>");
        Printer.println("In scanner mode: <2> <victim> <victim>");
        Printer.println("In scanner mode: <2> <victim> <victim> <victim>");
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

    public boolean shotgun(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim>");
        Printer.println("Basic mode: <1> <victim> <direction>");
        Printer.println("In long barrel mode: <2> <victim>");
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

    public boolean powerGlove(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim>");
        Printer.println("In rocket fist mode: <2> <direction>");

        Printer.println("In rocket fist mode: <2> <direction> <direction>");
        Printer.println("In rocket fist mode: <2> <victim> <direction>");

        Printer.println("In rocket fist mode: <2> <victim> <direction> <direction>");
        Printer.println("In rocket fist mode: <2> <victim> <victim> <direction>");

        Printer.println("In rocket fist mode: <2> <victim> <victim> <direction> <direction>");

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

    public boolean shockwave(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim>");
        Printer.println("Basic mode: <1> <victim> <victim>");
        Printer.println("Basic mode: <1> <victim> <victim> <victim>");
        Printer.println("In tsunami mode: <2>");
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

    public boolean sledgehammer(ClientInterface client, String weapon) throws IOException{
        Printer.println("Basic mode: <1> <victim>");
        Printer.println("In pulverize mode: <2> <victim>");
        Printer.println("In pulverize mode: <2> <victim> <direction>");
        Printer.println("In pulverize mode: <2> <victim> <direction> <direction>");
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

    private boolean basicFirst(String weapon){
        switch(weapon){
            case "grenadelauncher":
                Printer.println("Do you want to use the second effect first?: <yes> <no>");
                break;
            case "cyberblade2":
                Printer.println("Do you want to move after the third effect?: <yes> <no>");
                break;
            default:
                Printer.println("Do you want to move first?: <yes> <no>");
                break;
        }
        try {
            String basicFirst = userInputStream.readLine();
            if(basicFirst.equals("yes")){
                return false;
            }
        } catch (IOException e) {
            Printer.err(e);
        }
        return true;
    }
}
