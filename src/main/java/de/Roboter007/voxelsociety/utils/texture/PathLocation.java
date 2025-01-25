package de.Roboter007.voxelsociety.utils.texture;

public abstract class PathLocation {

    protected final SourcePath sourcePath;

    public PathLocation(SourcePath sourcePath) {
        this.sourcePath = sourcePath;
    }

    public PathLocation(String key, String location, String file) {
        this(new SourcePath(key, location, file));
    }

    public SourcePath sourcePath() {
        return sourcePath;
    }

}
