package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private final Movable movableActor;
    private Move<Movable> move;
    private Disposable moveDisposable;
    private final Set<Direction> activeDirections = new HashSet<>();
    private final Map<Input.Key, Direction> keyDirectionMap;

    public MovableController(Movable movable) {
        movableActor = movable;

        keyDirectionMap = Map.ofEntries(
            Map.entry(Input.Key.UP, Direction.NORTH),
            Map.entry(Input.Key.DOWN, Direction.SOUTH),
            Map.entry(Input.Key.RIGHT, Direction.EAST),
            Map.entry(Input.Key.LEFT, Direction.WEST)
        );
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (!keyDirectionMap.containsKey(key)) return;
        System.out.println("Key pressed: " + key);

        Direction direction = keyDirectionMap.get(key);
        activeDirections.add(direction);
        updateMovement();
    }

    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if (!keyDirectionMap.containsKey(key)) return;

        Direction direction = keyDirectionMap.get(key);
        activeDirections.remove(direction);
        updateMovement();
    }

    private void updateMovement() {
        if (movableActor==null) return;

        if (move != null) {
            move.stop();
            moveDisposable.dispose();
        }
        var direction = getDirection();

        if (direction!= Direction.NONE) {
            move = new Move<>(direction, 1f);
            moveDisposable=  move.scheduleFor(movableActor);
        }
    }

    private Direction getDirection() {
        var result = Direction.NONE;
        for (var direction : activeDirections) {
            result = result.combine(direction);
        }
        return result;
    }

}
