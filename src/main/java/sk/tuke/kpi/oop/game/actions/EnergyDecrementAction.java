package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.characters.Alive;

public class EnergyDecrementAction extends AbstractAction<Alive> {

    private final int decrement;
    private final float rate;
    private float timer;

    public EnergyDecrementAction(int increment, float rate) {
        this.decrement = increment;
        this.rate = rate;
    }

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) return;

        if (timer >=rate &&  getActor().getHealth().getValue()>0){
            getActor().getHealth().drain(decrement);
            timer = 0;
        }

        timer += deltaTime;
    }
}
