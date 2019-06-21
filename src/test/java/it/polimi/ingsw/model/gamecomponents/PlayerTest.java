package it.polimi.ingsw.model.gamecomponents;

import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.util.Parser;
import it.polimi.ingsw.util.Printer;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {

    @Test
    void playerSettersTest(){
        Player player = new Player(TokenColor.GREEN);
        player.setRespawned(true);
        assertTrue(player.isRespawned());
        player.setMoveAndReload(true);
        assertTrue(player.isMoveAndReload());
        player.setPowerupAsAmmo(true);
        assertTrue(player.isPowerupAsAmmo());
        player.setSpawned(true);
        assertTrue(player.isSpawned());
        player.setColor(TokenColor.BLUE);
        assertEquals(TokenColor.BLUE, player.getColor());
        player.setScore(300);
        assertEquals(300, player.getScore());
        player.setActionNumber(20);
        player.setMyTurn(true);
        assertTrue(player.isMyTurn());
        player.setUsername("P");
        assertEquals("P",player.getUsername());
        player.setDisconnected(true);
        assertTrue(player.isDisconnected());
        player.setPosition(0,1);
        assertEquals(0, player.getPosition().getX());
        assertEquals(1, player.getPosition().getY());
        Position pos = new Position(2,2);
        player.setPosition(pos);
        assertEquals(pos, player.getPosition());
        PlayerBoard playerBoard = new PlayerBoard();
        player.setPlayerBoard(playerBoard);
        assertEquals(playerBoard, player.getPlayerBoard());
        assertEquals(0, player.getBlueAmmo());
        assertEquals(0, player.getRedAmmo());
        assertEquals(0, player.getYellowAmmo());
    }
    @Test
    void resetPowerupAmmoTest(){
        Player player = new Player(TokenColor.BLUE);
        player.increasePowerupAmmoNumber(Color.BLUE);
        player.increasePowerupAmmoNumber(Color.YELLOW);
        player.increasePowerupAmmoNumber(Color.RED);
        player.setPowerupAsAmmo(true);
        assertTrue(player.isPowerupAsAmmo());
        player.resetPowerupAmmos();
        assertFalse(player.isPowerupAsAmmo());
    }
    @Test
    void addAmmoToReserveTest(){
        Player player = new Player(TokenColor.GREEN);
        player.addAmmoToReserve(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        assertEquals(3,player.getAmmoReserve().size());
    }
    @Test
    void ammoControlTest(){
        Player player = new Player(TokenColor.GREEN);
        player.addAmmo(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        assertTrue(player.ammoControl(1,1,1));
        assertFalse(player.ammoControl(2,2,2));
        assertTrue(player.ammoControl(0,0,0));
        assertFalse(player.ammoControl(1,0,2));
    }
    /*
    @Test
    void updateAmmoBoxAddTest(){
        Player player = new Player(TokenColor.GREEN);
        player.addAmmo(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        player.addAmmoToReserve(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        player.updateAmmoBoxAdd(1,1,1);
        assertEquals(6, player.getAmmoBox().size());
        assertEquals(0,player.getAmmoReserve().size());
        player.addAmmoToReserve(new Ammo(Color.BLUE), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        player.updateAmmoBoxAdd(0,2,1);
        assertEquals(9, player.getAmmoBox().size());
        assertEquals(0,player.getAmmoReserve().size());
    }
    @Test
    void updateAmmoBoxTest(){
        Player player = new Player(TokenColor.GREEN);
        player.addAmmo(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        player.addAmmoToReserve(new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW), new Ammo(Color.RED), new Ammo(Color.BLUE), new Ammo(Color.YELLOW));
        player.updateAmmoBox(1,1,1);
        assertEquals(0, player.getAmmoBox().size());
        assertEquals(6,player.getAmmoReserve().size());
    }
    
     */
    @Test
    void addWeaponTest(){
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
    void addPowerupTest(){
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
    void increaseAmmoNumberAndCanAddAmmoTest(){
        Player player = new Player(TokenColor.YELLOW);
        player.increaseAmmoNumber(Color.RED);
        player.increaseAmmoNumber(Color.RED);
        assertTrue(player.canAddAmmo(Color.RED));

        player.increaseAmmoNumber(Color.RED);
        assertFalse(player.canAddAmmo(Color.RED));
    }
    @Test
    void addAmmoTest(){
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
    void correctUpdateAmmoBox(){
        Player player = new Player(TokenColor.YELLOW);
        player.setAmmoBox(createAmmoBox());
        player.setAmmoReserve(createAmmoReserve());
        player.updateAmmoBox(1,1,0);
        assertEquals(1, player.getAmmoBox().size());
        assertEquals(8, player.getAmmoReserve().size());
    }
    @Test
    void wrongUpdateAmmoBox(){
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
