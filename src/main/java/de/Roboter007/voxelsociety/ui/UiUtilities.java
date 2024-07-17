package de.Roboter007.voxelsociety.ui;

import de.Roboter007.voxelsociety.utils.VoxelPanel;

import java.awt.*;

public class UiUtilities {

    private final VoxelPanel voxelPanel;
    private final Graphics2D graphics2D;

    public UiUtilities(VoxelPanel voxelPanel, Graphics2D graphics2D) {
        this.voxelPanel = voxelPanel;
        this.graphics2D = graphics2D;
        //this.graphics2D = (Graphics2D) this.voxelPanel.getGraphics();
        /*if(graphics2D != null) {
            System.out.println("nice");
        } else {
            System.out.println("???????");
        } */
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public VoxelPanel getVoxelPanel() {
        return voxelPanel;
    }

    public void drawString(String text, UiStyle uiStyle, boolean withBackgroundColor, float x, float y) {
        graphics2D.setColor(uiStyle.getColorPalette().textColor());
        graphics2D.setFont(uiStyle.getFont());
        if(withBackgroundColor) {
            graphics2D.setBackground(uiStyle.getColorPalette().backgroundColor());
        }
        graphics2D.drawString(text, x, y);
        graphics2D.dispose();
    }

    public void drawString(String text, Font font, Color mainColor, Color backgroundColor, float x, float y) {
        graphics2D.setColor(mainColor);
        graphics2D.setFont(font);
        graphics2D.setBackground(backgroundColor);
        graphics2D.drawString(text, x, y);
        graphics2D.dispose();
    }

    public void drawString(String text, Font font, Color color, float x, float y) {
        graphics2D.setColor(color);
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
        graphics2D.dispose();
    }

    public void drawRectangle(Color outlineColor, Color backgroundColor, int x, int y, int width, int height) {
        graphics2D.setColor(outlineColor);
        graphics2D.setBackground(backgroundColor);
        graphics2D.drawRect(x, y, width, height);
        graphics2D.dispose();
    }

    public void drawShape(Color color, Shape shape) {
        graphics2D.setColor(color);
        graphics2D.draw(shape);
        graphics2D.dispose();
    }

}
