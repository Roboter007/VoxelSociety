package de.Roboter007.voxelsociety.utils;

import de.Roboter007.voxelsociety.VoxelSociety;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public record TextureLocation(String key, String location, String file) {

    public String fullLocation() {
        return "/" + key + "/" + location + "/" + file;
    }

    public BufferedImage load() {
        if (!VoxelSociety.TEXTURE_MANAGER.isTextureRegistered(this)) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(fullLocation());
                if (inputStream != null) {
                    VoxelSociety.LOGGER.info("Loading Texture... " + file);

                    BufferedImage scaledImage = scaleImage(ImageIO.read(inputStream), VoxelPanel.tileSize, VoxelPanel.tileSize);
                    VoxelSociety.TEXTURE_MANAGER.registerTexture(this, scaledImage);

                    VoxelSociety.LOGGER.info("Loaded Texture: " + file);

                    return scaledImage;
                }
            } catch (IOException e) {
                VoxelSociety.LOGGER.info("Couldn't load Textures");
            }
        } else {
            return VoxelSociety.TEXTURE_MANAGER.getTexture(this);
        }
        return null;
    }

    public void drawImage(Graphics2D graphics2D, int screenX, int screenY) {
        graphics2D.drawImage(load(), screenX, screenY, null);
    }

    public static TextureLocation voxelSociety(String location, String file) {
        return new TextureLocation(VoxelSociety.GAME_NAME.toLowerCase(), location, file);
    }

    public static BufferedImage scaleImage(BufferedImage original, int w, int h) {
        BufferedImage scaledImage = new BufferedImage(w, h, original.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, w, h, null);
        graphics2D.dispose();
        return scaledImage;
    }


}
