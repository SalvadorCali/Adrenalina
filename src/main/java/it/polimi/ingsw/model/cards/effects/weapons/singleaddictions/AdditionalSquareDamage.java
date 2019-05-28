package it.polimi.ingsw.model.cards.effects.weapons.singleaddictions;

import it.polimi.ingsw.model.cards.effects.ActionInterface;
import it.polimi.ingsw.model.cards.effects.Effect;
import it.polimi.ingsw.util.Printer;

public class AdditionalSquareDamage extends SingleAddictionEffect{

    private String effectName;

    private int x, y;

    private int damagePower;

    private int redAmmos, blueAmmos, yellowAmmos;

    private boolean basicFirst;

    private boolean canUse;


    public AdditionalSquareDamage(String effectName, int damagePower, int redAmmos, int blueAmmos, int yellowAmmos, Effect effect){

        this.effectName = effectName;
        this.damagePower = damagePower;
        this.redAmmos = redAmmos;
        this.blueAmmos = blueAmmos;
        this.yellowAmmos = yellowAmmos;
        super.effect = effect;

    }


    @Override
    public boolean canUseEffect(ActionInterface actionInterface) {

        setData(actionInterface);


        if(basicFirst){
            canUse = super.effect.canUseEffect(actionInterface) && actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos);
            if(canUse && !effectName.equals("Rocket Launcher"))
                canUse = actionInterface.isVisibleDifferentSquare(x, y);
        }else{
            canUse = actionInterface.ammoControl(redAmmos, blueAmmos, yellowAmmos) && (actionInterface.isVisibleDifferentSquare(x, y) || (x == actionInterface.getClientData().getCurrentPlayer().getPosition().getX() && y == actionInterface.getClientData().getCurrentPlayer().getPosition().getY()));
                if(canUse)
                    canUse = super.effect.canUseEffect(actionInterface);
        }
        return canUse;
    }


    @Override
    public void useEffect(ActionInterface actionInterface) {

        super.effect.useEffect(actionInterface);
        Printer.print(x);
        Printer.println(y);
        actionInterface.squareDamage(x, y, damagePower, 0);
        if(effectName.equals("Rocket Launcher") && (actionInterface.getVictim().getPosition().getX()!= x || actionInterface.getVictim().getPosition().getY()!=y))
            actionInterface.playerDamage(actionInterface.getVictim().getColor(), damagePower);
        actionInterface.updateAmmoBox(redAmmos, blueAmmos, yellowAmmos);
    }

    private void setData(ActionInterface actionInterface){
        if(effectName.equals("Rocket Launcher")){
            actionInterface.getClientData().setSquare(actionInterface.getClientData().getVictim().getPosition().getX(), actionInterface.getClientData().getVictim().getPosition().getY());
        }else {
            this.x = actionInterface.getClientData().getSquare().getX();
            this.y = actionInterface.getClientData().getSquare().getY();
        }
        this.basicFirst = actionInterface.basicFirst();
        if(!basicFirst)
            actionInterface.getClientData().setAmmos();

    }
}
