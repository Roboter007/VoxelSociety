package de.Roboter007.voxelsociety.world;

import java.util.*;

public class RenderLayers {

    public static List<RenderLayer> LAYERABLE_LIST = new ArrayList<>();

    private static int MAIN_LAYER_NUMBER = 0;

    public static void setMainLayerNumber(int mainLayerNumber) {
        MAIN_LAYER_NUMBER = mainLayerNumber;
    }

    public static int getMainLayerNumber() {
        return MAIN_LAYER_NUMBER;
    }

    public boolean isOnMainLayer(int layer) {
        return MAIN_LAYER_NUMBER == layer;
    }

    public static RenderLayer getMainLayer() {
        return LAYERABLE_LIST.getFirst();
    }
}
