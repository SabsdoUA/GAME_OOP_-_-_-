package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable,Repairable {
    private int temperature = 0;
    private int damage = 0;

    private Animation previousAnimation = null;

    private final Animation normalAnimation;
    private final float normalFrameDuration = 0.1f;
    private final Animation overheatedAnimation;
    private final float overheatedFrameDuration = 0.05f;
    private final Animation brokenAnimation;
    private final float brokenFrameDuration = 0.1f;
    private final Animation nonActiveAnimation;
    private final Animation extinguishedAnimation;

    private final Set<EnergyConsumer> consumers = new HashSet<>();

    private boolean active = false;

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public Reactor() {
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, normalFrameDuration, Animation.PlayMode.LOOP_PINGPONG);
        overheatedAnimation = new Animation("sprites/reactor_hot.png", 80, 80, overheatedFrameDuration, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, brokenFrameDuration, Animation.PlayMode.LOOP_PINGPONG);

        nonActiveAnimation = new Animation("sprites/reactor.png");
        extinguishedAnimation = new Animation("sprites/reactor_extinguished.png");

        turnOff();
    }

    @Override
    public void turnOff() {
        previousAnimation = getAnimation();
        setAnimation(nonActiveAnimation);
        for (EnergyConsumer consumer : consumers)
            consumer.setPowered(false);
        active = false;

    }

    @Override
    public void turnOn() {
        if (damage ==100) return;
        active = true;
        setAnimation(previousAnimation);
        for (EnergyConsumer consumer : consumers)
            consumer.setPowered(true);
    }

    @Override
    public boolean isOn() {
        return active;
    }


    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    public void increaseTemperature(int increment) {

        if (damage >= 100 || increment < 0 || !active) {
            return;
        }

        if (damage >= 33 && damage <= 66) temperature += (int) (increment * 1.5);
        else if (damage > 66) temperature += increment * 2;
        else temperature += increment;

        increaseDamage();
        updateAnimation();
    }

    private void increaseDamage() {
        if (temperature > 6000) {
            damage = 100;
            active = false;
            for (EnergyConsumer consumer : consumers)
                consumer.setPowered(false);
        } else if (temperature > 2000) damage = Math.min((temperature - 2000) / 40, 100);
    }

    public void decreaseTemperature(int decrement) {
        if (damage == 100 || decrement < 0 || !active) return;
        if (damage >= 50) temperature = Math.max(0, temperature - decrement / 2);
        else temperature = Math.max(0, temperature - decrement);

        updateAnimation();
    }

    public boolean repair() {
        if (!active || damage < 0 || damage == 100) return false;

        int newDamage = damage - 50;
        damage = Math.max(0, newDamage);

        temperature = (int)(temperature * (1 - ((double) 50 / 100)));

        updateAnimation();
        return true;
    }

    public boolean extinguish() {
        if ( !active) return false;

        temperature = Math.min(4000, temperature);

        setAnimation(extinguishedAnimation);
        return true;
    }

    private void updateAnimation() {
        if (temperature > 4000 && temperature < 6000) {
            overheatedAnimation.setFrameDuration(calculateFrameDuration(overheatedFrameDuration, damage));
            setAnimation(overheatedAnimation);
        } else if (temperature == 6000) {
            brokenAnimation.setFrameDuration(calculateFrameDuration(brokenFrameDuration, damage));
            setAnimation(brokenAnimation);
        } else {
            normalAnimation.setFrameDuration(calculateFrameDuration(normalFrameDuration, damage));
            setAnimation(normalAnimation);
        }
    }

    private float calculateFrameDuration(float baseFrameDuration, float damage) {
        return baseFrameDuration - (damage * baseFrameDuration / 100);
    }

    public void addDevice(EnergyConsumer consumer) {
        if (consumer == null) return;
        consumers.add(consumer);

        if (active) consumer.setPowered(true);
    }

    public void removeDevice(EnergyConsumer consumer) {
        if (consumer != null && consumers.remove(consumer)) {
            consumer.setPowered(false);
        }
    }
}
