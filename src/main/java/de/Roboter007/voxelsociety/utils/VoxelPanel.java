package de.Roboter007.voxelsociety.utils;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.ScreenHandler;
import de.Roboter007.voxelsociety.ui.screen.screens.InGameScreen;
import de.Roboter007.voxelsociety.ui.screen.screens.InfoScreen;
import de.Roboter007.voxelsociety.world.block.BlockRegistry;
import de.Roboter007.voxelsociety.world.entity.Entities;
import de.Roboter007.voxelsociety.world.entity.Player;
import de.Roboter007.voxelsociety.keys.KeyHandler;
import de.Roboter007.voxelsociety.keys.VSKey;
import de.Roboter007.voxelsociety.world.Worlds;

import javax.swing.*;
import java.awt.*;

public class VoxelPanel extends JPanel implements Runnable {

    public static final int originalTileSize = 16;
    public static final int scale = 3;
    public static final int tileSize = originalTileSize * scale;

    public static final int screenColumns = 28;
    public static final int screenRows = 20;
    public static final int screenWidth = tileSize * screenColumns;
    public static final int screenHeight = tileSize * screenRows;

    public static final int maxWorldColumns = 50;
    public static final int maxWorldRows = 50;
    public static final int worldWidth = tileSize * maxWorldColumns;
    public static final int worldHeight = tileSize * maxWorldRows;

    private final KeyHandler keyHandler = new KeyHandler();
    private Thread thread;
    public UiUtilities uiUtilities = null;

    public int fps_limit = 60;
    public int fps = 0;
    public long timePassedNano;
    public double timePassedSec;


    public VoxelPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(Entities.PLAYER.keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        thread = new Thread(this);
        VoxelSociety.SERVICE.submit(thread);
    }

    public void update() {
        VoxelSociety.SERVICE.submit(Entities.PLAYER::update);
        Worlds.tickWorlds();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        uiUtilities = new UiUtilities(this, graphics2D);

        long drawStart = System.nanoTime();

        ScreenHandler.drawAllScreens(uiUtilities);

        if(VSKey.DEBUG_DRAW_TIME.isPressed()) {
            long drawEnd = System.nanoTime();
            timePassedNano = drawEnd - drawStart;
            timePassedSec = timePassedNano / 1000000000.0D;

            ScreenHandler.setCurrentScreen(new InfoScreen());

        } else {
            if(ScreenHandler.getCurrentScreen() instanceof InfoScreen) {
                ScreenHandler.setCurrentScreen(new InGameScreen());
            }
        }

        graphics2D.dispose();
    }


    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / fps_limit;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (thread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;

            lastTime = currentTime;

            if(delta >= 1) {
                //ToDo: seperates updaten vom Spieler
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                fps = drawCount;

                drawCount = 0;
                timer = 0;
            }
        }
    }
}
