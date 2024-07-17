package de.Roboter007.voxelsociety.world.pos;

public class IntPosition extends Position {

    private int x;
    private int y;

    public IntPosition() {
        this(0, 0);
    }

    public IntPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public <X extends Number> void setX(X x) {
        this.x = (int) x;
    }

    @Override
    public <Y extends Number> void setY(Y y) {
        this.y = (int) y;
    }

    @Override
    public <Y extends Number, X extends Number> void setPos(X x, Y y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    @Override
    public <XY extends Number> void setBoth(XY xy) {
        int i = (int) xy;
        this.x = i;
        this.y = i;
    }

    @Override
    public <X extends Number, Y extends Number> void minus(X x, Y y) {
        this.x -= (int) x;
        this.y -= (int) y;
    }

    @Override
    public <X extends Number, Y extends Number> void add(X x, Y y) {
        this.x += (int) x;
        this.y += (int) y;
    }


}
