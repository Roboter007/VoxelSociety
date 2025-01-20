package de.Roboter007.voxelsociety.config;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.config.values.VoxelConfigValue;
import de.Roboter007.voxelsociety.utils.VoxelPaths;
import de.Roboter007.voxelsociety.world.WorldSettings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class VoxelConfig {

    private List<VoxelConfigValue<?>> options = Collections.synchronizedList(new ArrayList<>());
    private final String path;
    private final File file;

    public VoxelConfig(String path) {
        this.path = path;
        this.file = new File(this.path);
        getDefaultConfigPath();
        this.options.addAll(getDefaultOptions());
        saveToFile();
    }

    public VoxelConfig(String path, List<VoxelConfigValue<?>> options) {
        this(path);
        this.options.addAll(options);
    }

    public abstract List<VoxelConfigValue<?>> getDefaultOptions();

    public List<VoxelConfigValue<?>> getOptions() {
        return options;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public void setOptions(List<VoxelConfigValue<?>> options) {
        this.options = options;
    }

    public void addOption(VoxelConfigValue<?> value) {
        this.options.add(value);
    }

    @SuppressWarnings("unchecked")
    public <T> void setOption(int opId, T newValue) {
        VoxelConfigValue<T> value = (VoxelConfigValue<T>) this.options.get(opId);
        value.setValue(newValue);
        this.options.set(opId, value);
        saveToFile();
    }

    @SuppressWarnings("unchecked")
    public <T> T getOption(int opId) {
        return (T) this.options.get(opId).getValue();
    }

    @SuppressWarnings("unchecked")
    public <T> String getOptionWithFallback(int opId, String fallback) {
        if(this.options.get(opId).getValue() == null) {
            return fallback;
        } else {
            return (String) this.options.get(opId).getValue();
        }
    }
    public void read() {
        this.options.clear();
        List<String> dataList = VoxelPaths.readLines(file.getPath());

        for(String data : dataList) {
            this.options.add(VoxelConfigValue.newConfigValue(data));
        }
    }

    public static WorldSettings fromFile(String worldName, File file) {
        if(file.exists()) {
            List<String> lines = VoxelPaths.readLines(file.getPath());
            List<Object> worldOptions = new ArrayList<>();
            for(String line : lines) {
                int index = lines.indexOf(line);
                worldOptions.add(index, line);
            }
            return new WorldSettings(worldName, worldOptions);
        } else {
            System.out.println("Couldn't find file: " + file.getPath());
        }

        return null;
    }

    public void saveToFile() {
        if(!file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> lines = new ArrayList<>();

        for(VoxelConfigValue<?> value : this.options) {
            lines.add(value.toString());
        }

        try {
            Files.write(Path.of(this.getPath()), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Path getDefaultConfigPath() {
        Path path = Path.of(System.getProperty("user.home") + "/AppData/Roaming/" + VoxelSociety.GAME_NAME + "/config");
        if(path.toFile().mkdirs()) {
            System.out.println("Created Config Path!");
        }
        return path;
    }


}
