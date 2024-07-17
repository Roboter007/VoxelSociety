package de.Roboter007.voxelsociety.ui.screen.screens;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelButton;
import de.Roboter007.voxelsociety.ui.screen.Screen;
import de.Roboter007.voxelsociety.utils.VoxelPanel;

import java.awt.*;

public class TitleScreen extends Screen {

    public TitleScreen() {
        super(false);
    }

    @Override
    public void onDelete(UiUtilities uiUtilities) {

    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        int x = VoxelPanel.screenWidth / 2 - (VoxelPanel.tileSize /2);
        int y = VoxelPanel.screenHeight / 2 - (VoxelPanel.tileSize /2);
        //Graphics2D graphics2D = uiUtilities.getGraphics2D();
        VoxelButton voxelButton = new VoxelButton(UiStyle.DEFAULT, x, y, 100, 20, () -> System.out.println("Nice Try"));
        voxelButton.draw(uiUtilities);
    }

    @Override
    public void onChange(Screen toScreen) {
    }
}
