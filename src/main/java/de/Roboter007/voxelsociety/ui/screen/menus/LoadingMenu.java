package de.Roboter007.voxelsociety.ui.screen.menus;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.api.loading.LoadingStage;
import de.Roboter007.voxelsociety.sound.Sound;
import de.Roboter007.voxelsociety.sound.SoundType;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.elements.VoxelProgressbar;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.ui.screen.menus.menu.GameMenu;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LoadingMenu extends GameMenu {


    private Sound sound = null;

    public LoadingMenu() {
        super("Loading Menu");

        Runnable runnable = () -> {
            for (LoadingStage loadingStage : LoadingStage.values()) {
                loadingStage.run();

                VoxelElement voxelElement = this.getElement(0);
                if(voxelElement instanceof VoxelProgressbar voxelProgressbar) {
                    VoxelProgressbar progressbar = voxelProgressbar.progress(loadingStage.id() * 50);
                    this.setElement(0, progressbar);
                }

                if (loadingStage == LoadingStage.DEPENDENCY) {

                    if (sound == null) {
                        sound = new Sound(SoundType.MUSIC, "intro.mp3");
                    }

                    if (!sound.isPlaying()) {
                        sound.play();
                    }

                    /*try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } */
                } else if(loadingStage == LoadingStage.TEXTURES) {
                    /*try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } */
                }
            }

            onFinishLoading();
        };

        VoxelSociety.SERVICE.submit(runnable);
    }


    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        RecCalculator recCalculator = new RecCalculator();

        graphics2D.setFont(new Font("Century Schoolbook Bold", Font.BOLD, 100));

        Rectangle2D stringBounds = graphics2D.getFont().getStringBounds(VoxelSociety.GAME_NAME, graphics2D.getFontRenderContext());
        recCalculator.calc((int) stringBounds.getWidth(), (int) stringBounds.getHeight());

        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawString(VoxelSociety.GAME_NAME, recCalculator.getX(), recCalculator.getY() - 150);

        recCalculator.calc(400, 20);
        drawElement(0, uiUtilities, new VoxelProgressbar(Color.ORANGE, Color.RED, recCalculator.getX(), recCalculator.getY(), 400, 20));


        recCalculator.calc(500, 80);

    }

    public void onFinishLoading() {
        if(this.sound != null) {
            this.sound.stop();
            MenuHandler.setFocusedScreen(new MainMenu());
        }
    }

}
