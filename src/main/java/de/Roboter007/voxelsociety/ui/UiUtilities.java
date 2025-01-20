package de.Roboter007.voxelsociety.ui;

import de.Roboter007.voxelsociety.utils.VoxelPanel;

import java.awt.*;

public class UiUtilities {

    private final VoxelPanel voxelPanel;
    private final Graphics2D graphics2D;

    public UiUtilities(VoxelPanel voxelPanel, Graphics2D graphics2D) {
        this.voxelPanel = voxelPanel;
        this.graphics2D = graphics2D;
    }

    public int calcScreenX(int screenPos) {
        return (VoxelPanel.screenWidth - VoxelPanel.tileSize - screenPos) / 2;
    }

    public int calcScreenY(int screenPos) {
        return (VoxelPanel.screenHeight - VoxelPanel.tileSize - screenPos) / 2;
    }


    public Color blend(Color c1, Color c2, float ratio) {
        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;

        int i1 = c1.getRGB();
        int i2 = c2.getRGB();

        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);

        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);

        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));

        return new Color( a << 24 | r << 16 | g << 8 | b );
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public VoxelPanel getVoxelPanel() {
        return voxelPanel;
    }

    public void drawString(String text, UiStyle uiStyle, boolean withBackgroundColor, int x, int y) {
        graphics2D.setColor(uiStyle.getColorPalette().textColor());
        graphics2D.setFont(uiStyle.getFont());
        if(withBackgroundColor) {
            graphics2D.setBackground(uiStyle.getColorPalette().backgroundColor());
        }
        graphics2D.drawString(text, x, y);

    }

    public void drawString(String text, Font font, Color mainColor, Color backgroundColor, int x, int y) {
        graphics2D.setColor(mainColor);
        graphics2D.setFont(font);
        graphics2D.setBackground(backgroundColor);
        graphics2D.drawString(text, x, y);
    }

    public void drawString(String text, Font font, Color color, int x, int y) {
        graphics2D.setColor(color);
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }

    public void drawString(String text, Font font, int x, int y) {
        graphics2D.setFont(font);
        graphics2D.drawString(text, x, y);
    }

    public void drawRectangle(Color outlineColor, Color backgroundColor, int x, int y, int width, int height) {
        graphics2D.setColor(outlineColor);
        graphics2D.setBackground(backgroundColor);
        graphics2D.fillRect(x, y, width, height);
    }

    public void drawRectangleOutline(Color outlineColor, Color backgroundColor, int x, int y, int width, int height) {
        graphics2D.setColor(outlineColor);
        graphics2D.setBackground(backgroundColor);
        graphics2D.drawRect(x, y, width, height);
    }

    public void drawShape(Color color, Shape shape) {
        graphics2D.setColor(color);
        graphics2D.draw(shape);
    }

    public void fillShape(Color color, Shape shape) {
        graphics2D.setColor(color);
        graphics2D.fill(shape);
    }

    public void dispose() {
        this.graphics2D.dispose();
    }

}
