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

/**
 * Controller of actions made by the cards.
 */
public class ActionController implements ActionInterface {

    /**
     * Class constructor.
     * @param game current game.
     */
    public ActionController(Game game){
        this.game = game;
        clientData = new ClientData();
    }

    /**
     * Current game.
     */
    private Game game;

    /**
     * Saves the data given by the client.
     */
    private ClientData clientData;

    /**
     * Getter of the clientData.
     * @return clientData.
     */
    public ClientData getClientData() {
        return clientData;
    }

    /**
     * Generates a player in the position of a weapon victim.
     * @param victim victim that gives the position.
     * @param player player to set on the board.
     */
    @Override
    public void generatePlayer(Player victim , Player player) {
        game.getBoard().generatePlayer(victim.getPosition().getX(), victim.getPosition().getY(), player);
    }

    /**
     * Moves a player in a direction.
     * @param direction chosen direction.
     * @param player player to move.
     */
    @Override
    public void move(Direction direction, Player player) {
        game.getBoard().move(direction, player);
    }

    /**
     * Gets the first move saved in the client data.
     * @return the first move of the client data.
     */
    @Override
    public Direction getFirstMove() {
        return clientData.getFirstMove();
    }

    /**
     * Gets the second move saved in the client data.
     * @return the second move saved in the client data.
     */
    @Override
    public Direction getSecondMove() {
        return clientData.getSecondMove();
    }

    /**
     * Gets the third move saved in the client data.
     * @return the third move saved in the client data.
     */
    @Override
    public Direction getThirdMove(){
        return clientData.getThirdMove();
    }

    /**
     * Gets the fourth move saved in the client data.
     * @return the fourth move saved in the client data.
     */
    @Override
    public Direction getFourthMove(){
        return clientData.getFourthMove();
    }

    /**
     * Removes a player from the game board.
     * @param player player to remove from the game board.
     */
    @Override
    public void removePlayer(Player player) {
        game.getBoard().getArena()[player.getPosition().getX()][player.getPosition().getY()].getPlayers().remove(player);
    }

    /**
     * Gets the position saved in the client data.
     * @return the position saved in the client data.
     */
    @Override
    public Position getSquare() {
        return clientData.getSquare();
    }

    /**
     * Gets the third victim saved in the client data.
     * @return the third victim saved in the client data.
     */
    @Override
    public Player getThirdVictim() {
        return clientData.getThirdVictim();
    }

    /**
     * Controls that the player isn't going out of bounds.
     * @param player player that wants to move.
     * @param direction direction of the move to control.
     * @return the result of the control.
     */
    @Override
    public boolean noOutOfBounds(Player player, Direction direction) {
        return game.getBoard().noOutofBounds(player, direction);
    }

    /**
     * Gets the fake player from the client data.
     * @return the fake player saved in the client data.
     */
    @Override
    public Player getFakePlayer() {
        return clientData.getFakePlayer();
    }

    /**
     * Gets the value of basic first saved in client data.
     * @return the value of basic first.
     */
    @Override
    public boolean basicFirst() {
        return clientData.basicFirst();
    }

    /**
     * Setter of clientData.
     * @param clientData chosen clientData to set.
     */
    public void setClientData(ClientData clientData) {
        this.clientData = clientData;
    }

    /**
     * Controls if the player has enough ammos to apply an action.
     * @param redAmmos red ammos required for the action.
     * @param blueAmmos blue ammos required for the action.
     * @param yellowAmmos yellow ammos required for the action.
     * @return the result of the control.
     */
    @Override
    public boolean ammoControl(int redAmmos, int blueAmmos, int yellowAmmos) {
        return clientData.ammoControl(redAmmos, blueAmmos, yellowAmmos);
    }

    /**
     * Damages a player of a certain color.
     * @param color color of the player to damage.
     * @param damagePower number of damages given to the player.
     */
    @Override
    public void playerDamage(TokenColor color, int damagePower) {
        for(int i = 0; i < damagePower; i++) {
            if(game.findPlayer(color).getPlayerBoard().getDamageBoard()[11].getFirstColor().equals(TokenColor.NONE)) {
                game.findPlayer(color).getPlayerBoard().addDamage(game.getCurrentPlayer().getColor());
                game.findPlayer(color).setDamaged(true);
            }
        }
    }

    /**
     * Gives marks to the victim.
     * @param shooter player who gives the marks.
     * @param victim victim receiving the marks.
     */
    @Override
    public void playerMark(Player shooter, Player victim) {
        for(int j = 0; j < game.getPlayers().size(); j++) {
            if (game.getPlayers().get(j).equals(victim)&& game.getPlayers().get(j).getPlayerBoard().getRevengeMarks().size() < 3)
                game.getPlayers().get(j).getPlayerBoard().addRevengeMarks(shooter.getColor());
        }
    }

    /**
     * Gives damages to the victim.
     * @param victim player who is damaged.
     * @param damagePower number of damages.
     */
    public void playerDamage(Player victim, int damagePower){
        for(int i = 0; i < damagePower; i++){
            for(int j = 0; j < game.getPlayers().size(); j++){
                if(game.getPlayers().get(j).equals(victim)) {
                    if(game.getPlayers().get(j).getPlayerBoard().getDamageBoard()[11].getFirstColor().equals(TokenColor.NONE)) {
                        game.getPlayers().get(j).getPlayerBoard().addDamage(game.getCurrentPlayer().getColor());
                        victim.setDamaged(true);
                    }
                }
            }
        }
    }

    /**
     * Gives marks to the victim.
     * @param victim player who is marked.
     * @param markPower number of marks.
     */
    @Override
    public void playerMark(Player victim, int markPower) {
        for(int i = 0; i < markPower; i++){
            for(int j = 0; j < game.getPlayers().size(); j++){
                if(game.getPlayers().get(j).equals(victim) && game.getPlayers().get(j).getPlayerBoard().getRevengeMarks().size() < 3)
                    game.getPlayers().get(j).getPlayerBoard().addRevengeMarks(game.getCurrentPlayer().getColor());
            }
        }
    }

    /**
     * Controls if the player of a certain color is visible from the current player.
     * @param color color of the victim.
     * @return the result of the control.
     */
    @Override
    public boolean isVisible(TokenColor color) {
        return game.isVisible(game.findPlayer(color));
    }

    /**
     * Control if the victim is visible from the shooter.
     * @param shooter player who's shooting.
     * @param victim victim.
     * @return the result of the control.
     */
    @Override
    public boolean isVisible(Player shooter, Player victim) {
        return game.getBoard().isVisible(shooter, victim);
    }

    /**
     * Adds ammos to the current player.
     * @param ammos ammos to add.
     */
    @Override
    public void addAmmo(Ammo...ammos){
        game.getCurrentPlayer().addAmmo(ammos);
    }

    /**
     * Adds a powerup to the current player's powerups.
     */
    @Override
    public void addPowerup(){
        if(game.getCurrentPlayer().getPowerups().size() < 3){
            game.getCurrentPlayer().addPowerup((PowerupCard) game.getPowerup().draw());
        }
    }

    /**
     * Adds a weapon to the current player's weapon cards.
     * @param weaponCard card to add.
     */
    @Override
    public void addWeapon(WeaponCard weaponCard){
        game.getCurrentPlayer().addWeapon(weaponCard);
    }

    /**
     * Controls if the shooter and the victim are in the same square.
     * @param shooter player who's shooting.
     * @param victim player who's getting shooted.
     * @return the result of the control.
     */
    @Override
    public boolean sameSquare(Player shooter, Player victim) {
        return game.sameSquare(shooter, victim);
    }

    /**
     * Controls if a square is visible, but isn't the same square as the shooter's one.
     * @param x row of the square.
     * @param y column of th square.
     * @return the result of the control.
     */
    @Override
    public boolean isVisibleDifferentSquare(int x, int y){
        return game.getBoard().isVisibleDifferentSquare(clientData.getCurrentPlayer(), x, y);
    }

    /**
     * Updates the ammo box of the current player after an action.
     * @param redAmmos red ammos required by the action.
     * @param blueAmmos blue ammos required by the action.
     * @param yellowAmmos yellow ammos required by the action.
     */
    @Override
    public void updateAmmoBox(int redAmmos, int blueAmmos, int yellowAmmos) {
        game.getCurrentPlayer().updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    /**
     * Controls if the victim can be moved in some consecutive directions.
     * @param victim player to move.
     * @param directions consecutive directions of the move.
     * @return the result of the control.
     */
    @Override
    public boolean canMove(Player victim, Direction... directions) {
        return game.getBoard().canMove(victim, directions);
    }

    /**
     * Controls the distance of a square from the current player.
     * @param x row of the square.
     * @param y column of the square.
     * @return the result of the control.
     */
    @Override
    public int distanceControl(int x, int y){
        return game.getBoard().distance(clientData.getCurrentPlayer(), x, y);
    }

    /**
     * Damages all the players present in a room.
     * @param x row of a square in the room.
     * @param y column of a square in the room.
     * @param damagePower number of damages given by the action.
     * @param markPower number of marks given by the action.
     */
    @Override
    public void roomDamage(int x, int y, int damagePower, int markPower) {
        game.getBoard().roomDamage(x, y, damagePower, markPower, game.getCurrentPlayer().getColor());
    }

    /**
     * Damages all the players present in a square.
     * @param x row of the square.
     * @param y column of the square.
     * @param damagePower number of damages given by the action.
     * @param markPower number of marks given by the action.
     */
    @Override
    public void squareDamage(int x, int y, int damagePower, int markPower) {
        game.getBoard().getArena()[x][y].squareDamage(damagePower, markPower, game.getCurrentPlayer().getColor());
    }

    /**
     * Moves the victim in a square on the board.
     * @param x row of the square.
     * @param y column of the square.
     * @param victim victim of the movement.
     */
    @Override
    public void move(int x, int y, Player victim) {
        game.getBoard().move(x, y, victim);
    }

    /**
     * Getter of the ammo card.
     * @return the ammo card.
     */
    @Override
    public AmmoCard getAmmo() {
        return game.getAmmos().remove(0);
    }

    /**
     * Controls if the player can grab a weapon.
     * @return the result of the control.
     */
    @Override
    public boolean canGetWeapon(){
        return game.getWeapons().size()>0;
    }

    /**
     * Draws a weapon card.
     * @return a weapon card.
     */
    @Override
    public WeaponCard getWeapon() {
        return (WeaponCard) game.getWeapons().draw();
    }

    /**
     * Getter of the current player of the game.
     * @return the current player.
     */
    @Override
    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    /**
     * Controls that a square contains a certain player.
     * @param x row of the square.
     * @param y column of the square.
     * @param player player in the square.
     * @return the result of the control.
     */
    @Override
    public boolean squareControl(int x, int y, Player player) {
        if(player == null)
            return true;
        return game.getBoard().getArena()[x][y].getPlayers().contains(player);
    }

    /**
     * Gets the first victim from the client data.
     * @return the first victim saved in the client data.
     * */
    @Override
    public Player getVictim() {
        return clientData.getVictim();
    }

    /**
     * Gets the second victim from the client data.
     * @return the second victim saved in the client data.
     */
    @Override
    public Player getSecondVictim() {
        return clientData.getSecondVictim();
    }

    /**
     * Controls the distance of a player from a certain square.
     * @param player player.
     * @param x row of the square.
     * @param y column of the square.
     * @return the distance between the player and the square.
     */
    @Override
    public int distanceControl(Player player, int x, int y){
        return game.getBoard().distance(player, x, y);
    }

    /**
     * Controls if the victim of the powerup is damaged.
     * @return the result of the control.
     */
    @Override
    public boolean isDamaged(){
        return clientData.getPowerupVictim().isDamaged();
    }

    /**
     * Controls if a square is active.
     * @param position position of the square to control.
     * @return the result of the control.
     */
    @Override
    public boolean isActive(Position position){
        return game.getBoard().getArena()[position.getX()][position.getY()].isActive();
    }
}

