package de.Roboter007.voxelsociety.utils;

public class VoxelUtils {

    private static boolean paused = false;

    public static void pauseGame() {
        paused = true;
    }

    public static void unpauseGame() {
        paused = false;
    }

    public static boolean isPaused() {
        return paused;
    }
}
