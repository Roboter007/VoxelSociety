package de.Roboter007.voxelsociety.config;

import de.Roboter007.voxelsociety.VoxelSociety;
import de.Roboter007.voxelsociety.config.values.VoxelConfigValue;
import de.Roboter007.voxelsociety.utils.VoxelPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//ToDo: Config updater
public abstract class VoxelConfig {

    private final List<VoxelConfigOption<?>> defaultOptions;
    private List<VoxelConfigOption<?>> options;
    private final String path;
    private final File file;

    public VoxelConfig(String path, List<VoxelConfigOption<?>> defaultOptions) {
        this.defaultOptions = defaultOptions;
        this.path = path;
        this.file = new File(this.path);
        this.options = read();
    }

    public List<VoxelConfigOption<?>> getDefaultOptions() {
        return defaultOptions;
    }

    public List<VoxelConfigOption<?>> getOptions() {
        return options;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public void setOptions(List<VoxelConfigOption<?>> options) {
        this.options = options;
    }


    public <T> void setOption(int opId, T newData) {
        VoxelConfigOption<T> configOption = getConfigOption(opId);

        VoxelConfigValue<T> data = configOption.getConfigValue();
        data.setValue(newData);

        configOption.setConfigValue(data);

        this.options.set(opId, configOption);

        saveToFile();
    }

    public <T> void setOption(String optionKey, T newData) {
        int opId = getOpId(optionKey);
        if(opId == -1) {
            System.out.println("Error: the OptionKey - " + optionKey + " does not exist");
        } else {
            VoxelConfigOption<T> configOption = getConfigOption(opId);

            VoxelConfigValue<T> data = configOption.getConfigValue();
            data.setValue(newData);

            configOption.setConfigValue(data);

            this.options.set(opId, configOption);

            saveToFile();
        }
    }

    public int getOpId(String optionKey){
        for(VoxelConfigOption<?> voxelConfigOption : this.options) {
            if(voxelConfigOption.getName().equals(optionKey)) {
                return this.options.indexOf(voxelConfigOption);
            }
        }
        return -1;
    }



    @SuppressWarnings("unchecked")
    public <T> T getValue(int opId) {
        return (T) this.options.get(opId).getConfigValue().getValue();
    }

    @SuppressWarnings("unchecked")
    public <T> VoxelConfigOption<T> getConfigOption(int opId) {
        return (VoxelConfigOption<T>) this.options.get(opId);
    }

    public <T> T getOptionWithFallback(int opId, T fallback) {
        T data = getValue(opId);

        if(data == null) {
            return fallback;
        } else {
            return data;
        }
    }

    public <T> T getOptionWithFallback(String optionKey, T fallback) {
        int opId = getOpId(optionKey);
        if(opId == -1) {
            System.out.println("Error: the OptionKey - " + optionKey + " does not exist");
            return fallback;
        } else {
            T data = getValue(opId);

            if (data == null) {
                return fallback;
            } else {
                return data;
            }
        }
    }

    public List<VoxelConfigOption<?>> read() {
        List<VoxelConfigOption<?>> options = new ArrayList<>();

        if(file != null) {
            List<String> dataList = VoxelPaths.readLines(file.getPath());

            if (dataList.isEmpty()) {
                return defaultOptions;
            }

            for (String data : dataList) {
                options.add(VoxelConfigOption.newConfigValue(data));
            }
        }

        return options;
    }


    public void saveToFile() {
        if(!file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                System.out.println("File could not be created!!!");
            }
        }

        List<String> lines = new ArrayList<>();


        for(VoxelConfigOption<?> value : this.options) {
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
