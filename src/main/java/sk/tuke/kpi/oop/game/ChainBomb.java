package sk.tuke.kpi.oop.game;

import java.awt.geom.Ellipse2D;

public class ChainBomb extends TimeBomb {
    public ChainBomb(float countdownTime) {
        super(countdownTime);
    }

    @Override
    public void activate() {
        super.activate();
        activateNearbyBombs();
    }

    @Override
    public void detonate() {
        super.detonate();
        activateNearbyBombs();
    }

    private void activateNearbyBombs() {
        float currentX = this.getPosX();
        float currentY = this.getPosY();

        Ellipse2D.Float activationArea = new Ellipse2D.Float(currentX - 50f, currentY - 50f,
            50f * 2, 50f * 2);

        if (getScene() == null) return;
        for (var actor : getScene().getActors()) {
            if (actor == this|| !(actor instanceof TimeBomb)) continue;
            var bomb = (TimeBomb) actor;
            if (!bomb.isActivated() && activationArea.intersects(bomb.getBoundingBox()))
                bomb.activate();
        }
    }
}
