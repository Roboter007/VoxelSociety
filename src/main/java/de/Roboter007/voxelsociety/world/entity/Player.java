package de.Roboter007.voxelsociety.world.entity;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.api.RecCalculator;
import de.Roboter007.voxelsociety.keys.KeyHandler;
import de.Roboter007.voxelsociety.ui.screen.MenuHandler;
import de.Roboter007.voxelsociety.utils.VoxelPaths;
import de.Roboter007.voxelsociety.utils.VoxelUtils;
import de.Roboter007.voxelsociety.utils.texture.Texture;
import de.Roboter007.voxelsociety.world.block.Block;
import de.Roboter007.voxelsociety.world.block.BlockEntry;
import de.Roboter007.voxelsociety.world.block.BlockRegistry;
import de.Roboter007.voxelsociety.world.collision.CollisionBox;
import de.Roboter007.voxelsociety.keys.KeyCategory;
import de.Roboter007.voxelsociety.world.entity.animation.AnimationState;
import de.Roboter007.voxelsociety.keys.VSKey;
import de.Roboter007.voxelsociety.utils.VoxelPanel;
import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.pos.IntPosition;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Player extends AnimatedEntity {

    private final String name;
    public final KeyHandler keyHandler = new KeyHandler();
    public Block targetedBlock = null;
    public final IntPosition screenPos = new IntPosition();
    public IntPosition cursor = null;
    private int counter = 0;
    private boolean canReplaceBlocks;
    public int[] hotbar = new int[10];
    private List<String> playerData = new ArrayList<>();

    public Player(String name, World world) {
        super(world, 15);
        this.name = name;

        screenPos.setX(VoxelPanel.screenWidth / 2 - (VoxelPanel.tileSize /2));
        screenPos.setY(VoxelPanel.screenHeight / 2 - (VoxelPanel.tileSize /2));

        collisionBox = new CollisionBox(this.world);
        collisionBox.x = 8;
        collisionBox.y = 16;
        collisionBox.width = 32;
        collisionBox.height = 32;

        setDefault();
    }

    public String getName() {
        return name;
    }

    public int getSelectedSlot() {
        return keyHandler.selectedSlot;
    }

    public int getBlockIdFromSelectedSlot() {
        return hotbar[getSelectedSlot()];
    }

    public void setBlockIdFromSelectedSlot(int id) {
        hotbar[getSelectedSlot()] = id;

        if(playerData.isEmpty()) {
            playerData.add("0000000000");
        }

        String hotbarContent = this.playerData.getFirst();
        if (hotbarContent != null && !hotbarContent.isBlank()) {
            char[] chars = hotbarContent.toCharArray();
            chars[getSelectedSlot()] = (char) (id + '0');
            hotbarContent = new String(chars);
        } else {
            StringBuilder invInfoBuilder = new StringBuilder();
            for (int i : hotbar) {
                invInfoBuilder.append(i);
            }
            hotbarContent = invInfoBuilder.toString();
        }
        this.playerData.set(0, hotbarContent);
        savePlayerData();
    }

    public void setDefault() {
        int x = 25;
        int y = 25;
        this.blockPosition.setPos(x * VoxelPanel.tileSize, y * VoxelPanel.tileSize);
        speed = 4;
    }

    public void update() {
        if(MenuHandler.isInGame() && !VoxelUtils.isPaused()) {
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
                if (counter == 20) {
                    spriteNumber = 1;
                    counter = 0;
                }

                counter++;
            }
        }
        updateTexture();
    }

    public Path getPlayerDataFolder() {
        if(this.world != null) {
            return Path.of(world.getWorldFolder().toString(), "/playerData");
        } else {
            return null;
        }
    }

    public Path getPlayerData() {
        Path playerDataFolder = getPlayerDataFolder();
        if(playerDataFolder != null) {
            playerDataFolder.toFile().mkdirs();
            return Path.of(playerDataFolder.toString(),getName() + ".json");
        } else {
            return null;
        }
    }

    public void createPlayerDataFile() {
        try {
            Path playerDataPath = getPlayerData();
            if(!playerDataPath.toFile().exists()) {
                if (getPlayerData().toFile().createNewFile()) {
                    System.out.println("File created: " + getPlayerData().toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readPlayerData() {
        if(getPlayerData().toFile().exists()) {
            System.out.println("Reading PlayerData...");
            this.playerData = VoxelPaths.readLines(getPlayerData().toString());

            System.out.println("Player Data: " + this.playerData);
        }
    }

    public void savePlayerData() {
        Thread saveThread = new Thread(() -> {
            try {
                Files.write(getPlayerData(), playerData);
                System.out.println("saved Player Data: " + playerData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        VoxelSociety.SERVICE.submit(saveThread);

    }

    public boolean showPauseScreen;
    public boolean showInfoScreen;

    @Override
    public void tick() {

    }

    public void drawPlayer(Graphics2D graphics2D) {
        pathLocation.drawImage(graphics2D, screenPos.getX(), screenPos.getY());
        if(VSKey.DEBUG_COLLISION.isPressed()) {
            graphics2D.setColor(Color.BLUE);
            graphics2D.drawRect(screenPos.getX() + collisionBox.x, screenPos.getY() + collisionBox.y, collisionBox.width, collisionBox.height);
        }
    }

    public void drawSelectionBar(Graphics2D graphics2D) {
        Texture texture = Texture.voxelSociety("gui", "hotbar.png", 180 * VoxelPanel.scale, 18 * VoxelPanel.scale);
        RecCalculator recCalculator = new RecCalculator();
        recCalculator.calc(180 * VoxelPanel.scale, 18 * VoxelPanel.scale);

        graphics2D.setColor(newColorWithAlpha(Color.decode("#9d9d9d"), 127));
        graphics2D.fillRect(recCalculator.getX(), VoxelPanel.screenHeight - 100, 180 * VoxelPanel.scale, 18 * VoxelPanel.scale);

        for (int slot = 0; slot < 10; slot++) {
            BlockEntry entry = BlockRegistry.getBlockById(hotbar[slot]);
            if(entry.getBlockId() != 0) {
                entry.getTexture().drawImage(graphics2D, recCalculator.getX() + (18 * VoxelPanel.scale) * slot + VoxelPanel.scale, VoxelPanel.screenHeight - 100 + VoxelPanel.scale);
            }
        }

        texture.drawImage(graphics2D, recCalculator.getX(), VoxelPanel.screenHeight - 100);

        graphics2D.setColor(Color.RED);
        Stroke oldStroke = graphics2D.getStroke();

        graphics2D.setStroke(new BasicStroke(4));
        graphics2D.drawRect(recCalculator.getX() + (18 * VoxelPanel.scale) * getSelectedSlot(), VoxelPanel.screenHeight - 100, VoxelPanel.scale * 18, VoxelPanel.scale * 18);
        graphics2D.setStroke(oldStroke);

    }

    public static Color newColorWithAlpha(Color original, int alpha) {
        return new Color(original.getRed(), original.getGreen(), original.getBlue(), alpha);
    }

    @Override
    public void updateTexture() {
        Texture newTextureLocation = Texture.voxelSociety("player", "player_" + this.animationState.getName() + "_" + spriteNumber + ".png");
        if(!newTextureLocation.equals(pathLocation)) {
            pathLocation = newTextureLocation;
        }
    }

    public World world() {
        return this.world;
    }

    public void updateInv() {
        int i = 0;
        if(!this.playerData.isEmpty() && this.playerData.getFirst() != null && !this.playerData.getFirst().isEmpty()) {
            for (char c : this.playerData.getFirst().toCharArray()) {
                this.hotbar[i] = Integer.parseInt(c + "");
                i++;
            }
        } else {
            this.hotbar = new int[]{0,0,0,0,0,0,0,0,0,0};
        }
    }

    public boolean isInWorld() {
        return this.world != null;
    }

    public void changeWorld(World toWorld) {
        if(this.world != null) {
            this.world.stop();
        }
        this.world = toWorld;
        readPlayerData();
        this.createPlayerDataFile();

        updateInv();

        toWorld.start();
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

                BlockEntry blockEntry = this.world.getBlockEntry(worldColumn, worldRow);
                Texture texture = blockEntry.getTexture();
                if (texture != null) {
                    texture.drawImage(g2, screenX, screenY);
                }

                //ToDo: Add change y-Axis to z Axis and add y-Axis which represents the World Layers

                //ToDo: Improve Performance
                if (contains(screenX, screenY, VoxelPanel.tileSize, VoxelPanel.tileSize, this.keyHandler.cursorPos.getX(), this.keyHandler.cursorPos.getY())) {
                    targetedBlock = this.world.getBlock(worldColumn, worldRow);
                    g2.setColor(Color.RED);
                    g2.drawRect(screenX - 1, screenY - 1, VoxelPanel.tileSize, VoxelPanel.tileSize);
                }
            }



            worldColumn++;

            if (worldColumn == this.world.getMaxXSize()) {
                worldColumn = 0;
                worldRow++;
            }
        }
    }

    public boolean contains(int x, int y, int w, int h, int X, int Y) {
        if ((w | h) < 0) {
            return false;
        }
        if (X < x || Y < y) {
            return false;
        }
        w += x;
        h += y;

        return ((w < x || w > X) &&
                (h < y || h > Y));
    }

}
