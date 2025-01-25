package de.Roboter007.voxelsociety.utils.texture;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.utils.VoxelPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Texture extends PathLocation {

    protected final int width;
    protected final int height;

    public Texture(String key, String location, String file, int width, int height) {
        super(key, location, file);
        this.width = width;
        this.height = height;
    }

    public Texture(SourcePath sourcePath, int width, int height) {
        super(sourcePath);
        this.width = width;
        this.height = height;
    }

    public static Texture voxelSociety(String location, String file) {
        return new Texture(VoxelSociety.GAME_ID, location, file, VoxelPanel.tileSize, VoxelPanel.tileSize);
    }

    public static Texture voxelSociety(String location, String file, int width, int height) {
        return new Texture(VoxelSociety.GAME_ID, location, file, width, height);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public BufferedImage loadImage() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(sourcePath.fullLocation());
            if (inputStream != null) {
                VoxelSociety.LOGGER.info("Loading Texture... " + sourcePath.file());
                BufferedImage scaledImage = scaleImage(ImageIO.read(inputStream), width, height);

                if(scaledImage != null) {
                    VoxelSociety.TEXTURE_MANAGER.registerTexture(sourcePath, scaledImage);
                    VoxelSociety.LOGGER.info("Loaded Texture: " + sourcePath.file());
                } else {
                    VoxelSociety.LOGGER.info("Couldn't load Textures: " + sourcePath.fullLocation());
                }

                return scaledImage;
            }
        } catch (IOException e) {
            VoxelSociety.LOGGER.info("Couldn't load Textures: " + sourcePath.fullLocation());
        }
        return null;
    }

    public void drawImage(Graphics2D graphics2D, int screenX, int screenY) {
        BufferedImage test = load();
        graphics2D.drawImage(test, screenX, screenY, null);
    }

    public BufferedImage load() {
        if (!VoxelSociety.TEXTURE_MANAGER.textureRegistered(sourcePath)) {
            return loadImage();
        } else {
            return VoxelSociety.TEXTURE_MANAGER.getTexture(sourcePath);
        }
    }

    public static BufferedImage scaleImage(BufferedImage original, int w, int h) {
        if (original != null) {
            BufferedImage scaledImage = new BufferedImage(w, h, original.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(original, 0, 0, w, h, null);
            graphics2D.dispose();
            return scaledImage;
        } else {
            return null;
        }
    }
}
