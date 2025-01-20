package de.Roboter007.voxelsociety.utils;

import java.awt.*;

public class ColorUtils {

    public static Color transparencyColor(Color color, int a) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
    }
}
