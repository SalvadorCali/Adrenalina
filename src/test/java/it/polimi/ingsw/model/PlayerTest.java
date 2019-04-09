package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.util.Parser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void addWeaponTest(){
        Player player = new Player(TokenColor.BLUE);
        WeaponCard weaponCard = (WeaponCard) Parser.createWeapons().draw();
        player.addWeapon(weaponCard);
        WeaponCard weaponCard2 = (WeaponCard) Parser.createWeapons().draw();
        player.addWeapon(weaponCard2);
        assertEquals(player.getWeapons().get(0), weaponCard);
        assertEquals(player.getWeapons().get(1), weaponCard2);
        assertNotEquals(player.getWeapons().get(0), weaponCard2);
    }

    @Test
    public void addPowerupTest(){
        Player player = new Player(TokenColor.BLUE);
        PowerupCard powerupCard = (PowerupCard) Parser.createPowerups().draw();
        player.addPowerup(powerupCard);
        PowerupCard powerupCard2 = (PowerupCard) Parser.createPowerups().draw();
        player.addPowerup(powerupCard2);
        assertEquals(player.getPowerups().get(0), powerupCard);
        assertEquals(player.getPowerups().get(1), powerupCard2);
        assertNotEquals(player.getPowerups().get(1), powerupCard);
    }

    @Test
    public void increaseAmmoNumberAndCanAddAmmoTest(){
        Player player = new Player(TokenColor.YELLOW);
        player.increaseAmmoNumber(Color.RED);
        player.increaseAmmoNumber(Color.RED);
        assertTrue(player.canAddAmmo(Color.RED));

        player.increaseAmmoNumber(Color.RED);
        assertFalse(player.canAddAmmo(Color.RED));
    }

    @Test
    public void addAmmoTest(){
        Player player = new Player(TokenColor.GREY);
        player.addAmmo(new Ammo(Color.RED), new Ammo(Color.RED), new Ammo(Color.RED), new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        assertEquals(5, player.getAmmoBox().size());
        assertEquals(Color.RED, player.getAmmoBox().get(0).getColor());
        assertEquals(Color.RED, player.getAmmoBox().get(1).getColor());
        assertEquals(Color.RED, player.getAmmoBox().get(2).getColor());
        assertEquals(Color.BLUE, player.getAmmoBox().get(3).getColor());
        assertEquals(Color.YELLOW, player.getAmmoBox().get(4).getColor());
    }

    @Test
    public void correctUpdateAmmoBox(){
        Player player = new Player(TokenColor.YELLOW);
        player.setAmmoBox(createAmmoBox());
        player.setAmmoReserve(createAmmoReserve());
        player.updateAmmoBox(1,1,0);
        assertEquals(1, player.getAmmoBox().size());
        assertEquals(8, player.getAmmoReserve().size());
    }

    @Test
    public void wrongUpdateAmmoBox(){
        Player player = new Player(TokenColor.GREY);
        player.setAmmoBox(createAmmoBox());
        player.setAmmoReserve(createAmmoReserve());
        player.updateAmmoBox(1,1,0);
        assertNotEquals(3, player.getAmmoBox().size());
        assertNotEquals(6, player.getAmmoReserve().size());
    }

    private ArrayList<Ammo> createAmmoBox(){
        Ammo[] ammos = new Ammo[]{new Ammo(Color.BLUE), new Ammo(Color.BLUE), new Ammo(Color.RED)};
        ArrayList<Ammo> ammoBox = new ArrayList<>();
        ammoBox.addAll(Arrays.asList(ammos));
        return ammoBox;
    }

    private ArrayList<Ammo> createAmmoReserve(){
        Ammo[] ammos = new Ammo[]{new Ammo(Color.YELLOW), new Ammo(Color.YELLOW), new Ammo(Color.YELLOW), new Ammo(Color.BLUE), new Ammo(Color.RED), new Ammo(Color.RED)};
        ArrayList<Ammo> ammoReserve = new ArrayList<>();
        ammoReserve.addAll(Arrays.asList(ammos));
        return ammoReserve;
    }
}
