package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);

        // missionImpossible
       // var missionImpossible = new MissionImpossible();
       //Scene scene = new World("world", "maps/mission-impossible.tmx",new MissionImpossible.Factory());
        //    scene.addListener(missionImpossible);

        //Escape room
       Scene scene = new World("world", "maps/escape-room.tmx",new EscapeRoom.Factory());
       scene.addListener(new EscapeRoom());

        game.addScene(scene);
        game.start();
    }
}


//Door closing issue links MissionImpossible Door

//Use scheduling issue links Use KeeperController
