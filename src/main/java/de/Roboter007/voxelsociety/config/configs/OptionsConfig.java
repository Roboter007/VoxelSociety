package de.Roboter007.voxelsociety.config.configs;

import de.Roboter007.voxelsociety.config.VoxelConfig;
import de.Roboter007.voxelsociety.config.VoxelConfigOption;
import de.Roboter007.voxelsociety.config.values.BooleanConfigValue;
import de.Roboter007.voxelsociety.config.values.IntConfigValue;
import de.Roboter007.voxelsociety.utils.JavaUtils;
import de.Roboter007.voxelsociety.utils.VoxelPanel;

public class OptionsConfig extends VoxelConfig {

    public OptionsConfig() {
        super(getDefaultConfigPath() + "/options.json", JavaUtils.arrayListOf(
                new VoxelConfigOption<>("fps", new IntConfigValue(VoxelPanel.screen_fps_limit)),
                new VoxelConfigOption<>("general_volume", new IntConfigValue(100)),
                new VoxelConfigOption<>("music_volume", new IntConfigValue(100)),
                new VoxelConfigOption<>("sound_effect_volume", new IntConfigValue(100))));
    }

}
