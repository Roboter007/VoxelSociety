package de.Roboter007.voxelsociety.world;

import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.block.BlockEntry;
import de.Roboter007.voxelsociety.world.block.BlockRegistry;
import de.Roboter007.voxelsociety.world.entity.Entity;
import de.Roboter007.voxelsociety.world.pos.IntPosition;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {
    private final String name;
    private final int maxXSize;
    private final int maxYSize;
    public final float tps_limit = 20;
    public final float tps = 0;
    //ToDo: verbessern des Block Caches für ein Gennerierungssystem
    public final int[][] blocks;
    public List<Entity> entities = new ArrayList<>();

    public World(String name, int maxXSize, int maxYSize) {
        this.name = name;
        this.maxXSize = maxXSize;
        this.maxYSize = maxYSize;
        this.blocks = new int[maxXSize][maxYSize];
        Worlds.REGISTERED_WORLDS.add(this);

    }

    public String getName() {
        return name;
    }

    public int getMaxXSize() {
        return maxXSize;
    }

    public int getMaxYSize() {
        return maxYSize;
    }

    public BlockEntry getBlock(int x, int y) {
        int id = blocks[x][y];
        return BlockRegistry.getBlockById(id);
    }

    public void setBlock(int id, int x, int y) {
        blocks[x][y] = id;
    }

    public void setBlock(BlockEntry blockEntry, int x, int y) {
        setBlock(blockEntry.getBlockId(), x, y);
    }

    public void setBlock(BlockEntry blockEntry, IntPosition position) {
        setBlock(blockEntry.getBlockId(), position.getX(), position.getY());
    }

    public void spawnEntity(Entity entity) {
        entities.add(entity);
    }

    public void killEntity(Entity entity) {
        entities.remove(entity);
    }

    public String getSaveLocation() {
        return "/voxelsociety/worlds/" + name + ".json";
    }

    public void tick() {
    }

    public void load() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(getSaveLocation());
            if(inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                int x = 0;
                int y = 0;

                while (x < maxXSize && y < maxYSize) {
                    String line = reader.readLine();

                    while (x < maxXSize) {
                        String[] numbers = line.split("-");
                        int number = Integer.parseInt(numbers[x]);
                        blocks[x][y] = number;
                        x++;
                    }
                    if(x == VoxelPanel.maxWorldColumns) {
                        x = 0;
                        y++;
                    }
                }

                reader.close();
            }
        } catch (Exception _) {
        }
    }

    public void generate() {

    }

    public void saveToJson() {

    }
}
