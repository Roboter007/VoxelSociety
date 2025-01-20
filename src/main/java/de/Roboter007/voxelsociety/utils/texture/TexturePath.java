package de.Roboter007.voxelsociety.utils.texture;

public record TexturePath(String key, String location, String file) {

    public String fullLocation() {
        return "/" + key + "/" + location + "/" + file;
    }
}
