package state.objects;

import character.XYPos;
import org.newdawn.slick.geom.Rectangle;

public class ObjectCollisionShape {

    private Rectangle shape;
    private XYPos position;

    public Rectangle getShape() {
        return shape;
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public XYPos getPosition() {
        return position;
    }

    public void setPosition(XYPos position) {
        this.position = position;
    }
}
