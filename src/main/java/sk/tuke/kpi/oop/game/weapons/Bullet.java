package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;
//
//public class Bullet extends AbstractActor implements Fireable {
//
//    public Bullet(){
//        setAnimation(new Animation("sprites/bullet.png",16,16));
//    }
//
//    @Override
//    public int getSpeed() {
//        return 4;
//    }
//
//    @Override
//    public void startedMoving(Direction direction) {
//        getAnimation().setRotation(direction.getAngle());
//    }
//
//    @Override
//    public void collidedWithWall() {
//      Objects.requireNonNull(getScene()).cancelActions(this);
//        getScene().removeActor(this);
//    }
//}


public class Bullet extends AbstractActor implements Fireable {

    public Bullet() {
        setAnimation(new Animation("sprites/bullet.png",
            16, 16, 0.1f, Animation.PlayMode.LOOP));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new When<>(
            () -> intersectsAnyAlive() || scene.getMap().intersectsWithWall(this),
            new Invoke<>(() -> {
                if (intersectsAnyAlive()) {
                    scene.getActors().stream()
                        .filter(actor -> actor instanceof Alive)
                        .filter(this::intersects)
                        .forEach(aliveActor -> ((Alive) aliveActor).getHealth().drain(15));
                }
                collidedWithWall();
            })
        ).scheduleFor(this);
    }

    private boolean intersectsAnyAlive() {
        return Objects.requireNonNull(getScene()).getActors().stream()
            .filter(actor -> actor instanceof Alive && !(actor instanceof Ripley))
            .anyMatch(this::intersects);
    }

    @Override
    public int getSpeed() {
        return 4;
    }

    @Override
    public void collidedWithWall() {
        Scene scene = Objects.requireNonNull(getScene());
        scene.removeActor(this);
    }
}
