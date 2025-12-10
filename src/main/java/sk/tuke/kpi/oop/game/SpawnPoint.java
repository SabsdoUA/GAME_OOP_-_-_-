package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Point;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class SpawnPoint extends AbstractActor {
    private int maxAmount;
    private int counter;

    public SpawnPoint(int maxAliens) {
        super("spawnPoint");
        maxAmount = maxAliens;
        counter = maxAliens;
        setAnimation(new Animation("sprites/spawn.png", 32, 32, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        var ripley = scene.getFirstActorByType(Ripley.class);
        if (ripley == null) return;

        new Loop<>(new When<>(this::isCounterMax,
            new Invoke<>(() -> spawnAlien(scene, ripley)
            ))).scheduleFor(this);

        new Loop<>(
            new ActionSequence<>(new Invoke<>(() -> spawnAlien(scene, ripley)
            ), new Wait<>(3))
        ).scheduleFor(this);
    }

    private void spawnAlien(@NotNull Scene scene, @NotNull Ripley ripley){
        if (counter > 0 && distance(getPosition(), ripley.getPosition()) <=50) {
            scene.addActor(new Alien(100, new RandomlyMoving()), getPosX()+16, getPosY()+16);
            counter--;
        }
    }

    private boolean isCounterMax(){
        return counter == maxAmount;
    }

    private double distance(Point p1, Point p2) {
        return Math.sqrt((Math.pow((double) (p1.getX() + 16) - p2.getX(), 2)
            + Math.pow((double) (p1.getY() + 16) - p2.getY(), 2)));
    }
}
