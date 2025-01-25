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
    private final List<SourcePath> sourcePathList = new ArrayList<>();

    public AnimatedTexture(int width, int height, SourcePath... sourcePaths) {
        super(sourcePaths[0], width, height);
        sourcePathList.addAll(List.of(sourcePaths));
    }

    public List<SourcePath> texturePaths() {
        return sourcePathList;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int frame() {
        return frame;
    }

    public SourcePath currentFrameTexture() {
        return sourcePathList.get(frame);
    }

    @Override
    public BufferedImage loadImage() {
        for(SourcePath sourcePath : sourcePathList) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(sourcePath.fullLocation());
                if (inputStream != null) {
                    BufferedImage scaledImage = scaleImage(ImageIO.read(inputStream), width, height);
                    if(scaledImage != null) {
                        VoxelSociety.TEXTURE_MANAGER.registerTexture(sourcePath, scaledImage);
                    } else {
                        VoxelSociety.LOGGER.info("Couldn't load Textures: " + sourcePath.fullLocation());
                    }
                }
            } catch (IOException e) {
                VoxelSociety.LOGGER.info("Couldn't load Textures: " + sourcePath.fullLocation());
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
