package de.Roboter007.voxelsociety.ui.screen.menus.options;

import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.sound.SoundType;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.slider.ScrollDirection;
import de.Roboter007.voxelsociety.ui.elements.slider.VoxelSlider;
import de.Roboter007.voxelsociety.ui.elements.VoxelTaskButton;
import de.Roboter007.voxelsociety.ui.screen.AmbientMenu;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;

import java.awt.*;

public class SoundOptionsMenu extends AmbientMenu {

    private final boolean inGame;

    public SoundOptionsMenu(boolean inGame) {
        super("Sound Options", 0);
        this.inGame = inGame;
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        RecCalculator recCalculator = new RecCalculator();
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        recCalculator.calc(1200, 750);
        graphics2D.setColor(new Color(42, 42, 42));
        graphics2D.fillRect(recCalculator.getX(), recCalculator.getY(), 1200, 750);

        recCalculator.calc(400, 80);
        drawElement(0, uiUtilities, new VoxelTaskButton("Back", UiStyle.DEFAULT, recCalculator.getX(), recCalculator.getY() + 300, 400, 80, () -> {
            MenuHandler.setFocusedScreen(new OptionsMenu(inGame));
        }));


        recCalculator.calc(200, 50);
        drawElement(1, uiUtilities, new VoxelSlider<>(ScrollDirection.VERTICAL, "Music Volume: ", UiStyle.DEFAULT, recCalculator.getX() - 200, recCalculator.getY() - 250, 200, 50, 0, 100, SoundType.MUSIC.volumeFromConfig(), 1,  SoundType.MUSIC::setVolume));

        drawElement(2, uiUtilities, new VoxelSlider<>(ScrollDirection.VERTICAL, "Sound Volume: ", UiStyle.DEFAULT, recCalculator.getX() + 200, recCalculator.getY() - 250, 200 , 50, 0, 100, SoundType.SOUND_EFFECT.volumeFromConfig(), 1, SoundType.SOUND_EFFECT::setVolume));

        super.draw(uiUtilities);
    }

}
