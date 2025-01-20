package de.Roboter007.voxelsociety.world;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.entity.Entities;
import de.Roboter007.voxelsociety.world.entity.Player;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Worlds {

    public static String WORLD_PATH = VoxelSociety.VOXEL_PATHS.getVoxelGamePath().toString() + "/worlds";
    public static List<World> REGISTERED_WORLDS = new ArrayList<>();

    public static void loadWorlds() {
        System.out.println("Loading Worlds...");

        REGISTERED_WORLDS = getWorlds();

        System.out.println(REGISTERED_WORLDS);

        for(World world : REGISTERED_WORLDS) {
            world.load();
            System.out.println("Loaded World: " + world.getName());
        }
        //Entities.PLAYER.changeWorld(REGISTERED_WORLDS.getFirst());
    }

    public static int worldId(World world) {
        return REGISTERED_WORLDS.indexOf(world);
    }

    public static List<World> getWorlds() {
        List<World> worldList = new ArrayList<>();
        File[] directories = new File(WORLD_PATH).listFiles(File::isDirectory);
        if(directories != null) {
            for (File file : directories) {
                WorldSettings worldSettings = WorldSettings.fromFile(file.getName(), new File(file.getAbsolutePath() + "/world.info"));
                if(worldSettings != null) {
                    worldList.add(new World(file.getName(), worldSettings));
                    System.out.println("Successfully imported World: " + file.getName());
                } else {
                    System.out.println("Failed to import World: " + file.getName());
                }
            }
        }

        return worldList;
    }

    public static World byName(String name) {
        World worldResult = null;
        for(World world : getWorlds()) {
            if(world.getName().equals(name)) {
                worldResult = world;
            }
        }
        return worldResult;
    }

    public static void register(World world) {
        world.load();
        world.getWorldSettings().saveToFile();
        REGISTERED_WORLDS.add(world);
    }
}
