package character.item;

import org.newdawn.slick.Animation;

public class Item {
    public final String code;
    private final Animation animation;

    public Item(String code, Animation animation) {
        this.code = code;
        this.animation = animation;
    }

    public String getCode() {
        return code;
    }

    public Animation getAnimation() {
        return animation;
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
