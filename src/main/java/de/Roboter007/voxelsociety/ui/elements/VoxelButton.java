package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.ColorPalette;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class VoxelButton extends VoxelElement {

    private final UiStyle uiStyle;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Runnable action;

    public VoxelButton(UiStyle uiStyle, int x, int y, int width, int height, Runnable action) {
        this.uiStyle = uiStyle;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.action = action;
    }


    @Override
    public void draw(UiUtilities uiUtilities) {
        uiUtilities.drawRectangle(uiStyle.getColorPalette().outlineColor(), uiStyle.getColorPalette().backgroundColor(), this.x, this.y, this.width, this.height);
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    public void onRightClick() {
        this.action.run();
    }

    @Override
    public void onLeftClick() {
        this.action.run();
    }
}
