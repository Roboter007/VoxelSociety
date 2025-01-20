package de.Roboter007.voxelsociety.utils.texture;

import de.Roboter007.voxelsociety.VoxelSociety;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AnimatedTexture extends Texture {

    private int frame;
    private final List<TexturePath> texturePathList = new ArrayList<>();

    public AnimatedTexture(int width, int height, TexturePath... texturePaths) {
        super(texturePaths[0], width, height);
        texturePathList.addAll(List.of(texturePaths));
    }

    public List<TexturePath> texturePaths() {
        return texturePathList;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int frame() {
        return frame;
    }

    public TexturePath currentFrameTexture() {
        return texturePathList.get(frame);
    }

    @Override
    public BufferedImage loadImage() {
        for(TexturePath texturePath : texturePathList) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(texturePath.fullLocation());
                if (inputStream != null) {
                    BufferedImage scaledImage = scaleImage(ImageIO.read(inputStream), width, height);
                    if(scaledImage != null) {
                        VoxelSociety.TEXTURE_MANAGER.registerTexture(texturePath, scaledImage);
                    } else {
                        VoxelSociety.LOGGER.info("Couldn't load Textures: " + texturePath.fullLocation());
                    }
                }
            } catch (IOException e) {
                VoxelSociety.LOGGER.info("Couldn't load Textures: " + texturePath.fullLocation());
            }
        }

        return VoxelSociety.TEXTURE_MANAGER.getTexture(currentFrameTexture());
    }

    @Override
    public void drawImage(Graphics2D graphics2D, int screenX, int screenY) {
        BufferedImage test = load();
        graphics2D.drawImage(test, screenX, screenY, null);
    }
}
