package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<A extends Actor> extends AbstractAction<A> {
    private final Usable<A> usableActor;

    public Use(Usable<A> actor) {
        this.usableActor = actor;
    }

    public Disposable scheduleForIntersectingWith(A mediatingActor) {
        if (mediatingActor == null) return null;

        setActor(mediatingActor);
        Scene scene = mediatingActor.getScene();
        if (scene == null || usableActor == null) return null;

        Class<A> usingActorClass = usableActor.getUsingActorClass();

        return scene.getActors().stream()
            .filter(mediatingActor::intersects)
            .filter(usingActorClass::isInstance)
            .map(usingActorClass::cast)
            .findFirst()
            .map(this::scheduleFor)
            .orElse(null);
    }


    @Override
    public void execute(float deltaTime) {
        if (usableActor == null || getActor() == null) {
            setDone(true);
            return;
        }

        usableActor.useWith(getActor());
        setDone(true);
    }
}
