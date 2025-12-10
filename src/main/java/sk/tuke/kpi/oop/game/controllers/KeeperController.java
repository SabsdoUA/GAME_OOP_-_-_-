package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class KeeperController implements KeyboardListener {
    private final Keeper actor;

    public KeeperController(Keeper actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        KeyboardListener.super.keyPressed(key);

        if (actor == null) return;

        switch (key) {
            case ENTER:
                new Take<>().scheduleFor(actor);
                break;
            case BACKSPACE:
                new Drop<>().scheduleFor(actor);
                break;
            case S:
                new Shift<>().scheduleFor(actor);
                break;
            case U: {
                var interactable = Objects.requireNonNull(actor.getScene())
                    .getActors().stream()
                    .filter(actor::intersects)
                    .filter(a -> a instanceof Usable<?>)
                    .findFirst()
                    .orElse(null);

                if (interactable == null) {
                    System.out.println("No usable object found.");
                    return;
                }

                @SuppressWarnings("unchecked")
                Usable<Actor> usable = (Usable<Actor>) interactable;

                new Use<>(usable).scheduleForIntersectingWith(actor);
                break;
            }
            case B: {
                var topItem = actor.getBackpack().peek();
                if (!(topItem instanceof Usable<?>)) {
                    System.out.println("Top item is not usable.");
                    return;
                }

                @SuppressWarnings("unchecked")
                Usable<Actor> usable = (Usable<Actor>) topItem;

                new Use<>(usable).scheduleForIntersectingWith(actor);
                break;
            }
            default: {}
        }
    }
}
