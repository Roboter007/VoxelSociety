package de.Roboter007.voxelsociety.sound;

public enum SoundType {

    MUSIC(1D),
    SOUND_EFFECT(1D);

    private final double volume;

    SoundType(double volume) {
        this.volume = volume;
    }

    public double volume() {
        return volume;
    }
}
