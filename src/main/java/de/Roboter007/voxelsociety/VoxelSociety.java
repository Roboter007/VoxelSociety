package de.Roboter007.voxelsociety;

import de.Roboter007.voxelsociety.utils.TextureManager;
import de.Roboter007.voxelsociety.utils.VoxelFrame;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.Worlds;
import de.Roboter007.voxelsociety.world.block.Blocks;

import java.util.concurrent.*;
import java.util.logging.Logger;

import static java.util.concurrent.ForkJoinPool.defaultForkJoinWorkerThreadFactory;

public class VoxelSociety {

    public static String GAME_NAME = "VoxelSociety";
    public static Logger LOGGER = Logger.getLogger(GAME_NAME);
    public static TextureManager TEXTURE_MANAGER = new TextureManager();
    public static ExecutorService SERVICE = new ForkJoinPool(Math.min(0x7fff, Runtime.getRuntime().availableProcessors()),
            defaultForkJoinWorkerThreadFactory, null, true,
            0, 0x7fff, 1, null, 60_000L, TimeUnit.MILLISECONDS);
    public VoxelSociety() {}

    public static VoxelPanel voxelPanel = null;

    public static void main(String[] args) {
        Blocks.loadTextures();
        Worlds.loadWorlds();

        VoxelPanel gamePanel = new VoxelPanel();
        voxelPanel = gamePanel;
        VoxelFrame window = new VoxelFrame(gamePanel);

        //ScreenHandler.setCurrentScreen(new InGameScreen(gamePanel.uiUtilities));

        /*GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        Font[] allFonts = ge.getAllFonts();

        for (Font font : allFonts) {

            System.out.println(font.getFontName(Locale.US));
        } */
    }

}