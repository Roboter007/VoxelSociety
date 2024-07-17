package de.Roboter007.voxelsociety.world;

import java.util.ArrayList;
import java.util.List;

public class Worlds {

    public static final List<World> REGISTERED_WORLDS = new ArrayList<>();

    public static void loadWorlds() {
        System.out.println("Loading Worlds...");
        for(World world : REGISTERED_WORLDS) {
            world.load();
        }
    }

    public static void tickWorlds() {

        for(World world : REGISTERED_WORLDS) {
            double drawInterval = (double) 1000000000 / world.tps_limit;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;
            world.tick();
        }
    }

    public static World MAIN_WORLD = new World("main_world", 50, 50);
    public static World SECOND_WORLD = new World("second_world", 50, 50);
}
