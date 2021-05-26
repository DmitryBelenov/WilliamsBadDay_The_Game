package state;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import params.GameParams;

public class IntState extends BasicGameState {

    private final int id;

    private final String cent;
    private final String rgt_dwn;
    private final String lft_dwn;
    private final String rgt_up;
    private final String lft_up;
    private int key;
    private int go_to_stage;

    public IntState(final int id, String cent, int key, int go_to_stage) throws SlickException {
        this(id, cent, null, null, null, null, key, go_to_stage);
    }

    public IntState(final int id, String cent, String rgt_dwn, int key, int go_to_stage) throws SlickException {
        this(id, cent, rgt_dwn, null, null, null, key, go_to_stage);
    }

    public IntState(final int id, String cent, String rgt_dwn, String lft_dwn, String rgt_up, String lft_up, int key, int go_to_stage) throws SlickException {
        this(id, cent, rgt_dwn, lft_dwn, rgt_up, lft_up);
        this.key = key;
        this.go_to_stage = go_to_stage;

        if (key == Input.KEY_ENTER && go_to_stage < 0) throw new SlickException("No stage ID go to (by ENTER)");
    }

    public IntState(final int id, String cent, String rgt_dwn, String lft_dwn, String rgt_up, String lft_up) {
        this.id = id;
        this.cent = cent;
        this.rgt_dwn = rgt_dwn;
        this.lft_dwn = lft_dwn;
        this.rgt_up = rgt_up;
        this.lft_up = lft_up;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        if (cent != null) {
            graphics.drawString(cent, (float) GameParams.WIDTH / 3, (float) GameParams.HEIGHT / 3);
        }
        if (rgt_dwn != null) {
            graphics.drawString(rgt_dwn, (float) GameParams.WIDTH - ((float) GameParams.WIDTH / 100 * 10), (float) GameParams.HEIGHT - ((float) GameParams.HEIGHT / 100 * 5));
        }
        if (lft_dwn != null) {

        }
        if (rgt_up != null) {

        }
        if (lft_up != null) {

        }
        graphics.setColor(Color.transparent);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (key >= -1) {
            if (key == Input.KEY_ENTER) {
                if (gameContainer.getInput().isKeyPressed(key)) {
                    stateBasedGame.enterState(go_to_stage, new FadeOutTransition(), new FadeInTransition());
                }
            }
        }
    }
}
