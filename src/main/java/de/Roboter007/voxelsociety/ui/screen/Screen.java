package de.Roboter007.voxelsociety.ui.screen;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;

import java.util.ArrayList;
import java.util.List;

public abstract class Screen {

    private final boolean keepScreens;
    private final List<VoxelElement> voxelElements = new ArrayList<>();

    public Screen(boolean keepScreens) {
        this.keepScreens = keepScreens;
        ScreenHandler.ACTIVE_SCREEN_LIST.add(this);
    }

    public boolean keepScreens() {
        return keepScreens;
    }

    public List<VoxelElement> getVoxelElements() {
        return voxelElements;
    }

    public abstract void draw(UiUtilities uiUtilities);

    public abstract void onChange(Screen toScreen);

    public abstract void onDelete(UiUtilities uiUtilities);

    public void deleteScreen() {
        ScreenHandler.setCurrentScreen(null);
    }


}
