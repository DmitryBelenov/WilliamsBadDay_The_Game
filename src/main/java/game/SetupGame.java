package game;

import character.Frames;
import character.Inventory;
import character.XYPos;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import plot.ItemHolder;
import plot.Plot;
import state.BaseState;
import state.IntState;

public class SetupGame extends StateBasedGame {
    public SetupGame(String name) throws SlickException {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        init();

        this.addState(new IntState(0, new Image("img/logo/logo.png"), null, "Press ENTER", Input.KEY_ENTER, 1));
        this.addState(new BaseState(1, "tiles/level/LEVEL_1.tmx", "tiles/level/LEVEL_1_FRONT.tmx", "sounds/level/Countryside.wav", new XYPos(115, 390))); //space-ambience.wav

        this.addState(new IntState(2, null,"William's Bad Day NEXT Level", "Press ENTER", Input.KEY_ENTER, 1));
    }

    private static void init() throws SlickException {
        ItemHolder.init();
        Inventory.init();
        Plot.init(new String[]{"dog", "exit"});

        if (Frames.RIGHT_WALK == null || Frames.LEFT_WALK == null) {
            throw new SlickException("Character animation not initialized");
        }
    }
}
