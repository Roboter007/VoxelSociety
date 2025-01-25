package de.Roboter007.voxelsociety.utils.texture;

public record SourcePath(String key, String location, String file) {

    public String fullLocation() {
        return "/" + key + "/" + location + "/" + file;
    }
}
