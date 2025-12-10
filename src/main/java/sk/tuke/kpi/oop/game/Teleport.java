package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private Teleport destinationTeleport;
    private final Animation animation;
    private boolean active = false;

    public Teleport(Teleport destinationTeleport) {
        this.destinationTeleport = destinationTeleport;
        animation = new Animation("sprites/lift.png", 48, 48);

        setAnimation(animation);
    }

    public Teleport getDestination() {
        return destinationTeleport;
    }

    public void setDestination(Teleport destinationTeleport) {
        if (destinationTeleport != null && destinationTeleport != this) {
            this.destinationTeleport = destinationTeleport;
        }
    }

    public void teleportPlayer(Player player) {
        if (destinationTeleport == null || !active) return;

        int destinationX = destinationTeleport.getPosX() + (destinationTeleport.getWidth() / 2);
        int destinationY = destinationTeleport.getPosY() + (destinationTeleport.getHeight() / 2);
        player.setPosition(destinationX, destinationY);

    }

    public void checkPlayerCollision(Player player) {
        float playerCenterX = player.getPosX() + (float) player.getWidth() / 2;
        float playerCenterY = player.getPosY() + (float) player.getHeight() / 2;

        if (isPlayerInTeleportArea(playerCenterX, playerCenterY)) {
            teleportPlayer(player);
            active = false;
        }
    }

    private boolean isPlayerInTeleportArea(float playerX, float playerY) {
        return playerX >= getPosX() && playerX <= (getPosX() + animation.getWidth()) &&
            playerY >= getPosY() && playerY <= (getPosY() + animation.getHeight());
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
