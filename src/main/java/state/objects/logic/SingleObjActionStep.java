package state.objects.logic;

public class SingleObjActionStep {
    private String stepName;
    private String text;
    private String takeItemCode;
    private String giveItemCode;

    public SingleObjActionStep(String stepName, String text, String takeItemCode, String giveItemCode) {
        this.stepName = stepName;
        this.text = text;
        this.takeItemCode = takeItemCode;
        this.giveItemCode = giveItemCode;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTakeItemCode() {
        return takeItemCode;
    }

    public void setTakeItemCode(String takeItemCode) {
        this.takeItemCode = takeItemCode;
    }

    public String getGiveItemCode() {
        return giveItemCode;
    }

    public void setGiveItemCode(String giveItemCode) {
        this.giveItemCode = giveItemCode;
    }
}
