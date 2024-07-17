package de.Roboter007.voxelsociety.world.block;

import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {

    private static final List<BlockEntry> BLOCKS = new ArrayList<>();

    public static BlockEntry getBlockById(int id) {
         return BLOCKS.get(id);
    }

    public static int getBlockId(BlockEntry blockEntry) {
        return BLOCKS.indexOf(blockEntry);
    }

    /*public void getBlockImage() {
         registerBlock(Blocks.GRASS);
         registerBlock(Blocks.STONE);
         registerBlock(Blocks.ROCK);
         registerBlock(Blocks.HILL);
         registerBlock(Blocks.SAND);
    } */

    public static void registerBlock(BlockEntry blockEntry) {
        blockEntry.getTextureLocation().load();
        System.out.println("Loaded Texture for Block: " + blockEntry.getTextureLocation().fullLocation());
        BLOCKS.add(blockEntry);
    }


}
