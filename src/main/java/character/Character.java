package character;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;
import state.Layer;

public class Character {
    private int x, y;
    private boolean right, left;

    private Animation animation;
    public static final int DRT = 65;
    private static final int STEP = 8; // px;

    public Sound pl_steps;
    private static final float STEP_VOL = 0.3f;

    public Character(int x, int y, Animation animation) throws SlickException {
        this.x = x;
        this.y = y;
        this.animation = animation;

        try {
            pl_steps = new Sound("sounds/player/Steps.ogg");
        } catch (SlickException se) {
            // dummy
        }
    }

    public void update(GameContainer gc, TiledMap level, Rectangle coBlk) {
            Vector2f move = new Vector2f(0, 0);
            Input input = gc.getInput();
            // when left and right keys pressed
            if (input.isKeyDown(Input.KEY_LEFT) && input.isKeyDown(Input.KEY_RIGHT)) {
                animation = new Animation(Frames.LEFT_STAY, DRT);
                if (left) left = false;
                if (right) right = false;
                if (pl_steps.playing()) {
                    pl_steps.stop();
                }
                return;
            }

            // play animation
            if (input.isKeyDown(Input.KEY_LEFT)) {
                if (!left) {
                    animation = new Animation(Frames.LEFT_WALK, DRT);
                    right = false;
                    left = true;
                }
            } else if (input.isKeyDown(Input.KEY_RIGHT)) {
                if (!right) {
                    animation = new Animation(Frames.RIGHT_WALK, DRT);
                    right = true;
                    left = false;
                }
            }

            // init MOVE layer (only zero points to go)
            int moveIdx = level.getLayerIndex(Layer.MOVE.name());
            level.getTileId(0, 0, moveIdx);

            if (input.isKeyDown(Input.KEY_RIGHT)) {
                if (level.getTileId((x / STEP) + 1, y / STEP, moveIdx) == 0) {
                    move.x += STEP;

                    if (pl_steps != null && !pl_steps.playing()) {
                        pl_steps.play(1.4f, STEP_VOL);
                    }
                } else {
                    stay(Frames.RIGHT_STAY);
                }
            } else if (input.isKeyDown(Input.KEY_LEFT)) {
                if (level.getTileId((x / STEP) - 1, y / STEP, moveIdx) == 0) {
                    move.x -= STEP;

                    if (pl_steps != null && !pl_steps.playing()) {
                        pl_steps.play(1.4f, STEP_VOL);
                    }
                } else {
                    stay(Frames.LEFT_STAY);
                }
            } else {
                if (left) {
                    animation = new Animation(Frames.LEFT_STAY, DRT);
                    left = false;
                } else if (right) {
                    animation = new Animation(Frames.RIGHT_STAY, DRT);
                    right = false;
                }
                if (pl_steps.playing()) {
                    pl_steps.stop();
                }
            }

            if (x + move.x > (level.getTileWidth()) && x + move.x < ((level.getWidth() * level.getTileWidth()) - (4 * (level.getTileWidth())))) {
                x += move.x / 4;
            } else {
                stay(Frames.LEFT_STAY);
            }

            if (y + move.y > (level.getTileHeight()) && y + move.y < ((level.getHeight() * level.getTileHeight()) - (4 * (level.getTileHeight())))) {
                y += move.y / 4;
            } else {
                stay(Frames.LEFT_STAY);
            }

            // set collision block position
            coBlk.setX(x);
            coBlk.setY(y);
    }

    private void stay(Image[] stayFrames) {
        animation = new Animation(stayFrames, DRT);
        if (pl_steps.playing()) {
            pl_steps.stop();
        }
    }

    public void render() {
        animation.draw(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
