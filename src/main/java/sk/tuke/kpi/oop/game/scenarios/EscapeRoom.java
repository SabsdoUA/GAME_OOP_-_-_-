package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.oop.game.SpawnPoint;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class EscapeRoom implements SceneListener {
    private Ripley player;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);

        player = scene.getFirstActorByType(Ripley.class);

        var keeperController = new KeeperController(player);
        var keeperControllerDisposable = scene.getInput().registerListener(keeperController);
        var movementController = new MovableController(player);
        var movementControllerDisposable = scene.getInput().registerListener(movementController);
        var shootingController = new ShooterController(player);
        var shootingControllerDisposable = scene.getInput().registerListener(shootingController);

        scene.follow(player);

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, _ripley -> {
            keeperControllerDisposable.dispose();
            movementControllerDisposable.dispose();
            shootingControllerDisposable.dispose();
        });

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door -> {
            if (door != null && door.getName().equalsIgnoreCase("front door")) {
                scene.getGame().getOverlay().drawText("Well done!", 100, 100).showFor(5);
                new ActionSequence<>(
                    new Wait<>(5),
                    new Invoke<>(()->scene.getGame().stop())
                ).scheduleFor(player);
            }
        });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        player.showRipleyState();
    }


    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name == null) return null;

            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "ammo":
                    return new Ammo();
                case "front door":
                    return new Door("Front Door", Door.Orientation.valueOf(Objects.requireNonNull(type).toUpperCase()));
                case "back door":
                    return new Door("Back Door", Door.Orientation.valueOf(Objects.requireNonNull(type).toUpperCase()));
                case "exit door":
                    return new Door("Exit Door", Door.Orientation.valueOf(Objects.requireNonNull(type).toUpperCase()));
                case "spawnPoint":
                    return new SpawnPoint(5);
                case "alien":
                    if (Objects.requireNonNull(type).equals("running")) {
                        return new Alien(100,new RandomlyMoving());
                    } else if (type.equals("waiting1")) {
                        return new Alien(100,new Observing<>(
                            Door.DOOR_OPENED,
                            door -> door != null && door.getName().equalsIgnoreCase("front door"),
                            new RandomlyMoving()
                        ));
                    } else if (type.equals("waiting2")) {
                        return new Alien(100,new Observing<>(
                            Door.DOOR_OPENED,
                            door -> door != null && door.getName().equalsIgnoreCase("back door"),
                            new RandomlyMoving()
                        ));
                    }
                case "alien mother":
                    return new MotherAlien(new Observing<>(
                        Door.DOOR_OPENED,
                        door -> door != null && door.getName().equalsIgnoreCase("back door"),
                        new RandomlyMoving()
                    ));
                default:
                    return null;
            }
        }
    }
}
