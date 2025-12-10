package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.EnergyDecrementAction;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.Objects;

public class Alien extends AbstractActor implements Movable, Alive, Enemy {

    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien(){
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f
            , Animation.PlayMode.LOOP));

        health = new Health(100);
    }

    public Alien(int healthValue,Behaviour<? super Alien> behaviour) {
        this.behaviour = behaviour;
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f
            , Animation.PlayMode.LOOP));

        health = new Health(healthValue);

        health.onFatigued(() -> {
            Objects.requireNonNull(getScene()).cancelActions(this);
            getScene().removeActor(this);
        });
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

///does not work properly  need to fix  enabling and disabling
        scene.getActors().stream()
            .filter(actor -> actor instanceof Alive && !(actor instanceof Enemy))
            .forEach(aliveActor -> new When<>(
                () -> this.intersects(aliveActor),
                new EnergyDecrementAction(1, 1f)
            ).scheduleFor((Alive) aliveActor));

        if (behaviour != null) behaviour.setUp(this);
    }


    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    @Override
    public void stoppedMoving() {
        getAnimation().pause();
    }

    @Override
    public Health getHealth() {
        return health;
    }
}
