package de.Roboter007.voxelsociety.sound;

import de.Roboter007.voxelsociety.config.VoxelConfigs;

public enum SoundType {

    MUSIC("music_volume"),
    SOUND_EFFECT("sound_effect_volume");

    private final String optionKey;

    SoundType(String optionKey) {
        this.optionKey = optionKey;
    }

    public double volume() {
        return (double) volumeFromConfig();
    }

    public Integer volumeFromConfig() {
        return VoxelConfigs.optionsConfig.getOptionWithFallback(optionKey, 100);
    }

    public void setVolume(Number volume) {
        VoxelConfigs.optionsConfig.setOption(optionKey, volume);
    }
}
