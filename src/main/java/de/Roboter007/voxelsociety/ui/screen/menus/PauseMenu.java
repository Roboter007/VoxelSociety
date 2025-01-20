package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelTaskButton;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;
import de.Roboter007.voxelsociety.ui.screen.menus.options.OptionsMenu;
import de.Roboter007.voxelsociety.utils.ColorUtils;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.utils.VoxelUtils;
import de.Roboter007.voxelsociety.world.entity.Entities;

import java.awt.*;

public class PauseMenu extends AmbientMenu {

    public PauseMenu() {
        super("Pause Menu", 0);
        VoxelUtils.pauseGame();
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        super.draw(uiUtilities);
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        RecCalculator recCalculator = new RecCalculator();

        graphics2D.setColor(ColorUtils.transparencyColor(Color.BLACK, 80));
        graphics2D.fillRect(0, 0, VoxelPanel.screenWidth, VoxelPanel.screenHeight);

        recCalculator.calc(400, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Back", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY(), 400, 80, () -> {
            if(MenuHandler.getFocusedScreen() instanceof PauseMenu) {
                MenuHandler.removeFocusedScreen();
                VoxelUtils.unpauseGame();
            }

        }));


        recCalculator.calc(400, 80);
        drawElement(1, uiUtilities, new VoxelTaskButton("Options", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 100, 400, 80, () -> {
            MenuHandler.removeScreen(PauseMenu.class);
            MenuHandler.setFocusedScreen(new OptionsMenu(true));
        }));

        recCalculator.calc(400, 80);
        drawElement(2, uiUtilities, new VoxelTaskButton("Title Screen", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 200, 400, 80, () -> {
            MenuHandler.ACTIVE_MENU_LIST.clear();

            Entities.PLAYER.world.stop();
            Entities.PLAYER.world = null;

            MenuHandler.setFocusedScreen(new MainMenu());
        }));

    }

    @Override
    public boolean deleteOnChange(GameMenu newMenu) {
        return false;
    }

    @Override
    public void onDelete() {
        AmbientMenu.playlist.pauseCurrentTrack();
    }
}
