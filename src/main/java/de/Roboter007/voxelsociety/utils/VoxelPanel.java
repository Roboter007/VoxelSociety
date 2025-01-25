package de.Roboter007.voxelsociety.utils;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.config.VoxelConfigs;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.world.entity.Entities;
import de.Roboter007.voxelsociety.keys.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class VoxelPanel extends JPanel implements Runnable {

    public static final int originalTileSize = 16;
    public static final int scale = 3;
    public static final int tileSize = originalTileSize * scale;

    public static final int screenColumns = 32;
    public static final int screenRows = 20;
    public static int prevScreenWidth;
    public static int prevScreenHeight;
    public static int screenWidth = tileSize * screenColumns;
    public static int screenHeight = tileSize * screenRows;

    private final KeyHandler keyHandler = new KeyHandler();
    private Thread thread;
    public UiUtilities uiUtilities = null;

    //public static int fps_limit = getFPSLimit();
    public static int screen_fps_limit = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
    public static int fps = 0;
    public long timePassedNano;
    public double timePassedSec;

    public boolean changeWinSizeOnResize = true;


    public static int getFPSLimit() {
        return VoxelConfigs.optionsConfig.getOptionWithFallback(0, screen_fps_limit);
    }

    public VoxelPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(Entities.PLAYER.keyHandler);
        this.addMouseMotionListener(Entities.PLAYER.keyHandler);
        this.addMouseListener(Entities.PLAYER.keyHandler);
        this.addMouseWheelListener(Entities.PLAYER.keyHandler);



        this.addMouseListener(MenuHandler.getFocusedScreen().inputHandler);
        this.addMouseMotionListener(MenuHandler.getFocusedScreen().inputHandler);
        this.addMouseWheelListener(MenuHandler.getFocusedScreen().inputHandler);
        this.addKeyListener(MenuHandler.getFocusedScreen().inputHandler);

        /*this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent event) {
                if(event.getComponent() instanceof VoxelPanel voxelPanel) {
                    int difW = screenWidth - prevScreenWidth;
                    int difH = screenHeight - prevScreenHeight;
                    int div = difW / difH;
                    for(String screen : MenuHandler.CACHED_VOXEL_ELEMENTS.keySet()) {
                        for(VoxelElement voxelElement : MenuHandler.CACHED_VOXEL_ELEMENTS.get(screen)) {
                            voxelElement.updateBoundingBox(voxelElement.getBoundingBox().x * div, voxelElement.getBoundingBox().y * div, voxelElement.getBoundingBox().width + difW, voxelElement.getBoundingBox().height + difH);
                        }
                    }

                }
            }
        }); */

        this.setFocusable(true);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    public boolean checkIfIsRightThread() {
        return Thread.currentThread() == thread;
    }

    public void startGameThread() {
        thread = new Thread(this);
        VoxelSociety.SERVICE.submit(thread);
    }

    public void update() {
        //Worlds.tickWorlds();
        //VoxelSociety.SERVICE.submit(Entities.PLAYER::update);
        if(Entities.PLAYER != null) {
            Entities.PLAYER.update();
        }
    }

    public void tick() {
        if(Entities.PLAYER != null) {
            Entities.PLAYER.tick();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        uiUtilities = new UiUtilities(this, graphics2D);

        long drawStart = System.nanoTime();

        MenuHandler.drawAllScreens(uiUtilities);

        tick();

        graphics2D.dispose();

        long drawEnd = System.nanoTime();
        timePassedNano = drawEnd - drawStart;
        timePassedSec = timePassedNano / 1000000000.0D;
    }


    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (thread != null) {
            double drawInterval = (double) 1000000000 / getFPSLimit();
            if(changeWinSizeOnResize) {
                if(screenWidth != this.getWidth()) {
                    prevScreenWidth = screenWidth;
                    screenWidth = this.getWidth();
                    Entities.PLAYER.screenPos.setX(VoxelPanel.screenWidth / 2 - (VoxelPanel.tileSize / 2));
                }
                if(screenHeight != this.getHeight()) {
                    prevScreenHeight = screenHeight;
                    screenHeight = this.getHeight();
                    Entities.PLAYER.screenPos.setY(VoxelPanel.screenHeight / 2 - (VoxelPanel.tileSize / 2));
                }
            }

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;

            lastTime = currentTime;

            if(delta >= 1) {
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
