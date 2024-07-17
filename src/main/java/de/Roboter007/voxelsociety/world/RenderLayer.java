package de.Roboter007.voxelsociety.world;

import java.util.ArrayList;
import java.util.List;

public class RenderLayer {

    private final List<Layerable> layerableList = new ArrayList<>();

    public RenderLayer() {
    }

    public List<Layerable> getLayerableList() {
        return layerableList;
    }

    public void addLayerableObject(Layerable layerable) {
        this.layerableList.add(layerable);
    }
}
