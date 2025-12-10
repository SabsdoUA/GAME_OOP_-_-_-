package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Take<T extends Keeper> extends AbstractAction<T> {
    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) {
            setDone(true);
            return;
        }

        Scene scene = getActor().getScene();
        var collectibles = Objects.requireNonNull(scene).getActors();

        for (var collectible : collectibles) {
            if (!(collectible instanceof Collectible)) continue;

            if (!getActor().intersects(collectible)) continue;

            try {
                getActor().getBackpack().add((Collectible)collectible);
                scene.removeActor(collectible);
                break;
            } catch (IllegalStateException e) {
                Overlay overlay = scene.getGame().getOverlay();
                overlay.drawText(e.getMessage(), 10, 50).showFor(2);
            }
        }

        setDone(true);
    }
}
