package state.objects.logic;

import character.Inventory;
import character.item.Item;
import game.screen.ScreenUtils;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import plot.Plot;

import java.util.List;

public class BasePlotTrigger {

    private String plotStartCode;
    private List<SingleObjActionStep> objActionSteps;
    private int curStepIdx;
    private boolean generalPlotShifted;

    public BasePlotTrigger(String plotStartCode) {
        this(plotStartCode, null);
    }

    public BasePlotTrigger(String plotStartCode, List<SingleObjActionStep> objActionSteps) {
        this.plotStartCode = plotStartCode;
        setObjActionSteps(objActionSteps);
    }

    public boolean shift(GameContainer gc) {
        if (!plotStartCode.equals(Plot.curCode())) {
            return false;
        }

        boolean isNextStep = false;
        if (gc.getInput().isKeyPressed(Input.KEY_E)) {
            isNextStep = nextStep();
        }

        return isNextStep;
    }

    private boolean nextStep() {
        if (objActionSteps == null || generalPlotShifted) {
            return true;
        }

        if (ScreenUtils.isShown(curStepIdx)) {
            boolean stepNext = true;

            SingleObjActionStep soa = getObjActionSteps().get(curStepIdx);
            final Item take = soa.getTakeItem();
            if (take != null) {
                if (Inventory.contains(take)) {
                    Inventory.drop(take);
                } else {
                    stepNext = false;
                }
            }

            if (stepNext) {
                curStepIdx++;
                if (curStepIdx == objActionSteps.size() - 1) {
                    movePlot();
                    generalPlotShifted = true;
                }
            }
        }

        return generalPlotShifted;
    }

    private void movePlot() {
        Plot.shiftFw();
    }

    public String getPlotStartCode() {
        return plotStartCode;
    }

    public void setPlotStartCode(String plotStartCode) {
        this.plotStartCode = plotStartCode;
    }

    public List<SingleObjActionStep> getObjActionSteps() {
        return objActionSteps;
    }

    public void setObjActionSteps(List<SingleObjActionStep> objActionSteps) {
        this.objActionSteps = objActionSteps;
    }

    public int getCurStepIdx() {
        return curStepIdx;
    }

    private void setCurStepIdx(int stepIdx) {
        this.curStepIdx = stepIdx;
    }
}
