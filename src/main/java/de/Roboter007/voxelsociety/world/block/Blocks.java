package de.Roboter007.voxelsociety.world.block;

import de.Roboter007.voxelsociety.utils.TextureLocation;

public class Blocks {

    public static void loadTextures() {}

    public static BlockEntry GRASS = new BlockEntry(TextureLocation.voxelSociety("block", "grass_block.png"), false, 1);
    public static BlockEntry STONE = new BlockEntry(TextureLocation.voxelSociety("block", "stone.png"), false, 1);
    public static BlockEntry ROCK = new BlockEntry(TextureLocation.voxelSociety("block", "rock.png"), true, 1);
    public static BlockEntry HILL = new BlockEntry(TextureLocation.voxelSociety("block", "hill.png"), true, 1);
    public static BlockEntry SAND = new BlockEntry(TextureLocation.voxelSociety("block", "sand.png"), false, 1);
}
