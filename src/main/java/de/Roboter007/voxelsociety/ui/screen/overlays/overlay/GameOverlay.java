package de.Roboter007.voxelsociety.ui.screen.overlays.overlay;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.screen.Screen;

import java.util.List;

public abstract class GameOverlay extends Screen implements Overlay {

    private final String name;
    private final List<VoxelElement> voxelElements;

    public GameOverlay(String name, List<VoxelElement> voxelElements) {
        this.name = name;
        this.voxelElements = voxelElements;
    }

    public String getName() {
        return name;
    }

    public List<VoxelElement> getVoxelElements() {
        return voxelElements;
    }

    public void draw(UiUtilities uiUtilities) {
        for(VoxelElement voxelElement : voxelElements) {
            voxelElement.draw(uiUtilities);
        }
    }

    public abstract int getRenderOrder();

}
