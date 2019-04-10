package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Color;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Player;

import java.util.List;

public class ActionController implements ActionInterface {
    public ActionController(Game game){
        this.game = game;
    }

    private Game game;

    @Override
    public boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos) {
        return game.getCurrentPlayer().ammoControl(redAmmos, blueAmmos, yellowAmmos);
    }

    @Override
    public void playerDamage(TokenColor color, int damagePower) {

        for(int i = 0; i < damagePower; i++)
            game.findPlayer(color).getPlayerBoard().addDamage(game.getCurrentPlayer().getColor());

    }

    @Override
    public void playerMark(TokenColor color, int markPower) {

        for(int i = 0; i < markPower; i++)
            game.findPlayer(color).getPlayerBoard().addRevengeMarks(game.getCurrentPlayer().getColor());

    }

    @Override
    public boolean isVisible(TokenColor color) {

        return game.isVisible(game.findPlayer(color));
    }

    @Override
    public void addAmmo(Ammo...ammos){
        game.getCurrentPlayer().addAmmo(ammos);
    }

    @Override
    public void addPowerup(){
        game.getCurrentPlayer().addPowerup((PowerupCard) game.getPowerup().draw());
    }

    @Override
    public void addWeapon(WeaponCard weaponCard){
        game.getCurrentPlayer().addWeapon(weaponCard);
    }

    @Override
    public boolean sameSquare(TokenColor color) {
        return game.sameSquare(color);
    }

    @Override
    public boolean isVisibleDifferentSquare(int x, int y){
        return game.getBoard().isVisibleDifferentSquare(game.getCurrentPlayer(), x, y);
    }

    @Override
    public void updateAmmoBox(int redAmmos, int blueAmmos, int yellowAmmos) {
        game.getCurrentPlayer().updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    @Override
    public boolean canMove(TokenColor victim, Direction... directions) {
        return game.getBoard().canMove(game.findPlayer(victim), directions);
    }

    @Override
    public int distanceControl(int x, int y){

        return game.getBoard().distance(game.getCurrentPlayer(), x, y);
    }

    @Override
    public void roomDamage(int x, int y, int damagePower, int markPower) {

        game.getBoard().roomDamage(x, y, damagePower, markPower, game.getCurrentPlayer().getColor());
    }

    @Override
    public void squareDamage(int x, int y, int damagePower, int markPower) {

        game.getBoard().getArena()[x][y].squareDamage(damagePower, markPower, game.getCurrentPlayer().getColor());
    }

    @Override
    public void move(int x, int y, Player victim) {
        game.getBoard().move(x, y, victim);
    }

    @Override
    public AmmoCard getAmmo() {
        return game.getAmmos().remove(0);
    }

    @Override
    public WeaponCard getWeapon() {
        return (WeaponCard) game.getWeapons().draw();
    }
}

