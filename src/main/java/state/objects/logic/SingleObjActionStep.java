package state.objects.logic;

public class SingleObjActionStep {
    private final String stepName;
    private final String text;
    private final String takeItemCode;
    private final int takeItemCount;
    private final String giveItemCode;
    private final int giveItemCount;

    public SingleObjActionStep(String stepName, String text, String takeItemCode, int takeItemCount, String giveItemCode, int giveItemCount) {
        this.stepName = stepName;
        this.text = text;
        this.takeItemCode = takeItemCode;
        this.takeItemCount = takeItemCount;
        this.giveItemCode = giveItemCode;
        this.giveItemCount = giveItemCount;
    }

    public String getStepName() {
        return stepName;
    }

    public String getText() {
        return text;
    }

    public String getTakeItemCode() {
        return takeItemCode;
    }

    public int getTakeItemCount() {
        return takeItemCount;
    }

    public String getGiveItemCode() {
        return giveItemCode;
    }

    public int getGiveItemCount() {
        return giveItemCount;
    }
}
