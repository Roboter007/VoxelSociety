package de.Roboter007.voxelsociety.world.block;

import de.Roboter007.voxelsociety.utils.texture.Texture;
import de.Roboter007.voxelsociety.utils.texture.PathLocation;

public class BlockEntry {
    private final Texture texture;
    private boolean collision;
    private final String name;

    public BlockEntry(Texture texture, boolean collision) {
        this.name = texture.sourcePath().file();
        this.texture = texture;
        this.collision = collision;
        BlockRegistry.registerBlock(this);
    }

    public BlockEntry(String name, boolean collision) {
        this.name = name;
        this.texture = Texture.voxelSociety("block", name + ".png");
        this.collision = collision;
        BlockRegistry.registerBlock(this);
    }

    public boolean canCollide() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public int getBlockId() {
        return BlockRegistry.getBlockId(this);
    }

    public Texture getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

}
