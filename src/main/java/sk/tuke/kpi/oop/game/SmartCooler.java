package sk.tuke.kpi.oop.game;

public class SmartCooler extends Cooler {
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    @Override
    public void coolReactor() {
        if (getReactor() == null) return;

        if (getReactor().getTemperature() > 2500)
            turnOn();
        else if (getReactor().getTemperature() < 1500)
            turnOff();

        if (isOn())
            super.coolReactor();
    }
}
