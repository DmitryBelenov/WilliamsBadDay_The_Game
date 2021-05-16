package game;

import character.Frames;
import character.XYPos;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import state.BaseState;
import state.IntState;

public class SetupGame extends StateBasedGame {
    public SetupGame(String name) throws SlickException {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        init();

        this.addState(new IntState(0, "William's Bad Day", "Press ENTER", Input.KEY_ENTER, 1));
        this.addState(new BaseState(1, "tiles/level/LEVEL_1.tmx", "tiles/level/LEVEL_1_FRONT.tmx", "sounds/level/Countryside.wav", new XYPos(115, 390)));

        this.addState(new IntState(2, "William's Bad Day NEXT Level", "Press ENTER", Input.KEY_ENTER, 1));
    }

    private static void init() throws SlickException {
        if (Frames.RIGHT_WALK == null || Frames.LEFT_WALK == null) {
            throw new SlickException("Character animation not initialized");
        }
    }
}
