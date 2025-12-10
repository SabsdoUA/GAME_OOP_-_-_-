package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.Objects;

public class Drop<T extends Keeper> extends AbstractAction<T> {

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) {
            setDone(true);
            return;
        }

        Collectible item = getActor().getBackpack().peek();

        if (item != null) {
            getActor().getBackpack().remove(item);

            Scene scene = getActor().getScene();
            int keeperCenterX = getActor().getPosX() + getActor().getWidth() / 2;
            int keeperCenterY = getActor().getPosY() + getActor().getHeight() / 2;

            item.setPosition(keeperCenterX - item.getWidth() / 2, keeperCenterY - item.getHeight() / 2);

            Objects.requireNonNull(scene).addActor(item);
        }

        setDone(true);
    }
}
