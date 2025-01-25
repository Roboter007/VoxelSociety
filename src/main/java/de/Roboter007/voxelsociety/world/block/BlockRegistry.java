package de.Roboter007.voxelsociety.world.block;

import de.Roboter007.voxelsociety.utils.texture.PathLocation;
import de.Roboter007.voxelsociety.utils.texture.Texture;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {

    private static final List<BlockEntry> BLOCKS = new ArrayList<>();

    public static BlockEntry getBlockById(int id) {
        BlockEntry blockEntry = BLOCKS.get(id);
        if(blockEntry != null) {
            return blockEntry;
        } else {
            return Blocks.AIR;
        }
    }

    public static int getBlockId(BlockEntry blockEntry) {
        return BLOCKS.indexOf(blockEntry);
    }

    public static void registerBlock(BlockEntry blockEntry) {
        Texture texture = blockEntry.getTexture();
        if(texture != null) {
            texture.load();
            System.out.println("Loaded Texture for Block: " + texture.sourcePath().fullLocation());
        }
        BLOCKS.add(blockEntry);
    }


}
