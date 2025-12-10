package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.*;

public class TimeBomb extends AbstractActor {
    private final float countdownTime;
    private boolean activated =false;

    private final Animation activeAnimation;
    private final Animation explodeAnimation;

    public TimeBomb(float countdownTime) {
        this.countdownTime = countdownTime;

        activeAnimation = new Animation("sprites/bomb_activated.png");

        explodeAnimation = new Animation("sprites/small_explosion.png");

        setAnimation(new Animation("sprites/bomb.png"));
    }

    public void activate() {
        if (activated) return;

        activated = true;
        setAnimation(activeAnimation);

        new ActionSequence<>(
            new Wait<>(countdownTime),
            new Invoke<>(this::detonate)
        ).scheduleFor(this);
    }

    public boolean isActivated() {
        return activated;
    }

    public void detonate() {
        if (!activated) return;

        setAnimation(explodeAnimation);
        new When<>(this::animationFinished, new Invoke<>(this::removeBomb)).scheduleFor(this);
        activated = false;
    }
    private boolean animationFinished() {
        return explodeAnimation.getCurrentFrameIndex() >= explodeAnimation.getFrameCount() - 1;
    }

    private void removeBomb() {
        if (getScene()==null) return;
        getScene().removeActor(this);
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(getPosX(), getPosY(),getWidth(),  getHeight());
    }
}
