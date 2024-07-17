package de.Roboter007.voxelsociety.world.pos;

public abstract class Position {

    public abstract Number getX();
    public abstract Number getY();

    public abstract <X extends Number> void setX(X x);
    public abstract <Y extends Number> void setY(Y y);
    public abstract <Y extends Number, X extends Number> void setPos(X x, Y y);
    public abstract <XY extends Number> void setBoth(XY xy);
    public abstract <X extends Number, Y extends Number> void minus(X x, Y y);
    public abstract <X extends Number, Y extends Number> void add(X x, Y y);
}
