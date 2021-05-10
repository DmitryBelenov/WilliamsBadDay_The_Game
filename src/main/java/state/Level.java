package state;

import camera.Camera;
import character.Frames;
import character.XYPos;
import game.MainGame;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import character.Character;
import org.newdawn.slick.tiled.TiledMap;
import params.GameParams;

public abstract class Level extends BasicGameState {

    /**
     * Unique level ID
     * */
    private final int id;
    /**
     * TMX level file URI
     * */
    private final String tmxURI;
    /**
     * TMX map object
     * */
    private TiledMap map;
    /**
     * TMX front level file URI
     * */
    private final String frontURI;
    /**
     * TMX front map object
     * */
    private TiledMap frontMap;

    /**
     * Character object
     * */
    private Character player;
    /**
     * Player start position on level
     * */
    private final XYPos playerStartPos;

    /**
     * Main camera object
     * */
    private Camera camera;

    /**
     * Transparent player's collision block
     * */
    private Rectangle coBlk;

    /**
     * Level background music URI
     * */
    private final String backMusicURI;

    public Level(final int id, final String tmxURI, final String frontURI, final String backMusicURI, final XYPos playerStartPos) throws SlickException {
        this.id = id;
        this.tmxURI = tmxURI;
        this.frontURI = frontURI;
        this.backMusicURI = backMusicURI;
        this.playerStartPos = playerStartPos;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        player = new Character(playerStartPos.getX(), playerStartPos.getY(), new Animation(Frames.RIGHT_STAY, Character.DRT));
        map = new TiledMap(tmxURI);
        if (frontURI != null) {
            frontMap = new TiledMap(frontURI);
        }
        camera = new Camera(map);
        coBlk = new Rectangle(playerStartPos.getX(), playerStartPos.getY(), MainGame.PL_WIDTH, MainGame.PL_HEIGHT);
        if (backMusicURI != null) {
            final Music backMusic = new Music(backMusicURI);
            backMusic.setVolume(0.5f);
            backMusic.loop();
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        // order matters
        camera.translate(graphics, player);
        map.render(0,0);
        if (!GameParams.IS_DEBUG_MODE) {
            graphics.setColor(Color.transparent);
        }
        graphics.fill(coBlk);
        player.render();

        // front map objects rendering at front
        if (frontMap != null) {
            frontMap.render(0,0);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        player.update(gameContainer, map, coBlk);
    }
}
