package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelTaskButton;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ConfirmMenu extends GameMenu {

    private final String message;
    private final GameMenu parent;
    private final Runnable task;

    public ConfirmMenu(String message, GameMenu parent, Runnable task) {
        super("Confirm Menu");
        this.task = task;
        this.parent = parent;
        this.message = message;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        RecCalculator recCalculator = new RecCalculator();

        recCalculator.calc(400, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Continue", UiStyle.DEFAULT, recCalculator.getX() - 210, recCalculator.getY() + 300, 400, 80, () -> {
            task.run();
            MenuHandler.setFocusedScreen(parent);
        }));

        drawElement(1, uiUtilities, new VoxelTaskButton("Cancel", UiStyle.DEFAULT, recCalculator.getX() + 210, recCalculator.getY() + 300, 400, 80, () -> MenuHandler.setFocusedScreen(parent)));

        Rectangle2D stringBounds = graphics2D.getFont().getStringBounds(message, graphics2D.getFontRenderContext());
        recCalculator.calc((int) stringBounds.getWidth(), (int) stringBounds.getHeight());
        graphics2D.drawString(message, recCalculator.getX(), recCalculator.getY());

    }


}
