package de.Roboter007.voxelsociety.utils;

import java.awt.image.BufferedImage;
import java.util.*;

public class TextureManager {
    private final HashMap<String, BufferedImage> textureRegistry = new HashMap<>();

    public TextureManager() {

    }

    public void registerTexture(TextureLocation textureLocation, BufferedImage bufferedImage) {
        if(!isTextureRegistered(textureLocation)) {
            textureRegistry.put(textureLocation.fullLocation(), bufferedImage);
        }
    }

    public void unregisterTexture(TextureLocation textureLocation) {
        textureRegistry.remove(textureLocation.fullLocation());
    }

    public BufferedImage getTexture(TextureLocation textureLocation) {
        return textureRegistry.get(textureLocation.fullLocation());
    }

    public boolean isTextureRegistered(TextureLocation textureLocation) {
        return textureRegistry.containsKey(textureLocation.fullLocation());
    }

}
