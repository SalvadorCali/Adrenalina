package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.cards.AmmoCard;
import it.polimi.ingsw.model.cards.PowerupCard;
import it.polimi.ingsw.model.cards.WeaponCard;
import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.enums.Direction;
import it.polimi.ingsw.model.enums.TokenColor;
import it.polimi.ingsw.model.gamecomponents.Ammo;
import it.polimi.ingsw.model.gamecomponents.Game;
import it.polimi.ingsw.model.gamecomponents.Player;
import it.polimi.ingsw.model.gamecomponents.Position;


public class ActionController implements ActionInterface {


    public ActionController(Game game){
        this.game = game;
        clientData = new ClientData();
    }

    private Game game;

    private ClientData clientData;

    public ClientData getClientData() {
        return clientData;
    }

    @Override
    public void generatePlayer(Player victim , Player player) {
        game.getBoard().generatePlayer(victim.getPosition().getX(), victim.getPosition().getY(), player);
    }

    @Override
    public void move(Direction direction, Player player) {
        game.getBoard().move(direction, player);
    }

    @Override
    public Direction getFirstMove() {
        return clientData.getFirstMove();
    }

    @Override
    public Direction getSecondMove() {
        return clientData.getSecondMove();
    }

    @Override
    public void removePlayer(Player player) {
        game.getBoard().getArena()[player.getPosition().getX()][player.getPosition().getY()].getPlayers().remove(player);
    }

    @Override
    public Position getSquare() {
        return clientData.getSquare();
    }

    @Override
    public Player getThirdVictim() {
        return clientData.getThirdVictim();
    }

    @Override
    public boolean noOutOfBounds(Player player, Direction direction) {
        return game.getBoard().noOutofBounds(player, direction);
    }

    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    @Override
    public boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos) {
        return clientData.getCurrentPlayer().ammoControl(redAmmos, blueAmmos, yellowAmmos);
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
    public boolean isVisible(Player shooter, Player victim) {
        return game.getBoard().isVisible(shooter, victim);
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
    public boolean sameSquare(Player shooter, Player victim) {
        return game.sameSquare(shooter, victim);
    }

    @Override
    public boolean isVisibleDifferentSquare(int x, int y){
        return game.getBoard().isVisibleDifferentSquare(clientData.getCurrentPlayer(), x, y);
    }

    @Override
    public void updateAmmoBox(int redAmmos, int blueAmmos, int yellowAmmos) {
        game.getCurrentPlayer().updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    @Override
    public boolean canMove(Player victim, Direction... directions) {
        return game.getBoard().canMove(victim, directions);
    }

    @Override
    public int distanceControl(int x, int y){

        return game.getBoard().distance(clientData.getCurrentPlayer(), x, y);
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

    @Override
    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    @Override
    public boolean squareControl(int x, int y, Player player) {

        return game.getBoard().getArena()[x][y].getPlayers().contains(player);
    }

    @Override
    public Player getVictim() {

        return clientData.getVictim();
    }

    @Override
    public Player getSecondVictim() {
        return clientData.getSecondVictim();
    }


}

