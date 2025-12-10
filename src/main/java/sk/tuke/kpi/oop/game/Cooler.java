package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class Cooler extends AbstractActor implements Switchable {
    private final Reactor reactor;
    private boolean active = false;

    public Cooler(@Nullable Reactor reactor) {
        this.reactor = reactor;
    }

    @Override
    public void turnOn() {
        active = true;
    }

    @Override
    public void turnOff() {
        active = false;
    }

    @Override
    public boolean isOn() {
        return active;
    }

    public void coolReactor() {
        if (!active) return;
        if (reactor == null) return;

        reactor.decreaseTemperature(1);
    }

    public Reactor getReactor() {
        return reactor;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
