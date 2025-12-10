package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper,Alive, Armed {
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    private int bullets = 450;
    private Backpack backpack;
    private Health health;
    private Firearm gun;

    public Ripley() {
        super("Ellen");
        setAnimation(new Animation("sprites/player.png", 32, 32, 0.1f,
            Animation.PlayMode.LOOP_PINGPONG));
        getAnimation().pause();

        backpack = new Backpack("Ripley's backpack", 10);
        health = new Health(100);
        gun = new Gun(30,60);

        health.onFatigued(() -> {
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);

            getScene().cancelActions(this);
            die();
        });
    }

    public void showRipleyState() {
        Overlay overlay = Objects.requireNonNull(getScene()).getGame().getOverlay();
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

        overlay.drawText(" | Heath: " + health.getValue() + "%", 100, yTextPos);
        overlay.drawText(" | Bullets: " + bullets, 250, yTextPos);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    private void die() {
        setAnimation(new Animation("sprites/player_die.png",32,32,0.1f,
            Animation.PlayMode.ONCE));
        Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int amount) {
        bullets = amount;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    @Override
    public void stoppedMoving() {
        getAnimation().pause();
    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }


    @Override
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        gun = weapon;
    }
}

