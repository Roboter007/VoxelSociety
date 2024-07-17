package de.Roboter007.voxelsociety.ui.screen.screens;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.Screen;
import de.Roboter007.voxelsociety.ui.screen.ScreenHandler;

import java.awt.*;

public class InfoScreen extends Screen {

    public InfoScreen() {
        super(true);
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        // Draw Time
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        graphics2D.setFont(UiStyle.DEFAULT.getFont());

        // Draw Time
        graphics2D.setColor(Color.WHITE);
        String info = "Draw Time: Nano - " + uiUtilities.getVoxelPanel().timePassedNano + "; Seconds - " + uiUtilities.getVoxelPanel().timePassedSec;
        graphics2D.drawString(info, 5, 20);
        System.out.println(info);

        //Fps
        graphics2D.setColor(Color.CYAN);
        String fps_string = "Fps: " + uiUtilities.getVoxelPanel().fps;
        System.out.println(fps_string);
        graphics2D.drawString(fps_string, 5, 40);
    }

    @Override
    public void onChange(Screen toScreen) {
        //ToDo: einfach eine boolean Variable machen, die das handelt
        ScreenHandler.ACTIVE_SCREEN_LIST.remove(this);
    }

    @Override
    public void onDelete(UiUtilities uiUtilities) {

    }
}
