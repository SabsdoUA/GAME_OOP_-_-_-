package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Backpack implements ActorContainer<Collectible> {

    private final String name;
    private final int capacity;
    private final List<Collectible> items;

    public Backpack(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        items = new ArrayList<>();
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(items);
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if (getSize() >= getCapacity())
            throw new IllegalStateException(name + " is full");

        items.add(actor);
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if (!items.contains(actor))
            return;

        items.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if (items.isEmpty()) return null;
        return items.get(items.size() - 1);
    }

    @Override
    public void shift() {
        if (!items.isEmpty())
            Collections.rotate(items,1);
    }

    @Override
    public @NotNull Iterator<Collectible> iterator() {
        return items.iterator();
    }
}
