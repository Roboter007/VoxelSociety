package de.Roboter007.voxelsociety.api;

import de.Roboter007.voxelsociety.utils.VoxelPanel;

public class RecCalculator {

    private int x;
    private int y;

    public RecCalculator() {}


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void calc(int width, int height) {
        this.x = (VoxelPanel.screenWidth - width) / 2;
        this.y = (VoxelPanel.screenHeight - height) / 2;
    }
}
