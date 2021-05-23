package state.objects;

import camera.Camera;
import character.XYPos;
import game.screen.ScreenUtils;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import state.objects.logic.BasePlotTrigger;
import character.Character;
import state.objects.logic.SingleObjActionStep;

import java.util.ArrayList;
import java.util.List;

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

    public void renderObject(Graphics graphics, Rectangle chCoBl, GameContainer gc, Camera camera) {
        if (objAnimation != null) {
            objAnimation.draw(position.getX(), position.getY());
        }
        if (objCollShape != null) {
            graphics.fill(objCollShape);

            if (chCoBl.intersects(objCollShape)) {
                BasePlotTrigger bt = getObjPlotTrigger();
                if (bt != null) {
                    List<SingleObjActionStep> aSList = bt.getObjActionSteps();
                    int idx = bt.getCurStepIdx();
                    if (aSList != null) {
                        SingleObjActionStep cur = aSList.get(idx);
                        if (gc.getInput().isKeyDown(Input.KEY_E)) {
                            ScreenUtils.showTxt(cur.getText(), gc.getGraphics(), camera, idx);
                        }
                    }
                }
            }
        }
    }

    public void updateObject(GameContainer gc, StateBasedGame sbg, Rectangle chCoBl, Character player) throws SlickException {
        if (objCollShape != null) {
            if (chCoBl.intersects(objCollShape)) {
                boolean pltShifted = objPlotTrigger != null && objPlotTrigger.shift(gc);

                if (!pltShifted) {
                    return;
                }

                if (goToLvLId != -1 && gc.getInput().isKeyDown(Input.KEY_E)) {
                    sbg.enterState(goToLvLId, new FadeOutTransition(), new FadeInTransition());

                    int movePlTo = isRightReentrant ? (int) objCollShape.getX() - 50 : (int) (objCollShape.getX() + objCollShape.getWidth()) + 50;
                    // move player to reentrant position
                    player.setX(movePlTo);
                }
            }
        }
    }

    public static synchronized List<SingleObjActionStep> parseActionSteps(final String actLine) {
        final String[] acts = actLine.split(",");
        List<SingleObjActionStep> parsedList = new ArrayList<>();
        for (String actionStep : acts) {
            final String[] s = actionStep.split("-");
            if (!stepOK(s)) {
                continue;
            }
            int len = s.length;
            parsedList.add(new SingleObjActionStep(
                    s[0],
                    s[1],
                    len > 2 ? s[2] : null,
                    len > 3 ? s[3] : null));
        }
        return parsedList.size() == 0 ? null : parsedList;
    }

    private static boolean stepOK(final String[] stepArr) {
        return stepArr.length >= 2;
    }

    public BasePlotTrigger getObjPlotTrigger() {
        return objPlotTrigger;
    }
}
