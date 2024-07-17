package de.Roboter007.voxelsociety.world.block;

import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.pos.IntPosition;

public class Block {
    private final BlockEntry blockEntry;
    private final World world;
    private IntPosition blockPos;

    public Block(BlockEntry blockEntry, World world, IntPosition blockPos) {
        this.blockEntry = blockEntry;
        this.world = world;
        this.blockPos = blockPos;
        this.world.setBlock(blockEntry, blockPos);
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

    public void placeBlock(int x, int y) {
        this.blockPos.setPos(x, y);
        this.world.setBlock(this.blockEntry, x, y);
    }

    public void placeBlock(IntPosition position) {
        this.blockPos = position;
        this.world.setBlock(this.blockEntry, position);
    }
}
