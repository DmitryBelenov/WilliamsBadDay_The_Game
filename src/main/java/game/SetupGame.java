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

        this.addState(new IntState(2, null,"Strange ground hole", "Press ENTER", Input.KEY_ENTER, 3));
        this.addState(new BaseState(3, "tiles/level/LEVEL_2.tmx", null, "sounds/level/space-ambience.wav", new XYPos(70, 440)));

        this.addState(new IntState(4, null,"The books..", "Press ENTER", Input.KEY_ENTER, 5));
        this.addState(new BaseState(5, "tiles/level/LEVEL_3.tmx", null, "sounds/level/space-ambience.wav", new XYPos(250, 512)));
    }

    private static void init() throws SlickException {
        ItemHolder.init();
        Inventory.init();
        Plot.init(new String[]{"exit"});

        if (Frames.RIGHT_WALK == null || Frames.LEFT_WALK == null) {
            throw new SlickException("Character animation not initialized");
        }
    }
}
