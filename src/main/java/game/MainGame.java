package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class MainGame {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static final int PL_WIDTH = 46;
    public static final int PL_HEIGHT = 165;

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new SetupGame("William's Bad Day"));
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setVSync(true);
        app.start();
    }
}
