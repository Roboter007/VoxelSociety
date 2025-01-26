package de.Roboter007.voxelsociety.ui.screen.menus.options;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelSlider;
import de.Roboter007.voxelsociety.ui.elements.VoxelTaskButton;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.utils.VoxelPanel;

import java.awt.*;

import static de.Roboter007.voxelsociety.config.VoxelConfigs.optionsConfig;

public class VideoSettingsMenu extends AmbientMenu {

    private final boolean inGame;

    public VideoSettingsMenu(boolean inGame) {
        super("Video Settings", 0);
        this.inGame = inGame;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {

        RecCalculator recCalculator = new RecCalculator();
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        recCalculator.calc(1000, 750);
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(recCalculator.getX(), recCalculator.getY(), 1000, 750);

        recCalculator.calc(400, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Back", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 300, 400, 80, () -> {
            MenuHandler.setFocusedScreen(new OptionsMenu(inGame));
        }));

        recCalculator.calc(300, 100);
        drawElement(1, uiUtilities, new VoxelSlider<>(VoxelSlider.Direction.VERTICAL, "Fps: ", UiStyle.DEFAULT, recCalculator.getX() - 200, recCalculator.getY() - 250, 100, 1, 0, 300, VoxelPanel.getFPSLimit(), 1, (fps) -> {
            optionsConfig.setOption("fps", fps);
        }));
    }
}
