package de.Roboter007.voxelsociety.world.pos;

public class DoublePosition extends Position {
    private double x, y;
    public DoublePosition() {
        this(0, 0);
    }

    public DoublePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public <X extends Number> void setX(X x) {
        this.x = (double) x;
    }

    @Override
    public <Y extends Number> void setY(Y y) {
        this.y = (double) y;
    }

    @Override
    public <Y extends Number, X extends Number> void setPos(X x, Y y) {
        this.x = (double) x;
        this.y = (double) y;
    }

    @Override
    public <XY extends Number> void setBoth(XY xy) {
        this.x = (double) xy;
        this.y = (double) xy;
    }

    @Override
    public <X extends Number, Y extends Number> void minus(X x, Y y) {
        this.x -= (double) x;
        this.y -= (double) y;
    }

    @Override
    public <X extends Number, Y extends Number> void add(X x, Y y) {
        this.x += (double) x;
        this.y += (double) y;
    }

}
