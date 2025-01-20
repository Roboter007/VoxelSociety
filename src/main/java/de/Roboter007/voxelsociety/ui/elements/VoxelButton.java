package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class VoxelButton extends VoxelElement {

    protected final String text;
    protected final UiStyle uiStyle;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public VoxelButton(String text, UiStyle uiStyle, int x, int y, int width, int height) {
        super();
        this.text = text;
        this.uiStyle = uiStyle;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public VoxelButton(UiStyle uiStyle, int x, int y, int width, int height) {
        this(null, uiStyle, x, y, width, height);
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        if(!this.isMouseOverElement()) {
            graphics2D.setColor(Color.BLACK);
        } else {
            graphics2D.setColor(Color.DARK_GRAY);
        }
        graphics2D.fillRect(x, y, width, height);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRect(x, y, width, height);

        if(this.text != null) {
            int x = (this.x + ((this.width / 2) - ((int) this.uiStyle.getFont().getStringBounds(this.text, graphics2D.getFontRenderContext()).getWidth() / 2))) - 2;
            int y = (this.y + ((this.height / 2) + ((int) this.uiStyle.getFont().getStringBounds(this.text, graphics2D.getFontRenderContext()).getHeight() / 2))) - 2;

            uiUtilities.drawString(this.text, uiStyle, false, x, y);
        }
    }


    public String getText() {
        return text;
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    public void updateBoundingBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }
}
