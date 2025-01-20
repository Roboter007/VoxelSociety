package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiUtilities;

import java.awt.*;

//ToDo: Fix some Errors
public class VoxelProgressbar extends VoxelElement {

    private final Color color;
    private final Color outline;
    private int progress = 0;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public VoxelProgressbar(Color color, Color outline, int x, int y, int width, int height) {
        this.color = color;
        this.outline = outline;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        graphics2D.setColor(color);
        graphics2D.fill(this.getInnerBB());

        graphics2D.setColor(outline);
        graphics2D.draw(this.getBoundingBox());
    }

    @Override
    public void updateBoundingBox(int x, int y, int width, int height) {
        //Don't do anything...
    }

    @Override
    public void move(int x, int y) {
        //Don't do anything...
    }

    public VoxelProgressbar progress(int progress) {
        this.progress = progress;
        return this;
    }

    private int calculatePercentage(int inValue, int percent) {
        float percentFloat = (float) percent / 100;
        float outValueFloat = (inValue * percentFloat);
        return Math.round(outValueFloat);
    }

    public Rectangle getInnerBB() {
        return new Rectangle(x, y, calculatePercentage(width, progress), height);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }
}
