package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Game;

public class ActionController implements ActionInterface {


    private Game game;

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
}

