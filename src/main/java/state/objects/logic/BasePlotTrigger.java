package state.objects.logic;

import java.util.List;

public class BasePlotTrigger {

    private String plotStartCode;
    private List<SingleObjActionStep> objActionSteps;
    private SingleObjActionStep curStep;
    private String noActTxt; // default plot text

    public BasePlotTrigger(String plotStartCode) {
        this(plotStartCode, null, null);
    }

    public BasePlotTrigger(String plotStartCode, List<SingleObjActionStep> objActionSteps, SingleObjActionStep curStep) {
        this.plotStartCode = plotStartCode;
        this.objActionSteps = objActionSteps;
        this.curStep = curStep;
    }

    public boolean check() {
        if (!plotStartCode.equals("start")) {
            // show default plot text (no plot move)
            return false;
        }
        return true;
    }

    private void nextStep() {
    }

    private void move() {
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
        if (this.objActionSteps != null && this.objActionSteps.size() > 0) {
            setCurStep(this.objActionSteps.get(0)); // set current step as first
        }
    }

    public SingleObjActionStep getCurStep() {
        return curStep;
    }

    public void setCurStep(SingleObjActionStep curStep) {
        this.curStep = curStep;
    }

    public void setNoActTxt(String noActTxt) {
        this.noActTxt = noActTxt;
    }
}
