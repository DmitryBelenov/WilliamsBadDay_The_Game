package state;

import character.XYPos;
import org.newdawn.slick.SlickException;

public class BaseState extends Level {

    public BaseState(int id, String tmxURI, String frontURI, String backMusicURI, XYPos playerStartPos) throws SlickException {
        super(id, tmxURI, frontURI, backMusicURI, playerStartPos);
    }
}
