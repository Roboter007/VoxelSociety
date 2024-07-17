package de.Roboter007.voxelsociety.world.collision;

import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.block.BlockEntry;
import de.Roboter007.voxelsociety.world.block.BlockRegistry;
import de.Roboter007.voxelsociety.world.entity.Entity;
import de.Roboter007.voxelsociety.utils.VoxelPanel;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class CollisionBox extends Rectangle {

    private boolean collision;
    private final World world;

    public CollisionBox(World world, int x, int y, int width, int height, boolean collision) {
        super(x, y, width, height);
        this.collision = collision;
        this.world = world;
    }

    public CollisionBox(World world) {
        super();
        this.collision = true;
        this.world = world;
    }

    public CollisionBox(World world, Rectangle r) {
        super(r.x, r.y, r.width, r.height);
        this.collision = true;
        this.world = world;
    }

    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public CollisionBox(CollisionBox c) {
        this(c.world, c.x, c.y, c.width, c.height, c.collision);
    }

    public boolean isCollisionOn() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void checkBlock(Entity entity) {
        int entityLeftWorldX = entity.blockPosition.getX() + entity.collisionBox.x;
        int entityRightWorldX = entity.blockPosition.getX() + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.blockPosition.getY() + entity.collisionBox.y;
        int entityBottomWorldY = entity.blockPosition.getY() + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol = entityLeftWorldX / VoxelPanel.tileSize;
        int entityRightCol = entityRightWorldX / VoxelPanel.tileSize;
        int entityTopRow = entityTopWorldY / VoxelPanel.tileSize;
        int entityBottomRow = entityBottomWorldY / VoxelPanel.tileSize;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = ((entityTopWorldY - entity.speed) / VoxelPanel.tileSize);
                BlockEntry blockEntry1 = world.getBlock(entityLeftCol,entityTopRow);
                BlockEntry blockEntry2 = world.getBlock(entityRightCol,entityTopRow);

                if (blockEntry1 != null && blockEntry2 != null) {
                    if (blockEntry1.canCollide() || blockEntry2.canCollide()) {
                        entity.collisionBox.setCollision(true);
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / VoxelPanel.tileSize;
                BlockEntry blockEntry1 = world.getBlock(entityLeftCol,entityBottomRow);
                BlockEntry blockEntry2 = world.getBlock(entityRightCol,entityBottomRow);

                if (blockEntry1 != null && blockEntry2 != null) {
                    if (blockEntry1.canCollide() || blockEntry2.canCollide()) {
                        entity.collisionBox.setCollision(true);
                    }
                }
            }
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / VoxelPanel.tileSize;
                BlockEntry blockEntry1 = world.getBlock(entityLeftCol,entityTopRow);
                BlockEntry blockEntry2 = world.getBlock(entityLeftCol,entityBottomRow);

                if (blockEntry1 != null && blockEntry2 != null) {
                    if (blockEntry1.canCollide() || blockEntry2.canCollide()) {
                        entity.collisionBox.setCollision(true);
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / VoxelPanel.tileSize;
                BlockEntry blockEntry1 = world.getBlock(entityRightCol,entityTopRow);
                BlockEntry blockEntry2 = world.getBlock(entityRightCol,entityBottomRow);

                if (blockEntry1 != null && blockEntry2 != null) {
                    if (blockEntry1.canCollide() || blockEntry2.canCollide()) {
                        entity.collisionBox.setCollision(true);
                    }
                }
            }
        }


    }

}
