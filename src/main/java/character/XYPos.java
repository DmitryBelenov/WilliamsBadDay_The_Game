package character;

public class XYPos {
    private int x, y;

    public XYPos() {
        this.x = 0;
        this.y = 0;
    }

    public XYPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isZeroCoordinates() {
        return x == 0 && y == 0;
    }
}
