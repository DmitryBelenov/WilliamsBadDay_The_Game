package state.objects.logic;

import character.item.Item;
import plot.ItemHolder;

public class SingleObjActionStep {
    private final String stepName;
    private final String text;
    private Item takeItem;
    private final int takeItemCount;
    private Item giveItem;
    private final int giveItemCount;

    public SingleObjActionStep(String stepName, String text, String takeItemCode, int takeItemCount, String giveItemCode, int giveItemCount) {
        this.stepName = stepName;
        this.text = text;
        if (takeItemCode != null) {
            this.takeItem = ItemHolder.get(takeItemCode);
        }
        this.takeItemCount = takeItemCount;
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
}
