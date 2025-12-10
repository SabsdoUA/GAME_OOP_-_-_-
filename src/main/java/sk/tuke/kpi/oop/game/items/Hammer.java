package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible {

    public Hammer() {
        super(1);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    public Hammer(int usages) {
        super(usages);
        setAnimation(new Animation("sprites/hammer.png"));
    }

    @Override
    public void useWith(Repairable actor) {
        if (actor == null) return;
        if (actor.repair())
            super.useWith(actor);
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
