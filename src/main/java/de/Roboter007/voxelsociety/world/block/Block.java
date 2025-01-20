package de.Roboter007.voxelsociety.world.block;

import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.pos.IntPosition;

public class Block {
    private final BlockEntry blockEntry;
    private final World world;
    private final IntPosition blockPos;

    public Block(BlockEntry blockEntry, World world, IntPosition blockPos) {
        this.blockEntry = blockEntry;
        this.world = world;
        this.blockPos = blockPos;
        this.world.setBlock(blockEntry, blockPos);
    }

    public Block(int blockId, World world, IntPosition blockPos) {
        this(BlockRegistry.getBlockById(blockId), world, blockPos);
    }

    public BlockEntry getBlockEntry() {
        return blockEntry;
    }

    public IntPosition getBlockPos() {
        return blockPos;
    }

    public World getWorld() {
        return world;
    }
}
