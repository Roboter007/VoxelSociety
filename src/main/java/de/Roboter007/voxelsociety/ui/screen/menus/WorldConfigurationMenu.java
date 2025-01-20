package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelTaskButton;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.World;

import java.awt.*;

public class WorldConfigurationMenu extends AmbientMenu {

    private final World world;

    public WorldConfigurationMenu(World world) {
        super("World Configuration Menu");
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        super.draw(uiUtilities);
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        graphics2D.setColor(Color.WHITE);

        RecCalculator recCalculator = new RecCalculator();

        graphics2D.fillRect(0, 100, VoxelPanel.screenWidth,700);

        recCalculator.calc(400, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Back", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 300, 400, 80, () -> MenuHandler.setFocusedScreen(new WorldSelectionMenu())));

    }
}

