package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

public class PowerSwitch extends AbstractActor {
    private final Switchable switchable;

    public PowerSwitch(Switchable switchable) {
        this.switchable = switchable;
    }

    public Switchable getDevice() {
        return switchable;
    }

    public void switchOn() {
        if (switchable == null) return;
        switchable.turnOn();
    }

    public void switchOff() {
        if (switchable == null) return;
        switchable.turnOff();
    }
}
