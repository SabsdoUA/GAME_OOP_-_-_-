package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean active = false;
    private boolean electricity = false;

    private final Animation lightOnAnimation;
    private final Animation lightOffAnimation;

    public Light() {
        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16);
        setAnimation(lightOffAnimation);
    }

    public Animation getLightAnimation(boolean state)
    {
        return state ? lightOnAnimation : lightOffAnimation;
    }

    @Override
    public void turnOn() {
        active = true;
        updateAnimation();
    }

    @Override
    public void turnOff() {
        active = false;
        updateAnimation();
    }

    public void toggle() {
        active = !active;
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return active;
    }

    public boolean noElectricity() {
        return !electricity;
    }

    @Override
    public void setPowered(boolean powered) {
        electricity = powered;
        updateAnimation();
    }

    private void updateAnimation() {
        if (active && electricity)
            setAnimation(lightOnAnimation);
        else
            setAnimation(lightOffAnimation);
    }
}
