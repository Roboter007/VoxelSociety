package de.Roboter007.voxelsociety.ui;

import java.awt.*;

public enum UiStyle {
    DEFAULT(new ColorPalette(Color.BLACK, Color.WHITE, Color.WHITE), new Font("Century Schoolbook Bold", Font.BOLD, 20));

    private final ColorPalette colorPalette;
    private final Font font;

    UiStyle(ColorPalette colorPalette, Font font) {
        this.colorPalette = colorPalette;
        this.font = font;
    }

    public ColorPalette getColorPalette() {
        return colorPalette;
    }

    public Font getFont() {
        return font;
    }
}
