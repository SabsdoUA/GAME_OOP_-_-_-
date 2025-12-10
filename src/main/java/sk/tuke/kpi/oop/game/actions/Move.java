package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;

public class Move<T extends Movable> implements Action<T> {
    private T actor;
    private final Direction direction;
    private final float duration;
    private float elapsedTime = 0;
    private boolean done = false;

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;

    }

    public Move(Direction direction) {
        this(direction, 0);
    }

    @Override
    public @Nullable T getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable T actor) {
        this.actor = actor;
    }


    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void execute(float deltaTime) {
        if (actor == null|| done) return;

        if (elapsedTime == 0) actor.startedMoving(direction);

        elapsedTime += deltaTime;

        if (elapsedTime >= duration) {
            done = true;
            actor.stoppedMoving();
            return;
        }

        int dx = direction.getDx();
        int dy = direction.getDy();
        float speed = actor.getSpeed();

        actor.setPosition(
            (int) (actor.getPosX() + dx * speed),
            (int) (actor.getPosY() + dy * speed)
        );

        if (Objects.requireNonNull(actor.getScene()).getMap().intersectsWithWall(actor)) {
            actor.setPosition(
                (int) (actor.getPosX() - dx * speed),
                (int) (actor.getPosY() - dy * speed)
            );
            actor.collidedWithWall();
        }
    }

    @Override
    public void reset() {
        elapsedTime = 0;
        done  =false;
    }

    public void stop(){
        if (actor != null) actor.stoppedMoving();
        done = true;
    }
}
