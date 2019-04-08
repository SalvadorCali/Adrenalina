package it.polimi.ingsw.util;


import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CLIHandler {

    private Player shooter;
    private Player victim;
    private PlayerBoard playerBoard;
    private GameBoard gameBoard;
    private Square[][] arena;

    public void printDmgBoard(){

        Token[] dmgBoard;
        dmgBoard = playerBoard.getDamageBoard();

        System.out.println("DamageBoard: \n");
        for(int i = 0; i < dmgBoard.length; i++)
            System.out.println(dmgBoard);
    }

    public void printScore(){
/*
        Map<TokenColor, Integer> scoreList = playerBoard.getScoreList();

        System.out.println("Actual Score: \n");
        System.out.println(Arrays.asList(scoreList));
        */
    }

    public void printDeathNumber(){

        int totalDeath;
        totalDeath = playerBoard.getDeathNumber();

        System.out.println(totalDeath);
    }

    public void printArena(){

        arena = gameBoard.getArena();

        for(int i = 0; i < arena.length; i++) {
            for (int j = 0; j < arena[i].length; j++)
                System.out.print(arena[i][j] + " ");
            System.out.println();
        }
    }

    public void printYourPosition(){

        int x;
        int y;

        x = shooter.getPosition().getX();
        y = shooter.getPosition().getY();

        System.out.println("Your position is: " + x + ", " + y);
    }

    public void printAmmoReserve(){

        List<Ammo> ammoReserve;
        ammoReserve = shooter.getAmmoReserve();

        System.out.println("Your ammo: ");
        System.out.println(Arrays.asList(ammoReserve));
    }

    public void printWeapons(){

        List<WeaponCard> weaponList;
        weaponList = shooter.getWeapons();

        System.out.println("Your weapons: ");
        System.out.println(Arrays.asList(weaponList));
    }

    public void enemyVisible(){

        if(gameBoard.isVisible(shooter, victim)) {
            System.out.println(victim.getColor() + " is visible from player " + shooter.getColor());
        }
    }

    public void printSquareCardinal(){

        int x;
        int y;


        x = shooter.getPosition().getX();
        y = shooter.getPosition().getY();
        arena = gameBoard.getArena();

        System.out.println("North: " + arena[x][y].getNorth() + "\nEast: " + arena[x][y].getEast() + "\nWest: " + arena[x][y].getWest() + "\nSouth: " +  arena[x][y].getSouth());
    }

    public void printPowerUps(){

        List<PowerupCard> powerupCards;
        powerupCards = shooter.getPowerups();

        System.out.println(Arrays.asList(powerupCards));
    }

    public void whichTurn(){

        //this methods should print player's turn
    }

}
