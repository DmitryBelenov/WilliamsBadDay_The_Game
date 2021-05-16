package state.objects;

import character.XYPos;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import state.objects.logic.BasePlotTrigger;
import character.Character;

public class LevelObject {

    private final String objName;
    private final int goToLvLId;
    private final XYPos position;
    private final Animation objAnimation;
    private final Rectangle objCollShape;
    private final BasePlotTrigger objPlotTrigger;
    private final Boolean isRightReentrant;

    public LevelObject(String objName, int goToLvLId, XYPos position, Animation objAnimation, Rectangle objCollShape, BasePlotTrigger objPlotTrigger, Boolean isRightReentrant) {
        this.objName = objName;
        this.goToLvLId = goToLvLId;
        this.position = position;
        this.objAnimation = objAnimation;
        this.objCollShape = objCollShape;
        this.objPlotTrigger = objPlotTrigger;
        this.isRightReentrant = isRightReentrant;
    }

    public void renderObject(Graphics graphics) {
        if (objAnimation != null) {
            objAnimation.draw(position.getX(), position.getY());
        }
        if (objCollShape != null) {
            graphics.fill(objCollShape);
        }
    }

    public void updateObject(GameContainer gc, StateBasedGame sbg, Rectangle chCoBl, Character player) throws SlickException {
        if (objCollShape != null) {
            if (chCoBl.intersects(objCollShape)) {
                boolean pltChecked = objPlotTrigger == null || objPlotTrigger.check();
                if (pltChecked && goToLvLId != -1) {
                    sbg.enterState(goToLvLId, new FadeOutTransition(), new FadeInTransition());
                    int movePlTo = isRightReentrant ? (int) objCollShape.getX() - 100 : (int) (objCollShape.getX() + objCollShape.getWidth()) + 100;
                    // move player to reentrant position
                    player.setX(movePlTo);
                }
            }
        }
    }
}
