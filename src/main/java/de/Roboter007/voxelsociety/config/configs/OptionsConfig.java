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
                new VoxelConfigOption<>("test", new BooleanConfigValue(false))));
    }

}
