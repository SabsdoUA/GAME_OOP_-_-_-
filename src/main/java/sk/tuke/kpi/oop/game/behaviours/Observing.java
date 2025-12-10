package sk.tuke.kpi.oop.game.behaviours;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;
import java.util.function.Predicate;

public class Observing<T, A extends Actor> implements Behaviour<A> {
    private final Topic<T> topic;
    private final Predicate<T> predicate;
    private final Behaviour<A> delegate;

    public Observing(@NotNull Topic<T> topic, @NotNull Predicate<T> predicate, @NotNull Behaviour<A> delegate) {
        this.topic = topic;
        this.predicate = predicate;
        this.delegate = delegate;
    }

    @Override
    public void setUp(A actor) {
        if (actor == null) return;

        Objects.requireNonNull(actor.getScene()).getMessageBus().subscribe(topic, message -> {
            if (predicate.test(message)) {
                delegate.setUp(actor);
            }
        });
    }
}
