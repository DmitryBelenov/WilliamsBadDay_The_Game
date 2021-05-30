package state.objects.logic;

import character.item.Item;
import plot.ItemHolder;

public class SingleObjActionStep {
    private final String stepName;
    private final String text;
    private Item takeItem;
    private int takeItemCount;
    private Item giveItem;
    private int giveItemCount;

    public SingleObjActionStep(String stepName, String text, String takeItemCode, int takeItemCount, String giveItemCode, int giveItemCount) {
        this.stepName = stepName;
        this.text = text;
        if (takeItemCode != null && !takeItemCode.equals("NA")) {
            this.takeItem = ItemHolder.get(takeItemCode);
        }
        this.takeItemCount = takeItemCode != null && takeItemCode.equals("NA") ? 0 : takeItemCount;
        if (giveItemCode != null) {
            this.giveItem = ItemHolder.get(giveItemCode);
        }
        this.giveItemCount = giveItemCount;
    }

    public String getStepName() {
        return stepName;
    }

    public String getText() {
        return text;
    }

    public Item getTakeItem() {
        return takeItem;
    }

    public int getTakeItemCount() {
        return takeItemCount;
    }

    public Item getGiveItem() {
        return giveItem;
    }

    public int getGiveItemCount() {
        return giveItemCount;
    }

    public void setTakeItemCount(int takeItemCount) {
        this.takeItemCount = takeItemCount;
    }

    public void setGiveItemCount(int giveItemCount) {
        this.giveItemCount = giveItemCount;
    }

    public void removeGiveItem() {
        this.giveItem = null;
        this.giveItemCount = 0;
    }
}
