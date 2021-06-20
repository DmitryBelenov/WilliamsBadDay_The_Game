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
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LevelObject {

    private final String objName;
    private final int goToLvLId;
    private final XYPos position;
    private final Animation objAnimation;
    private final Animation objActAnimation;
    private final Rectangle objCollShape;
    private final BasePlotTrigger objPlotTrigger;

    public LevelObject(String objName, int goToLvLId, XYPos position, Animation objAnimation, Animation objActAnimation, Rectangle objCollShape, BasePlotTrigger objPlotTrigger) {
        this.objName = objName;
        this.goToLvLId = goToLvLId;
        this.position = position;
        this.objAnimation = objAnimation;
        this.objActAnimation = objActAnimation;
        this.objCollShape = objCollShape;
        this.objPlotTrigger = objPlotTrigger;
    }

    public void renderObject(Graphics graphics, Rectangle chCoBl, GameContainer gc, Camera camera) {
        if (ActiveObj.isActive(objName)) {
            if (objActAnimation != null) {
                objActAnimation.draw(position.getX(), position.getY());
            }
        } else {
            if (objAnimation != null) {
                objAnimation.draw(position.getX(), position.getY());
            }
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
                    ActiveObj.activate(objName);
                    ScreenUtils.showStart();
                    TextViewer.setShowing();
                    ScreenUtils.setShowDuration(idx);
                    QuestMenu.set(objName.toUpperCase(Locale.ROOT) + ": " + cur.getText());
                }
            }
        }

        if (cur != null && ActiveObj.isActive(objName)) {
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
                    len > 3 ? Integer.parseInt(s[3]) : 1,
                    len > 4 ? s[4] : null,
                    len > 5 ? Integer.parseInt(s[5]) : 1));
        }
        return parsedList.size() == 0 ? null : parsedList;
    }

    private static boolean stepOK(final String[] stepArr) {
        return stepArr.length >= 2;
    }

    public BasePlotTrigger getObjPlotTrigger() {
        return objPlotTrigger;
    }

    public String getObjName() {
        return objName;
    }

    public static class ActiveObj {
        private static final ReentrantReadWriteLock rwL = new ReentrantReadWriteLock();
        private static final ReentrantReadWriteLock.ReadLock rL = rwL.readLock();
        private static final ReentrantReadWriteLock.WriteLock wL = rwL.writeLock();

        private static String activeObject;

        public static void activate(final String name) {
            wL.lock();
            try {
                activeObject = name;
            } finally {
                wL.unlock();
            }
        }

        public static void deactivate() {
            wL.lock();
            try {
                activeObject = null;
            } finally {
                wL.unlock();
            }
        }

        public static boolean isActive(final String name) {
            rL.lock();
            try {
                return activeObject != null && activeObject.equals(name);
            } finally {
                rL.unlock();
            }
        }
    }
}
