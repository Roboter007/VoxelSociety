package de.Roboter007.voxelsociety.utils.texture;

import java.awt.image.BufferedImage;
import java.util.*;

public class TextureManager {
    private final HashMap<String, BufferedImage> textureRegistry = new HashMap<>();

    public TextureManager() {

    }

    public void registerTexture(SourcePath sourcePath, BufferedImage bufferedImage) {
        if(!textureRegistered(sourcePath)) {
            textureRegistry.put(sourcePath.fullLocation(), bufferedImage);
        }
    }

    public void unregisterTexture(PathLocation pathLocation) {
        textureRegistry.remove(pathLocation.sourcePath.fullLocation());
    }

    public BufferedImage getTexture(SourcePath sourcePath) {
        return textureRegistry.get(sourcePath.fullLocation());
    }

    public boolean textureRegistered(SourcePath sourcePath) {
        return textureRegistry.containsKey(sourcePath.fullLocation());
    }

}
