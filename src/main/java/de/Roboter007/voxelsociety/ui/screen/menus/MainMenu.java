package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.*;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.options.OptionsMenu;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MainMenu extends AmbientMenu {


    public MainMenu() {
        super("Main Menu");
    }


    @Override
    public void draw(UiUtilities uiUtilities) {
        super.draw(uiUtilities);
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        int width = 500;
        int height = 80;

        RecCalculator recCalculator = new RecCalculator();

        graphics2D.setFont(new Font("Century Schoolbook Bold", Font.BOLD, 100));

        Rectangle2D stringBounds = graphics2D.getFont().getStringBounds(VoxelSociety.GAME_NAME, graphics2D.getFontRenderContext());
        recCalculator.calc((int) stringBounds.getWidth(), (int) stringBounds.getHeight());

        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawString(VoxelSociety.GAME_NAME, recCalculator.getX(), recCalculator.getY() - 150);

        recCalculator.calc(500, 80);

        drawElement(0, uiUtilities, new VoxelTaskButton("Singleplayer", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY(), width, height, () -> MenuHandler.setFocusedScreen(new WorldSelectionMenu())));

        drawElement(1, uiUtilities, new VoxelTaskButton("Multiplayer", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + height + 20, width, height, () -> System.out.println("Not Implemented")));

        drawElement(2, uiUtilities, new VoxelTaskButton("Options", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 2 * height + 40, width, height, () -> MenuHandler.setFocusedScreen(new OptionsMenu(false))));
    }

}
