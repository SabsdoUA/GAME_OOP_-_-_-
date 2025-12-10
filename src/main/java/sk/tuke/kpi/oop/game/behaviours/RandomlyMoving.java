package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable> {
    private final Random random = new Random();

    @Override
    public void setUp(Movable actor) {
        // does not work properly
        if (actor == null) return;

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() ->
                {
                    Direction randomDirection = getRandomDirection();
                    new Move<>(randomDirection, 2).scheduleFor(actor);
                }),
                new Wait<>(2)
            )
        ).scheduleFor(actor);
    }

    private Direction getRandomDirection() {
        Direction[] directions = Direction.values();
        Direction randomDirection;
        do {
            randomDirection = directions[random.nextInt(directions.length)];
        } while (randomDirection == Direction.NONE);
        return randomDirection;
    }
}
