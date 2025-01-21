package de.Roboter007.voxelsociety.ui.screen.menus.options;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.config.OptionsConfig;
import de.Roboter007.voxelsociety.config.values.VoxelConfigValue;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.*;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.PauseMenu;
import de.Roboter007.voxelsociety.ui.screen.menus.MainMenu;
import de.Roboter007.voxelsociety.utils.VoxelPanel;

import java.awt.*;

//import static de.Roboter007.voxelsociety.config.OptionsConfig.options;

public class OptionsMenu extends AmbientMenu {

    private final boolean inGame;


    public OptionsMenu(boolean inGame) {
        super("OptionsScreen", 0);
        this.inGame = inGame;
    }


    @Override
    public void draw(UiUtilities uiUtilities) {
        super.draw(uiUtilities);
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        graphics2D.setColor(Color.WHITE);

        RecCalculator recCalculator = new RecCalculator();

        recCalculator.calc(1000, 750);
        graphics2D.fillRect(recCalculator.getX(), recCalculator.getY(), 1000, 750);

        recCalculator.calc(400, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Back", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 300, 400, 80, () -> {
            if(inGame) {
                MenuHandler.setFocusedScreen(new PauseMenu());
            } else {
                MenuHandler.setFocusedScreen(new MainMenu());
            }
        }));

        recCalculator.calc(300, 100);
        int defaultFps = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
        drawElement(1, uiUtilities, new VoxelSlider<>(VoxelSlider.Direction.VERTICAL, "Fps: ", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY(), 100, 1, 0, 300, defaultFps, 1, (fps) -> {
            //VoxelPanel.fps_limit = fps;
            //options.setOption(0, fps);
            System.out.println("Fps Limit: " + VoxelPanel.fps_limit);
        }));

       /* drawElement(1, uiUtilities, new VoxelSlider<>(VoxelSlider.Direction.VERTICAL, "Test: ", UiStyle.DEFAULT, 0, 100, 100, 5,0F, 10F,1F, 0.5F, (test2) -> test3 = test2));
        drawElement(2, uiUtilities, new VoxelToggleButton("Test: ", UiStyle.DEFAULT, 0, 300, 50, 50, (n) -> nice = n));
        drawElement(3, uiUtilities, new VoxelTextField("Moin", UiStyle.DEFAULT, 5, 700, 50, 10, false, (lol) -> test = lol));
        drawElement(4, uiUtilities, new VoxelTextField("xD", UiStyle.DEFAULT, 5, 500, 50, 10, (lol) -> test = lol));


        recCalculator.calc(500, 1000);
        drawElement(6, uiUtilities, new VoxelElementList(ScrollableVoxelElement.Direction.X, new Rectangle(recCalculator.getX(), recCalculator.getY(), 500, 1000), new Rectangle(recCalculator.getX(), 250, 500, 500), 10).getVoxelBuilder()
                .addElement(1, 1, new VoxelTaskButton("Test-1", UiStyle.DEFAULT, 0, 0, 100, 100, () -> System.out.println("Test-1")))
                .addElement(2, 1, new VoxelTaskButton("Test-2", UiStyle.DEFAULT, 0, 0, 100, 100, () -> System.out.println("Test-2")))
                .addElement(1, 2, new VoxelTaskButton("Test-3", UiStyle.DEFAULT, 0, 0, 100, 100, () -> System.out.println("Test-3")))
                .addElement(2, 2, new VoxelTaskButton("Test-4", UiStyle.DEFAULT, 0, 0, 100, 100, () -> System.out.println("Test-4")))
                .build()); */

    }

}
