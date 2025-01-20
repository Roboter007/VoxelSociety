package de.Roboter007.voxelsociety.utils.texture;

public abstract class PathLocation {

    protected final TexturePath texturePath;

    public PathLocation(TexturePath texturePath) {
        this.texturePath = texturePath;
    }

    public PathLocation(String key, String location, String file) {
        this(new TexturePath(key, location, file));
    }

    public TexturePath texturePath() {
        return texturePath;
    }

}
