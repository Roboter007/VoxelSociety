package de.Roboter007.voxelsociety.utils.texture;

import java.awt.image.BufferedImage;
import java.util.*;

public class TextureManager {
    private final HashMap<String, BufferedImage> textureRegistry = new HashMap<>();

    public TextureManager() {

    }

    public void registerTexture(TexturePath texturePath, BufferedImage bufferedImage) {
        if(!textureRegistered(texturePath)) {
            textureRegistry.put(texturePath.fullLocation(), bufferedImage);
        }
    }

    public void unregisterTexture(PathLocation pathLocation) {
        textureRegistry.remove(pathLocation.texturePath.fullLocation());
    }

    public BufferedImage getTexture(TexturePath texturePath) {
        return textureRegistry.get(texturePath.fullLocation());
    }

    public boolean textureRegistered(TexturePath texturePath) {
        return textureRegistry.containsKey(texturePath.fullLocation());
    }

}
