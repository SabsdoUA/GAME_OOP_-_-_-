package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

import java.util.Random;

public class DefectiveLight extends Light implements Repairable {
    private Disposable blinkingAction;
    private float lastRepairTime;
    private final Random random = new Random();

    public DefectiveLight() {
        super();
    }

    private void behave() {
        if (noElectricity() && isOn()) {
            setAnimation(getLightAnimation(false));
            return;
        }

        if (!isOn() || noElectricity()) return;

        if (random.nextInt(5) == 1) {
            if (getAnimation() == getLightAnimation(true))
                setAnimation(getLightAnimation(false));
            else
                setAnimation(getLightAnimation(true));
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        blinkingAction = new Loop<>(new Invoke<>(this::behave)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if (System.currentTimeMillis() - lastRepairTime < 1000)
            return false;

        lastRepairTime = System.currentTimeMillis();

        if (blinkingAction != null)
            blinkingAction.dispose();

        turnOn();

        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(() -> blinkingAction = new Loop<>(new Invoke<>(this::behave)).scheduleFor(this))
        ).scheduleFor(this);

        return true;
    }


}
