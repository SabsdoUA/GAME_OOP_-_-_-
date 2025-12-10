package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    private Player player;

    public Helicopter() {
        setAnimation(new Animation("sprites/heli.png",64, 64, 0.5f,
            Animation.PlayMode.LOOP_PINGPONG));
    }

    public void searchAndDestroy() {
        if (getScene() == null) return;
        player = getScene().getFirstActorByType(Player.class);

        if (player == null) return;
        getScene().scheduleAction(new Invoke<>(this::chasePlayer));
    }

    private void chasePlayer() {
        if (player == null) return;

        if (intersects(player))
            player.setEnergy(player.getEnergy() - 1);
        else
            moveToPlayer();
    }

    private void moveToPlayer() {
        if (player == null) return;
        int deltaX = player.getPosX() - getPosX();
        int deltaY = player.getPosY() - getPosY();
        double angle = Math.atan2(deltaY, deltaX);
        int speed = 2;

        this.setPosition((int) (getPosX() + Math.cos(angle) * speed),
            (int) (getPosY() + Math.sin(angle) * speed));
    }
}
