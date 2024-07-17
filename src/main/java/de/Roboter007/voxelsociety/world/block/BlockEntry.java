package de.Roboter007.voxelsociety.world.block;

import de.Roboter007.voxelsociety.utils.TextureLocation;
import de.Roboter007.voxelsociety.world.Layerable;

public class BlockEntry implements Layerable {
    private final TextureLocation textureLocation;
    private boolean collision;
    private int renderLayer;

    public BlockEntry(TextureLocation textureLocation, boolean collision, int layer) {
        this.textureLocation = textureLocation;
        this.collision = collision;
        this.renderLayer = layer;
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

    public TextureLocation getTextureLocation() {
        return textureLocation;
    }

    @Override
    public int getLayer() {
        return renderLayer;
    }

    @Override
    public void setLayer(int layer) {
        this.renderLayer = layer;
    }

}
