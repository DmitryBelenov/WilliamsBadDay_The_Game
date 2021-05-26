package state.objects;

import camera.Camera;
import character.Character;
import character.XYPos;
import game.screen.ScreenUtils;
import game.screen.TextViewer;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import plot.QuestMenu;
import state.objects.logic.BasePlotTrigger;
import state.objects.logic.SingleObjActionStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        List<SingleObjActionStep> aSList;
        SingleObjActionStep cur = null;

        int idx = -1;
        BasePlotTrigger bt = getObjPlotTrigger();
        if (bt != null) {
            aSList = bt.getObjActionSteps();
            idx = bt.getCurStepIdx();
            if (aSList != null) {
                cur = aSList.get(idx);
            }
        }

        if (objCollShape != null) {
            graphics.fill(objCollShape);
            if (chCoBl.intersects(objCollShape) && cur != null) {
                if (gc.getInput().isKeyDown(Input.KEY_E) && !TextViewer.isShowing()) {
                    ScreenUtils.showStart();
                    TextViewer.setShowing();
                    ScreenUtils.setShowDuration(idx);
                    QuestMenu.set(objName.toUpperCase(Locale.ROOT) + ": " + cur.getText());
                }
            }
        }

        if (cur != null) {
            if (TextViewer.isShowing()) {
                ScreenUtils.showTxt(cur.getText(), gc.getGraphics(), camera);
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
                    len > 3 ? Integer.parseInt(s[3]) : 0,
                    len > 4 ? s[4] : null,
                    len > 5 ? Integer.parseInt(s[5]) : 0));
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
