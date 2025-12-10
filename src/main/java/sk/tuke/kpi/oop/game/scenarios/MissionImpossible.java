package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.actions.EnergyDecrementAction;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class MissionImpossible implements SceneListener {
    private Ripley player;
    private Disposable energyDecrementDisposable;

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);
        System.out.println("sceneInitialized");

        player = scene.getFirstActorByType(Ripley.class);
        var door = scene.getFirstActorByType(Door.class);
        Objects.requireNonNull(door).close();

        scene.getGame().pushActorContainer(player.getBackpack());
        player.getBackpack().add(new AccessCard());

        var keeperController = new KeeperController(player);
        var keeperControllerDisposable = scene.getInput().registerListener(keeperController);
        var movementController = new MovableController(player);
        var movementControllerDisposable = scene.getInput().registerListener(movementController);

        scene.follow(player);
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, _door -> energyDecrementDisposable = new EnergyDecrementAction(1, 0.25f).scheduleFor(player));

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, _ripley -> {
            keeperControllerDisposable.dispose();
            movementControllerDisposable.dispose();
            energyDecrementDisposable.dispose();
        });

        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, _ventilator -> energyDecrementDisposable.dispose());
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
//                case "front door":
//                    return new LockedDoor();
//                case "locked door":
//                    return new LockedDoor();
                case "access card":
                    return new AccessCard();
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();
                default:
                    return null;
            }
        }
    }
}
