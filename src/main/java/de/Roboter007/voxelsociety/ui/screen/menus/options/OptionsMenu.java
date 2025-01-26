package de.Roboter007.voxelsociety.ui.screen.menus.options;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.*;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.PauseMenu;
import de.Roboter007.voxelsociety.ui.screen.menus.MainMenu;

import java.awt.*;

public class OptionsMenu extends AmbientMenu {

    protected final boolean inGame;


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
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(recCalculator.getX(), recCalculator.getY(), 1000, 750);

        recCalculator.calc(400, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Back", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 300, 400, 80, () -> {
            if(inGame) {
                MenuHandler.setFocusedScreen(new PauseMenu());
            } else {
                MenuHandler.setFocusedScreen(new MainMenu());
            }
        }));

        recCalculator.calc(350, 80);
        drawElement(1, uiUtilities, new VoxelTaskButton("Video Settings", UiStyle.DEFAULT, recCalculator.getX() - 200, recCalculator.getY() - 250, 350, 80, () -> {
            MenuHandler.setFocusedScreen(new VideoSettingsMenu(inGame));
        }));

        recCalculator.calc(350, 80);
        drawElement(2, uiUtilities, new VoxelTaskButton("Sound Options", UiStyle.DEFAULT, recCalculator.getX() + 200, recCalculator.getY() - 250, 350, 80, () -> {
            MenuHandler.setFocusedScreen(new SoundOptionsMenu(inGame));
        }));
    }

}
