package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private final Orientation orientation;

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }

    private boolean open = true;

    public Door(Orientation orientation) {
        this.orientation = orientation;

        if (orientation == Orientation.VERTICAL)
            setAnimation(new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED));
        else if (orientation == Orientation.HORIZONTAL)
            setAnimation(new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED));
    }

    public Door(String name, Orientation orientation) {
        super(name);
        this.orientation = orientation;

        if (orientation == Orientation.VERTICAL)
            setAnimation(new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED));
        else if (orientation == Orientation.HORIZONTAL)
            setAnimation(new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        close();
    }

    @Override
    public void open() {
        if (open) return;
        getAnimation().setPlayMode(Animation.PlayMode.ONCE);
        open = true;
        setWall(false);
        Objects.requireNonNull(getScene()).getMessageBus().publish(DOOR_OPENED, this);
        System.out.println("open door");
    }

    @Override
    public void close() {
        if (!open) return;
        getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        open = false;
        setWall(true);
        Objects.requireNonNull(getScene()).getMessageBus().publish(DOOR_CLOSED, this);
    }

    private void setWall(boolean wall) {
        int tileX = getPosX() / 16;
        int tileY = getPosY() / 16;

        int width = orientation == Orientation.HORIZONTAL ? 2 : 1;
        int height = orientation == Orientation.VERTICAL ? 2 : 1;

        for (int x = tileX; x < tileX + width; x++) {
            for (int y = tileY; y < tileY + height; y++) {
                MapTile tile = Objects.requireNonNull(getScene()).getMap().getTile(x, y);
                tile.setType(wall ? MapTile.Type.WALL : MapTile.Type.CLEAR);
            }
        }


    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void useWith(Actor actor) {
        if (open) close();
        else open();
    }
}
