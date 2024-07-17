package de.Roboter007.voxelsociety.ui.screen;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.screen.screens.InGameScreen;
import de.Roboter007.voxelsociety.ui.screen.screens.TitleScreen;

import java.util.ArrayList;
import java.util.List;

public class ScreenHandler {

    public static final List<VoxelElement> ACTIVE_ELEMENTS_LIST = new ArrayList<>();
    public static final List<Screen> ACTIVE_SCREEN_LIST = new ArrayList<>();
    private static Screen FOCUSED = new TitleScreen();

    public static void setCurrentScreen(Screen screen) {
        FOCUSED.onChange(screen);
        FOCUSED = screen;
        if(screen != null) {
            if (!screen.keepScreens()) {
                ACTIVE_SCREEN_LIST.clear();
            }
        } else {
            ACTIVE_SCREEN_LIST.clear();
        }
    }

    public static Screen getCurrentScreen() {
        return FOCUSED;
    }

    public static boolean isScreenActive(Screen screen) {
        return FOCUSED == screen;
    }

    public static void drawAllScreens(UiUtilities uiUtilities) {
        if(FOCUSED != null) {
            for (Screen screen : ACTIVE_SCREEN_LIST) {
                screen.draw(uiUtilities);
            }
        }
    }

}
