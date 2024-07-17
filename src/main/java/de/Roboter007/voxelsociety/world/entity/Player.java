package de.Roboter007.voxelsociety.world.entity;

import de.Roboter007.voxelsociety.keys.KeyHandler;
import de.Roboter007.voxelsociety.world.Worlds;
import de.Roboter007.voxelsociety.world.block.BlockEntry;
import de.Roboter007.voxelsociety.world.collision.CollisionBox;
import de.Roboter007.voxelsociety.keys.KeyCategory;
import de.Roboter007.voxelsociety.world.entity.animation.AnimationState;
import de.Roboter007.voxelsociety.keys.VSKey;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.utils.TextureLocation;
import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.pos.IntPosition;

import java.awt.*;

public class Player extends AnimatedEntity {

    public final KeyHandler keyHandler = new KeyHandler();
    public final IntPosition screenPos = new IntPosition();
    private int counter = 0;

    public Player(World world) {
        super(world, 15);

        screenPos.setX(VoxelPanel.screenWidth / 2 - (VoxelPanel.tileSize /2));
        screenPos.setY(VoxelPanel.screenHeight / 2 - (VoxelPanel.tileSize /2));

        collisionBox = new CollisionBox(this.world);
        collisionBox.x = 8;
        collisionBox.y = 16;
        collisionBox.width = 32;
        collisionBox.height = 32;

        setDefault();
    }

    public void setDefault() {
        int x = 25;
        int y = 25;
        this.blockPosition.setPos(VoxelPanel.tileSize * x, VoxelPanel.tileSize * y);
        speed = 4;
    }

    public void update() {
        if (VSKey.isKeyPressedByCategory(KeyCategory.WALKING)) {
            if (VSKey.WALK_UP.isPressed()) {
                animationState = AnimationState.UP;
            }
            if (VSKey.WALK_DOWN.isPressed()) {
                animationState = AnimationState.DOWN;
            }
            if (VSKey.WALK_LEFT.isPressed()) {
                animationState = AnimationState.LEFT;
            }
            if (VSKey.WALK_RIGHT.isPressed()) {
                animationState = AnimationState.RIGHT;
            }
            this.direction = animationState.getName().toLowerCase();

            super.update();
            updateTexture();

            collisionBox.setCollision(false);
            collisionBox.checkBlock(this);

            if (!collisionBox.isCollisionOn()) {
                if (VSKey.WALK_UP.isPressed()) {
                    this.blockPosition.minus(0, speed);
                }
                if (VSKey.WALK_DOWN.isPressed()) {
                    this.blockPosition.add(0, speed);
                }
                if (VSKey.WALK_LEFT.isPressed()) {
                    this.blockPosition.minus(speed, 0);
                }
                if (VSKey.WALK_RIGHT.isPressed()) {
                    this.blockPosition.add(speed, 0);
                }
            }
        } else {
            if(counter == 20) {
                spriteNumber = 1;
                updateTexture();
                counter = 0;
            }

            counter++;
        }

    }

    public void drawPlayer(Graphics2D graphics2D) {
        textureLocation.drawImage(graphics2D, screenPos.getX(), screenPos.getY());
        if(VSKey.DEBUG_COLLISION.isPressed()) {
            graphics2D.setColor(Color.BLUE);
            graphics2D.drawRect(screenPos.getX() + collisionBox.x, screenPos.getY() + collisionBox.y, collisionBox.width, collisionBox.height);
        }
    }

    @Override
    public void updateTexture() {
        TextureLocation newTextureLocation = TextureLocation.voxelSociety("player", "player_" + this.animationState.getName() + "_" + spriteNumber + ".png");
        if(!newTextureLocation.equals(textureLocation)) {
            textureLocation = newTextureLocation;
        }
    }

    public World world() {
        return this.world;
    }

    public void changeWorld(World toWorld) {
        this.world = toWorld;
    }

    public void drawScreen(Graphics2D g2) {
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < this.world.getMaxXSize() && worldRow < this.world.getMaxYSize()) {
            int worldX = worldColumn * VoxelPanel.tileSize;
            int worldY = worldRow * VoxelPanel.tileSize;
            int screenX = worldX - this.blockPosition.getX() + this.screenPos.getX();
            int screenY = worldY - this.blockPosition.getY() + this.screenPos.getY();

            if (worldX + VoxelPanel.tileSize > this.blockPosition.getX() - this.screenPos.getX()
                    && worldX - VoxelPanel.tileSize < this.blockPosition.getX() + this.screenPos.getX()
                    && worldY + VoxelPanel.tileSize > this.blockPosition.getY() - this.screenPos.getY()
                    && worldY - VoxelPanel.tileSize < this.blockPosition.getY() + this.screenPos.getY()) {
                BlockEntry blockEntry = this.world.getBlock(worldColumn, worldRow);
                blockEntry.getTextureLocation().drawImage(g2, screenX, screenY);
            }
            worldColumn++;

            if (worldColumn == this.world.getMaxXSize()) {
                worldColumn = 0;
                worldRow++;
            }
        }
    }
}
