package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;


public class FirstSteps implements SceneListener {
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);
        var player = new Ripley();
        scene.addActor(player,0,0);


        AddCollectibles(scene,0);
        AddCollectibles(scene,50);
        AddCollectibles(scene,100);

        scene.getGame().pushActorContainer(player.getBackpack());


        var firstAidKit = new Energy();
        scene.addActor(firstAidKit,-100,50);
        var secondAidKit = new Energy();
        scene.addActor(secondAidKit,-100,100);

        var firstAmmo = new Ammo();
        scene.addActor(firstAmmo,-100,-50);
        var secondAmmo = new Ammo();
        scene.addActor(secondAmmo,-100,-100);

        var keeperController = new KeeperController(player);
        scene.getInput().registerListener(keeperController);
        var movementController = new MovableController(player);
        scene.getInput().registerListener(movementController);


//        new When<>(()->player.intersects(firstAmmo), new Use<>(firstAmmo))
//            .scheduleFor(player);
//        new When<>(()->player.intersects(secondAmmo), new Use<>(secondAmmo))
//            .scheduleFor(player);
    }

    private void AddCollectibles(@NotNull Scene scene,int startY) {
        scene.addActor(new Wrench(),0,startY);
        scene.addActor(new Hammer(),50,startY);
        scene.addActor(new Mjolnir(),100,startY);
        scene.addActor(new FireExtinguisher(),150,startY);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if (ripley != null) {
            Overlay overlay = scene.getGame().getOverlay();
            int windowHeight = scene.getGame().getWindowSetup().getHeight();
            int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

            overlay.drawText(" | Heath: " + ripley.getHealth() + "%", 100, yTextPos);
            overlay.drawText(" | Bullets: " + ripley.getBullets(), 250, yTextPos);
        }
    }
}
