package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Firearm;

import java.util.Objects;

public class Fire<A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        if (getActor()==null) {
            setDone(true);
            return;
        }

        Armed armedActor = getActor();

        Firearm firearm = armedActor.getFirearm();
        if (firearm == null) return;

        var bullet = firearm.fire();
        if (bullet == null) return;
        Objects.requireNonNull(armedActor.getScene()).addActor(bullet, armedActor.getPosX(), armedActor.getPosY());


        float angle = armedActor.getAnimation().getRotation();
        Direction direction = Direction.fromAngle(angle);

        bullet.getAnimation().setRotation(angle);

        new Move<>(direction, 1).scheduleFor(bullet);
//
//        armedActor.getScene().getActors().stream()
//            .filter(actor -> actor instanceof Alive && actor instanceof Enemy)
//            .forEach(aliveActor -> disposable = new When<>(
//                () -> bullet.intersects(aliveActor),
//                new Invoke<>(() -> {
//                    ((Alive) aliveActor).getHealth().drain(15);
//                    bullet.collidedWithWall();
//                    disposable.dispose();
//                })).scheduleFor(aliveActor));

        setDone(true);
    }
}
