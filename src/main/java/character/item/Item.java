package character.item;

import org.newdawn.slick.Animation;
import params.GameParams;

public class Item {
    public final String code;
    private final Animation animation;
    private final int animDur;

    public Item(String code, Animation animation) {
      this(code, animation, GameParams.INV_ITEM_DEF_DUR);
    }

    public Item(String code, Animation animation, int animDur) {
        this.code = code;
        this.animation = animation;
        this.animDur = animDur;
    }

    public String getCode() {
        return code;
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getAnimDur() {
        return animDur;
    }

    @Override
    public int hashCode() {
        int res = 17;
        res = 37 * res + code.hashCode();
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Item))
            return false;
        Item that = (Item) o;
        return this.code.equals(that.code);
    }
}
