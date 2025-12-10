package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;

public class Ventilator extends AbstractActor implements Repairable {
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);
    private boolean broken = true;

    public Ventilator() {
        setAnimation(new Animation("sprites/ventilator.png",32,32,0.1f));
        getAnimation().stop();
    }

    @Override
    public boolean repair() {
        if (!broken) return false;
        broken = false;
        getAnimation().play();
        Objects.requireNonNull(getScene()).getMessageBus().publish(VENTILATOR_REPAIRED, this);
        return true;
    }
}
