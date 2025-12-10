package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;

import java.util.Objects;

public class Ammo extends AbstractActor implements Usable<Armed>{

    public Ammo(){
        setAnimation(new Animation("sprites/ammo.png"));
    }

    @Override
    public void useWith(Armed actor) {
        if (actor==null) return;
        if (actor.getFirearm().getAmmo()==60) return;

        actor.getFirearm().reload(30);

        Objects.requireNonNull(getScene()).removeActor(this);
    }

    @Override
    public Class<Armed> getUsingActorClass() {
      return   Armed.class;
    }
}
