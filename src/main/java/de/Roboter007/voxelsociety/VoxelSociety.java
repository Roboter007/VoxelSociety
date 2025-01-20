package de.Roboter007.voxelsociety;

import de.Roboter007.voxelsociety.api.tester.PerformanceTester;
import de.Roboter007.voxelsociety.utils.texture.TextureManager;
import de.Roboter007.voxelsociety.utils.VoxelFrame;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.utils.VoxelPaths;

import java.io.File;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static java.util.concurrent.ForkJoinPool.defaultForkJoinWorkerThreadFactory;

public class VoxelSociety  {

    public static String GAME_NAME = "Voxel Society";
    public static String GAME_ID = "voxelsociety";
    public static Logger LOGGER = Logger.getLogger(GAME_NAME);
    public static TextureManager TEXTURE_MANAGER = new TextureManager();
    public static ExecutorService SERVICE = new ForkJoinPool(Math.min(0x7fff, Runtime.getRuntime().availableProcessors()),
            defaultForkJoinWorkerThreadFactory, null, true,
            2, 0x7fff, 1, null, 60_000L, TimeUnit.MILLISECONDS);
    public VoxelSociety() {}

    public static PerformanceTester VOXEL_PERF_TESTER = new PerformanceTester();
    public static VoxelPaths VOXEL_PATHS = new VoxelPaths();


    public static void main(String[] args) {
        VoxelPanel gamePanel = new VoxelPanel();
        VoxelFrame window = new VoxelFrame(gamePanel);
        //ScreenHandler.setFocusedScreen(new LoadingScreen());
        System.out.println("Game Folder Path: " + VOXEL_PATHS.getVoxelGamePath());

        /*GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        Font[] allFonts = ge.getAllFonts();

        for (Font font : allFonts) {

            System.out.println(font.getFontName(Locale.US));
        } */
    }



    public static void deleteFolder(File folder, Runnable taskOnDeletion) {
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f, null);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();

        if(taskOnDeletion != null) {
            taskOnDeletion.run();
        }
    }

}