package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiUtilities;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

public abstract class ScrollableVoxelElement extends VoxelElement {

    protected final Direction scrollDirection;
    protected final Rectangle completeArea;
    protected final Rectangle renderedArea;
    protected final int scrollSpeed;

    public ScrollableVoxelElement(Direction scrollDirection, Rectangle completeArea, Rectangle renderedArea, int scrollSpeed) {
        super();
        this.scrollDirection = scrollDirection;
        this.completeArea = completeArea;
        this.renderedArea = renderedArea;
        this.scrollSpeed = scrollSpeed;

    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        uiUtilities.drawShape(Color.RED, completeArea);
        uiUtilities.drawShape(Color.BLUE, renderedArea);
        this.drawRenderedArea(renderedArea, uiUtilities);
    }

    public abstract void drawRenderedArea(Rectangle renderedArea, UiUtilities uiUtilities);

    public void moveRenderedArea(int wheelRotation) {
        if(this.getScrollDirection() == Direction.X) {
            this.renderedArea.setLocation(this.renderedArea.getLocation().x + wheelRotation * scrollSpeed, this.renderedArea.getLocation().y);
        } else {
            this.renderedArea.setLocation(this.renderedArea.getLocation().x, this.renderedArea.getLocation().y + wheelRotation * scrollSpeed);
        }
    }

    public int getHeight() {
        return renderedArea.y;
    }

    public void setHeight(int height) {
        this.renderedArea.setLocation(this.getRenderedArea().x, height);
    }

    @Override
    public void onMouseWheelMove(MouseWheelEvent e) {
        moveRenderedArea(e.getUnitsToScroll());
        this.onScroll();
        super.customMouseWheelMove(e);
    }

    public int getScrollSpeed() {
        return scrollSpeed;
    }

    public abstract void onScroll();

    public Rectangle getRenderedArea() {
        return renderedArea;
    }

    @Override
    public Rectangle getBoundingBox() {
        return completeArea;
    }

    public Direction getScrollDirection() {
        return scrollDirection;
    }

    @Override
    public void updateBoundingBox(int x, int y, int width, int height) {
        this.completeArea.setBounds(x, y, width, height);
        this.renderedArea.setSize(width, height);
    }

    @Override
    public void move(int x, int y) {
        this.completeArea.setLocation(x, y);
    }

    public enum Direction {
        X,
        Y;
    }
}
