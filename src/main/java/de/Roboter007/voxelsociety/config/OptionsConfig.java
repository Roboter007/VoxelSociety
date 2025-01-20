package de.Roboter007.voxelsociety.config;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.config.values.VoxelConfigValue;
import de.Roboter007.voxelsociety.utils.VoxelPaths;
import de.Roboter007.voxelsociety.world.World;
import de.Roboter007.voxelsociety.world.Worlds;

import java.awt.*;
import java.util.List;

public class OptionsConfig extends VoxelConfig {

    public static OptionsConfig options = new OptionsConfig();

    public OptionsConfig() {
        super(getDefaultConfigPath() + "/options.json");
        read();
    }

    @Override
    public List<VoxelConfigValue<?>> getDefaultOptions() {
        return List.of(
                new VoxelConfigValue<>(Integer.class, "fps"),
                new VoxelConfigValue<>(Boolean.class, "test"));
    }
}
