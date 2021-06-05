package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import params.GameParams;

public class MainGame {
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new SetupGame("William's Bad Day"));
        app.setDisplayMode(GameParams.WIDTH, GameParams.HEIGHT, GameParams.IS_FULLSCREEN);
        if (GameParams.IS_TARGET_FR) {
            app.setTargetFrameRate(80);
        }
        app.setVSync(true);
        app.start();
    }
}
