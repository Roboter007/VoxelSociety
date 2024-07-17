package de.Roboter007.voxelsociety.ui.screen.screens;

import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.Screen;
import de.Roboter007.voxelsociety.ui.screen.ScreenHandler;
import de.Roboter007.voxelsociety.world.entity.Entities;

public class InGameScreen extends Screen {
    public InGameScreen() {
        super(true);
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        //this.getUtilities().getVoxelPanel().blockRegistry.draw(getUtilities().getGraphics2D());
        Entities.PLAYER.drawScreen(uiUtilities.getGraphics2D());
        Entities.PLAYER.drawPlayer(uiUtilities.getGraphics2D());
    }

    @Override
    public void onChange(Screen toScreen) {
    }

    @Override
    public void onDelete(UiUtilities uiUtilities) {

    }
}
